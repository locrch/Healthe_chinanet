package com.pangu.neusoft.healthcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

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
	DBManager mgr;
	String username;
	//List<BookingInfos> list;
	
	private ProgressDialog mProgressDialog; 
	String cancleid;
	String hospitalid;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_history);
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
		
		//取得默认卡号
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		String cardname=Setting.getDefaultCardNumber(sp,editor);
		user_card_text.setText(cardname);		
		username=sp.getString("card"+sp.getString("defaultcardno","")+"_"+"owner","");
		
		user_card_text.clearFocus();
		change_card_btn.setFocusable(true);
		change_card_btn.setFocusableInTouchMode(true);
		change_card_btn.requestFocus();
		
		
		mProgressDialog = new ProgressDialog(ShowHistoryActivity.this);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        hist_array=new ArrayList<HashMap<String,String>> ();
        hist_array_temp=new ArrayList<HashMap<String,String>> ();
		for(int i=0;i<10;i++){
    	setList("booking");
        setList("cancled");
        setList("passed");
      }
        start=0;
        page=0;
       updatedata();
        /*	
		SimpleAdapter adapter=new SimpleAdapter(
					this,hist_array,R.layout.history_content,new String[]{"hospital","department","doctor","datatime","cancleid","hospitalid","SerialNumber"},
					new int[]{R.id.his_hospital,R.id.hisdDepartment,R.id.his_doctor,R.id.his_date_time,R.id.his_cancel_id,R.id.his_hospitalid,R.id.seria_num});
		booking_history_list.setAdapter(adapter);	
		booking_history_list.setOnItemClickListener(item_click);
		*/
		mgr.closeDB();
		
	}

	public void setList(String type){
		 
		List<BookingInfos> list=mgr.getBookingList(username,type);
		for(BookingInfos booking:list){
		
				HashMap<String,String> map=new  HashMap<String,String>();
				map.put("hospital", booking.getHospitalname());
				map.put("department", booking.getDepartmentname());
				map.put("doctor",booking.getDoctorname());
				map.put("datatime", booking.getReservedate()+" "+booking.getReservetime());
				map.put("cancleid", booking.getCancelid());
				map.put("hospitalid", booking.getHospitalid());
				map.put("SerialNumber", booking.getSerialNumber());
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
	        		HashMap<String,String> mapx=(HashMap<String,String>)hist_array.get(i);
	        		mapx.put("hospital", mapx.get("hospital")+count);
		        		hist_array.set(i, mapx);
		        		hist_array_temp.add(hist_array.get(i));
		        		count+=1;
		        	}
		        }
				SimpleAdapter adapter=new SimpleAdapter(
							this,hist_array_temp,R.layout.history_content,new String[]{"hospital","department","doctor","datatime","cancleid","hospitalid","SerialNumber"},
							new int[]{R.id.his_hospital,R.id.hisdDepartment,R.id.his_doctor,R.id.his_date_time,R.id.his_cancel_id,R.id.his_hospitalid,R.id.seria_num});
				booking_history_list.setAdapter(adapter);	
				booking_history_list.setOnItemClickListener(item_click);
	        }
	}
	
	OnClickListener changecard=new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Setting.state="history";
			startActivity(new Intent(ShowHistoryActivity.this,ListCardActivity.class));
		}
	};
	
	OnItemClickListener item_click=new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		  HashMap<String,String> map=(HashMap<String,String>)booking_history_list.getItemAtPosition(arg2);
		  cancleid=map.get("cancleid");
		  hospitalid=map.get("hospitalid");
		 // if(type.equals("booking"))
		//	  dialog();
		
		}
	};

	String message="";
	
	protected void dialog() {
		AlertDialog.Builder builder = new Builder(ShowHistoryActivity.this);
		builder.setMessage("确认要取消预约吗？");
		builder.setTitle("提示");

		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
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

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
				
				mPullToRefreshView.onHeaderRefreshComplete();
		        start=0;
		        page=0;
		        hist_array_temp.clear();
		       updatedata();
			}
		},1000);
		
	}
}
