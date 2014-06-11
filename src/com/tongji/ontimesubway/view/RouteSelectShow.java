package com.tongji.ontimesubway.view;

import java.util.ArrayList;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.base.CollectRoute;

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
	//≤‚ ‘ ˝æ›
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
		for(int i=0;i<CollectrouteList.size();i++)
		{
			Log.d("init",String.valueOf(i));
			RouteShowUnit routeShow=new RouteShowUnit(context);
			routeShow.setCollectRoute(CollectrouteList.get(i), i+1);
			layout.addView(routeShow);
		}	
		
		layout.invalidate();
	}
	
	
}
