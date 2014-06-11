package com.tongji.ontimesubway.view;

import com.tongji.ontimesubway.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuButton extends LinearLayout{
	private ImageView ImageOrigen;
	private TextView text;

	public MenuButton(Context context)
	{
		super(context);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.menu_up_button, null);
		ImageOrigen=(ImageView)findViewById(R.id.menu_up_button_imageView);
		text=(TextView)findViewById(R.id.menu_up_button_text);
		//Bitmap bm= BitmapFactory.decodeResource(getResources(), R.drawable.logo);
		//this.setImageBitmap(bm);
	}
	public MenuButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.menu_up_button, this);
		ImageOrigen=(ImageView)findViewById(R.id.menu_up_button_imageView);
		//ImageOrigen.setImageBitmap(bm);
		text=(TextView)findViewById(R.id.menu_up_button_text);
		//Bitmap bm= BitmapFactory.decodeResource(getResources(), R.drawable.collect_logo);
		//setImageBitmap(bm);
		// TODO Auto-generated constructor stub
	}
	/*private void DoRefectImageView(Bitmap bmOr)
	{
		//Bitmap bmOr=((BitmapDrawable) this.ImageOrigen.getDrawable()).getBitmap();
		//final int reflectionGap = 4;
		int width = bmOr.getWidth();
		int height = bmOr.getHeight();
		Matrix matrix = new Matrix();
		// 第一个参数为1表示x方向上以原比例为准保持不变，正数表示方向不变。
		// 第二个参数为-1表示y方向上以原比例为准保持不变，负数表示方向取反。
		matrix.setScale(1f, -0.75f);
		//matrix.setSkew(-0.75f, 1f);
		Bitmap reflectionImage = Bitmap.createBitmap(bmOr, 0, 0, width, height*3/4 , matrix, true);
		
		// 创建一个渐变的蒙版放在下面被反转的图片上面
				Paint paint = new Paint();
				LinearGradient shader = new LinearGradient(0, 0, width, height*3/4
						, 0xFFffffff, 0x90ffffff, TileMode.CLAMP);
				paint.setShader(shader);
				// Set the Transfer mode to be porter duff and destination in
				paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		Canvas canvas=new Canvas(reflectionImage);
		canvas.drawRect(0, 0, width, reflectionImage.getHeight() , paint);
		this.ImageRef.setImageBitmap(reflectionImage);
		//this.ImageRef.setAlpha(40.0f);
	};*/
	
	public  void setMidText(String text)
	{
		this.text.setText(text);
	}
	public void setImageBitmap(Bitmap bm)
	{
		this.ImageOrigen.setImageBitmap(bm);
		//this.DoRefectImageView(bm);
	}
	public void setBackgroundResource(int resid)
	{
		this.ImageOrigen.setBackgroundResource(resid);
	}

}
