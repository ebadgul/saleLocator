package com.wit.salelocator.shopuser;

import android.app.Application;
import android.util.Log;

public class SaleLocatorApp extends Application{

	public static ShopUser currentShopUser ;
	public static boolean loggedIn = false;
	
	public void onCreate(){
		super.onCreate();
		Log.v("Sale locatoer app", "app started");
	}
	
	
	
	
}
