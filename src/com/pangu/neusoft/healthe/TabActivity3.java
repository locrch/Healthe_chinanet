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
	
	Button button2;//登录
	Button button3;//就诊人信息
	Button button4;//预约记录
	Button button5;//个人收藏
	Button button6;//切换账户
	Button button7;//清除缓存
	TextView textview1;
	DBManager mgr;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2_user);
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		mgr=new DBManager(TabActivity3.this);
		
		button2=(Button)findViewById(R.id.button2);
		button3=(Button)findViewById(R.id.button3);
		button4=(Button)findViewById(R.id.button4);
		button5=(Button)findViewById(R.id.button5);
		button6=(Button)findViewById(R.id.button6);
		button7=(Button)findViewById(R.id.button7);
		
		button2.setOnClickListener(button2_click);
		button3.setOnClickListener(button3_click);
		button4.setOnClickListener(button4_click);
		button5.setOnClickListener(button5_click);
		button6.setOnClickListener(button6_click);
		button7.setOnClickListener(button7_click);
		
	}
	
	OnClickListener button2_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			Intent intent=getIntent();
			intent.setClass(TabActivity3.this, LoginActivity.class);
			startActivity(intent);
			
		}
	};
	OnClickListener button3_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			Intent intent=getIntent();
			intent.setClass(TabActivity3.this, ListPeopleActivity.class);
			startActivity(intent);
		}
	};
	OnClickListener button4_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=getIntent();
			intent.putExtra("action", "history");
			intent.setClass(TabActivity3.this, ListPeopleActivity.class);
			startActivity(intent);
		}
	};
	OnClickListener button5_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			
			Intent intent=getIntent();
			intent.setClass(TabActivity3.this, ConnectListActivity.class);
			startActivity(intent);
		}
	};
	OnClickListener button6_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			editor.putBoolean("loginsuccess", false);
			editor.commit();
			startActivity(new Intent(TabActivity3.this,LoginActivity.class));
		}
	};
	OnClickListener button7_click=new OnClickListener(){
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
	protected void onDestroy() {  
	    super.onDestroy();  
	    if (mgr  != null) {  
	    	mgr.closeDB();  
	    }  
	}  


}