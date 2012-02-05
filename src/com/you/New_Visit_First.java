package com.you;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 第一次建立游记时新建游记页面
 * @author Simple_liu
 *
 */

public class New_Visit_First extends Activity{

	private Button nvf_b1,nvf_b2;
	private EditText nvf_et1,nvf_et2;
	
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.new_visit_first);
        MyApplication.getInstance().addActivity(this);
        
        nvf_b1 = (Button) findViewById(R.id.nvf_b1);
        nvf_b2 = (Button) findViewById(R.id.nvf_b2);
        nvf_et1= (EditText) findViewById(R.id.nvf_et1);
        nvf_et2= (EditText) findViewById(R.id.nvf_et2);
        
        
        // 保存游记
        nvf_b1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//trim()方法是将String前后的空格去掉
				String name =nvf_et1.getText().toString().trim();
				String miaosu =nvf_et2.getText().toString().trim();
				
				//保存新建游记的名字到SharedPreferences--"Visit_Name"中
				
				SharedPreferences set=getSharedPreferences("VisitName",MODE_PRIVATE);
				SharedPreferences.Editor editor=set.edit();
				editor.putString("Vcontent", name);
				editor.commit();
				
				//创建游记文件夹
				MakeDirs("YouTu/"+name);
				
				//保存游记相关信息到SharedPreferences--"Visit_Message"中
				SharedPreferences vm=getSharedPreferences("Visit_Message", 0);
				SharedPreferences.Editor ve=vm.edit();
				//将游记的名字作为key，保存相应游记的信息.
				ve.putString(name, miaosu);
				ve.commit();
				
				//跳转页面
				Intent nvf_intent1= new Intent();
				nvf_intent1.setClass(New_Visit_First.this, Begin_Visit.class);
				startActivity(nvf_intent1);
			}
        	
        });
        
        //取消创建游记.
        nvf_b2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
		        //跳转至第一次使用游图页面
		        final Intent nvf_intent1=new Intent();
		        nvf_intent1.setClass(New_Visit_First.this, You_First.class);
				
				startActivity(nvf_intent1);
			}
        });
	}

	
	/**
	 * 判断SD卡是否存在
	 * @return
	 */
	public static boolean IsSdCard() 
	{ 
		boolean nIsSaveSdCard=false; 
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) 
			nIsSaveSdCard= true; 
		else 
			nIsSaveSdCard= false; 
		return nIsSaveSdCard; 
		} 


	public static boolean MakeDirs(String aPath) 
	{ 
		boolean nIsExist=false; 
		if (IsSdCard()) { 
			File sdCardDir = Environment.getExternalStorageDirectory();
			String p = sdCardDir.getAbsolutePath()+"/"+aPath;
			Log.i("New_Visit.MakeDirs", p);
	        File nPath=new File(p); 
	        nIsExist=nPath.exists(); 
	        if(!nIsExist) 
	        	nIsExist=nPath.mkdirs(); 
	        }	
		else 
		{ 
			File nPath=new File("./"+aPath); 
			nIsExist=nPath.exists(); 
			if(!nIsExist) 
				nIsExist=nPath.mkdirs(); 
			} 
		return nIsExist; 
		} 

	
	public static FileOutputStream GetFileOutStream(String aFileName,boolean aIsApp) 
	{ 
		try { 
			if (IsSdCard()) { 
				File sdCardDir = Environment.getExternalStorageDirectory();
				File saveFile = new File(sdCardDir, aFileName); 
				FileOutputStream outStream = new FileOutputStream(saveFile,aIsApp); 
				return outStream; 
				} 
			else 
			{ 
				File saveFile = new File("./", aFileName); 
				FileOutputStream outStream = new FileOutputStream(saveFile,aIsApp); 
				return outStream; 
				} 
			} catch (Exception e) { 
				e.printStackTrace(); 
				return null; 
				} 
		}
}
