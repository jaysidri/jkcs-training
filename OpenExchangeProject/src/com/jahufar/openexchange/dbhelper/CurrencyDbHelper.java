package com.jahufar.openexchange.dbhelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CurrencyDbHelper {

	public static final String TABLE = "currencies";

	public static final String ID = "_id";
	public static final String CODE = "code";
	public static final String NAME = "name";

	SQLiteDatabase db = null;

	public CurrencyDbHelper(SQLiteDatabase db) {
		this.db = db;

	}

	public Cursor findCurrencyByName(String name) {

		name = "%" + name.trim() + "%";

		String sql = "SELECT " + NAME + ", " + ID + ", " + CODE + " FROM "
				+ TABLE + " WHERE " + NAME + " LIKE ?";

		Cursor cursor = db.rawQuery(sql, new String[] { name });

		return cursor;
	}

}
