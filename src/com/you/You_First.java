package com.you;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class You_First extends Activity{
	
	private Button you_b1;
	
	 public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.you_first);
	        MyApplication.getInstance().addActivity(this);
	        
	        you_b1 = (Button) findViewById(R.id.yf_b1);
	        
	        you_b1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Intent intent = new Intent();
					intent.setClass(You_First.this, New_Visit_First.class);
					startActivity(intent);
				}
			});
	        
	 }
	 
	 
	 /*覆写下面两个方法*/
	 /*添加菜单*/
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
		/*menu.add(组ID，项ID，显示顺序，显示标题)*/
	    	menu.add(0,0,0,"关于").setIcon(android.R.drawable.ic_menu_info_details);
	    	menu.add(0,1,1,"帮助").setIcon(android.R.drawable.ic_menu_help);
	    	menu.add(0,2,2,"退出").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
	    	return true;
		}
	    /*处理菜单事件*/
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	int item_id=item.getItemId();//得到当前选中MenuItem的ID
	    	switch(item_id){
	    	case 0:{
	    		
	    		}
	    	case 1:{
	    		
	    	    }
	    	case 2:{
				//事件处理代码
	    		MyApplication.getInstance().exit();
	    		}
	    	}
	    	return true;
	    }	 

}

