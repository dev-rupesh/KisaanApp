package rsoni.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import rsoni.JustAgriAgro.App;
import rsoni.JustAgriAgro.R;
import rsoni.modal.Business;
import rsoni.modal.BuyNode;


public class BuyListAdaptor extends BaseAdapter {

	//ImageLoader imageLoader;
	List<BuyNode> buyNodes;
	BuyNode buyNode;
	Context context;
	LayoutInflater layoutInflater;
	int mGMTOffset ;
	private Map<Integer, Business> businessMap;

	// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-mm");

	public BuyListAdaptor(Context context, List<BuyNode> buyNodes) {
		this.context = context;
		this.buyNodes = buyNodes;
		//imageLoader = new ImageLoader(activity);
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TimeZone mTimeZone = TimeZone.getDefault();
		mGMTOffset = mTimeZone.getRawOffset();
		System.out.println("mGMTOffset : "+mGMTOffset);
		businessMap = Business.getBusinessMap(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return buyNodes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return buyNodes.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		buyNode = buyNodes.get(position);

		viewHolder holder;

		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.list_item_sale, null);
			holder = new viewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.setData(buyNode);
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

		void setData(BuyNode buyNode) {

			System.out.println("businessMap : "+App.gson.toJson(businessMap));
			System.out.println("business_id : "+buyNode.business_id);

			if(businessMap.get(buyNode.business_id) !=null)
				tv_business_type.setText(businessMap.get(buyNode.business_id).business);
			else
				tv_business_type.setText("NA");

			//tv_business_type.setText(""+buyNode.business_id);
			tv_note_date.setText(App.dateFormate_DDMMYYY_Time.format(new Date(buyNode.note_date)));
			tv_note.setText(buyNode.buy_note);
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
