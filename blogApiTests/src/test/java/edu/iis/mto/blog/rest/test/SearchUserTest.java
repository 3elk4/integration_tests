package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static edu.iis.mto.blog.rest.test.FunctionalTestsData.*;
import static io.restassured.RestAssured.given;

public class SearchUserTest extends FunctionalTests {
	@Test
	void searchExistingUserByNameSurnameOrEmailReturnsOkStatus() {
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
				.queryParam("searchString", CONFIRMED_USER_NAME)
		.expect()
				.statusCode(HttpStatus.SC_OK)
				.body("email", Matchers.hasItems("john@domain.com"))
		.when()
				.get(FIND_USER_API);
	}

	@Test
	void searchNonExistingUserByNameSurnameOrEmailReturnsOkStatusAndEmptyEmailList() {
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
				.queryParam("searchString", REMOVED_USER_NAME)
		.expect()
				.statusCode(HttpStatus.SC_OK)
				.body("email", Matchers.hasItems())
		.when()
				.get(FIND_USER_API);
	}
}
