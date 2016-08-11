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
			param.add(new BasicNameValuePair("mobile", appUser.username));
			param.add(new BasicNameValuePair("pass", appUser.password));
			return getResponce(url,Task.post,task,param);
		} else if (task == Task.mobile_register){
			url+="auth.php";
			//param.add(new BasicNameValuePair("deviceId", device_id));
			param.add(new BasicNameValuePair("opt", "sign-up"));
			param.add(new BasicNameValuePair("mobile", appUser.username));
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
			param.add(new BasicNameValuePair("user_name", appUser.username));
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

	public DataResult Search(Task task,int area_id, int cuisine_id, String search) {
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

	}

	public DataResult Restaurant(Task task,HashMap<String, String> data){
		String url = App.ServiceUrl ;
		String json = "";
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

		if (task == Task.restaurant_click){
			url+="accounts/deals/1/10/";
			System.out.println("url :"+url);
			//param.add(new BasicNameValuePair("deviceType", "android"));
			return getResponce(url,Task.post,task,param);
		} else if (task == Task.restaurant_rate_post){
			url+="api/restaurant/"+data.get("restaurant_id")+"/rate/create";
			param.add(new BasicNameValuePair("rating", data.get("rating")));
			param.add(new BasicNameValuePair("appUser_id", ""+App.getLogedAppUser().id));
			return getResponce(url,Task.post,task,param);
		} else if (task == Task.restaurant_review_post){
			url+="api/restaurant/"+data.get("restaurant_id")+"/reviews/create";
			param.add(new BasicNameValuePair("review_text", data.get("review_text")));
			param.add(new BasicNameValuePair("rating", data.get("rating")));
			param.add(new BasicNameValuePair("appUser_id", ""+App.getLogedAppUser().id));
			return getResponce(url,Task.post,task,param);
		} else if (task == Task.restaurant_rate_get){
			url+="accounts/fblogin";

			return getResponce(url,Task.post,task,param);
		} else if (task == Task.restaurant_review_get){
			url+="api/restaurant/"+data.get("restaurant_id")+"/reviews/list";

			return getResponce(url,Task.get,task,param);
		}

		return null;

	}

	public DataResult TopScorers(Task mode,Object... data) {
		String url = App.ServiceUrl ;
		String json = "";
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("deviceId", App.REG_ID));
		param.add(new BasicNameValuePair("deviceType", "android"));
		if (mode == Task.topscorers) {
			url+="topscorers/"+data[0]+"/"+data[1];
			return getResponce(url,Task.get,mode,param);
		}
		return null;
	}

	public DataResult groups(Task mode,Object... data) {
		String url = App.ServiceUrl ;
		String json = "";
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("deviceId", App.REG_ID));
		param.add(new BasicNameValuePair("deviceType", "android"));
		if (mode == Task.group_list) {
			url+="groups/"+data[0];
			return getResponce(url,Task.get,mode,param);

		} else if (mode == Task.group) {
			url+="group/"+data[0];
			return getResponce(url,Task.get,mode,param);
		}

		return null;

//		json = connection.getJsonFromUrlGet(url, param);
//		//System.out.println("json = " + json);
//		if (json == null)
//			return connection.invalidResponse();
//		else
//			return dataParser.Group(json, mode);

	}

	public DataResult Teams(Task mode,Object... data) {
		String url = App.ServiceUrl ;
		String json = "";
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("deviceId", App.REG_ID));
		param.add(new BasicNameValuePair("deviceType", "android"));
		if (mode == Task.team) {
			// teamdetails/{teamId}
			url+="teamdetails/"+data[0];
			return getResponce(url,Task.get,mode,param);

		} else if (mode == Task.group) {
			url+="group/"+data[0];
			return getResponce(url,Task.get,mode,param);
		}

		return null;

//		json = connection.getJsonFromUrlGet(url, param);
//		//System.out.println("json = " + json);
//		if (json == null)
//			return connection.invalidResponse();
//		else
//			return dataParser.Group(json, mode);

	}

	public DataResult fixtures(Task mode,Object... data) {
		System.out.println("fixtures()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		//param.add(new BasicNameValuePair("acc_id",""+App.account.acc_id));
		param.add(new BasicNameValuePair("deviceId", App.REG_ID));
		param.add(new BasicNameValuePair("deviceType", "android"));
		if (mode == Task.fixture_list_of_tournament) { //
			url+="v2/fixtures/bydate/"+data[0];
			return getResponce(url,Task.get,mode,param);
		}else if (mode == Task.upcomming_fixtures) { //
			url+="v2/fixtures/bydate/upcoming";
			return getResponce(url,Task.get,mode,param);
		}else if (mode == Task.last7days_fixtures) { //
			url+="v2/fixtures/bydate/previous7";
			return getResponce(url,Task.get,mode,param);
		}else if (mode == Task.fixture_list_of_team) { //
			url+="fixtures/"+data[0]+"/byteam/"+data[1];
			//param.add(new BasicNameValuePair("quiz_id", ""+data[0]));
			return getResponce(url,Task.get,mode,param);
		}else if (mode == Task.fixture) { //
			url+="matchdetails/"+data[0];
			//param.add(new BasicNameValuePair("quiz_id", ""+data[0]));
			return getResponce(url,Task.get,mode,param);
		} else if (mode == Task.match_events) { //
			// matchevents/{matchId}
			url+="matchevents/"+data[0];
			//param.add(new BasicNameValuePair("quiz_id", ""+data[0]));
			return getResponce(url,Task.get,mode,param);
		}
		return null;
	}

	public DataResult News(Task task,Object... data) {
		System.out.println("History()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		//param.add(new BasicNameValuePair("acc_id",""+App.account.acc_id));
		param.add(new BasicNameValuePair("deviceId", App.REG_ID));
		param.add(new BasicNameValuePair("deviceType", "android"));
		if (task == Task.new_news_web) { //
			// /team/{teamId}/follow
			url+="newsnewer";
			param.add(new BasicNameValuePair("newsId", ""+data[0]));
			return getResponce(url,Task.get,task,param);
		}else if (task == Task.old_news_web) { //
			// /team/{teamId}/follow
			url+="newsolder";
			param.add(new BasicNameValuePair("newsId", ""+data[0]));
			return getResponce(url,Task.get,task,param);
		}
		return null;
	}
	public DataResult Turnament(Task mode,Object... data) {
		System.out.println("fixtures()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		//param.add(new BasicNameValuePair("acc_id",""+App.account.acc_id));
		param.add(new BasicNameValuePair("deviceId", App.REG_ID));
		param.add(new BasicNameValuePair("deviceType", "android"));
		if (mode == Task.turnament) { //
			url+="tournaments/"+data[0];
			return getResponce(url,Task.get,mode,param);
		}
		return null;
	}

	public DataResult Squad(Task task,Object... data) {
		System.out.println("Squad()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("deviceId", App.REG_ID));
		param.add(new BasicNameValuePair("deviceType", "android"));
		//param.add(new BasicNameValuePair("acc_id",""+App.account.acc_id));
		if (task == Task.players_of_team) { //
			url+="squad/"+data[0]+"/"+data[1];
			return getResponce(url,Task.get,task,param);
		} else if (task == Task.team) { //
			url+="";
			//param.add(new BasicNameValuePair("live_quiz_id", ""+live_quiz_id));
			//param.add(new BasicNameValuePair("user_id", ""+App.userData.id));
			return getResponce(url,Task.get,task,param);
		}
		return null;
	}

	public DataResult Like(Task task,Object... data) {
		System.out.println("Squad()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		//param.add(new BasicNameValuePair("acc_id",""+App.account.acc_id));
		param.add(new BasicNameValuePair("deviceId", App.REG_ID));
		param.add(new BasicNameValuePair("deviceType", "android"));
		if (task == Task.likeMatch) { //
			url+="match/"+data[0]+"/like";
			return getResponce(url,Task.get,task,param);
		}else if (task == Task.unLikeMatch) { //
			url+="match/"+data[0]+"/unlike";
			return getResponce(url,Task.get,task,param);
		}else if (task == Task.likePlayer) { //
			url+="player/"+data[0]+"/like";
			return getResponce(url,Task.get,task,param);
		} else if (task == Task.unLikePlayer) { //
			url+="player/"+data[0]+"/unlike";
			return getResponce(url,Task.get,task,param);
		} else if (task == Task.likeTeam) { //
			url+="team/"+data[0]+"/like";
			return getResponce(url,Task.get,task,param);
		} else if (task == Task.unLikeTeam) { //
			url+="team/"+data[0]+"/unlike";
			return getResponce(url,Task.get,task,param);
		}
		return null;
	}

	public DataResult Follow(Task task,Object... data) {
		System.out.println("Squad()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		//param.add(new BasicNameValuePair("acc_id",""+App.account.acc_id));
		param.add(new BasicNameValuePair("deviceId", App.REG_ID));
		param.add(new BasicNameValuePair("deviceType", "android"));
		if (task == Task.followTeam) { //
			// /team/{teamId}/follow
			url+="team/"+data[0]+"/follow";
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.unfollowTeam) { //
			// /team/{teamId}/unfollow
			url+="team/"+data[0]+"/unfollow";
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.followMatch) { //
			// /match/{matchId}/follow
			url+="match/"+data[0]+"/follow";
			return getResponce(url,Task.post,task,param);
		} else if (task == Task.unfollowMatch) { //
			// /match/{matchId}/unfollow
			url+="match/"+data[0]+"/unfollow";
			return getResponce(url,Task.post,task,param);
		}
		return null;
	}

	public DataResult Comment(Task task,Object... data) {
		System.out.println("Squad()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("deviceId", App.REG_ID));
		param.add(new BasicNameValuePair("deviceType", "android"));
		//param.add(new BasicNameValuePair("acc_id",""+App.account.acc_id));
		if (task == Task.commentOnMatch) { //
			// /match/{matchId}/comments/create
			url+="match/"+data[0]+"/comments/create";
			param.add(new BasicNameValuePair("comment_text", ""+data[1]));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.commentOnTeam) { //
			// /team/{teamId}/comments/create
			url+="team/"+data[0]+"/comments/create";
			param.add(new BasicNameValuePair("comment_text", ""+data[1]));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.viewTeamComments) { //
			// /team/{teamId}/comments/list
			url+="team/"+data[0]+"/comments/list";
			param.add(new BasicNameValuePair("commentId", ""+data[1]));
			return getResponce(url,Task.get,task,param);
		}else if (task == Task.viewMatchComments) { //
			// /match/{matchId}/comments/list
			url+="match/"+data[0]+"/comments/list";
			param.add(new BasicNameValuePair("commentId", ""+data[1]));
			return getResponce(url,Task.get,task,param);
		}else if (task == Task.post_comment) { //
			url+="";
			param.add(new BasicNameValuePair("type", ""+data[0]));
			param.add(new BasicNameValuePair("type_id", ""+data[1]));
			param.add(new BasicNameValuePair("comment", ""+data[2]));
			param.add(new BasicNameValuePair("device_id", ""+data[3]));
			return getResponce(url,Task.get,task,param);
		}
		return null;
	}

	public DataResult Profile(Task task,Object... data) {
		System.out.println("Squad()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		//param.add(new BasicNameValuePair("acc_id",""+App.account.acc_id));
		param.add(new BasicNameValuePair("deviceId", App.REG_ID));
		param.add(new BasicNameValuePair("deviceType", "android"));
		if (task == Task.profile) { //
			// /team/{teamId}/follow
			url+="profile";
			param.add(new BasicNameValuePair("name", ""+data[0]));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.unfollowTeam) { //
			// /team/{teamId}/unfollow
			url+="team/"+data[0]+"/unfollow";
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

		case forgot_pass:
			dataResult = dataParser.UserAuth(json, mode);
			break;

		case cuisines_search:
		case areas_search:
		case categories_search:
		case features_search:
		case filters_search:
		case restaurants_search:
			dataResult = dataParser.Search(json, mode);
			break;

		case restaurant_click:
		case restaurant_rate_get:
		case restaurant_rate_post:
		case restaurant_review_get:
		case restaurant_review_post:
			dataResult = dataParser.Restaurant(json, mode);
			break;

		case cuisines_search_json:
		case areas_search_json:
		case categories_search_json:
		case features_search_json:
		case filters_search_json:
		case restaurants_search_json:
			dataResult = dataParser.SearchJson(json, mode);
			break;

		default:
			break;
		}

		return dataResult;
	}




}
