package utilities;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class PetStoreBase {

    public static RequestSpecification requestSpec;
    public static ResponseSpecification responseSpec;

    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we don't need to type each http method.
        baseURI = ConfigReader.getProperty("baseURI");
       // port = 7000;
      //  basePath ="/api";

        requestSpec = given()
                        .accept(ContentType.JSON)
                        .and()
                        .log().all();


       responseSpec = expect().statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .logDetail(LogDetail.ALL);  //logging with response specification

    }

    @AfterAll
    public static void close(){
        //reset the info we set above ,method comes from restAssured
        reset();
    }
}
