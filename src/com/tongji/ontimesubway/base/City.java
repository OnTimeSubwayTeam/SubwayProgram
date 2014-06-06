package com.tongji.ontimesubway.base;

public class City {
	private int cityID;
	private String name;
	public int getCityID() {
		return cityID;
	}
	public City()
	{
		cityID=0;
		name=null;
	}
	public City(int id)
	{
		cityID=id;
		name=CityCode.getCityName(id);
	}
	public City(int id,String name)
	{
		cityID=id;
		this.name=name;
	}
	public void setCityID(int cityID) {
		this.cityID = cityID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
