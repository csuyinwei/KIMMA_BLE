package com.example.kimma_test_ui_hs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends ListActivity {

	private Button scan_bt;
    private ArrayList<HashMap<String, Object>> listItems;    //������֡�ͼƬ��Ϣ
    private SimpleAdapter listItemAdapter;           //������   
	//�������йص�
	private BluetoothManager mBtManager;
	private BluetoothAdapter mBtAdapter;	
	private Handler mHandler;
	//�洢����ɨ����,	key:name_address, value: iBeacon
	private Map<String,iBeacon> mapScanResult;
    @Override
    public void onCreate(Bundle icicle)   {
	    super.onCreate(icicle);
	    setContentView(R.layout.activity_main);
	 
	  //��ȡBluetoothAdapter
	    mBtManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
	    mBtAdapter = mBtManager.getAdapter();
  		
  		//��������Ƿ��
  		if(mBtManager ==null || !mBtAdapter.isEnabled()){
  			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
  			startActivityForResult(intent, 1);
  		}
	    
	    scan_bt = (Button)findViewById(R.id.scan_bt);
		mapScanResult = new HashMap<String,iBeacon>();
		mHandler = new Handler();
	    scan_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//��ɨ��
				initParam();
				//Ȼ���ʼ��ListView
				while(mapScanResult==null){}
	            System.out.println("initListView");
	            for(String dev:mapScanResult.keySet()){  
	            	System.out.println(dev);
	            	System.out.println(mapScanResult.get(dev).getRSSI());
	            }
	    		initListView();
	        }
		});    
    }
    private void initListView(){   
        listItems = new ArrayList<HashMap<String, Object>>();
        for(String dev:mapScanResult.keySet()){  
            HashMap<String, Object> map = new HashMap<String, Object>();   
            map.put("address", dev);    //��ַ
            map.put("rssi",mapScanResult.get(dev).getRSSI());
            map.put("ItemImage",R.drawable.bluetooth);   //ͼƬ   
            listItems.add(map);
        }
        //������������Item�Ͷ�̬�����Ӧ��Ԫ��   
        listItemAdapter = new SimpleAdapter(
        		this,
        		listItems,   // listItems����Դ    
                R.layout.list_item,  //ListItem��XML����ʵ��  
                new String[] {"address","rssi","ItemImage"},     //��̬������ImageItem��Ӧ������         
                new int[] {R.id.address,R.id.rssi, R.id.ItemImage}//list_item.xml�����ļ������һ��ImageView��ID,һ��TextView ��ID  
        );
        MainActivity.this.setListAdapter(listItemAdapter);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)  {
        // TODO Auto-generated method stub       
        String []Click_key= (String[])mapScanResult.keySet().toArray(new String[mapScanResult.keySet().size()]);
        String Click_Address = (String)(mapScanResult.get(Click_key[position]).getAddress());
        System.out.println(Click_Address);
        Intent intent = new Intent(MainActivity.this,ShowDataActivity.class);
        intent.putExtra("Click_Address", Click_Address);
        startActivity(intent);
    }  
	
	//��ʼ��ɨ�������Ĺ���
	public void initParam(){
	    // �豸SDK�汾����17��Build.VERSION_CODES.JELLY_BEAN_MR1����֧��BLE 4.0  
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
	    	System.out.println("SDK > 17");
	    	mBtManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
	    	mBtAdapter = mBtManager.getAdapter();
	    	if(!mBtAdapter.isEnabled())
	    	{	
	    		//�������δ�������������������ַ����ᵯ����ʾ�Ի���
	    		Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	    		startActivityForResult(enableIntent, 1);
	    	}
	    	startScan();
	    }
	}
	
	//��ʼɨ������
	@SuppressLint("NewApi") 
	public void startScan()  
	{  
		
	    if (mBtAdapter != null && mBtAdapter.isEnabled()) {  
	        // 5���ֹͣɨ�裬�Ͼ�ɨ�������豸�ȽϷѵ磬���ݶ�λ��ʱ�����е�����ֵ  
	        mHandler.postDelayed(new Runnable() { 
				@Override
	            public void run() {
	                mBtAdapter.stopLeScan(bltScanCallback);  
	            }
	        },500);
		        System.out.println("startLeScan:");

		        mBtAdapter.startLeScan(bltScanCallback); // ��ʼɨ��	        	

	    }
	}
	private LeScanCallback bltScanCallback = new BluetoothAdapter.LeScanCallback() {  
	    @Override  
	    public void onLeScan(final BluetoothDevice device, int rssi,byte[] scanRecord)
	    {   
	        iBeacon mBeacon = new iBeacon();
            String address = device.getAddress();   // ��ȡMac��ַ  
            String name = device.getName();         // ��ȡ�豸����  
            String key = name + "_" + address;
            mBeacon.setAddress(address);		
            mBeacon.setRSSI(rssi);
            if (!mapScanResult.containsKey(key)){
                mapScanResult.put(key, mBeacon);
            }
	    }
	};
}
