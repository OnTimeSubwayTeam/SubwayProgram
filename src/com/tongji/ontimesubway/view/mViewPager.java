package com.tongji.ontimesubway.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class mViewPager extends ViewPager{

	public mViewPager(Context context)
	{
		super(context);
	}
	public mViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		return false;
	}

}
