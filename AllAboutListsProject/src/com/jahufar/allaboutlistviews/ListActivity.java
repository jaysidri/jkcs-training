package com.jahufar.allaboutlistviews;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends Activity implements OnItemClickListener  {

	ListView list;

	ArrayList<AddressBook> data;

	ArrayAdapter<AddressBook> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		list = (ListView) findViewById(R.id.list);

		data = populate();
		
		

		adapter = new ArrayAdapter<AddressBook>(this,
				android.R.layout.simple_list_item_1, data);

		list.setAdapter(adapter);

		list.setOnItemClickListener(this);
		
		TextView txtNoItems = (TextView) findViewById(R.id.txtNoItems);
		
		list.setEmptyView(txtNoItems);
		
		
	}

	private ArrayList<AddressBook> populate() {

		ArrayList<AddressBook> temp = new ArrayList<AddressBook>();
		
	
		for (int i = 0; i <= 20; i++) {
			
			AddressBook entry =  new AddressBook();
			entry.setId(Integer.valueOf(i));
			entry.setName("Name: " + String.valueOf(i));
			entry.setPhone("Phone: " + String.valueOf(i));
			
			temp.add(entry);

			//temp.add("Item: " + String.valueOf(i));

		}

		return temp;
		

	}


	
	 @Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Toast.makeText(this, "You clicked: " + data.get(position),
				Toast.LENGTH_SHORT).show();
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


}