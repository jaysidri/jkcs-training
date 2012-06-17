package com.jahufar.openexchange.contentprovider.test;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText txtCurrencyCode = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		txtCurrencyCode = (EditText) findViewById(R.id.editText1);

	}

	public void onConvertClick(View view) {

		Log.d(getClass().getName(), "Convert now..");

		Cursor cursor = null;

		try {
			cursor = getContentResolver()
					.query(Uri.parse("content://com.jahufar.openexchange.contentprovider/rates/"
							+ txtCurrencyCode.getText().toString()), null,
							null, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
			return;
		}

		cursor.moveToFirst();

		Toast.makeText(this, cursor.getString(cursor.getColumnIndex("VALUE")),
				Toast.LENGTH_SHORT).show();

	}
}