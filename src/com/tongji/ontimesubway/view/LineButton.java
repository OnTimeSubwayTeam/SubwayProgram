package com.tongji.ontimesubway.view;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.base.BaseKey;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


public class LineButton extends LinearLayout {
	
	private TextView lineNumber;
	private TextView ElineName;
	
	public LineButton(Context context)
	{
		super(context);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.linebutton, this);
		init();
	}
	
	public LineButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.linebutton, this);
		 init();
	}
	public void init()
	{
		this.lineNumber=(TextView)findViewById(R.id.lineNumber);
		this.ElineName=(TextView)findViewById(R.id.ElineName);
	}
	public void setLineNumber(int lineNumber)
	{
		if(lineNumber>BaseKey.lineColor.length)
			return ;
		this.lineNumber.setText(String.valueOf(lineNumber));
		this.ElineName.setText("line "+String.valueOf(lineNumber));
		this.lineNumber.setBackgroundColor(Color.parseColor(BaseKey.lineColor[lineNumber-1]));
	}

}
