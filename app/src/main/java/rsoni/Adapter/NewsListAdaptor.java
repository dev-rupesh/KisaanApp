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

		TextView tv_historytitle, tv_historydesc, tv_new_date;
		ImageView iv_news_thumb;

		public viewHolder(View convertView) {
			tv_historytitle = (TextView) convertView
					.findViewById(R.id.tv_historytitle);
			tv_historydesc = (TextView) convertView
					.findViewById(R.id.tv_historydesc);
			//tv_new_date = (TextView) convertView.findViewById(R.id.tv_new_date);
			// iv_news_thumb = (ImageView)
			// convertView.findViewById(R.id.iv_news_thumb);
		}

		void setData(NewsItem newsItem) {
			tv_historytitle.setText(newsItem.title);
			tv_historydesc.setText(newsItem.description);
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