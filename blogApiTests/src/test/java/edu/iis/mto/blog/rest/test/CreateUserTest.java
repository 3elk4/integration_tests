package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class CreateUserTest extends FunctionalTests {
    private static final String USER_API = "/blog/user";
	private static final String IRRELEVANT_NAME = "CREATE_USER_TEST";

    @BeforeAll
	public static void init(){
		JSONObject jsonObj = createJSONUser(IRRELEVANT_NAME, IRRELEVANT_NAME,IRRELEVANT_NAME + "1@domain.com");
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
				.body(jsonObj.toString())
		.when()
				.post(USER_API);
	}

    @Test
    void createUserWithProperDataReturnsCreatedStatus() {
        JSONObject jsonObj = createJSONUser("Tracy", "Jackson", "tracy1@domain.com");
		given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
				.body(jsonObj.toString())
		.expect()
				.statusCode(HttpStatus.SC_CREATED)
				.body("id", Matchers.equalTo(1))
		.when()
				.post(USER_API);
    }

    @Test
    void createUserWithProperDataReturnsConflictStatus() {
		JSONObject jsonObj = createJSONUser(IRRELEVANT_NAME, IRRELEVANT_NAME,IRRELEVANT_NAME + "1@domain.com");
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
