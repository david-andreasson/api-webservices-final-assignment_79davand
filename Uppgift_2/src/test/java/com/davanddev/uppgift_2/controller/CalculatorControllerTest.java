package com.davanddev.uppgift_2.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculatorControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testAddition() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"a\": 10, \"b\": 5}")
                .when()
                .post("/calculate/add")
                .then()
                .statusCode(200)
                .body("result", equalTo(15));
    }

    @Test
    public void testSubtraction() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"a\": 10, \"b\": 5}")
                .when()
                .post("/calculate/subtract")
                .then()
                .statusCode(200)
                .body("result", equalTo(5));
    }

    @Test
    public void testMultiplication() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"a\": 10, \"b\": 5}")
                .when()
                .post("/calculate/multiply")
                .then()
                .statusCode(200)
                .body("result", equalTo(50));
    }

    @Test
    public void testDivision() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"a\": 10, \"b\": 5}")
                .when()
                .post("/calculate/divide")
                .then()
                .statusCode(200)
                .body("result", equalTo(2));
    }

    @Test
    public void testDivisionByZero() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"a\": 10, \"b\": 0}")
                .when()
                .post("/calculate/divide")
                .then()
                .statusCode(400)
                .body(equalTo("Division by zero is not allowed."));
    }
}
