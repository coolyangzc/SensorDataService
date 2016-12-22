package com.pcg.sensordataservice;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{
	
	private Button startService, stopService;
	private Button[] buttons = new Button[6];
	
	private boolean[] switches = new boolean[6];
	private DataService.MyBinder binder;
	
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
	        Manifest.permission.READ_EXTERNAL_STORAGE,
	        Manifest.permission.WRITE_EXTERNAL_STORAGE
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		verifyStoragePermissions(this);
		
		startService = (Button) findViewById(R.id.startService);
		stopService = (Button) findViewById(R.id.stopService);
		buttons[0] = (Button) findViewById(R.id.Button0);
		buttons[1] = (Button) findViewById(R.id.Button1);
		buttons[2] = (Button) findViewById(R.id.Button2);
		buttons[3] = (Button) findViewById(R.id.Button3);
		buttons[4] = (Button) findViewById(R.id.Button4);
		buttons[5] = (Button) findViewById(R.id.Button5);
		
		startService.setOnClickListener(this);
		stopService.setOnClickListener(this);
		for (int i=0; i<switches.length; ++i) {
			switches[i] = true;
			buttons[i].setOnClickListener(this);
		}
	}
	
	public static void verifyStoragePermissions(Activity activity) {
		
		int permission = ActivityCompat.checkSelfPermission(activity, 
				Manifest.permission.WRITE_EXTERNAL_STORAGE);

	    if (permission != PackageManager.PERMISSION_GRANTED) {
			// We don't have permission so prompt the user
			ActivityCompat.requestPermissions(
					activity, 
					PERMISSIONS_STORAGE, 
					REQUEST_EXTERNAL_STORAGE);
			}
		}
	
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d("Func", "onServiceConnected()");
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
			try {
				unbindService(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Intent stopIntent = new Intent(this, DataService.class);
			stopService(stopIntent);
			break;
		case R.id.Button0:
			switches[0] ^= true;
			if (switches[0])
				buttons[0].setText("���ټƣ���");
			else
				buttons[0].setText("���ټƣ���");
			break;
		case R.id.Button1:
			switches[1] ^= true;
			if (switches[1])
				buttons[1].setText("�����ǣ���");
			else
				buttons[1].setText("�����ǣ���");
			break;
		case R.id.Button2:
			switches[2] ^= true;
			if (switches[2])
				buttons[2].setText("�ų�����");
			else
				buttons[2].setText("�ų�����");
			break;
		case R.id.Button3:
			switches[3] ^= true;
			if (switches[3])
				buttons[3].setText("��������");
			else
				buttons[3].setText("��������");
			break;
		case R.id.Button4:
			switches[4] ^= true;
			if (switches[4])
				buttons[4].setText("�ٽ����룺��");
			else
				buttons[4].setText("�ٽ����룺��");
			break;
		case R.id.Button5:
			switches[5] ^= true;
			if (switches[5])
				buttons[5].setText("�⴫��������");
			else
				buttons[5].setText("�⴫��������");
			break;
		default:
			break;
		}
	}
}
