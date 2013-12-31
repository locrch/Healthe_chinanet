package com.pangu.neusoft.healthcard;


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
import android.graphics.Paint;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class LoginActivity extends FatherActivity {
	private SharedPreferences sp;
	private Editor editor;
	private WebService service;
	private ProgressDialog mProgressDialog; 
	private EditText username;
	private EditText password;
	private CheckBox member_CheckBox,auto_CheckBox;
	private TextView for_pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		//if(sp.getBoolean("loginsuccess", false)){
		//	startActivity(new Intent(LoginActivity.this,ListPeopleActivity.class));
		//	finish();
		//}
		
		
		setContentView(R.layout.login_layout);
		
		setactivitytitle("登录");
		service=new WebService();
	  	mProgressDialog = new ProgressDialog(LoginActivity.this);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		 OnClickListener reg=new OnClickListener(){//注册
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(LoginActivity.this,
							RegisterActivity.class);
					startActivity(intent);
					finish();
				}
		  };
		  Button reg_btn=(Button)findViewById(R.id.reg_btn);
		  reg_btn.setOnClickListener(reg);
		  member_CheckBox=(CheckBox)findViewById(R.id.member_CheckBox);
		  auto_CheckBox=(CheckBox)findViewById(R.id.auto_CheckBox);
	      username=(EditText)findViewById(R.id.username);
	      password=(EditText)findViewById(R.id.password);
	      
	      
		  Button login_btn=(Button)findViewById(R.id.login_btn);
		  login_btn.setOnClickListener(login);
		 // Setting.bookingdata=null;//清除本次预约数据
		  member_CheckBox.setChecked(sp.getBoolean("auto_ischecked", true));
		  auto_CheckBox.setChecked(sp.getBoolean("auto_login_ischecked", true));
		  
		if (member_CheckBox.isChecked())
		{ 
			  username.setText(sp.getString("username", ""));
			  password.setText(sp.getString("password", ""));
		}
	      else {
	    	  //username.getText().clear();
			  password.getText().clear();
		}
	      
		  member_CheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				// TODO Auto-generated method stub
				if (isChecked)
				{
					editor.putBoolean("auto_ischecked", true);
					editor.commit();					
				}
				else {
					editor.putBoolean("auto_ischecked", false);
					editor.putString("password", "");
					editor.commit();
					
				}
			}
		});
		  
		  auto_CheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
			{
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					// TODO Auto-generated method stub
					if (isChecked)
					{
						editor.putBoolean("auto_login_ischecked", true);
						editor.commit();						
					}
					else {
						editor.putBoolean("auto_login_ischecked", false);
						editor.commit();
						
					}
				}
			});
		  
		  for_pass=(TextView)findViewById(R.id.for_pass);
		  for_pass.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
		  for_pass.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
			
		  for_pass.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//找回密码
				String phone=username.getText().toString();
				Intent intent = new Intent(LoginActivity.this,ChangePassActivity.class);
				intent.putExtra("userphone", phone);
				startActivity(intent);
				//finish();
			} 
		  });
		  
	}
	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
		editor.putString("username", username.getText().toString());
		editor.putString("password", password.getText().toString());
		editor.commit();
	}
	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		
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
									if(!member.getUserName().equals(sp.getString("username", ""))){
										
										int totalcards=sp.getInt("total_cards", 0);
										for(int i=0;i<totalcards;i++){
											editor.remove("card"+i+"_"+"owner");
											editor.remove("card"+i+"_"+"cardnum");
											editor.remove("card"+i+"_"+"cardtype");
											editor.remove("card"+i+"_"+"idnumber");
											editor.remove("card"+i+"_"+"idtype");
											editor.remove("card"+i+"_"+"phonenumber");
										}
										editor.remove("defaultcardno");
										editor.remove("total_cards");
										editor.commit();
									}
									
									editor.putString("username", member.getUserName());
									editor.putString("password", member.getPassword());
									editor.putBoolean("loginsuccess",true);
									editor.putString("defaultcardno","0");
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
										startActivity(new Intent(LoginActivity.this,ListCardActivity.class));
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
	
	

}
