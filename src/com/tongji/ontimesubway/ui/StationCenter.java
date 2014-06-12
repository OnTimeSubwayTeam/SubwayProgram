package com.tongji.ontimesubway.ui;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.adapter.RealTimeShowAdapter;
import com.tongji.ontimesubway.base.BaseAppClient;
import com.tongji.ontimesubway.base.BaseUI;
import com.tongji.ontimesubway.base.StationNote;
import com.tongji.ontimesubway.base.StationRoute;
import com.tongji.ontimesubway.network.NetAsynctask;
import com.tongji.ontimesubway.network.NetTask;
import com.tongji.ontimesubway.view.DragImageView;
import com.tongji.ontimesubway.view.RouteSelectShow;
import com.tongji.ontimesubway.view.selectStationMap;
import com.tongji.ontimesubway.view.mViewPager;
import com.tongji.ontimesubway.view.selectStationMap.SelectEvent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StationCenter extends BaseUI{

	private RelativeLayout stationVP;
	private TextView[] titleView;
	protected Button titleLeft;
	protected Button titleRight;
	protected TextView titleCenter;
	private static StationNote selectStationNote;
	private Fragment showFragment;
	
	
	//实时屏显的数据
	
	@SuppressLint("NewApi")
	private OnClickListener listener=new OnClickListener(){

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			if(view==titleView[0])
			{
				selectVp(0);
				Display display = getWindowManager().getDefaultDisplay();
				showFragment=MyFragment.create(0);
				getFragmentManager().beginTransaction().replace(R.id.stationCenterVP, showFragment).commit();
				return ;
			}
			else if(view==titleView[1])
			{
				selectVp(1);
				Display display = getWindowManager().getDefaultDisplay();
				showFragment=MyFragment.create(1);
				getFragmentManager().beginTransaction().replace(R.id.stationCenterVP, showFragment).commit();
				return ;
			}
			else if(view==titleView[2])
			{
				selectVp(2);
				Display display = getWindowManager().getDefaultDisplay();
				showFragment=new selectStationMap(selectEvent);
				getFragmentManager().beginTransaction().replace(R.id.stationCenterVP, showFragment).commit();
				return ;
			}
			else if(view.getId()==R.id.title_left)
			{
				StationCenter.this.finish();
				return ;
			}
			else if(view.getId()==R.id.title_right)
			{
				if(BaseAppClient.isInCollectStation(selectStationNote.getStationID()))
				{
					//取消收藏
					BaseAppClient.removeCollectStation(selectStationNote.getStationID());
					((TextView)view).setText(getString(R.string.title_left_collect));
				}
				else 
				{
					//添加收藏
					BaseAppClient.addCollectStation(selectStationNote.getStationID());
					((TextView)view).setText(getString(R.string.cancelCollect));
				}
				//收藏事件
				return ;
			}
		}

		
		
	};
	
    
    //选择终点后的回调函数
    private selectStationMap.SelectEvent selectEvent=new selectStationMap.SelectEvent(){

		@SuppressLint("NewApi")
		@Override
		public void AfterGetStation(int StationID) {
			// TODO Auto-generated method stub
			Log.d("stationCenter",String.valueOf(StationID));
			Display display = getWindowManager().getDefaultDisplay();
			showFragment=new RouteSelectShow();
			getFragmentManager().beginTransaction().replace(R.id.stationCenterVP, showFragment).commit();
		}
    	
    };
    
	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_station_center);
		Intent intent=this.getIntent();
		int stationID=intent.getIntExtra("StationID", -1);
		if(stationID!=-1){
			selectStationNote=BaseAppClient.getStation(stationID);
			BaseAppClient.readStaion(stationID);
			//测试
			ArrayList<String> Mapurl=new ArrayList<String>();
			Mapurl.add("http://www.10333.com/upload/1326766103445.jpg");
			Mapurl.add("http://image.tianjimedia.com/uploadImages/2013/317/8JLNA0YJMZH0.jpg");
			selectStationNote.setMapURL(Mapurl);
		}
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
		stationVP=(RelativeLayout)findViewById(R.id.stationCenterVP);		
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
	
	
	
	
	
	 @SuppressLint({ "ValidFragment", "NewApi" })
	public static class MyFragment extends Fragment{
		private List<StationRoute> real_Time_list;
		 private RealTimeShowAdapter real_Time_adapter;
		 public ListView listview;
		 
		 //进度条变量
		 private FrameLayout progressBarLayout;
		 private boolean isLoading=false;
		 
		 //map界面的变量
		 private TextView b1,b2;
		 private DragImageView map;
		 private int loadImagePosition=-1;
		 @Override
		 public void onCreate(Bundle savedInstanceState)
		 {
			 super.onCreate(savedInstanceState);
			 real_Time_list=new ArrayList<StationRoute>();
			 initData();
			 real_Time_adapter=new RealTimeShowAdapter(this.getActivity(),real_Time_list);
			 real_Time_adapter.startRunTime();			 
			 
			 //网络任务，获取列车到站时间
			 //doStationTimeNet();
		 }
		 
		 	/**
			 * test data init
			 */
			public void initData()
			{
				StationRoute sr=new StationRoute("4号线",4,6,32);
				sr.InitTime(120, 400, 600);
				this.real_Time_list.add(sr);
			}
		 	@SuppressLint("NewApi")
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
		 			//控制imageView的显示，使其可以恢复以前的那张图
		 			 map =(DragImageView)view.findViewById(R.id.stationCenter_map_image);
		 			  b1=(TextView)view.findViewById(R.id.stationCenter_map_b1);
		 			  b2=(TextView)view.findViewById(R.id.stationCenter_map_b2);
		 			 if(selectStationNote.getMapURL()==null)
		 			 {
		 				 b1.setVisibility(View.GONE);
		 			 }
		 			 else if(selectStationNote.getMapURL().size()==1)
		 			 {
		 				 RelativeLayout layout=(RelativeLayout)view.findViewById(R.id.stationCenter_map_layout);
		 				 layout.bringChildToFront(b1);
		 				 if(loadImagePosition>=0)
		 				 {
		 					BaseAppClient.imageCache.getImage(map, selectStationNote.getMapURL().get(loadImagePosition));
		 				 }
		 				 else {
		 					 BaseAppClient.imageCache.getImage(map, selectStationNote.getMapURL().get(0));
		 					 loadImagePosition=0;
		 				 }
		 				 //b1.setOnClickListener(floorButtonlistener);
		 			 }
		 			 else if(selectStationNote.getMapURL().size()>=2)
		 			 {
		 				 RelativeLayout layout=(RelativeLayout)view.findViewById(R.id.stationCenter_map_layout);
		 				 layout.bringChildToFront(b1);
		 				 layout.bringChildToFront(b2);
		 				 b1.setOnClickListener(floorButtonlistener);
		 				 b2.setOnClickListener(floorButtonlistener);
		 				if(loadImagePosition>=0)
		 				 {
		 					BaseAppClient.imageCache.getImage(map, selectStationNote.getMapURL().get(loadImagePosition));
		 				 }
		 				else {
		 				 BaseAppClient.imageCache.getImage(map, selectStationNote.getMapURL().get(0));
		 				loadImagePosition=0;
		 				}
		 			 }
		 			 
		 			 
		 			 
		 			WindowManager manager = this.getActivity().getWindowManager();
			        int window_width = manager.getDefaultDisplay().getWidth();
					int window_height = manager.getDefaultDisplay().getHeight();
		 			map.setScreen_H(window_height);
		 			map.setScreen_W(window_width);
		 			//map.setMax_W_H(window_width*2, window_height*2);
		 			map.setmActivity(getActivity());
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
					JSONObject resultJson=null;
					try {
						resultJson=new JSONObject(result);
						JSONArray resultArray=resultJson.getJSONArray("output");
						if(resultArray!=null)
						{
							
							ArrayList<String> Mapurl=new ArrayList<String>();
							for(int i=0;i<resultJson.length();i++)
							{
								JSONObject route=(JSONObject)resultArray.get(i);
								if(route!=null)
								{
									int routeID=route.getInt("myStationRoute");
									int goalStationID=route.getInt("myGoalStation");
									int firstTime=route.getInt("firstTime");
									int secondTime=route.getInt("secondTime");
									int thirdTime=route.getInt("thirdTime");
									String mapURL=route.getString("picturePath");
									StationRoute sr=new StationRoute(BaseAppClient.getRoute(routeID).getName(),
											routeID,goalStationID,selectStationNote.getStationID());
									sr.InitTime(firstTime, secondTime, thirdTime);
									real_Time_adapter.update(sr);
									Mapurl.add(mapURL);
									
								}
							}
							selectStationNote.setMapURL(Mapurl);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
			 
			 //mapCenter 监听b1与b2
		 	private OnClickListener floorButtonlistener=new OnClickListener(){

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					switch(view.getId())
					{
					case R.id.stationCenter_map_b1:
						view.setBackgroundResource(R.drawable.circle_press);
						b2.setBackgroundResource(R.drawable.circle_normal);
						BaseAppClient.imageCache.getImage(map, selectStationNote.getMapURL().get(0));
						loadImagePosition=0;
						break;
					case R.id.stationCenter_map_b2:
						b2.setBackgroundResource(R.drawable.circle_press);
						b1.setBackgroundResource(R.drawable.circle_normal);
						BaseAppClient.imageCache.getImage(map, selectStationNote.getMapURL().get(1));
						loadImagePosition=1;
						break;
					}
				}
		 		
		 	};
	 }

	 //始初化title
	 public void setTitle()
	 {
		 getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
		 this.titleLeft=(Button)findViewById(R.id.title_left);
		 this.titleCenter=(TextView)findViewById(R.id.title_center);
		 this.titleRight=(Button)findViewById(R.id.title_right);
		 this.titleLeft.setText(this.getString(R.string.back));
		 if(BaseAppClient.isInCollectStation(selectStationNote.getStationID()))
			 this.titleRight.setText(this.getString(R.string.cancelCollect));
		 else 
		 	this.titleRight.setText(this.getString(R.string.title_left_collect));
		 this.titleLeft.setOnClickListener(listener);
		 this.titleRight.setOnClickListener(listener);
		 if(selectStationNote!=null)
		 {
			 this.titleCenter.setText(selectStationNote.getName());
		 }
		 
	 }

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}
	 
	 
}
