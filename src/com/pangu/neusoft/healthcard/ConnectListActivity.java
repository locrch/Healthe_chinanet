package com.pangu.neusoft.healthcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.adapters.DoctorList;
import com.pangu.neusoft.adapters.DoctorListAdapter;
import com.pangu.neusoft.adapters.HospitalList;
import com.pangu.neusoft.adapters.HospitalListAdapter;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.ConnectDoctor;
import com.pangu.neusoft.core.models.Doctor;
import com.pangu.neusoft.core.models.DoctorInfoReq;
import com.pangu.neusoft.core.models.DoctorReq;
import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.healthe.BookingMainActivity;
import com.pangu.neusoft.healthe.FatherActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.ScheduleListActivity;
import com.pangu.neusoft.healthe.Setting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ConnectListActivity extends FatherActivity {
	ListView doctorlistView;
	WebService service;
	List<DoctorList> doctorList;
	
	List<ConnectDoctor> doctors;
	SharedPreferences sp;
	Editor editor;
	private ProgressDialog mProgressDialog;
	
	public final class ViewHolder{  
        TextView texta;  
    }
	boolean connecting;
	TextView infos_text,infos_notext;
	HashMap<String,ConnectDoctor> doctormap=new HashMap<String,ConnectDoctor>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		setactivitytitle("名医收藏");
		doctorlistView=(ListView)findViewById(R.id.list);
		infos_notext=(TextView)findViewById(R.id.infos_notext);
		service=new WebService();
		
		
		
		sp= getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor=sp.edit();
		
		infos_text=(TextView)findViewById(R.id.infos_text);
		infos_text.setVisibility(View.GONE);
		
		mProgressDialog = new ProgressDialog(ConnectListActivity.this);
		mProgressDialog.setMessage("正在加载数据...");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setCanceledOnTouchOutside(false);// 设置进度条是否可以按退回键取消
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		
		
		
		
		
		
		
		
		
//		for(ConnectDoctor doctor:doctors){
//			DoctorList list=new DoctorList();
//			list.setId(doctor.getHospitalid()+"|"+doctor.getDepartmentid()+"|"+doctor.getDoctorid());
//			list.setImageUrl(doctor.getImageUrl());
//			list.setLevel(doctor.getLevel());
//			list.setText(doctor.getHospitalname()+"\n"+doctor.getDepartmentname()+"\n"+doctor.getDoctorname()+"\n"+doctor.getHospitalid());
//			list.setVersion(doctor.getVersion());
//			doctormap.put(doctor.getDoctorid()+doctor.getDoctorname(), doctor);
//			doctorList.add(list);
//		}	
		showlist();
		
	}


	public void showInList(){
		 
	
		DoctorListAdapter adapter=new DoctorListAdapter(ConnectListActivity.this, doctorList);
		
		if (doctorList.size()==1)
		{
			//如果医生列表只有一个数据，就自定义设置高度
			doctorlistView.getLayoutParams().height=(int)getResources().getDimension(R.dimen.booking_main_scroll_height);
		}
		
		doctorlistView.setAdapter(adapter);
		doctorlistView.setClickable(true);
		doctorlistView.setFocusable(true);
		
		//doctorlistView.getLayoutParams().height=2000;
		
		/*doctorlistView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
			{
				DoctorList map=(DoctorList)doctorlistView.getItemAtPosition(arg2);
				
				String doctorId=map.getId(); //获得Areaid		
				String doctorText=map.getText(); //获得AreaName
				String[] arraydoctorText=doctorText.split("\n");
				String doctorName=arraydoctorText[2];
				
				String version=map.getVersion();
				
				ConnectDoctor doctor=doctormap.get(doctorId+doctorName);
				if(doctor!=null){
				
					//记录医生信息
					editor.putString("hospitalId", doctor.getHospitalid());
					editor.putString("hospitalName", doctor.getHospitalname());
					editor.putString("departmentId", doctor.getDepartmentid());
					editor.putString("departmentName", doctor.getDepartmentname());
				
					editor.putString("doctorId", doctorId);
					editor.putString("doctorName", doctorName);
					
					editor.commit();
					startActivity(new Intent (ConnectListActivity.this, ScheduleListActivity.class));
				}
			}
		});*/
	}
	  

	
	protected void onRestart(){
		super.onRestart();
		showlist();
	}
	
	public void showlist(){
		doctorlistView.removeAllViewsInLayout();
		
		DBManager mgr=new DBManager(ConnectListActivity.this);
		doctors=mgr.queryConnectDoctors(sp.getString("username", ""));
		mgr.closeDB();
		
		doctorList=new ArrayList<DoctorList>();	
		
		new AsyncTask<Void, Void, Boolean>()
		{

			@Override
			protected void onPreExecute()
			{
				super.onPreExecute();
				mProgressDialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... params)
			{
				for(ConnectDoctor doctor:doctors){
				
					DoctorInfoReq req=new DoctorInfoReq();
					req.setHospitalId(doctor.getHospitalid());
					req.setDoctorId(doctor.getDoctorid());
					req.setAucode(GET.Aucode);
					SoapObject  obj = service.getDoctorDetail(req);
					if (obj != null){
						SoapObject areaObject=(SoapObject)obj.getProperty("doctordetail");					
						String doctorId=areaObject.getProperty("doctorId").toString();
						String doctorName=areaObject.getProperty("doctorName").toString();
						String sex=areaObject.getProperty("sex").toString();
						String title=areaObject.getProperty("title").toString();
						String info="";
						String version = areaObject.getProperty("version").toString();
	
							String imageUrl;
	
							try
							{
								if (areaObject.getProperty("pictureUrl") == null)
								{
	
									imageUrl = Setting.DEFAULT_DOC_url;
								} else
								{
	
									imageUrl = areaObject.getProperty("pictureUrl")
											.toString();
								}
	
							} catch (Exception ex)
							{
								imageUrl = Setting.TEST_url;
							}
	
//							SoapObject scheduleListObject = (SoapObject) areaObject.getProperty("scheduleList");
//							List<Schedule> schedulelist = new ArrayList<Schedule>();
//							for (int j = 0; j < scheduleListObject.getPropertyCount(); j++)
//							{
//								SoapObject scheduleObject = (SoapObject) scheduleListObject.getProperty(j);
//								Schedule sch = new Schedule();
//								if (scheduleObject.getProperty("availableNum") != null)
//									sch.setAvailableNum(scheduleObject.getProperty(
//											"availableNum").toString());
//	
//								if (scheduleObject.getProperty("consultationFee") != null)
//									sch.setConsultationFee(scheduleObject
//											.getProperty("consultationFee")
//											.toString());
//	
//								if (scheduleObject.getProperty("dayOfWeek") != null)
//									sch.setDayOfWeek(scheduleObject.getProperty(
//											"dayOfWeek").toString());
//	
//								if (scheduleObject.getProperty("doctorId") != null)
//									sch.setDoctorId(scheduleObject.getProperty(
//											"doctorId").toString());
//	
//								if (scheduleObject.getProperty("doctorName") != null)
//									sch.setDoctorName(scheduleObject.getProperty(
//											"doctorName").toString());
//	
//								if (scheduleObject.getProperty("isSuspend") != null)
//									sch.setIsSuspend(scheduleObject.getProperty(
//											"isSuspend").toString());
//	
//								if (scheduleObject.getProperty("outcallDate") != null)
//									sch.setOutcallDate(scheduleObject.getProperty(
//											"outcallDate").toString());
//	
//								if (scheduleObject.getProperty("regId") != null)
//									sch.setRegId(scheduleObject
//											.getProperty("regId").toString());
//	
//								if (scheduleObject.getProperty("regName") != null)
//									sch.setRegName(scheduleObject.getProperty(
//											"regName").toString());
//	
//								if (scheduleObject.getProperty("scheduleID") != null)
//									sch.setScheduleID(scheduleObject.getProperty(
//											"scheduleID").toString());
//	
//								if (scheduleObject.getProperty("schState") != null)
//									sch.setSchState(scheduleObject.getProperty(
//											"schState").toString());
//	
//								if (scheduleObject.getProperty("timeRange") != null)
//									sch.setTimeRange(scheduleObject.getProperty(
//											"timeRange").toString());
//								schedulelist.add(sch);
//							}
//	
							DoctorList map = new DoctorList();
							map.setId(doctor.getHospitalid()+"|"+doctor.getDepartmentid()+"|"+doctor.getDoctorid());
							map.setText(doctor.getHospitalname()+"|"+doctor.getDepartmentname()+"|"+doctorName);
							map.setImageUrl(imageUrl);
							// 获取未知性别医生有错，返回值为"anyType{}"，下面判定为暂时解决办法，希望服务器端能解决
							if (sex.equals("anyType{}"))
							{
								map.setSex(sex);
								sex = "";
								map.setLevel(title);
							}else {
								map.setSex(sex);
								map.setLevel(title);
							}
	
							
	
							map.setVersion(version);
							//map.setScheduleList(schedulelist);
							doctorList.add(map);
						//}
						String resultCode = obj.getProperty("resultCode")
								.toString();// 0000成功1111报错
						String msg = obj.getProperty("msg").toString();// 返回的信息
						
						Log.e("error1", resultCode);
						Log.e("error2", msg);
						
					} else{
						DoctorList list=new DoctorList();
						list.setId(doctor.getHospitalid()+"|"+doctor.getDepartmentid()+"|"+doctor.getDoctorid());
						list.setImageUrl(doctor.getImageUrl());
						list.setLevel(doctor.getLevel());
						list.setText(doctor.getHospitalname()+"\n"+doctor.getDepartmentname()+"\n"+doctor.getDoctorname()+"\n"+doctor.getHospitalid());
						list.setVersion(doctor.getVersion());
						doctormap.put(doctor.getDoctorid()+doctor.getDoctorname(), doctor);
						doctorList.add(list);
					}
						
				}
				return true;
			}

			@Override
			protected void onPostExecute(Boolean result)
			{
				super.onPostExecute(result);
				if (mProgressDialog.isShowing())
				{
					mProgressDialog.dismiss();
				}
				showInList();
				if (doctorList.size()==0)
				{
					infos_notext.setVisibility(View.VISIBLE);
				}
			}

			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				if (mProgressDialog.isShowing())
				{
					mProgressDialog.dismiss();
				}
			}

			
		}.execute();
	}
	
	
	
	
	

}
