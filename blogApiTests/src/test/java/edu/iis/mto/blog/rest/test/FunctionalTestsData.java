package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class FunctionalTestsData {
	public static final String USER_API = "/blog/user";
	public static final String FIND_USER_API = "/blog/user/find";
	public static final String POST_API = "/blog/user/{userid}/post";
	public static final String LIKE_API = "/blog/user/{userId}/like/{postId}";
	public static final String FIND_POST_API = "/blog/user/{id}/post";

	public static JSONObject createJSONUser(String firstName, String lastName, String email){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("email", email);
		jsonObj.put("firstName", firstName);
		jsonObj.put("lastName", lastName);
		return jsonObj;
	}

	public static JSONObject createJSONPost(String entry){
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("entry", entry);
		return jsonObj;
	}

	public static String createTestUser(JSONObject body, String user_api){
		return given()
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json;charset=UTF-8")
				.body(body.toString())
				.when()
				.post(user_api)
				.asString();
	}
}
