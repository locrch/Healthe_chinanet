package com.pangu.neusoft.healthe;


import java.io.File;
import java.io.IOException;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.HandleBooking;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.tools.DialogShow;
import com.pangu.neusoft.user.ConnectListActivity;
import com.pangu.neusoft.user.ListPeopleActivity;
import com.pangu.neusoft.user.LoginActivity;
import com.pangu.neusoft.user.ShowHistoryActivity;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class TabActivity3 extends Activity {
	private SharedPreferences sp;
	private Editor editor;
	
	Button tab3_login;//登录
	Button tab3_peopleinfo;//就诊人信息
	Button tab3_bookinghist;//预约记录
	Button tab3_showhist;//个人收藏
	Button tab3_userchange;//切换账户
	Button tab3_cleanDB;//清除缓存
	Button tab3_settextsize;//设置字体
	TextView textview1;
	DBManager mgr;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab3_user);
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		mgr=new DBManager(TabActivity3.this);
		
		tab3_login=(Button)findViewById(R.id.tab3_login);
		tab3_peopleinfo=(Button)findViewById(R.id.tab3_peopleinfo);
		tab3_bookinghist=(Button)findViewById(R.id.tab3_bookinghist);
		tab3_showhist=(Button)findViewById(R.id.tab3_showhist);
		tab3_userchange=(Button)findViewById(R.id.tab3_userchange);
		tab3_cleanDB=(Button)findViewById(R.id.tab3_cleanDB);
		tab3_settextsize=(Button)findViewById(R.id.tab3_settextsize);
		
		tab3_login.setOnClickListener(login_click);
		tab3_peopleinfo.setOnClickListener(peopleinfo_click);
		tab3_bookinghist.setOnClickListener(bookinghist_click);
		tab3_showhist.setOnClickListener(showhist_click);
		tab3_userchange.setOnClickListener(userchange_click);
		tab3_cleanDB.setOnClickListener(cleanDB_click);
		tab3_settextsize.setOnClickListener(settextsize_click);
	}
	OnClickListener settextsize_click = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			startActivity(new Intent(TabActivity3.this, SetTextSizeActivity.class));
		}
	};
	
	
	OnClickListener login_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			Intent intent=getIntent();
			intent.setClass(TabActivity3.this, LoginActivity.class);
			startActivity(intent);
			
		}
	};
	OnClickListener peopleinfo_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			Intent intent=getIntent();
			intent.setClass(TabActivity3.this, ListPeopleActivity.class);
			startActivity(intent);
		}
	};
	OnClickListener bookinghist_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=getIntent();
			intent.putExtra("action", "history");
			intent.setClass(TabActivity3.this, ListPeopleActivity.class);
			startActivity(intent);
		}
	};
	OnClickListener showhist_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			
			Intent intent=getIntent();
			intent.setClass(TabActivity3.this, ConnectListActivity.class);
			startActivity(intent);
		}
	};
	OnClickListener userchange_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			editor.putBoolean("loginsuccess", false);
			editor.commit();
			startActivity(new Intent(TabActivity3.this,LoginActivity.class));
		}
	};
	OnClickListener cleanDB_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			//删除数据库记录
			
					
			dialog();
			
			
			
		}
	};
	
	
	protected void dialog() {
		AlertDialog.Builder builder = new Builder(TabActivity3.this);
		builder.setMessage("确认要删除缓存数据吗？");
		builder.setTitle("提示");

		builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				DBManager mgr=new DBManager(TabActivity3.this);
				mgr.clearDB();
				mgr.closeDB();
				
				File dir=new File(Setting.catche_dir);
				if(dir.exists()){
					try{
						del(dir.getPath());
					}catch(Exception ex){}
				}
				editor.clear();
				editor.commit();
				DialogShow.showDialog(TabActivity3.this, "清除成功");
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	
	public void del(String filepath) throws IOException{  
	File f = new File(filepath);//定义文件路径         
		if(f.exists() && f.isDirectory()){//判断是文件还是目录  
		   if(f.listFiles().length==0){//若目录下没有文件则直接删除  
		       f.delete();  
		    }else{//若有则把文件放进数组，并判断是否有下级目录  
		       File delFile[]=f.listFiles();  
		        int i =f.listFiles().length;  
		        for(int j=0;j<i;j++){  
		            if(delFile[j].isDirectory()){  
		                     del(delFile[j].getAbsolutePath());//递归调用del方法并取得子目录路径  
		            }  
		          delFile[j].delete();//删除文件  
		       	}
		     }
		}      
	} 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}
	
	@Override  
	protected void onDestroy() {  
	    super.onDestroy();  
	    if (mgr  != null) {  
	    	mgr.closeDB();  
	    }  
	}  


}