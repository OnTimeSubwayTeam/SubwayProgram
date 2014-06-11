package com.tongji.ontimesubway.base;

import java.util.ArrayList;
import java.util.Iterator;

public class Route {
	private ArrayList<Train> trainGroup;
	protected int routeID;
	protected String routeName;
	protected ArrayList<Integer> stationGroup;
	protected StationNote endStation;
	
	public Route()
	{
		trainGroup=null;
		routeID=0;
		this.routeName=null;
		stationGroup=new ArrayList<Integer>();
		endStation=null;
	}
	public String getName()
	{
		return this.routeName;
	}
	public int getID()
	{
		return this.routeID;
	}
	public void setStationGroup(ArrayList<Integer> stationgroup)
	{
		this.stationGroup=stationgroup;
		endStation=BaseAppClient.getStation(stationgroup.get(stationGroup.size()-1));
	}
	public ArrayList<Integer> getStationGroup()
	{
		return this.stationGroup;
	}
	public StationNote searchStation(int stationID)
	{
		for(int i=0;i<stationGroup.size();i++)
		{
			if(stationGroup.get(i)==stationID)
				return BaseAppClient.getStation(stationGroup.get(i));
		}
		return null;
	}
	public StationNote getFirstStation()
	{
		return this.stationGroup==null ? null:BaseAppClient.getStation(this.stationGroup.get(0));
	}
	public StationNote getLastStation()
	{
		return this.stationGroup==null ? null: BaseAppClient.getStation(this.stationGroup.get(this.stationGroup.size()-1));
		
	}

}
