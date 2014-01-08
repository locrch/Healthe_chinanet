package com.pangu.neusoft.healthe;


import java.io.File;
import java.io.IOException;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.HandleBooking;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.tools.DialogShow;
import com.pangu.neusoft.healthcard.ChangePassActivity;
import com.pangu.neusoft.healthcard.ConnectListActivity;
import com.pangu.neusoft.healthcard.ListCardActivity;
import com.pangu.neusoft.healthcard.LoginActivity;
import com.pangu.neusoft.healthcard.ShowHistoryActivity;
import com.pangu.neusoft.healthcard.UserInfoActivity;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class TabActivity3 extends Activity {
	private SharedPreferences sp;
	private Editor editor;
	private String welcome_username = "用户";
	Button tab3_login;//登录
	Button tab3_peopleinfo;//就诊人信息
	Button tab3_bookinghist;//预约记录
	Button tab3_showhist;//个人收藏
	Button user_info;//个人信息
	Button change_pass;//个人信息
	Button tab3_cleanDB;//清理缓存
	Button tab3_settextsize;//设置字体
	
	TextView textview1,tab3_welcome_text;
	DBManager mgr;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Setting.state="userinfo";
		setContentView(R.layout.tab3_user);
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		mgr=new DBManager(TabActivity3.this);
		tab3_welcome_text = (TextView)findViewById(R.id.tab3_welcome_text);
		tab3_login=(Button)findViewById(R.id.tab3_login);
		tab3_peopleinfo=(Button)findViewById(R.id.tab3_peopleinfo);
		tab3_bookinghist=(Button)findViewById(R.id.tab3_bookinghist);
		tab3_showhist=(Button)findViewById(R.id.tab3_showhist);
		
		user_info=(Button)findViewById(R.id.user_info);
		change_pass=(Button)findViewById(R.id.change_pass);
		
		tab3_cleanDB=(Button)findViewById(R.id.tab3_cleanDB);
		tab3_settextsize=(Button)findViewById(R.id.tab3_settextsize);
		
		tab3_login.setOnClickListener(login_click);
		tab3_peopleinfo.setOnClickListener(peopleinfo_click);
		tab3_bookinghist.setOnClickListener(bookinghist_click);
		tab3_showhist.setOnClickListener(showhist_click);
		user_info.setOnClickListener(user_info_click);
		tab3_cleanDB.setOnClickListener(cleanDB_click);
		tab3_settextsize.setOnClickListener(settextsize_click);
		change_pass.setOnClickListener(chang_pass_click);
		Setting.bookingdata=null;//清除本次预约数据
		editor.putString("now_state", "usersetting");
		editor.commit();
		
		Islogin();
	    
	}
	//修改密码
	OnClickListener chang_pass_click = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			String phone=sp.getString("username", "");
			
			Intent intent=new Intent(TabActivity3.this, ChangePassActivity.class);
			intent.putExtra("userphone", phone);
			startActivity(intent);
		}
	};
	
	
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
			if(tab3_login.getText().toString().equals("登录")){
				Intent intent=getIntent();
				intent.setClass(TabActivity3.this, LoginActivity.class);
				startActivity(intent);
			}else{
				
				logoutDialog(TabActivity3.this);
			}
			
		}
	};
	
	private void logoutDialog(Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage("确认要注销吗？");
		builder.setTitle("提示");

		builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//注销
				for(int i=0;i<5;i++)
					editor.remove("card"+i+"_"+"owner");				
				editor.remove("username");
		    	editor.remove("password");
		    	editor.remove("loginsuccess");
				editor.remove("defaultcardno");				
		    	editor.commit();
				
		    	tab3_login.setText("登录");
		    	Islogin();
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
	
	OnClickListener peopleinfo_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			Setting.state="show_cardlist";
			Intent intent=getIntent();
			intent.putExtra("action", "info");
			intent.setClass(TabActivity3.this, ListCardActivity.class);
			startActivity(intent);
		}
	};
	OnClickListener bookinghist_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(sp.getString("username", "").equals("")){
				Toast.makeText(TabActivity3.this, "请先登录", Toast.LENGTH_SHORT).show();
			}else{
				Intent intent=getIntent();
				intent.setClass(TabActivity3.this, ShowHistoryActivity.class);
				startActivity(intent);
			}
			
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
	
	@SuppressLint("ShowToast")
	OnClickListener user_info_click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(sp.getString("username", "").equals("")){
				Toast.makeText(TabActivity3.this, "请先登录", Toast.LENGTH_SHORT).show();
			}else{
				Intent intent=getIntent();
				intent.setClass(TabActivity3.this, UserInfoActivity.class);
				startActivity(intent);
			}
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
		builder.setMessage("确认要删除图片缓存数据吗？");
		builder.setTitle("提示");

		builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
//				//删除数据库记录
//				DBManager mgr=new DBManager(TabActivity3.this);
//				mgr.clearDB();
//				mgr.closeDB();
				//删除图片缓存
				File dir=new File(Setting.catche_dir);
				if(dir.exists()){
					try{
						del(dir.getPath());
					}catch(Exception ex){}
				}
				//下面是清除用户登录信息？
				//editor.clear();
				//editor.commit();
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
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
		Islogin();
	}
	@Override
	protected void onRestart()
	{
		// TODO Auto-generated method stub
		super.onRestart();
		Islogin();
	}
	@Override  
	protected void onDestroy() {  
	    super.onDestroy();  
	    if (mgr  != null) {  
	    	mgr.closeDB();  
	    }  
	}  
	
	private void Islogin()
	{
		//判断登录状态
		if(sp.getString("username", "").equals("")){
			tab3_login.setText("登录");
			tab3_welcome_text.setText("");
		}else{
			tab3_welcome_text.setText("尊敬的"+ sp.getString("card"+sp.getString("defaultcardno","")+"_"+"owner", "用户")+",您好！");
			tab3_login.setText("注销");
		}
	}

}
