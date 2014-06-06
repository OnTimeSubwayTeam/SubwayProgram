package com.tongji.ontimesubway.base;

import java.util.HashMap;

import com.tongji.ontimesubway.network.NetTask;
import com.tongji.ontimesubway.network.NetTaskPool;

import android.app.Application;

public class BaseAppClient extends Application{
	
	//记录所有站点的HashMap
	public static HashMap<Integer,StationNote> StationMap;
	public static HashMap<Integer,Route> RouteMap;
	private JsonFile jsonHandler;
	private static NetTaskPool taskpool;
	/**
	 *初始化各类数据
	 *
	 */
	@Override
	public void onCreate(){
		
		//初始化站点信息和线路信息
		jsonHandler=new JsonFile(this.getApplicationContext());
		StationMap=jsonHandler.readStationNote();
		RouteMap=jsonHandler.readStationRoute();
		
		//get the nettaskpool
		taskpool=NetTaskPool.getInstance();
	}
	
	/**
	 * 获得stationID 对应的stationNote
	 * @param stationID
	 * @return StationNote 
	 */
	
	public static StationNote getStation(Integer stationID)
	{
		return StationMap.get(stationID);
	}
	public static Route getRoute(Integer routeID)
	{
		return RouteMap.get(routeID);
	}
	public static int getRouteSize()
	{
		return RouteMap.size();
	}
	
	public static void doNetWork(String url,HashMap<String,String> param,int taskID,NetTask nettask)
	{
		taskpool.addTask(url,param, taskID, nettask);
	}
	public static void doNetWork(String url, int taskID,NetTask nettask)
	{
		taskpool.addTask(url, taskID, nettask);
	}
}
