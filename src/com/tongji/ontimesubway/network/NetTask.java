package com.tongji.ontimesubway.network;

public interface NetTask {

	/**
	 * ����ʼǰ
	 */
	public void onPreExecute(NetAsynctask Task);
	/**
	 * ����������UI ����
	 * @param result
	 */
	public void onPostExecute(String result);
	/**
	 * ����ʧ�ܺ����
	 * @param e
	 */
	public void onError(Exception e);
	
}
