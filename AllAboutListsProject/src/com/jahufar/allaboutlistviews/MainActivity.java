package com.jahufar.allaboutlistviews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

	}

	public void onButtonClick(View v) {

		int id = v.getId();
		Intent intent = null;

		switch (id) {

		case R.id.cmdPlainOldListview:
			intent = new Intent(this, ListActivity.class);

			break;

		case R.id.cmdListViewViaListActivity:
			intent = new Intent(this, ListActivityV2.class);
			break;

		case R.id.cmdCustomListview:
			intent = new Intent(this, CustomListview.class);
			break;
			
		case R.id.cmdSpinner:
			intent = new Intent(this, SpinnerActivity.class);
			break;

		}

		if (null != intent)
			startActivity(intent);

	}
}
