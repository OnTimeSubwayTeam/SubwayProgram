package com.tongji.ontimesubway.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;


import com.tongji.ontimesubway.base.BaseKey;


import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;

public class NetAsynctask extends AsyncTask<HashMap<String,String>,Integer,String>{

	protected String apiUrl=null;
	protected HashMap<String,String> param=null;
	protected NetTask nettask;
	protected HttpClient httpClient;
	protected HttpPost httpPost=null;
	protected int taskid;
	
	// compress strategy
		final private static int CS_NONE = 0;
		final private static int CS_GZIP = 1;
		final static private int timeoutConnection = 10000;
		final static private int timeoutSocket = 10000;
		private static int compress = CS_NONE;
		final private String charset = HTTP.UTF_8;
		
	public NetAsynctask(String url,HashMap<String,String> param,NetTask task,int taskID)
	{
		this.apiUrl=BaseKey.BaseApi+url;
		this.param=param;
		this.nettask=task;
		this.taskid=taskID;
	}
	@Override
	protected String doInBackground(HashMap<String, String>... params) {
		// TODO Auto-generated method stub
		
		//如果任务已经被取消，则直接退出
		if(this.isCancelled())
			return null;
		
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
			HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);
			// init client
			httpClient = new DefaultHttpClient(httpParams);
		if(apiUrl==null){
			Log.e("netAsynctask error", "url is null");
			return null;
		}
		else 
		{
			String result=null;
			try{
				if(param==null){
					result=Httpget();
				}
				else {

					result=Httppost(this.param);
				}
			}
			catch(Exception e)
			{
				nettask.onError(e);
				return null;
			}
			return result;
		}
	}

	@Override
	protected void onCancelled(String result)
	{
		Log.d("netasynctask cancel id=",String.valueOf(this.taskid));
	}
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		this.nettask.onPreExecute(this);
	}
	@Override
	protected void onPostExecute(String result){
		httpClient.getConnectionManager().shutdown();
		super.onPostExecute(result);
		if(result!=null){
			//String to Json
			this.nettask.onPostExecute(result);
		}
		return;
	}
	
	private String Httpget() throws Exception
	{
		try {
			
			httpPost = headerFilter(new HttpPost(this.apiUrl));
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			//get
			//String apiSid = AppUtil.getSessionId();
			/*if(apiSid != null && apiSid.length()>0){
				//httpPost.setHeader("Cookie", "JSESSIONID=" + apiSid); 
				postParams.add(new BasicNameValuePair("sid", apiSid));
				////Log.i("test sid", apiSid);
			}else{
				////Log.i("test sid","no sid here");
			}*/
			// set data charset
			if (this.charset != null) {
				httpPost.setEntity(new UrlEncodedFormEntity(postParams, this.charset));
			} else {
				httpPost.setEntity(new UrlEncodedFormEntity(postParams));
			}
			// send post request
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String httpResult = resultFilter(httpResponse.getEntity());
				//Log.w("AppClient.post.result", httpResult);
				return httpResult;
			} else {
				return null;
			}
		} catch (ConnectTimeoutException e) {
			//Log.i("error", "appClient");
			throw new Exception(BaseKey.error.network);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private String Httppost(HashMap urlparams) throws Exception
	{
		try {
			Log.i("yutest","url:"+this.apiUrl);
			httpPost = headerFilter(new HttpPost(this.apiUrl));
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			// get post parameters
			if(urlparams!=null){
				// post
				Iterator it = urlparams.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();
					postParams.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
				}
			}
			//get
			/*String apiSid = AppUtil.getSessionId();
			if(apiSid != null && apiSid.length()>0){
				//httpPost.setHeader("Cookie", "JSESSIONID=" + apiSid); 
				postParams.add(new BasicNameValuePair("sid", apiSid));
				//Log.i("test sid", apiSid);
			}else{
				//Log.i("test sid","no sid here");
			}*/
			// set data charset
			if (this.charset != null) {
				httpPost.setEntity(new UrlEncodedFormEntity(postParams, this.charset));
			} else {
				httpPost.setEntity(new UrlEncodedFormEntity(postParams));
			}
			// send post request
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String httpResult = resultFilter(httpResponse.getEntity());
				Log.w("AppClient.post.result", httpResult);
				return httpResult;
			} else {
				return null;
			}
		} catch (ConnectTimeoutException e) {
			//Log.i("error", "appClient");
			throw new Exception(BaseKey.error.network);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unused")
	private static HttpGet headerFilter (HttpGet httpGet) {
		switch (compress) {
			case CS_GZIP:
				httpGet.addHeader("Accept-Encoding", "gzip");
				break;
			default :
				break;
		}
		return httpGet;
	}
	
	private static HttpPost headerFilter (HttpPost httpPost) {
		switch (compress) {
			case CS_GZIP:
				httpPost.addHeader("Accept-Encoding", "gzip");
				break;
			default :
				break;
		}
		return httpPost;
	}
	
	private static String resultFilter(HttpEntity entity){
		String result = null;
		try {
			switch (compress) {
				case CS_GZIP:
					result = gzipToString(entity);
					break;
				default :
					result = EntityUtils.toString(entity);
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	/* 为 EntityUtils.toString() 添加 gzip 解压功能 */
	public static String gzipToString(final HttpEntity entity)
		throws IOException, ParseException {
		return gzipToString(entity, null);
	}
	
	/* 为 EntityUtils.toString() 添加 gzip 解压功能 */
	public static String gzipToString(final HttpEntity entity, final String defaultCharset) throws IOException, ParseException {
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		InputStream instream = entity.getContent();
		if (instream == null) {
			return "";
		}
		// gzip logic start
		if (entity.getContentEncoding().getValue().contains("gzip")) {
			instream = new GZIPInputStream(instream);
		}
		// gzip logic end
		if (entity.getContentLength() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
		}
		int i = (int)entity.getContentLength();
		if (i < 0) {
			i = 4096;
		}
		String charset = EntityUtils.getContentCharSet(entity);
		if (charset == null) {
			charset = defaultCharset;
		}
		if (charset == null) {
			charset = HTTP.DEFAULT_CONTENT_CHARSET;
		}
		Reader reader = new InputStreamReader(instream, charset);
		CharArrayBuffer buffer = new CharArrayBuffer(i);
		try {
			char[] tmp = new char[1024];
			int l;
			while((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
		} finally {
			reader.close();
		}
		return buffer.toString();
	}
	
	/**
	 * cancel the network
	 */
	public void Cancel()
	{
		this.cancel(true);
		if(httpPost!=null)
		{
			httpPost.abort();
		}
	}
}                                                                                                           