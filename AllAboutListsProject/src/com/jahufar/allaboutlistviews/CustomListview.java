package com.jahufar.allaboutlistviews;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListview extends ListActivity {

	ArrayList<AddressBook> data;

	MyCustomAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		data = populate();

		// adapter = new ArrayAdapter<AddressBook>(this, R.layout.custom_row,
		// data);
		adapter = new MyCustomAdapter(this, R.layout.custom_row, data);

		setListAdapter(adapter);

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

	class MyCustomAdapter extends ArrayAdapter<AddressBook> {

		private Context context;
		private ArrayList<AddressBook> data;

		public MyCustomAdapter(Context context, int textViewResourceId,
				ArrayList<AddressBook> data) {
			super(context, textViewResourceId, data);

			this.context = context;
			this.data = data;
		}
		
		

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view;
			ViewHolder viewHolder;

			if (null == convertView) {
				
				LayoutInflater inflator = (LayoutInflater) context
						.getSystemService(LAYOUT_INFLATER_SERVICE);
				view = inflator.inflate(R.layout.custom_row, null);
				
				viewHolder = new ViewHolder(view);
				view.setTag(viewHolder);
					

			} else {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
								
			}
			
						
			viewHolder.name.setText(data.get(position).getName());
			viewHolder.phone.setText(data.get(position).getPhone());

			return view;

		}
	}

	class ViewHolder {
		ImageView profile;
		TextView name;
		TextView phone;

		ViewHolder(View view) {
			name = (TextView) view.findViewById(R.id.txtName);
			phone = (TextView) view.findViewById(R.id.txtPhone);

		}

	}

}
