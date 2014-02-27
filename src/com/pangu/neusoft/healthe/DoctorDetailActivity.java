package com.pangu.neusoft.healthe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.adapters.DoctorList;
import com.pangu.neusoft.adapters.HospitalList;
import com.pangu.neusoft.adapters.HospitalListAdapter;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.ConnectDoctor;
import com.pangu.neusoft.core.models.Coordinates;
import com.pangu.neusoft.core.models.Doctor;
import com.pangu.neusoft.core.models.DoctorInfoReq;
import com.pangu.neusoft.core.models.Hospital;
import com.pangu.neusoft.core.models.HospitalInfoReq;
import com.pangu.neusoft.core.models.HospitalReq;
import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.drawable;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
import com.pangu.neusoft.tools.AsyncBitmapLoader;
import com.pangu.neusoft.tools.DialogShow;
import com.pangu.neusoft.tools.SortListByItem;
import com.pangu.neusoft.tools.AsyncBitmapLoader.ImageCallBack;

import android.net.Uri;
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
import android.graphics.Bitmap;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class DoctorDetailActivity extends FatherActivity
{
	private String doctorId;
	private String hosiptalId;
	private String doctorVersion;
	private WebService service;
	private ProgressDialog mProgressDialog;
	Doctor doctor = new Doctor();
	SharedPreferences sp;
	Editor editor;

	private ImageView pic;
	private TextView doctorIdText;
	private TextView doctorNameText;
	private TextView doctorlevelText;
	private TextView doctorInfoText;
	private LinearLayout scheduleTableLayout;
	private TextView doctor_detail_department_grade,
			doctor_detail_hospital_grade, doctor_detail_grade;
	private AsyncBitmapLoader asyncImageLoader;

	private LinearLayout schedule_list_layout;
	private LinearLayout scheduledetail;
	private TableLayout scheduledays;
	// private Button booking_btn;
	private Button connect;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		service = new WebService();
		setContentView(R.layout.detail_doctor);
		super.setactivitytitle("医生信息");
		doctorId = this.getIntent().getStringExtra("doctorId");
		doctorVersion = this.getIntent().getStringExtra("doctorVersion");

		doctorIdText = (TextView) findViewById(R.id.doctor_detail_id);
		doctorNameText = (TextView) findViewById(R.id.doctor_detail_name);
		doctorlevelText = (TextView) findViewById(R.id.doctor_detail_level_grade);
		doctorInfoText = (TextView) findViewById(R.id.doctor_detail_intro);
		scheduleTableLayout = (LinearLayout) findViewById(R.id.doctor_detail_schedule_table);

		schedule_list_layout = (LinearLayout) findViewById(R.id.schedule_list_layout);
		scheduledetail = (LinearLayout) findViewById(R.id.scheduledetail);
		doctor_detail_grade = (TextView) findViewById(R.id.doctor_detail_grade);

		scheduledays = (TableLayout) findViewById(R.id.scheduledays);

		doctor_detail_department_grade = (TextView) findViewById(R.id.doctor_detail_department_grade);
		doctor_detail_hospital_grade = (TextView) findViewById(R.id.doctor_detail_hospital_grade);
		doctor_detail_hospital_grade.setText(sp.getString("hospitalName", ""));
		doctor_detail_department_grade.setText(sp.getString("departmentName",
				""));

		pic = (ImageView) findViewById(R.id.doctor_detail_pictureurl);
		asyncImageLoader = new AsyncBitmapLoader();

		hosiptalId = sp.getString("hospitalId", "NG");

		// booking_btn = (Button)findViewById(R.id.doctor_detail_booking_btn);
		connect = (Button) findViewById(R.id.connect);
		connect.setOnClickListener(connect_click);



	}
	
	
	
	public void checkConnected(){
		
		
		ConnectDoctor conndoctor = new ConnectDoctor();
		conndoctor.setHospitalid(sp.getString("hospitalId", ""));
		conndoctor.setHospitalname(sp.getString("hospitalName", ""));
		conndoctor.setDepartmentid(sp.getString("departmentId", ""));
		conndoctor.setDepartmentname(sp.getString("departmentName", ""));
		conndoctor.setDoctorid(doctor.getDoctorId());
		conndoctor.setDoctorname(doctor.getDoctorName());
		conndoctor.setImageUrl(doctor.getPictureUrl());
		conndoctor.setLevel(doctor.getTitle());
		DBManager mgr = new DBManager(DoctorDetailActivity.this);
		if (mgr.connected(conndoctor,sp.getString("username", ""))){
			connect.setText("已收藏");
		}
		else{
			connect.setText("收藏");		
		}
		mgr.closeDB();
	}
	
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
		//schedule_list_layout.removeAllViews();
		//scheduledetail.removeAllViews() ;
		scheduledays.removeAllViews();
		
		getDataFromInternet();
		
	}
	public void getDataFromInternet()
	{
		
		mProgressDialog = new ProgressDialog(DoctorDetailActivity.this);
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
				DoctorInfoReq req = new DoctorInfoReq();

				req.setHospitalId(hosiptalId);
				req.setDoctorId(doctorId);
				req.setAucode(GET.Aucode);
				SoapObject obj = service.getDoctorDetail(req);

				if (obj != null)
				{
					SoapObject areaObject = (SoapObject) obj
							.getProperty("doctordetail");
					String doctorId = areaObject.getProperty("doctorId")
							.toString();
					String doctorName = areaObject.getProperty("doctorName")
							.toString();
					String sex = areaObject.getProperty("sex").toString();
					String title = areaObject.getProperty("title").toString();

					String info = "";
					try
					{
						info = areaObject.getProperty("info").toString();
					} catch (Exception ex)
					{

					}

					SoapObject coordinatesObj = (SoapObject) areaObject
							.getProperty("scheduleList");
					List<Schedule> scheduleList = new ArrayList<Schedule>();
					for (int i = 0; i < coordinatesObj.getPropertyCount(); i++)
					{
						SoapObject soapChilds = (SoapObject) coordinatesObj
								.getProperty(i);
						String schState = soapChilds.getProperty("schState")
								.toString();
						String doctorId_s = soapChilds.getProperty("doctorId")
								.toString();
						String doctorName_s = soapChilds.getProperty(
								"doctorName").toString();
						String regId = soapChilds.getProperty("regId")
								.toString();
						String regName = soapChilds.getProperty("regName")
								.toString();
						String isSuspend = soapChilds.getProperty("isSuspend")
								.toString();
						String outcallDate = soapChilds.getProperty(
								"outcallDate").toString();
						String dayOfWeek = soapChilds.getProperty("dayOfWeek")
								.toString();
						String timeRange = soapChilds.getProperty("timeRange")
								.toString();
						String availableNum = soapChilds.getProperty(
								"availableNum").toString();
						String consultationFee = soapChilds.getProperty(
								"consultationFee").toString();
						String scheduleID = soapChilds
								.getProperty("scheduleID").toString();
						Schedule s = new Schedule();
						s.setAvailableNum(availableNum);
						s.setConsultationFee(consultationFee);
						s.setDayOfWeek(dayOfWeek);
						s.setDoctorId(doctorId_s);
						s.setDoctorName(doctorName_s);
						s.setIsSuspend(isSuspend);
						s.setOutcallDate(outcallDate);
						s.setRegId(regId);
						s.setRegName(regName);
						s.setScheduleID(scheduleID);
						s.setSchState(schState);
						s.setTimeRange(timeRange);
						scheduleList.add(s);
					}
					int version = 0;
					try
					{
						version = Integer.parseInt(areaObject.getProperty(
								"version").toString());
					} catch (Exception ex)
					{

					}
					String imageUrl = "";
					try
					{
						if (areaObject.getProperty("pictureUrl") != null)
						{
							imageUrl = areaObject.getProperty("pictureUrl")
									.toString();
						}
					} catch (Exception ex)
					{
						pic.setBackgroundResource(R.drawable.booking_doc);
					}

					doctor.setDoctorId(doctorId);
					doctor.setDoctorName(doctorName);
					doctor.setInfo(info);
					doctor.setScheduleList(scheduleList);
					doctor.setPictureUrl(imageUrl);
					doctor.setSex(sex);
					doctor.setTitle(title);
					doctor.setVersion(version);

					String resultCode = obj.getProperty("resultCode")
							.toString();// 0000成功1111报错
					String msg = obj.getProperty("msg").toString();// 返回的信息
					return true;
				} else
					return false;
			}

			@Override
			protected void onPostExecute(Boolean result)
			{
				super.onPostExecute(result);
				if (mProgressDialog.isShowing())
				{
					mProgressDialog.dismiss();
				}
				if (result)
				{
					doctorIdText.setText(doctor.getDoctorId());
					doctorNameText.setText(doctor.getDoctorName());
					doctorlevelText.setText("(" + doctor.getSex() + ")  "
							+ doctor.getTitle());
					doctorInfoText.setText(doctor.getInfo());
					doctorInfoText.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (doctor.getInfo()!="") {
								Toast.makeText(getApplicationContext(), doctor.getInfo(), Toast.LENGTH_LONG).show();
							}
							
						}
					});
					// pic.setText(doctor.getHospitalId());
					// GetSchedule sch=new GetSchedule();
					// View
					// scheduleView=sch.getScheduleView(DoctorDetailActivity.this,
					// doctor.getScheduleList());
					// scheduleTableLayout.addView(scheduleView);
					Log.e("pic", doctor.getPictureUrl());
					Bitmap bitmap = asyncImageLoader.loadBitmap(pic,
							doctor.getPictureUrl(), new ImageCallBack()
							{

								@Override
								public void imageLoad(ImageView imageView,
										Bitmap bitmap)
								{
									// TODO Auto-generated method stub
									imageView.setImageBitmap(bitmap);

								}
							});
					if (bitmap == null)
					{
						if (doctor.getSex() != null
								&& doctor.getSex().equals("男"))
						{
							pic.setImageResource(R.drawable.doc_man_img);
						} else if (doctor.getSex() != null
								&& doctor.getSex().equals("女"))
						{
							pic.setImageResource(R.drawable.doc_women_img);
						} else
						{
							pic.setImageResource(R.drawable.doc_def_img);
						}

					} else
					{
						pic.setImageBitmap(bitmap);
					}
					SchedultLayout schedule = new SchedultLayout(
							DoctorDetailActivity.this,
							doctor.getScheduleList(), scheduledetail,
							scheduledays, doctor_detail_grade);
					
					schedule.getView();
					checkConnected();
				}
			}

			@Override
			protected void onCancelled()
			{
				super.onCancelled();
			}

		}.execute();
	}

	OnClickListener connect_click = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			ConnectDoctor conndoctor = new ConnectDoctor();
			conndoctor.setHospitalid(sp.getString("hospitalId", ""));
			conndoctor.setHospitalname(sp.getString("hospitalName", ""));
			conndoctor.setDepartmentid(sp.getString("departmentId", ""));
			conndoctor.setDepartmentname(sp.getString("departmentName", ""));
			conndoctor.setDoctorid(doctor.getDoctorId());
			conndoctor.setDoctorname(doctor.getDoctorName());
			conndoctor.setImageUrl(doctor.getPictureUrl());
			conndoctor.setLevel(doctor.getTitle());
			DBManager mgr = new DBManager(DoctorDetailActivity.this);
			if (mgr.addDoctor(conndoctor,sp.getString("username", ""))){
				connect.setText("已收藏");
				DialogShow.showDialog(DoctorDetailActivity.this, "收藏成功！");
			}
			else{
				connect.setText("收藏");
				DialogShow.showDialog(DoctorDetailActivity.this, "取消收藏！");
			}
			mgr.closeDB();
		}

	};

	

}