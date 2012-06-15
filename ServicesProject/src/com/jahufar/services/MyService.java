package com.jahufar.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	
	int startId;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		Log.d("Services", "onStart");
		
		this.startId = startId;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("Services", "onDestroy");
		
		stopSelf(this.startId);
	}

}
