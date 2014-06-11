package com.tongji.ontimesubway.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class StationNoteCenterView extends View{

	private int color=Color.RED;
	public StationNoteCenterView(Context context)
	{
		super(context);
	}
	public StationNoteCenterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		Paint p=new Paint();
		p.setColor(color);
		p.setAntiAlias(true);
		canvas.clipRect(0, 0,40, 40);
		canvas.drawCircle(20, 20, 20, p);
		
		
	}
	
	public void setColor(int color)
	{
		this.color=color;
		this.invalidate();
	}

}
