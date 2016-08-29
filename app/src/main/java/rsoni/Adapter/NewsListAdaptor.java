package rsoni.Adapter;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import rsoni.kisaanApp.R;
import rsoni.modal.NewsItem;


public class NewsListAdaptor extends BaseAdapter {

	//ImageLoader imageLoader;
	List<NewsItem> newsItems;
	NewsItem newsItem;
	Context context;
	LayoutInflater layoutInflater;

	// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-mm");

	public NewsListAdaptor(Context context, List<NewsItem> newsItems) {
		this.context = context;
		this.newsItems = newsItems;
		//imageLoader = new ImageLoader(activity);
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newsItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return newsItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		newsItem = newsItems.get(position);

		viewHolder holder;

		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.list_item_news, null);
			holder = new viewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.setData(newsItem);
		return convertView;
	}

	class viewHolder {

		TextView tv_news_title, tv_news_text, tv_news_date;
		ImageView iv_news_img;

		public viewHolder(View convertView) {
			tv_news_title = (TextView) convertView.findViewById(R.id.tv_news_title);
			tv_news_text = (TextView) convertView.findViewById(R.id.tv_news_text);
			iv_news_img = (ImageView) convertView.findViewById(R.id.iv_news_img);
			// iv_news_thumb = (ImageView)
			// convertView.findViewById(R.id.iv_news_thumb);
		}

		void setData(NewsItem newsItem) {
			tv_news_title.setText(newsItem.news_title);
			tv_news_text.setText(newsItem.news_text);
			// tv_new_date.setText(newsItem.pubDate);
			// try {
			// tv_new_date.setText(App.MonthDateYearTime.format(formatter.parse(newsItem.pubDate)));
			// } catch (ParseException e) {
			//
			// e.printStackTrace();
			// }
			// System.out.println("newsItem.thumburl : "+newsItem.thumburl);
			// iv_news_thumb.setVisibility(View.GONE);
			/*
			 * if(newsItem.thumburl!=null &&
			 * newsItem.thumburl.startsWith("http") ){
			 * iv_news_thumb.setVisibility(View.VISIBLE);
			 * imageLoader.DisplayImage(newsItem.thumburl, iv_news_thumb); }
			 */
		}

	}

}
