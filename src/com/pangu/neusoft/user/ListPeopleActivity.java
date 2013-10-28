package com.pangu.neusoft.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.db.DBConn;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.db.People;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.pangu.neusoft.healthe.FatherActivity;
public class ListPeopleActivity extends FatherActivity {
	private SharedPreferences sp;
	private Editor editor;
	private Button create_btn;
	private Button logout_btn;
	DBManager mgr;
	private ListView list; 
	
	String action="history";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mgr=new DBManager(ListPeopleActivity.this);
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		if(!sp.getBoolean("loginsuccess", false)){
			finish();
			startActivity(new Intent(ListPeopleActivity.this,LoginActivity.class));
		}
		
		Intent intent = getIntent();
		action=intent.getStringExtra("action");
		
		
		setContentView(R.layout.activity_list_people);
		
		create_btn=(Button)findViewById(R.id.create_people);
		create_btn.setOnClickListener(create_btn_click);
		
		logout_btn=(Button)findViewById(R.id.logout_btn);
		logout_btn.setOnClickListener(logout_btn_click);
		
		
		if(action!=null&&action.equals("history")){
			create_btn.setVisibility(View.INVISIBLE);
			logout_btn.setVisibility(View.INVISIBLE);
		}else{
			create_btn.setVisibility(View.VISIBLE);
			logout_btn.setVisibility(View.VISIBLE);
		}
			
		
		//DBConn dbconn =new DBConn(ListPeopleActivity.this);
			//Cursor cursor=null;
		     try{ 	 
		    	
		    	 //String sql="select * from users";
		    	 //cursor=dbconn.SqliteDatabase.rawQuery(sql, null);
		    	 
		    	 list=(ListView)findViewById(R.id.list_people);
		    	 
		    	  List<String> arr=new ArrayList<String>();
		    	  
		    	  List<People> peoples=mgr.query();
		    	  for(People people:peoples){
		    		  arr.add(people.getUsername());
		    	  }
		         
		    	  if(arr.size()==0){
		    		  arr.add("请先添加就诊人");
		    		  ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,arr);
			          list.setAdapter(adapter);
		    	  }else{
		    		  ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,arr);
			          list.setAdapter(adapter);		          
			          list.setOnItemClickListener(choosepeople);
		    	  }
		        mgr.closeDB();
		     }catch(Exception ex){
		    	 ex.printStackTrace();
		     }
		     
		     
		
	}

	OnItemClickListener choosepeople=new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String name=(String)list.getItemAtPosition(arg2);
			
			if(action!=null&&action.equals("history")){
				Intent intent=new Intent();
				intent.setClass(ListPeopleActivity.this, HistoryViewActivity.class);
				intent.putExtra("username", name);
				startActivity(intent);
			}else{
				//显示详细信息
				Intent intent=new Intent();
				intent.setClass(ListPeopleActivity.this, PeopleInfoActivity.class);
				intent.putExtra("username", name);
				startActivity(intent);
			}
		     
		}
		
	};
	
	
	OnClickListener create_btn_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			startActivity(new Intent(ListPeopleActivity.this,CreatePeopleActivity.class));
		}
	};
	
	OnClickListener logout_btn_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			editor.putBoolean("loginsuccess", false);
			editor.commit();
			startActivity(new Intent(ListPeopleActivity.this,LoginActivity.class));
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_people, menu);
		return true;
	}
	@Override  
	protected void onDestroy() {  
	    super.onDestroy();  
	    if (mgr  != null) {  
	    	mgr.closeDB();  
	    }  
	}  

}
