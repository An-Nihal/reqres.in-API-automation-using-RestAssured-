import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class HTTPRequests {
    int id;

    @Test(priority = 1)
    void getUsers() {
        given()

                .when()
                .get("https://reqres.in/api/users?page=2")

                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .log().all();
    }

    @Test(priority = 2)
    void createUser() {

        HashMap data = new HashMap<>();
        data.put("name", "Nihal");
        data.put("job", "QA");
        id = given()
                .contentType("application/json")
                .body(data)

                .when()
                .post("https://reqres.in/api/users")
                .jsonPath().getInt("id");


    }

    @Test(priority = 3, dependsOnMethods = {"createUser"})
        // dependsonMethods is this method will be executed if the mentioned method/test is passed
    void updateUser() {

        HashMap updata = new HashMap<>();
        updata.put("name", "Nishat");
        updata.put("job", "Dev");

        given()
                .contentType("application/json")
                .body(updata)

                .when()
                .put("https://reqres.in/api/users/" + id)

                .then()
                .statusCode(200)
                .log().all();

    }

    @Test(priority = 4)
    void deleteUser(){
        given()

                .when()
                .delete("https://reqres.in/api/users/" + id)

                .then()
                .statusCode(204)
                .log().all();
    }
}


