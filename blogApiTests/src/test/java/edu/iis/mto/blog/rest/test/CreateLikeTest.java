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
	private static final String IRRELEVANT_NAME = "CREATE_LIKE_TEST";
	private static int USER_ID;
	private static int POST_ID;

	@BeforeAll
	public static void init(){
		JSONObject jsonObj = createJSONUser(IRRELEVANT_NAME, IRRELEVANT_NAME,IRRELEVANT_NAME + "1@domain.com");
		String id = createTestUser(jsonObj, USER_API);
		JSONObject obj = new JSONObject(id);
		USER_ID = (int)obj.get("id");

		jsonObj = createJSONPost(IRRELEVANT_NAME);
		id = createTestPost(jsonObj, POST_API, USER_ID);
		obj = new JSONObject(id);
		POST_ID = (int)obj.get("id");
	}

	@Test
	void addLikeAsAuthorOfPostReturnsBadRequestStatus() {
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
		.expect()
				.statusCode(HttpStatus.SC_BAD_REQUEST)
		.when()
				.post(LIKE_API, USER_ID, POST_ID);
	}

	@Test
	void addLikeWithProperValuesReturnsOkStatus() {
		JSONObject jsonObj = createJSONUser(IRRELEVANT_NAME, IRRELEVANT_NAME,IRRELEVANT_NAME + "2@domain.com");
		String id = createTestUser(jsonObj, USER_API);
		JSONObject obj = new JSONObject(id);
		int local_id = (int)obj.get("id");

		String result = given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
		.expect()
				.statusCode(HttpStatus.SC_OK)
		.when()
				.post(LIKE_API, local_id, POST_ID)
		.asString();

		Assertions.assertEquals("true", result);
	}

	@Test
	void addLikeWithProperValuesSameUserReturnsOkStatus() {
		JSONObject jsonObj = createJSONUser(IRRELEVANT_NAME, IRRELEVANT_NAME,IRRELEVANT_NAME + "3@domain.com");
		String id = createTestUser(jsonObj, USER_API);
		JSONObject obj = new JSONObject(id);
		int local_id = (int)obj.get("id");
		createTestLike(LIKE_API, local_id, POST_ID);

		String result = given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
		.expect()
				.statusCode(HttpStatus.SC_OK)
		.when()
				.post(LIKE_API, local_id, POST_ID)
		.asString();

		Assertions.assertEquals("false", result);
	}
}
