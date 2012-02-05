package com.you;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Begin_Visit extends Activity{
	
	private Button bv_b1,bv_b2,bv_b3,bv_b4;
	private TextView bv_tv1;
	
	@Override  
    public void onCreate(Bundle savedInstanceState) {   
        super.onCreate(savedInstanceState);   
        setContentView(R.layout.begin_visit); 
        MyApplication.getInstance().addActivity(this);
        
        bv_b1 = (Button) findViewById(R.id.bv_b1);
        bv_b2 = (Button) findViewById(R.id.bv_b2);
        bv_b3 = (Button) findViewById(R.id.bv_b3);
        bv_b4 = (Button) findViewById(R.id.bv_b4);
        bv_tv1 = (TextView) findViewById(R.id.bv_tv1);
        
        
        String STORE_NAME="VisitName";
		SharedPreferences set=getSharedPreferences(STORE_NAME, MODE_PRIVATE);
		String con=set.getString("Vcontent", "");
		bv_tv1.setText(con);
        
        //跳转到拍照页面
        bv_b1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent bv_intent1= new Intent();
				bv_intent1.setClass(Begin_Visit.this, Visit_List.class);
				startActivity(bv_intent1);
			}
        	
        });
        
        //返回主页面
        bv_b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent bv_intent2= new Intent();
				bv_intent2.setClass(Begin_Visit.this, Main.class);
				startActivity(bv_intent2);
				finish();
				
			}
		});
        
        //拍照页面
        bv_b3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent bv_intent3 = new Intent();
				bv_intent3.setClass(Begin_Visit.this,sCamera.class );
				startActivity(bv_intent3);
			}
        	
        });
        
        //返回主页面
        bv_b4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent bv_intent4 = new Intent();
				bv_intent4.setClass(Begin_Visit.this,Main.class );
				startActivity(bv_intent4);
			}
        	
        });
	}

}
