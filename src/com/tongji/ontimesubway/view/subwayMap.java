package com.tongji.ontimesubway.view;

import java.lang.ref.SoftReference;

import com.tongji.ontimesubway.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

@SuppressLint("NewApi")
public class subwayMap extends Fragment{
	private DragImageView mapView;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	 @Override  
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
	        View view=inflater.inflate(R.layout.subwaymap,container,false);  
	        mapView=(DragImageView)view.findViewById(R.id.subway_map);
	        Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.subway);
	        //»Ì“˝”√
	        //SoftReference<Bitmap> softbitmap=new SoftReference<Bitmap>(bitmap);
	        mapView.setImageBitmap(bitmap);
	        mapView.setmActivity(getActivity());
	        WindowManager manager = this.getActivity().getWindowManager();
	        int window_width = manager.getDefaultDisplay().getWidth();
			int window_height = manager.getDefaultDisplay().getHeight();
			mapView.setScreen_H(window_height);
			mapView.setScreen_W(window_width);
	        return view;
	   }  
}
