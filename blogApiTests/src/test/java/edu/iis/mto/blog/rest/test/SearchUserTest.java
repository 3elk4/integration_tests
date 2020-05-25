package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SearchUserTest extends FunctionalTests {
	private static final String USER_API = "/blog/user";
	private static final String FIND_USER_API = "/blog/user/find";
	private static final String IRRELEVANT_NAME = "FIND_USER_TEST";

	@BeforeAll
	public static void init(){
		for(int i = 1; i <= 2; ++i){
			JSONObject jsonObj = createJSONUser(IRRELEVANT_NAME, IRRELEVANT_NAME, IRRELEVANT_NAME + i + "@domain.com");
			given()
					.accept(ContentType.JSON)
					.header("Content-Type", "application/json;charset=UTF-8")
					.body(jsonObj.toString())
			.when()
					.post(USER_API);
		}
	}

	@Test
	void searchUserByNameSurnameOrEmail() {
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
				.queryParam("searchString", IRRELEVANT_NAME)
		.expect()
				.statusCode(HttpStatus.SC_OK)
				.body("email", Matchers.hasItems(IRRELEVANT_NAME + "1@domain.com", IRRELEVANT_NAME + "2@domain.com"))
		.when()
				.get(FIND_USER_API);
	}
}
