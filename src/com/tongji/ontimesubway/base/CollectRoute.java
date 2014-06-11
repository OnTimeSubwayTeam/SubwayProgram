package com.tongji.ontimesubway.base;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

public class CollectRoute extends Route{

	private int price ;
	private int time;
	private int countPrice()
	{
		return price;
	}
	public CollectRoute()
	{
		super();
	}
	public CollectRoute(ArrayList<Integer> sationgroup,int price,int time)
	{
		this.price=price;
		this.time=time;
		this.stationGroup=sationgroup;
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

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	public void setID(int id)
	{
		this.routeID=id;
	}
	@Override
	public String toString()
	{
		///routeID
		StringBuffer str=new StringBuffer("{\"routeID\"");
		str.append(':');
		str.append(this.getID());
		str.append(',');
		///price
		str.append("\"price\":");
		str.append(this.price);
		str.append(',');
		//time
		str.append("\"time\":");
		str.append(this.time);
		str.append(',');
		////routeName
		str.append("\"routeName\"");
		str.append(':');
		str.append('"');
		str.append(this.getName());
		str.append('"');
		str.append(',');
		///stationGroup
		str.append("\"stationGroup\":");
		str.append('[');
		for(int i=0;i<this.getStationGroup().size();i++)
		{
			str.append(this.getStationGroup().get(i));
			if(i<this.getStationGroup().size()-1)
				str.append(',');
		}
		str.append(']');
		str.append('}');
		return str.toString();
	}
	
	public boolean equals(CollectRoute obj2)
	{
		if(this.getID()==obj2.getID()) 
			return true;
		//头尾与中间的都相等，则说明相等 
		if(this.getFirstStation().getStationID()==obj2.getFirstStation().getStationID()&&
				this.getLastStation().getStationID()==obj2.getLastStation().getStationID()&&
				this.stationGroup.get(this.stationGroup.size()/2)==obj2.stationGroup.get(obj2.stationGroup.size()/2))
		{
			return true;
		}
		else 
			return false;
	}
	
}
