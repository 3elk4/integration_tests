package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;

public class FunctionalTests {

    @BeforeAll
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(8080);
        } else {
            RestAssured.port = Integer.valueOf(port);
        }

        String basePath = System.getProperty("server.base");
        if (basePath == null) {
            basePath = "";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;
    }

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

    public static String createTestPost(JSONObject body, String post_api, int user_id){
        return given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(body.toString())
                .when()
                .post(post_api, user_id)
                .asString();
    }

    public static String createTestLike(String like_api, int user_id, int post_id){
        return given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .when()
                .post(like_api, user_id, post_id)
                .asString();
    }
}
