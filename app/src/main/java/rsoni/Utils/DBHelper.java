package rsoni.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.pi.entity.Comment;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "MyDBName.db";
	public static final String CONTACTS_TABLE_NAME = "contacts";
	public static final String CONTACTS_COLUMN_ID = "id";
	public static final String CONTACTS_COLUMN_NAME = "name";
	public static final String CONTACTS_COLUMN_EMAIL = "email";
	public static final String CONTACTS_COLUMN_STREET = "street";
	public static final String CONTACTS_COLUMN_CITY = "place";
	public static final String CONTACTS_COLUMN_PHONE = "phone";

	private HashMap hp;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table contacts "
				+ "(id integer primary key, name text,phone text,email text, street text,place text)");
		db.execSQL("create table team_comments "
				+ "(id integer primary key, team_id integer, by_name text,comment text,on_date text)");
		db.execSQL("create table match_comments "
				+ "(id integer primary key, match_id integer, by_name text,comment text,on_date text)");
		db.execSQL("create table news "
				+ "(newsitemid integer primary key, author text, link text,title text,description text,newsid text, thumburl text, pubdate text)");
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS contacts");
		db.execSQL("DROP TABLE IF EXISTS team_comments");
		db.execSQL("DROP TABLE IF EXISTS match_comments");
		db.execSQL("DROP TABLE IF EXISTS news");
		onCreate(db);
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
				CONTACTS_TABLE_NAME);
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
					.getColumnIndex(CONTACTS_COLUMN_NAME)));
			res.moveToNext();
		}
		return array_list;
	}

}
