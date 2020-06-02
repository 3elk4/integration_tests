package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static edu.iis.mto.blog.rest.test.FunctionalTestsData.*;
import static io.restassured.RestAssured.given;

public class CreateLikeTest extends FunctionalTests {
	private int POST_OWNER_USER_ID = 1;
	private int CONFIRMED_USER_ID = 2;
	private int CONFIRMED_USER_ID2 = 3;
	private int NEW_USER_ID = 4;
	private int POST_ID = 1;

	@Test
	void addLikeAsAuthorOfPostReturnsBadRequestStatus() {
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
		.expect()
				.statusCode(HttpStatus.SC_BAD_REQUEST)
		.when()
				.post(LIKE_API, POST_OWNER_USER_ID, POST_ID);
	}

	@Test
	void addLikeAsConfirmedUserReturnsOkStatus() {
		String result = given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
		.expect()
				.statusCode(HttpStatus.SC_OK)
		.when()
				.post(LIKE_API, CONFIRMED_USER_ID, POST_ID)
		.asString();

		Assertions.assertEquals("true", result);
	}

	@Test
	void addSecondLikeAsConfirmedUserReturnsOkStatus() {
		String result = given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
		.expect()
				.statusCode(HttpStatus.SC_OK)
		.when()
				.post(LIKE_API, CONFIRMED_USER_ID2, POST_ID)
		.asString();

		Assertions.assertEquals("false", result);
	}

	@Test
	void addLikeAsNonConfirmedUserReturnsBadRequestStatus() {
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
		.expect()
				.statusCode(HttpStatus.SC_BAD_REQUEST)
		.when()
				.post(LIKE_API, NEW_USER_ID, POST_ID);
	}
}
