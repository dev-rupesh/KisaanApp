package rsoni.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import rsoni.JustAgriAgro.App;
import rsoni.kisaanApp.R;
import rsoni.modal.CommodityPrice;


public class CommodityPriceListAdaptor extends BaseAdapter {

	//ImageLoader imageLoader;
	List<CommodityPrice> commodityPrices;
	CommodityPrice commodityPrice;
	Context context;
	LayoutInflater layoutInflater;
	int mGMTOffset ;

	// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-mm");

	public CommodityPriceListAdaptor(Context context, List<CommodityPrice> commodityPrices) {
		this.context = context;
		this.commodityPrices = commodityPrices;
		//imageLoader = new ImageLoader(activity);
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TimeZone mTimeZone = TimeZone.getDefault();
		mGMTOffset = mTimeZone.getRawOffset();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commodityPrices.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return commodityPrices.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		commodityPrice = commodityPrices.get(position);

		viewHolder holder;

		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.list_item_commodity_price, null);
			holder = new viewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.setData(commodityPrice);
		return convertView;
	}

	class viewHolder {

		TextView tv_business_type, tv_note_date, tv_note;
		//ImageView iv_news_thumb;

		public viewHolder(View convertView) {
			tv_business_type = (TextView) convertView
					.findViewById(R.id.tv_business_type);
			tv_note_date = (TextView) convertView
					.findViewById(R.id.tv_note_date);
			tv_note = (TextView) convertView.findViewById(R.id.tv_note);
			// iv_news_thumb = (ImageView)
			// convertView.findViewById(R.id.iv_news_thumb);
		}

		void setData(CommodityPrice commodityPrice) {
			tv_business_type.setText(commodityPrice.commodity_name);
			tv_note_date.setText(App.dateFormate_DDMMYYY_Time.format(new Date(commodityPrice.price_date+mGMTOffset)));
			tv_note.setText(commodityPrice.price_note);
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
