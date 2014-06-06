package com.tongji.ontimesubway.adapter;

import java.lang.reflect.Field;
import java.util.List;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.base.BaseAppClient;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StationViewAdapter extends BaseAdapter{

	private List<Integer> mList;
	private LayoutInflater mInflater;
	private int RouteID;
	public StationViewAdapter(List<Integer> list,Context context,int RouteID)
	{
		mList=list;
		mInflater=LayoutInflater.from(context);
		this.RouteID=RouteID;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList==null? 0: mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public int getStationId(int position)
	{
		return mList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder=null;
		if(convertView==null)
		{
			holder=new Holder();
			convertView=this.mInflater.inflate(R.layout.route_station_list_view, null);
			holder.layout=(LinearLayout)convertView.findViewById(R.id.sation_view_image_layout);
			holder.station=(TextView)convertView.findViewById(R.id.station_view_station);			
			convertView.setTag(holder);
		}
		else 
		{
			holder=(Holder)convertView.getTag();
		}
		String bgName="station_button_"+String.valueOf(RouteID);
		try {
			Log.d("try","hello");
			Field field=R.drawable.class.getField(bgName);
			int resInt= field.getInt(new R.drawable());
			holder.station.setBackgroundResource(resInt);
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
		String stationName=BaseAppClient.getStation(mList.get(position)).getName();
		holder.station.setText(stationName);
		return convertView;
	}
	/**
	 * update
	 * @author Rong
	 *
	 */
	public void Update (List<Integer> list,int RouteID)
	{
		this.RouteID=RouteID;
		mList=list;
		this.notifyDataSetChanged();
		
	}
	private class Holder
	{
		LinearLayout layout;
		TextView station;
	}

}
