package by.org;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.codehaus.groovy.transform.StaticTypesTransformation;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class gradle_new{
    @Test
    public void testPage(){
        String url = "https://reqres.in/api/users/2";
        given().contentType(ContentType.JSON)
                .when().get(url)
                .then().log().all().statusCode(200);
    }

    @Test
    public void testPage1(){
        String url = "https://reqres.in/api/users";
        String request = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        given().contentType(ContentType.JSON)
                .body(request)
                .when().post(url)
                .then().statusCode(201).log().all();

    }

    @Test
    public void test2(){
        String url = "https://reqres.in/api/register";
        String request = "{\n" +
                "    \"email\": \"sydney@fife\"\n" +
                "}";
        RestAssured.given().contentType(ContentType.JSON).body(request).log().all()
                .when().post(url)
                .then().log().all().statusCode(400);
    }

    @Test
    public void testGetPublic(){
        String url = "https://api.publicapis.org/entries";
        given().
                queryParam("").
                when().
                get(url).
                then().
                log().body().
                assertThat()
                .body("count", is(1425))
                .body("entries[2].HTTPS", is(true));
    }

    @Test
    public void testPagBugRed(){
        String url = "http://users.bugred.ru/tasks/rest/doregister";
        String name = "dom24";
        String email = "dom156@gmail.com";
        String password = "23344";
        String hobby = "tur";
        String request = String.format("{\"name\":\"%s\",\"email\":\"%s\",\"hobby\":\"%s\",\"password\":\"%s\"}", name, email, hobby, password);
        given()
                .contentType(ContentType.JSON)
                .and()
                .body(request)
                .when()
                .post(url)
                .then().statusCode(200).log().all();
    }

    @Test
    public void testGetRequest(){
        String endPoint = "http://users.bugred.ru/tasks/rest/getuser";
        String email = "dom156@gmail.com";
        String name = "dom24";
        String gender = "";
        String request = String.format("{\"email\":\"%s\"}", email);
        given()
                .contentType(ContentType.JSON)
                .and()
                .body(request)
                .when()
                .get(endPoint)
                .then()
                .statusCode(200)
                .body("email", is(email))
                .body("name", is(name))
                .body("gender", emptyString())
                .body("password", containsString("9beb4f4aa82"))
                .body("birthday", is(0))
                .body("name", equalTo("dom24"))
                .log().all();
    }

    @Test
    public void testGetCoinDesk(){
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        given()
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .log().body()
                .body("bpi.USD.code", is("USD"))
                .body("bpi.GBP.symbol", is("&pound;"))
                .body("bpi.EUR.description", is("Euro"));

    }

    @Test
    public void testGetData(){
        String url = "https://datausa.io/api/data";
        given()
                .queryParam("drilldowns", "Nation")
                .queryParam("measures", "Population")
                .contentType(ContentType.JSON)
                .when()
                .get(url)
                .then()
                .assertThat()
                .statusCode(200)
                .log().body()
                .body("source[0].substitutions", is(empty()))
                .body("source[0].measures[0]", is("Population"))
                .body("source[0].name", is("acs_yg_total_population_5"))
                .body("source[0].annotations.table_id", is("B01003"))
                .body("data[0].ID Nation", is("01000US"));
    }

    @Test
    public void testNationalize(){
        String url = "https://api.nationalize.io";
        given()
                .queryParam("name", "nathaniel")
                .contentType(ContentType.JSON)
                .when().get(url)
                .then()
                .statusCode(200)
                .body("country[0].country_id", is("GH"))
                .body("country[3].probability", is(0.061F))
                .body("name", is("nathaniel"));

    }

    @Test
    public void testRandomuser(){
        String url = "https://randomuser.me/api/";
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .log().body()
                .body("results[0].nat", is("RS"));
    }
    @Test
    public void testPageUniversites(){
        String url="http://universities.hipolabs.com/search?country=United+States";
        given()
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .get(url)
                .then()
                .log().body()
                .body("$[0].name",is("Marywood University"));
    }
}
;