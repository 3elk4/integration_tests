package edu.iis.mto.blog.rest.test;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;

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

}
