package com.wit.salelocator;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.wit.salelocator.db.DBDesigner;
import com.wit.salelocator.shopuser.SaleLocatorApp;
import com.wit.salelocator.shopuser.ShopUser;

public class ShopControl extends Activity {

	/// thanks god its working yeaaaaaaaaaaaaaaaaaaaasssssssssss
	private DBDesigner	dbDesigner = new DBDesigner(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_control);
		
		 int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
		    setRequestedOrientation(orientation);
		
		
		TextView name = (TextView) findViewById(R.id.showUserName);
		TextView shopName = (TextView) findViewById(R.id.showShopName);
		TextView shopAddress = (TextView) findViewById(R.id.showShopAddress);
//		Switch swwitch = (Switch) findViewById(R.id.on_off_switch);
		
		
		name.setText(SaleLocatorApp.currentShopUser.shopUserName);
		shopName.setText(SaleLocatorApp.currentShopUser.shopName);
		shopAddress.setText(SaleLocatorApp.currentShopUser.shopAddress);
//		shopName.setText(SaleLocatorApp.currentShopUser.shopName+"--"+SaleLocatorApp.currentShopUser.latitude);
//		shopAddress.setText(SaleLocatorApp.currentShopUser.shopAddress+SaleLocatorApp.currentShopUser.saleOn);
		
		
		dbDesigner.open();
		
		
		
		ShopUser u =dbDesigner.getUser(SaleLocatorApp.currentShopUser.shopUserName);
		
//		Switch switch = (Switch)findViewById(R.id.on_off_switch);
		Switch swwitch1 = (Switch) findViewById(R.id.on_off_switch);
		
		if(u.saleOn.equals("Y")){
			
			swwitch1.setChecked(true);
		}else{
			
			swwitch1.setChecked(false);
		}
		
		swwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					//Log.v("", "somthing in log swithc");
					SaleLocatorApp.currentShopUser.saleOn = "Y";
				
				dbDesigner.open();
				dbDesigner.setSale(SaleLocatorApp.currentShopUser.shopUserName, "Y");
				
				
				ShopUser u =dbDesigner.getUser(SaleLocatorApp.currentShopUser.shopUserName);
				
				Log.v("Switched on", u.saleOn);
				}
				else if (!isChecked){
					
					SaleLocatorApp.currentShopUser.saleOn = "N";
					
					dbDesigner.open();
					dbDesigner.setSale(SaleLocatorApp.currentShopUser.shopUserName, "N");
					
					ShopUser u =dbDesigner.getUser(SaleLocatorApp.currentShopUser.shopUserName);
					
					Log.v("Switched off", u.saleOn);
				}
			}
		}) ;
		
		
/*		ToggleButton toggle = (ToggleButton) findViewById(R.id.togglebutton);
		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		            // The toggle is enabled
		        } else {
		            // The toggle is disabled
		        }
		    }
		});*/
		
		
	}//end of onCreate method....
	
	public void goToMap(View v){
		
		Intent intent = new Intent(getApplicationContext(), SaleActivity.class);
		startActivity(intent);
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop_controll, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.logout) {
			SaleLocatorApp.loggedIn = false;
			startActivity(new Intent(this, SaleActivity.class));
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
