package com.jahufar.test.sensor;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	SensorManager sensorManager = null;
	List<Sensor> sensors = null;
	ArrayList<String> sensorNames = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

		sensorNames = new ArrayList<String>();

		for (Sensor sensor : sensors) {
			sensorNames.add(sensor.getName());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, sensorNames);

		setListAdapter(adapter);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		// Toast.makeText(this, sensors.get(position).getName(),
		// Toast.LENGTH_SHORT).show();

		Intent i = new Intent(this, SensorReadingsActivity.class);

		Bundle bundle = new Bundle();
		bundle.putInt("sensor_type", sensors.get(position).getType());

		i.putExtras(bundle);

		startActivity(i);

	}

}