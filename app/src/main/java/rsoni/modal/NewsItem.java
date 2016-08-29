package rsoni.modal;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsItem {

	public int id;
	public String news_title;
	public String news_text;
	public String news_img;
	public String news_date;
	public String link;

	public NewsItem(){}
	public NewsItem(int id, String news_title, String news_text, String news_img,
			String news_date, String link) {
		this.id = id;
		this.news_title = news_title;
		this.news_text = news_text;
		this.news_img = news_img;
		this.news_date = news_date;
		this.link = link;
	}

	public static NewsItem getNewsItem(JSONObject data){		
		
		NewsItem newsItem = new NewsItem(data.optInt("id"),
				data.optString("news_title"),
				data.optString("news_text"),
				data.optString("news_img"),
				data.optString("news_date"),
				data.optString("link"));
		
		return newsItem;
		
	}

	public static NewsItem getNewsItem(Cursor cursor){

		NewsItem newsItem = new NewsItem(cursor.getInt(cursor.getColumnIndex("id")),
				cursor.getString(cursor.getColumnIndex("news_title")),
				cursor.getString(cursor.getColumnIndex("news_text")),
				cursor.getString(cursor.getColumnIndex("news_img")),
				cursor.getString(cursor.getColumnIndex("news_date")),
				cursor.getString(cursor.getColumnIndex("link")));

		return newsItem;

	}


	
	public static ArrayList<NewsItem> getNewsItems(JSONArray jsson_array_news) {
		ArrayList<NewsItem> newsItems = new ArrayList<NewsItem>();
		try {
			JSONObject json_news;
			for (int i = 0; i < jsson_array_news.length(); i++) {
				json_news = (JSONObject) jsson_array_news.get(i);
				System.out.println("json_comment : "+json_news.toString());
				newsItems.add(getNewsItem(json_news));

			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return newsItems;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
