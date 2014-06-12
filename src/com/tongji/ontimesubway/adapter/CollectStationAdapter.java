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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CollectStationAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<Integer> mList;
	private int S_width;
	public CollectStationAdapter(Context context,ArrayList<Integer> list,int S_width)
	{
		this.mInflater=LayoutInflater.from(context);
		this.mList=list;
		this.S_width=S_width;
	
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
			holder.wc=(ImageView)convertView.findViewById(R.id.wc);
			holder.translate=(ImageView)convertView.findViewById(R.id.translate);
			holder.end=(TextView)convertView.findViewById(R.id.collectunit_end);
			RelativeLayout lPart=(RelativeLayout)convertView.findViewById(R.id.collect_route_left);
			LinearLayout.LayoutParams gi=(LinearLayout.LayoutParams) lPart.getLayoutParams();
			gi.width=S_width;
			holder.deleteButton=(Button)convertView.findViewById(R.id.delete);
			holder.toMenu=(Button)convertView.findViewById(R.id.addtomenu);
			holder.deleteButton.setOnClickListener(listener);
			holder.toMenu.setOnClickListener(listener);
			convertView.setTag(holder);

		} else {
			holder = (collectStationHolder) convertView.getTag();
		}
		if(BaseAppClient.getStation(mList.get(position)).getIsWC()==0)
		{
			holder.wc.setVisibility(View.INVISIBLE);
		}
		else 
		{
			holder.wc.setVisibility(View.VISIBLE);
		}
		if(BaseAppClient.getStation(mList.get(position)).getRouteGroup().size()>1)
		{
			holder.translate.setVisibility(View.VISIBLE);
			
		}
		else 
		{
			holder.translate.setVisibility(View.INVISIBLE);
		}
		holder.deleteButton.setTag(position);
		holder.toMenu.setTag(mList.get(position));
		if(BaseAppClient.isInRecentStation(mList.get(position))){
			holder.toMenu.setText(R.string.removeFrommenu);
		}
		else {
			holder.toMenu.setText(R.string.addTomenu);
		}
		holder.start.setText(BaseAppClient.getStation(mList.get(position)).getName());
		holder.end.setText(BaseAppClient.getStation(mList.get(position)).getRouteName());

		return convertView;
	}
	
	private OnClickListener listener=new OnClickListener(){

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
			case R.id.delete:
				Integer position=(Integer)view.getTag();
				mList.remove((int)position);
				notifyDataSetChanged();
				break;
			case R.id.addtomenu:
				
				Integer stationID=(Integer)view.getTag();
				if(BaseAppClient.isInRecentStation(stationID)){
				BaseAppClient.removeRecentStation(stationID);
				((Button)view).setText(R.string.addTomenu);
				}
				else {
					BaseAppClient.addRecentStation(stationID);
					((Button)view).setText(R.string.removeFrommenu);
				}
				break;
			}
		}
		
	};
	public class collectStationHolder{
		
		public TextView start;
		public ImageView wc;
		public ImageView translate;
		public TextView end;
		public Button deleteButton;
		public Button toMenu;
	} 

}
