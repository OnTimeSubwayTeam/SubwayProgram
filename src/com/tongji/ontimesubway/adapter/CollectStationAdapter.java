package com.tongji.ontimesubway.adapter;

import java.util.ArrayList;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.base.BaseAppClient;
import com.tongji.ontimesubway.base.StationNote;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectStationAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<Integer> mList;
	public CollectStationAdapter(Context context,ArrayList<Integer> list)
	{
		this.mInflater=LayoutInflater.from(context);
		this.mList=list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList==null ? 0:mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		collectStationHolder holder=null;
		if (convertView == null) {
			holder = new collectStationHolder();
			convertView = mInflater.inflate(R.layout.collectunit, null);
			holder.start=(TextView)convertView.findViewById(R.id.collectunit_start);
			holder.center=(TextView)convertView.findViewById(R.id.collectunit_center);
			holder.end=(TextView)convertView.findViewById(R.id.collectunit_end);
			convertView.setTag(holder);

		} else {
			holder = (collectStationHolder) convertView.getTag();
		}
		
		holder.start.setText(BaseAppClient.getStation(mList.get(position)).getName());
		holder.center.setText("");
		holder.end.setText(BaseAppClient.getStation(mList.get(position)).getRouteName());

		return convertView;
	}
	public class collectStationHolder{
		
		public TextView start;
		public TextView center;
		public TextView end;
	} 

}
