package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static edu.iis.mto.blog.rest.test.FunctionalTestsData.*;
import static io.restassured.RestAssured.given;

public class SearchPostTest extends FunctionalTests {
	@Test
	void searchConfirmedUserPostWithOneLikeReturnsOkStatus() {
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
		.expect()
				.statusCode(HttpStatus.SC_OK)
				.body("likesCount", Matchers.hasItem(1))
		.when()
				.get(FIND_POST_API, CONFIRMED_USER_ID);
	}

	@Test
	void searchRemovedUserPostReturnsBadRequestStatus() {
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
		.expect()
				.statusCode(HttpStatus.SC_BAD_REQUEST)
		.when()
				.get(FIND_POST_API, REMOVED_USER_ID);
	}
}
