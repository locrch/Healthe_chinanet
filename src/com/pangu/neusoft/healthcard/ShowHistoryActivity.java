package com.pangu.neusoft.healthcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.baidu.mobstat.StatService;
import com.pangu.neusoft.adapters.HistoryListAdapter;
import com.pangu.neusoft.adapters.PullToRefreshView.OnFooterRefreshListener;
import com.pangu.neusoft.adapters.PullToRefreshView.OnHeaderRefreshListener;
import com.pangu.neusoft.adapters.DepartmentListAdapter;
import com.pangu.neusoft.adapters.PullToRefreshView;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.BookingInfos;
import com.pangu.neusoft.core.models.Department;
import com.pangu.neusoft.core.models.DepartmentReq;
import com.pangu.neusoft.core.models.HandleBooking;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.healthe.BookingMainActivity;
import com.pangu.neusoft.healthe.DepartmentListActivity;
import com.pangu.neusoft.healthe.FatherActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
import com.pangu.neusoft.tools.DialogShow;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ShowHistoryActivity extends FatherActivity  implements OnHeaderRefreshListener,OnFooterRefreshListener{

	TextView user_card_text;
	Button change_card_btn;
	ListView booking_history_list;
	//ListView booking_history_list_passed;
	//ListView booking_history_list_cancled;
	public DBManager mgr;
	String username;
	//List<BookingInfos> list;
	HistoryListAdapter adapter;
	
	//String cancleid;
	//String hospitalid;
	private SharedPreferences sp;
	private Editor editor;
	ArrayList<HashMap<String,String>> hist_array;
	ArrayList<HashMap<String,String>> hist_array_temp;
	PullToRefreshView mPullToRefreshView;
	int count=0;
	int start=0;
	int end=10;
	int totalpage=0;
	int totalsize=0;
	int page=0;
	
	public DBManager getMgr() {
		return mgr;
	}

	public void setMgr(DBManager mgr) {
		this.mgr = mgr;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_history);
		setactivitytitle("我的预约");
		booking_history_list=(ListView)findViewById(R.id.booking_history_list_using);
		//booking_history_list_passed=(ListView)findViewById(R.id.booking_history_list_passed);
		//booking_history_list_cancled=(ListView)findViewById(R.id.booking_history_list_cancled);
		
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
        
		//setListAdapter(new DataAdapter(this));
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);
		
		user_card_text=(TextView)findViewById(R.id.user_card_text);
		change_card_btn=(Button)findViewById(R.id.change_card_btn);
		change_card_btn.setOnClickListener(changecard);
		
		mgr=new DBManager(ShowHistoryActivity.this);
		
		
		user_card_text.clearFocus();
		mPullToRefreshView.setFocusable(true);
		mPullToRefreshView.setFocusableInTouchMode(true);
		mPullToRefreshView.requestFocus();
		
		
       // updatedata();
        /*	
		SimpleAdapter adapter=new SimpleAdapter(
					this,hist_array,R.layout.history_content,new String[]{"hospital","department","doctor","datatime","cancleid","hospitalid","SerialNumber"},
					new int[]{R.id.his_hospital,R.id.hisdDepartment,R.id.his_doctor,R.id.his_date_time,R.id.his_cancle_id,R.id.his_hospitalid,R.id.seria_num});
		booking_history_list.setAdapter(adapter);	
		booking_history_list.setOnItemClickListener(item_click);
		*/
		mgr.closeDB();
		
	}

	public void setList(String type){
		 
		List<BookingInfos> list=mgr.getBookingList(username,type);
		for(BookingInfos booking:list){
		//R.id.his_username,R.id.his_cancle_id,R.id.his_doctor,R.id.his_num,R.id.his_time,R.id.his_hospitalid,R.id.his_doctorid
				HashMap<String,String> map=new  HashMap<String,String>();
				map.put("his_username", booking.getUsername());
				map.put("his_doctor",booking.getDoctorname());
				map.put("his_department", "-"+booking.getDepartmentname()+"-");
				map.put("his_hospital", booking.getHospitalname());
				map.put("his_time", booking.getReservedate()+" "+booking.getReservetime());
				map.put("his_cancle_id", booking.getCancelid());
				map.put("his_hospitalid", booking.getHospitalid());
				map.put("his_num", booking.getSerialNumber());
				map.put("his_doctorid", booking.getDoctorid());
				if(type.equals("booking")){
					map.put("type", " 有效 ");
				}else if(type.equals("cancled")){
					map.put("type", "已取消");
				}else{
					map.put("type", "已过期");
				}
				
				hist_array.add(map);
			
		}
	}
	

	public void updatedata(){
	
	        totalsize=hist_array.size();
	        totalpage=totalsize/Setting.history_list_show;
	        int res=totalsize%Setting.history_list_show;
	        if(res!=0){
	        	totalpage+=1;
	        }
	        
	        	if(page<=totalpage){
	        	start=page*10;
	        	end=page*10+10;
	        	if(end>totalsize){
	        		end=totalsize;
	        	}
		        for(int i=start;i<end;i++){
		        	if(count<totalsize){
	        		//HashMap<String,String> mapx=(HashMap<String,String>)hist_array.get(i);
	        		//mapx.put("hospital", mapx.get("hospital")+count);
		        		//hist_array.set(i, mapx);
		        		hist_array_temp.add(hist_array.get(i));
		        		count+=1;
		        	}
		        }
		        if(hist_array_temp.size()>0){
			        adapter=new HistoryListAdapter(
								this,hist_array_temp,R.layout.history_content,new String[]{"his_username","his_doctor","his_num","his_time","his_hospitalid","his_doctorid","type","his_cancle_id","his_department","his_hospital"},
								new int[]{R.id.his_username,R.id.his_doctor,R.id.his_num,R.id.his_time,R.id.his_hospitalid,R.id.his_doctorid,R.id.his_state,R.id.his_cancle_id,R.id.his_department,R.id.his_hospital});
					
					booking_history_list.setAdapter(adapter);	
					//booking_history_list.setOnItemClickListener(item_click);
		        }else{
		        	
		        	String[] strs = new String[] {"（暂无预约记录）"};
		        	booking_history_list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, strs));
		        }
	        }
	}
	
	OnClickListener changecard=new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Setting.state="history";
			startActivity(new Intent(ShowHistoryActivity.this,ListCardActivity.class));
			//finish();
		}
	};
	
//	OnItemClickListener item_click=new OnItemClickListener(){
//		@Override
//		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//		  HashMap<String,String> map=(HashMap<String,String>)booking_history_list.getItemAtPosition(arg2);
//		  cancl/eid=map.get("cancleid");
//		  hospitalid=map.get("hospitalid");
//		 // if(type.equals("booking"))
//		//	  dialog();
//		
//		}
//	};

	
	
	
	@Override  
	protected void onDestroy() {  
	    super.onDestroy();  
	    if (mgr  != null) {  
	    	mgr.closeDB();  
	    }  
	}  
	
	public void update(){
		mPullToRefreshView.onHeaderRefreshComplete();
        start=0;
        page=0;
        hist_array_temp.clear();
        setList("booking");
        setList("cancled");
        setList("passed");
        updatedata();
        
	}
	
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();
				
				updatedata();
				page+=1;
			}
		}, 1000);
	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				refresh();
				 //update();
			}
		},1000);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
				//取得默认卡号
				sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
				editor = sp.edit();
				String cardname=Setting.getDefaultCardNumber(sp,editor);
				user_card_text.setText(cardname);		
				username=sp.getString("card"+sp.getString("defaultcardno","")+"_"+"owner","");
				
				
		        hist_array=new ArrayList<HashMap<String,String>> ();
		        hist_array_temp=new ArrayList<HashMap<String,String>> ();
				//for(int i=0;i<10;i++){
		    	setList("booking");
		        setList("cancled");
		        setList("passed");
		        // }
		        start=0;
		        page=0;
		
		 updatedata();
		//PushManager.resumeWork(getApplicationContext());
	}
	
	
	 public void refresh() {  
	         
	        Intent intent = new Intent(ShowHistoryActivity.this, ShowHistoryActivity.class);  
	        startActivity(intent);  
	        finish();
	    }  
}
