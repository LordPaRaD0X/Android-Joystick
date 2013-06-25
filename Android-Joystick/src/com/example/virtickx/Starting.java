package com.example.virtickx;

import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.app.Activity;
import android.widget.Toast;

import java.lang.Thread;
import java.lang.InterruptedException;
import android.content.Intent;

public class Starting extends Activity {

	BluetoothAdapter ba=BluetoothAdapter.getDefaultAdapter();
	protected final int REQUEST_ENABLE_BT=1;
	protected boolean BTSTATE=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.starting);
		if(ba==null)
		{
			Toast.makeText(getApplicationContext(), "BLUETOOTH service is NOT AVAILABLE", Toast.LENGTH_SHORT).show();
            finish();
            return;
		}
		Thread CountDown=new Thread()
		{
			public void run()
			{
				while(BTSTATE==false);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent i=new Intent(getApplicationContext(),BluetoothScan.class);
	        	startActivityForResult(i,100);
				
			}
		};
		CountDown.start();
		if (!ba.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		else
			BTSTATE=true;
		
	}
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			 super.onActivityResult(requestCode, resultCode, data);
			 if(requestCode==REQUEST_ENABLE_BT)
			 {
				 if(resultCode==Activity.RESULT_CANCELED){
					 if(!ba.isEnabled()){
					 	Toast.makeText(getApplicationContext(), "BLUETOOTH service is NOT AVAILABLE \n  APPLICATION TERMINATING", Toast.LENGTH_SHORT).show();
					 	finish();
					 }
				 }
				 else
					 BTSTATE=true;
			 }
			 if(requestCode==100)
				 finish();
			 return;
	     }
}
