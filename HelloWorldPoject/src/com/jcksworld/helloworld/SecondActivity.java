package com.jcksworld.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity implements OnClickListener {

	private TextView editRecievedText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.second_activity);

		Button buttonBack = (Button) findViewById(R.id.buttonBack);

		buttonBack.setOnClickListener(this);

		editRecievedText = (TextView) findViewById(R.id.textRecievedMessage);

		Intent intent = getIntent();

		String message = intent.getStringExtra("message");

		editRecievedText.setText(message);

	}

	@Override
	public void onClick(View v) {

		Intent intent = new Intent();

		intent.putExtra("result", "Goodbye World");

		setResult(RESULT_OK, intent);

		finish();

	}

}
