package com.pangu.neusoft.healthcard;

import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.ChangePassReq;
import com.pangu.neusoft.core.models.GetCAPTCHAReg;
import com.pangu.neusoft.core.models.MemberReg;
import com.pangu.neusoft.healthe.FatherActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.tools.DialogShow;
import com.pangu.neusoft.tools.StringMethods;

public class ChangePassActivity extends FatherActivity{
	
	EditText userphone_text;
	EditText ver_pass_text;
	EditText new_pass_text;
	EditText confirm_pass_text;
	Button get_ver_btn;
	Button reset_pass_btn;
	private ProgressDialog mProgressDialog; 
	WebService service;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_pass);
		
		userphone_text=(EditText)findViewById(R.id.userphone);
		ver_pass_text=(EditText)findViewById(R.id.ver_pass);
		new_pass_text=(EditText)findViewById(R.id.new_pass);
		confirm_pass_text=(EditText)findViewById(R.id.confirm_pass);
		get_ver_btn=(Button)findViewById(R.id.get_ver);
		reset_pass_btn=(Button)findViewById(R.id.reset_pass);
		Intent intent = getIntent();
		String userphone=intent.getStringExtra("userphone");
		if(userphone!=null){
			userphone_text.setText(userphone);
		}
		
		get_ver_btn.setOnClickListener(getver);
		reset_pass_btn.setOnClickListener(change_click);
		
		mProgressDialog = new ProgressDialog(ChangePassActivity.this);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        service=new WebService();
	}
	
	OnClickListener getver=new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			final String phone=userphone_text.getText().toString();
			SendCaptcha sendAction=new SendCaptcha(phone,mProgressDialog,service,ChangePassActivity.this,get_ver_btn);
			sendAction.sendData();
		}
	};

	public String checkData(){
		String msg="";
		if(userphone_text.getText().toString().equals("")){
			msg+="用户名不能为空\n";
			userphone_text.setHint(msg);
			userphone_text.setHintTextColor(Color.RED);
			return msg;
		}		
		if(ver_pass_text.getText().toString().equals("")){
			msg+="密码不能为空\n";
			ver_pass_text.setHint(msg);
			ver_pass_text.setHintTextColor(Color.RED);
			return msg;
		}
		if(!StringMethods.isMobileNO(userphone_text.getText().toString())||userphone_text.getText().length()!=11){
			msg+="用户名必须为11位手机号码";
			userphone_text.setText("");
			userphone_text.setHint(msg);
			userphone_text.setHintTextColor(Color.RED);
			return msg;
		}
		if(!new_pass_text.getText().toString().equals(confirm_pass_text.getText().toString())){
			msg+="两次密码输入不同\n";
			confirm_pass_text.setText("");
			new_pass_text.setText("");
			confirm_pass_text.setHint(msg);			
			confirm_pass_text.setHintTextColor(Color.RED);
			return msg;
		}
		
		if(ver_pass_text.getText().toString().equals("")){
			msg+="请输入验证码\n";
			ver_pass_text.setText("");
			ver_pass_text.setHint(msg);
			ver_pass_text.setHintTextColor(Color.RED);
			return msg;
		}
		return msg;
	}
	OnClickListener change_click=new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			final String phone=userphone_text.getText().toString();
			final String ver=ver_pass_text.getText().toString();
			final String newpass=new_pass_text.getText().toString();
			final String confirmpass=confirm_pass_text.getText().toString();
			
			String msg=checkData();
			if(msg.equals("")){
				new AsyncTask<Void, Void, Boolean>(){
					
					String msg="密码修改失败！";
				    @SuppressWarnings("deprecation")
					@Override  
			        protected void onPreExecute() {   
			            super.onPreExecute();   
			            mProgressDialog.show();
			        }			
					@Override
					protected Boolean doInBackground(Void... params){
						ChangePassReq reg=new ChangePassReq();
							reg.setUserName(phone);
							reg.setNewPassword(newpass);
							reg.setNewPasswordAgain(confirmpass);
							reg.setCAPTCHA(ver);
							reg.setAucode(GET.Aucode);
						 SoapObject obj= service.changePass(reg,"ResetPassword");
						 if(obj!=null){
							 String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
							 msg=obj.getProperty("msg").toString();//返回的信息
							 Log.e("",msg);
							 String IsGetSuccess=obj.getProperty("IsResetPasswordSuccess").toString();//0000成功1111报错
							 if(IsGetSuccess.equals("true"))
								 return true;
							 else 
								 return false;
						 }else 
							 return false;
					}
					@SuppressWarnings("deprecation")
					@Override
					protected void onPostExecute(Boolean result){
						super.onPostExecute(result);
						
						if(mProgressDialog.isShowing()){
							mProgressDialog.dismiss();
						}
						DialogShow.showDialog(ChangePassActivity.this, msg);
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
		}
	};
}
