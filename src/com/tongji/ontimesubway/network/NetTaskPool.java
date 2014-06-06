package com.tongji.ontimesubway.network;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetTaskPool {
	// task thread pool
	private ExecutorService taskPool;
	public static NetTaskPool nettaskpool=new NetTaskPool();
	private NetTaskPool(){
		//创建一个并发线程数为5的线程池
		taskPool=Executors.newFixedThreadPool(5);
	}
	public static NetTaskPool getInstance()
	{
		return nettaskpool;
	}

	@SuppressWarnings("unchecked")
	public void addTask(String url,HashMap<String,String> param,int taskID,NetTask nettask)
	{
		NetAsynctask task=new NetAsynctask(url,param,nettask,taskID);
		task.executeOnExecutor(taskPool, param);
	}
	
	public void addTask(String url,int taskID,NetTask nettask)
	{
		addTask(url,null,taskID,nettask);
	}
	
}
