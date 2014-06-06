package com.tongji.ontimesubway.base;

import java.io.File;

import android.os.Environment;

public class BaseKey {
	public static int Real_Time_Update_OK		= 1;
	public static int Real_Time_Update_Error	= 0;
	public static String BaseFileDir			= Environment.getExternalStorageDirectory()+File.pathSeparator+"onTimeSubway"+File.pathSeparator;
	
	
	public static String BaseApi				= "";
	public static String[] lineColor={"#e20333","#7bc922","#f8dd07","#500576","#a64c94","#d10164","#ef7616","#059bd8","#98d1e6","#d1b2d0","#741229"};
	public class error{
		public static final String network		= "ÍøÂç´íÎó";
	}
}
