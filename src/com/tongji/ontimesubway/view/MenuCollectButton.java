package com.tongji.ontimesubway.view;

import java.lang.reflect.Field;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.base.BaseAppClient;
import com.tongji.ontimesubway.base.MenuCollectButtonListener;
import com.tongji.ontimesubway.base.StationNote;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuCollectButton extends LinearLayout{

	private TextView station;
	private TextView delete_up;
	private StationNote stationNote=null;
	private MenuCollectButtonListener Menulistener=new MenuCollectButtonListener(){

		@Override
		public void stationClick(int StationID) {
			// TODO Auto-generated method stub			
		}

		@Override
		public void deleteClick(int StationID) {
			// TODO Auto-generated method stub			
		}
		
	};
	private boolean deletestage;
	private Context mContext;
	
	private Animation shake ;
	public MenuCollectButton(Context context)
	{
		super(context);
		mContext=context;
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.menu_collect_button, this);
		this.station=(TextView)findViewById(R.id.collect_button_center);
		this.delete_up=(TextView)findViewById(R.id.collect_button_up_delete);
		shake= AnimationUtils.loadAnimation(mContext, R.anim.horizontal);
	}
	public MenuCollectButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext=context;
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.menu_collect_button, this);
		this.station=(TextView)findViewById(R.id.collect_button_center);
		this.delete_up=(TextView)findViewById(R.id.collect_button_up_delete);	
		shake= AnimationUtils.loadAnimation(mContext, R.anim.horizontal);
	}
	public void setStation(int stationID)
	{
		stationNote=BaseAppClient.getStation(stationID);
		this.station.setText(stationNote.getName());
		String bgName="station_button_"+String.valueOf(stationNote.getRouteGroup().get(0));
		try {
//			Log.d("try","hello");
			Field field=R.drawable.class.getField(bgName);
			int resInt= field.getInt(new R.drawable());
			this.station.setBackgroundResource(resInt);
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
		setOnClickEvent();
	}
	public void setOnClickEvent()
	{
		this.station.setOnClickListener(listener);
		this.delete_up.setOnClickListener(listener);
		this.station.setOnLongClickListener(longlistener);
	}
	private OnClickListener listener=new OnClickListener(){

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId()){
			case R.id.collect_button_center:
				if(deletestage)
					CancelDeleteStage();
				else 
					Menulistener.stationClick(stationNote.getStationID());
				break;
			case R.id.collect_button_up_delete:
				Menulistener.deleteClick(stationNote.getStationID());
				break;
			}
		}
		
	};
	
	public StationNote getStation()
	{
		return this.stationNote;
	}
	private OnLongClickListener longlistener=new OnLongClickListener(){

		@Override
		public boolean onLongClick(View view) {
			// TODO Auto-generated method stub
			if(deletestage)
				return false;
			else{ 
				deletestage=true;
				delete_up.setVisibility(View.VISIBLE);				
				station.startAnimation(shake);
				//shake.st
				return true;
			}
		}
		
	};
	//用于取消删除状态
	public void CancelDeleteStage()
	{
		this.shake.cancel();
		deletestage=false;
		delete_up.setVisibility(View.INVISIBLE);
	}
	public boolean isDeleteStage()
	{
		return this.deletestage;
	}
	
	public void setMenuCollectClickListener(MenuCollectButtonListener listener)
	{
		this.Menulistener=listener;
	}
}
