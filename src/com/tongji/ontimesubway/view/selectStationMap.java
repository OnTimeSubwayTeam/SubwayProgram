package com.tongji.ontimesubway.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.adapter.StationViewAdapter;
import com.tongji.ontimesubway.base.BaseAppClient;
import com.tongji.ontimesubway.base.StationNote;
import com.tongji.ontimesubway.ui.StationCenter;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "NewApi", "ValidFragment" })
public class selectStationMap extends Fragment{
	private LineButton[] RouteGroupButton=new LineButton[11];
	private RelativeLayout layout;
	private TextView stationInfo;
	private Button selectOK;
	private ListView listview;
	private StationViewAdapter stationAdapter;
	private int lastSelectPosition=-1;
	private TextView routeName;
	private SelectEvent selectEvent=new SelectEvent(){

		@Override
		public void AfterGetStation(int StationID) {
			// TODO Auto-generated method stub
			
		}
		
	};
	public selectStationMap(SelectEvent event)
	{
		this.selectEvent=event;
	}
	public void setSelectEvent(SelectEvent event)
	{
		this.selectEvent=event;
	}
	@Override  
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 
		super.onCreateView(inflater, container, savedInstanceState);
		View view=inflater.inflate(R.layout.selectstation,container,false);
		routeName=(TextView)view.findViewById(R.id.route_name);
		RouteGroupButton[0]=(LineButton)view.findViewById(R.id.Route_1);
		RouteGroupButton[1]=(LineButton)view.findViewById(R.id.Route_2);
		RouteGroupButton[2]=(LineButton)view.findViewById(R.id.Route_3);
		RouteGroupButton[3]=(LineButton)view.findViewById(R.id.Route_4);
		RouteGroupButton[4]=(LineButton)view.findViewById(R.id.Route_5);
		RouteGroupButton[5]=(LineButton)view.findViewById(R.id.Route_6);
		RouteGroupButton[6]=(LineButton)view.findViewById(R.id.Route_7);
		RouteGroupButton[7]=(LineButton)view.findViewById(R.id.Route_8);
		RouteGroupButton[8]=(LineButton)view.findViewById(R.id.Route_9);
		RouteGroupButton[9]=(LineButton)view.findViewById(R.id.Route_10);
		RouteGroupButton[10]=(LineButton)view.findViewById(R.id.Route_11);
		
		
		for(int i=0;i<RouteGroupButton.length;i++)
		{
			RouteGroupButton[i].setOnClickListener(listener);
			RouteGroupButton[i].setLineNumber(i+1);
			RouteGroupButton[i].setTag(i);
		}
		listview=(ListView)view.findViewById(R.id.route_station_list);
		listview.setOnItemClickListener(itemListener);
		if(stationAdapter==null)
		{
			stationAdapter=new StationViewAdapter(BaseAppClient.getRoute(1).getStationGroup(),inflater.getContext(),1);
			listview.setAdapter(stationAdapter);		
		}
		else 
			listview.setAdapter(stationAdapter);
		layout=(RelativeLayout)view.findViewById(R.id.stationinfo_layout);
		layout.setVisibility(View.INVISIBLE);
		stationInfo=(TextView)view.findViewById(R.id.stationInfo);
		selectOK=(Button)view.findViewById(R.id.selectOK);
		selectOK.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				//跳转，并且去除bitmap;
				Jump(lastSelectPosition);
			}
			
		});
		return view;
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	private OnClickListener listener=new OnClickListener(){

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			int position=(Integer)view.getTag();
			//Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_LONG);
			Log.d("selectStationMap",String.valueOf(position));
			stationAdapter.Update(BaseAppClient.getRoute(position+1).getStationGroup(),position+1); 
			routeName.setText(String.valueOf(position+1)+"号线");
			layout.setVisibility(View.INVISIBLE);
		}
		
	};
	private OnItemClickListener itemListener=new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long arg3) {
			// TODO Auto-generated method stub
			//Log.d("selectStationMap",String.valueOf(position));
			if(lastSelectPosition!=stationAdapter.getStationId(position)){
				lastSelectPosition=stationAdapter.getStationId(position);
				layout.setVisibility(View.VISIBLE);
				StationNote station=BaseAppClient.getStation(stationAdapter.getStationId(position));
				stationInfo.setText(station.getInfo());
			}
			else 
			{
				lastSelectPosition=stationAdapter.getStationId(position);
				//双击事件
				//跳转
				Jump(stationAdapter.getStationId(position));
				
			}
		}
		
	};
	@Override
	public void onDestroyView()
	{
		//做fragment 切出屏幕时做相关的处理
		super.onDestroyView();
		
		
	}
	public void Jump(int StationID)
	{
	   //Toast.makeText(this.getActivity(), "跳转", Toast.LENGTH_LONG);
		//跳转
		this.selectEvent.AfterGetStation(StationID);
	}
	public interface SelectEvent{
		//获取选中的station后的执行回调
		public void AfterGetStation(int StationID);	
	}
	
}
