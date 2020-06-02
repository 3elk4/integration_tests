package edu.iis.mto.blog.rest.test;

import static edu.iis.mto.blog.rest.test.FunctionalTestsData.*;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class CreatePostTest extends FunctionalTests {
	private final String IRRELEVANT_NAME = "CREATE_POST_TEST";
	private final int CONFIRMED_USER_ID = 1;
	private final int NEW_USER_ID = 3;

	@Test
	void createPostByUserWithConfirmedStatusReturnsCreatedStatus() {
		JSONObject jsonObj = createJSONPost(IRRELEVANT_NAME);
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
				.body(jsonObj.toString())
		.expect()
				.statusCode(HttpStatus.SC_CREATED)
				.body("id", Matchers.equalTo(CONFIRMED_USER_ID))
		.when()
				.post(POST_API, CONFIRMED_USER_ID);
	}

	@Test
	void createPostByUserWithNotConfirmedStatusReturnsBadRequestStatus() {
		JSONObject jsonPost = createJSONPost(IRRELEVANT_NAME);
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
				.body(jsonPost.toString())
		.expect()
				.statusCode(HttpStatus.SC_BAD_REQUEST)
		.when()
				.post(POST_API, NEW_USER_ID);
	}
}
