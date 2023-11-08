package PetStore.pet.Tasks;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.*;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit5.*;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.*;
import utilities.ConfigReader;
import utilities.PetStoreUtil;


import static io.restassured.RestAssured.*;
import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.*;

//@Disabled
@DisplayName("TASKS")
@SerenityTest
public class PetTests {

    public static long id;
    public static String responseBodyTask1;

    @BeforeAll
    public static void init(){

        // we can use baseURI for all tests from any environment how we define
        // also we can use test base
       baseURI = ConfigReader.getProperty("baseURI");
    }




    @DisplayName("Task1 POST a pet with JSON body")
    @Test
    public void test1() throws Exception {

    /*        For getting a JSON for a pet
        ** 1st way:  We can use JSON data from resources

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode petJsonData = objectMapper.readTree(
                           getClass().getResourceAsStream("/petData.json")
        );
    */
        //** 2nd way: we can generate random pet information with using GenerateRandomPet method from PetStoreUtil
        // if we use this scenario we have to change the name of the pet with dynamic version

        ObjectNode petJsonData = PetStoreUtil.GenerateRandomPet();
        System.out.println("petJsonData = " + petJsonData);

        // request with JSON body
        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(petJsonData)
                .when().post("/pet").prettyPeek();


        // validations
        Ensure.that("Status code is 200", vRec -> vRec.statusCode(200));
        Ensure.that("Id is not null value", vRec -> vRec.body("id", is(notNullValue())));
        Ensure.that("Name is Scout", vRec -> vRec.body("name", is("Scout")));
        Ensure.that("Content type is JSON", vRec -> vRec.contentType(ContentType.JSON));
        Ensure.that("Header includes date value", vRec -> vRec.header("date",is(notNullValue())));


        // get body and make it global for second task
        responseBodyTask1 = lastResponse().asString();
        JsonPath jsonPath = lastResponse().jsonPath();

        // get id and make it global for second task
        id = jsonPath.getLong("id");
        System.out.println("id = " + id);

    }

    @DisplayName("Task2 GET a pet by id")
    @Test
    public void test2(){

        // request with id
       given().accept(ContentType.JSON)
                .log().all()
                .pathParam("id", id)
                .when().get("/pet/{id}");


       // validations
        Ensure.that("Status code is 200", vRec -> vRec.statusCode(200));
        Ensure.that("Content type is JSON", vRec -> vRec.contentType(ContentType.JSON));
        Ensure.that("Header includes date value", vRec -> vRec.header("date",is(notNullValue())));


        // compare two body
        String responseBodyTask2 = lastResponse().asString();
        Assertions.assertEquals(responseBodyTask1,responseBodyTask2);


    }

    @DisplayName("Task3 DELETE a pet by id")
    @Test
    public void test3(){

        // request with id
        given().accept(ContentType.JSON)
                .pathParam("id",id)
                .when().delete("/pet/{id}").prettyPeek();


        // validations
        Ensure.that("Status code is 200", vRec -> vRec.statusCode(200));
        Ensure.that("Content type is JSON", vRec -> vRec.contentType(ContentType.JSON));
        Ensure.that("type is unknown", vRec -> vRec.body("type",is("unknown")));
        Ensure.that("message is id", vRec -> vRec.body("message",is(""+id)));




    }







}
