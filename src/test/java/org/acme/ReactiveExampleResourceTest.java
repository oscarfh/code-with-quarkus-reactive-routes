package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public class ReactiveExampleResourceTest {

    private final String PATH = "/reactive-hello";

    @Test
    public void testHelloEndpoint() {
        String username = "myuser";
        given()
                .when().get(PATH + "?name=" + username)
                .then()
                .statusCode(200)
                .body("username", equalTo(username));
    }

    @Test
    public void testHelloEndpointFail() {
        String username = "fail";
        given()
                .when().get(PATH + "?name=" + username)
                .then()
                .statusCode(400);
    }

    @Test
    public void testHelloEndpointFailWith500() {
        given()
                .when().get(PATH)
                .then()
                .statusCode(500);
    }
}