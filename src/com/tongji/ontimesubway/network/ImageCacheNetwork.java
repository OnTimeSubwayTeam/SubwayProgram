package com.tongji.ontimesubway.network;

import android.graphics.Bitmap;
import android.view.View;

public interface ImageCacheNetwork {

	//��ȡͼƬ֮ǰ��������
	public void getImagePre(View view, String url);
	//��ȡͼƬ�ɹ�֮����������
	public void getImagePost(View view, Bitmap bitmap);
	//��ȡͼƬʧ��֮����������
	public void getImageFail(Exception e);
}
