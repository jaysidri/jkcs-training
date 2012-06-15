package com.jcksworld.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HelloWorldActivity extends Activity implements OnClickListener {

	private final String TAG = HelloWorldActivity.class.getSimpleName();

	private EditText message;

	private Button exit;

	private final int ID = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d(TAG, "onCreate Fired!");

		Button button = (Button) findViewById(R.id.btnTest);

		button.setOnClickListener(this);

		message = (EditText) findViewById(R.id.editMessage);
		exit = (Button) findViewById(R.id.btnExit);

		exit.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		
		int id = v.getId();

		switch (id) {
		case R.id.btnTest:
			Intent intent = new Intent(this, SecondActivity.class);

			intent.putExtra("message", message.getText().toString());
			startActivityForResult(intent, ID);
			break;
			
		case R.id.btnExit:
			finish();
			break;

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ID && resultCode == RESULT_OK) {
			String result = data.getStringExtra("result");

			Toast.makeText(this, "Result was: " + result, Toast.LENGTH_SHORT)
					.show();
		} else
			Toast.makeText(this, "The user cancelled", Toast.LENGTH_LONG)
					.show();

	}

}