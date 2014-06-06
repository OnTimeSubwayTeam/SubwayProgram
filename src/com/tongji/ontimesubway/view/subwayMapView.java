package com.tongji.ontimesubway.view;

import com.tongji.ontimesubway.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class subwayMapView extends View{

	private Canvas canvas;
	private float bitmapX;
	private float bitmapY;
	private boolean initDraw=true;
	public subwayMapView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	public subwayMapView(Context context)
	{
		super(context);
		
	}
	public subwayMapView(Context context,AttributeSet attrs)
	{
		super(context,attrs);
	}
	// 重写该方法，进行绘图  
    @SuppressLint("DrawAllocation")
	@Override  
    protected void onDraw(Canvas canvas) { 
    	if(this.canvas==null)
    		this.canvas=canvas;
        super.onDraw(this.canvas);    
    }
    public void initShow(){
        	Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.subway);
        	//Paint paint=new Paint();
        	this.canvas=new Canvas(bitmap);
        	this.bitmapX=bitmap.getWidth();
        	this.bitmapY=bitmap.getHeight();
        	this.invalidate();
        	
    }
    public void showAll()
    {
    	float scaleX=this.getWidth()/this.bitmapX;
    	float scaleY=this.getHeight()/this.bitmapY;
    	Bitmap bitmap=this.getDrawingCache();
    	initDraw=false;
    	this.invalidate();
    }

}
