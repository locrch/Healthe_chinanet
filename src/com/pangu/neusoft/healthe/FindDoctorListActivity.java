package com.pangu.neusoft.healthe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.adapters.DoctorList;
import com.pangu.neusoft.adapters.DoctorListAdapter;
import com.pangu.neusoft.adapters.FindDoctorListAdapter;
import com.pangu.neusoft.adapters.HospitalList;
import com.pangu.neusoft.adapters.HospitalListAdapter;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.Doctor;
import com.pangu.neusoft.core.models.DoctorReq;
import com.pangu.neusoft.core.models.FindDoctorListReq;
import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
import com.pangu.neusoft.tools.AsyncBitmapLoader;
import com.pangu.neusoft.tools.AsyncBitmapLoader.ImageCallBack;

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
import android.widget.Toast;

public class FindDoctorListActivity extends FatherActivity {
	ListView doctorlistView;
	WebService service;
	List<DoctorList> doctorList;
	SharedPreferences sp;
	private ProgressDialog mProgressDialog;
	Editor editor;
	private String who;
	TextView infos_text;

	public final class ViewHolder {
		TextView texta;
	}

	boolean connecting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		setactivitytitle("选择医生");
		doctorlistView = (ListView) findViewById(R.id.list);

		service = new WebService();
		doctorList = new ArrayList<DoctorList>();

		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();

		infos_text = (TextView) findViewById(R.id.infos_text);
		infos_text.setText(sp.getString("hospitalName", "") + "-"
				+ sp.getString("departmentName", ""));

		mProgressDialog = new ProgressDialog(FindDoctorListActivity.this);
		mProgressDialog.setMessage("正在加载数据...");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setCanceledOnTouchOutside(false);// 设置进度条是否可以按退回键取消
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				if(mProgressDialog!=null&&!mProgressDialog.isShowing())
					mProgressDialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... params) {

				Intent intent = getIntent();
				who = intent.getStringExtra("who");

				
					setactivitytitle("搜索医生");
					infos_text.setVisibility(View.GONE);
					FindDoctorListReq req = new FindDoctorListReq();

					req.setDoctorName(sp.getString("serach_doc", ""));

					req.setAucode(GET.Aucode);

					SoapObject obj = service.findDoctorList(req);

					if (obj != null) {
						SoapObject areaObject = (SoapObject) obj
								.getProperty("doctorList");

						for (int i = 0; i < areaObject.getPropertyCount(); i++) {

							SoapObject soapChilds = (SoapObject) areaObject
									.getProperty(i);
							String doctorId = soapChilds
									.getProperty("doctorId").toString();
							String doctorName = soapChilds.getProperty(
									"doctorName").toString();
							String sex = soapChilds.getProperty("sex")
									.toString();
							String title = soapChilds.getProperty("title")
									.toString();
							
							String hospitalId = soapChilds.getProperty(
									"hospitalID").toString();
							String departmentId = soapChilds.getProperty(
									"departmentID").toString();
							String hospitalName = soapChilds.getProperty(
									"hospitalName").toString();
							String departmentName = soapChilds.getProperty(
									"departmentName").toString();

							// String
							// info=soapChilds.getProperty("info").toString();
							String version = soapChilds.getProperty("version")
									.toString();

							String imageUrl;

							try {
								if (soapChilds.getProperty("pictureUrl") == null) {

									imageUrl = Setting.DEFAULT_DOC_url;
								} else {

									imageUrl = soapChilds.getProperty(
											"pictureUrl").toString();
								}

							} catch (Exception ex) {
								imageUrl = Setting.TEST_url;
							}

//							SoapObject scheduleListObject = (SoapObject) soapChilds
//									.getProperty("scheduleList");
//							List<Schedule> schedulelist = new ArrayList<Schedule>();
//							for (int j = 0; j < scheduleListObject
//									.getPropertyCount(); j++) {
//								SoapObject scheduleObject = (SoapObject) scheduleListObject
//										.getProperty(j);
//								Schedule sch = new Schedule();
//								if (scheduleObject.getProperty("availableNum") != null)
//									sch.setAvailableNum(scheduleObject
//											.getProperty("availableNum")
//											.toString());
//
//								if (scheduleObject
//										.getProperty("consultationFee") != null)
//									sch.setConsultationFee(scheduleObject
//											.getProperty("consultationFee")
//											.toString());
//
//								if (scheduleObject.getProperty("dayOfWeek") != null)
//									sch.setDayOfWeek(scheduleObject
//											.getProperty("dayOfWeek")
//											.toString());
//
//								if (scheduleObject.getProperty("doctorId") != null)
//									sch.setDoctorId(scheduleObject.getProperty(
//											"doctorId").toString());
//
//								if (scheduleObject.getProperty("doctorName") != null)
//									sch.setDoctorName(scheduleObject
//											.getProperty("doctorName")
//											.toString());
//
//								if (scheduleObject.getProperty("isSuspend") != null)
//									sch.setIsSuspend(scheduleObject
//											.getProperty("isSuspend")
//											.toString());
//
//								if (scheduleObject.getProperty("outcallDate") != null)
//									sch.setOutcallDate(scheduleObject
//											.getProperty("outcallDate")
//											.toString());
//
//								if (scheduleObject.getProperty("regId") != null)
//									sch.setRegId(scheduleObject.getProperty(
//											"regId").toString());
//
//								if (scheduleObject.getProperty("regName") != null)
//									sch.setRegName(scheduleObject.getProperty(
//											"regName").toString());
//
//								if (scheduleObject.getProperty("scheduleID") != null)
//									sch.setScheduleID(scheduleObject
//											.getProperty("scheduleID")
//											.toString());
//
//								if (scheduleObject.getProperty("schState") != null)
//									sch.setSchState(scheduleObject.getProperty(
//											"schState").toString());
//
//								if (scheduleObject.getProperty("timeRange") != null)
//									sch.setTimeRange(scheduleObject
//											.getProperty("timeRange")
//											.toString());
//								schedulelist.add(sch);
//							}
//
							DoctorList map = new DoctorList();
							map.setId(hospitalId+"|"+departmentId+"|"+doctorId);
							map.setText(doctorName);
							map.setImageUrl(imageUrl);
							map.setDepartmentname(departmentName);
							map.setHospitalname(hospitalName);
							// 获取未知性别医生有错，返回值为"anyType{}"，下面判定为暂时解决办法，希望服务器端能解决
							if (sex.equals("anyType{}")) {
								sex = "";
							}

							map.setLevel("(" + sex + ") " + title);

							map.setVersion(version);
							//map.setScheduleList(schedulelist);
							doctorList.add(map);
						}
						String resultCode = obj.getProperty("resultCode")
								.toString();// 0000成功1111报错
						String msg = obj.getProperty("msg").toString();// 返回的信息

						Log.e("resultCode", resultCode);
						Log.e("msg", msg);
						return true;
					} else{
						return false;
					}
				

			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				// 标记打开我的activity
				// Toast.makeText(getApplicationContext(), who,
				// Toast.LENGTH_SHORT).show();
				if (!result) {
					connecting = false;

				} else {
					connecting = true;
				}
				showInList();
			}

			@Override
			protected void onCancelled() {
				super.onCancelled();
				
			}

			public void showInList() {
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
				if (doctorList.size() > 0) {
					if (doctorList.size() == 1) {
						// 如果医生列表只有一个数据，就自定义设置高度
						doctorlistView.getLayoutParams().height = 1000;
					}

					FindDoctorListAdapter adapter = new FindDoctorListAdapter(
							FindDoctorListActivity.this, doctorList);
					adapter.notifyDataSetChanged();
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

									String doctorId;

									String allid = map.getId();
									String[] ids = allid.split("\\|");
									if (ids.length == 3) {
										editor.putString("hospitalId", ids[0]);
										editor.putString("departmentId", ids[1]);
										editor.commit();
										doctorId = ids[2];
									} else {

										doctorId = map.getId();
									}

									// 获得doctorid
									String doctorName = map.getText(); // 获得AreaName
									String version = map.getVersion();

									// 记录医生信息要清空
									editor.putString("doctorId", doctorId);
									editor.putString("doctorName", doctorName);

									editor.commit();
									// startActivity(new Intent
									// (DoctorListActivity.this,
									// ScheduleListActivity.class));
									Intent intent = new Intent(
											FindDoctorListActivity.this,
											DoctorDetailActivity.class);
									intent.putExtra("doctorId", doctorId);
									intent.putExtra("doctorVersion", version);
									startActivity(intent);

								}
							});
				} else {
					infos_text.setVisibility(View.VISIBLE);
					infos_text.setText("未找到医生");
				}
			}
		}.execute();
	}
	
	@Override  
	protected void onDestroy() {  
	    super.onDestroy();  
	    if (mProgressDialog!=null) {
			mProgressDialog.dismiss();
		}
	   
	}  

}
