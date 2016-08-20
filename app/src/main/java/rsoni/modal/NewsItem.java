package rsoni.modal;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsItem {

	public int id;
	public String author;
	public String link;
	public String pub_date;
	public String title;
	public String description;
	public int news_id;
	public String thumburl;
	
	public NewsItem(int id, String author, String link, String pub_date,
			String title, String description, int news_id, String thumburl) {
		this.id = id;
		this.author = author;
		this.link = link;
		this.pub_date = pub_date;
		this.title = title;
		this.description = description;
		this.news_id = news_id;
		this.thumburl = thumburl;
	}

	public static NewsItem getNewsItem(JSONObject data){		
		
		NewsItem newsItem = new NewsItem(data.optInt("id"),
				data.optString("author"),				
				data.optString("link"),
				data.optString("pub_date"),
				data.optString("title"),
				data.optString("description"),
				data.optInt("news_id"),
				data.optString("thumburl"));
		
		return newsItem;
		
	}

	public static NewsItem getNewsItem(Cursor cursor){

		NewsItem newsItem = new NewsItem(cursor.getInt(cursor.getColumnIndex("id")),
				cursor.getString(cursor.getColumnIndex("author")),
				cursor.getString(cursor.getColumnIndex("link")),
				cursor.getString(cursor.getColumnIndex("pub_date")),
				cursor.getString(cursor.getColumnIndex("title")),
				cursor.getString(cursor.getColumnIndex("description")),
				cursor.getInt(cursor.getColumnIndex("news_id")),
				cursor.getString(cursor.getColumnIndex("thumburl")));

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
