package com.example.virtickx;

import android.os.Bundle;
import android.widget.ImageButton;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.content.Intent;

public class Orientation extends Activity {

    private ImageButton start;
    double sumx=0,sumy=0,sumz=0,AVGACCX=0,AVGACCY=0,AVGACCZ=0,msumx=0,msumy=0,AVGMAG=0,AVGGXZ=0,AVGGYZ=0,AVGMAGX=0,AVGMAGY=0,MAXx=-9999999,MINx=9999999,MAXy=0,MINy=0,ERRORx=0,ERRORy=0;
	protected int processStatus=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orientation);
		final String address = getIntent().getStringExtra("address");
	    start=(ImageButton)findViewById(R.id.start);
	    start.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((event.getAction() == (MotionEvent.ACTION_DOWN)))
				{
					Intent intent = new Intent(getApplicationContext(), Joystick.class);
		        	intent.putExtra("address",address);
	            	startActivity(intent);
	            	finish();
				}
				return true;
	        }
		});
	}
}
