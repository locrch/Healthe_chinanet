package com.pangu.neusoft.healthcard;

import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.serialization.SoapObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.GetCAPTCHAReg;
import com.pangu.neusoft.core.models.MemberReg;
import com.pangu.neusoft.healthe.FatherActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.tools.DialogShow;

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
			
			if(!phone.equals(""))
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
					if(result)
						DialogShow.showDialog(ChangePassActivity.this, msg);
					else
						DialogShow.showDialog(ChangePassActivity.this, "验证码获取失败，请重试！");
					
											
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
	};

	OnClickListener change_click=new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}
	};
}
