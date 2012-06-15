package com.jahufar.services;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class MainActivity extends Activity {
	
	WakeLock wl;
	PowerManager pm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent intent = new Intent(this, MyService.class);

		// startService(intent);

		// stopService(intent);

		pm = (PowerManager) getSystemService(POWER_SERVICE);

		

	}
	
	@Override
	protected void onResume() {

		super.onResume();
		
		wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.FULL_WAKE_LOCK, "MyWakeLock");
		
		wl.acquire();
		
	}
	
	 @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		wl.release();
		
		
	}
	
	
}