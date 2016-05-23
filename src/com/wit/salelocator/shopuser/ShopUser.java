package com.wit.salelocator.shopuser;

import android.location.Location;

public class ShopUser {

	public String shopUserName;
	public String shopName;
	public String shopAddress;
	public Location shopLocation;
	public String shopPassword;
	public String saleOn;
	public double latitude;
	public double longitude;
	
	 
	
/*	public ShopUser(String shopUserName, String shopName, String shopAddress,
			Location shopLocation, String shopPassword, boolean saleOn) {
		super();
		this.shopUserName = shopUserName;
		this.shopName = shopName;
		this.shopAddress = shopAddress;
		this.shopLocation = shopLocation;
		this.shopPassword = shopPassword;
		this.saleOn = saleOn;
	}*/

	public ShopUser(String shopUserName, String shopPassword, String shopName, String shopAddress, String saleOn)
	{
		super();
		this.shopUserName = shopUserName;
		this.shopName = shopName;
		this.shopAddress = shopAddress;
		this.shopPassword = shopPassword;
		this.saleOn = saleOn;
		
		
	}
	
	
	
}
