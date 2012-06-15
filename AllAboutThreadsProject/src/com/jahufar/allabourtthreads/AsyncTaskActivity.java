package com.jahufar.allabourtthreads;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AsyncTaskActivity extends Activity {

	String[] items = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5",
			"Item 6", "Item 7", "Item 8" };

	ProgressBar progressBar;
	Task task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		progressBar = (ProgressBar) findViewById(R.id.progress);

	}

	public void updateProgress(int progress) {
		progressBar.setProgress(progress);
	}

	public void initProgessbar() {
		progressBar.setMax(items.length - 1); 

	}

	private static class Task extends AsyncTask<String, Integer, Void> { 

		AsyncTaskActivity activity;

		int progress;

		public Task(AsyncTaskActivity activity) {
			this.activity = activity;
		}

		@Override
		protected Void doInBackground(String... items) {
			int i = 0;
			for (String item : items) {
				Log.d("task", "Processing item #" + i);
				/**
				 * if (i == 4) { cancel(false); }
				 **/

				if (isCancelled())
					return null;

				publishProgress(i);
				i++;

				SystemClock.sleep(1000);

			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			this.activity.updateProgress(progress[0]);
			this.progress = progress[0];
		}

		@Override
		protected void onPostExecute(Void result) {
			if (!isCancelled())
				Toast.makeText(activity, "All done master :)",
						Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onPreExecute() {
			activity.initProgessbar();

		}

		@Override
		protected void onCancelled() {
			Toast.makeText(activity, "Oh noes.. cancelled! :(",
					Toast.LENGTH_LONG).show();

		}

		void attach(AsyncTaskActivity activity) {
			this.activity = activity;
		}

		void detach() {
			this.activity = null;
		}

		int getProgress() {
			return this.progress;
		}

	}

	protected void onStart() {
		super.onStart();

		task = (Task) getLastNonConfigurationInstance();
		
		initProgessbar();

		if (null == task) { 
			task = new Task(this);
			task.execute(items);
		} else {
			task.attach(this);
			progressBar.setProgress(task.getProgress());			
		}

		


	};

	@Override
	public Object onRetainNonConfigurationInstance() {
		task.detach();
		return task;
	}

}
