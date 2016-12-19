package com.pcg.sensordataservice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class DataService extends Service implements SensorEventListener {
	
	private SensorManager sensorManager;
	private Sensor accelerometer;
	
	private final String pathName = "/sdcard/SensorData/";
	private String fileName = "";
	
	private File file, path;
	private FileOutputStream fos;
	
	@Override
	public void onCreate() {
		Log.d("Func", "onCreate()");
		super.onCreate();
		
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		
		try {
			
			SimpleDateFormat format = new SimpleDateFormat("MM.dd HH_mm_ss");
			fileName =format.format(new Date()) + ".txt";
			path = new File(pathName);
			file = new File(pathName + fileName);
			Log.d("File", pathName + format.format(new Date()) + ".txt");
			if (!path.exists())
				path.mkdir();
			if (!file.exists())
				file.createNewFile();
				fos = new FileOutputStream(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		Notification.Builder builder = new Notification.Builder(this);
		builder.setContentTitle("SensorDataService");
		builder.setContentText("Recording " + pathName + fileName + "...");
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentIntent(pendingIntent);
		Notification notification = builder.build();
		startForeground(1, notification);
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("Func", "onStartCommand()");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.d("Func", "onDestory()");
		sensorManager.unregisterListener(this);
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}
	
	public void onSensorChanged(SensorEvent event) {
		String s = "";
		switch (event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			s = "TYPE_ACCELEROMETER";
			break;
		}
		s += " " + Long.toString(event.timestamp);
		s += " " + Integer.toString(event.accuracy);
		for (int i=0; i < event.values.length; ++i)	
			s += " " + Float.toString(event.values[i]);
		s += "\n";
		byte [] buffer = s.getBytes();
		try {
			fos.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

}
