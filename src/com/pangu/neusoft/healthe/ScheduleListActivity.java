package com.pangu.neusoft.healthe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.Area;
import com.pangu.neusoft.core.models.AreaReq;
import com.pangu.neusoft.core.models.AreaResponse;
import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.core.models.SchedulingReq;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ScheduleListActivity extends FatherActivity {
	//ListView schedulelistView;
	WebService service;
	LinearLayout layout;
	
	SharedPreferences sp;
	private ProgressDialog mProgressDialog; 
	Editor editor;
	
	public final class ViewHolder{  
        TextView texta;  
    }
	
	ArrayList<Schedule> list;
	boolean connecting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_show);
		//schedulelistView=(ListView)findViewById(R.id.area_list);
		this.setactivitytitle("选择就诊时间");
		layout=(LinearLayout)findViewById(R.id.layout_scheduling);
		
		
		service=new WebService();
		
		sp= getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor=sp.edit();
	  	mProgressDialog = new ProgressDialog(ScheduleListActivity.this);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		
		new AsyncTask<Void, Void, Boolean>(){
		    @SuppressWarnings("deprecation")
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute();   
	            mProgressDialog.show();
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				SchedulingReq req=new SchedulingReq();
				req.setHospitalId(sp.getString("hosiptalId", ""));
				req.setDepartmentId(sp.getString("departmentId", ""));
				req.setDoctorId(sp.getString("doctorId", ""));
				req.setBeginDate("");
				req.setEndDate("");
				req.setAucode(GET.Aucode);
				SoapObject  obj = service.getSchedulingList(req);
				
				if(obj!=null){
					list=new ArrayList<Schedule>();
					
					SoapObject scheduleObject=(SoapObject)obj.getProperty("scheduleList");
					for(int i=0;i <scheduleObject.getPropertyCount();i++){ 
						
						SoapObject soapChilds =(SoapObject)scheduleObject.getProperty(i); 
						String schState=soapChilds.getProperty("schState").toString();
						String doctorId=soapChilds.getProperty("doctorId").toString();
						String doctorName=soapChilds.getProperty("doctorName").toString();
						String regId=soapChilds.getProperty("regId").toString();
						String regName=soapChilds.getProperty("regName").toString();
						String isSuspend=soapChilds.getProperty("isSuspend").toString();
						String outcallDate=soapChilds.getProperty("outcallDate").toString();
						String dayOfWeek=soapChilds.getProperty("dayOfWeek").toString();
						String timeRange=soapChilds.getProperty("timeRange").toString();
						String availableNum=soapChilds.getProperty("availableNum").toString();
						String consultationFee=soapChilds.getProperty("consultationFee").toString();
						String scheduleID=soapChilds.getProperty("scheduleID").toString();
						
						Schedule schedule=new  Schedule();
						
						schedule.setAvailableNum(availableNum);
						schedule.setConsultationFee(consultationFee);
						schedule.setDayOfWeek(dayOfWeek);
						schedule.setDoctorId(doctorId);
						schedule.setDoctorName(doctorName);
						schedule.setIsSuspend(isSuspend);
						schedule.setOutcallDate(outcallDate);
						schedule.setRegId(regId);
						schedule.setRegName(regName);
						schedule.setScheduleID(scheduleID);
						schedule.setSchState(schState);
						schedule.setTimeRange(timeRange);
						
						
						list.add(schedule);
					}		
					
					String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
					String msg=obj.getProperty("msg").toString();//返回的信息
					
					
					Log.e("error1", resultCode);
					Log.e("error2", msg);
					
					return true;
				}
				else
					return false;
			}
			
			@SuppressWarnings("deprecation")
			@Override
			protected void onPostExecute(Boolean result){
				super.onPostExecute(result);
				if (!result){
					HashMap<String,String> map=new HashMap<String,String>();
					map.put("scheduleID", "");
					if(list==null){
						
						map.put("timeRange", "请检查网络连接");
					}else{
						
						map.put("timeRange", "没有数据");
					}
					
					connecting=false;
					
				}else{
					connecting=true;				
				}
				//showInList();
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
				}
				GetSchedule sch=new GetSchedule();
				View scheduleView=sch.getScheduleView(ScheduleListActivity.this, list);
				layout.addView(scheduleView);
			}
			@SuppressWarnings("deprecation")
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				HashMap<String,String> map=new HashMap<String,String>();
				map.put("scheduleID", "");
				map.put("timeRange", "取消连接。。");
				//scheduleArrayList.add(map);
				//showInList();
			}
			
		
			/*public void showInList(){
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
				}
				
				/*
				 * map.put("scheduleID", item.getScheduleID());
				map.put("regID", item.getRegId());
				map.put("number", item.getAvailableNum());
				map.put("schstate", item.getSchState());
				map.put("timeRange", item.getTimeRange());
				*/
				
			//}
		}.execute();
	}


	  
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.area_list, menu);
		return true;
	}

}
