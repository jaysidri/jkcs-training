package com.jahufar.openexchange.application;

import com.jahufar.openexchange.service.CurrencyRatesService;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.util.Log;

public class OpenExchangeApplication extends Application {
	
	
	public boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

		NetworkInfo ni = cm.getActiveNetworkInfo();

		if (null == ni)
			return false;

		if (ni.isConnected())
			return true;

		return false;
	}

	public void registerCurrencyExchangeRateServiceAlarm(Context context) {

		Log.d(getClass().getName(), "Setting repeating alarm..");
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

		Intent intent = new Intent(context, CurrencyRatesService.class);

		PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);

		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				SystemClock.elapsedRealtime() + 3000, 10000, pi);

	}

	public void unregisterCurrencyExchangeRateServiceAlarm(Context context) {
		Log.d(getClass().getName(), "Unsetting repeating alarm..");

		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

		Intent intent = new Intent(context, CurrencyRatesService.class);

		PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);

		am.cancel(pi);

	}

}
