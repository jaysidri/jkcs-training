package com.jahufar.openexchange.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.widget.TextView;
import android.widget.Toast;

import com.jahufar.openexchange.R;
import com.jahufar.openexchange.dbhelper.CurrencyDbHelper;
import com.jahufar.openexchange.dbhelper.CurrencyRatesDbHelper;
import com.jahufar.openexchange.dbhelper.Db;

public class ConvertActivity extends Activity {

	SimpleCursorAdapter adapterFromCurrency, adapterToCurrency;
	AutoCompleteTextView acFromCurrency;
	AutoCompleteTextView acToCurrency;
	TextView txtAmount;

	Db dbHelper = null;
	SQLiteDatabase db = null;
	CurrencyDbHelper currencyDbHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.convert);

		adapterFromCurrency = new SimpleCursorAdapter(this,
				android.R.layout.simple_dropdown_item_1line, null,
				new String[] { "name" }, new int[] { android.R.id.text1 });

		adapterToCurrency = new SimpleCursorAdapter(this,
				android.R.layout.simple_dropdown_item_1line, null,
				new String[] { "name" }, new int[] { android.R.id.text1 });

		acFromCurrency = (AutoCompleteTextView) findViewById(R.id.acFromCurrency);
		acToCurrency = (AutoCompleteTextView) findViewById(R.id.acToCurrency);
		txtAmount = (TextView) findViewById(R.id.editAmount);

		acFromCurrency.setAdapter(adapterFromCurrency);
		acToCurrency.setAdapter(adapterToCurrency);

		dbHelper = new Db(this);

		db = dbHelper.getReadableDatabase();

		currencyDbHelper = new CurrencyDbHelper(db);

		adapterFromCurrency
				.setFilterQueryProvider(new ConvertorFilterQueryProvider(
						acFromCurrency));

		adapterFromCurrency
				.setCursorToStringConverter(new ConvertorCursorToStringConverter(
						acFromCurrency));

		adapterToCurrency
				.setFilterQueryProvider(new ConvertorFilterQueryProvider(
						acToCurrency));

		adapterToCurrency
				.setCursorToStringConverter(new ConvertorCursorToStringConverter(
						acToCurrency));

	}

	private class ConvertorCursorToStringConverter implements
			CursorToStringConverter {

		AutoCompleteTextView textview;

		public ConvertorCursorToStringConverter(AutoCompleteTextView textview) {
			this.textview = textview;
		}

		@Override
		public CharSequence convertToString(Cursor cursor) {

			textview.setTag(cursor.getString(cursor
					.getColumnIndex(CurrencyDbHelper.CODE)));

			return cursor.getString(cursor
					.getColumnIndex(CurrencyDbHelper.NAME));

		}

	}

	private class ConvertorFilterQueryProvider implements FilterQueryProvider {

		AutoCompleteTextView textview;

		public ConvertorFilterQueryProvider(AutoCompleteTextView textview) {
			this.textview = textview;
		}

		@Override
		public Cursor runQuery(CharSequence constraint) {

			Log.d(getClass().getName(), "FilterQueryProvider::runQuery => "
					+ constraint);

			if (null == constraint)
				return null;

			return currencyDbHelper.findCurrencyByName(constraint.toString());

		}

	}

	public void onClickConvert(View v) {

		Float amount;

		try {
			amount = Float.parseFloat(txtAmount.getText().toString());
		} catch (NumberFormatException e) {
			Toast.makeText(this,
					"Invalid amount entered:" + txtAmount.getText(),
					Toast.LENGTH_SHORT).show();
			return;
		}

		CurrencyRatesDbHelper currencyRatesDbHelper = new CurrencyRatesDbHelper(
				db);

		float fromRate = currencyRatesDbHelper.getExchangeRate(acFromCurrency
				.getTag().toString());

		float toRate = currencyRatesDbHelper.getExchangeRate(acToCurrency
				.getTag().toString());

		// Toast.makeText(this, fromRate + " -> " + toRate, Toast.LENGTH_LONG)
		// .show();

		double result = amount * (Math.round(toRate * (1 / fromRate)));

		Toast.makeText(
				this,
				String.valueOf(amount) + acFromCurrency.getTag().toString()
						+ " = " + String.valueOf(result) + " "
						+ acToCurrency.getTag().toString(), Toast.LENGTH_LONG)
				.show();

	}
}
