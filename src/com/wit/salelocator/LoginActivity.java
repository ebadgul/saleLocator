package com.wit.salelocator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wit.salelocator.db.DBDesigner;
import com.wit.salelocator.shopuser.SaleLocatorApp;

public class LoginActivity extends Activity {
	
	Button signInBtn;
	DBDesigner dbDesigner;
	
	 EditText editTextUserName;
	 EditText editTextPassword;
//	 Button signInBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		// creating object of the SQLite database
		dbDesigner = new DBDesigner(this);
		dbDesigner = dbDesigner.open();
		
		//getting the reference of sign in button
		signInBtn = (Button) findViewById(R.id.btnLogin);
		
		
		
		// going back home screen button on the top left corner
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
		registerScreen.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*Double lat = SaleLocatorApp.currentShopUser.latitude;
				if (lat == 0) {
					startActivity(new Intent(getApplicationContext(), SetLocation.class));
				}else {*/
				
				//switching to register screen when the lik is clicked
				Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(intent);
//			}
			}
		});// end of the actionlistener
		
		
	//}
	//public void logIn(View view){
		
		 editTextUserName = (EditText) findViewById(R.id.logInUserNameInput);
		editTextPassword = (EditText) findViewById(R.id.logInPasswordInput);
		signInBtn = (Button) findViewById(R.id.btnLogin);
		/*		signInBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String userName = editTextUserName.getText().toString();
				String password = editTextPassword.getText().toString();
				
				String storedPassword = dbDesigner.getSingleEntry(userName);
				
				if (password.equals(storedPassword)) {
					Intent intent = new Intent(getApplicationContext(), ShopControl.class);
					startActivity(intent);
					
//					startActivity(new Intent(getApplicationContext(), ShopControl.class));
					
					Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
					Log.v("passwrod", ""+storedPassword);
					editTextUserName.setText("");
					editTextPassword.setText("");
				}else {
					Toast.makeText(LoginActivity.this, "User Name or Password Doesn't Match", Toast.LENGTH_LONG).show();
				}
			} 
		});//end of the actionlistner
*/		
	}
	
	public void onLogInClick(View v) {
		
		String userName = editTextUserName.getText().toString();
		String password = editTextPassword.getText().toString();
		
		String storedPassword = dbDesigner.getSingleEntry(userName);
		
		if(password.equals("")){
			Toast.makeText(getApplicationContext(), "sorry man a field is empty", Toast.LENGTH_LONG).show();
			return;
		}if (password.equals(storedPassword)) {
			
			SaleLocatorApp.loggedIn = true;
			
			SaleLocatorApp.currentShopUser =  dbDesigner.getUser(userName); 
			Intent intent = new Intent(v.getContext(), ShopControl.class);
			startActivity(intent);
			LoginActivity.this.finish();
//			startActivity(new Intent(this, ShopControl.class));
			
			Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
			Log.v("passwrod", ""+storedPassword);
			editTextUserName.setText("");
			editTextPassword.setText("");
		}else {
			Toast.makeText(LoginActivity.this, "user name or password doesn't match", Toast.LENGTH_LONG).show();
		}
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		dbDesigner.close();
		super.onDestroy();
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//going back home button
		case android.R.id.home:
			super.onBackPressed();
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

}
