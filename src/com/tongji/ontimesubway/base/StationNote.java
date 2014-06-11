package com.tongji.ontimesubway.base;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.drawable.Drawable;

public class StationNote {
	private int stationID;
	private String name;
	private ArrayList<String> mapURL;
	private String info;
	public StationNote next;
	public StationNote fore;
	public ArrayList<Integer> routeGroup;
	//isWC=0: ÎÞwc£¬ isWC=1; ÓÐwc
	private int isWC;
	public StationNote()
	{
		setStationID(0);
		setName(null);
		setMapURL(null);
		next=null;
		fore=null;
		routeGroup=null;
		setIsWC(0);
	}
	public StationNote(int id,String name,StationNote next, StationNote fore, ArrayList<Integer> routegroup)
	{
		this.stationID=id;
		this.name=name;
		this.next=next;
		this.fore=fore;
		this.routeGroup=routegroup;
		setIsWC(0);
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
	public ArrayList<String> getMapURL() {
		return this.mapURL;
	}
	public void setMapURL(ArrayList<String> mapURL) {
		this.mapURL = mapURL;
	}

	public String getRouteName()
	{
		StringBuffer routeName = new StringBuffer();
		Iterator<Integer> it=routeGroup.iterator();
		while(it.hasNext()){
			routeName.append(it.next().toString());
			if(it.hasNext())
				routeName.append(',');
		}
		routeName.append("Ïß");
		return routeName.toString();
	}
	public ArrayList<Integer> getRouteGroup()
	{
		return this.routeGroup;
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
	public int getIsWC() {
		return isWC;
	}
	public void setIsWC(int isWC) {
		this.isWC = isWC;
	}
}
