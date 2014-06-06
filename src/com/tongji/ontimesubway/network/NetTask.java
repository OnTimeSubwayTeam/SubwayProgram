package com.tongji.ontimesubway.network;

public interface NetTask {

	/**
	 * 任务开始前
	 */
	public void onPreExecute(NetAsynctask Task);
	/**
	 * 任务结束后的UI 更新
	 * @param result
	 */
	public void onPostExecute(String result);
	/**
	 * 任务失败后调用
	 * @param e
	 */
	public void onError(Exception e);
	
}
