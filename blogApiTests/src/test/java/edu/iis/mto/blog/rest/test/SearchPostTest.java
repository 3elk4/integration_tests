package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static edu.iis.mto.blog.rest.test.FunctionalTestsData.*;
import static io.restassured.RestAssured.given;

public class SearchPostTest extends FunctionalTests {
	private static final String IRRELEVANT_NAME = "SEARCH_POST_TEST";
	private static int USER_ID;
	private static int POST_ID;

	@BeforeAll
	public static void init(){
		JSONObject jsonObj = null;
		String id;
		for(int i = 1; i <= 2; ++i){
			jsonObj = createJSONUser(IRRELEVANT_NAME, IRRELEVANT_NAME,IRRELEVANT_NAME + i + "@domain.com");
			id = createTestUser(jsonObj, USER_API);
			jsonObj = new JSONObject(id);
			USER_ID = (int)jsonObj.get("id");
		}

		jsonObj = createJSONPost(IRRELEVANT_NAME);
		id = createTestPost(jsonObj, POST_API, USER_ID);
		jsonObj = new JSONObject(id);
		POST_ID = (int)jsonObj.get("id");

		createTestLike(LIKE_API, USER_ID-1, POST_ID);
	}

	@Test
	void searchUserPostWithOneLikeReturnsOkStatus() {
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
		.expect()
				.statusCode(HttpStatus.SC_OK)
				.body("likesCount", Matchers.hasItem(1))
		.when()
				.get(FIND_POST_API, USER_ID);
	}
}
