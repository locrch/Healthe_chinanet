package com.pangu.neusoft.user;


import com.pangu.neusoft.healthe.FatherActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.Area;
import com.pangu.neusoft.core.models.AreaReq;
import com.pangu.neusoft.core.models.Member;
import com.pangu.neusoft.core.models.MemberReg;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
import com.pangu.neusoft.tools.DialogShow;
import com.pangu.neusoft.tools.StringMethods;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class LoginActivity extends FatherActivity {
	private SharedPreferences sp;
	private Editor editor;
	private WebService service;
	private ProgressDialog mProgressDialog; 
	private EditText username;
	private EditText password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		if(sp.getBoolean("loginsuccess", false)){
			startActivity(new Intent(LoginActivity.this,ListPeopleActivity.class));
			finish();
		}
		
		
		setContentView(R.layout.login_layout);
		
		service=new WebService();
	  	mProgressDialog = new ProgressDialog(LoginActivity.this);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		 OnClickListener reg=new OnClickListener(){//注册
				@Override
				public void onClick(View v) {
					startActivity(new Intent(LoginActivity.this,
							RegisterActivity.class));
				}
		  };
		  Button reg_btn=(Button)findViewById(R.id.reg_btn);
		  reg_btn.setOnClickListener(reg);
		  
	      username=(EditText)findViewById(R.id.username);
	      password=(EditText)findViewById(R.id.password);
	      username.setText(sp.getString("username", ""));
	      password.setText(sp.getString("password", ""));
	      
		  
		  
		  Button login_btn=(Button)findViewById(R.id.login_btn);
		  login_btn.setOnClickListener(login);
	}

	
	public String checkData(){
		String msg="";
		if(username.getText().toString().equals("")){
			msg+="用户名不能为空\n";
		}
		if(password.getText().toString().equals("")){
			msg+="密码不能为空\n";
		}
		if(!StringMethods.isMobileNO(username.getText().toString())){
			msg+="请用手机号注册\n";
		}
		return msg;
	}
	
	
	
	OnClickListener login=new OnClickListener(){//登陆
		@Override
		public void onClick(View v) {
			String msg=checkData();
				if(msg.equals("")){
					new AsyncTask<Void, Void, Boolean>(){
						
						String msg="登陆失败";
					    @SuppressWarnings("deprecation")
						@Override  
				        protected void onPreExecute() {   
				            super.onPreExecute();   
				            mProgressDialog.show();
				        }			
						@Override
						protected Boolean doInBackground(Void... params){
							 MemberReg member=new MemberReg();
							 member.setUserName(username.getText().toString());
							 member.setPassword(password.getText().toString());
							 member.setCAPTCHA("");
							 member.setAucode(GET.Aucode);
							 SoapObject obj= service.sendMemberData(member,"userLogin");
							
							if(obj!=null){
								
								String IsLoginSuccess=obj.getProperty("IsLoginSuccess").toString();//0000成功1111报错
								String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
								msg=obj.getProperty("msg").toString();//返回的信息
								
								if(IsLoginSuccess.equals("true")){
									editor.putString("username", member.getUserName());
									editor.putString("password", member.getPassword());
									editor.putBoolean("loginsuccess",true);
									editor.commit();
									
									return true;
								}else{
									return false;
								}
							}
							else{
								msg="登陆失败";
								return false;
							}
						}
						@SuppressWarnings("deprecation")
						@Override
						protected void onPostExecute(Boolean result){
							super.onPostExecute(result);
							
							if(mProgressDialog.isShowing()){
								mProgressDialog.dismiss();
							}
							
							DialogShow.showDialog(LoginActivity.this, msg);
							
							
							if(result){	
								final Timer t = new Timer();
								t.schedule(new TimerTask() {
									public void run() {
										startActivity(new Intent(LoginActivity.this,ListPeopleActivity.class));
										finish();
										t.cancel(); 
									}
								}, Setting.dialogtimeout+1000);
								
							}
													
						}
						@SuppressWarnings("deprecation")
						@Override
						protected void onCancelled()
						{
							super.onCancelled();
							if(mProgressDialog.isShowing()){
								mProgressDialog.dismiss();
							}
						}	
				}.execute();
			}else{
				DialogShow.showDialog(LoginActivity.this, msg);
			}
		}
  };
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
