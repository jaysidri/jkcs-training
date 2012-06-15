package com.jahufar.allaboutlistviews;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SpinnerActivity extends Activity implements OnItemSelectedListener {

	Spinner spin;
	ArrayList<AddressBook> data;
	ArrayAdapter<AddressBook> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.spinner);

		spin = (Spinner) findViewById(R.id.spin);

		data = populate();

		adapter = new ArrayAdapter<AddressBook>(this,
				android.R.layout.simple_spinner_item, data);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spin.setAdapter(adapter);

		spin.setOnItemSelectedListener(this);

	}

	private ArrayList<AddressBook> populate() {

		ArrayList<AddressBook> temp = new ArrayList<AddressBook>();

		for (int i = 0; i <= 20; i++) {

			AddressBook entry = new AddressBook();
			entry.setId(Integer.valueOf(i));
			entry.setName("Name: " + String.valueOf(i));
			entry.setPhone("Phone: " + String.valueOf(i));

			temp.add(entry);

		}

		return temp;

	}

	class AddressBook {
		private Integer id;
		private String name;
		private String phone;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		@Override
		public String toString() {
			return this.name;
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int postion,
			long id) {

		LayoutInflater infalter = getLayoutInflater();

		View toastView = infalter.inflate(R.layout.custom_toast, null);

		TextView caption = (TextView) toastView.findViewById(R.id.txtToast);
		
		caption.setText(data.get(postion).getName());
	
		Toast toast = new Toast(this);
		
		toast.setView(toastView);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
		
	

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
