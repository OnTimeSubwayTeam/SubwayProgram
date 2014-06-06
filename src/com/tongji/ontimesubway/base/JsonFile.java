package com.tongji.ontimesubway.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tongji.ontimesubway.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

public class JsonFile {
	
	private Context mContext;
	
	/**
	 * 构造器
	 * 
	 * @param context
	 * 
	 */
	
	public JsonFile(Context context)
	{
		mContext=context;
	}
	
	
	/**
	 * 读Json文件 得到 Json对象
	 * 
	 * @param fileURL
	 * 
	 * @return JsonObject
	 */
	
	public JSONObject readJsonFromFile(String fileRUL)
	{
		return new JSONObject();
	}
	/**
	 * 将json对象写入文件里
	 * @param Json //要写入的对象
	 * @return boolean //成功 true 失败 false
	 */
	public boolean writeJsonToFile(JSONObject json, String fileURL)
	{
		File file=new File(BaseKey.BaseFileDir+fileURL);
		if(!file.getParentFile().exists())
		{
			file.getParentFile().mkdir();
		}
		
		// 写入内存卡  
        PrintStream outputStream = null;  
        try {  
            outputStream = new PrintStream(new FileOutputStream(file));  
            outputStream.print(json.toString());  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } finally {  
            if (outputStream != null) {  
                outputStream.close();  
            }  
        }  
		return true;
	}
	/**
	 * 读取站点基本信息
	 * 
	 * @param null
	 * @return HashMap
	 * 
	 */
	
	public HashMap<Integer,StationNote> readStationNote() 
	{
		HashMap<Integer, StationNote> StationMap=new HashMap<Integer, StationNote>();
		
		//读取R。raw.stationjson里的内容
		Resources res=mContext.getResources();
		InputStream in=null;
		BufferedReader br=null;
		//StringBuffer sb=new StringBuffer();
		try{
			in=res.openRawResource(R.raw.stationjson);
			br=new BufferedReader(new InputStreamReader(in,"GBK"));
			String JsonStr=null;
			while((JsonStr=br.readLine())!=null)
			{
				//把JsonStr变为JsonObject
				JSONObject json=new JSONObject(JsonStr);
				//把JsonObject 变成  StationNote实例
				StationNote station=(StationNote)this.Json2Object(json,"StationNote");
				//将StationNote 加入Map里
				StationMap.put(station.getStationID(), station);
			}
		}
		catch(NotFoundException e)
		{
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return StationMap;
	}
	/**
	 * 
	 * 读取route的基本信息
	 * 
	 * @return HashMap<Intger,StationRoute 存有route 的hashmap Key-routeID, value- StationRoute
	 */
	public HashMap<Integer,Route> readStationRoute()
	{
		HashMap<Integer, Route> RouteMap=new HashMap<Integer, Route>();
		
		//读取R。raw.stationjson里的内容
		Resources res=mContext.getResources();
		InputStream in=null;
		BufferedReader br=null;
		//StringBuffer sb=new StringBuffer();
		try{
			in=res.openRawResource(R.raw.routejson);
			br=new BufferedReader(new InputStreamReader(in,"GBK"));
			String JsonStr=null;
			while((JsonStr=br.readLine())!=null)
			{
				//把JsonStr变为JsonObject
				JSONObject json=new JSONObject(JsonStr);
				//把JsonObject 变成  Route实例
				Route route=(Route)this.Json2Object(json,"Route");
				//将StationNote 加入Map里
				RouteMap.put(route.getID(), route);
			}
		}
		catch(NotFoundException e)
		{
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return RouteMap;
	}
	
	/**
	 * 将JSONOJect 实例转变为ObjectName的实例
	 * 
	 * @param json
	 * @param OjbectName  OjbectName必须是com.tongji.ontimesubway.base下的类
	 * @return OjbectName对象的实例
	 */
	
	public Object Json2Object(JSONObject json,String OjbectName)
	{
		String ObjectURL=(new StringBuffer("com.tongji.ontimesubway.base.")).append(OjbectName).toString();
		Object objectinstance=null;
		Class object=null;
		try {
			object=Class.forName(ObjectURL);
			objectinstance=object.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    @SuppressWarnings("unchecked")
		Iterator<String>it=json.keys();
	    while(it.hasNext())
	    {
	    	//获得域名
	    	String varField=it.next();
	    	Field field=null;
	    	try {
				field=object.getDeclaredField(varField);
				//设置为可修改
				field.setAccessible(true);
				//Log.d("JsonFile",field.getType().toString());
				//如查是String
				if(field.getType().equals(String.class))
				{
					String value=json.getString(varField);
					field.set(objectinstance, value);
				}//旭查是integer
				else if(field.getType().equals(Integer.class)||field.getType().equals(int.class))
				{
					int value=json.getInt(varField);
					field.set(objectinstance, value);
				}//如查是ArrayList
				else if (field.getType().equals(ArrayList.class)){
					//
					JSONArray jsonArray=json.getJSONArray(varField);
					ArrayList<Integer>list=new ArrayList<Integer>(10);
					for(int i=0;i<jsonArray.length();i++)
					{
						list.add(jsonArray.getInt(i));
					}
					field.set(objectinstance, list);
				}
				
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    return objectinstance;
	}

}
