package com.jahufar.allaboutlistviews;

import java.util.ArrayList;

import com.jahufar.allaboutlistviews.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivityV2 extends ListActivity {

	ArrayList<AddressBook> data;

	ArrayAdapter<AddressBook> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.listactivity);

		data = populate();

		adapter = new ArrayAdapter<AddressBook>(this,
				android.R.layout.simple_list_item_1, data);

		setListAdapter(adapter);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		getListAdapter();

	}

	private ArrayList<AddressBook> populate() {

		ArrayList<AddressBook> temp = new ArrayList<AddressBook>();

		for (int i = 0; i <= 20; i++) {

			AddressBook entry = new AddressBook();
			entry.setId(Integer.valueOf(i));
			entry.setName("Name: " + String.valueOf(i));
			entry.setPhone("Phone: " + String.valueOf(i));

			temp.add(entry);

			// temp.add("Item: " + String.valueOf(i));

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

}
