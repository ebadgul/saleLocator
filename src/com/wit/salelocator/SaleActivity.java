package com.wit.salelocator;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wit.salelocator.db.DBDesigner;
import com.wit.salelocator.shopuser.SaleLocatorApp;
import com.wit.salelocator.shopuser.ShopUser;

public class SaleActivity extends Activity implements OnMapReadyCallback, LocationListener{

	private GoogleMap gMap;
	private MarkerOptions markerOptions;
	// private GoogleMap mapFragment;
	public Marker marker;
	private LocationManager locationManager;
	private List<ShopUser> users;
	private DBDesigner dbDesigner = new DBDesigner(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale);

		// adding map fragment
		// mapFragment = ((MapFragment)
		// getFragmentManager().findFragmentById(R.id.map)).getMap();
		// mapFragment.setMyLocationEnabled(true);
		// mapFragment.getMapAsync(this);
		
		locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 5, this);
	      
		
/*		new AlertDialog.Builder(this)
	    .setTitle("Delete entry")
	    .setMessage("Are you sure you want to delete this entry?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        }
	     }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int which) { 
	             // do nothing
	         }
	      })
	     .setIcon(android.R.drawable.ic_dialog_alert)
	      .show();
		*/
		

		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		gMap = (mapFragment).getMap();
		/*gMap.getCameraPosition();
		gMap.getUiSettings();*/
		gMap.setMyLocationEnabled(true);
		mapFragment.getMapAsync(this);
		
		
		
		
	}
	

	//***************************************************************************************************//
	//***************************************************************************************************//

	@Override
	protected void onRestart() {
//		startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
		super.onRestart();
	}

	@Override
	protected void onResume() {
		
		this.onMapReady(gMap);
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

//***************************************************************************************************//
//***************************************************************************************************//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sale, menu);
		return true;
	}

	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 super.onPrepareOptionsMenu(menu);
		 
		 MenuItem login = menu.findItem(R.id.xml_login);
		 MenuItem controlPanel = menu.findItem(R.id.Control_Panel);
		 
		 if (SaleLocatorApp.loggedIn) {
			 login.setEnabled(false);
			 controlPanel.setEnabled(true);
		 }else{
			 login.setEnabled(true);
			 controlPanel.setEnabled(false);
		 }
			 
		return true;
		
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		switch (item.getItemId()) {
		case R.id.xml_login:
			startActivity(new Intent(this, LoginActivity.class));
			break;

		case R.id.Control_Panel:
			 Double lat = SaleLocatorApp.currentShopUser.latitude;
			 if (lat == 0) {
			startActivity(new Intent(this, SetLocation.class));
			 }else{
				 startActivity(new Intent(this, ShopControl.class));	 
			 }
				 
			break;
			
		default:
			break;
		}
		return true;
		/*
		 * int id = item.getItemId(); if (id == R.id.action_settings) { return
		 * true; } return super.onOptionsItemSelected(item);
		 */
	}

	@Override
	public void onMapReady(final GoogleMap googleMap) {
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.getUiSettings().setCompassEnabled(true);
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);
		
		
		dbDesigner.open();	
		users = dbDesigner.getAll();
		
		for(int i=0; i<users.size(); i++){
			
			double lat = users.get(i).latitude;
			double lng = users.get(i).longitude;
			
			
			markerOptions = new MarkerOptions()
			.position(new LatLng(lat, lng))
			.title(users.get(i).shopName)
			.snippet(users.get(i).shopAddress)
			.draggable(false);
			markerOptions.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
			marker = googleMap.addMarker(markerOptions);
		}
		
		

		
		
	/// working of the google map button end	
		
		
		
	//***************************Done and working start**********************************//
	//***************************Done and working start**********************************//	
		
		googleMap.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener() {
			 @Override
	         public boolean onMyLocationButtonClick() {

//	             locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

	             final boolean enabledGPS = locationManager
	                     .isProviderEnabled(LocationManager.GPS_PROVIDER);

	            // if (!enabledGPS) {
	             AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SaleActivity.this);
	             alertDialogBuilder.setTitle("Location services disabled");
	             
	             alertDialogBuilder
	             .setMessage("Sale Locator needs access to your location. Please turn on location access.")
	             .setCancelable(false)
	             .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
							 Intent intent = new Intent(
			                         Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			                 startActivity(intent);
						
						
					} 
				}).setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
	            if(!enabledGPS){
	             AlertDialog alerDialog = alertDialogBuilder.create();
	             alerDialog.show();
	            	        return true;       
	            }
	            return false;
	                     
			 }
			 });
		
		//***************************Done and working end**********************************//
		//***************************Done and working end**********************************//
		
		
	}
	
	
	/*public void currentLocation(MenuItem item) {

		Toast.makeText(this, "you pressed the current location icon",
				Toast.LENGTH_SHORT).show();

	}*/


//*********************************************************************************************//
	//*********************************************************************************************//

	@Override
	public void onLocationChanged(Location location) {
		
		LatLng userLocaiton = null;
		if (location !=null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			userLocaiton = new LatLng(lat, lng);
			Log.v("long & lat", ""+userLocaiton);
		}
		gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocaiton, 10));
		gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocaiton, 10.0f));
//		gMap.addMarker(markerOptions);
//		gMap.
		
		
	}

	//***********************************************************************************//



	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	//*********************************************************************************************//
	//*********************************************************************************************//	

	
/*	
	@Override
	public boolean onMyLocationButtonClick() {
		
		 Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
		
		boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!enabled) {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
//			return true;
		}
		
		gMap.getMyLocation();
		return true;
	}*/

	//////////////////////////////////

}// end of the class
