package com.example.virtickx;

import android.widget.ArrayAdapter;
import 	android.widget.ListView;
import android.graphics.Color;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import com.example.virtickx.R;


public class BluetoothScan extends Activity {
	
	protected final int REQUEST_ENABLE_BT=1;
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	private ArrayAdapter<String> PairedDevices;
	BluetoothAdapter ba=BluetoothAdapter.getDefaultAdapter();
	ListView PairedDevicesFound;
	ListView ScannedDevicesFound;
	protected final BroadcastReceiver mReceiver = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        final String action = intent.getAction();
	        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
	            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
	                                                 BluetoothAdapter.ERROR);
	            if(state==BluetoothAdapter.STATE_OFF)
	               ba.enable();
	        }   
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	                // Get the BluetoothDevice object from the Intent
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	                // If it's already paired, skip it, because it's been listed already
	            if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
	                    PairedDevices.add(device.getName() + "\n" + device.getAddress());
	                    PairedDevices.notifyDataSetChanged();
	             }
	            // When discovery is finished, change the Activity title
	         } 
	         else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
	        	 Button scanButton = (Button) findViewById(R.id.button_scan);
	        	 scanButton.setText("Scan for more devices");
	        	 if (PairedDevices.getCount() == 0) {
	            	 PairedDevices.add("No Devices Available");
	                 PairedDevices.notifyDataSetChanged();
	             }
	        }
	    }
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetoothscan);
		IntentFilter BluetoothState = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
	    this.registerReceiver(mReceiver, BluetoothState);
		Intent i = new Intent();
		setResult(100,i);
		PairedDevicesFound = (ListView)findViewById(R.id.paired_devices);
		PairedDevicesFound.setOnItemClickListener(mDeviceClickListener);
		PairedDevices=new ArrayAdapter<String>(BluetoothScan.this ,R.layout.forlist);
		PairedDevicesFound.setAdapter(PairedDevices);
		
		Set<BluetoothDevice> pairedDevices = ba.getBondedDevices();
		IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);
		if (pairedDevices.size() > 0) {
	            for (BluetoothDevice device : pairedDevices) {
	            	PairedDevices.add(device.getName() + "\n" + device.getAddress());
	            	PairedDevices.notifyDataSetChanged();
	            }
		 }
		 final Button scanButton = (Button) findViewById(R.id.button_scan);
	        scanButton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	            	PairedDevices.clear();
	            	if(ba.isDiscovering()==true)
	                	ba.cancelDiscovery();
	                Set<BluetoothDevice> pairedDevices = ba.getBondedDevices();
	                if (pairedDevices.size() > 0) {
	    	            for (BluetoothDevice device : pairedDevices) {
	    	            	PairedDevices.add(device.getName() + "\n" + device.getAddress());
	    	            	PairedDevices.notifyDataSetChanged();
	    	            }
	    		 }
	                Button scanButton = (Button) findViewById(R.id.button_scan);
	                scanButton.setText("Scanning......");
	                // Turn on sub-title for new devices
	                try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                //scanButton.setBackgroundColor(Color.parseColor("#2695CE"));
	                while(!ba.startDiscovery());	
	            }
	        });

		// Binding resources Array to ListAdapter
        //this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, adobe_products));

	}
	 private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
	        	//findViewById(R.id.no_new_devices).setVisibility(View.GONE);
	        	v.setBackgroundColor(Color.parseColor("#FFA800"));
	        	if(ba.isDiscovering()==true)
                	ba.cancelDiscovery();
	        	String info = ((TextView) v).getText().toString();
	        	if(info.equalsIgnoreCase("No Devices Available")==false)
	        	{
	        		Intent intent = new Intent(getApplicationContext(), VirtickXMenu.class);
	        		String address = info.substring(info.length() - 17);
	        		intent.putExtra("address",address);
	        		startActivityForResult(intent,100);
	        	}	
	        }
	    };
	    // Initialize the button to perform device discovery
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
			 }
			 if(requestCode==100)
				 finish();
			 return;
	     }
	@Override
	protected void onPause()
	{
		if(ba.isDiscovering()==true)
        	ba.cancelDiscovery();
		(BluetoothScan.this).unregisterReceiver(mReceiver);
		super.onPause();
		
	}
}