package com.tongji.ontimesubway.network;

import com.tongji.ontimesubway.R;
import com.tongji.ontimesubway.ui.RemindCenter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;

public class NotificationServer extends Service{
	
	private Context mContext;
	private NotificationHandler handler;
	private String ns = Context.NOTIFICATION_SERVICE;
	private Notification mNotification;
	private NotificationManager mNotificationManager;
	private PendingIntent contentIntent;
	private final int NotificationID	=	10021;
	private final int MSGUP				=	100;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		return null;
	}
	@Override
	public void onCreate()
	{
		mContext=this.getApplicationContext();
		mNotificationManager = (NotificationManager)getSystemService(ns);
		handler=new NotificationHandler();
		setMsgNotification();
		test();
		Log.d("service","onCreate");
	}
	
	//test
	public void test()
	{
		Thread thread=new Thread(){
			@Override
			public void run(){
				int i=0;
				while(i<10)
				{
					try {
						this.sleep(1000*15);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg=new Message();
					msg.what=MSGUP;
					handler.sendMessage(msg);
					
				}
			}
		};
		thread.start();
	}
	/** 
     * 创建通知 
     */  
    private void setMsgNotification() {   
        CharSequence tickerText = "hello, this is test";  
        long when = System.currentTimeMillis();  
        mNotification = new Notification(R.drawable.logo, tickerText, when);  
        
        // 放置在"正在运行"栏目中  
        //mNotification.flags = Notification.FLAG_ONGOING_EVENT;  
  
        //RemoteViews contentView = new RemoteViews(mContext.getPackageName(),  
       //         R.layout.notification_view);  
       // contentView.setTextViewText(R.id.noticfication_news, );  
        //contentView.setTextViewText(R.id.notify_msg, "手机QQ正在后台运行");  
        //contentView.setTextViewText(R.id.noticfication_time, getDate());  
        // 指定个性化视图  
       // mNotification.contentView = contentView;  
  
        Intent intent = new Intent(this, RemindCenter.class);  
         contentIntent= PendingIntent.getActivity(mContext, 0,  
                intent, PendingIntent.FLAG_UPDATE_CURRENT);  
        // 指定内容意图  
        //mNotification.contentIntent = contentIntent; 
        //mNotification.icon=R.drawable.logo;
        mNotification.setLatestEventInfo(mContext, "下站曹安公路，请换乘2号线", "下次换乘：于曹安路换乘2号线", contentIntent);
       // mNotification.contentView.setImageViewResource(R.id.logo_bg, R.drawable.logo);
        mNotificationManager.notify(NotificationID, mNotification);
               
    } 
    public class NotificationHandler extends Handler{
    	private int num;
    	@Override
    	public void handleMessage(Message msg){
    		switch(msg.what)
    		{
    		case MSGUP:
    			num++;
    			CharSequence tickerText = "hello, this is test"+String.valueOf(num);  
    	        long when = System.currentTimeMillis();  
    	        mNotification = new Notification(R.drawable.logo, tickerText, when); 
    	        //RemoteViews contentView = new RemoteViews(mContext.getPackageName(),  
    	         //       R.layout.notification_view);  
    	       // contentView.setTextViewText(R.id.noticfication_news, );  
    	        //contentView.setTextViewText(R.id.notify_msg, "手机QQ正在后台运行");  
    	        //contentView.setTextViewText(R.id.noticfication_time, getDate());  
    	        // 指定个性化视图  
    	        //mNotification.contentView = contentView;
    	        //mNotification.icon=R.drawable.logo;
    	        mNotification.setLatestEventInfo(mContext, "下站曹安公路，请换乘2号线", "下次换乘：曹安路，2号线", contentIntent);
    	        
    	        //mNotification.contentView.setImageViewResource(R.id.logo_bg, R.drawable.logo);
    	        mNotificationManager.notify(NotificationID, mNotification);
    			break;
    		}
    	}
    	
    	

    }
    public String getDate()
    {
    	return new Time().format("HH mm ss").toString();
    }

}
