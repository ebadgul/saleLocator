package com.wit.salelocator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class SetLocation extends Activity implements OnMapReadyCallback, LocationListener{
	
	private GoogleMap gMap;
	private MarkerOptions markerOptions;
	private Marker marker;
	private LocationManager locationManager;
	private DBDesigner dbDesigner = new DBDesigner(this);
	public Button regUserConfirmBtn;
	
//	private Location location;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_location);
		
		//look orientation to portrait..
	    int orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
	    setRequestedOrientation(orientation);
		
		
		
		Button registerScreen = (Button) findViewById(R.id.regNewAccount);
		registerScreen.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//switching to register screen when the lik is clicked
				Double lat = SaleLocatorApp.currentShopUser.latitude; 
				if (lat == 0) {
					Toast.makeText(getApplicationContext(), "Sorry man you gotta set you shop location on the map", Toast.LENGTH_LONG).show();
					return;
				}
				
				dbDesigner = dbDesigner.open();
				dbDesigner.insertNewUser(SaleLocatorApp.currentShopUser);
				
				Intent intent = new Intent(getApplicationContext(), ShopControl.class);
				startActivity(intent);
			}
		});// end of the ac
		
		
		
		
		
		
		
		
		
		locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 5, this);
		
		
		
		
		
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.setLocationMap);
		gMap = (mapFragment).getMap();
		gMap.getUiSettings().setZoomControlsEnabled(true);
		gMap.setMyLocationEnabled(true);
		mapFragment.getMapAsync(this);
		
		
	}
	
/*	@Override
	protected void onResume() {
		
		locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 5, this);
		
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.setLocationMap);
		gMap = (mapFragment).getMap();
		gMap.getUiSettings().setZoomControlsEnabled(true);
		gMap.setMyLocationEnabled(true);
		mapFragment.getMapAsync(this);
		
		this.onMapReady(gMap);
		super.onResume();
	}*/
	

	@Override
	public void onMapReady(final GoogleMap googleMap) {
		googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			@Override
			public void onMapClick(LatLng point) {
				if(marker != null){
					marker.remove();
				}
				markerOptions = new MarkerOptions()
				.position(new LatLng(point.latitude, point.longitude))
				.title("")
				.snippet("")
				.draggable(true);
				
				
				
				//dbDesigner.setLocation(SaleLocatorApp.currentShopUser.shopUserName, point.latitude, point.longitude);
				Log.v("lat & lng", ""+point.latitude+ "" +point.longitude);
				SaleLocatorApp.currentShopUser.latitude =point.latitude;
				SaleLocatorApp.currentShopUser.longitude =point.longitude;
				
				
				
				googleMap.getUiSettings().setCompassEnabled(true);
				googleMap.getUiSettings().setMyLocationButtonEnabled(true);
				markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
				marker = googleMap.addMarker(markerOptions);
//				Toast.makeText(getApplicationContext(), "This Marker is added now"+markerOptions, Toast.LENGTH_LONG).show();
				
							}
		});
		
		//***************************Done and working start**********************************//
		//***************************Done and working start**********************************//	
			
			googleMap.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener() {
				 @Override
		         public boolean onMyLocationButtonClick() {

//		             locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		             final boolean enabledGPS = locationManager
		                     .isProviderEnabled(LocationManager.GPS_PROVIDER);

		            // if (!enabledGPS) {
		             AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SetLocation.this);
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
//				                 SaleActivity.this.finish();
							
							
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
			
			//***************************Done and working start**********************************//
			//***************************Done and working start**********************************//
			
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_location, menu);
		return true;
	}

	
	
	
/*	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(getApplicationContext(), SaleActivity.class));
		
//		Toast.makeText(getApplicationContext(), "set loc, you can't go back", Toast.LENGTH_LONG).show();
		return;
	}

	@Override
	public void onLocationChanged(Location location) {
		
		LatLng userLocation = null;
		
		if (location != null) {
			double lat= location.getLatitude();
			double lng = location.getLongitude();
			userLocation = new LatLng(lat, lng);	
			Log.v("long & lat", ""+userLocation);
		}
		gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation , 10));

	}

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

	/*@Override
	public boolean onMyLocationButtonClick() {
		
	    boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    	// check if enabled and if not send user to the GSP settings
    	// Better solution would be to display a dialog and suggesting to 
    	// go to the settings
    	if (!enabled) {
    	  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    	  
    	  startActivity(intent);
    	  return true;
    	} 
    	Log.v("working", "ttttttttttt");
		return false;
	}*/

	
}
