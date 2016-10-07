package rsoni.WebServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONObject;

import rsoni.modal.Business;
import rsoni.modal.BuyNode;
import rsoni.modal.Commodity;
import rsoni.modal.CommodityCat;
import rsoni.modal.CommodityPrice;
import rsoni.modal.District;
import rsoni.modal.Market;
import rsoni.modal.NewsItem;
import rsoni.modal.SaleNode;
import rsoni.modal.State;
import rsoni.modal.UserProfile;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "MyDBName.db";
	public static final String TABLE_STATE = "state";
	public static final String TABLE_DISTRICT = "district";
	public static final String TABLE_MARKET = "market";
	public static final String TABLE_SALNODE = "salenode";
	public static final String TABLE_BUYNODE = "buynode";
	public static final String TABLE_NEWS = "news";
	public static final String TABLE_COMMODITYCAT = "commoditycat";
	public static final String TABLE_COMMODITY = "commodity";
	public static final String TABLE_COMMODITY_PRICE = "commodityprice";
	public static final String TABLE_USER_PROFILE = "userprofile";
	public static final String TABLE_BUSINESS = "business";

	private HashMap hp;
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}
	public DBHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table state "
				+ "(id integer primary key, state_id integer, state_name text,country_id integer)");
		db.execSQL("create table district "
				+ "(id integer primary key, district_id integer,state_id integer, district_name text)");
		db.execSQL("create table market "
				+ "(id integer primary key, mandi_id integer, mandi_name text,district_id integer,latitude real,longitude real,address text,city text,contact_no text,email_id text)");
		db.execSQL("create table business "
				+ "(id integer primary key, business_id integer, business text)");
		db.execSQL("create table buynode "
				+ "(id integer primary key AUTOINCREMENT, user_id integer, buy_note text,state_id integer,district_id integer,market_id integer, commodity_cat_id integer,commodity_id integer,business_id integer,usercat integer,note_date integer)");
		db.execSQL("create table salenode "
				+ "(id integer primary key AUTOINCREMENT, user_id integer, sale_note text,state_id integer,district_id integer,market_id integer, commodity_cat_id integer,commodity_id integer,business_id integer,usercat integer,note_date integer)");
		db.execSQL("create table news "
				+ "(id integer primary key, news_type text, news_url text,news_title text,news_text text,news_ing text, news_date text)");
		db.execSQL("create table commoditycat "
				+ "(id integer primary key, commodity_cat text, commodity_desc text)");
		db.execSQL("create table commodity "
				+ "(id integer primary key, commodity_cat_id integer, commodity_name text, commodity_desc text)");
		db.execSQL("create table commodityprice "
				+ "(id integer primary key, user_id integer, price_note text,state_id integer,district_id integer,market_id integer, commodity_cat_id integer,commodity_id integer,commodity_name text,price_date integer)");
		db.execSQL("create table userprofile "
				+ "(id integer primary key, mobile text, company_name text,owner_name text,state_id integer,state_name text,district_id integer,district_name text,market_id integer,market_name text,usersubcat_id integer,address text,pincode integer,business_id integer,business text,email text)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("On DB Update called....");
		List<String> tables = new ArrayList<String>();
		Cursor cursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table';", null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String tableName = cursor.getString(1);
			if (!tableName.equals("android_metadata") && !tableName.equals("sqlite_sequence"))
				tables.add(tableName);
			cursor.moveToNext();
		}
		cursor.close();
		for(String tableName:tables) {
			db.execSQL("DROP TABLE IF EXISTS " + tableName);
		}
		onCreate(db);
	}

	public void truncateDB(){
		SQLiteDatabase db = this.getReadableDatabase();
		db.delete(TABLE_BUYNODE, null, null);
		db.delete(TABLE_SALNODE, null, null);
	}
	
	public int getLastNewsId() {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "";
		sql = "select max(newsitemid) from news";
		Cursor res = db.rawQuery(sql, null);
		res.moveToFirst();		
		int id = res.getInt(0);
		db.close();
		return id;
	}

	public List<NewsItem> getAllNews() {
		List<NewsItem> newsItems = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from contacts", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				newsItems.add(NewsItem.getNewsItem(cursor));
				cursor.moveToNext();
			}
		}
		return newsItems;
	}

	public List<SaleNode> getSaleNodes(){
		List<SaleNode> saleNodes = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from salenode", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				saleNodes.add(SaleNode.getSaleNode(cursor));
				cursor.moveToNext();
			}
		}
		return saleNodes;
	}

	public SaleNode updateSaleNodes(SaleNode saleNode){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues updateValues = new ContentValues();
		updateValues.put("sale_note", saleNode.sale_note);
		updateValues.put("business_id", saleNode.business_id);
		updateValues.put("note_date", saleNode.note_date);
		int count = db.update(TABLE_SALNODE, updateValues, "business_id = ?", new String[]{""+saleNode.business_id});
		if(count==0)
			saleNode = saveSaleNodes(saleNode);
		return saleNode;
	}
	public SaleNode saveSaleNodes(SaleNode saleNode){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues insertValues = new ContentValues();
		//insertValues.put("id", saleNode.id);
		insertValues.put("sale_note", saleNode.sale_note);
		insertValues.put("business_id", saleNode.business_id);
		insertValues.put("note_date", saleNode.note_date);
		db.insert(TABLE_SALNODE, null, insertValues);
		return saleNode;
	}

	public boolean saveSaleNodes(List<SaleNode> saleNodes){
		SQLiteDatabase db = this.getReadableDatabase();
		// Begin the transaction
		db.beginTransaction();
		try{
			ContentValues insertValues = new ContentValues();
			for(SaleNode saleNode : saleNodes){
				insertValues.clear();
				//insertValues.put("id", saleNode.id);
				insertValues.put("sale_note", saleNode.sale_note);
				insertValues.put("business_id", saleNode.business_id);
				insertValues.put("note_date", saleNode.note_date);
				db.insert(TABLE_SALNODE,null,insertValues);
			}
		// Transaction is successful and all the records have been inserted
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.toString();
		}finally{
		//End the transaction
			db.endTransaction();
		}
		return true;
	}

	public List<BuyNode> getBuyNodes(){
		List<BuyNode> buyNodes = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from buynode", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				buyNodes.add(BuyNode.getBuyNode(cursor));
				cursor.moveToNext();
			}
		}
		return buyNodes;
	}

	public BuyNode updateBuyNode(BuyNode buyNode){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues updateValues = new ContentValues();
		updateValues.put("buy_note", buyNode.buy_note);
		updateValues.put("business_id", buyNode.business_id);
		updateValues.put("note_date", buyNode.note_date);
		int count = db.update(TABLE_BUYNODE, updateValues, "business_id = ?", new String[]{""+buyNode.business_id});
		if(count==0)
			buyNode = saveBuyNodes(buyNode);
		return buyNode;
	}

	public BuyNode saveBuyNodes(BuyNode buyNode){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues insertValues = new ContentValues();
		//insertValues.put("id", buyNode.id);
		insertValues.put("buy_note", buyNode.buy_note);
		insertValues.put("business_id", buyNode.business_id);
		insertValues.put("note_date", buyNode.note_date);
		db.insert(TABLE_BUYNODE, null, insertValues);
		return buyNode;
	}

	public boolean saveBuyNodes(List<BuyNode> buyNodes){
		SQLiteDatabase db = this.getReadableDatabase();
		// Begin the transaction
		db.beginTransaction();
		try{
			ContentValues insertValues = new ContentValues();
			for(BuyNode buyNode : buyNodes){
				insertValues.clear();
				//insertValues.put("id", buyNode.id);
				insertValues.put("buy_note", buyNode.buy_note);
				insertValues.put("business_id", buyNode.business_id);
				insertValues.put("note_date", buyNode.note_date);
				db.insert(TABLE_BUYNODE,null,insertValues);
			}
			// Transaction is successful and all the records have been inserted
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.toString();
		}finally{
			//End the transaction
			db.endTransaction();
		}
		return true;
	}

	public List<CommodityPrice> getCommodityPrice(){
		List<CommodityPrice> commodityPrices = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from commodityprice", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				commodityPrices.add(CommodityPrice.getCommodityPrice(cursor));
				cursor.moveToNext();
			}
		}
		return commodityPrices;
	}

	public CommodityPrice saveCommodityPrice(CommodityPrice commodityPrice){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues insertValues = new ContentValues();
		insertValues.put("id", commodityPrice.id);
		insertValues.put("user_id", commodityPrice.user_id);
		insertValues.put("commodity_cat_id", commodityPrice.commodity_cat_id);
		insertValues.put("commodity_id", commodityPrice.commodity_id);
		insertValues.put("commodity_name", commodityPrice.commodity_name);
		insertValues.put("state_id", commodityPrice.state_id);
		insertValues.put("district_id", commodityPrice.district_id);
		insertValues.put("market_id", commodityPrice.market_id);
		insertValues.put("price_note", commodityPrice.price_note);
		insertValues.put("price_date", commodityPrice.price_date);
		db.insert(TABLE_COMMODITY_PRICE, null, insertValues);
		return commodityPrice;
	}
	public boolean saveCommodityPrices(List<CommodityPrice> commodityPrices){
		SQLiteDatabase db = this.getReadableDatabase();
		// Begin the transaction
		db.beginTransaction();
		try{
			ContentValues insertValues = new ContentValues();
			for(CommodityPrice commodityPrice : commodityPrices){
				insertValues.clear();
				insertValues.put("id", commodityPrice.id);
				insertValues.put("user_id", commodityPrice.user_id);
				insertValues.put("commodity_cat_id", commodityPrice.commodity_cat_id);
				insertValues.put("commodity_id", commodityPrice.commodity_id);
				insertValues.put("commodity_name", commodityPrice.commodity_name);
				insertValues.put("state_id", commodityPrice.state_id);
				insertValues.put("district_id", commodityPrice.district_id);
				insertValues.put("market_id", commodityPrice.market_id);
				insertValues.put("price_note", commodityPrice.price_note);
				insertValues.put("price_date", commodityPrice.price_date);
				db.insert(TABLE_COMMODITY_PRICE,null,insertValues);
			}
			// Transaction is successful and all the records have been inserted
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.toString();
		}finally{
			//End the transaction
			db.endTransaction();
		}
		return true;
	}

	public List<NewsItem> getNews(int count){
		List<NewsItem> newsItems = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from news ORDER BY id DESC", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				newsItems.add(NewsItem.getNewsItem(cursor));
				cursor.moveToNext();
			}
		}
		return newsItems;
	}
	public boolean saveNews(List<NewsItem> newsItems){
		SQLiteDatabase db = this.getReadableDatabase();
		// Begin the transaction
		db.beginTransaction();
		try{
			ContentValues insertValues = new ContentValues();
			for(NewsItem newsItem : newsItems){
				insertValues.clear();
				insertValues.put("id", newsItem.id);
				insertValues.put("news_title", newsItem.news_title);
				insertValues.put("news_text", newsItem.news_text);
				insertValues.put("news_img", newsItem.news_img);
				insertValues.put("news_date", newsItem.news_date);
				insertValues.put("link", newsItem.link);
				db.insert(TABLE_NEWS,null,insertValues);
			}
			// Transaction is successful and all the records have been inserted
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.toString();
		}finally{
			//End the transaction
			db.endTransaction();
		}
		return true;
	}

	public UserProfile getUserProfile(int user_id){
		UserProfile userProfile = null;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_USER_PROFILE, null, "id=?", new String[] { ""+user_id }, null, null, null);
		if (cursor.moveToFirst()) {
			userProfile = UserProfile.getUserProfile(cursor);
		}
		return userProfile;
	}

	public UserProfile saveUserProfile(UserProfile userProfile){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues insertValues = new ContentValues();
		 insertValues.put("id",userProfile.id);
		 insertValues.put("mobile",userProfile.mobile);
		 insertValues.put("company_name",userProfile.company_name);
		 insertValues.put("owner_name",userProfile.owner_name);
		 insertValues.put("state_id",userProfile.state_id);
		 insertValues.put("state_name",userProfile.state_name);
		 insertValues.put("district_id",userProfile.district_id);
		 insertValues.put("district_name",userProfile.district_name);
		 insertValues.put("market_id",userProfile.market_id);
		 insertValues.put("market_name",userProfile.market_name);
		 insertValues.put("usersubcat_id",userProfile.usersubcat_id);
		 insertValues.put("address",userProfile.address);
		 insertValues.put("pincode",userProfile.pincode);
		 insertValues.put("business_id",userProfile.business_id);
		 db.insert(TABLE_USER_PROFILE, null, insertValues);
		 return userProfile;
	}

	public List<State> getStates(boolean with_select_option){
		List<State> states = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_STATE, null, null, null, null, null, null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				states.add(State.getState(cursor));
				cursor.moveToNext();
			}
		}
		if(with_select_option) states.add(0,new State(with_select_option));
		return states;
	}


	public List<Business> getAllBusiness(boolean with_select_option) {
		List<Business> states = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_BUSINESS, null, null, null, null, null, null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				states.add(Business.getBusiness(cursor));
				cursor.moveToNext();
			}
		}
		if(with_select_option) states.add(0,new Business(with_select_option));
		return states;
	}

	public List<District> getDistricts(boolean with_select_option,int state_id){
		List<District> districts = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_DISTRICT, null, "state_id=?", new String[] { ""+state_id }, null, null, null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				districts.add(District.getDistrict(cursor));
				cursor.moveToNext();
			}
		}
		if(with_select_option) districts.add(0,new District(with_select_option));
		return districts;
	}

	public List<District> getAllDistricts(boolean with_select_option){
		List<District> districts = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_DISTRICT, null, null, null, null, null, null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				districts.add(District.getDistrict(cursor));
				cursor.moveToNext();
			}
		}
		if(with_select_option) districts.add(0,new District(with_select_option));
		return districts;
	}

	public List<Market> getMarkets(boolean with_select_option,int district_id){
		List<Market> markets = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_MARKET, null, "district_id=?", new String[] { ""+district_id }, null, null, null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				markets.add(Market.getMarket(cursor,false));
				cursor.moveToNext();
			}
		}
		if(with_select_option) markets.add(0,new Market(with_select_option));
		return markets;
	}

	public List<Market> getAllMarkets(){
		List<Market> markets = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_MARKET, null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				markets.add(Market.getMarket(cursor,true));
				cursor.moveToNext();
			}
		}
		return markets;
	}

	public List<CommodityCat> getCommodityCat(boolean with_select_option){
		List<CommodityCat> commodityCats = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_COMMODITYCAT, null, null, null, null, null, null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				commodityCats.add(CommodityCat.getCommodityCat(cursor));
				cursor.moveToNext();
			}
		}
		if(with_select_option) commodityCats.add(0,new CommodityCat(with_select_option));
		return commodityCats;
	}
	public List<Commodity> getCommodity(boolean with_select_option, int cat_id){
		List<Commodity> commodities = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_COMMODITY, null, "commodity_cat_id=?", new String[] { ""+cat_id }, null, null, null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				commodities.add(Commodity.getCommodity(cursor));
				cursor.moveToNext();
			}
		}
		if(with_select_option) commodities.add(0,new Commodity(with_select_option));
		return commodities;
	}
	public List<Commodity> getAllCommodity(boolean with_select_option){
		List<Commodity> commodities = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_COMMODITY, null, null, null, null, null, null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				commodities.add(Commodity.getCommodity(cursor));
				cursor.moveToNext();
			}
		}
		if(with_select_option) commodities.add(0,new Commodity(with_select_option));
		return commodities;
	}

	public void AddMasterDataFromJson(Context context){

		SQLiteDatabase db = this.getWritableDatabase();
		try {

			db.beginTransaction();
			ContentValues values = new ContentValues();
			//add states
			List<State> states = State.getStateList(context);
			values.clear();
			for (State state : states) {
				values.put("state_id", state.state_id);
				values.put("state_name", state.state_name);
				db.insert(TABLE_STATE, null, values);
			}
			//db.setTransactionSuccessful();

			//add Districts
			//db.beginTransaction();
			List<District> districts = District.getDistricts(context);
			values.clear();
			for (District district : districts) {
				values.put("district_id", district.district_id);
				values.put("district_name", district.district_name);
				values.put("state_id", district.state_id);
				db.insert(TABLE_DISTRICT, null, values);
			}
			//db.setTransactionSuccessful();

			//add Markets
			//db.beginTransaction();
			List<Market> markets = Market.getMarkets(context);
			values.clear();
			for (Market  market : markets) {
				System.out.println("Market : "+market.mandi_name+" >> "+market.id);
				values.put("mandi_id", market.id);
				values.put("mandi_name", market.mandi_name);
				values.put("district_id", market.district_id);
				values.put("latitude", market.latitude);
				values.put("longitude", market.longitude);
				values.put("address", market.address);
				values.put("city", market.city);
				values.put("contact_no", market.contact_no);
				values.put("email_id", market.email_id);

				db.insert(TABLE_MARKET, null, values);
			}
			//db.setTransactionSuccessful();

			//add CommodityCats
			//db.beginTransaction();
			List<CommodityCat> commodityCats = CommodityCat.getCommodityCat(context);
			values.clear();
			for (CommodityCat commodityCat : commodityCats) {
				values.put("id", commodityCat.id);
				values.put("commodity_cat", commodityCat.commodity_cat);
				db.insert(TABLE_COMMODITYCAT, null, values);
			}
			//db.setTransactionSuccessful();

			//add Commodity
			//db.beginTransaction();
			List<Commodity> commodities = Commodity.getCommodities(context);
			values.clear();
			for (Commodity  commodity : commodities) {
				values.put("id", commodity.id);
				values.put("commodity_name", commodity.commodity_name);
				values.put("commodity_cat_id", commodity.commodity_cat_id);
				db.insert(TABLE_COMMODITY, null, values);
			}
			db.setTransactionSuccessful();


		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

	}

	public void AddMasterDataFromJson(Context context, JSONObject data){

		SQLiteDatabase db = this.getWritableDatabase();
		try {

			db.beginTransaction();
			ContentValues values = new ContentValues();
			//add states
			List<State> states = State.getStateList(data.optString("states"));
			values.clear();
			for (State state : states) {
				values.put("state_id", state.state_id);
				values.put("state_name", state.state_name);
				db.insert(TABLE_STATE, null, values);
			}
			//add Districts
			List<District> districts = District.getDistricts(data.optString("districts"));
			values.clear();
			for (District district : districts) {
				values.put("district_id", district.district_id);
				values.put("district_name", district.district_name);
				values.put("state_id", district.state_id);
				db.insert(TABLE_DISTRICT, null, values);
			}
			//add Markets
			List<Market> markets = Market.getMarkets(data.optString("markets"));
			values.clear();
			for (Market  market : markets) {
				System.out.println("Market : "+market.mandi_name+" >> "+market.id);
				values.put("id", market.id);
				values.put("mandi_id", market.id);
				values.put("mandi_name", market.mandi_name);
				values.put("district_id", market.district_id);
				values.put("latitude", market.latitude);
				values.put("longitude", market.longitude);
				values.put("address", market.address);
				values.put("city", market.city);
				values.put("contact_no", market.contact_no);
				values.put("email_id", market.email_id);
				db.insert(TABLE_MARKET, null, values);
			}

			//add Business
			List<Business> businesses = Business.getBusiness(data.optString("business"));
			values.clear();
			for (Business business : businesses) {
				values.put("id", business.id);
				values.put("business_id", business.business_id);
				values.put("business", business.business);
				db.insert(TABLE_BUSINESS, null, values);
			}

			//add CommodityCats
			List<CommodityCat> commodityCats = CommodityCat.getCommodityCat(data.optString("commoditycats"));
			values.clear();
			for (CommodityCat commodityCat : commodityCats) {
				values.put("id", commodityCat.id);
				values.put("commodity_cat", commodityCat.commodity_cat);
				db.insert(TABLE_COMMODITYCAT, null, values);
			}
			//add Commodity
			List<Commodity> commodities = Commodity.getCommodities(data.optString("commodities"));
			values.clear();
			for (Commodity  commodity : commodities) {
				values.put("id", commodity.id);
				values.put("commodity_name", commodity.commodity_name);
				values.put("commodity_cat_id", commodity.commodity_cat_id);
				db.insert(TABLE_COMMODITY, null, values);
			}
			db.setTransactionSuccessful();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

	}

	public void UpdateMasterDataFromJson(Context context, JSONObject data){

		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.beginTransaction();
			ContentValues values = new ContentValues();
			//update Markets
			List<Market> markets = Market.getMarkets(data.optString("markets"));
			values.clear();
			db.delete(TABLE_MARKET, null, null);
			for (Market  market : markets) {
				System.out.println("Market : "+market.mandi_name+" >> "+market.id);
				values.put("mandi_id", market.id);
				values.put("mandi_name", market.mandi_name);
				values.put("district_id", market.district_id);
				values.put("latitude", market.latitude);
				values.put("longitude", market.longitude);
				values.put("address", market.address);
				values.put("city", market.city);
				values.put("contact_no", market.contact_no);
				values.put("email_id", market.email_id);
				db.insert(TABLE_MARKET, null, values);
			}
			//update CommodityCats
			List<CommodityCat> commodityCats = CommodityCat.getCommodityCat(data.optString("commoditycats"));
			values.clear();
			db.delete(TABLE_COMMODITYCAT, null, null);
			for (CommodityCat commodityCat : commodityCats) {
				values.put("id", commodityCat.id);
				values.put("commodity_cat", commodityCat.commodity_cat);
				db.insert(TABLE_COMMODITYCAT, null, values);
			}
			//update Commodity
			List<Commodity> commodities = Commodity.getCommodities(data.optString("commodities"));
			values.clear();
			db.delete(TABLE_COMMODITY, null, null);
			for (Commodity  commodity : commodities) {
				values.put("id", commodity.id);
				values.put("commodity_name", commodity.commodity_name);
				values.put("commodity_cat_id", commodity.commodity_cat_id);
				db.insert(TABLE_COMMODITY, null, values);
			}
			db.setTransactionSuccessful();

		} finally {
			db.endTransaction();
		}

	}

	public int numberOfRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				TABLE_NEWS);
		return numRows;
	}



}
