package com.example.virtickx;

import java.io.*;

import android.widget.ImageButton;
import android.widget.Button;
import java.util.UUID;
import android.os.Looper;


import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.widget.Toast;
import android.view.View;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.io.IOException;

import android.view.MotionEvent;

public class Joystick extends Activity implements SensorEventListener {
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private SensorManager mSensorManager;
	private Sensor mAcc;
	BluetoothAdapter ba=BluetoothAdapter.getDefaultAdapter();
	//local bluetooth adapter object created
	BluetoothDevice bd;																	//GLOBAL DECLARATION BLOCK
	BluetoothSocket bs;
	OutputStream os;
	InputStream in;
	private int PREVDELTHETA=0,PREVDELGAMMA=0,SensorStatus=0,BTSTATE=0,CNTACC=0;
	double sumx=0,sumy=0,sumz=0,AVGACCX=0,AVGACCY=0,AVGACCZ=0,msumx=0,msumy=0,AVGMAG=0,AVGGXZ=0,AVGGYZ=0,AVGMAGX=0,AVGMAGY=0,ERRORx=0,ERRORy=0;
	ImageButton btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14;
	Button b6,b5,b3,b4,b10,b12,b18,b16;
	int KEYPRESS1=0,KEYPRESS2=64,KEYPRESS3=128,KEYPRESS4=192,MouseReq=0,read_values=0;
	private static final UUID MY_UUID =UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		setContentView(R.layout.joystick);
		if(ba.isEnabled()==false)														//BLUETOOTH ADAPTER ENABLED IF WAS DISABLED
			ba.enable();
		final String address = getIntent().getStringExtra("address").trim();
		setResult(100,new Intent());
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn1=(ImageButton)findViewById(R.id.imageButton1);
		btn2=(ImageButton)findViewById(R.id.imageButton2);
		btn3=(ImageButton)findViewById(R.id.imageButton3);
		btn4=(ImageButton)findViewById(R.id.imageButton4);
		btn5=(ImageButton)findViewById(R.id.imageButton5);
		btn6=(ImageButton)findViewById(R.id.imageButton6);
		btn7=(ImageButton)findViewById(R.id.imageButton7);								//IMAGE BUTTON OBJECT INITIALIZATION
		btn8=(ImageButton)findViewById(R.id.imageButton8);
		btn9=(ImageButton)findViewById(R.id.imageButton9);
		btn10=(ImageButton)findViewById(R.id.imageButton10);
		btn11=(ImageButton)findViewById(R.id.imageButton11);
		btn12=(ImageButton)findViewById(R.id.imageButton12);
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		b3=(Button)findViewById(R.id.button3);
		b4=(Button)findViewById(R.id.button4);										//BUTTON OBJECT INITIALIZATION
		b5=(Button)findViewById(R.id.button5);
		b6=(Button)findViewById(R.id.button6);
		b12=(Button)findViewById(R.id.button12);
		b18=(Button)findViewById(R.id.button18);										//BUTTON OBJECT INITIALIZATION
		b16=(Button)findViewById(R.id.button16);
		b10=(Button)findViewById(R.id.button10);
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		bd=ba.getRemoteDevice(address);
		BluetoothConnect.start();
		//REMOTE DEVICE OBJECT CREATED
		/*try{Thread.sleep(5000);}
		catch(InterruptedException e){}*/
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	}
	@Override
	protected void onResume()
	{
		super.onResume();
	    mSensorManager.registerListener(this, mAcc, SensorManager.SENSOR_DELAY_GAME);
		//onTouch function for various buttons
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn1.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{
					KEYPRESS1=KEYPRESS1&(43);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{	KEYPRESS1=KEYPRESS1|(20);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn2.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{	
					KEYPRESS1=KEYPRESS1&(59);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{	
					KEYPRESS1=KEYPRESS1|(4);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
	            return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn3.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{	
					KEYPRESS1=KEYPRESS1&(55);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
					KEYPRESS1=KEYPRESS1|(8);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn4.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{	
					KEYPRESS1=KEYPRESS1&(47);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{	
					KEYPRESS1=KEYPRESS1|(16);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
	            return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn5.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{	
					KEYPRESS1=KEYPRESS1&(31);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{	
					KEYPRESS1=KEYPRESS1|(32);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
	            return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn10.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{
					KEYPRESS1=KEYPRESS1&(39);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
	            	KEYPRESS1=KEYPRESS1|(24);
	            	try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
	            }
	            return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn11.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if(((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))&&(BTSTATE==1))
				{
					KEYPRESS1=KEYPRESS1&(23);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
	            	KEYPRESS1=KEYPRESS1|40;
	            	try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
	            }
				return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn12.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{
					KEYPRESS1=KEYPRESS1&(27);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
	            	KEYPRESS1=KEYPRESS1|36;
	            	try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
	            }
				return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn6.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{
					KEYPRESS2=KEYPRESS2&(123);
					try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
	            	KEYPRESS2=KEYPRESS2|4;
	            	try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
	            }
	            return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn7.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{
					KEYPRESS2=KEYPRESS2&(119);
					try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
	            	KEYPRESS2=KEYPRESS2|8;
	            	try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
	            }
				return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn8.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{
					KEYPRESS2=KEYPRESS2&(111);
					try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
	            	KEYPRESS2=KEYPRESS2|(16);
	            	try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
	            }
				return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btn9.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{
					KEYPRESS2=KEYPRESS2&(95);
					try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
	            	KEYPRESS2=KEYPRESS2|32;
	            	try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
	            }
				return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		b10.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{
					KEYPRESS2=KEYPRESS2&(235);
					try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
	            	KEYPRESS2=KEYPRESS2|(84);
	            	try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
	            }
	            return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		b12.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{
					KEYPRESS2=KEYPRESS2&(219);
					try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
	            	KEYPRESS2=KEYPRESS2|100;
	            	try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
	            }
				return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		b18.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{
					KEYPRESS2=KEYPRESS2&(215);
					try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
	            	KEYPRESS2=KEYPRESS2|104;
	            	try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
	            }
				return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		b16.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{
					KEYPRESS2=KEYPRESS2&(231);
					try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
	            	KEYPRESS2=KEYPRESS2|(88);
	            	try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
	            }
				return true;
	        }
		});
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		b3.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{	
					KEYPRESS1=KEYPRESS1&(59);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{	
					KEYPRESS1=KEYPRESS1|(4);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				return true;
	        }
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		b4.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{	
					KEYPRESS1=KEYPRESS1&(55);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
					KEYPRESS1=KEYPRESS1|(8);
					try
					{os.write(KEYPRESS1);
						os.write(KEYPRESS1);}
					catch(IOException e){}
				}
				return true;
	        }
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		b5.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{
					KEYPRESS2=KEYPRESS2&(123);
					try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
	            	KEYPRESS2=KEYPRESS2|4;
	            	try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
	            }
	            return true;
	        }
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		b6.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if((((event.getAction() == (MotionEvent.ACTION_UP))||(event.getAction() == (MotionEvent.ACTION_POINTER_UP))||
	            		(event.getAction() == (MotionEvent.ACTION_CANCEL))||(event.getAction() == (MotionEvent.ACTION_OUTSIDE)))==true)&&(BTSTATE==1))
				{
					KEYPRESS2=KEYPRESS2&(119);
					try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
				}
				else if(((event.getAction() == (MotionEvent.ACTION_DOWN)==true))&&(BTSTATE==1))
				{
	            	KEYPRESS2=KEYPRESS2|8;
	            	try
					{os.write(KEYPRESS2);
						os.write(KEYPRESS2);}
					catch(IOException e){}
	            }
				return true;
	        }
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
	@Override
	  public final void onAccuracyChanged(Sensor sensor, int accuracy) {
	    // Do something here if sensor accuracy changes.
	  }
	@Override
	  public final void onSensorChanged(SensorEvent event) {
		  double x=event.values[0];
		  double y=event.values[1];
		  double z=event.values[2];
		  	if(20<=CNTACC&&CNTACC<70)
		  	{
			  sumx=sumx+x;
			  sumy=sumy+y;
			  sumz=sumz+z;
		  	}
		  	else if(CNTACC==70)
		  	{
			  AVGACCX=sumx/50;
			  AVGACCY=sumy/50;
			  AVGACCZ=sumz/50;
			  AVGGXZ=Math.sqrt(AVGACCX*AVGACCX+AVGACCZ*AVGACCZ);
			  AVGGYZ=Math.sqrt(AVGACCY*AVGACCY+AVGACCZ*AVGACCZ);
		  	}
		  	else if(BTSTATE==1&&CNTACC>70)
		  	{
			    if(Math.sqrt(z*z+x*x+y*y)>20)
			    {
			    	try {
						os.write(1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			    else
			    {
			  		double dotproxz=AVGACCX*x+AVGACCZ*z;
				  	double dotproyz=AVGACCY*y+AVGACCZ*z;
				  	double magxz=Math.sqrt(x*x+z*z);
				  	double magyz=Math.sqrt(y*y+z*z);
				  	int deltheta=(int)(((Math.acos(dotproxz/(AVGGXZ*magxz)))/Math.PI)*180);
				  	int delgamma=(int)(((Math.acos(dotproyz/(AVGGYZ*magyz)))/Math.PI)*180);
				  	int signxz=(AVGACCX*z-AVGACCZ*x)<0?1:-1;
				  	int signyz=(AVGACCY*z-AVGACCZ*y)<0?1:-1;
				  	if(signxz==1&&deltheta>10)
				  		KEYPRESS4=KEYPRESS4|32;
				  	else if(signxz==-1&&deltheta>10)
				   		KEYPRESS4=KEYPRESS4|16;
				  	else
				  		KEYPRESS4=KEYPRESS4&143;
				  	if(signyz==1&&delgamma>15)
				   		KEYPRESS4=KEYPRESS4|8;
				  	else if(signyz==-1&&delgamma>15)
				   		KEYPRESS4=KEYPRESS4|4;	
				  	else
					  	KEYPRESS4=KEYPRESS4&179;
				  	if(delgamma-10<0)
				  		delgamma=0;
				  	else
				  		delgamma=delgamma-10;
				  	if(deltheta-15<0)
				  		deltheta=0;
				  	else
				  		deltheta=deltheta-15;
				  	try{
				  		os.write(KEYPRESS4);
				  		os.write(deltheta);
				  		os.write(delgamma);
				  	}
				  	catch(IOException e){}
					PREVDELTHETA=deltheta;
					PREVDELGAMMA=delgamma;
			    }
		  }
		  CNTACC++;
	}


	  @Override
	  protected void onPause() {
	      super.onPause();
	  }
	  @Override
	  public void onStop()
		{
		  mSensorManager.unregisterListener(this);
		  if(BTSTATE==1)
		  {
			  try{os.write(255);
			  	Thread.sleep(100);
			  	os.write(255);
			  	Thread.sleep(100);
			  	os.write(255);
			  	Thread.sleep(100);
			  	os.write(255);
			  	os.close();
				bs.close();
			  }
			  catch(IOException e){} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
			super.onStop();
		}
	  Thread BluetoothConnect=new Thread(){
		  public void run()
		  {
			  Looper.prepare();
			  Toast.makeText(getApplicationContext(), "Scanning and connecting to pc", Toast.LENGTH_LONG).show();
			  int i=0,flag=0;
			  while(i<1000)
				{
					try{
						bs=bd.createRfcommSocketToServiceRecord(MY_UUID);
						bs.connect();
						os = bs.getOutputStream();
						in=bs.getInputStream();
						if(mAcc!=null)
							SensorStatus=SensorStatus|1;
						flag=1;
						BTSTATE=1;
						os.write(SensorStatus);
						MouseReq=in.read();
						break;
					}
					catch(IOException e){}
					i++;
				}
				if(flag==0)
				{	
					Toast.makeText(getApplicationContext(),"Unable to connect", Toast.LENGTH_SHORT).show();
					finish();
				}
				while(read_values!=255)
				{
					try{
					  read_values=in.read();
					}catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				BTSTATE=0;
				try{os.write(255);
				os.close();
				bs.close();
			  }
			  catch(IOException e){}
				finish();
			  
		  }
		  
	  };
}
