package com.tongji.ontimesubway.base;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.ui.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public abstract class BaseUI extends Activity{
	protected Button titleLeft;
	protected Button titleRight;
	protected TextView titleCenter;
	private View menuShowView;
	
	//滑动变量
	private float end,start;
	private final float MAX	= 999999;
	private static MenuWindow Menu=null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//自定义titlebar
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		Menu=getMenu();				
	}
	public MenuWindow getMenu()
	{
		//menuShowView = getLayoutInflater().inflate(R.layout.notification_view,  
	     //           null, false);
		 //menuShowView=(View)findViewById(R.id.menuShowLocation);
		Display display = getWindowManager().getDefaultDisplay();
		MenuWindow menu=MenuWindow.getInstance(this,display.getWidth(),display.getHeight()*1/3);
		//menu.setAnimationStyle(R.style.AnimationFade);
		 return menu;
		 
	}
	protected void setTitleBar()
	{
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
		this.titleLeft=(Button)findViewById(R.id.title_left);
		this.titleCenter=(TextView)findViewById(R.id.title_center);
		this.titleRight=(Button)findViewById(R.id.title_right);
		//this.titleRight.set
		
	}
	@Override 
	public boolean onTouchEvent(MotionEvent event)
	{
		switch(event.getAction() & MotionEvent.ACTION_MASK)
		{
		case MotionEvent.ACTION_DOWN:
			Log.d("BaseUI","Down+"+String.valueOf(event.getRawY()));
			start=event.getRawY();
			//if(start>300)
				//start=MAX;
			break;
		case MotionEvent.ACTION_MOVE:
			//Log.d("BaseUI","MoveX="+String.valueOf(event.getX()));
			break;
		case MotionEvent.ACTION_UP:
			if(event.getRawY()-start>100)
			{
				if(!Menu.isShowing())
				{
					View view=(View)findViewById(R.id.menuShowLocation);
					Menu.showAsDropDown(view, 0, 0);
				}			
			}
			else if(start-event.getRawY()>100)
			{
				if(Menu.isShowing())
					Menu.dismiss();
			}
			Log.d("BaseUI","Move Down");
			break;
		}
		return true;
	}
	

	public abstract Context getContext();
}
