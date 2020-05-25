package edu.iis.mto.blog.rest.test;

import static edu.iis.mto.blog.rest.test.FunctionalTestsData.*;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CreatePostTest extends FunctionalTests {
	private static final String IRRELEVANT_NAME = "CREATE_POST_TEST";
	private static int USER_ID;

	@BeforeAll
	public static void init(){
		JSONObject jsonObj = createJSONUser(IRRELEVANT_NAME, IRRELEVANT_NAME,IRRELEVANT_NAME + "1@domain.com");
		String id = createTestUser(jsonObj, USER_API);
		JSONObject obj = new JSONObject(id);
		USER_ID = (int)obj.get("id");
	}

	@Test
	void createPostWithProperDataReturnsCreatedStatus() {
		JSONObject jsonObj = createJSONPost(IRRELEVANT_NAME);
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
				.body(jsonObj.toString())
		.expect()
				.statusCode(HttpStatus.SC_CREATED)
				.body("id", Matchers.equalTo(USER_ID + 1))
		.when()
				.post(POST_API,USER_ID);
	}
}
