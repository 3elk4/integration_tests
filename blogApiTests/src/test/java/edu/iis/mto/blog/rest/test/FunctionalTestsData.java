package edu.iis.mto.blog.rest.test;
import org.json.JSONObject;

public class FunctionalTestsData {
	public static final String USER_API = "/blog/user";
	public static final String FIND_USER_API = "/blog/user/find";
	public static final String POST_API = "/blog/user/{userid}/post";
	public static final String LIKE_API = "/blog/user/{userId}/like/{postId}";
	public static final String FIND_POST_API = "/blog/user/{id}/post";

	public static final int POST_OWNER_USER_ID = 1;
	public static final int CONFIRMED_USER_ID = 1;
	public static final int CONFIRMED_USER_ID2 = 2;
	public static final int CONFIRMED_USER_ID3 = 3;
	public static final int NEW_USER_ID = 5;
	public static final int REMOVED_USER_ID = 6;
	public static final int POST_ID = 1;
	public static final String CONFIRMED_USER_NAME = "John";
	public static final String REMOVED_USER_NAME = "Removed";

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
}
