package com.tongji.ontimesubway.base;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.drawable.Drawable;

public class StationNote {
	private int stationID;
	private String name;
	private String mapURL;
	private String info;
	public StationNote next;
	public StationNote fore;
	public ArrayList<Integer> routeGroup;
	public StationNote()
	{
		setStationID(0);
		setName(null);
		setMapURL(null);
		next=null;
		fore=null;
		routeGroup=null;
	}
	public StationNote(int id,String name, String mapurl,StationNote next, StationNote fore, ArrayList<Integer> routegroup)
	{
		this.stationID=id;
		this.name=name;
		this.mapURL=mapurl;
		this.next=next;
		this.fore=fore;
		this.routeGroup=routegroup;
	}
	public int getStationID() {
		return stationID;
	}
	public void setStationID(int stationID) {
		this.stationID = stationID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMapURL() {
		return mapURL;
	}
	public void setMapURL(String mapURL) {
		this.mapURL = mapURL;
	}

	public String getRouteName()
	{
		StringBuffer routeName = new StringBuffer("µØÌú ");
		Iterator<Integer> it=routeGroup.iterator();
		while(it.hasNext()){
			routeName.append(it.next().toString());
			if(it.hasNext())
				routeName.append(',');
		}
		routeName.append("Ïß");
		return routeName.toString();
	}
	public Drawable getMap()
	{
		return null;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
}
