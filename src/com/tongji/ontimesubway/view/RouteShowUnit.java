package com.tongji.ontimesubway.view;

import java.util.ArrayList;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.base.BaseAppClient;
import com.tongji.ontimesubway.base.CollectRoute;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RouteShowUnit extends RelativeLayout{
	//
	private CollectRoute collectRoute;
	//显示位置: 线路一，线路二
	private int position;

	private TextView routeName;
	private TextView routeTime;
	private TextView routePrice;
	private Button collectButton;
	private triangleView hideTriangle;
	private RelativeLayout Route_hide;
	private LinearLayout snContainer;
	private RelativeLayout routeshow;
	private boolean isShow=false;
	
	private Context context;
	public RouteShowUnit(Context context)
	{
		super(context);
		this.context=context;
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.route_list_view, this);
		routeName=(TextView)findViewById(R.id.route_name);
		//routeName.setOnClickListener(listener);
		routeshow=(RelativeLayout)findViewById(R.id.route_show_part);
		routeshow.setOnClickListener(listener);
		routeTime=(TextView)findViewById(R.id.route_time);
		routePrice=(TextView)findViewById(R.id.route_fee);
		collectButton=(Button)findViewById(R.id.route_collect_botton);
		collectButton.setOnClickListener(listener);
		hideTriangle=(triangleView)findViewById(R.id.triangle);
		Route_hide=(RelativeLayout)findViewById(R.id.route_hide_part);
		snContainer=(LinearLayout)findViewById(R.id.stationnote_container);
	}
	public RouteShowUnit(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.route_list_view, this);
		routeName=(TextView)findViewById(R.id.route_name);
		routeName.setOnClickListener(listener);
		routeTime=(TextView)findViewById(R.id.route_time);
		routePrice=(TextView)findViewById(R.id.route_fee);
		collectButton=(Button)findViewById(R.id.route_collect_botton);
		collectButton.setOnClickListener(listener);
		hideTriangle=(triangleView)findViewById(R.id.triangle);
		Route_hide=(RelativeLayout)findViewById(R.id.route_hide_part);
		snContainer=(LinearLayout)findViewById(R.id.stationnote_container);
	}
	//完成界面显示与初始化 position 从1开始
	public void setCollectRoute(CollectRoute route,int position)
	{
		this.collectRoute=route;
		this.position=position;
		//完成
		this.routeName.setText("线路"+NumberToString(this.position));
		StringBuffer time=new StringBuffer();
		time.append(this.collectRoute.getTime()/60);
		time.append("分");
		time.append(this.collectRoute.getTime()%60);
		time.append("秒");
		this.routeTime.setText(time);
		
		//
		if(BaseAppClient.isInCollectRoute(collectRoute)){
			this.collectButton.setText(context.getResources().getString(R.string.cancelCollect));
			this.collectButton.setTag(false);
		}
		else 
			this.collectButton.setTag(true);
		this.routePrice.setText(this.collectRoute.getPrice()+"元");
		initStationNoteContainer();
		
	}
	//画出route 的线路图
	public void initStationNoteContainer()
	{
		//
		ArrayList<Integer> group=this.collectRoute.getStationGroup();
		if(group.size()>0){
			for(int i=0;i<group.size()-1;i++){
				StationNoteView station=new StationNoteView(context);
				station.setStation(BaseAppClient.getStation(group.get(i)), getInGroup(i-1), getInGroup(i+1));
				this.snContainer.addView(station);
			}
			//加上最后一个节点
			StationNoteView station=new StationNoteView(context);
			station.setStation(BaseAppClient.getStation(group.get(group.size()-1)), getInGroup(group.size()-2), -1);
			this.snContainer.addView(station);
			this.snContainer.invalidate();
		}
	}
	//得到第position 个站点所处的线路
	public int getInGroup(int position)
	{
		//如果position <0，则返回错误
		if(position <0)
		{
			return -1;
		}
		//如果是第一个站点了，则返回站点里的第一条路线
		if(position==0)
		{
			return BaseAppClient.getStation(this.collectRoute.getStationGroup().get(position)).getRouteGroup().get(0);
		}
		//如果站点只在一个路线上，则返回该路线
		if(BaseAppClient.getStation(this.collectRoute.getStationGroup().get(position)).getRouteGroup().size()==1)
		{
			return BaseAppClient.getStation(this.collectRoute.getStationGroup().get(position)).getRouteGroup().get(0);
		}
		//如果站点在多条路线上，则与前一个站点相同，
		else 
			return getInGroup(position-1);
	}
	private String NumberToString(int position)
	{
		switch(position)
		{
		case 1:
			return "一";
		case 2: 
			return "二";
		case 3:
			return "三";
		case 4: 
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		default:
			return "七";
		
		}
	}
	private OnClickListener listener=new OnClickListener(){

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
			case R.id.route_show_part:
				if(!isShow){
					isShow=true;
					hideTriangle.setVisibility(View.VISIBLE);
					Route_hide.setVisibility(View.VISIBLE);
					Animation HideToshow_Animation = AnimationUtils.loadAnimation(context, R.anim.route_hide_show);
					Route_hide.startAnimation(HideToshow_Animation);
				}
				else 
				{
					isShow=false;
					hideTriangle.setVisibility(View.GONE);
					Route_hide.setVisibility(View.GONE);
				}
				break;
			case R.id.route_collect_botton:
				if((Boolean)view.getTag()){
					view.setTag(false);
					BaseAppClient.addCollectRoute(collectRoute);
					((Button)view).setText(context.getResources().getString(R.string.cancelCollect));
				}
				else 
				{
					view.setTag(true);
					((Button)view).setText(context.getResources().getString(R.string.title_left_collect));
					BaseAppClient.removeCollectRoute(collectRoute);
				}
				break;
			
			}
		}
		
	};

}
