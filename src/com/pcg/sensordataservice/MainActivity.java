package com.pcg.sensordataservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{
	
	private Button startService, stopService;
	private Button[] buttons = new Button[4];
	
	private boolean[] switches = new boolean[4];
	private DataService.MyBinder binder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startService = (Button) findViewById(R.id.startService);
		stopService = (Button) findViewById(R.id.stopService);
		buttons[0] = (Button) findViewById(R.id.Button0);
		buttons[1] = (Button) findViewById(R.id.Button1);
		buttons[2] = (Button) findViewById(R.id.Button2);
		buttons[3] = (Button) findViewById(R.id.Button3);
		
		startService.setOnClickListener(this);
		stopService.setOnClickListener(this);
		for (int i=0; i<switches.length; ++i) {
			switches[i] = true;
			buttons[i].setOnClickListener(this);
		}
	}
	
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d("Func", "onServiceConnected");
			binder = (DataService.MyBinder) service;
			binder.setSensors(switches);
		}
	};
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.startService:
			Intent startIntent = new Intent(this, DataService.class);
			startService(startIntent);
			Intent bindIntent = new Intent(this, DataService.class);
			bindService(bindIntent, connection, BIND_AUTO_CREATE);
			
			break;
		case R.id.stopService:
			unbindService(connection);
			Intent stopIntent = new Intent(this, DataService.class);
			stopService(stopIntent);
			break;
		case R.id.Button0:
			switches[0] ^= true;
			if (switches[0])
				buttons[0].setText("加速计：开");
			else
				buttons[0].setText("加速计：关");
			break;
		case R.id.Button1:
			switches[1] ^= true;
			if (switches[1])
				buttons[1].setText("陀螺仪：开");
			else
				buttons[1].setText("陀螺仪：关");
		case R.id.Button2:
			switches[2] ^= true;
			if (switches[2])
				buttons[2].setText("磁场：开");
			else
				buttons[2].setText("磁场：关");
		case R.id.Button3:
			switches[3] ^= true;
			if (switches[3])
				buttons[3].setText("重力：开");
			else
				buttons[3].setText("重力：关");
		default:
			break;
		}
	}
}
