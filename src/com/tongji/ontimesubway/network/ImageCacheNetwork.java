package com.tongji.ontimesubway.network;

import android.graphics.Bitmap;
import android.view.View;

public interface ImageCacheNetwork {

	//获取图片之前所做的事
	public void getImagePre(View view, String url);
	//获取图片成功之后所做的事
	public void getImagePost(View view, Bitmap bitmap);
	//获取图片失败之后所做的事
	public void getImageFail(Exception e);
}
