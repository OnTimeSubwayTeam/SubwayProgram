package com.tongji.ontimesubway.adapter;

import java.util.ArrayList;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.R.color;
import com.tongji.ontimesubway.adapter.CollectStationAdapter.collectStationHolder;
import com.tongji.ontimesubway.base.BaseAppClient;
import com.tongji.ontimesubway.base.BaseKey;
import com.tongji.ontimesubway.base.CollectRoute;
import com.tongji.ontimesubway.view.StationNoteView;
import com.tongji.ontimesubway.view.triangleView;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class CollectRouteAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private ArrayList<CollectRoute> mList;
	private int dm;
	public CollectRouteAdapter()
	{
		mInflater=null;
		this.mList=null;
	}
	public CollectRouteAdapter(Context context,ArrayList<CollectRoute> list,int s_Width)
	{
		this.mInflater=LayoutInflater.from(context);
		this.mList=list;
		
		dm= s_Width;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.mList==null? 0:mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		CollectRouteHolder holder=null;
		if (convertView == null) {
			holder = new CollectRouteHolder();
			convertView = mInflater.inflate(R.layout.collect_route_unit, null);
			holder.lPart=(RelativeLayout)convertView.findViewById(R.id.collect_route_left);
			LinearLayout.LayoutParams gi=(LinearLayout.LayoutParams) holder.lPart.getLayoutParams();
			gi.width=dm;			
			holder.startStation=(TextView)convertView.findViewById(R.id.collectunit_start);
			holder.Center=(TextView)convertView.findViewById(R.id.collectunit_center);
			holder.endStation=(TextView)convertView.findViewById(R.id.collectunit_end);
			holder.price=(TextView)convertView.findViewById(R.id.route_price);
			holder.time=(TextView)convertView.findViewById(R.id.route_time);
			holder.triangle=(triangleView)convertView.findViewById(R.id.triangle);
			holder.deleteButton=(Button)convertView.findViewById(R.id.delete);
			holder.deleteButton.setOnClickListener(listener);
			holder.route_hide_part=(RelativeLayout)convertView.findViewById(R.id.route_hide_part);
			holder.stationnote_container=(LinearLayout)convertView.findViewById(R.id.stationnote_container);
			holder.hs=(HorizontalScrollView)convertView.findViewById(R.id.collect_route_unit_hs);
			convertView.setTag(holder);

		} else {
			holder = (CollectRouteHolder) convertView.getTag();
		}

		holder.deleteButton.setTag(position);
		holder.startStation.setText(mList.get(position).getFirstStation().getName());
		//holder.startStation.setTextColor(Color.parseColor(BaseKey.lineColor[getInGroup(mList.get(position),0)]));
		holder.Center.setText("--->");
		holder.endStation.setText(mList.get(position).getLastStation().getName());
		//holder.endStation.setTextColor(Color.parseColor(BaseKey.lineColor[getInGroup(mList.get(position),mList.get(position).getStationGroup().size()-1)]));
		
		//
		holder.price.setText(String.valueOf(mList.get(position).getPrice())+"元");
		StringBuffer time=new StringBuffer();
		time.append(mList.get(position).getTime()/60);
		time.append("分");
		time.append(mList.get(position).getTime()%60);
		time.append("秒");
		holder.time.setText(time);
		
		if(holder.hs.getScrollX()!=0)
		{
			holder.hs.scrollTo(0, 0);
		}
		//添加监听事件
		holder.lPart.setTag(holder);
		holder.lPart.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view ) {
				// TODO Auto-generated method stub
				CollectRouteHolder holder =(CollectRouteHolder)view.getTag();
				if(holder.isHide){
					holder.isHide=false;
					holder.triangle.setVisibility(View.VISIBLE);
					holder.route_hide_part.setVisibility(View.VISIBLE);
					//开始动画
					Animation HideToshow_Animation = AnimationUtils.loadAnimation(mInflater.getContext(), R.anim.route_hide_show);
					holder.route_hide_part.startAnimation(HideToshow_Animation);
				}
				else 
				{				
					holder.isHide=true;
					holder.triangle.setVisibility(View.INVISIBLE);
					holder.route_hide_part.setVisibility(View.GONE);
				}
			}
			
		});
		//初始化站点
		initStationNoteContainer(mList.get(position),holder.stationnote_container);
		return convertView;
	}
	
	public void initStationNoteContainer(CollectRoute collectRoute,LinearLayout snContainer)
	{
		//先去除所有的对象
		snContainer.removeAllViews();
		//
		ArrayList<Integer> group=collectRoute.getStationGroup();
		if(group.size()>0){
			for(int i=0;i<group.size()-1;i++){
				StationNoteView station=new StationNoteView(mInflater.getContext());
				station.setStation(BaseAppClient.getStation(group.get(i)), getInGroup(collectRoute,i-1), getInGroup(collectRoute,i+1));
				snContainer.addView(station);
			}
			//加上最后一个节点
			StationNoteView station=new StationNoteView(mInflater.getContext());
			station.setStation(BaseAppClient.getStation(group.get(group.size()-1)), getInGroup(collectRoute,group.size()-2), -1);
			snContainer.addView(station);
			snContainer.invalidate();
			
		}
	}
	
	private OnClickListener listener=new OnClickListener(){

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			Integer position=(Integer)view.getTag();
			BaseAppClient.removeCollectRoute(mList.get(position));
			mList.remove((int)position);
			notifyDataSetChanged();
		}
		
	};
	//得到第position 个站点所处的线路
	public int getInGroup(CollectRoute collectRoute,int position)
	{
		//如果position <0，则返回错误
		if(position <0)
		{
			return -1;
		}
		//如果是第一个站点了，则返回站点里的第一条路线
		if(position==0)
		{
			return BaseAppClient.getStation(collectRoute.getStationGroup().get(position)).getRouteGroup().get(0);
		}
		//如果站点只在一个路线上，则返回该路线
		if(BaseAppClient.getStation(collectRoute.getStationGroup().get(position)).getRouteGroup().size()==1)
		{
			return BaseAppClient.getStation(collectRoute.getStationGroup().get(position)).getRouteGroup().get(0);
		}
		//如果站点在多条路线上，则与前一个站点相同，
		else 
			return getInGroup(collectRoute,position-1);
	}
	private class  CollectRouteHolder{
		public TextView startStation;
		public TextView Center;
		public TextView endStation;
		public TextView price;
		public TextView time;
		public triangleView triangle;
		public Button deleteButton;
		public RelativeLayout route_hide_part;
		public LinearLayout stationnote_container;
		public HorizontalScrollView hs;
		public RelativeLayout lPart;
		public boolean isHide=true;
	}

}
