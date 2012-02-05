package com.you;

import java.io.File;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class sCamera extends Activity {

	Button b1,b2,b3;
	ImageView iv1;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scamera);
		MyApplication.getInstance().addActivity(this);

		b1 = (Button) findViewById(R.id.ca_b1);
		b2 = (Button) findViewById(R.id.ca_b2);
		b3 = (Button) findViewById(R.id.ca_b3);
		iv1 = (ImageView) findViewById(R.id.ca_iv1);
		
		//拍照
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StartCamera();
			}
		});
		
		//跳转到照片编辑页面
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent in2 = new Intent();
				in2.setClass(sCamera.this, Photo_Edit.class);
				startActivity(in2);
			}
		});
		
		//返回主页面
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent in3 = new Intent();
				in3.setClass(sCamera.this, Main.class);
				startActivity(in3);
			}
		});
	}

	// 调用系统相机
	public final String SAVE_PATH_IN_SDCARD = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath();

	private void StartCamera() {

		Intent ca_intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// ca_intent2.putExtra("autofocus", true);

		ca_intent2.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(SAVE_PATH_IN_SDCARD, "You.JPEG")));

		startActivityForResult(ca_intent2, 10);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// 非常重要！！如果被呼叫的activity中途放弃，就没有返回值，要避免错误
		if (data == null)
			return;

		if (requestCode == 10) {
			if (resultCode == Activity.RESULT_OK) {
				
				Bitmap bt=BitmapFactory.decodeFile("sdcard/You.JPEG");
				Log.d("��Ƭ", bt.toString());
				iv1.setImageBitmap(bt);
				
			}
			if (resultCode == RESULT_CANCELED)
				super.onRestart();
		}

		super.onActivityResult(requestCode, resultCode, data);		
	}	
	
	/**
	 * 重写Back键方法，直接返回Main主页面，而不是返回Camera照相页面
	 */

	@Override
	public void onBackPressed() {
		Intent inten = new Intent();
		inten.setClass(sCamera.this, Main.class);
		startActivity(inten);
		finish();
		return;
	}
}
