package rsoni.WebServices;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
;

import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.kisaanApp.App;
import rsoni.modal.AppUser;
import rsoni.modal.UserProfile;


public class NetworkService {

	WebConnection connection = new WebConnection();
	DataParser dataParser = new DataParser();


	public DataResult UserAuth(Task task,AppUser appUser){
		String url = App.ServiceUrl ;
		String json = "";
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		System.out.println("22222");
		if (task == Task.mobile_login){
			url+="auth.php";
			//param.add(new BasicNameValuePair("deviceId", device_id));
			param.add(new BasicNameValuePair("opt", "sign-in"));
			param.add(new BasicNameValuePair("mobile", appUser.mobile));
			param.add(new BasicNameValuePair("pass", appUser.password));
			return getResponce(url,Task.post,task,param);
		} else if (task == Task.mobile_register){
			url+="auth.php";
			//param.add(new BasicNameValuePair("deviceId", device_id));
			param.add(new BasicNameValuePair("opt", "sign-up"));
			param.add(new BasicNameValuePair("mobile", appUser.mobile));
			param.add(new BasicNameValuePair("pass", appUser.password));
			param.add(new BasicNameValuePair("email", appUser.email));
			param.add(new BasicNameValuePair("usercat", ""+appUser.userCategory));
			System.out.println("333333");
			return getResponce(url,Task.post,task,param);
		} else if (task == Task.email_login){
			url+="auth.php";
			//param.add(new BasicNameValuePair("deviceId", device_id));
			param.add(new BasicNameValuePair("deviceType", "android"));
			param.add(new BasicNameValuePair("email", appUser.email));
			param.add(new BasicNameValuePair("password", appUser.password));
			return getResponce(url,Task.post,task,param);
		} else if (task == Task.fb_login){
			url+="accounts/fblogin";
			param.add(new BasicNameValuePair("mobile", appUser.mobile));
			//param.add(new BasicNameValuePair("user_id",appUser.socisal_id ));
			param.add(new BasicNameValuePair("email", appUser.email));

			return getResponce(url,Task.post,task,param);
		}else if (task == Task.g_login){
			url+="accounts/forgot-password";
			//param.add(new BasicNameValuePair("deviceId", device_id));
			param.add(new BasicNameValuePair("deviceType", "android"));
			param.add(new BasicNameValuePair("email", appUser.email));

			return getResponce(url,Task.post,task,param);
		}else if (task == Task.email_register){
			url+="accounts/register";
			//param.add(new BasicNameValuePair("deviceId", device_id));
			param.add(new BasicNameValuePair("deviceType", "android"));
			param.add(new BasicNameValuePair("username", appUser.username));
			param.add(new BasicNameValuePair("email", appUser.email));
			param.add(new BasicNameValuePair("password1", appUser.password));
			param.add(new BasicNameValuePair("password2", appUser.password));

			return getResponce(url,Task.post,task,param);
		}else if (task == Task.forgot_pass){
			url+="accounts/forgot-password";
			//param.add(new BasicNameValuePair("deviceId", device_id));
			param.add(new BasicNameValuePair("deviceType", "android"));
			param.add(new BasicNameValuePair("email", appUser.email));

			return getResponce(url,Task.post,task,param);
		}

		return null;

	}

	/*public DataResult Search(Task task,int area_id, int cuisine_id, String search) {
		String url = App.ServiceUrl;
		String json = "";
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("search", search));

		switch (task) {
		case categories_search:
		case categories_search_json:
			url += "api/categories";
			return getResponce(url, Task.get, task, param);
			//break;
		case cuisines_search:
		case cuisines_search_json:
			url += "api/cuisines";
			return getResponce(url, Task.get, task, param);
			//break;
		case areas_search:
		case areas_search_json:
			url += "api/areas";
			return getResponce(url, Task.get, task, param);
			//break;
		case features_search:
		case features_search_json:
			url += "api/features";
			return getResponce(url, Task.get, task, param);
			//break;
		case filters_search:
		case filters_search_json:
			url += "api/filters";
			return getResponce(url, Task.get, task, param);
			//break;
		case restaurants_search:
		case restaurants_search_json:
			url += "api/restaurants";
			param.add(new BasicNameValuePair("area_id", ""+area_id));
			param.add(new BasicNameValuePair("cuisine_id", ""+cuisine_id));
			return getResponce(url, Task.get, task, param);
			//break;

		default:
			break;
		}


		return null;

	}*/



	public DataResult Profile(Task task,UserProfile userProfile) {
		System.out.println("Squad()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		//param.add(new BasicNameValuePair("acc_id",""+App.account.acc_id));
		param.add(new BasicNameValuePair("deviceId", App.REG_ID));
		param.add(new BasicNameValuePair("deviceType", "android"));
		if (task == Task.get_profile) { //
			// /team/{teamId}/follow
			url+="profile";
			//param.add(new BasicNameValuePair("name", ""+data[0]));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.update_profile){
			url+="auth.php";
			//param.add(new BasicNameValuePair("deviceId", update-profile));
			param.add(new BasicNameValuePair("opt", "update-profile"));
			param.add(new BasicNameValuePair("id", ""+userProfile.id));
			param.add(new BasicNameValuePair("mobile", userProfile.mobile));
			param.add(new BasicNameValuePair("company_name", userProfile.company_name));
			param.add(new BasicNameValuePair("owner_name", userProfile.owner_name));
			param.add(new BasicNameValuePair("address", userProfile.address));
			param.add(new BasicNameValuePair("pincode", ""+userProfile.pincode));
			param.add(new BasicNameValuePair("state_id", ""+userProfile.state_id));
			param.add(new BasicNameValuePair("state_name", userProfile.state_name));
			param.add(new BasicNameValuePair("district_id", ""+userProfile.district_id));
			param.add(new BasicNameValuePair("district_name", userProfile.district_name));
			param.add(new BasicNameValuePair("market_id", ""+userProfile.market_id));
			param.add(new BasicNameValuePair("market_name", userProfile.market_name));
			param.add(new BasicNameValuePair("usersubcat_id", ""+userProfile.usersubcat_id));
			System.out.println("333333");
			return getResponce(url,Task.post,task,param);
		}
		return null;
	}

	private DataResult getResponce(String url,Task url_type ,Task mode, ArrayList<NameValuePair> param) {
		System.out.println("getResponce()...");
		String json = null;
		if(url_type == Task.post) {
			json = connection.getJsonFromUrl2(url, param);
		}else
			json = connection.getJsonFromUrlGet(url, param);
		System.out.println("json : "+json);
		DataResult dataResult = null;
		if (json == null)
			dataResult = connection.invalidResponse();

		switch (mode) {

		case mobile_login:
		case email_login:
		case fb_login:
		case g_login:
			dataResult = dataParser.UserAuth(json, mode);
			break;

		case mobile_register:
		case email_register:
		case fb_register:
		case g_register:
			dataResult = dataParser.UserAuth(json, mode);
			break;

		case get_profile:
		case update_profile:
			dataResult = dataParser.UserAuth(json, mode);
			break;

		case forgot_pass:
			dataResult = dataParser.UserAuth(json, mode);
			break;

		default:
			break;
		}

		return dataResult;
	}




}
