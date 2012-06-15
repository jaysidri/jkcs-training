package com.jahufar.test.sensor;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SensorReadingsActivity extends Activity implements
		SensorEventListener {
	SensorManager sensorManager = null;
	Sensor sensor = null;

	TextView txtX, txtY, txtZ, txtName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sensor_reading);

		txtX = (TextView) findViewById(R.id.txtX);
		txtY = (TextView) findViewById(R.id.txtY);
		txtZ = (TextView) findViewById(R.id.txtZ);
		txtName = (TextView) findViewById(R.id.txtName);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		Intent intent = getIntent();

		Bundle bundle = intent.getExtras();

		sensor = sensorManager.getDefaultSensor(bundle.getInt("sensor_type"));

		txtName.setText(sensor.getName());

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		sensorManager.registerListener(this, sensor,
				SensorManager.SENSOR_DELAY_UI);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		switch (sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
		case Sensor.TYPE_ORIENTATION:
		case Sensor.TYPE_MAGNETIC_FIELD:
			txtX.setText(String.valueOf(event.values[SensorManager.DATA_X]));
			txtY.setText(String.valueOf(event.values[SensorManager.DATA_Y]));
			txtZ.setText(String.valueOf(event.values[SensorManager.DATA_Z]));
			break;

		case Sensor.TYPE_LIGHT:
		case Sensor.TYPE_PROXIMITY:
			txtX.setText(String.valueOf(event.values[SensorManager.DATA_X]));
			break;

		default:
			sensorManager.unregisterListener(this);
			Toast.makeText(this, "Unhandled Sensor Event", Toast.LENGTH_SHORT)
					.show();

		}

	}
}
