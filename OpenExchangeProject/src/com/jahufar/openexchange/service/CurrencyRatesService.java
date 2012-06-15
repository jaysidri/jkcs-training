package com.jahufar.openexchange.service;

import java.io.IOException;
import java.util.Iterator;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.jahufar.openexchange.dbhelper.CurrencyRatesDbHelper;
import com.jahufar.openexchange.dbhelper.Db;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

public class CurrencyRatesService extends Service {

	WakeLock wl = null;

	CurrencyRatesDbHelper currencyRatesDbHelper = null;
	Db DbConnection = null;

	SQLiteDatabase db = null;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	public void done(String response) {

		Log.d(getClass().getName(), "Done.. parsing data now..");

		// make sure we delete all data before we begin this operation
		db.execSQL("BEGIN TRANSACTION");
		db.execSQL("DELETE FROM " + CurrencyRatesDbHelper.TABLE);

		JSONObject json = null;
		JSONObject rates = null;

		try {
			json = new JSONObject(response);
			rates = json.getJSONObject("rates");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Iterator iterator = rates.keys();

		while (iterator.hasNext()) {

			try {
				String currencyCode = iterator.next().toString();
				String currencyValue = rates.getString(currencyCode);

				ContentValues cv = new ContentValues();

				cv.put(CurrencyRatesDbHelper.CURRENCY_CODE, currencyCode);
				cv.put(CurrencyRatesDbHelper.VALUE, currencyValue);

				db.insert(CurrencyRatesDbHelper.TABLE, null, cv);

				Log.d(getClass().getName(), currencyCode + " => "
						+ currencyValue);

			} catch (JSONException e) {
				e.printStackTrace(); 
			}
		}

		db.execSQL("COMMIT");
		db.close();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		Editor editor = prefs.edit();
		editor.putLong("last_run", SystemClock.elapsedRealtime());
		editor.commit();

		wl.release();
		stopSelf();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.d(getClass().getName(), "Starting service..");

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

		wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.PARTIAL_WAKE_LOCK, getClass().getName()
				+ "_WAKELOCK");

		wl.acquire();

		DbConnection = new Db(this);

		db = DbConnection.getWritableDatabase();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		new Task(this).execute(prefs.getString("pref_currency_rate_url",
				"http://openexchangerates.org/latest.json"));

		return super.onStartCommand(intent, flags, startId);
	}

	private static class Task extends AsyncTask<String, Void, String> {

		CurrencyRatesService service = null;

		public Task(CurrencyRatesService service) {
			this.service = service;
		}

		@Override
		protected String doInBackground(String... url) {

			HttpClient client = new DefaultHttpClient();

			HttpGet get = new HttpGet(url[0]);

			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			String response = null;

			try {
				response = client.execute(get, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			this.service.done(result);
			super.onPostExecute(result);
		}

	}

}
