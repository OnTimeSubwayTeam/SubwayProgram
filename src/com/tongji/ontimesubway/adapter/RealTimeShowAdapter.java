package com.tongji.ontimesubway.adapter;

import java.lang.reflect.Field;
import java.util.List;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.base.BaseAppClient;
import com.tongji.ontimesubway.base.BaseKey;
import com.tongji.ontimesubway.base.StationRoute;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RealTimeShowAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<StationRoute> mlist;
	private TimeThread updateTime=new TimeThread();
	private String realAppendStr=" 后到达";
	public RealTimeShowAdapter(Context context,List<StationRoute> list)
	{
		mInflater=LayoutInflater.from(context);
		mlist=list;		
	}
	
	//start the timeThread
	public void startRunTime(){
			updateTime.start();
	}			
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist==null ? 0 : mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder=null;
		if(convertView==null)
		{
			holder=new Holder();
			convertView=this.mInflater.inflate(R.layout.real_unit, null);
			holder.routeName=(TextView)convertView.findViewById(R.id.real_routename);
			holder.firstTime=(TextView)convertView.findViewById(R.id.real_first);
			holder.secondTime=(TextView)convertView.findViewById(R.id.real_second);
			holder.thirdTime=(TextView)convertView.findViewById(R.id.real_third);
			holder.goalStation=(TextView)convertView.findViewById(R.id.real_goal_station);
			convertView.setTag(holder);
		}
		else 
			holder=(Holder)convertView.getTag();
		
		//显示初始化
		holder.routeName.setText(this.mlist.get(position).getRouteName());
		int RouteID=this.mlist.get(position).getRouteID();
		String bgName="station_button_"+String.valueOf(RouteID);
		try {
			//Log.d("try","hello");
			Field field=R.drawable.class.getField(bgName);
			int resInt= field.getInt(new R.drawable());
			holder.routeName.setBackgroundResource(resInt);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		holder.goalStation.setText(BaseAppClient.getStation(mlist.get(position).getGoalStationID()).getName()+"\n方向");
		holder.firstTime.setText(this.mlist.get(position).getTime(0).append(realAppendStr));
		holder.secondTime.setText(this.mlist.get(position).getTime(1).append(realAppendStr));
		holder.thirdTime.setText(this.mlist.get(position).getTime(2).append(realAppendStr));
		return convertView;
	}

	private class Holder{
		private TextView routeName;
		private TextView firstTime;
		private TextView secondTime;
		private TextView thirdTime;	
		private TextView goalStation;
	}
	
	
	//thread update the route time that route to the station 
	private class TimeThread extends Thread{
		private boolean IsRun=true;
		@Override
		public void run()
		{
			while(IsRun){
				//每隔1s 更新一次
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i=0;i<mlist.size();i++)
				{
					mlist.get(i).timerun();
				}
				Message msg=new Message();
				msg.what=BaseKey.Real_Time_Update_OK;
				RealTimeShowAdapter.this.UpdateUIHanlder.sendMessage(msg);
			}
		}
		public void StopThread()
		{
			IsRun=false;
		}	
	};
	@SuppressLint("HandlerLeak")
	private Handler UpdateUIHanlder=new Handler(){
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what==BaseKey.Real_Time_Update_OK)
			{
				RealTimeShowAdapter.this.notifyDataSetChanged();
			}
		}
	};
	public void stopRunTime()
	{
		this.updateTime.StopThread();
	}
	public void update(StationRoute updateRoute)
	{
		for(int i=0;i<mlist.size();i++)
		{
			if(mlist.get(i).equals(updateRoute))
			{
				mlist.get(i).updateTime(updateRoute);
				this.notifyDataSetChanged();
				return ;
			}
		}
		mlist.add(updateRoute);
		this.notifyDataSetChanged();
		return ;
		
	}
	public void update (List<StationRoute> updateList)
	{
		mlist=updateList;
		this.notifyDataSetChanged();
		return ;
	}
}
