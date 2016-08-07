package rsoni.modal;

import org.json.JSONObject;

public class AppUser {
	
	public int id = 0;
	public String username = "";
	public String name = "";
	public String email = "";
	public String password = "";
	public String socisal_id = "";
	public String photo ="";
	
	
	public static AppUser getAppUserByJsonObject(JSONObject json_appUser){
		AppUser appUser = new AppUser();
		appUser.id = json_appUser.optInt("id");
		appUser.username = json_appUser.optString("username");
		appUser.email = json_appUser.optString("email");
		appUser.photo = json_appUser.optString("photo");
		return appUser;
	}
	
	public void print(){
		
		System.out.println("user_id : "+id);
		System.out.println("username : "+username);
		System.out.println("email : "+email);
		System.out.println("photo : "+photo);
		
	}

}
