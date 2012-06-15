package com.jahufar.batteryreciever;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView status;
	ProgressBar progress;
	TextView charged;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        registerReceiver(BatteryChangedReciever,  new IntentFilter(Intent.ACTION_BATTERY_CHANGED) );
        
        status = (TextView) findViewById(R.id.textStatus);
        charged = (TextView) findViewById(R.id.textPercent);
        progress = (ProgressBar) findViewById(R.id.progressBar1);
        
        
    }
    
    BroadcastReceiver BatteryChangedReciever  = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			int level =  intent.getIntExtra("level", 1);	
			int scale = intent.getIntExtra("scale", 1);
			
			progress.setMax(scale);
			progress.setProgress(level);
			
			int percent = 100 * level / scale;
			
			charged.setText("Level charged: " + Integer.valueOf(percent) + "%");
		
		}
	};
    
    
    
}