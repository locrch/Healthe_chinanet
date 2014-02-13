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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class BookingMainActivity extends FatherActivity {
	private static final String[] select = { "医生", "医院" };

	private EditText selecttext;
	private ImageButton search_btn;
	// private Spinner select_spinner;
	private Button doctor_hospital1;
	private Button doctor_hospital2;
	private Button doctor_hospital3;

	private TextView welcome;
	private Button select_area_btn;

	private ImageView first;
	private EditText hospital;
	private ImageView second;
	private EditText department;
	private ImageView third;
	private EditText doctor;
	private DBManager mgr;
	// private Button booking;
	private int countTimes = 0;
	private SlidingDrawer sd;
	private TextView message;
	private int width;
	private int height;
	private int fontsizex = Setting.fontsizex;

	private SharedPreferences sp;
	private Editor editor;
	List<DoctorList> doctorList;
	static ListView doctorlistView;
	private String areaId;
	private String areaName;
	private String hospitalId;
	private String hospitalName;
	private String departmentId;
	private String departmentName;
	private String doctorId;
	private String doctorName;
	private ImageView booking_knower;
	private SlidingDrawer booking_knower_btn;
	private LinearLayout content;
	private ProgressDialog mProgressDialog;
	private ScrollView booking_main_sv;
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

		// select_spinner = (Spinner) findViewById(R.id.spinner1);
		SpinnerAdapter adapter = new SpinnerAdapter(this,
				android.R.layout.simple_spinner_item, select);

		mgr = new DBManager(BookingMainActivity.this);

		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 搜索部分
		// select_spinner.setAdapter(adapter);

		booking_main_sv = (ScrollView) findViewById(R.id.booking_main_sv);

		selecttext = (EditText) findViewById(R.id.search_text);
		search_btn = (ImageButton) findViewById(R.id.search_btn);
		search_btn.setOnClickListener(search_btn_doctor_click);

		selecttext.clearFocus();

		search_btn.setFocusable(true);
		search_btn.setFocusableInTouchMode(true);
		search_btn.requestFocus();
		doctor_hospital1 = (Button) findViewById(R.id.doctor_hospital1);
		doctor_hospital2 = (Button) findViewById(R.id.doctor_hospital2);

		doctor_hospital3 = (Button) findViewById(R.id.doctor_hospital3);

		doctor_hospital1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (doctor_hospital2.getVisibility() == View.VISIBLE) {
					doctor_hospital2.setVisibility(View.INVISIBLE);
					doctor_hospital3.setVisibility(View.INVISIBLE);
				} else {
					doctor_hospital2.setVisibility(View.VISIBLE);
					doctor_hospital3.setVisibility(View.VISIBLE);
				}
			}
		});
		doctor_hospital2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				doctor_hospital1.setText("医生");
				selecttext.setHint("请输入医生姓名");
				search_btn.setOnClickListener(search_btn_doctor_click);
				doctor_hospital2.setVisibility(View.INVISIBLE);
				doctor_hospital3.setVisibility(View.INVISIBLE);
			}
		});
		doctor_hospital3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				doctor_hospital1.setText("医院");
				selecttext.setHint("请输入医院名称");
				search_btn.setOnClickListener(search_btn_hospital_click);
				doctor_hospital2.setVisibility(View.INVISIBLE);
				doctor_hospital3.setVisibility(View.INVISIBLE);
			}
		});

		// 欢迎您和地区选择部分
		welcome = (TextView) findViewById(R.id.booking_main_welcome_content);
		select_area_btn = (Button) findViewById(R.id.select_area_btn);
		select_area_btn.setOnClickListener(select_area_click);

		// 选择医院、科室、医生部分
		first = (ImageView) findViewById(R.id.cashe_list_image);
		second = (ImageView) findViewById(R.id.imageView2);
		third = (ImageView) findViewById(R.id.imageView3);
		hospital = (EditText) findViewById(R.id.hospital);
		hospital.setOnClickListener(select_hospital_click);

		department = (EditText) findViewById(R.id.department);
		department.setOnClickListener(select_department_click);
		doctor = (EditText) findViewById(R.id.doctor);
		doctor.setOnClickListener(select_doctor_click);

		message = (TextView) findViewById(R.id.messages);
		message.setText("1.累计爽约超过3次，将被列入黑名单，限制挂号2个自然月。" + "\n" + "\n"
				+ "2.每周累计主动取消次数超过3次，将被限制挂号2个自然月。" + "\n" + "\n"
				+ "3.同一健康卡在同一就诊日、同一医院只能预约同一医生1次。" + "\n" + "\n"
				+ "4.同一健康卡在同一就诊日、同一医院只能预约2次。");
		message.setTextColor(R.color.black);
		message.setTextSize(15);
		selecttext.clearFocus();
		welcome.setFocusable(true);
		welcome.setFocusableInTouchMode(true);
		welcome.requestFocus();

		booking_knower_btn = (SlidingDrawer) findViewById(R.id.slidingDrawer1);
		booking_knower = (ImageView) findViewById(R.id.booking_knower_btn);

		booking_knower_btn
				.setOnDrawerOpenListener(new android.widget.SlidingDrawer.OnDrawerOpenListener() {
					public void onDrawerOpened() {// 当抽屉打开时执行此操作
						booking_knower
								.setImageResource(R.drawable.booking_knower_btn);
					}
				});
		booking_knower_btn
				.setOnDrawerCloseListener(new android.widget.SlidingDrawer.OnDrawerCloseListener() {
					public void onDrawerClosed() {// 抽屉关闭时执行此操作
						booking_knower
								.setImageResource(R.drawable.booking_knower_btn);
					}
				});
		content = (LinearLayout) findViewById(R.id.content);

		content.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		setItemSize();
		Intent intent = getIntent();
		String message = intent.getStringExtra("error_message");
		if (message != null && !message.equals("")) {
			SetNotice(message);
		}

	}

	public void setItemSize() {

	}

	public void setHistory() {
		doctorList.clear();

		mProgressDialog = new ProgressDialog(BookingMainActivity.this);
		mProgressDialog.setMessage("正在加载数据...");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setCanceledOnTouchOutside(false);// 设置进度条是否可以按退回键取消
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				if (mProgressDialog != null && !mProgressDialog.isShowing())
					mProgressDialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				DoctorInfoReq req = new DoctorInfoReq();
				req.setHospitalId(hospitalId);
				req.setDoctorId(doctorId);
				req.setAucode(GET.Aucode);
				SoapObject obj = service.getDoctorDetail(req);

				if (obj != null) {
					SoapObject areaObject = (SoapObject) obj
							.getProperty("doctordetail");
					String doctorId = areaObject.getProperty("doctorId")
							.toString();
					String doctorName = areaObject.getProperty("doctorName")
							.toString();
					String sex = areaObject.getProperty("sex").toString();
					String title = areaObject.getProperty("title").toString();
					String info = "";

					// SoapObject areaObject = (SoapObject)
					// obj.getProperty("doctorList");
					// for (int i = 0; i < areaObject.getPropertyCount(); i++) {

					// SoapObject soapChilds = (SoapObject)
					// areaObject.getProperty(i);
					// String doctorId =
					// soapChilds.getProperty("doctorId").toString();
					// String doctorName =
					// soapChilds.getProperty("doctorName").toString();
					// String sex = soapChilds.getProperty("sex").toString();
					// String title =
					// soapChilds.getProperty("title").toString();
					// String
					// info=soapChilds.getProperty("info").toString();
					String version = areaObject.getProperty("version")
							.toString();

					String imageUrl;

					try {
						if (areaObject.getProperty("pictureUrl") == null) {

							imageUrl = Setting.DEFAULT_DOC_url;
						} else {

							imageUrl = areaObject.getProperty("pictureUrl")
									.toString();
						}

					} catch (Exception ex) {
						imageUrl = Setting.TEST_url;
					}

					SoapObject scheduleListObject = (SoapObject) areaObject
							.getProperty("scheduleList");
					List<Schedule> schedulelist = new ArrayList<Schedule>();
					for (int j = 0; j < scheduleListObject.getPropertyCount(); j++) {
						SoapObject scheduleObject = (SoapObject) scheduleListObject
								.getProperty(j);
						Schedule sch = new Schedule();
						if (scheduleObject.getProperty("availableNum") != null)
							sch.setAvailableNum(scheduleObject.getProperty(
									"availableNum").toString());

						if (scheduleObject.getProperty("consultationFee") != null)
							sch.setConsultationFee(scheduleObject.getProperty(
									"consultationFee").toString());

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
							sch.setRegId(scheduleObject.getProperty("regId")
									.toString());

						if (scheduleObject.getProperty("regName") != null)
							sch.setRegName(scheduleObject
									.getProperty("regName").toString());

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
					map.setId(sp.getString("hospitalId", "") + "|"
							+ sp.getString("departmentId", "") + "|" + doctorId);
					map.setText(doctorName);
					map.setImageUrl(imageUrl);
					// 获取未知性别医生有错，返回值为"anyType{}"，下面判定为暂时解决办法，希望服务器端能解决
					if (sex.equals("anyType{}")) {
						sex = "";
					}

					map.setLevel("(" + sex + ") " + title);

					map.setVersion(version);
					map.setScheduleList(schedulelist);
					doctorList.add(map);
					// }
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
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				showInList();

			}

			@Override
			protected void onCancelled() {
				super.onCancelled();
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
			}

			public void showInList() {
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}

				DoctorListAdapter adapter = new DoctorListAdapter(
						BookingMainActivity.this, doctorList);

				doctorlistView.setAdapter(adapter);
				doctorlistView.setClickable(true);
				doctorlistView.setFocusable(true);
				doctorlistView
						.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								DoctorList map = (DoctorList) doctorlistView
										.getItemAtPosition(arg2);

								String doctorId = map.getId(); // 获得Areaid
								String doctorName = map.getText(); // 获得AreaName
								String version = map.getVersion();

								// 记录医生信息要清空
								editor.putString("doctorId", doctorId);
								editor.putString("doctorName", doctorName);

								editor.commit();
								// startActivity(new Intent
								// (DoctorListActivity.this,
								// ScheduleListActivity.class));

							}
						});

				// 设置预约历史记录 医生列表高度
				doctorlistView.getLayoutParams().height = (int) getResources()
						.getDimension(R.dimen.booking_main_scroll_height);
				Setting.bookingmain_scroll_height = Setting.Px2Dp(
						getApplicationContext(), doctorlistView.getHeight());
			}
		}.execute();
	}

	@Override
	public void onStart() {
		super.onStart();
		getDataFromSP();
		if (!doctorId.equals("NG")) {
			setHistory();
		}
		Islogin();
	}

	// 搜索医生功能
	OnClickListener search_btn_doctor_click = new OnClickListener() {
		@SuppressLint("ShowToast")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// 收起键盘
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(BookingMainActivity.this
							.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
			String serachdoctorname = selecttext.getText().toString();
			if (serachdoctorname.equals("")) {
				Toast.makeText(BookingMainActivity.this, "请先输入医生姓名",
						Toast.LENGTH_SHORT).show();
			} else {
				editor.putString("serach_doc", serachdoctorname);
				editor.commit();
				Intent intent = new Intent(BookingMainActivity.this,
						FindDoctorListActivity.class);
				intent.setFlags(0);
				intent.putExtra("who", "serach");
				startActivity(intent);
			}
		}
	};
	// 搜索医院功能
	OnClickListener search_btn_hospital_click = new OnClickListener() {

		@SuppressLint("ShowToast")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// 收起键盘
						((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
								.hideSoftInputFromWindow(BookingMainActivity.this
										.getCurrentFocus().getWindowToken(),
										InputMethodManager.HIDE_NOT_ALWAYS);
						
			String serachhospitalname = selecttext.getText().toString();
			if (serachhospitalname.equals("")) {
				Toast.makeText(BookingMainActivity.this, "请先输入医院姓名",
						Toast.LENGTH_SHORT).show();
			} else {
				editor.putString("serach_hosp", serachhospitalname);
				editor.commit();
				Intent intent = new Intent(BookingMainActivity.this,
						HospitalListActivity.class);
				intent.setFlags(0);
				intent.putExtra("who", "serach");
				startActivity(intent);
			}
		}
	};

	// Booking
	OnClickListener booking_btn_click = new OnClickListener() {

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

	// 获得屏幕大小
	public void getScreenSize() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels;
		height = metric.heightPixels;

		width = DensityUtil.px2dip(this, width);
		height = DensityUtil.px2dip(this, height);
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
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Islogin();

	}

	private void Islogin() {
		// 判断登录状态
		if (sp.getString("username", "").equals("")) {
			welcome.setText("尊敬的用户,您好！");
		} else {

			welcome.setText("尊敬的"
					+ sp.getString("card" + sp.getString("defaultcardno", "")
							+ "_" + "owner", "用户") + ",您好！");

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
		if (mgr != null) {
			mgr.closeDB();
		}
	}

}
