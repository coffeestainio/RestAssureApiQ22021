import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class Base {

    @Parameters("host")
    @BeforeSuite
    public void setup(@Optional("http://localhost:9000") String host) {

        System.out.println(String.format("Test Host: %s", host));

        RestAssured.baseURI = host;
    }

}
