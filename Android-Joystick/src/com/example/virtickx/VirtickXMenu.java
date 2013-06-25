package com.example.virtickx;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.graphics.Color;

public class VirtickXMenu extends Activity {

	String address;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		Intent i = new Intent();
		setResult(100,i);
		address = getIntent().getStringExtra("address");
		final Button btnJoystick=(Button)findViewById(R.id.button1);
		btnJoystick.setOnClickListener(new View.OnClickListener() {
				 
	            public void onClick(View arg0) {
	            	btnJoystick.setBackgroundColor(Color.parseColor("#FFA800"));
	            	Intent intent = new Intent(getApplicationContext(), Orientation.class);
		        	intent.putExtra("address",address);
	            	startActivity(intent);
	            	btnJoystick.setBackgroundColor(Color.parseColor("#000000"));
	 
	            }
	        });
		final Button btnChangeDevice=(Button)findViewById(R.id.button2);
		btnChangeDevice.setOnClickListener(new View.OnClickListener() {
				 
	            public void onClick(View arg0) {
	            	btnChangeDevice.setBackgroundColor(Color.parseColor("#FFA800"));
	            	Intent intent = new Intent(getApplicationContext(), BluetoothScan.class);
	            	startActivityForResult(intent,100); 
	 
	            }
	        });
		 
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			 super.onActivityResult(requestCode, resultCode, data);
			 if(requestCode==100)
				 finish();
			 return;
	}

}
