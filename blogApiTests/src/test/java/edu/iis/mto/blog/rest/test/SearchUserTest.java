package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static edu.iis.mto.blog.rest.test.FunctionalTestsData.*;
import static io.restassured.RestAssured.given;

public class SearchUserTest extends FunctionalTests {
	private static final String IRRELEVANT_NAME = "FIND_USER_TEST";
	private static List<String> emails = new ArrayList<>();

	@BeforeAll
	public static void init(){
		for(int i = 1; i <= 2; ++i){
			emails.add(IRRELEVANT_NAME + i + "@domain.com");
		}
		for (String email : emails) {
			JSONObject jsonObj = createJSONUser(IRRELEVANT_NAME, IRRELEVANT_NAME, email);
			createTestUser(jsonObj, USER_API);
		}
	}

	@Test
	void searchUserByNameSurnameOrEmailReturnsOkStatus() {
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
				.queryParam("searchString", IRRELEVANT_NAME)
		.expect()
				.statusCode(HttpStatus.SC_OK)
				.body("email", Matchers.hasItems(emails.get(0), emails.get(1)))
		.when()
				.get(FIND_USER_API);
	}
}
