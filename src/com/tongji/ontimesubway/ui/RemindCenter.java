package com.tongji.ontimesubway.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.base.BaseAppClient;
import com.tongji.ontimesubway.base.Route;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RemindCenter extends Activity {

	private Spinner start_route_spinner,start_station_spinner,start_direction_spinner;
	private Spinner end_route_spinner,end_station_spinner;
	
	//先这样做吧，如果以后还有更好的方法，将不用这种方法
	private ArrayAdapter start_route_adapter,start_station_adapter,start_direction_adapter;
	private ArrayAdapter end_route_adapter,end_station_adapter;
	
	//
	private ArrayList<Integer> routeIDList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remind_center);
		//init the spinner view 
		initSpinner();
	}

	/**
	 * 对spinner 的初始化
	 * 
	 * @author Rong
	 * 
	 * @param 
	 * nulls
	 * 
	 * @return
	 * void  
	 */
	@SuppressLint("NewApi")
	public void initSpinner()
	{
		start_route_spinner=(Spinner)findViewById(R.id.rc_selected_start_route_spinner);
		start_station_spinner=(Spinner)findViewById(R.id.rc_selected_start_station_spinner);
		start_direction_spinner=(Spinner)findViewById(R.id.rc_selected_start_direction_spinner);
		end_route_spinner=(Spinner)findViewById(R.id.rc_selected_end_route_spinner);
		end_station_spinner=(Spinner)findViewById(R.id.rc_selected_end_station_spinner);
		
		/**
		 * 下面来定义各个spinner的行为
		 * 
		 * @author Rong
		 */
		start_route_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
		start_route_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		List<String> routeList=new ArrayList<String>();
		routeIDList=new ArrayList<Integer>();
		//添加线路的数据
		Iterator it=BaseAppClient.RouteMap.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<Integer, Route>entry=(Map.Entry<Integer, Route>) it.next();
			routeList.add(entry.getValue().getName());
			routeIDList.add(entry.getValue().getID());
		}
		start_route_adapter.addAll(routeList);
		start_route_spinner.setAdapter(start_route_adapter);
		start_route_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//Log.d("remind start spinner item selected,",String.valueOf(position));
				Route route=BaseAppClient.RouteMap.get(routeIDList.get(position));
				ArrayList<Integer> stationGroup=route.getStationGroup();
				start_station_adapter=new ArrayAdapter<String>(RemindCenter.this,android.R.layout.simple_spinner_item);
				start_station_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				for(int i=0;i<stationGroup.size();i++){
					start_station_adapter.add(BaseAppClient.getStation(stationGroup.get(i)).getName());
				}
				start_station_spinner.setAdapter(start_station_adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				start_station_spinner.setAdapter(null);
			}		
		});
		end_route_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
		end_route_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		end_route_adapter.addAll(routeList);
		end_route_spinner.setAdapter(end_route_adapter);
		end_route_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Route route=BaseAppClient.RouteMap.get(routeIDList.get(position));
				ArrayList<Integer> stationGroup=route.getStationGroup();
				end_station_adapter=new ArrayAdapter<String>(RemindCenter.this,android.R.layout.simple_spinner_item);
				end_station_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
				for(int i=0;i<stationGroup.size();i++){
					end_station_adapter.add(BaseAppClient.getStation(stationGroup.get(i)).getName());
				}
				end_station_spinner.setAdapter(end_station_adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				end_station_spinner.setAdapter(null);
			}			
		});
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.remind_center, menu);
		return true;
	}

}
