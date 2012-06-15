package com.jahufar.location;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements LocationListener {

	LocationManager locationManager;
	String provider = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		List<String> providers = locationManager.getAllProviders();

		for (String provider : providers) {
			Log.d(getClass().getName(), provider);
		}

		Criteria criteria = new Criteria();

		criteria.setCostAllowed(true);
		criteria.setAccuracy(Criteria.ACCURACY_FINE);

		provider = locationManager.getBestProvider(criteria, false);

		Log.d(getClass().getName(), "Best provider found for your criteria: "
				+ provider);
		/**
		 * if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		 * { Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		 * Toast.makeText(this, "I require GPS enabled.. please enable now..",
		 * Toast.LENGTH_LONG).show();
		 * 
		 * startActivity(i); } else { Toast.makeText(this, "GPS enabled",
		 * Toast.LENGTH_LONG).show();
		 * 
		 * }
		 **/

		Location location = locationManager.getLastKnownLocation(provider);

		if (null != location) {
			Log.d(getClass().getName(), "location detected, Lat/Lon: "
					+ location.getLatitude() + "/" + location.getLongitude());
		} else {
			Log.d(getClass().getName(), "Last known location unknown");

		}

		Intent i = new Intent(this, LocationBroadcastReciever.class);

		// Log.d(getClass().getName(), "Setting proximity");

		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i,
				PendingIntent.FLAG_UPDATE_CURRENT);

		locationManager.addProximityAlert(41.902915, 12.453389, 10, -1, pi);

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onPause() {
		super.onPause();

		// locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {

		if (null != location) {
			Log.d(getClass().getName(), "location detected, Lat/Lon: "
					+ location.getLatitude() + "/" + location.getLongitude());
		}

		Geocoder geocoder = new Geocoder(this);

		try {
			List<Address> addresses = geocoder.getFromLocation(
					location.getLatitude(), location.getLongitude(), 2);

			for (Address address : addresses) {
				Log.d(getClass().getName(), address.toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}
}