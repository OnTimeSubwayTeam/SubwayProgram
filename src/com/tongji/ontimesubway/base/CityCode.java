package com.tongji.ontimesubway.base;

import java.util.HashMap;

public class CityCode {
	public static HashMap<Integer,String > cityInfo = new HashMap<Integer, String >();
	static {
		cityInfo.put(001, "����");
		cityInfo.put(002, "�Ϻ�");
	}
	public static String getCityName(int cityID)
	{
		return cityInfo.get(cityID);
	}

}
