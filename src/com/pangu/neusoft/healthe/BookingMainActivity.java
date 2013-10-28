package com.pangu.neusoft.healthe;

import java.util.Timer;
import java.util.TimerTask;

import com.pangu.neusoft.tools.DensityUtil;
import com.pangu.neusoft.tools.DialogShow;
import com.pangu.neusoft.user.LoginActivity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.TextView;

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
	
	private Button booking;

	private SlidingDrawer sd;
	private TextView message;
	private int width;
	private int height;
	private int fontsizex = Setting.fontsizex;
	
	private SharedPreferences sp;
	private Editor editor;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_booking_main);
		
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();

		getScreenSize();

		select_spinner = (Spinner) findViewById(R.id.spinner1);
		SpinnerAdapter adapter = new SpinnerAdapter(this,
				android.R.layout.simple_spinner_item, select);
		
		
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//搜索部分
		select_spinner.setAdapter(adapter);
		selecttext = (EditText) findViewById(R.id.search_text);
		search_btn = (ImageButton) findViewById(R.id.search_btn);

		select_spinner.getLayoutParams().width = width / 3;
		select_spinner.getLayoutParams().height = height / 8;

		selecttext.getLayoutParams().width=width / 2;
		selecttext.getLayoutParams().height= height / 9;
		selecttext.setTextSize(width / fontsizex);
		selecttext.clearFocus();

		//search_btn.getLayoutParams().width=(height / 10);
		//search_btn.getLayoutParams().height=(width / 10);
		//search_btn.setTextSize(width / fontsizex);

		//欢迎您和地区选择部分
		welcome = (TextView) findViewById(R.id.welcome);
		welcome.getLayoutParams().height = height / 10;
		welcome.setTextSize(width / fontsizex);

		select_area_btn = (Button) findViewById(R.id.select_area_btn);
		select_area_btn.setTextSize(width / fontsizex);
		
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
		hospital.setTextSize(width / fontsizex);
		hospital.getLayoutParams().height= height / 8;
		hospital.setOnClickListener(select_hospital_click);

		department = (EditText) findViewById(R.id.department);
		department.setTextSize(width / fontsizex);
		department.getLayoutParams().height= height / 8;
		department.setOnClickListener(select_department_click);

		doctor = (EditText) findViewById(R.id.doctor);
		doctor.setTextSize(width / fontsizex);
		doctor.getLayoutParams().height= height / 8;
		doctor.setOnClickListener(select_doctor_click);
		
		booking = (Button) findViewById(R.id.booking_confirm_btn);
		booking.setTextSize(width / fontsizex);
		booking.setHeight(height / 10);
		booking.setOnClickListener(booking_btn_click);
		
		message = (TextView) findViewById(R.id.messages);
		message.setText("1.每月爽约次数超过3次，将被限制挂号2个自然月。"+"\n"+"\n"+"2.每周累计主动取消次数超过3次，将被限制挂号2个自然月。"+"\n"+"\n"+"3.同一就诊人在同一就诊日、同一医院、同一医生只能预约1次。"+"\n"+"\n"+"4.同一就诊人在同一就诊日、同一医院只能预约2次。"+"\n"+"\n"+"5.同一就诊人每月预约不能超过6次（医院临时停改诊除外）。");
		message.setTextColor(R.color.honeydew);
		message.setTextSize(width / fontsizex);

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

	@Override
	public void onStart() {
		super.onStart();
		getDataFromSP();

	}
	
	//Booking
	OnClickListener booking_btn_click=new OnClickListener(){

		@Override
		public void onClick(View v) {
			if (doctorId.equals("NG")) {
				DialogShow.showDialog(BookingMainActivity.this, "请先选择医生");
			} else {
				// 启动预约
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
							startActivity(new Intent(BookingMainActivity.this,
									DoctorListActivity.class));
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

}
