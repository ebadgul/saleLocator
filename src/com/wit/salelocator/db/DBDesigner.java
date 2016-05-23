package com.wit.salelocator.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wit.salelocator.shopuser.ShopUser;

public class DBDesigner {
	private static final String DATABASE_NAME = "salelocations.db";
	private static final int DATABASE_VERSION = 1;
//	private static final int NAME_COLUMN = 1;
	
	/*
	 * public static final String TABLE_DONATION = "table_donation"; public
	 * static final String COLUMN_ID = "id"; public static final String
	 * COLUMN_AMOUNT = "amount"; public static final String COLUMN_METHOD =
	 * "method";
	 */

	static final String DATABASE_CREATE = "create table " + "LOGIN" + "( "
			+ "ID" + " integer primary key autoincrement,"
			+ "USERNAME  text,PASSWORD text, SHOPNAME text, SHOPADDRESS text, LATITUDE text, LONGITUDE text, SALEON text); ";

	// SQLiteDatabase sqldb;
	public SQLiteDatabase db;
	private final Context context;
	private DatabaseHelper dbHelper;

	public DBDesigner(Context _context) {
		context = _context;
		dbHelper = new DatabaseHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public DBDesigner open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	public SQLiteDatabase getDatabaseInstance() {
		return db;
	}

	
	
	
	public List<ShopUser> getAll(){
		List<ShopUser> users = new ArrayList<ShopUser>();
		Cursor cursor = db.rawQuery("select * from LOGIN where SALEON = 'Y';", null);//where SALEON = 'Y'
		
		//int c = cursor.getCount();
		
		if(cursor.moveToFirst()){
		
		while (!cursor.isAfterLast()) {
			ShopUser u = toUser(cursor);
			users.add(u);
			cursor.moveToNext();
		}
		cursor.close();
		}
		return users;
	}
	
	
	
	
	
	
	//entering shop user details in the registration 1st screen...
	public void insertEntry(String userName, String password, String shopName, String shopAddress) {
		ContentValues cv = new ContentValues();
		cv.put("USERNAME", userName);
		cv.put("SHOPNAME", shopName);
		cv.put("SHOPADDRESS", shopAddress);
		cv.put("PASSWORD", password);

		db.insert("LOGIN", null, cv);
	}
//////// testing /////
	
public void insertNewUser(ShopUser user){
	
	
	
	ContentValues cv = new ContentValues();
	cv.put("USERNAME", user.shopUserName);
	cv.put("SHOPNAME", user.shopName);
	cv.put("SHOPADDRESS", user.shopAddress);
	cv.put("PASSWORD", user.shopPassword);
	cv.put("LATITUDE", user.latitude);
	cv.put("LONGITUDE", user.longitude);
	cv.put("SALEON", "N");

	db.insert("LOGIN", null, cv);
}
///*****************************************///
	

///*****************************************///
	
//////////////////////////////	
	public int deleteEntry(String userName) {
		String where = "USERNAME=?";
		int numberOfEntriesDeleted = db.delete("LOGIN", where,
				new String[] { userName });
		return numberOfEntriesDeleted;
	}

	public String getSingleEntry(String userName) {
		Cursor cursor = db.query("LOGIN", null, "USERNAME=?",
				new String[] { userName }, null, null, null);
		Log.v("from lingleEntry", ""+cursor.getCount());
		if (cursor.getCount() < 1) {
			cursor.close();
			return "NOT EXIST";
		}
		cursor.moveToFirst();
		String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
		cursor.close();
		return password;

	}
	
	//checking if the username is already exited....
	public boolean userNameTaken(String userName) {
		Cursor cursor = db.query("LOGIN", null, "USERNAME=?",
				new String[] { userName }, null, null, null);
		if (cursor.getCount() > 0) {
			cursor.close();
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	public ShopUser getUser(String userName){
		Cursor cursor = db.rawQuery("Select * from LOGIN where USERNAME = '"+userName+"';", null);
		cursor.moveToFirst();
		ShopUser user = toUser(cursor);
		cursor.close();		
		return user;
	}

	public ShopUser toUser(Cursor cursor){
		
		ShopUser user = new ShopUser(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(7));
		
//		Double.parseDouble(cursor.getString(5))
//		Double.parseDouble(cursor.getString(6))
		
		user.latitude =Double.parseDouble(cursor.getString(5));
		user.longitude =Double.parseDouble(cursor.getString(6));
		Log.v("lat and lang", ""+user.latitude+"---"+user.longitude);
		return user;
	}
	
	public void setSale(String usrName, String sale){
		ContentValues updateValues = new ContentValues();
		updateValues.put("SALEON", sale);
		//updateValues.put("LONGITUDE", "Gul");
		//db.insert("LOGIN", null, updateValues);
		String where = "USERNAME = '"+usrName+"'";
	//db.rawQuery("update LOGIN set SALEON ='"+sale+"' where USERNAME = '"+usrName+"'", null );
		db.update("LOGIN", updateValues, where, null);
	}
	
	
	public void updateEntry(String userName, String password, String shopName, String shopAddress) {
		ContentValues updateValues = new ContentValues();
		updateValues.put("USERNAME", userName);
		updateValues.put("SHOPNAME", shopName);
		updateValues.put("SHOPADDRESS", shopAddress);
		updateValues.put("PASSWORD", password);

		String where = "USERNAME=?";
		db.update("LOGIN", updateValues, where, new String[] { userName });
	}

}
