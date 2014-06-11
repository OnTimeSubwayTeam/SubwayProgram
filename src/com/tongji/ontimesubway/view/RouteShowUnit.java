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
	//��ʾλ��: ��·һ����·��
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
	//��ɽ�����ʾ���ʼ�� position ��1��ʼ
	public void setCollectRoute(CollectRoute route,int position)
	{
		this.collectRoute=route;
		this.position=position;
		//���
		this.routeName.setText("��·"+NumberToString(this.position));
		StringBuffer time=new StringBuffer();
		time.append(this.collectRoute.getTime()/60);
		time.append("��");
		time.append(this.collectRoute.getTime()%60);
		time.append("��");
		this.routeTime.setText(time);
		
		//
		if(BaseAppClient.isInCollectRoute(collectRoute)){
			this.collectButton.setText(context.getResources().getString(R.string.cancelCollect));
			this.collectButton.setTag(false);
		}
		else 
			this.collectButton.setTag(true);
		this.routePrice.setText(this.collectRoute.getPrice()+"Ԫ");
		initStationNoteContainer();
		
	}
	//����route ����·ͼ
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
			//�������һ���ڵ�
			StationNoteView station=new StationNoteView(context);
			station.setStation(BaseAppClient.getStation(group.get(group.size()-1)), getInGroup(group.size()-2), -1);
			this.snContainer.addView(station);
			this.snContainer.invalidate();
		}
	}
	//�õ���position ��վ����������·
	public int getInGroup(int position)
	{
		//���position <0���򷵻ش���
		if(position <0)
		{
			return -1;
		}
		//����ǵ�һ��վ���ˣ��򷵻�վ����ĵ�һ��·��
		if(position==0)
		{
			return BaseAppClient.getStation(this.collectRoute.getStationGroup().get(position)).getRouteGroup().get(0);
		}
		//���վ��ֻ��һ��·���ϣ��򷵻ظ�·��
		if(BaseAppClient.getStation(this.collectRoute.getStationGroup().get(position)).getRouteGroup().size()==1)
		{
			return BaseAppClient.getStation(this.collectRoute.getStationGroup().get(position)).getRouteGroup().get(0);
		}
		//���վ���ڶ���·���ϣ�����ǰһ��վ����ͬ��
		else 
			return getInGroup(position-1);
	}
	private String NumberToString(int position)
	{
		switch(position)
		{
		case 1:
			return "һ";
		case 2: 
			return "��";
		case 3:
			return "��";
		case 4: 
			return "��";
		case 5:
			return "��";
		case 6:
			return "��";
		default:
			return "��";
		
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
