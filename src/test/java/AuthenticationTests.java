import model.User;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static helpers.DataHelper.generateRandomEmail;
import static io.restassured.RestAssured.given;

public class AuthenticationTests extends Base{

    @Test(description = "This test aims to register a user")
    public void testRegister(){
        User testUser = new User(
                    "Pablo Juan",
                    generateRandomEmail(),
                    "password");

        String email = testUser.getEmail();

        given()
                .body(testUser)
            .when()
                .post("/v1/user/register")
            .then()
                .statusCode(200)
                .body("user.email", Matchers.equalTo(email))
                .body("user.name", Matchers.equalTo("Pablo Juan"));
    }

    @Test(description = "This test aims to register login a user")
    public void testDuplicateRegister(){
        User testUser = new User(
                "Pablo Juan",
                "uqudwkj@testemail.com",
                "password");

        given()
                .body(testUser)
                .when()
                .post("/v1/user/register")
                .then()
                .statusCode(406)
                .body("message", Matchers.equalTo("User already exists"));
    }

    @Test(description = "This test aims to register login a user")
    public void testLogin(){
        User testUser = new User(
                "Pablo Juan",
                "uqudwkj@testemail.com",
                "password");

        given()
                .body(testUser)
                .when()
                .post("/v1/user/login")
                .then()
                .statusCode(200)
                .body("token.access_token", Matchers.notNullValue())
                .body("user.email", Matchers.equalTo("uqudwkj@testemail.com"));
    }

    @Test(description = "This test aims to register an invalid user")
    public void testInvalidLogin(){
        User testUser = new User(
                "Pablo Juan",
                "asdasdasnotexist@testemail.com",
                "password");

        given()
                .body(testUser)
                .when()
                .post("/v1/user/login")
                .then()
                .statusCode(404)
                .body("message", Matchers.equalTo("Invalid login details"));
    }

}
