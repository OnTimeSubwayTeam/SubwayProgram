package com.tongji.ontimesubway.ui;

import java.util.ArrayList;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.R.layout;
import com.tongji.ontimesubway.R.menu;
import com.tongji.ontimesubway.adapter.CollectRouteAdapter;
import com.tongji.ontimesubway.adapter.CollectStationAdapter;
import com.tongji.ontimesubway.base.BaseUI;
import com.tongji.ontimesubway.base.CollectRoute;
import com.tongji.ontimesubway.base.StationNote;

import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class CollectCenterActivity extends BaseUI {

	private TabHost collecthost;
	private OnClickListener listener;
	private final String StationTabName="站点";
	private final String RouteTabName="线路";
	
	//listview 
	private ListView stationlistview;
	private ListView routelistview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collect_cneter);
		//设置监听器
		setListener();
		//设置titleBar
		this.setTitleBar();
		
		//设置tabhost
		 setTabhost();
		 
		 //init()
		 init();
	}

	private void init() {
		// TODO Auto-generated method stub
		stationlistview=(ListView)findViewById(R.id.cs_listview);
		routelistview=(ListView)findViewById(R.id.cr_listview);
		ArrayList<Integer> Slist = new ArrayList<Integer>();
		ArrayList<CollectRoute> Rlist = new ArrayList<CollectRoute>();
		initData(Slist,Rlist);
		CollectStationAdapter CSadapter=new CollectStationAdapter(this, Slist);
		CollectRouteAdapter CRadapter=new CollectRouteAdapter(this,Rlist);
		stationlistview.setAdapter(CSadapter);
		routelistview.setAdapter(CRadapter);
	}

	private void initData(ArrayList<Integer> slist,
			ArrayList<CollectRoute> rlist) {
		// TODO Auto-generated method stub
		ArrayList<Integer> routeID=new ArrayList<Integer>();
		routeID.add(11);
		routeID.add(12);
		StationNote s1=new StationNote(1, "昌吉东路", null, null, null, routeID);
		slist.add(s1.getStationID());
		s1=new StationNote(2,"上海汽车城",null,null,null,routeID);
		slist.add(s1.getStationID());
		CollectRoute r1=new CollectRoute();
		r1.setName("11号线");
		r1.setStationGroup(slist);
		rlist.add(r1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.collect_cneter, menu);
		return true;
	}
	public void setListener()
	{
		listener=new OnClickListener(){
			@Override
			public void onClick(View clickview) {
				// TODO Auto-generated method stub
				switch(clickview.getId())
				{
				case R.id.title_left:
					CollectCenterActivity.this.finish();
					break;
				}
			}	
		};
		return ;
	}
	//初始化tabhost
	public void setTabhost()
	{
		this.collecthost=(TabHost)findViewById(R.id.collecttabhost);
		this.collecthost.setup();
		this.collecthost.addTab(collecthost.newTabSpec("station")
				.setIndicator(this.StationTabName)
				.setContent(R.id.collectstationTab));
		this.collecthost.addTab(collecthost.newTabSpec("route")
				.setIndicator(this.RouteTabName)
				.setContent(R.id.collectrouteTab));
		updateTab(this.collecthost);
		this.collecthost.setOnTabChangedListener(new OnTabChangeListener(){

			@Override
			public void onTabChanged(String tabName) {
				// TODO Auto-generated method stub
				if(tabName.equals("station"))
				{
					updateTab(collecthost);
				}
				else if(tabName.equals("route"))
				{
					updateTab(collecthost);
				}
			}
			
		});
				
	}
	//改变tabwight的样式
		public void updateTab(final TabHost tabHost)
		{
			for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) { 
	            View view = tabHost.getTabWidget().getChildAt(i);
	            LayoutParams layoutParams=(LinearLayout.LayoutParams)view.getLayoutParams();
	            layoutParams.height=70;
	            layoutParams.setMargins(3, 5, 3, 5);
	            layoutParams.gravity=Gravity.CENTER_VERTICAL;
	            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); 
	            tv.setTextSize(16);
	            tv.setGravity(Gravity.CENTER); // 设置字体和风格  
	            tv.setTextColor(this.getResources().getColorStateList( 
	                    android.R.color.white));
	            if (tabHost.getCurrentTab() == i) {//选中  
	                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.circularbuttonn));//选中后的背景  
	                 
	            } else {//不选中  
	                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.circularbuttonef));//非选择的背景         
	            } 
			}
		}
	@Override
	public void setTitleBar()
	{
		super.setTitleBar();
		this.titleRight.setVisibility(View.GONE);
		this.titleCenter.setText(this.getString(R.string.title_center_collect));
		this.titleLeft.setText(this.getString(R.string.back));
		this.titleLeft.setOnClickListener(listener);
	}
}
