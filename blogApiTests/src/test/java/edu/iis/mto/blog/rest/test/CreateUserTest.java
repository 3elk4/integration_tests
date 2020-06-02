package edu.iis.mto.blog.rest.test;

import static edu.iis.mto.blog.rest.test.FunctionalTestsData.*;
import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class CreateUserTest extends FunctionalTests {
    @Test
    void createUserWithProperDataReturnsCreatedStatus() {
        JSONObject jsonObj = createJSONUser("Tracy", "Jackson", "tracy1@domain.com");
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
				.body(jsonObj.toString())
		.expect()
				.statusCode(HttpStatus.SC_CREATED)
		.when()
				.post(USER_API);
    }

    @Test
    void createUserWithProperDataReturnsConflictStatus() {
		JSONObject jsonObj = createJSONUser("John", "Steward", "john@domain.com");
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
				.body(jsonObj.toString())
		.expect()
				.statusCode(HttpStatus.SC_CONFLICT)
		.when()
				.post(USER_API);
    }
}
