package com.tongji.ontimesubway.view;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.base.CollectRoute;
import com.tongji.ontimesubway.network.NetAsynctask;
import com.tongji.ontimesubway.network.NetTask;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


@SuppressLint("NewApi")
public class RouteSelectShow extends Fragment{
	
	private Context context;
	private LinearLayout layout; 
	//测试数据
	private ArrayList<Integer> group;
	private ArrayList<CollectRoute> CollectrouteList;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		initData();
	}
	public void initData()
	{
		group=new ArrayList<Integer>();
		group.add(1);
		group.add(2);
		group.add(3);
		group.add(4);
		
		CollectrouteList=new ArrayList<CollectRoute>();
		CollectRoute route1=new CollectRoute(group,5,60*20+35);
		CollectrouteList.add(route1);
		ArrayList<Integer> group2=new ArrayList<Integer>();
		group2.add(1);
		group2.add(2);
		group2.add(3);
		CollectRoute route2=new CollectRoute(group2,3,60*5+40);
		CollectrouteList.add(route2);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		View view=inflater.inflate(R.layout.stationcenter_end_after,container,false);
		context=inflater.getContext();
		layout=(LinearLayout)view.findViewById(R.id.route_show_layout);
		initRouteShow();
		return view;
	}
	
	public void initRouteShow()
	{
		layout.removeAllViews();
		for(int i=0;i<CollectrouteList.size();i++)
		{
			Log.d("init",String.valueOf(i));
			RouteShowUnit routeShow=new RouteShowUnit(context);
			routeShow.setCollectRoute(CollectrouteList.get(i), i+1);
			layout.addView(routeShow);
		}	
		
		layout.invalidate();
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
			//将获取到的结果更新到real_Time_adapter
			JSONObject resultJson=null;
			try {
				resultJson=new JSONObject(result);
				//JSONArray resultArray=resultJson.getJSONArray("output");
				if(resultJson!=null)
				{
					int time=resultJson.getInt("timeCost");
					int price=resultJson.getInt("price");
					JSONArray jsonList=resultJson.getJSONArray("path");
					ArrayList<Integer>stationList=new ArrayList<Integer>();
					for(int i=0;i<jsonList.length();i++)
					{
						stationList.add(jsonList.getInt(i));
					}
					CollectRoute route=new CollectRoute(stationList,price,time);
					CollectrouteList.add(route);
					
					//更新界面
					initRouteShow();
				}
			}
			catch(JSONException e)
			{
				
			}
		}

		@Override
		public void onError(Exception e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
}
