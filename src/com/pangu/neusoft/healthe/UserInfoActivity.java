package com.pangu.neusoft.healthe;

import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.CardListReq;
import com.pangu.neusoft.core.models.MemberChangeReg;
import com.pangu.neusoft.core.models.MemberReg;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.db.People;
import com.pangu.neusoft.healthcard.ListCardActivity;
import com.pangu.neusoft.healthcard.RegisterActivity;
import com.pangu.neusoft.tools.DialogShow;
import com.pangu.neusoft.tools.StringMethods;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class UserInfoActivity extends FatherActivity {
	private SharedPreferences sp;
	private Editor editor;
	private ProgressDialog mProgressDialog; 
	
	private EditText username;
	private Spinner sex;
	private EditText card_num;
	private EditText membername;
	private WebService service;
	
	
	String UserName;
	String PhoneNumber;
	String MemberName;
	String Sex;
	String IDCardNo;
	String RegTime;
	String LastLoginTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		setactivitytitle("个人信息");
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		mProgressDialog = new ProgressDialog(UserInfoActivity.this);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        
  	  Button reg_btn=(Button)findViewById(R.id.reg_reg_btn);
	  reg_btn.setOnClickListener(changeinfo);
	  
	  username=(EditText)findViewById(R.id.reg_username);
      sex=(Spinner)findViewById(R.id.reg_sex);
      card_num=(EditText)findViewById(R.id.card_num);
      membername=(EditText)findViewById(R.id.reg_name);
      service=new WebService();
      username.setEnabled(false);
		getUserInfo();
		username.clearFocus();//取消输入法焦点
		sex.setFocusable(true);
		sex.setFocusableInTouchMode(true);
		sex.requestFocus();
	}

	public void getUserInfo(){
		new AsyncTask<Void, Void, Boolean>(){
			String msg="注册失败";
		    @SuppressWarnings("deprecation")
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute();   
	            mProgressDialog.show();
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				CardListReq req=new CardListReq();
				req.setUserName(sp.getString("username", ""));
				req.setAucode(GET.Aucode);
				
				SoapObject obj= service.getUserInfo(req,"GetMemberInfo");
				
				if(obj!=null){
					String IsGetInfoSuccess=obj.getProperty("IsGetInfoSuccess").toString();//0000成功1111报错
					String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
					msg=obj.getProperty("msg").toString();//返回的信息
					 UserName=obj.getProperty("UserName").toString();
					 PhoneNumber=obj.getProperty("PhoneNumber").toString();
					 MemberName=obj.getProperty("MemberName").toString();
					 Sex=obj.getProperty("Sex").toString();
					 IDCardNo=obj.getProperty("IDCardNo").toString();
					 RegTime=obj.getProperty("RegTime").toString();
					 LastLoginTime=obj.getProperty("LastLoginTime").toString();
					if(IsGetInfoSuccess.equals("true")){
						
						return true;
					}else{
						return false;
					}
					
				}
				else{
					msg="注册失败";
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
				
				if(result){
					username.setText(UserName);
					ArrayAdapter adapter=(ArrayAdapter) sex.getAdapter();
					int position=adapter.getPosition(Sex);
					sex.setSelection(position,true);
					card_num.setText(IDCardNo);
					membername.setText(UserName);
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
	}
	
	public String checkData(){
		String msg="";
		if(username.getText().toString().equals("")){
			msg+="用户名不能为空\n";
			username.setHint(msg);
			username.setHintTextColor(Color.RED);
			return msg;
		}		
		if(!StringMethods.isMobileNO(username.getText().toString())||username.getText().length()!=11){
			msg+="用户名必须为11位手机号码";
			username.setText("");
			username.setHint(msg);
			username.setHintTextColor(Color.RED);
			return msg;
		}
		if(card_num.getText().toString().equals("")||card_num.getText().length()!=18){
			msg+="身份证号码必须是18位\n";
			card_num.setText("");
			card_num.setHint(msg);
			card_num.setHintTextColor(Color.RED);
			return msg;
		}
		if(membername.getText().toString().equals("")){
			msg+="姓名不能为空\n";
			membername.setText("");
			membername.setHint(msg);
			membername.setHintTextColor(Color.RED);
			return msg;
		}
		return msg;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_info, menu);
		return true;
	}
	
	 OnClickListener changeinfo=new OnClickListener(){//修改信息
			@Override
			public void onClick(View v) {
				String msg=checkData();
				if(msg.equals("")){
					new AsyncTask<Void, Void, Boolean>(){
						String msg="注册失败";
					    @SuppressWarnings("deprecation")
						@Override  
				        protected void onPreExecute() {   
				            super.onPreExecute();   
				            mProgressDialog.show();
				        }			
						@Override
						protected Boolean doInBackground(Void... params){
							MemberChangeReg member=new MemberChangeReg();
							 
							 member.setUserName(username.getText().toString().trim());							 
							 member.setIDCardNo(card_num.getText().toString());
							 member.setMemberName(membername.getText().toString());
							 String gender=sex.getSelectedItem().toString();
							 if(gender.equals("男"))
								 member.setSex(1);
							 else
								 member.setSex(0);
							 member.setAucode(GET.Aucode);
							 
							 SoapObject obj= service.modifyMemberData(member,"ModifyMemberInfo");
							 
							if(obj!=null){
								String IsModifyInfoSuccess=obj.getProperty("IsModifyInfoSuccess").toString();//0000成功1111报错
								String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
								msg=obj.getProperty("msg").toString();//返回的信息

								if(IsModifyInfoSuccess.equals("true")){
									
									return true;
								}else{
									return false;
								}
								
							}
							else{
								msg="注册失败";
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
							DialogShow.showDialog(UserInfoActivity.this, msg);
							if(result){
								DBManager mgr=new DBManager(UserInfoActivity.this);
								People person= new People();
								person.setSex(sex.getSelectedItem().toString());
								person.setUsername(membername.getText().toString());
								person.setPhone(username.getText().toString());
								person.setAddress("");
								person.setLicence_num(card_num.getText().toString());
								person.setLicence_type("身份证");
								mgr.update(person);
								/*
								final Timer t = new Timer();
								t.schedule(new TimerTask() {
									public void run() {
										finish();
										t.cancel(); 
									}
								}, Setting.dialogtimeout+1000);
								*/
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
				}
//				else{
//					DialogShow.showDialog(RegisterActivity.this, msg);
//				}
			}
	  };

}
