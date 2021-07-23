import Specififactions.RequestSpecifications;
import Specififactions.ResponseSpecifications;
import helpers.RequestHelpers;
import model.Article;
import model.User;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static helpers.DataHelper.generateRandomEmail;
import static io.restassured.RestAssured.given;

public class ArticlesTests extends Base{

    @Test(description = "This test aims to create a new article")
    public void createArticle(){

        Article testArticle = new Article("randomTitle", "Lorem Impusim short mode");

        given().spec(RequestSpecifications.useJWTAuthentication())
                .body(testArticle)
            .when()
                .post("/v1/article")
            .then()
                .spec(ResponseSpecifications.validatePositiveResponse())
                .body("message", Matchers.equalTo("Article created"));
    }

    @Test(description = "This test aims to get all articles")
    public void getAllArticles(){

        int id = RequestHelpers.createRandomArticleAndGetID();

        given().spec(RequestSpecifications.useJWTAuthentication())
                .when()
                .get("/v1/articles")
                .then()
                .statusCode(200)
                .body("results[0].data[0].id", Matchers.equalTo(id));

        RequestHelpers.cleanUpArticle(id);

    }

}
