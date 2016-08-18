package rsoni.modal;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsItem {

	public int newsitemid;
	public String author;
	public String link;
	public String pubDate;
	public String title;
	public String description;
	public String newsid;
	public String thumburl;
	
	public NewsItem(int newsitemid, String author, String link, String pubDate,
			String title, String description, String newsid, String thumburl) {
		this.newsitemid = newsitemid;
		this.author = author;
		this.link = link;
		this.pubDate = pubDate;
		this.title = title;
		this.description = description;
		this.newsid = newsid;
		this.thumburl = thumburl;
	}

	public static NewsItem getNewsItem(JSONObject data){		
		
		NewsItem newsItem = new NewsItem(data.optInt("newsitemid"),
				data.optString("author"),				
				data.optString("link"),
				data.optString("pubDate"),
				data.optString("title"),
				data.optString("description"),
				data.optString("newsid"),
				data.optString("thumburl"));
		
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
	
	
	public int getNewsitemid() {
		return newsitemid;
	}
	public void setNewsitemid(int newsitemid) {
		this.newsitemid = newsitemid;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNewsid() {
		return newsid;
	}
	public void setNewsid(String newsid) {
		this.newsid = newsid;
	}
	public String getThumburl() {
		return thumburl;
	}
	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
	}
}
