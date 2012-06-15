package com.jahufar.allabourtthreads;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	
	ProgressBar progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        progress = (ProgressBar) findViewById(R.id.progress);
        
        progress.setMax(100);
        progress.setProgress(0);
        
       
    }
    
    
    
    
    
    Handler handler = new  Handler() {
    	
    	public void handleMessage(Message msg) {
    		progress.incrementProgressBy(1);
    	};
    	
    };
    
    
    
    
    Thread thread = new Thread( new Runnable() {
		
		@Override
		public void run() {
			
			for(int i = 0; i<= 100; i++ ) {
				//Message message = new Message();
				//Bundle data = new Bundle();
				//data.putString("Blah", "fdjklshfsdkljf");
				//message.setData(data);
				
				//message.setTarget(handler);
				
				handler.sendMessage(handler.obtainMessage());
				
				SystemClock.sleep(100);
				

				
				
			}
	
			
		}
		
		
	} );
    
    
    protected void onStart() {
    	super.onStart();
    	thread.start();
    }
    
    
    
}