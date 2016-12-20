package com.pcg.sensordataservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Timestamp;
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
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class DataService extends Service implements SensorEventListener {
	
	private DataService service = this;
	private SensorManager sensorManager;
	private Sensor sensor;
	
	private MyBinder binder = new MyBinder();
	
	private Long startTime;
	
	private final String pathName = "/sdcard/SensorData/";
	private String fileName = "";
	private File file, path;
	private FileOutputStream fos;
	
	@Override
	public void onCreate() {
		Log.d("Func", "onCreate()");
		super.onCreate();
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
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
				startTime = System.currentTimeMillis();
				String s = Long.toString(startTime) + "\n";
				fos.write(s.getBytes());
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
			s = "0";
			break;
		case Sensor.TYPE_GYROSCOPE:
			s = "1";
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			s = "2";
			break;
		case Sensor.TYPE_GRAVITY:
			s = "3";
			break;
		}
		s += " " + Long.toString(event.timestamp / 1000000 - startTime);
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
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("Func", "onBind()");
		return binder;
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.d("Func", "onUnbind()");
		return super.onUnbind(intent);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	class MyBinder extends Binder {
		public void setSensors(boolean[] switches) {
			Log.d("Func", "setSensors()");
			for (int i=0; i<switches.length; ++i) 
				if (switches[i]) {
				switch(i) {
				case 0:
					sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
					break;
				case 1:
					sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
					break;
				case 2:
					sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
					break;
				case 3:
					sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
					break;
				default:
					break;
				}
				sensorManager.registerListener(service, sensor, SensorManager.SENSOR_DELAY_GAME);
			}
		}
	}

}
