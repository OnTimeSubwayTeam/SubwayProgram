package com.tongji.ontimesubway.base;

public class RecentStation {
	private int stationID;
	private int count;
	public RecentStation()
	{
		
	}
	public RecentStation(int id,int count)
	{
		this.stationID=id;
		this.count=count;
	}
	public int getCount()
	{
		return this.count;
	}
	public int getStationID()
	{
		return this.stationID;
	}
	public void addCount()
	{
		this.count++;
	}
	@Override
	public String toString()
	{
		return String.valueOf(stationID)+" "+String.valueOf(count);
	}
	public static RecentStation getFromString(String str)
	{
		RecentStation rs=new RecentStation();
		String[] fields=str.split(" ");
		rs.stationID=Integer.parseInt(fields[0]);
		rs.count=Integer.parseInt(fields[1]);
		return rs;
	}

}
