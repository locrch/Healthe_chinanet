package com.pangu.neusoft.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.adapters.DepartmentListAdapter;
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
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
import com.pangu.neusoft.tools.DialogShow;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ShowHistoryActivity extends FatherActivity {

	ListView booking_history_list;
	DBManager mgr;
	String username;
	List<BookingInfos> list;
	private ArrayList<HashMap<String,String>> hist_array;
	private ProgressDialog mProgressDialog; 
	String cancleid;
	String hospitalid;
	String type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_history);
		booking_history_list=(ListView)findViewById(R.id.booking_history_list);
		mgr=new DBManager(ShowHistoryActivity.this);
		
		Intent intent=getIntent();
		type=intent.getStringExtra("type");
		username=getIntent().getStringExtra("username");
		hist_array=new ArrayList<HashMap<String,String>> ();
		list=mgr.getBookingList(username,type);
		mgr.closeDB();
		
		mProgressDialog = new ProgressDialog(ShowHistoryActivity.this);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		for(BookingInfos booking:list){
			HashMap<String,String> map=new  HashMap<String,String>();
			map.put("hospital", booking.getHospitalname());
			map.put("department", booking.getDepartmentname());
			map.put("doctor",booking.getDoctorname());
			map.put("datatime", booking.getReservedate()+booking.getReservetime());
			map.put("cancleid", booking.getCancelid());
			map.put("hospitalid", booking.getHospitalid());
			map.put("SerialNumber", booking.getSerialNumber());
			hist_array.add(map);
		}
		
		SimpleAdapter adapter=new SimpleAdapter(
				this,hist_array,R.layout.history_content,new String[]{"hospital","department","doctor","datatime","cancleid","hospitalid","SerialNumber"},
				new int[]{R.id.his_hospital,R.id.hisdDepartment,R.id.his_doctor,R.id.his_date_time,R.id.his_cancel_id,R.id.his_hospitalid,R.id.seria_num});
		booking_history_list.setAdapter(adapter);	
		//添加OnItemClickListener
		booking_history_list.setOnItemClickListener(item_click);
		//将musiclist的Adapter设置为adapter
		
		
	}

	OnItemClickListener item_click=new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		  HashMap<String,String> map=(HashMap<String,String>)booking_history_list.getItemAtPosition(arg2);
		  cancleid=map.get("cancleid");
		  hospitalid=map.get("hospitalid");
		  if(type.equals("booking"))
			  dialog();
		
		}
	};

	String message="";
	
	protected void dialog() {
		AlertDialog.Builder builder = new Builder(ShowHistoryActivity.this);
		builder.setMessage("确认要取消预约吗？");
		builder.setTitle("提示");

		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				new AsyncTask<Void, Void, Boolean>(){
				    
					@Override  
			        protected void onPreExecute() {   
			            super.onPreExecute(); 
			            mProgressDialog.show();
			        }			
					@Override
					protected Boolean doInBackground(Void... params){
						HandleBooking req=new HandleBooking();
						req.setAucode(GET.Aucode);
						req.setCancleID(cancleid);
						req.setCardNum("");
						req.setHospitalId(hospitalid);
						req.setOrderNum("");
						req.setReserveDate("");
						req.setReserveTime("");
						
						WebService service = new WebService();
						SoapObject  obj = service.cancelBooking(req);
						
						if(obj!=null){
							String isSuccessful=obj.getProperty("isSuccessful").toString();
							message=obj.getProperty("Message").toString();

							//String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
							//String msg=obj.getProperty("msg").toString();//返回的信息
							//Log.e("error1", message);
							//Log.e("error2", msg);
							if(isSuccessful.equals("true")){
								return true;
								
							}else{
								return false;
							}
						}
						else{
							message="取消失败";
							return false;
						}
					}
					
					@Override
					protected void onPostExecute(Boolean result){
						super.onPostExecute(result);
						if(mProgressDialog.isShowing()){
							mProgressDialog.dismiss();
						}
						if (result){
							mgr.cancleBooking(cancleid);
							mgr.closeDB();
							DialogShow.showDialog(ShowHistoryActivity.this, "取消成功！");
						}else{
							mgr.cancleBooking(cancleid);
							mgr.closeDB();
							DialogShow.showDialog(ShowHistoryActivity.this, message);
						}
						
					}
					@Override
					protected void onCancelled()
					{
						super.onCancelled();
				
					}
				
				}.execute();
			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_history, menu);
		return true;
	}
	@Override  
	protected void onDestroy() {  
	    super.onDestroy();  
	    if (mgr  != null) {  
	    	mgr.closeDB();  
	    }  
	}  
}
