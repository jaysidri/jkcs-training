package com.jahufar.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

public class LocationBroadcastReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.d(getClass().getName(), "LocationBroadcastReciever fired");

		if (intent.hasExtra(LocationManager.KEY_PROXIMITY_ENTERING)) {

			if (intent.getExtras().getBoolean(
					LocationManager.KEY_PROXIMITY_ENTERING)) {
				Log.d(getClass().getName(), "Entering proximity");
			} else {
				Log.d(getClass().getName(), "Exiting proximity");
			}

		}

	}

}
