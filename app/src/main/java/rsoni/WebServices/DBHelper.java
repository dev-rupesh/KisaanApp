package rsoni.WebServices;

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
				+ "(newsitemid integer primary key, author text, link text,title text,description text,newsid text, thumburl text, pubdate text)");
	
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

	public List<State> getStates(){
		List<State> states = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from contacts", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				states.add(State.getState(cursor));
				cursor.moveToNext();
			}
		}
		return states;
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
