package com.tongji.ontimesubway.base;

import android.text.format.Time;

public class Train {

	protected int trainID;
	protected String name;
	protected Time beginTime;
	protected Time endTime;
	protected int speed;
	protected StationNote currentPosition;
	protected int dirtection;
	
	
	public Train()
	{
		trainID=0;
		name=null;
		beginTime=null;
		endTime=null;
		speed=0;
		currentPosition=null;
		dirtection=-1;
		
	}
	public int getTrainID()
	{
		return this.trainID;
	}
	public String getName()
	{
		return this.name;
	}
	public Boolean run()
	{
		return true;
	}
	public int TimegetDisNextStation()
	{
		return 1;
	}
	
}
