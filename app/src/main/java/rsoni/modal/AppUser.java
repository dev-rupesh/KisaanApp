package rsoni.modal;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class AppUser {
	
	public int id = 0;
	public String username = "";
	public String email = "";
	public String password = "";
	public int userCategory = 0;
	public String mobile = "";
	public UserProfile userProfile = null;
	
	
	public static AppUser getAppUserByJsonObject(JSONObject json_appUser){
		AppUser appUser = new AppUser();
		appUser.id = json_appUser.optInt("id");
		appUser.mobile = json_appUser.optString("mobile");
		appUser.email = json_appUser.optString("email");
		appUser.userCategory =  json_appUser.optInt("usercat");
		if(json_appUser.optString("company_name")!=null && !json_appUser.optString("company_name").isEmpty()){
			appUser.userProfile = new UserProfile();
			appUser.userProfile.id = appUser.id;
			appUser.userProfile.mobile = appUser.mobile;
			appUser.userProfile.company_name = json_appUser.optString("company_name");
			appUser.userProfile.owner_name = json_appUser.optString("owner_name");
			appUser.userProfile.address = json_appUser.optString("address");
			appUser.userProfile.pincode = json_appUser.optInt("pincode");
			appUser.userProfile.state_id = json_appUser.optInt("state_id");
			appUser.userProfile.state_name = json_appUser.optString("state_name");
			appUser.userProfile.district_id = json_appUser.optInt("district_id");
			appUser.userProfile.district_name = json_appUser.optString("district_name");
			appUser.userProfile.market_id = json_appUser.optInt("market_id");
			appUser.userProfile.market_name = json_appUser.optString("market_name");
			appUser.userProfile.usersubcat_id = json_appUser.optInt("usersubcat_id");
		}
		return appUser;
	}
	
	public void print(){
		System.out.println("user_id : "+id);
		System.out.println("mobile : "+mobile);
		System.out.println("email : "+email);
		System.out.println("userCategory : "+userCategory);
	}

}
