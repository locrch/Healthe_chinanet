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
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.db.People;
import com.pangu.neusoft.healthe.BookingMainActivity;
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
import android.widget.Spinner;

public class RegisterActivity extends FatherActivity {
	private SharedPreferences sp;
	private Editor editor;
	private WebService service;
	private ProgressDialog mProgressDialog; 
	private EditText username;
	private EditText password;
	
	private EditText confirm_password;
	private Spinner sex;
	private EditText card_num;
	private EditText membername;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_layout);
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		service=new WebService();
	  	mProgressDialog = new ProgressDialog(RegisterActivity.this);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		
		  Button reg_btn=(Button)findViewById(R.id.reg_reg_btn);
		  reg_btn.setOnClickListener(login);
		  
	      username=(EditText)findViewById(R.id.reg_username);
	      password=(EditText)findViewById(R.id.reg_password);
	      confirm_password=(EditText)findViewById(R.id.reg_password_con);
	      sex=(Spinner)findViewById(R.id.reg_sex);
	      card_num=(EditText)findViewById(R.id.card_num);
	      membername=(EditText)findViewById(R.id.reg_name);
	      
	      //username.setText(sp.getString("username", ""));
	      //password.setText(sp.getString("password", ""));
	      
		  
		 
		 
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
		if(!password.getText().toString().equals(confirm_password.getText().toString())){
			msg+="两次密码输入不同\n";
		}
		if(card_num.getText().toString().equals("")){
			msg+="身份证号码不能为空\n";
		}
		if(membername.getText().toString().equals("")){
			msg+="姓名不能为空\n";
		}
		return msg;
	}
	
	 OnClickListener login=new OnClickListener(){//注册
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
							 MemberReg member=new MemberReg();
							 
							 member.setUserName(username.getText().toString().trim());
							 member.setPassword(password.getText().toString());
							 
							 member.setIDCardNo(card_num.getText().toString());
							 member.setMemberName(membername.getText().toString());
							 String gender=sex.getSelectedItem().toString();
							 if(gender.equals("男"))
								 member.setSex(1);
							 else
								 member.setSex(0);
							 
							 member.setCAPTCHA("");
							 
							 member.setAucode(GET.Aucode);
							 SoapObject obj= service.sendMemberData(member,"userRegister");
							 
							if(obj!=null){
								String IsRegisterSuccess=obj.getProperty("IsRegisterSuccess").toString();//0000成功1111报错
								String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
								msg=obj.getProperty("msg").toString();//返回的信息
								
								
								
								if(IsRegisterSuccess.equals("true")){
									editor.putString("username", member.getUserName());
									editor.putString("password", member.getPassword());
									editor.commit();
									
									
									
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
							DialogShow.showDialog(RegisterActivity.this, msg);
							if(result){
								DBManager mgr=new DBManager(RegisterActivity.this);
								People person= new People();
								person.setSex(sex.getSelectedItem().toString());
								person.setUsername(membername.getText().toString());
								person.setPhone(username.getText().toString());
								person.setAddress("");
								person.setLicence_num(card_num.getText().toString());
								person.setLicence_type("身份证");
								
								mgr.add(person);
								
								final Timer t = new Timer();
								t.schedule(new TimerTask() {
									public void run() {
										startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
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
					DialogShow.showDialog(RegisterActivity.this, msg);
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
