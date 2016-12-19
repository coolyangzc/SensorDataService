package com.pcg.sensordataservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class DataService extends Service {

	@Override
	public void onCreate() {
		Log.d("Func", "onCreate()");
		super.onCreate();
		
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		
		Notification.Builder builder = new Notification.Builder(this);
		builder.setContentTitle("SensorDataService");
		builder.setContentText("Running...");
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentIntent(pendingIntent);
		
		Notification notification = builder.getNotification();
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
		super.onDestroy();
	}
	
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
