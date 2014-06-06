package com.tongji.ontimesubway.base;

import com.tongji.ontimesubway.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class BaseUI extends Activity{
	protected Button titleLeft;
	protected Button titleRight;
	protected TextView titleCenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//×Ô¶¨Òåtitlebar
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	}
	protected void setTitleBar()
	{
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
		this.titleLeft=(Button)findViewById(R.id.title_left);
		this.titleCenter=(TextView)findViewById(R.id.title_center);
		this.titleRight=(Button)findViewById(R.id.title_right);
		
	}

}
