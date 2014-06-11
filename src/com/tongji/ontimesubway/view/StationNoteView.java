package com.tongji.ontimesubway.view;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.base.BaseKey;
import com.tongji.ontimesubway.base.StationNote;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StationNoteView extends RelativeLayout{

	private TextView stationNoteName;
	private TextView stationNoteTranslate;
	private TextView stationNotePre;
	private TextView stationNoteNext;
	private StationNoteCenterView stationCenter;
	public StationNoteView(Context context)
	{
		super(context);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.stationnote, this);
		stationNoteName=(TextView)findViewById(R.id.stationnote_name);
		stationNoteTranslate=(TextView)findViewById(R.id.stationnote_bottom);
		stationCenter=(StationNoteCenterView)findViewById(R.id.stationnote_center);
		stationNotePre=(TextView)findViewById(R.id.stationnote_pre);
		stationNoteNext=(TextView)findViewById(R.id.stationnote_next);
	}
	public StationNoteView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.stationnote, this);
		stationNoteName=(TextView)findViewById(R.id.stationnote_name);
		stationNoteTranslate=(TextView)findViewById(R.id.stationnote_bottom);
		stationCenter=(StationNoteCenterView)findViewById(R.id.stationnote_center);
		stationNotePre=(TextView)findViewById(R.id.stationnote_pre);
		stationNoteNext=(TextView)findViewById(R.id.stationnote_next);
	}
	//前面一条线会与前一站的group颜色相同，如果没有前一站，则不显示,
	//后一条线会与后一站的颜色相同，如查没有后一站，则不显示
	//中间点与前一站的颜色相同，如果没有则与后一站的颜色相同。
	//下方可换先乘与中间点颜色相同
	public void setStation(StationNote stationNote,int prestationGroup,int nextstationGroup)
	{
		this.stationNoteName.setText(stationNote.getName());
		if(stationNote.getRouteGroup().size()>1)
		{
			this.stationNoteTranslate.setText("可换"+stationNote.getRouteName());
			this.stationNoteTranslate.setVisibility(View.VISIBLE);
			if(prestationGroup>0)
			{
				this.stationNotePre.setBackgroundColor(Color.parseColor(BaseKey.lineColor[prestationGroup-1]));
				this.stationCenter.setColor(Color.parseColor(BaseKey.lineColor[prestationGroup-1]));
				this.stationNoteTranslate.setBackgroundColor(Color.parseColor(BaseKey.lineColor[prestationGroup-1]));
			}
			else 
			{
				this.stationNotePre.setVisibility(View.INVISIBLE);
				this.stationCenter.setColor(Color.parseColor(BaseKey.lineColor[nextstationGroup-1]));
				this.stationNoteTranslate.setBackgroundColor(Color.parseColor(BaseKey.lineColor[nextstationGroup-1]));
			}
			
			if(nextstationGroup>0)
			{
				this.stationNoteNext.setBackgroundColor(Color.parseColor(BaseKey.lineColor[nextstationGroup-1]));
				
			}
			else 
			{
				this.stationNoteNext.setVisibility(View.INVISIBLE);
			}
		}
		else {
			this.stationCenter.setColor(Color.parseColor(BaseKey.lineColor[stationNote.getRouteGroup().get(0)-1]));
			if(prestationGroup>0)
			{
				this.stationNotePre.setBackgroundColor(Color.parseColor(BaseKey.lineColor[stationNote.getRouteGroup().get(0)-1]));
			}
			else 
				this.stationNotePre.setVisibility(View.INVISIBLE);
			if(nextstationGroup>0)
			{
				this.stationNoteNext.setBackgroundColor(Color.parseColor(BaseKey.lineColor[stationNote.getRouteGroup().get(0)-1]));
			}
			else 
				this.stationNoteNext.setVisibility(View.INVISIBLE);
		}
		
		
	}

}
