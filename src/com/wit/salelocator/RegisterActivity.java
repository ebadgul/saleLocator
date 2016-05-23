package com.wit.salelocator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wit.salelocator.db.DBDesigner;
import com.wit.salelocator.shopuser.SaleLocatorApp;
import com.wit.salelocator.shopuser.ShopUser;

public class RegisterActivity extends Activity{

	private EditText regUserName, regShopName, regShopAddress, regUserPassword, regUserConfirmPassword;
	private String regSaleOn;
//	private  Button regUserConfirmBtn;
	private DBDesigner registerDatabase;
//	Button regUserConfirmBtn;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		
		TextView setUpLoc = (TextView) findViewById(R.id.regPickurLoc);
		setUpLoc.setPaintFlags(setUpLoc.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		setUpLoc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String userName = regUserName.getText().toString();
				String shopName = regShopName.getText().toString();
				String shopAddress = regShopAddress.getText().toString();
				String password = regUserPassword.getText().toString();
				String saleOn = regSaleOn;
				
				
				////jst checkn if paswords matches or nt
				String confirmPassword = regUserConfirmPassword.getText().toString();
				/////cheking username if already exited or not...
				boolean taken = registerDatabase.userNameTaken(userName);
				if (taken) {
					Toast.makeText(getApplicationContext(), "Sorry man the name already exits", Toast.LENGTH_LONG).show();
					return;
				}
				
				if(userName.equals("") || shopName.equals("") || shopAddress.equals("") || password.equals("") || confirmPassword.equals("")){
					Toast.makeText(getApplicationContext(), "sorry man a field is empty",
							Toast.LENGTH_LONG).show();
					return;
					
				}if(password.length() <= 4){
					Toast.makeText(getApplicationContext(), "password should be atleast 5 digits", Toast.LENGTH_LONG).show();
					return;
				}
				
				if (!password.equals(confirmPassword)) {
					Toast.makeText(getApplicationContext(),
							"Password does not match", Toast.LENGTH_LONG)
							.show();
					return;
				}else {
					//registerDatabase.insertEntry(userName, password, shopName, shopAddress);
					Toast.makeText(getApplicationContext(),
							"Account Successfully Created ", Toast.LENGTH_LONG)
							.show();
					//initallising the shopuser opjects
					SaleLocatorApp.currentShopUser = new ShopUser(userName, password, shopName, shopAddress, saleOn);
					
					
//					Toast.makeText(getApplicationContext(), "name & password"+registerDatabase, Toast.LENGTH_LONG).show();
				}
				SaleLocatorApp.loggedIn = true;
				Intent intent = new Intent(v.getContext(), SetLocation.class);
				startActivity(intent);
				RegisterActivity.this.finish();
			}
		});
		
		
		
		
		
		
		TextView loginScreen =  (TextView) findViewById(R.id.link_to_login);
		// going to login screen back from registration
		loginScreen.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//switching to login screen and closing register screen
				finish();
			}
		});// end of changing to login screen actionlistener
		
		//***********list activity*****************************************************************//
		
		
		
		 
		
		registerDatabase = new DBDesigner(this);
		registerDatabase = registerDatabase.open();
		
		
		// database entriess starts
		regUserName = (EditText) findViewById(R.id.regUserNameInput);
		regShopName = (EditText) findViewById(R.id.regshopNameInput);
		regShopAddress = (EditText) findViewById(R.id.regShopAddressInput);
		regUserPassword = (EditText) findViewById(R.id.regUserFirstPassword);
		regUserConfirmPassword = (EditText) findViewById(R.id.regUserConfirmPassword);
		
		
	}
	
	
	protected void onDestroy(){
		super.onDestroy();
		registerDatabase.close();
	}

	
}
