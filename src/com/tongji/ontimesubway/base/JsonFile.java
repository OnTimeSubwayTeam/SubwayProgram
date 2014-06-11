package com.tongji.ontimesubway.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tongji.ontimesubway.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

public class JsonFile {
	
	private Context mContext;
	
	/**
	 * ������
	 * 
	 * @param context
	 * 
	 */
	
	public JsonFile(Context context)
	{
		mContext=context;
	}
	
	
	/**
	 * ��Json�ļ� �õ� Json����
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
	 * ��json����д���ļ���
	 * @param Json //Ҫд��Ķ���
	 * @return boolean //�ɹ� true ʧ�� false
	 */
	public boolean writeJsonToFile(JSONObject json, String fileURL)
	{
		File file=new File(BaseKey.BaseFileDir+fileURL);
		if(!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
		
		// д���ڴ濨  
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
	public <T> boolean writeListToFile(List<T> list, String fileURL)
	{
		File file=new File(BaseKey.BaseFileDir+fileURL);
		if(!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
		 PrintStream outputStream = null;  
	        try {  
	            outputStream = new PrintStream(new FileOutputStream(file)); 
	            for(int i=0;i<list.size();i++)
	            	outputStream.println(((T)list.get(i)).toString());
	            
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        }  finally {  
	            if (outputStream != null) {  
	                outputStream.close();  
	            }  
	        }  
			return true;
	}
	/**
	 * ��ȡ�ղص�վռ
	 * @return
	 */
	public ArrayList<Integer> readCollectStation()
	{
		ArrayList<Integer> list=new ArrayList<Integer>();
		InputStream in=null;
		BufferedReader br=null;
		//StringBuffer sb=new StringBuffer();
		try{
			File file=new File(BaseKey.BaseFileDir+BaseKey.CollectStationFile);
			in=new FileInputStream(file);
			Scanner scanner=new Scanner(in);
			while(scanner.hasNext())
			{
				list.add(scanner.nextInt());
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally{
			try {
				if(in!=null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public ArrayList<RecentStation> readRecentStation()
	{
		ArrayList<RecentStation> list=new ArrayList<RecentStation>();
		InputStream in=null;
		BufferedReader br=null;
		//StringBuffer sb=new StringBuffer();
		try{
			File file=new File(BaseKey.BaseFileDir+BaseKey.RecentStationFile);
			in=new FileInputStream(file);
			br=new BufferedReader(new InputStreamReader(in,"GBK"));
			String JsonStr=null;
			while((JsonStr=br.readLine())!=null)
			{
				list.add(RecentStation.getFromString(JsonStr));
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally{
			try {
				if(in!=null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
	/**
	 * ��map ���valueֵ��json����ʽд��File�ÿ��valueռһ��
	 * @param <T>
	 * @return
	 */
	public <T> boolean writeMapToFile(HashMap<?,T> map,String fileURL)
	{
		File file=new File(BaseKey.BaseFileDir+fileURL);
		if(!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
			Log.d("create","create");
		}
			
		
		// д���ڴ濨  
        PrintStream outputStream = null;  
        try {  
            outputStream = new PrintStream(new FileOutputStream(file)); 
            Iterator it=map.entrySet().iterator();
            while (it.hasNext()) {
            	Map.Entry entry = (Map.Entry) it.next();
            	T value = (T) entry.getValue();
            	JSONObject json=new JSONObject(value.toString());
            	outputStream.print(json.toString());
            	outputStream.print("\r\n");
            	}  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {  
            if (outputStream != null) {  
                outputStream.close();  
            }  
        }  
		return true;
	}
	/**
	 * ��ȡվ�������Ϣ
	 * 
	 * @param null
	 * @return HashMap
	 * 
	 */
	
	public HashMap<Integer,StationNote> readStationNote() 
	{
		HashMap<Integer, StationNote> StationMap=new HashMap<Integer, StationNote>();
		
		//��ȡR��raw.stationjson�������
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
				//��JsonStr��ΪJsonObject
				JSONObject json=new JSONObject(JsonStr);
				//��JsonObject ���  StationNoteʵ��
				StationNote station=(StationNote)this.Json2Object(json,"StationNote");
				//��StationNote ����Map��
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
				if(in!=null)
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
	 * ��ȡroute�Ļ�����Ϣ
	 * 
	 * @return HashMap<Intger,StationRoute ����route ��hashmap Key-routeID, value- StationRoute
	 */
	public HashMap<Integer,Route> readStationRoute()
	{
		HashMap<Integer, Route> RouteMap=new HashMap<Integer, Route>();
		
		//��ȡR��raw.stationjson�������
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
				//��JsonStr��ΪJsonObject
				JSONObject json=new JSONObject(JsonStr);
				//��JsonObject ���  Routeʵ��
				Route route=(Route)this.Json2Object(json,"Route");
				//��StationNote ����Map��
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
				if(in!=null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return RouteMap;
	}
	
	/**
	 * ��ȡ�ղص�վ����Ϣ
	 * @return
	 */
	@SuppressLint("UseSparseArrays")
	public HashMap<Integer,CollectRoute> readCollectRoute()
	{
		HashMap<Integer, CollectRoute> RouteMap=new HashMap<Integer, CollectRoute>();
		
		
		//��ȡĿ¼�µ��ղ�����
		InputStream in=null;
		BufferedReader br=null;
		//StringBuffer sb=new StringBuffer();
		try{
			File file=new File(BaseKey.BaseFileDir+BaseKey.CollectRouteFile);
			in=new FileInputStream(file);
			br=new BufferedReader(new InputStreamReader(in,"GBK"));
			String JsonStr=null;
			while((JsonStr=br.readLine())!=null)
			{
				//��JsonStr��ΪJsonObject
				JSONObject json=new JSONObject(JsonStr);
				//��JsonObject ���  Routeʵ��
				CollectRoute route=(CollectRoute)this.Json2Object(json,"CollectRoute");
				//��StationNote ����Map��
				Log.d("json collectroute=",route.toString());
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
				if(in!=null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return RouteMap;
	}
	
	
	/**
	 * ��JSONOJect ʵ��ת��ΪObjectName��ʵ��
	 * 
	 * @param json
	 * @param OjbectName  OjbectName������com.tongji.ontimesubway.base�µ���
	 * @return OjbectName�����ʵ��
	 */
	
	public Object Json2Object(JSONObject json,String OjbectName)
	{
		String ObjectURL=(new StringBuffer("com.tongji.ontimesubway.base.")).append(OjbectName).toString();
		Object objectinstance=null;
		Class object=null;
		try {
			object=Class.forName(ObjectURL);
			Log.d("object=",object.getName());
			
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
	    	//�������
	    	String varField=it.next();
	    	Field field=null;
	    	try {
				field=getField(object,varField);
				Log.d("field=",field.getName());
				//����Ϊ���޸�
				field.setAccessible(true);
				//Log.d("JsonFile",field.getType().toString());
				//�����String
				if(field.getType().equals(String.class))
				{
					String value=json.getString(varField);
					field.set(objectinstance, value);
				}//�����integer
				else if(field.getType().equals(Integer.class)||field.getType().equals(int.class))
				{
					int value=json.getInt(varField);
					field.set(objectinstance, value);
				}//�����ArrayList
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
	
	//���clazz����������
	public Field getField(Class clazz,String varField) throws NoSuchFieldException
	{
		Field field=null;
		try {
			field=clazz.getDeclaredField(varField);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			try {
				field=clazz.getSuperclass().getDeclaredField(varField);
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				throw e1;
			}
		}
		return field;
	}

}
