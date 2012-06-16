package com.jahufar.openexchange.dbhelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CurrencyRatesDbHelper {

	public static final String TABLE = "exchange_rates";
	public static final String ID = "_id";
	public static final String CURRENCY_CODE = "code";
	public static final String VALUE = "value";

	SQLiteDatabase db;

	public CurrencyRatesDbHelper(SQLiteDatabase db) {
		this.db = db;
	}

	public Float getExchangeRate(String code) {

		Cursor cursor = db.query(TABLE, new String[] { VALUE }, CURRENCY_CODE
				+ "= ?", new String[] { code }, null, null, null);

		cursor.moveToFirst();
		return new Float(cursor.getString(cursor.getColumnIndex(VALUE)));
	}

}
