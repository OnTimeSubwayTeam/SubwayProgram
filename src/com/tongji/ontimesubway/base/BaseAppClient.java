package com.tongji.ontimesubway.base;

import java.util.HashMap;

import com.tongji.ontimesubway.network.NetTask;
import com.tongji.ontimesubway.network.NetTaskPool;

import android.app.Application;

public class BaseAppClient extends Application{
	
	//��¼����վ���HashMap
	public static HashMap<Integer,StationNote> StationMap;
	public static HashMap<Integer,Route> RouteMap;
	private JsonFile jsonHandler;
	private static NetTaskPool taskpool;
	/**
	 *��ʼ����������
	 *
	 */
	@Override
	public void onCreate(){
		
		//��ʼ��վ����Ϣ����·��Ϣ
		jsonHandler=new JsonFile(this.getApplicationContext());
		StationMap=jsonHandler.readStationNote();
		RouteMap=jsonHandler.readStationRoute();
		
		//get the nettaskpool
		taskpool=NetTaskPool.getInstance();
	}
	
	/**
	 * ���stationID ��Ӧ��stationNote
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
