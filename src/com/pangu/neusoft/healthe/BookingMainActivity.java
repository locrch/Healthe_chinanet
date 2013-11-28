package com.pangu.neusoft.healthe;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.adapters.DoctorList;
import com.pangu.neusoft.adapters.DoctorListAdapter;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.DoctorInfoReq;
import com.pangu.neusoft.core.models.DoctorReq;
import com.pangu.neusoft.core.models.FindDoctorListReq;
import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.healthcard.ShowHistoryActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.color;
import com.pangu.neusoft.healthe.R.drawable;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
import com.pangu.neusoft.tools.DensityUtil;
import com.pangu.neusoft.tools.DialogShow;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("ResourceAsColor")
public class BookingMainActivity extends FatherActivity {
	private static final String[] select = { "医生", "医院" };

	private EditText selecttext;
	private ImageButton search_btn;
	private Spinner select_spinner;
	
	private TextView welcome;
	private Button select_area_btn;
	
	private ImageView first;
	private EditText hospital;
	private ImageView second;
	private EditText department;
	private ImageView third;
	private EditText doctor;
	private DBManager mgr;
	//private Button booking;
	private int countTimes=0;
	private SlidingDrawer sd;
	private TextView message;
	private int width;
	private int height;
	private int fontsizex = Setting.fontsizex;
	
	private SharedPreferences sp;
	private Editor editor;
	List<DoctorList> doctorList;
	ListView doctorlistView;
	private String areaId;
	private String areaName;
	private String hospitalId;
	private String hospitalName;
	private String departmentId;
	private String departmentName;
	private String doctorId;
	private String doctorName;
	private ImageView handler;
	private SlidingDrawer slidingDrawer1;
	private ProgressDialog mProgressDialog;
	
	WebService service;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking_main);
		this.setactivitytitle("预约挂号");
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		doctorlistView = (ListView) findViewById(R.id.listHistory);
		doctorList = new ArrayList<DoctorList>();
		service = new WebService();
		getScreenSize();

		select_spinner = (Spinner) findViewById(R.id.spinner1);
		SpinnerAdapter adapter = new SpinnerAdapter(this,
				android.R.layout.simple_spinner_item, select);
		
		
		mgr=new DBManager(BookingMainActivity.this);
		
		
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//搜索部分
		select_spinner.setAdapter(adapter);
		selecttext = (EditText) findViewById(R.id.search_text);
		search_btn = (ImageButton) findViewById(R.id.search_btn);

		//select_spinner.getLayoutParams().width = width / 3;
		//select_spinner.getLayoutParams().height = height / 8;

		//selecttext.getLayoutParams().width=width / 2;
		//selecttext.getLayoutParams().height= height / 9;
		//selecttext.setTextSize(width / fontsizex);
		selecttext.clearFocus();

		//search_btn.getLayoutParams().width=(height / 10);
		//search_btn.getLayoutParams().height=(width / 10);
		//search_btn.setTextSize(width / fontsizex);

		//欢迎您和地区选择部分
		welcome = (TextView) findViewById(R.id.welcome);
		welcome.getLayoutParams().height = height / 10;
		//welcome.setTextSize(width / fontsizex);

		select_area_btn = (Button) findViewById(R.id.select_area_btn);
		//select_area_btn.setTextSize(width / fontsizex);
		
		select_area_btn.setOnClickListener(select_area_click);

		welcome.setWidth(width - select_area_btn.getWidth());
		welcome.setText("欢迎您");

		
		//选择医院、科室、医生部分
		first = (ImageView) findViewById(R.id.cashe_list_image);
		second = (ImageView) findViewById(R.id.imageView2);
		third = (ImageView) findViewById(R.id.imageView3);
		first.getLayoutParams().width = height / 10;
		first.getLayoutParams().height = height / 10;
		second.getLayoutParams().width = height / 10;
		second.getLayoutParams().height = height / 10;
		third.getLayoutParams().width = height / 10;
		third.getLayoutParams().height = height / 10;

		
		hospital = (EditText) findViewById(R.id.hospital);
	//	hospital.setTextSize(width / fontsizex);
		hospital.getLayoutParams().height= height / 8;
		hospital.setOnClickListener(select_hospital_click);

		department = (EditText) findViewById(R.id.department);
		//department.setTextSize(width / fontsizex);
		department.getLayoutParams().height= height / 8;
		department.setOnClickListener(select_department_click);

		doctor = (EditText) findViewById(R.id.doctor);
		//doctor.setTextSize(width / fontsizex);
		doctor.getLayoutParams().height= height / 8;
		doctor.setOnClickListener(select_doctor_click);
		
		search_btn.setOnClickListener(search_btn_click);
		
		//booking = (Button) findViewById(R.id.booking_confirm_btn);
		//booking.setTextSize(width / fontsizex);
		//booking.setHeight(height / 10);
		//booking.setOnClickListener(booking_btn_click);
		
		message = (TextView) findViewById(R.id.messages);
		message.setText("1.每月爽约次数超过3次，将被限制挂号2个自然月。"+"\n"+"\n"+"2.每周累计主动取消次数超过3次，将被限制挂号2个自然月。"+"\n"+"\n"+"3.同一就诊人在同一就诊日、同一医院、同一医生只能预约1次。"+"\n"+"\n"+"4.同一就诊人在同一就诊日、同一医院只能预约2次。"+"\n"+"\n"+"5.同一就诊人每月预约不能超过6次（医院临时停改诊除外）。");
		message.setTextColor(R.color.black_overlay);
		//message.setTextSize(width / fontsizex);

		welcome.setFocusable(true);
		welcome.setFocusableInTouchMode(true);
		welcome.requestFocus();
		
		slidingDrawer1=(SlidingDrawer)findViewById(R.id.slidingDrawer1);
		handler = (ImageView)findViewById(R.id.handle);
		int o_width=width/4;
		int o_height=height/12;
		
		
		handler.getLayoutParams().width=o_width;
		handler.getLayoutParams().height=o_height;
		
		//handler.getLayoutParams().height=handler.getLayoutParams().width;
		slidingDrawer1.setOnDrawerOpenListener(new android.widget.SlidingDrawer.OnDrawerOpenListener() {		 
            public void onDrawerOpened() {// 当抽屉打开时执行此操作  
            	handler.setImageResource(R.drawable.handle_up); 
            	
            }  
        }); 
		
		slidingDrawer1.setOnDrawerCloseListener(new android.widget.SlidingDrawer.OnDrawerCloseListener() { 
            public void onDrawerClosed() {// 抽屉关闭时执行此操作 
            	handler.setImageResource(R.drawable.handle_down);
            } 
        });
		
		
		
		
	}

	public void setHistory(){
		doctorList.clear();
	
		mProgressDialog = new ProgressDialog(BookingMainActivity.this);
		mProgressDialog.setMessage("正在加载数据...");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setCanceledOnTouchOutside(false);// 设置进度条是否可以按退回键取消
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
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
				DoctorInfoReq req=new DoctorInfoReq();
				req.setHospitalId(hospitalId);
				req.setDoctorId(doctorId);
				req.setAucode(GET.Aucode);
				SoapObject  obj = service.getDoctorDetail(req);
				
						
				
				if (obj != null)
				{
					SoapObject areaObject=(SoapObject)obj.getProperty("doctordetail");					
					String doctorId=areaObject.getProperty("doctorId").toString();
					String doctorName=areaObject.getProperty("doctorName").toString();
					String sex=areaObject.getProperty("sex").toString();
					String title=areaObject.getProperty("title").toString();
					String info="";
			

					//SoapObject areaObject = (SoapObject) obj.getProperty("doctorList");
				//	for (int i = 0; i < areaObject.getPropertyCount(); i++)	{

						//SoapObject soapChilds = (SoapObject) areaObject.getProperty(i);
						//String doctorId = soapChilds.getProperty("doctorId").toString();
						//String doctorName = soapChilds.getProperty("doctorName").toString();
						//String sex = soapChilds.getProperty("sex").toString();
						//String title = soapChilds.getProperty("title").toString();
						// String
						// info=soapChilds.getProperty("info").toString();
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

						SoapObject scheduleListObject = (SoapObject) areaObject
								.getProperty("scheduleList");
						List<Schedule> schedulelist = new ArrayList<Schedule>();
						for (int j = 0; j < scheduleListObject.getPropertyCount(); j++)
						{
							SoapObject scheduleObject = (SoapObject) scheduleListObject.getProperty(j);
							Schedule sch = new Schedule();
							if (scheduleObject.getProperty("availableNum") != null)
								sch.setAvailableNum(scheduleObject.getProperty(
										"availableNum").toString());

							if (scheduleObject.getProperty("consultationFee") != null)
								sch.setConsultationFee(scheduleObject
										.getProperty("consultationFee")
										.toString());

							if (scheduleObject.getProperty("dayOfWeek") != null)
								sch.setDayOfWeek(scheduleObject.getProperty(
										"dayOfWeek").toString());

							if (scheduleObject.getProperty("doctorId") != null)
								sch.setDoctorId(scheduleObject.getProperty(
										"doctorId").toString());

							if (scheduleObject.getProperty("doctorName") != null)
								sch.setDoctorName(scheduleObject.getProperty(
										"doctorName").toString());

							if (scheduleObject.getProperty("isSuspend") != null)
								sch.setIsSuspend(scheduleObject.getProperty(
										"isSuspend").toString());

							if (scheduleObject.getProperty("outcallDate") != null)
								sch.setOutcallDate(scheduleObject.getProperty(
										"outcallDate").toString());

							if (scheduleObject.getProperty("regId") != null)
								sch.setRegId(scheduleObject
										.getProperty("regId").toString());

							if (scheduleObject.getProperty("regName") != null)
								sch.setRegName(scheduleObject.getProperty(
										"regName").toString());

							if (scheduleObject.getProperty("scheduleID") != null)
								sch.setScheduleID(scheduleObject.getProperty(
										"scheduleID").toString());

							if (scheduleObject.getProperty("schState") != null)
								sch.setSchState(scheduleObject.getProperty(
										"schState").toString());

							if (scheduleObject.getProperty("timeRange") != null)
								sch.setTimeRange(scheduleObject.getProperty(
										"timeRange").toString());
							schedulelist.add(sch);
						}

						DoctorList map = new DoctorList();
						map.setId(doctorId);
						map.setText(doctorName);
						map.setImageUrl(imageUrl);
						// 获取未知性别医生有错，返回值为"anyType{}"，下面判定为暂时解决办法，希望服务器端能解决
						if (sex.equals("anyType{}"))
						{
							sex = "";
						}

						map.setLevel("(" + sex + ") " + title);

						map.setVersion(version);
						map.setScheduleList(schedulelist);
						doctorList.add(map);
					//}
					String resultCode = obj.getProperty("resultCode")
							.toString();// 0000成功1111报错
					String msg = obj.getProperty("msg").toString();// 返回的信息

					Log.e("error1", resultCode);
					Log.e("error2", msg);

					return true;
				} else
					return false;

			}

			@Override
			protected void onPostExecute(Boolean result)
			{
				super.onPostExecute(result);
				showInList();
			}

			@Override
			protected void onCancelled()
			{
				super.onCancelled();

			}

			public void showInList()
			{
				if (mProgressDialog.isShowing())
				{
					mProgressDialog.dismiss();
				}

				DoctorListAdapter adapter = new DoctorListAdapter(
						BookingMainActivity.this, doctorList);

				doctorlistView.setAdapter(adapter);
				doctorlistView.setClickable(true);
				doctorlistView.setFocusable(true);
				doctorlistView.setOnItemClickListener(new OnItemClickListener()
				{
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3)
					{
						DoctorList map = (DoctorList) doctorlistView
								.getItemAtPosition(arg2);

						String doctorId = map.getId(); // 获得Areaid
						String doctorName = map.getText(); // 获得AreaName
						String version = map.getVersion();

						// 记录医生信息要清空
						editor.putString("doctorId", doctorId);
						editor.putString("doctorName", doctorName);

						editor.commit();
						// startActivity(new Intent (DoctorListActivity.this,
						// ScheduleListActivity.class));

					}
				});
				doctorlistView.getLayoutParams().height+=300;
				 
			}
		}.execute();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		getDataFromSP();
		if(!doctorId.equals("NG")){
			setHistory();
		}
	}
	//搜索功能
	OnClickListener search_btn_click=new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			String serachdoctorname = selecttext.getText().toString();
			
			editor.putString("serach_doc", serachdoctorname);
			
			editor.commit();
			
			Intent intent = new Intent(BookingMainActivity.this,DoctorListActivity.class);
			
			intent.setFlags(0);
			
			intent.putExtra("who", "serach");
			
			startActivity(intent);
		}
	};
	//Booking
	OnClickListener booking_btn_click=new OnClickListener(){

		@Override
		public void onClick(View v) {
			if (doctorId.equals("NG")) {
				DialogShow.showDialog(BookingMainActivity.this, "请先选择医生");
			} else {
				// 启动预约
				editor.putString("now_state", "booking");
				editor.commit();
				startActivity(new Intent(BookingMainActivity.this,
						ScheduleListActivity.class));
				
			}
			
		}
		
	};
	

	// 选择医院动作
	OnClickListener select_hospital_click = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (areaId.equals("NG")) {
				DialogShow.showDialog(BookingMainActivity.this, "请先选择地区");
			} else {
				// 启动选择医院
				startActivity(new Intent(BookingMainActivity.this,
						HospitalListActivity.class));
			}
		}
	};

	// 选择科室动作
		OnClickListener select_department_click = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (hospitalId.equals("NG")) {
					DialogShow.showDialog(BookingMainActivity.this, "请先选择医院");
				} else {
					// 启动选择医院
					startActivity(new Intent(BookingMainActivity.this,
							DepartmentListActivity.class));
				}
			}
		};
	
		// 选择医生动作
				OnClickListener select_doctor_click = new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (departmentId.equals("NG")) {
							DialogShow.showDialog(BookingMainActivity.this, "请先选择科室");
						} else {
							// 启动选择医生
							Intent intent = new Intent(BookingMainActivity.this,
									DoctorListActivity.class);
							
							intent.putExtra("who", "");
							startActivity(intent);
						}
					}
				};
		
	
	// 选择地区动作
	OnClickListener select_area_click = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(BookingMainActivity.this,
					AreaListActivity.class));
		}
	};

	// 获取记录的地区、医院、科室、医生信息（ID、Name）
	private void getDataFromSP() {
		areaId = sp.getString("areaId", "NG");
		areaName = sp.getString("areaName", "地区");
		select_area_btn.setText(areaName);

		hospitalId = sp.getString("hospitalId", "NG");
		hospitalName = sp.getString("hospitalName", "请选择医院");
		hospital.setText(hospitalName);

		departmentId = sp.getString("departmentId", "NG");
		departmentName = sp.getString("departmentName", "请选择科室");
		department.setText(departmentName);

		doctorId = sp.getString("doctorId", "NG");
		doctorName = sp.getString("doctorName", "请选择医生");
		doctor.setText(doctorName);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.booking_main, menu);
		return true;
	}

	// 获得屏幕大小
	public void getScreenSize() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels;
		height = metric.heightPixels;
		
		width=DensityUtil.px2dip(this, width);
		height=DensityUtil.px2dip(this, height);
		editor.putInt("screen_width", width);
		editor.putInt("screen_height", height);
		editor.commit();
	}

	// 设置Dialog字体大小
	private void setDialogFontSize(Dialog dialog, int size) {
		Window window = dialog.getWindow();
		View view = window.getDecorView();
		setViewFontSize(view, size);
	}

	private void setViewFontSize(View view, int size) {
		if (view instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) view;
			int count = parent.getChildCount();
			for (int i = 0; i < count; i++) {
				setViewFontSize(parent.getChildAt(i), size);
			}
		} else if (view instanceof TextView) {
			TextView textview = (TextView) view;
			textview.setTextSize(size);
		}
	}

	// 设置搜索医生或医院
	private class SpinnerAdapter extends ArrayAdapter<String> {
		Context context;
		String[] items = new String[] {};

		public SpinnerAdapter(final Context context,
				final int textViewResourceId, final String[] objects) {
			super(context, textViewResourceId, objects);
			this.items = objects;
			this.context = context;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {

			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setTextSize(width / fontsizex * 2);
			return convertView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			// android.R.id.text1 is default text view in resource of the
			// android.
			// android.R.layout.simple_spinner_item is default layout in
			// resources of android.

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setTextSize(width / fontsizex);
			return convertView;
		}
	}
	
	@Override  
	protected void onDestroy() {  
	    super.onDestroy();  
	    if (mgr  != null) {  
	    	mgr.closeDB();  
	    }  
	}  

}
