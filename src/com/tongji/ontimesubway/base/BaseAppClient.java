package com.tongji.ontimesubway.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tongji.ontimesubway.network.ImageCache;
import com.tongji.ontimesubway.network.NetTask;
import com.tongji.ontimesubway.network.NetTaskPool;

import android.app.Application;
import android.util.Log;

public class BaseAppClient extends Application{
	
	//记录所有站点的HashMap
	public static HashMap<Integer,StationNote> StationMap;
	public static HashMap<Integer,Route> RouteMap;
	//记录所有收藏的站点与路线
	public static HashMap<Integer, CollectRoute> CollectRouteMap;
	public static ArrayList<Integer> CollectStation;
	public static ArrayList<RecentStation> RecentStation;
	private static final int RecentCount=20;
	private static JsonFile jsonHandler;
	private static NetTaskPool taskpool;
	public static ImageCache imageCache=ImageCache.getInstance();
	
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
		
		//初始化收藏路线
		CollectRouteMap=jsonHandler.readCollectRoute();
		CollectStation=jsonHandler.readCollectStation();
		
		RecentStation=jsonHandler.readRecentStation();
		//
		imageCache.initImageCache(this.getApplicationContext());
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
	//添加收藏路线
	public static void addCollectRoute(CollectRoute collectRoute)
	{
		//如果collectRouteMap 里没有内容，则对collectRouteid =1000;
		if(CollectRouteMap.size()<=0)
		{
			collectRoute.setID(BaseKey.FirstCollectRouteID);
			CollectRouteMap.put(BaseKey.FirstCollectRouteID, collectRoute);
		}
		else 
		{
			if(isInCollectRoute(collectRoute))
				return ;
			Iterator it=CollectRouteMap.entrySet().iterator();
			int lastID=-1;
			while(it.hasNext())
			{
				Map.Entry entry = (Map.Entry) it.next();
				lastID=((CollectRoute) entry.getValue()).getID();
			}
			collectRoute.setID(lastID+1);
			CollectRouteMap.put(lastID+1, collectRoute);
		}
		jsonHandler.writeMapToFile(CollectRouteMap, BaseKey.CollectRouteFile);
	}
	public static void removeCollectRoute(CollectRoute collectRoute)
	{
		//Log.d("baseAppClient",String.valueOf(collectRoute.getID())+" "+CollectRouteMap.get(collectRoute.getID()));
		int id=-1;
		Iterator it=CollectRouteMap.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry) it.next();
			if(((CollectRoute)entry.getValue()).equals(collectRoute))
			{
				id=((CollectRoute)entry.getValue()).getID();
				break;
			}
		}
		CollectRouteMap.remove(id);
		jsonHandler.writeMapToFile(CollectRouteMap, BaseKey.CollectRouteFile);
	}
	public static void addCollectStation(int StationID)
	{
		if(!isInCollectStation(StationID))
		{
			CollectStation.add(StationID);
			jsonHandler.writeListToFile(CollectStation, BaseKey.CollectStationFile);
		}
	}
	
	public static void removeCollectStation(int StationID)
	{
		for(int i=0;i<CollectStation.size();i++)
		{
			if(CollectStation.get(i)==StationID)
			{
				CollectStation.remove(i);
				break;
			}
		}
		jsonHandler.writeListToFile(CollectStation, BaseKey.CollectStationFile);
	}
	
	public static boolean isInCollectRoute(CollectRoute route)
	{
		Iterator it=CollectRouteMap.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry) it.next();
			if(((CollectRoute)entry.getValue()).equals(route))
			{
				return true;
			}
		}
		return false;
	}
	public static boolean isInCollectStation(int StationID)
	{
		for(int i=0;i<CollectStation.size();i++)
		{
			if(CollectStation.get(i)==StationID)
			{
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList<CollectRoute> getCollectRouteList()
	{
		ArrayList<CollectRoute> list=new ArrayList<CollectRoute>();
		Iterator it=CollectRouteMap.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry) it.next();
			list.add((CollectRoute)entry.getValue());
		}
		return list;		
	}
	
	public static void readStaion(int StationID)
	{
		
		RecentStation removestation=null;
		if(RecentStation.size()>0)
			removestation=RecentStation.get(0);
		for(int i=0;i<RecentStation.size();i++)
		{
			if(RecentStation.get(i).getStationID()==StationID)
			{
				RecentStation.get(i).addCount();
				jsonHandler.writeListToFile(RecentStation, BaseKey.RecentStationFile);
				return;
			}
			if(removestation==null||RecentStation.get(i).getCount()<removestation.getCount())
			{
				removestation=RecentStation.get(i);
			}
		}
		if(RecentStation.size()<RecentCount)
		{
			RecentStation rs=new RecentStation(StationID,1);
			RecentStation.add(rs);
			jsonHandler.writeListToFile(RecentStation, BaseKey.RecentStationFile);
			Log.d("BaseAppClient ","RecentStation size ="+String.valueOf(RecentStation.size()));
			return ;
		}
		else {
			RecentStation.remove(removestation);
			RecentStation rs=new RecentStation(StationID,1);
			RecentStation.add(rs);
		}
		jsonHandler.writeListToFile(RecentStation, BaseKey.RecentStationFile);
		return ;
	}
	public static void addRecentStation(int StationID)
	{
		//boolean isAlread=false;
		RecentStation addRS=new RecentStation(StationID,10);
		RecentStation.add(0, addRS);
		//去除一个最小的
		if(RecentStation.size()>RecentCount)
		{
			RecentStation removestation=RecentStation.get(1);
			for(int i=1;i<RecentStation.size();i++)
			{
				if(RecentStation.get(i).getCount()<removestation.getCount())
				{
					removestation=RecentStation.get(i);
				}
			}
			RecentStation.remove(removestation);
		}
		jsonHandler.writeListToFile(RecentStation, BaseKey.RecentStationFile);
	}
	public static void removeRecentStation(int StationID)
	{
		for(int i=0;i<RecentStation.size();i++)
		{
			if(RecentStation.get(i).getStationID()==StationID)
			{
				RecentStation.remove(i);
				jsonHandler.writeListToFile(RecentStation, BaseKey.RecentStationFile);
				return ;
			}
		}
	}
	//StationID对应的stationNote 是否在菜单 里
	public static boolean isInRecentStation(int StatoinID)
	{
		boolean isIn=false;
		for(int i=0;i<RecentStation.size();i++)
		{
			if(RecentStation.get(i).getStationID()==StatoinID)
			{
				isIn=true;
				break;
			}
		}
		return isIn;
	}
}
