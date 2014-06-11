package com.tongji.ontimesubway.base;

import java.util.LinkedList;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.ui.CollectCenterActivity;
import com.tongji.ontimesubway.ui.MainActivity;
import com.tongji.ontimesubway.ui.RemindCenter;
import com.tongji.ontimesubway.view.MenuButton;
import com.tongji.ontimesubway.view.MenuCollectButton;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class MenuWindow extends PopupWindow {
	private static MenuWindow menu=null;
	private static BaseUI mActivity;
	private static View menuShowView;
	private MenuButton station_button,collect_button,reminder_button;
	private RelativeLayout bottom;
	private LinearLayout collectLayout;
	private int pressButtonID=0;

	private MenuWindow(int width, int height){
		
		
		super(menuShowView,width,height);
		this.setAnimationStyle(R.style.AnimationFade);
		this.setTouchInterceptor(onTouchListener);
		station_button=(MenuButton)menuShowView.findViewById(R.id.Menu_Main_center);
		collect_button=(MenuButton)menuShowView.findViewById(R.id.Menu_Collect_center);
		reminder_button=(MenuButton)menuShowView.findViewById(R.id.Menu_Remind_center);
		station_button.setBackgroundResource(R.drawable.menu_station_button);
		station_button.setMidText("站点");
		collect_button.setBackgroundResource( R.drawable.menu_collect_button);
		collect_button.setMidText("收藏");
		reminder_button.setBackgroundResource( R.drawable.menu_remind_button);
		reminder_button.setMidText("提醒");
		bottom=(RelativeLayout)menuShowView.findViewById(R.id.menu_bottom_layout);
		collectLayout=(LinearLayout)menuShowView.findViewById(R.id.collectStationlayout);
		bottom.setOnClickListener(listener);
		station_button.setOnClickListener(listener);
		collect_button.setOnClickListener(listener);
		reminder_button.setOnClickListener(listener);
		this.setOnDismissListener(onDismissListener);
		
	}
	private OnDismissListener onDismissListener=new OnDismissListener(){

		@Override
		public void onDismiss() {
			// TODO Auto-generated method stub
			switch(pressButtonID)
			{
			case R.id.Menu_Main_center:
				Intent intent=new Intent();
				intent.setClass(mActivity.getContext(),MainActivity.class);
				mActivity.getContext().startActivity(intent);
				break;
			case R.id.Menu_Collect_center:
				Intent intent2=new Intent();
				intent2.setClass(mActivity.getContext(),CollectCenterActivity.class);
				mActivity.getContext().startActivity(intent2);
				break;
			case R.id.Menu_Remind_center:
				Intent intent3=new Intent();
				intent3.setClass(mActivity.getContext(),RemindCenter.class);
				mActivity.getContext().startActivity(intent3);
				break;
			}
			pressButtonID=0;
		}
		
	};
	@Override
	public void showAsDropDown(View view,int x,int y)
	{
		super.showAsDropDown(view, x, y);
		initCollectButton();
	}
	//初始化所有的buttom
	public void initCollectButton()
	{
		collectLayout.removeAllViews();
		for(int i=0;i<10&&i<BaseAppClient.RecentStation.size();i++)
		{
			MenuCollectButton menucollect=new MenuCollectButton(menuShowView.getContext()); 
			menucollect.setStation(BaseAppClient.RecentStation.get(i).getStationID());
			menucollect.setMenuCollectClickListener(mcbListener);
			//collectButtonList.add(menucollect);
			collectLayout.addView(menucollect);
			collectLayout.invalidate();
			
		}
	}
	public static MenuWindow getInstance( BaseUI activity,int width, int height)
	{
		
		if(menu==null)
		{	
			mActivity=activity;
			menuShowView = mActivity.getLayoutInflater().inflate(R.layout.menu_layout,  
	                null, false);
			menu=new MenuWindow(width,height);
		}
		//Log.d("");
		mActivity=activity;
		return menu;
	}
	
	private OnTouchListener onTouchListener=new OnTouchListener(){

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// TODO Auto-generated method stub
			
			return true;
		}		
	};
	
	private OnClickListener listener=new OnClickListener(){

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
			case R.id.menu_bottom_layout:
				((View)menuShowView.findViewById(R.id.menu_botton_layout_inbutton)).requestFocus();
				MenuWindow.this.dismiss();
				break;
			case R.id.Menu_Main_center:
				//站点
				//if(mActivity)
				pressButtonID=R.id.Menu_Main_center;
				
				dismiss();
				break;
			case R.id.Menu_Collect_center:
				//收藏
				
				pressButtonID=R.id.Menu_Collect_center;
				dismiss();
				break;
			case R.id.Menu_Remind_center:
				pressButtonID=R.id.Menu_Remind_center;
				dismiss();
				//提醒
				break;
			}
		}
		
	};
	
	private MenuCollectButtonListener mcbListener=new MenuCollectButtonListener(){

		@Override
		public void stationClick(int StationID) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void deleteClick(int StationID) {
			// TODO Auto-generated method stub
			removeRecentButton(StationID);
		}
		
	};
	
	public void addRecentButton(int StationID)
	{
		BaseAppClient.addRecentStation(StationID);
		MenuCollectButton menucollect=new MenuCollectButton(menuShowView.getContext()); 
		menucollect.setStation(StationID);
		menucollect.setMenuCollectClickListener(mcbListener);
		collectLayout.addView(menucollect, 0);
	}
	
	public void removeRecentButton(int StationID)
	{
		BaseAppClient.removeRecentStation(StationID);
		//MenuCollectButton menucollect=new MenuCollectButton(menuShowView.getContext());
		for(int i=0;i<collectLayout.getChildCount();i++)
		{
			if(((MenuCollectButton)collectLayout.getChildAt(i)).getStation().getStationID()==StationID)
			{
				collectLayout.removeViewAt(i);
				return ;
			}
		}
	}
}
