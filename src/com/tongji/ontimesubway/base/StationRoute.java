package com.tongji.ontimesubway.base;

/**
 * 用于实时屏显的类
 * 
 * @author Rong
 *
 */
public class StationRoute {
	
	private String routeName;
	private int routeID;
	private int stationID;
	private String stationName;
	//最多显示三辆车
	private int trainTime[];

	public StationRoute()
	{
		trainTime=new int[3];
	}
	public StationRoute(String routeName, int routeId,String stationName,int stationID)
	{
		this.trainTime=new int[3];
		this.setRouteName(routeName);
		this.setRouteID(routeId);
		this.setStationName(stationName);
		this.setStationID(stationID);
	}
	/**
	 * init time that trains arrive the station 
	 */
	public void InitTime(int first,int second,int third)
	{
		trainTime[0]=first;
		trainTime[1]=second;
		trainTime[2]=third;
	}
	public StringBuffer getTime(int position)
	{
		StringBuffer timeString = new StringBuffer();
		if(trainTime[position]>0){
			timeString.append(trainTime[position]/60);
			timeString.append(":");
			if(trainTime[position]%60<10)
				timeString.append("0");
			timeString.append(trainTime[position]%60);
		}
		else 
		{
			timeString.append("不明时间 ");
		}
		return timeString;
	}
	/**
	 * make the time decrease 1 self
	 */
	public void timerun()
	{
		for(int i=0;i<3;i++)
		{
			trainTime[i]--;
		}
		
		//对于0的处理
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public int getRouteID() {
		return routeID;
	}
	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}
	public int getStationID() {
		return stationID;
	}
	public void setStationID(int stationID) {
		this.stationID = stationID;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	/**
	 * 只要认为两个实例的routeID相等，则认为两个对象是相等的
	 * @param obj2
	 * @return
	 */
	public boolean equals(StationRoute obj2) 
	{
		if(this.getRouteID()==obj2.getRouteID())
			return true;
		else 
			return false;
	}
	/**
	 * 将时间更新到obj2的时间
	 * @param obj2
	 */
	public void updateTime(StationRoute obj2)
	{
		this.trainTime[0]=obj2.trainTime[0];
		this.trainTime[1]=obj2.trainTime[1];
		this.trainTime[2]=obj2.trainTime[2];
	}
}
