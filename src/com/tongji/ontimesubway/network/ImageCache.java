package com.tongji.ontimesubway.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.R.color;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.View;

public class ImageCache {
	private  Context context;
	//ͼƬ����ʱ�ı���ͼ
	private  Bitmap bg_or;
	
	//ǰ��ͼ
	private Bitmap fg;
	//һ������
	private HashMap<String,SoftReference<Bitmap>> FirstCache;
	//����ȡ�������̵߳�
	private List<ImageNetAsynctask> tasklist;
	private static ImageCache imagecache=new ImageCache();
	//�¼�������
	private ImageCacheNetwork network=new ImageCacheNetwork(){
		@Override
		public void getImagePre(View view, String url) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getImagePost(View view, Bitmap bitmap) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void getImageFail(Exception e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	//�̳߳�, Ϊ��֤����Ŀ�����ֻͬʱִ��һ���߳�
	private ExecutorService taskPool;
	private ImageCache()
	{
		taskPool=Executors.newSingleThreadExecutor();
		tasklist=new LinkedList<ImageNetAsynctask>();
		FirstCache=new HashMap<String,SoftReference<Bitmap>>();
	}
	//ʹ��֮ǰ�ȳ�ʼ��
	public void initImageCache(Context context)
	{
		this.context=context;
		bg_or=BitmapFactory.decodeResource(context.getResources(), R.drawable.loading);
		//ǰ��ͼ����
		fg = Bitmap.createBitmap(bg_or.getWidth(), 1, Bitmap.Config.ARGB_8888);  
        Canvas c = new Canvas(fg);  
        Paint p = new Paint();  
        p.setColor(context.getResources().getColor(R.color.title_button_bc));  
        c.drawRect(0, 0, bg_or.getWidth(), 1, p);
	}
	//���ûص�����
	public void setNetWork(ImageCacheNetwork network)
	{
		this.network=network;
	}
	public static ImageCache getInstance()
	{
		return imagecache;
	}
	//��������ͼƬ��View��
	public void getImage(View view, String url)
	{
		//�б����и������򲻼����������û����������һ������
		for(int i=0;i<tasklist.size();i++)
		{
			if(tasklist.get(i).equals(view, url))
				return ;
		}		
		ImageNetAsynctask imageTask=new ImageNetAsynctask(view, url);
		tasklist.add(imageTask);
		imageTask.executeOnExecutor(taskPool, url);
	}
	
	//��ȡͼƬ��bitmap��Դ
	private Bitmap getImageBitmap(String url)
	{
		return null;
	}
	
	//��ȡͼƬ���̣߳�
	private class ImageNetAsynctask extends AsyncTask<String, Float, Bitmap>{

		private View view;
		private String url;
		private boolean isInCache;
		private Bitmap bg;
		private int lastH;
		
		public ImageNetAsynctask(View view, String url)
		{
			this.view = view;
			this.url=url;
			isInCache=false;
			bg=bg_or.copy(Bitmap.Config.ARGB_8888, true);
		}
		@Override
		protected Bitmap doInBackground(String... urls) {
			// TODO Auto-generated method stub
			//�ȴ�һ��������ȡ������
			SoftReference<Bitmap> softResult;
			synchronized(FirstCache){
				softResult=FirstCache.get(SDUtil.UrlToFilename(url));
			}
			//���һ��������û��ͼƬ���ٴ��ļ���ȡ��
			if(softResult==null||softResult.get()==null){
				isInCache=false;
				Bitmap result=SDUtil.getImage(SDUtil.UrlToFilename(url));
				if(result==null)
				{
					//result=IOUtil.getBitmapRemote(context, url);
					HttpClient httpClient = new DefaultHttpClient();  
			        HttpGet httpGet = new HttpGet(url);  
			        InputStream is = null;  
			        ByteArrayOutputStream baos = null;  
			        try {  
			            HttpResponse httpResponse = httpClient.execute(httpGet);  
			            //printHttpResponse(httpResponse);  
			            HttpEntity httpEntity = httpResponse.getEntity();  
			            long length = httpEntity.getContentLength();  
			            //LogOut.out(this, "content length=" + length);  
			            is = httpEntity.getContent();  
			            if (is != null) {  
			                baos = new ByteArrayOutputStream();  
			                byte[] buf = new byte[128];  
			                int read = -1;  
			                int count = 0;  
			                while ((read = is.read(buf)) != -1) {  
			                    baos.write(buf, 0, read);  
			                    count += read;  
			                    publishProgress(count *1.0f / length);  
			                }  
			                //LogOut.out(this, "count=" + count + " length=" + length);  
			                byte[] data = baos.toByteArray();  
			                result = BitmapFactory.decodeByteArray(data, 0, data.length);
			            }
			        } catch (ClientProtocolException e) {  
			            e.printStackTrace();  
			        } catch (IOException e) {  
			            e.printStackTrace();  
			        } finally {  
			            try {  
			                if (baos != null) {  
			                    baos.close();  
			                }  
			                if (is != null) {  
			                    is.close();  
			                }  
			            } catch (IOException e) {  
			                e.printStackTrace();  
			            }  
			        } 
					if(result!=null){
						SDUtil.saveImage(result, SDUtil.UrlToFilename(url));
					}
					return result;
				}
				else 
				{
					return result;
				}
			}
			else{
				isInCache=true;
				return softResult.get();
			}
		}
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			network.getImagePre(view,url);
		}
		@Override
		protected void onPostExecute(Bitmap result){
			//��ͼƬд���ļ���
			if(!isInCache&&result!=null){
				//д��һ��������
				SoftReference<Bitmap> softBitmap=new SoftReference<Bitmap>(result);
				synchronized(FirstCache){
				FirstCache.put(SDUtil.UrlToFilename(url), softBitmap);
				}
			}
			view.setBackgroundDrawable(new BitmapDrawable(result));
			network.getImagePost(view, result);
			
			//������ɺ���б���ɾ��
			tasklist.remove(this);
		}
		//����UI ��ʾ���ؽ���
		@Override
		protected void onProgressUpdate(Float... ProgressValue)
		{
			//�õ�����ͼƬ
			Canvas canvas=new Canvas(bg);
			Paint paint=new Paint();
			paint.setAlpha(100);
			int height=(int) (bg.getHeight()*ProgressValue[0]);
			int tempH=bg.getHeight()-height;
			for(int i=0;i<height-lastH;i++)
			{
				canvas.drawBitmap(fg, 0,tempH+i ,paint);
			}
			lastH=height;
			view.setBackgroundDrawable(new BitmapDrawable(bg));			
		}
		
		
		public boolean equals(View view, String url)
		{
			if(this.view.equals(view)&&this.url.equals(url))
				return true;
			else 
				return false;
		}
	}
}
