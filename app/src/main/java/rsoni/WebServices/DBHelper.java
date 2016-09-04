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

import rsoni.modal.BuyNode;
import rsoni.modal.Commodity;
import rsoni.modal.CommodityCat;
import rsoni.modal.District;
import rsoni.modal.Market;
import rsoni.modal.NewsItem;
import rsoni.modal.SaleNode;
import rsoni.modal.State;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "MyDBName.db";
	public static final String TABLE_STATE = "state";
	public static final String TABLE_DISTRICT = "district";
	public static final String TABLE_MARKET = "market";
	public static final String TABLE_SALNODE = "salenode";
	public static final String TABLE_BUYNODE = "buynode";
	public static final String TABLE_NEWS = "news";

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
				+ "(id integer primary key, match_id integer, by_name text,comment text,on_date text)");
		db.execSQL("create table district "
				+ "(id integer primary key, match_id integer, by_name text,comment text,on_date text)");
		db.execSQL("create table market "
				+ "(id integer primary key, match_id integer, by_name text,comment text,on_date text)");
		db.execSQL("create table business "
				+ "(id integer primary key, match_id integer, by_name text,comment text,on_date text)");
		db.execSQL("create table buynode "
				+ "(id integer primary key, user_id integer, buy_note text,state_id integer,district_id integer,market_id integer, commodity_cat_id integer,commodity_id integer,business_id integer,usercat integer,note_date integer)");
		db.execSQL("create table salenode "
				+ "(id integer primary key, user_id integer, sale_note text,state_id integer,district_id integer,market_id integer, commodity_cat_id integer,commodity_id integer,business_id integer,usercat integer,note_date integer)");
		db.execSQL("create table news "
				+ "(id integer primary key, news_type text, news_url text,news_title text,news_text text,news_ing text, news_date text)");
		db.execSQL("create table commoditycat "
				+ "(id integer primary key, commodity_cat text, commodity_desc text)");
		db.execSQL("create table commodity "
				+ "(id integer primary key, commodity_cat_id integer, commodity text, commodity_desc text)");
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
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

	public SaleNode saveSaleNodes(SaleNode saleNode){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues insertValues = new ContentValues();
		insertValues.put("id", saleNode.id);
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
				insertValues.put("id", saleNode.id);
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

	public BuyNode saveBuyNodes(BuyNode buyNode){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues insertValues = new ContentValues();
		insertValues.put("id", buyNode.id);
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
				insertValues.put("id", buyNode.id);
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

	private List<State> getStates(){
		List<State> states = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from state", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				states.add(State.getState(cursor));
				cursor.moveToNext();
			}
		}
		return states;
	}

	public List<District> getDistricts(int state_id){
		List<District> districts = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from state", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				districts.add(District.getDistrict(cursor));
				cursor.moveToNext();
			}
		}
		return districts;
	}

	public List<Market> getMarkets(int district_id){
		List<Market> markets = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from state", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				markets.add(Market.getMarket(cursor));
				cursor.moveToNext();
			}
		}
		return markets;
	}

	public List<CommodityCat> getCommodityCats(){
		List<CommodityCat> commodityCats = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from state", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				commodityCats.add(CommodityCat.getCommodityCat(cursor));
				cursor.moveToNext();
			}
		}
		return commodityCats;
	}
	public List<Commodity> getCommodityCats(int cat_id){
		List<Commodity> commodities = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from state", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				commodities.add(Commodity.getCommodity(cursor));
				cursor.moveToNext();
			}
		}
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
				db.insert("states", null, values);
			}
			db.setTransactionSuccessful();
			//add Districts
			List<District> districts = District.getDistricts(context);
			values.clear();
			for (District district : districts) {
				values.put("district_id", district.district_id);
				values.put("district_id", district.district_id);
				values.put("state_id", district.state_id);
				db.insert("districts", null, values);
			}
			db.setTransactionSuccessful();
			//add Markets
			List<Market> markets = Market.getMarkets(context);
			values.clear();
			for (Market  market : markets) {
				values.put("mandi_id", market.mandi_id);
				values.put("mandi_name", market.mandi_name);
				values.put("district", market.district);
				db.insert("markets", null, values);
			}
			db.setTransactionSuccessful();

			//add CommodityCats
			List<CommodityCat> commodityCats = CommodityCat.getCommodityCat(context);
			values.clear();
			for (CommodityCat commodityCat : commodityCats) {
				values.put("id", commodityCat.id);
				values.put("commodity_cat", commodityCat.commodity_cat);
				db.insert("commoditycat", null, values);
			}
			db.setTransactionSuccessful();

			//add Commodity
			List<Commodity> commodities = Commodity.getCommodities(context);
			values.clear();
			for (Commodity  commodity : commodities) {
				values.put("id", commodity.id);
				values.put("commodity", commodity.commodity);
				values.put("commodity_cat_id", commodity.commodity_cat_id);
				db.insert("commodity", null, values);
			}
			db.setTransactionSuccessful();


		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

	}


	public boolean insertContact(String name, String phone, String email,
			String street, String place) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("name", name);
		contentValues.put("phone", phone);
		contentValues.put("email", email);
		contentValues.put("street", street);
		contentValues.put("place", place);

		db.insert("contacts", null, contentValues);
		db.close();
		return true;
	}

	public Cursor getData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from contacts where id=" + id + "",
				null);
		return res;
	}

	public int numberOfRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				TABLE_NEWS);
		return numRows;
	}

	public boolean updateContact(Integer id, String name, String phone,
			String email, String street, String place) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", name);
		contentValues.put("phone", phone);
		contentValues.put("email", email);
		contentValues.put("street", street);
		contentValues.put("place", place);
		db.update("contacts", contentValues, "id = ? ",
				new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deleteContact(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete("contacts", "id = ? ",
				new String[] { Integer.toString(id) });
	}

	public ArrayList getAllCotacts() {
		ArrayList array_list = new ArrayList();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from contacts", null);
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			array_list.add(res.getString(res
					.getColumnIndex(TABLE_NEWS)));
			res.moveToNext();
		}
		return array_list;
	}

}
