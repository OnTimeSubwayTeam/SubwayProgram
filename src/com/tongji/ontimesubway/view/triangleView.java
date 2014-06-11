package com.tongji.ontimesubway.view;

import com.tongji.ontimesubway.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class triangleView extends View{

	public triangleView(Context context)
	{
		super(context);
	}
	public triangleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	//画三角形
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		Paint p=new Paint();
		p.setColor(this.getResources().getColor(R.color.grey));
		Path path = new Path(); 
		Log.d("",String.valueOf(canvas.getWidth()));
        path.moveTo(25, 0);// 此点为多边形的起点  
        path.lineTo(0, 50);  
        path.lineTo(50, 50);  
        path.close(); // 使这些点构成封闭的多边形  
        canvas.drawPath(path, p);
	}

}
