package com.pangu.neusoft.healthcard;

import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.serialization.SoapObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.GetCAPTCHAReg;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.tools.DialogShow;

public class SendCaptcha {
	private String phone;
	private ProgressDialog mProgressDialog; 
	private WebService service;
	private Activity activity;
	private Button button;
	private int time=Setting.countDownGetCaptcha;
	public SendCaptcha(String phone,ProgressDialog mProgressDialog,WebService service,Activity activity,Button button){
		this.phone=phone;
		this.mProgressDialog=mProgressDialog;
		this.service=service;
		this.activity=activity;
		this.button=button;
	}
	
	public void sendData(){
		
		if(!phone.equals("")&&mProgressDialog!=null&&service!=null)
			new AsyncTask<Void, Void, Boolean>(){
				
				String msg="验证码已经发送";
			    @SuppressWarnings("deprecation")
				@Override  
		        protected void onPreExecute() {   
		            super.onPreExecute();   
		            mProgressDialog.show();
		        }			
				@Override
				protected Boolean doInBackground(Void... params){
					 GetCAPTCHAReg getReg=new GetCAPTCHAReg();
					 getReg.setPhoneNumber(phone);
					 getReg.setAucode(GET.Aucode);
					 SoapObject obj= service.sendVerfily(getReg,"GetCAPTCHA");
					 if(obj!=null){
						 String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
						 msg=obj.getProperty("msg").toString();//返回的信息
						 Log.e("",msg);
						 String IsGetSuccess=obj.getProperty("IsGetSuccess").toString();//0000成功1111报错
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
					if(result){
						DialogShow.showDialog(activity, msg);
						button.setClickable(false);
						
						
						TimerTask task=new TimerTask(){
							@Override
							public void run() {
							  time--;
							  activity.runOnUiThread(new Runnable(){
								@Override
								public void run() {
									button.setText("等待"+"("+time+")秒");	
								}								  
							  });
							  
							  if(time<0)
							  {
								 activity.runOnUiThread(new Runnable(){
										@Override
										public void run() {
											button.setText(activity.getResources().getString(R.string.get_captcha));
											button.setClickable(true);
								 		}								  
								 });
							      this.cancel();
							  }
							}
						};
						Timer timer=new Timer();
						timer.schedule(task,0,1000);
					}
					else{
						DialogShow.showDialog(activity, "验证码获取失败，请重试！");
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
}
