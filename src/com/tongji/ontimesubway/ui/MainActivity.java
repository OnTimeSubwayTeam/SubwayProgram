package com.tongji.ontimesubway.ui;

import java.util.HashMap;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.R.layout;
import com.tongji.ontimesubway.R.menu;
import com.tongji.ontimesubway.base.BaseAppClient;
import com.tongji.ontimesubway.base.BaseUI;
import com.tongji.ontimesubway.network.NetAsynctask;
import com.tongji.ontimesubway.network.NetTask;
import com.tongji.ontimesubway.view.selectStationMap;
import com.tongji.ontimesubway.view.subwayMap;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends BaseUI {

	private OnClickListener listener;
	private Button MapButton;
	private selectStationMap selectStation=null;
	private subwayMap subwayMap=null;
	private final Integer SELECT_STATION_TAG = 1;
	private final Integer ROUTE_MAP_TAG		 = 2;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//设置监听器
		mainListener();
		//自定义titlebar
		this.setTitleBar();
		
		///上传测试
		//System.out.println("12341241");
		MapButton=(Button)findViewById(R.id.reminder_button);
		MapButton.setOnClickListener(listener);
		MapButton.setTag(this.SELECT_STATION_TAG);
		//将BUTTON置前
		RelativeLayout layout=(RelativeLayout)findViewById(R.id.main_relativeLayout);
		layout.bringChildToFront(MapButton);
		
		Display display = getWindowManager().getDefaultDisplay();
		selectStation=new selectStationMap();
		getFragmentManager().beginTransaction().replace(R.id.stationMapfragment, selectStation).commit();
		
	}

	public void doNetTask(){
		HashMap params=new HashMap<String,String>();
		params.put("name", "15021039623");
		params.put("pwd", "123");
		params.put("loginway", "1");
		BaseAppClient.doNetWork("http://www.dubianinfo.com/server/index.php/Usercontrol/login", params,2, nettask);
		
	}
	private NetAsynctask preTask=null;
	private NetTask nettask=new NetTask(){

		@Override
		public void onPreExecute(NetAsynctask task) {
			// TODO Auto-generated method stub
			//取消之前的任务
			if(preTask!=task&&preTask!=null){
				preTask.Cancel();
				preTask=task;
			}
			else 
				preTask=task;
			
		}

		@Override
		public void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Log.d("main netresult",result);
		}

		@Override
		public void onError(Exception e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void setTitleBar()
	{
		super.setTitleBar();
		this.titleCenter.setText(this.getString(R.string.title_center_app));
		this.titleLeft.setOnClickListener(listener);
		this.titleLeft.setText(this.getString(R.string.title_center_collect));
		this.titleRight.setText(this.getString(R.string.title_right_reminder));
		this.titleRight.setOnClickListener(listener);
	}
	public void  mainListener()
	{
		listener=new OnClickListener(){

			@SuppressLint("NewApi")
			@Override
			public void onClick(View clickView) {
				// TODO Auto-generated method stub
				switch(clickView.getId()){
				case R.id.title_left:
					Intent collectIntent=new Intent();
					collectIntent.setClass(MainActivity.this,CollectCenterActivity.class);
					startActivity(collectIntent);
					//MainActivity.this.finish();
					break;
				case R.id.title_right:
					//跳转到提醒页面
					Intent remind = new Intent();
					remind.setClass(MainActivity.this, RemindCenter.class);
					startActivity(remind);
					break;
				case R.id.reminder_button:
					if(((Integer)clickView.getTag())==SELECT_STATION_TAG){
						clickView.setTag(ROUTE_MAP_TAG);
						if(subwayMap==null)
						{
							subwayMap=new subwayMap();
						}
						MainActivity.this.getFragmentManager().beginTransaction().replace(R.id.stationMapfragment, subwayMap).commit();
					}
					else{
						clickView.setTag(SELECT_STATION_TAG);
						if(selectStation==null)	
							selectStation=new selectStationMap();
						getFragmentManager().beginTransaction().replace(R.id.stationMapfragment, selectStation).commit();
					}						
					break;
				
				}
			}	
		};
		return ;
	}

}
