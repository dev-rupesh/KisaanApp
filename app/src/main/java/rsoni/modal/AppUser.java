package rsoni.modal;

import org.json.JSONObject;

public class AppUser {
	
	public int id = 0;
	public String username = "";
	public String email = "";
	public String password = "";
	public int userCategory = 0;
	public UserProfile userProfile = null;
	
	
	public static AppUser getAppUserByJsonObject(JSONObject json_appUser){
		AppUser appUser = new AppUser();
		appUser.id = json_appUser.optInt("id");
		appUser.username = json_appUser.optString("mobile");
		appUser.email = json_appUser.optString("email");
		appUser.userCategory =  json_appUser.optInt("usercat");
		return appUser;
	}
	
	public void print(){
		System.out.println("user_id : "+id);
		System.out.println("username : "+username);
		System.out.println("email : "+email);
		System.out.println("userCategory : "+userCategory);
	}

}
