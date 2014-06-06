package com.tongji.ontimesubway.base;

import android.graphics.drawable.Drawable;

public class CollectRoute extends Route{

	private int price ;
	private int countPrice()
	{
		return 0;
	}
	
	public Boolean addStation(StationNote adds)
	{
		if(this.stationGroup.add(adds.getStationID()))
			return true;
		else 
			return false;
	}
	public Boolean removeStation(int stationID)
	{
		StationNote target=null;
		if((target=this.searchStation(stationID))!=null)
		{
			this.stationGroup.remove(target);
			return true;
		}
		return false;
	}
	public int getPrice()
	{
		return this.countPrice();
	}
	public Drawable getRouteMap()
	{
		return null;
	}
	public void setName(String name)
	{
		super.routeName=name;
	}
	public void setEndStation(StationNote end)
	{
		this.endStation=end;
	}
	
}
