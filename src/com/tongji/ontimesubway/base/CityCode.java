package com.tongji.ontimesubway.base;

import java.util.HashMap;

public class CityCode {
	public static HashMap<Integer,String > cityInfo = new HashMap<Integer, String >();
	static {
		cityInfo.put(001, "北京");
		cityInfo.put(002, "上海");
	}
	public static String getCityName(int cityID)
	{
		return cityInfo.get(cityID);
	}

}
