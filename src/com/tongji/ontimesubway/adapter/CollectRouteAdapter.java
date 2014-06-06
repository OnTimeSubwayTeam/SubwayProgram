package com.tongji.ontimesubway.adapter;

import java.util.ArrayList;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.adapter.CollectStationAdapter.collectStationHolder;
import com.tongji.ontimesubway.base.CollectRoute;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CollectRouteAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private ArrayList<CollectRoute> mList;
	public CollectRouteAdapter()
	{
		mInflater=null;
		this.mList=null;
	}
	public CollectRouteAdapter(Context context,ArrayList<CollectRoute> list)
	{
		this.mInflater=LayoutInflater.from(context);
		this.mList=list;
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
			convertView = mInflater.inflate(R.layout.collectunit, null);
			holder.startStation=(TextView)convertView.findViewById(R.id.collectunit_start);
			holder.Center=(TextView)convertView.findViewById(R.id.collectunit_center);
			holder.endStation=(TextView)convertView.findViewById(R.id.collectunit_end);
			convertView.setTag(holder);

		} else {
			holder = (CollectRouteHolder) convertView.getTag();
		}

		holder.startStation.setText(mList.get(position).getFirstStation().getName());
		holder.Center.setText("--->");
		holder.endStation.setText(mList.get(position).getLastStation().getName());

		return convertView;
	}
	private class  CollectRouteHolder{
		public TextView startStation;
		public TextView Center;
		public TextView endStation;
	}

}
