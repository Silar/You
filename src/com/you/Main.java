package com.you;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {
   
	
	Button b1,b2,b3;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MyApplication.getInstance().addActivity(this);
        
        b1 =(Button) findViewById(R.id.button1);
        b2 =(Button) findViewById(R.id.button2);
        b3 =(Button) findViewById(R.id.button3);
        
        //拍照
        b1.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent a= new Intent();
				a.setClass(Main.this, sCamera.class);
				startActivity(a);
			}
        	
        });
        
        //游记列表
        b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent b = new Intent();
				b.setClass(Main.this, Visit_List.class);
				startActivity(b);
			}
		});
        
        //创建新游记
        b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent c = new Intent();
				c.setClass(Main.this, New_Visit_First.class);
				startActivity(c);
			}
		});
    }
    
    
    /* 覆写下面两个方法 */
	/* 添加菜单 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/* menu.add(组ID，项ID，显示顺序，显示标题) */
		menu.add(0, 0, 0, "About").setIcon(
				android.R.drawable.ic_menu_info_details);
		menu.add(0, 1, 1, "Help").setIcon(android.R.drawable.ic_menu_help);
		menu.add(0, 2, 2, "Exit").setIcon(
				android.R.drawable.ic_menu_close_clear_cancel);
		return true;
	}

	/* 处理菜单事件 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();// 得到当前选中MenuItem的ID
		switch (item_id) {
		case 0: {

		}
		case 1: {

		}
		case 2: {
			// 事件处理代码
			MyApplication.getInstance().exit();
		}
		}
		return true;
	}

	 
}