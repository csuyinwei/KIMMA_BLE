package com.example.kimma_test_ui_hs;

import java.util.UUID;

import javax.security.auth.Destroyable;


import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "HandlerLeak", "ShowToast" })
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class ShowDataActivity extends ActionBarActivity {
	
	private Button date;
	private Button T_now;
	private Button T_All;
	private TextView title;
	private TextView IdLab;
	private TextView showId;
	private TextView showDate;
	private TextView show_T_now;
	private Tools tools;
	private String Test_str;
	private BluetoothGattService bluetoothGattService;
	private BluetoothGatt bluetoothGatt;
	private BluetoothGattCharacteristic characteristic;
	private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;
    private String Click_Address;
    UUID Service = UUID.fromString("0000FFF0-0000-1000-8000-00805F9B34FB");
	UUID Characteristic = UUID.fromString("0000FFF6-0000-1000-8000-00805F9B34FB");
	UUID Descriptor = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
	final UUID[] myUUID = {Service,Characteristic,Descriptor};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//����ȫ����ʾ
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_data);
		//��ʼ���ؼ�
		date = (Button)findViewById(R.id.date);
		date.setEnabled(false);
		T_now = (Button)findViewById(R.id.T_now);
		T_now.setEnabled(false);
		T_All = (Button)findViewById(R.id.T_All);
		T_All.setEnabled(false);
		title = (TextView)findViewById(R.id.title);
		title.setText("����Ƽ� ");
		IdLab = (TextView)findViewById(R.id.IdLab);
		IdLab.setText("�������к�");
		showId = (TextView)findViewById(R.id.showId);
		showDate = (TextView)findViewById(R.id.showDate);
		show_T_now = (TextView)findViewById(R.id.show_T_now);
		//��ʼ�������豸
		bluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();	
		//����MAC��ַ����
		Intent intent = getIntent();
		Click_Address = intent.getStringExtra("Click_Address");
		//��ʼ����
		bluetoothAdapter.startLeScan(leScanCallback);
		//��ѯ��ʷ�����¼�
		T_All.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//�������
				LinearLayout layout_1 = (LinearLayout)findViewById(R.id.linechart_1);
				LinearLayout layout_2 = (LinearLayout)findViewById(R.id.linechart_2);
				tools = new Tools(ShowDataActivity.this,layout_1,layout_2);
				tools.TemperautreForOneDay();
				tools.TemperautreForAll();
			}
		});
		
		date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				readCharacteristic(characteristic);
			}
		});
		
		T_now.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				byte[] data = {0x0A};
				writeCharacteristic(characteristic, data);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_data, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressWarnings("deprecation")
	@Override  
	    public boolean onKeyDown(int keyCode, KeyEvent event)  
	    {  
	        if (keyCode == KeyEvent.KEYCODE_BACK )  
	        {  
	            // �����˳��Ի���  
	            AlertDialog isExit = new AlertDialog.Builder(this).create();  
	            // ���öԻ������  
	            isExit.setTitle("ϵͳ��ʾ");  
	            // ���öԻ�����Ϣ  
	            isExit.setMessage("ȷ��Ҫ�˳���");  
	            // ���ѡ��ť��ע�����  
	            isExit.setButton("ȷ��", listener);  
	            isExit.setButton2("ȡ��", listener);  
	            // ��ʾ�Ի���  
	            isExit.show();  
	        }  
	        return false;  
	    }  
	 
	    /**�����Ի��������button����¼�*/  
	    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()  
	    {  
	        public void onClick(DialogInterface dialog, int which)  
	        {  
	            switch (which)  
	            {  
	            case AlertDialog.BUTTON_POSITIVE:// "ȷ��"��ť�˳�����  
	            	close();
	            	
	                finish();  
	                break;  
	            case AlertDialog.BUTTON_NEGATIVE:// "ȡ��"�ڶ�����ťȡ���Ի���  
	                break;  
	            default:  
	                break;  
	            }  
	        }  
	    };   
	    
	    @Override
	    public void onDestroy(){
	        super.onDestroy();
	        close();
	    }
	    
    private Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            if (msg.what == 0) {  
            	StratUI(); 	 
            } 
            if(msg.what == 1){
            	showDate.setText(Test_str);
            }
        }  
    };
	/*
	 * ���Ļص�����
	 * 
	 */
	private LeScanCallback leScanCallback = new LeScanCallback(){
		@Override
		public void onLeScan(final BluetoothDevice arg0, int arg1,byte[] arg2) {
			System.out.println("--->-1");
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
				    BluetoothDevice device = bluetoothAdapter.getRemoteDevice(Click_Address);
					bluetoothGatt = device.connectGatt(ShowDataActivity.this, false,bluetoothGattCallback );
				}
			});
		}
    };
	
	private BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
		@Override
	    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState)
	    {
	        if(newState == BluetoothProfile.STATE_CONNECTED)
	        {
	            System.out.println("onConnectionStateChange--1");
	            gatt.discoverServices();
	        }
	        else if(newState == BluetoothProfile.STATE_DISCONNECTED)
	        {
	            System.out.println("onConnectionStateChange--2");
	        }
	    }
			
			@Override
	    	public void onServicesDiscovered(BluetoothGatt gatt, int status){
				System.out.println("--->0");
	    		if(status == BluetoothGatt.GATT_SUCCESS){
					bluetoothGattService =bluetoothGatt.getService(myUUID[0]);
					characteristic = bluetoothGattService.getCharacteristic(myUUID[1]); 
					Message msg = new Message();  
		            msg.what = 0;  
		            handler.sendMessage(msg);
	    		}else{
	    			System.out.println("onServicesDiscovered received");  
	    		}
	    	}
			
			@Override
			public void onCharacteristicRead(BluetoothGatt gatt,BluetoothGattCharacteristic characteristic,int status){
					byte[] data = characteristic.getValue();
					Test_str = bytesToHexString(data);
					/**********����**********/
					Message msg = new Message();  
		            msg.what = 1;  
		            handler.sendMessage(msg);
		            System.out.println(Test_str);
					
			}
			
			@Override
			public void onCharacteristicWrite(BluetoothGatt gatt,BluetoothGattCharacteristic characteristic, int status){
				
			}
	};
	//���豸������
	public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (bluetoothAdapter == null || bluetoothGatt == null) {
            System.out.println( "BluetoothAdapter not initialized");
            return;
        }
        bluetoothGatt.readCharacteristic(characteristic);
     }
	//���豸д����
	@SuppressWarnings("static-access")
	public void writeCharacteristic(BluetoothGattCharacteristic characteristic,byte[] data) {
		characteristic.setValue(data);
		characteristic.setWriteType(characteristic.WRITE_TYPE_NO_RESPONSE);
        System.out.println("д������");
        bluetoothGatt.writeCharacteristic(characteristic);
     }
	//���ֽ�����ת��Ϊ�ַ���
	public static String bytesToHexString(byte[] src){   
	    StringBuilder stringBuilder = new StringBuilder("");   
	    if (src == null || src.length <= 0) {   
	        return null;   
	    }   
	    for (int i = 0; i < src.length; i++) {   
	        int v = src[i] & 0xFF;   
	        String hv = Integer.toHexString(v);   
	        if (hv.length() < 2) {   
	            stringBuilder.append(0);   
	        }   
	        stringBuilder.append(hv);   
	    }   
	    return stringBuilder.toString();   
	}
	
	//����UI����
	public void StratUI(){
		T_now.setEnabled(true);
		date.setEnabled(true);
		T_All.setEnabled(true);
	}
	
	
	//�Ͽ�����
	public void close() {  
	    if (bluetoothGatt == null) {  
	        return;  
	    }
	    bluetoothGatt.disconnect();
	    bluetoothGatt.close(); 
	    bluetoothGatt = null;  
	}  
	
	
}
