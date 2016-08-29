package rsoni.WebServices;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.kisaanApp.App;
import rsoni.modal.AppUser;
import rsoni.modal.BuyNode;
import rsoni.modal.NewsItem;
import rsoni.modal.SaleNode;
import rsoni.modal.SearchFilter;


public class NetworkService {

	WebConnection connection = new WebConnection();
	DataParser dataParser = new DataParser();


	public DataResult UserAuth(Task task,AppUser appUser){
		String url = App.ServiceUrl ;
		String json = "";
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		System.out.println("22222");
		if (task == Task.mobile_login){
			url+="auth/sign-in";
			param.add(new BasicNameValuePair("opt", "sign-in"));
			param.add(new BasicNameValuePair("mobile", appUser.mobile));
			param.add(new BasicNameValuePair("pass", appUser.password));
			return getResponce(url,Task.post,task,param);
		} else if (task == Task.mobile_register){
			url+="auth/sign-up";
			param.add(new BasicNameValuePair("opt", "sign-up"));
			param.add(new BasicNameValuePair("mobile", appUser.mobile));
			param.add(new BasicNameValuePair("pass", appUser.password));
			param.add(new BasicNameValuePair("email", appUser.email));
			param.add(new BasicNameValuePair("usercat", ""+appUser.userCategory));
			System.out.println("333333");
			return getResponce(url,Task.post,task,param);
		} else if (task == Task.email_login){
			url+="auth";
			param.add(new BasicNameValuePair("email", appUser.email));
			param.add(new BasicNameValuePair("password", appUser.password));
			return getResponce(url,Task.post,task,param);
		} else if (task == Task.fb_login){
			url+="accounts";
			param.add(new BasicNameValuePair("email", appUser.email));

			return getResponce(url,Task.post,task,param);
		}else if (task == Task.g_login){
			url+="accounts/forgot-password";
			param.add(new BasicNameValuePair("email", appUser.email));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.email_register){
			url+="accounts/register";
			param.add(new BasicNameValuePair("username", appUser.username));
			param.add(new BasicNameValuePair("email", appUser.email));
			param.add(new BasicNameValuePair("password1", appUser.password));
			param.add(new BasicNameValuePair("password2", appUser.password));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.forgot_pass){
			url+="accounts/forgot-password";
			param.add(new BasicNameValuePair("email", appUser.email));
			return getResponce(url,Task.post,task,param);
		}

		return null;

	}

	public DataResult Search(Task task, SearchFilter search) {
		String url = App.ServiceUrl;
		String json = "";
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		switch (task) {
		case buyer_search:
			url += "api/search";
			param.add(new BasicNameValuePair("user_id", ""+App.appUser.id));
			param.add(new BasicNameValuePair("for", search.searchFor));
			param.add(new BasicNameValuePair("business_id", ""+search.business_id));
			param.add(new BasicNameValuePair("state_id", ""+search.state_id));
			param.add(new BasicNameValuePair("district_id", ""+search.district_id));
			return getResponce(url, Task.post, task, param);
			//break;
		case seller_search:
			url += "api/search";
			param.add(new BasicNameValuePair("user_id", ""+App.appUser.id));
			param.add(new BasicNameValuePair("for", search.searchFor));
			param.add(new BasicNameValuePair("business_id", ""+search.business_id));
			param.add(new BasicNameValuePair("state_id", ""+search.state_id));
			param.add(new BasicNameValuePair("district_id", ""+search.district_id));
			return getResponce(url, Task.post, task, param);
			//break;
		case crop_search:
			url += "api/search";
			param.add(new BasicNameValuePair("user_id", ""+App.appUser.id));
			param.add(new BasicNameValuePair("for", search.searchFor));
			param.add(new BasicNameValuePair("business_id", ""+search.business_id));
			param.add(new BasicNameValuePair("state_id", ""+search.state_id));
			param.add(new BasicNameValuePair("district_id", ""+search.district_id));
			return getResponce(url, Task.post, task, param);
			//break;

		default:
			break;
		}

	    return null;

	}

	public DataResult SaleNode(Task task,SaleNode saleNode) {
		System.out.println("SaleNode()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		if (task == Task.get_sale_node) { //
			url+="sale/get-sale-node";
			param.add(new BasicNameValuePair("opt", "get-sale-node"));
			param.add(new BasicNameValuePair("id", ""+saleNode.id));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.list_sale_node){
			url+="sale/list-sale-node";
			param.add(new BasicNameValuePair("opt", "list-sale-node"));
			param.add(new BasicNameValuePair("user_id", ""+App.appUser.id));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.add_sale_node){
			url+="sale/add-sale-node";
			param.add(new BasicNameValuePair("opt", "add-sale-node"));
			param.add(new BasicNameValuePair("user_id", ""+saleNode.user_id));
			param.add(new BasicNameValuePair("state_id", ""+saleNode.state_id));
			param.add(new BasicNameValuePair("district_id", ""+saleNode.district_id));
			param.add(new BasicNameValuePair("market_id", ""+saleNode.market_id));
			param.add(new BasicNameValuePair("usercat", ""+saleNode.usercat));
			param.add(new BasicNameValuePair("commodity_cat_id", ""+saleNode.commodity_cat_id));
			param.add(new BasicNameValuePair("commodity_id", ""+saleNode.commodity_id));
			param.add(new BasicNameValuePair("business_id", ""+saleNode.business_id));
			param.add(new BasicNameValuePair("sale_note", saleNode.sale_note));

			return getResponce(url,Task.post,task,param);
		}else if (task == Task.update_sale_node){
			url+="sale/update-sale-node";
			param.add(new BasicNameValuePair("opt", "update-sale-node"));
			param.add(new BasicNameValuePair("id", ""+saleNode.id));
			param.add(new BasicNameValuePair("note_date", ""+saleNode.note_date));
			param.add(new BasicNameValuePair("sale_note", saleNode.sale_note));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.delete_sale_node){
			url+="sale/delete-sale-node";
			param.add(new BasicNameValuePair("opt", "delete-sale-node"));
			param.add(new BasicNameValuePair("id", ""+saleNode.id));
			return getResponce(url,Task.post,task,param);
		}
		return null;
	}

	public DataResult BuyNode(Task task,BuyNode buyNode) {
		System.out.println("BuyNode()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		if (task == Task.get_buy_node) { //
			url+="buy/get-buy-node";
			param.add(new BasicNameValuePair("opt", "get-buy-node"));
			param.add(new BasicNameValuePair("id", ""+App.appUser.id));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.list_buy_node){
			url+="buy/list-buy-node";
			param.add(new BasicNameValuePair("opt", "list-buy-node"));
			param.add(new BasicNameValuePair("user_id", ""+App.appUser.id));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.add_buy_node){
			url+="buy/add-buy-node";
			param.add(new BasicNameValuePair("opt", "add-buy-node"));
			param.add(new BasicNameValuePair("user_id", ""+App.appUser.id));
			param.add(new BasicNameValuePair("state_id", ""+buyNode.state_id));
			param.add(new BasicNameValuePair("district_id", ""+buyNode.district_id));
			param.add(new BasicNameValuePair("market_id", ""+buyNode.market_id));
			param.add(new BasicNameValuePair("usercat", ""+buyNode.usercat));
			param.add(new BasicNameValuePair("commodity_cat_id", ""+buyNode.commodity_cat_id));
			param.add(new BasicNameValuePair("commodity_id", ""+buyNode.commodity_id));
			param.add(new BasicNameValuePair("note_date", ""+buyNode.note_date));
			param.add(new BasicNameValuePair("business_id", ""+buyNode.business_id));
			param.add(new BasicNameValuePair("buy_note", buyNode.buy_note));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.update_buy_node){
			url+="buy/update-buy-node";
			param.add(new BasicNameValuePair("opt", "update-buy-node"));
			param.add(new BasicNameValuePair("id", ""+App.appUser.id));
			param.add(new BasicNameValuePair("note_date", ""+buyNode.note_date));
			param.add(new BasicNameValuePair("buy_note", buyNode.buy_note));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.delete_buy_node){
			url+="buy/delete-buy-node";
			param.add(new BasicNameValuePair("opt", "delete-buy-node"));
			param.add(new BasicNameValuePair("id", ""+buyNode.id));
			return getResponce(url,Task.post,task,param);
		}
		return null;
	}

	public DataResult News(Task task,NewsItem newsItem) {
		System.out.println("News()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		if (task == Task.get_news_details) { //
			url+="news/get-news-details";
			//param.add(new BasicNameValuePair("opt", "get-news-details"));
			param.add(new BasicNameValuePair("id", ""+newsItem.id));
			return getResponce(url,Task.get,task,param);
		}else if (task == Task.news_list_web){
			url+="news/news_list";
			//param.add(new BasicNameValuePair("opt", "news_list_sort"));
			param.add(new BasicNameValuePair("latest_id", ""+newsItem.id));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.add_news){
			url+="news.php";
			param.add(new BasicNameValuePair("opt", "add-news"));
			//param.add(new BasicNameValuePair("news_id", ""+newsItem.news_id));
			//param.add(new BasicNameValuePair("state_id", ""+newsItem.author));
			//param.add(new BasicNameValuePair("district_id", ""+newsItem.description));
			//param.add(new BasicNameValuePair("market_id", ""+newsItem.pub_date));
			//param.add(new BasicNameValuePair("usercat", ""+newsItem.link));
			//param.add(new BasicNameValuePair("usersubcat_id", ""+newsItem.thumburl));
			//param.add(new BasicNameValuePair("commodity_cat_id", ""+newsItem.title));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.delete_news){
			url+="news/delete-news";
			param.add(new BasicNameValuePair("opt", "delete-news"));
			param.add(new BasicNameValuePair("id", ""+newsItem.id));
			return getResponce(url,Task.post,task,param);
		}
		return null;
	}


	public DataResult Profile(Task task,AppUser appUser) {
		System.out.println("Profile()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		if (task == Task.get_profile) { //
			url+="profile";
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.update_profile){
			url+="auth/update-profile";
			param.add(new BasicNameValuePair("opt", "update-profile"));
			param.add(new BasicNameValuePair("id", ""+appUser.id));
			param.add(new BasicNameValuePair("mobile", appUser.mobile));
			param.add(new BasicNameValuePair("company_name", appUser.userProfile.company_name));
			param.add(new BasicNameValuePair("owner_name", appUser.userProfile.owner_name));
			param.add(new BasicNameValuePair("address", appUser.userProfile.address));
			param.add(new BasicNameValuePair("pincode", ""+appUser.userProfile.pincode));
			param.add(new BasicNameValuePair("state_id", ""+appUser.userProfile.state_id));
			param.add(new BasicNameValuePair("state_name", appUser.userProfile.state_name));
			param.add(new BasicNameValuePair("district_id", ""+appUser.userProfile.district_id));
			param.add(new BasicNameValuePair("district_name", appUser.userProfile.district_name));
			param.add(new BasicNameValuePair("market_id", ""+appUser.userProfile.market_id));
			param.add(new BasicNameValuePair("market_name", appUser.userProfile.market_name));
			param.add(new BasicNameValuePair("usersubcat_id", ""+appUser.userProfile.usersubcat_id));
			param.add(new BasicNameValuePair("business_id", ""+appUser.userProfile.business_id));
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

			case get_sale_node:
			case list_sale_node:
			case add_sale_node:
			case update_sale_node:
			case delete_sale_node:
				dataResult = dataParser.SaleNode(json, mode);
				break;

			case get_buy_node:
			case list_buy_node:
			case add_buy_node:
			case update_buy_node:
			case delete_buy_node:
				dataResult = dataParser.BuyNode(json, mode);
				break;

			case get_news_details:
			case news_list_sort:
			case news_list_web:
			case add_news:
			case delete_news:
				dataResult = dataParser.News(json, mode);
				break;

			case buyer_or_seller_search:
			case buyer_search:
			case seller_search:
			case  crop_search:
				dataResult = dataParser.Search(json, mode);
				break;

		default:
			break;
		}

		return dataResult;
	}




}
