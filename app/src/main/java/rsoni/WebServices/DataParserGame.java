package rsoni.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pi.Utils.DataResult;
import com.pi.Utils.Task;
import com.pi.entity.AppUser;
import com.pi.entity.Area;
import com.pi.entity.Category;
import com.pi.entity.Cuisines;
import com.pi.entity.Feature;
import com.pi.entity.Filter;
import com.pi.entity.Rating;
import com.pi.entity.Restaurant;
import com.pi.entity.Review;

public class DataParserGame {
	String error_msg = "";

	private JSONObject Start(String json, DataResult result) {
		System.out.println("in Start");
		JSONObject jsonResponse = null;
		if (json == null) {
			result.Status = false;
			result.msg = "Responce is blank";
			return null;
		}
		try {
			json.trim();
			jsonResponse = new JSONObject(json);
			result.Status = jsonResponse.optBoolean("is_processed");
			//System.out.println("jsonResponse.getString(Status) : "
			//		+ jsonResponse.optString("Status"));
			System.out.println("result.Status : "+result.Status);
			result.msg = jsonResponse.optString("msg");
			result.extras = jsonResponse.optJSONArray("non_field_errors");
			result.extras2 = jsonResponse.optJSONArray("field_errors");

		} catch (JSONException e) {
			result.Status = false;
			result.msg = "server error";
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
	private JSONObject StartForSuccess(String json, DataResult result) {
		System.out.println("in Start");
		JSONObject jsonResponse = null;
		if (json == null) {
			result.Status = false;
			result.msg = "Responce is blank";
			return null;
		}
		try {
			json.trim();
			jsonResponse = new JSONObject(json);
			int success = jsonResponse.optInt("success");
			result.Status = (success==1)?true:false;
			System.out.println("success : "+success);
			//System.out.println("jsonResponse.getString(Status) : "
			//		+ jsonResponse.optString("Status"));
			System.out.println("result.Status : "+result.Status);
			result.msg = jsonResponse.optString("msg");

		} catch (JSONException e) {
			result.Status = false;
			result.msg = "server error";
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
	private JSONObject StartForSuccessBoolean(String json, DataResult result) {
		System.out.println("in Start");
		JSONObject jsonResponse = null;
		if (json == null) {
			result.Status = false;
			result.msg = "Responce is blank";
			return null;
		}
		try {
			json.trim();
			jsonResponse = new JSONObject(json);
			boolean success = jsonResponse.optBoolean("success");
			result.Status = success;
			System.out.println("success : "+success);
			//System.out.println("jsonResponse.getString(Status) : "
			//		+ jsonResponse.optString("Status"));
			System.out.println("result.Status : "+result.Status);
			result.msg = jsonResponse.optString("msg");

		} catch (JSONException e) {
			result.Status = false;
			result.msg = "server error";
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
	public DataResult Search(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = StartForSuccessBoolean(json, result);
		if (result.Status ) {
			try {				
				switch (mode) {	
				case categories_search:
					result.Data = Category.getDealsFromJsonAray(response.getJSONArray("categories"))	;
					break;
				case cuisines_search:
					result.Data = Cuisines.getCuisinesFromJsonAray(response.getJSONArray("cuisines"))	;
					break;
				case areas_search:
					result.Data = Area.getAreasFromJsonAray(response.getJSONArray("areas"))	;
					break;
				case features_search:
					result.Data = Feature.getFeaturesFromJsonAray(response.getJSONArray("filters"))	;
					break;
				case filters_search:
					result.Data = Filter.getFiltersFromJsonAray(response.getJSONArray("filters"))	;
					break;
				case restaurants_search:
					result.Data = Restaurant.getRestaurantsFromJsonAray(response.getJSONArray("restaurants"))	;
					break;
				default:
					break;
				}
			} catch (JSONException e) {
				result.Status = false;
				result.msg = "" + e;
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	
	public DataResult SearchJson(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		if(mode == Task.cuisines_search_json || mode == Task.cuisines_search){
			try {
				response = new JSONObject(json);
				result.Status = true;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			response = StartForSuccessBoolean(json, result);
		}
		
		if (result.Status ) {
			try {				
				switch (mode) {	
				case categories_search_json:
					result.Data = response.getJSONArray("categories");
					break;
				case cuisines_search_json:
					result.Data = response.getJSONArray("cuisines");
					break;
				case areas_search_json:
					result.Data = response.getJSONArray("areas");
					break;
				case features_search_json:
					result.Data = response.getJSONArray("filters");
					break;
				case filters_search_json:
					result.Data = response.getJSONArray("filters");
					break;
				case restaurants_search_json:
					result.Data = response.getJSONArray("restaurants");
					break;
				default:
					break;
				}
			} catch (JSONException e) {
				result.Status = false;
				result.msg = "" + e;
				e.printStackTrace();
			}
		}
		return result;
	}

	public DataResult Restaurant(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = StartForSuccessBoolean(json, result);
		if (result.Status) {
			try {
				
				if (mode == Task.restaurant_click ) {
					result.Data = com.pi.entity.Team.getTeamByJsonObject(response.getJSONObject("data"));					
				}else if (mode == Task.restaurant_rate_get ) {
					result.Data = com.pi.entity.Team.getTeamByJsonObject(response.getJSONObject("data"));					
				}else if (mode == Task.restaurant_rate_post ) {
					boolean is_rate_done = response.optBoolean("is_rate_done");
					if(is_rate_done){
						result.Data = Rating.getRatingByJsonObject(response.getJSONObject("rating"));	
					}else{
						result.Status = false;
						result.msg = response.optString("error");
					}				
				}else if (mode == Task.restaurant_review_get ) {
					System.out.println("in restaurant_review_get parsing mode");
					result.Data = Review.getReviewsFromJsonAray(response.optJSONArray("reviews"));					
				}else if (mode == Task.restaurant_review_post ) {
					boolean is_review_done = response.optBoolean("is_review_done");
					if(is_review_done){
						result.Data = Review.getReviewsFromJsonAray(response.optJSONArray("reviews"));	
					}else{
						result.Status = false;
						result.msg = response.optString("error");
					}
				}
				
			} catch (JSONException e) {
				result.Status = false;
				result.msg = "" + e;
				e.printStackTrace();
			}
		}
		//System.out.println("size of list : " + result.Data);
		return result;
	}
	
	
	
	public DataResult UserAuth(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = Start(json, result);
		if (result.Status ) {
			try {
				if (mode == Task.email_login || mode == Task.fb_login || mode == Task.g_login  ) {					
					result.Data = AppUser.getAppUserByJsonObject(response.getJSONObject("user"))	;
					
				} 

			} catch (JSONException e) {
				result.Status = false;
				result.msg = "" + e;
				e.printStackTrace();
			}
		}
		return result;
	}
	public DataResult TopScorer(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = Start(json, result);
		if (result.Status ) {
			try {
				if (mode == Task.topscorers ) {					
					result.Data = com.pi.entity.Team.getTeamByJsonObject(response.getJSONObject("data"));	
				} 

			} catch (JSONException e) {
				result.Status = false;
				result.msg = "" + e;
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	public DataResult Profile(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = Start(json, result);
		if (result.Status ) {
			try {
				if (mode == Task.profile ) {					
					result.Data = response.getJSONObject("data");
				} 

			} catch (JSONException e) {
				result.Status = false;
				result.msg = "" + e;
				e.printStackTrace();
			}
		}
		return result;
	}
	

}