package com.jahufar.openexchange.activity;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jahufar.openexchange.R;
import com.jahufar.openexchange.R.id;
import com.jahufar.openexchange.R.layout;
import com.jahufar.openexchange.R.menu;
import com.jahufar.openexchange.application.OpenExchangeApplication;
import com.jahufar.openexchange.dbhelper.CurrencyDbHelper;
import com.jahufar.openexchange.dbhelper.Db;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	SimpleCursorAdapter adapter = null;

	Button btnFetchCurrencies = null;

	Db dbHelper = null;
	SQLiteDatabase db = null;

	OpenExchangeApplication app;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_stop:
			app.unregisterCurrencyExchangeRateServiceAlarm(this);
			break;

		case R.id.menu_start:
			if (!app.isConnected()) {
				Toast.makeText(
						this,
						"Sorry, not network connection was detected on your phone.",
						Toast.LENGTH_LONG).show();
			} else {
				app.registerCurrencyExchangeRateServiceAlarm(this);
			}

			break;

		case R.id.menu_settings:
			startActivity(new Intent(this, PreferenceActivity.class));
			break;

		case R.id.menu_convert:
			startActivity(new Intent(this, ConvertActivity.class));
			break;

		}

		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		app = (OpenExchangeApplication) getApplication();

		btnFetchCurrencies = (Button) findViewById(R.id.btnFetchCurrencies);

		btnFetchCurrencies.setEnabled(app.isConnected());

		registerReceiver(ConnectionChangedReciever, new IntentFilter(
				"android.net.conn.CONNECTIVITY_CHANGE"));

		dbHelper = new Db(this);
		db = dbHelper.getWritableDatabase();

		loadData();
	}

	public void loadData() {
		Cursor cursor = db.query(CurrencyDbHelper.TABLE, new String[] {
				CurrencyDbHelper.ID, CurrencyDbHelper.CODE,
				CurrencyDbHelper.NAME }, null, null, null, null,
				CurrencyDbHelper.NAME);

		adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, new String[] {
						CurrencyDbHelper.NAME, CurrencyDbHelper.CODE },
				new int[] { android.R.id.text1, android.R.id.text2 });

		setListAdapter(adapter);

		/**
		 * If you want to iterate thru the cursor, do this:
		 * 
		 * while (cursor.moveToNext()) { Log.d(getClass().getName(),
		 * cursor.getString(cursor .getColumnIndex(CurrencyDbHelper.NAME)) +
		 * " -> " + cursor.getString(cursor
		 * .getColumnIndex(CurrencyDbHelper.CODE)));
		 * 
		 * }
		 **/

	}

	public void parseCurrencies(String response) {

		String sql = null;
		sql = "DELETE FROM " + CurrencyDbHelper.TABLE;
		db.execSQL(sql);

		JSONObject json = null;

		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONArray currencies = json.names();

		for (int i = 0; i < currencies.length(); i++) {
			String currencyCode = null;
			String currencyName = null;

			try {
				currencyCode = currencies.get(i).toString();
				currencyName = json.getString(currencyCode);

				ContentValues cv = new ContentValues();
				cv.put(CurrencyDbHelper.CODE, currencyCode);
				cv.put(CurrencyDbHelper.NAME, currencyName);

				db.insert(CurrencyDbHelper.TABLE, null, cv);

				Log.d(getClass().getName(), "Inserting: " + currencyCode
						+ " -> " + currencyName);

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		adapter.notifyDataSetChanged();

	}

	public void onClick(View view) {

		Task task = new Task(this);

		String url = null;

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		url = prefs.getString("pref_currency_list_url",
				"http://openexchangerates.org/currencies.json");

		task.execute(url);

	}

	BroadcastReceiver ConnectionChangedReciever = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			btnFetchCurrencies.setEnabled(app.isConnected());
		}
	};

	private static class Task extends AsyncTask<String, Void, String> {

		MainActivity activity;
		ProgressDialog pd;

		public Task(MainActivity activity) {
			this.activity = activity;

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			pd.dismiss();
			this.activity.parseCurrencies(result);
		}

		@Override
		protected void onPreExecute() {

			pd = ProgressDialog.show(this.activity, "Currencies",
					"Please wait, getting currencies..");

		}

	}
}