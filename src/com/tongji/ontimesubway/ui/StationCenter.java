package com.tongji.ontimesubway.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.adapter.RealTimeShowAdapter;
import com.tongji.ontimesubway.base.BaseAppClient;
import com.tongji.ontimesubway.base.StationNote;
import com.tongji.ontimesubway.base.StationRoute;
import com.tongji.ontimesubway.network.NetAsynctask;
import com.tongji.ontimesubway.network.NetTask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StationCenter extends FragmentActivity{

	private ViewPager stationVP;
	private TextView[] titleView;
	protected Button titleLeft;
	protected Button titleRight;
	protected TextView titleCenter;
	private StationNote selectStationNote;
	
	//实时屏显的数据
	
	private OnClickListener listener=new OnClickListener(){

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			if(view==titleView[0])
			{
				selectVp(0);
				stationVP.setCurrentItem(0);
				return ;
			}
			else if(view==titleView[1])
			{
				selectVp(1);
				stationVP.setCurrentItem(1);
				return ;
			}
			else if(view==titleView[2])
			{
				selectVp(2);
				stationVP.setCurrentItem(2);
				return ;
			}
		}
		
	};
	
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int position)
        {
        	selectVp(position);
        	return;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {

        }

        @Override
        public void onPageScrollStateChanged(int arg0)
        {

        }
    };
	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_station_center);
		Intent intent=this.getIntent();
		int stationID=intent.getIntExtra("StationID", -1);
		if(stationID!=-1)
			selectStationNote=BaseAppClient.getStation(stationID);
		else 
			Toast.makeText(this, "程序出错了", Toast.LENGTH_LONG);
		//初始化title 
		this.setTitle();
		
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.station_center, menu);
		return true;
	}
	
	//
	public void init()
	{
		stationVP=(ViewPager)findViewById(R.id.stationCenterVP);
		viewPagerAdapter vpadapter=new viewPagerAdapter(getSupportFragmentManager());
		stationVP.setAdapter(vpadapter);
		stationVP.setOnPageChangeListener(mPageChangeListener);
		titleView=new TextView[3];
		titleView[0]=(TextView)findViewById(R.id.st_1);
		titleView[1]=(TextView)findViewById(R.id.st_2);
		titleView[2]=(TextView)findViewById(R.id.st_3);
		for(int i=0;i<3;i++)
			titleView[i].setOnClickListener(listener);
		selectVp(0);
		
		
		
	}
	
	public void selectVp(int position)
	{
		//更新title样式
		for(int i=0;i<3;i++)
		{
			titleView[i].setBackgroundResource(R.drawable.circularbuttonef);
		}
		titleView[position].setBackgroundResource(R.drawable.circularbuttonn);	
	}
	
	
	
	
	private class viewPagerAdapter extends FragmentPagerAdapter{

		public viewPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return MyFragment.create(position);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}
		
	}
	 public static class MyFragment extends Fragment{
		 private List<StationRoute> real_Time_list;
		 private RealTimeShowAdapter real_Time_adapter;
		 public ListView listview;
		 
		 //进度条变量
		 private FrameLayout progressBarLayout;
		 private boolean isLoading=false;
		 @Override
		 public void onCreate(Bundle savedInstanceState)
		 {
			 super.onCreate(savedInstanceState);
			 real_Time_list=new ArrayList<StationRoute>();
			 initData();
			 real_Time_adapter=new RealTimeShowAdapter(this.getActivity(),real_Time_list);
			 real_Time_adapter.startRunTime();			 
			 
			 //网络任务，获取列车到站时间
			 doStationTimeNet();
		 }
		 
		 	/**
			 * test data init
			 */
			public void initData()
			{
				StationRoute sr=new StationRoute("4号线",4,"曹杨路",32);
				sr.InitTime(120, 400, 600);
				this.real_Time_list.add(sr);
			}
		 	@Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                Bundle savedInstanceState)
	        {
		 		 Bundle b = getArguments();
		 		 int position =b.getInt("position");
		 		 View view;
		 		 if(position==0)
		 		 {
		 			 view= inflater.inflate(R.layout.stationcenter_real, null);
		 			 //初始化界面数值
		 			 listview=(ListView)view.findViewById(R.id.real_time_sr_listview);
		 			 listview.setAdapter(real_Time_adapter);
		 			 
		 				 progressBarLayout=(FrameLayout)view.findViewById(R.id.progressbarFrame);		 				 
		 			 
		 				 if(isLoading)
		 				 {
		 					 showBar();
		 				 }
		 				 else 
		 				 {
		 					 hideBar();
		 				 }
		 			 
		 			 showBar();
		 		 }
		 		 else if(position==1)
		 		 {
		 			 view=inflater.inflate(R.layout.stationcenter_map, null);
		 			 //init the view		 			 
		 		 }
		 		 else 
		 		 {
		 			 view= inflater.inflate(R.layout.stationcenter_end, null);
		 			 //init the view		 			 
		 		 }
				return view;
		 		
	        }
		 	public static MyFragment create(int position)
		 	{
		 		MyFragment f=new MyFragment();
		 		Bundle b = new Bundle();
	            b.putInt("position", position);
	            f.setArguments(b);
	            return f;
		 	}
		 	
		 	
		 	//获取列车到站时间的网络任务
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
					//将获取到的结果更新到real_Time_adapter
					
				}

				@Override
				public void onError(Exception e) {
					// TODO Auto-generated method stub
					
				}
				
			};
			
			public void doStationTimeNet()
			{
				HashMap<String, String> params=new HashMap<String,String>();
				params.put("name", "15021039623");
				params.put("pwd", "123");
				params.put("loginway", "1");
				BaseAppClient.doNetWork("http://www.dubianinfo.com/server/index.php/Usercontrol/login", params,2, nettask);
			}
			
			//显示进度条
			 public void showBar()
			 {
				 progressBarLayout.setVisibility(View.VISIBLE);
				 isLoading=true;
			 }
			 
			 public void hideBar()
			 {
				 progressBarLayout.setVisibility(View.GONE);
				 isLoading=false;
			 }
		 	
	 }

	 //始初化title
	 public void setTitle()
	 {
		 getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
		 this.titleLeft=(Button)findViewById(R.id.title_left);
		 this.titleCenter=(TextView)findViewById(R.id.title_center);
		 this.titleRight=(Button)findViewById(R.id.title_right);
		 this.titleLeft.setText(this.getString(R.string.back));
		 this.titleRight.setText(this.getString(R.string.title_left_collect));
		 if(selectStationNote!=null)
		 {
			 this.titleCenter.setText(selectStationNote.getName());
		 }
		 
	 }
	 
	 
	 
}
