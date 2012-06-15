package com.jahufar.openexchange.reciever;

import com.jahufar.openexchange.application.OpenExchangeApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NetworkChangeBroadcastReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		OpenExchangeApplication app = (OpenExchangeApplication) context
				.getApplicationContext();
		
		if( app.isConnected()) {
			Log.d(getClass().getName(), "Network available..");
			app.registerCurrencyExchangeRateServiceAlarm(context);
			
		} else {
			Log.d(getClass().getName(), "Network not available");
			app.unregisterCurrencyExchangeRateServiceAlarm(context);
			
		}
	}

}
