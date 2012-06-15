package com.jahufar.openexchange.dbhelper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "openexchange.db";
	private static final int DB_VERSION = 1;

	private static final String CREATE_TABLE_CURRENCIES = "CREATE TABLE "
			+ CurrencyDbHelper.TABLE + "( " + CurrencyDbHelper.ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,  " + CurrencyDbHelper.CODE
			+ " TEXT," + CurrencyDbHelper.NAME + " TEXT )";

	private static final String CREATE_TABLE_CURRENCY_RATES = "CREATE TABLE  "
			+ CurrencyRatesDbHelper.TABLE + "(" + CurrencyRatesDbHelper.ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ CurrencyRatesDbHelper.CURRENCY_CODE + " TEXT, "
			+ CurrencyRatesDbHelper.VALUE + " TEXT);";

	public Db(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_CURRENCIES);
		db.execSQL(CREATE_TABLE_CURRENCY_RATES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
