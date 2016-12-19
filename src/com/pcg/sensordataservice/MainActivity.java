package com.pcg.sensordataservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{
	
	private Button startService;
	private Button stopService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startService = (Button) findViewById(R.id.startService);
		stopService = (Button) findViewById(R.id.stopService);
		
		startService.setOnClickListener(this);
		stopService.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.startService:
			Intent startIntent = new Intent(this, DataService.class);
			startService(startIntent);
			break;
		case R.id.stopService:
			Intent stopIntent = new Intent(this, DataService.class);
			stopService(stopIntent);
			break;
		default:
			break;
		}
	}
}
