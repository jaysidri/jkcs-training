package com.jahufar.openexchange.contentprovider;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.jahufar.openexchange.dbhelper.CurrencyRatesDbHelper;
import com.jahufar.openexchange.dbhelper.Db;

public class ExchangeRateProvider extends ContentProvider {

	/**
	 * So our URI to query this content provider looks like:
	 * 
	 * content://com.jahufar.openexchange.contentprovider/rates/[code]
	 * 
	 * e.g:
	 * 
	 * content://com.jahufar.openexchange.contentprovider/rates/JPY
	 * 
	 * This return back the exchange rate for the Yen (relative to USD)
	 */

	// Used for the UriMacher
	private static final int CURRENCY_CODE = 10;

	private static final String BASE_PATH = "rates";
	public final static String AUTHORITY = "com.jahufar.openexchange.contentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ BASE_PATH); // URI for access the Content Provider

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/rate";

	private static final UriMatcher URI_MATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);

	static {
		URI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/*", CURRENCY_CODE);
	}

	Db dbHelper = null;
	SQLiteDatabase db = null;
	CurrencyRatesDbHelper currencyRatesDbHelper = null;

	private static final String[] AVAILABLE_COLUMNS = {
			CurrencyRatesDbHelper.CURRENCY_CODE, CurrencyRatesDbHelper.ID,
			CurrencyRatesDbHelper.VALUE };

	@Override
	public boolean onCreate() {
		dbHelper = new Db(getContext());

		return (dbHelper == null) ? false : true;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// We don't want other people deleting or inserting data into our DB,
		// for obvious reasons.
		//
		// You can always return null.. but I like to throw
		// java.lang.UnsupportedOperationException to make sure the person
		// calling this method gets the message.

		throw new java.lang.UnsupportedOperationException(
				"Operation not supported");

	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		throw new java.lang.UnsupportedOperationException(
				"Operation not supported");
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		checkColumns(projection); // making sure all the columns asked for are
									// valid

		if (db == null) {
			// it's recommended not to open the DB inside onCreate(). So
			// instead, we lazy load it here. NOTE: We can never close the DB
			// here.. doing so will automatically destroy the cursor object we
			// intend to return.

			db = dbHelper.getReadableDatabase();
			currencyRatesDbHelper = new CurrencyRatesDbHelper(db);
		}

		// something new for you - instead of using query(), I'm using
		// SQLiteQueryBuilder to build my query. why? because I can
		SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

		sqLiteQueryBuilder.setTables(CurrencyRatesDbHelper.TABLE);

		int uriType = URI_MATCHER.match(uri);

		if (uriType != CURRENCY_CODE)
			throw new IllegalArgumentException("Unknown URI: " + uri);

		sqLiteQueryBuilder.appendWhere(CurrencyRatesDbHelper.CURRENCY_CODE
				+ " = '" + uri.getLastPathSegment() + "'");

		Cursor cursor = sqLiteQueryBuilder.query(db, projection, null, null,
				null, null, null);

		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		throw new java.lang.UnsupportedOperationException(
				"Operation not supported");

	}

	private void checkColumns(String[] projection) {

		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(
					Arrays.asList(projection));

			HashSet<String> availableColumns = new HashSet<String>(
					Arrays.asList(AVAILABLE_COLUMNS));

			// Check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException(
						"Unknown columns in projection");
			}
		}
	}

}
