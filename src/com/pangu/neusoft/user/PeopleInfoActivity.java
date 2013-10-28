package com.pangu.neusoft.user;

import com.pangu.neusoft.healthe.FatherActivity;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.adapters.HospitalList;
import com.pangu.neusoft.adapters.HospitalListAdapter;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.AddMemberMedicalCard;
import com.pangu.neusoft.core.models.BookingInfos;
import com.pangu.neusoft.core.models.BookingReq;
import com.pangu.neusoft.core.models.DeleteCardReq;
import com.pangu.neusoft.core.models.HospitalReq;
import com.pangu.neusoft.db.Cards;
import com.pangu.neusoft.db.DBConn;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.db.People;
import com.pangu.neusoft.healthe.AreaListActivity;
import com.pangu.neusoft.healthe.BookingMainActivity;
import com.pangu.neusoft.healthe.FristActivity;
import com.pangu.neusoft.healthe.HospitalListActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.healthe.R.array;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
import com.pangu.neusoft.tools.DialogShow;
import com.pangu.neusoft.tools.Utility;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class PeopleInfoActivity extends FatherActivity {
    private String SerialNumber;
	private String xname;
	private DBManager mgr;
	private Spinner sex;
	private Spinner licence_type;

	private ArrayAdapter selectadapter_sex;
	private ArrayAdapter selectadapter_lic;
	private ProgressDialog mProgressDialog;
	private EditText username_text;
	private EditText phone_text;
	private EditText address_text;
	private EditText licence_text;

	private ListView cardlist;

	private Button create_card_btn;
	private Button create_people_btn;
	private Button back_to_people_list;
	private SharedPreferences sp;
	private Editor editor;
	private Button delete_people_btn;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mgr = new DBManager(PeopleInfoActivity.this);
		setContentView(R.layout.activity_people_info);
		Intent intent = getIntent();
		xname = intent.getStringExtra("username");
		mProgressDialog = new ProgressDialog(PeopleInfoActivity.this);
		mProgressDialog.setMessage("正在加载数据...");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setCanceledOnTouchOutside(false);// 设置进度条是否可以按退回键取消
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();

		sex = (Spinner) findViewById(R.id.people_sex);
		licence_type = (Spinner) findViewById(R.id.people_licence);

		selectadapter_sex = ArrayAdapter.createFromResource(this, R.array.sex,
				android.R.layout.simple_spinner_item);
		// 设置下拉列表的风格
		selectadapter_sex
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		sex.setAdapter(selectadapter_sex);

		selectadapter_lic = ArrayAdapter.createFromResource(this,
				R.array.licences_type, android.R.layout.simple_spinner_item);
		// 设置下拉列表的风格
		selectadapter_lic
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		licence_type.setAdapter(selectadapter_lic);

		username_text = (EditText) findViewById(R.id.people_name);
		phone_text = (EditText) findViewById(R.id.people_phone);
		address_text = (EditText) findViewById(R.id.people_address);
		licence_text = (EditText) findViewById(R.id.people_licence_num);
		back_to_people_list = (Button) findViewById(R.id.back_people_select);
		back_to_people_list.setOnClickListener(back);
		create_people_btn = (Button) findViewById(R.id.create_people_btn);
		create_people_btn.setOnClickListener(save);

		delete_people_btn=(Button)findViewById(R.id.delete_people_btn);
		delete_people_btn.setOnClickListener(deletePeople);
		create_card_btn = (Button) findViewById(R.id.create_card_btn);
		create_card_btn.setOnClickListener(create_card_click);
		cardlist = (ListView) findViewById(R.id.card_list);
		cardlist.setOnItemClickListener(booking);
		username_text.clearFocus();

		back_to_people_list.setFocusable(true);
		back_to_people_list.setFocusableInTouchMode(true);
		back_to_people_list.requestFocus();

		setOldData();
		showCardsInfo();

	}

	OnClickListener deletePeople=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			confirmDeletePeople();
		}
		
	};
	
	protected void confirmDeletePeople() {
		
		AlertDialog.Builder builder = new Builder(PeopleInfoActivity.this);
		builder.setMessage("确认要删除就诊人吗？");

		builder.setTitle("提示");

		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();						
						People person = new People();
						person.setUsername(username_text.getText().toString());
						mgr.deleteOldPerson(person);
						mgr.closeDB();
						startActivity(new Intent(PeopleInfoActivity.this,ListPeopleActivity.class));
					}
				});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}
	
	
	OnItemClickListener booking = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			final String card = cardlist.getItemAtPosition(arg2).toString();
			String[] arg = card.split("\n");
			if(arg.length==2){
				final String cardnum = arg[1];
				final String cardtype = arg[0];
	
				showCardDialog(arg);
			}
		}

	};

	
	public void showCardDialog(String[] args) {
		final String[] arg = args;
		Dialog dialog = new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.btn_star)
				.setTitle("提示")
				.setMessage("诊疗卡？")
				.setPositiveButton("预约",
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								if (sp.getString("ScheduleID", "").equals("")) {
									// 查看***********************
									startActivity(new Intent(PeopleInfoActivity.this,
											BookingMainActivity.class));

								} else {

									// 预约操作
									booking_action(arg);

								}
							}
						})
				.setNegativeButton("删除诊疗卡",
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								confirmDeleteDialog(arg);
								
							}
						})
				.setNeutralButton("取消",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						}).create();

		dialog.show();
	}
	
	
	

	protected void confirmDeleteDialog(String args[]) {
		final String[] arg = args;
		AlertDialog.Builder builder = new Builder(PeopleInfoActivity.this);
		builder.setMessage("确认要删除诊疗卡吗？");

		builder.setTitle("提示");

		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();						
						deleteCard(arg[1],arg[0]);						
					}
				});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}

	//发送删除诊疗卡
	
	public void deleteCard(String medicalid,String cardtype){
		final String mediaid=medicalid;
		final String cardtypes=cardtype;
		new AsyncTask<Void, Void, Boolean>(){
			
			String msg="删除诊疗卡失败";
			String IsAddSuccess="false";
			String MedicalCardID="";
		    @SuppressWarnings("deprecation")
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute();   
	            mProgressDialog.show();
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				WebService service=new WebService();
				DeleteCardReq req=new DeleteCardReq();
				req.setAucode(GET.Aucode);
				req.setMedicalCardID(mediaid);
				
				String username=sp.getString("username", ""); 
				req.setUserName(username);
				
				 SoapObject obj= service.deleteMemberCard(req);
				
				if(obj!=null){
					IsAddSuccess=obj.getProperty("IsRemoveSuccess").toString();//0000成功1111报错
					msg=obj.getProperty("msg").toString();//0000成功1111报错
					
					if(IsAddSuccess.equals("true")){
						msg="删除诊疗卡成功";
						return true;
					}else{
						return false;
					}
				}
				else{
					msg="删除诊疗卡失败";
					return false;
				}
			}
			@SuppressWarnings("deprecation")
			@Override
			protected void onPostExecute(Boolean result){
				super.onPostExecute(result);
				
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
				}
				
				if(result){
					/*
					 * save to db
					 * */
					Cards card=new Cards();
					card.setCardtype(cardtypes);
					card.setCardnum(mediaid);
					card.setUsername(xname);
					mgr.deleteCard(card);
					mgr.closeDB();
					Intent intent=getIntent();
					intent.setClass(PeopleInfoActivity.this, PeopleInfoActivity.class);
					intent.putExtra("username",xname);
					startActivity(intent);
					
				}else{
					DialogShow.showDialog(PeopleInfoActivity.this, msg);
				}
										
			}
			@SuppressWarnings("deprecation")
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
				}
				DialogShow.showDialog(PeopleInfoActivity.this, msg);
			}	
	}.execute();
	}
	
	
	public void booking_action(String[] arg) {
		final String cardnum = arg[1];
		final String cardtype = arg[0];
		new AsyncTask<Void, Void, Boolean>() {
			String booking_msg = "";
			BookingInfos bookingdata;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mProgressDialog.show();
			}

			@Override
			protected Boolean doInBackground(Void... params) {
				WebService service = new WebService();
				// 发送预定
				BookingReq req = new BookingReq();
				req.setAucode(GET.Aucode);
				req.setBookingWayID("3");
				req.setHospitalId(sp.getString("hospitalId", ""));
				req.setDepartmentId(sp.getString("departmentId", ""));
				req.setDoctorId(sp.getString("doctorId", ""));
				req.setMemberId(sp.getString("username", ""));
				req.setScheduleID(sp.getString("ScheduleID", ""));
				req.setScheduleID("");
				req.setSchState(sp.getString("SchState", ""));
				req.setPhoneNumber(phone_text.getText().toString());
				req.setRegId(sp.getString("RegId", ""));
				req.setReserveDate(sp.getString("ReserveDate", ""));
				req.setReserveTime(sp.getString("ReserveTime", ""));
				req.setIdType(licence_type.getSelectedItem().toString());
				// req.setIdType("");
				req.setIdCode(licence_text.getText().toString());
				req.setCardNum(cardnum);
				req.setPatientName(xname);
				if (cardtype.equals("佛山健康卡"))
					req.setMedicalCardTypeID("1");
				else
					req.setMedicalCardTypeID("2");
				/*
				 * req.setAucode(GET.Aucode); req.setBookingWayID("3");
				 * req.setHospitalId("1114"); req.setDepartmentId("200000370");
				 * req.setDoctorId("1920"); req.setMemberId("1");
				 * req.setScheduleID(""); req.setSchState("0");
				 * req.setPhoneNumber("18927268160"); req.setRegId("400000758");
				 * req.setReserveDate("2013-10-22");
				 * req.setReserveTime("11:00:00-11:30:00"); req.setIdType("");
				 * req.setIdCode("440682198610172113");
				 * req.setCardNum("4406000006000174421");
				 * req.setPatientName("黎泳仪");
				 */

				SoapObject obj = service.booking(req);

				if (obj != null) {
					String isSuccessful=obj.getProperty("isSuccessful").toString();

					Log.e("e2:", obj.getProperty("Message").toString());
					
					if(isSuccessful.equals("true")){
						SerialNumber = obj.getProperty("SerialNumber").toString();
						
					}
					String recodeid = obj.getProperty(
							"BookingRecordID_CancleID").toString();

					if (obj.getProperty("isSuccessful").toString()
							.equals("true")) {
						// String
						// SerialNumber=obj.getProperty("SerialNumber").toString();//0000成功1111报错
						// String
						// QueueIndex=obj.getProperty("QueueIndex").toString();//返回的信息
						// DialogShow.showDialog(PeopleInfoActivity.this,
						// "预定成功");
						editor.putString("ScheduleID", "");
						editor.commit();
						// Log.e("error1", SerialNumber);
						// Log.e("error2", QueueIndex);
						Log.e("error3", recodeid);

						DBManager mng = new DBManager(PeopleInfoActivity.this);
						bookingdata = new BookingInfos();
						// 设置对象
						bookingdata.setCancelid(recodeid);
						bookingdata.setCardnumber(req.getCardNum());
						bookingdata.setDepartmentid(req.getDepartmentId());
						bookingdata.setDepartmentname(sp.getString(
								"departmentName", ""));
						bookingdata.setDoctorid(req.getDoctorId());
						bookingdata.setDoctorname(sp
								.getString("doctorName", ""));
						bookingdata.setHospitalid(req.getHospitalId());
						bookingdata.setHospitalname(sp.getString("hospitalName", ""));
						bookingdata.setIdcode(req.getIdCode());
						bookingdata.setIdtype(req.getIdType());
						bookingdata.setMemberid(req.getMemberId());
						bookingdata.setPhonenumber(req.getPhoneNumber());
						bookingdata.setRegid(req.getRegId());
						bookingdata.setReservedate(req.getReserveDate());
						bookingdata.setReservetime(req.getReserveTime());
						bookingdata.setScheduleid(req.getScheduleID());
						bookingdata.setSchstate(req.getSchState());
						bookingdata.setUsername(req.getPatientName());
						bookingdata.setSerialNumber(obj.getProperty("SerialNumber").toString());
						mng.addBookingRecord(bookingdata);
						booking_msg = "预约成功";
						mng.closeDB();
						return true;
					} else {
						booking_msg = obj.getProperty("Message").toString();
						return false;
					}
				} else {
					booking_msg = "预约失败";
					return false;
				}
			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
				if (booking_msg.equals("预约成功")) {
					if (bookingdata != null) {
						dialogBooking();
					} else {
						DialogShow.showDialog(PeopleInfoActivity.this,
								booking_msg);
					}
				} else {
					DialogShow.showDialog(PeopleInfoActivity.this, booking_msg);
				}

			}

			@Override
			protected void onCancelled() {
				super.onCancelled();

			}

			protected void dialogBooking() {
				
				AlertDialog.Builder builder = new Builder(
						PeopleInfoActivity.this);
				builder.setMessage("预约成功:\n"
						+"就诊人："+xname+"\n"
						+"预约医院："+ bookingdata.getHospitalname()+ "\n" 
						+"预约科室："+ bookingdata.getDepartmentname() + "\n"
						+"预约医生："+ bookingdata.getDoctorname() + "\n"
						+"预约日期："+ bookingdata.getReservedate() + "\n"
						+"预约时间："+ bookingdata.getReservetime()+ "\n"
						+ "流水号："+SerialNumber+"\n"
						+"取号地点：现场挂号处"+"\n"
						+"支付方式：现场支付"
						);
				builder.setTitle("提示");
				builder.setPositiveButton("确认",
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});

				builder.create().show();
			}

		}.execute();
	}

	public void showCardsInfo() {
		try {
			List<String> arr = new ArrayList<String>();

			List<Cards> cards = mgr.queryCardByName(xname);
			for (Cards card : cards) {
				arr.add(card.getHospitalname() + "\n" + card.getCardnum());
			}

			if (arr.size() == 0) {
				arr.add("请先添加诊疗卡");
				ListAdapter adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_expandable_list_item_1, arr);
				cardlist.setAdapter(adapter);
				create_card_btn.setVisibility(View.VISIBLE);
			} else {
				ListAdapter adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_expandable_list_item_1, arr);
				cardlist.setAdapter(adapter);
				// cardlist.setOnItemClickListener();
				create_card_btn.setVisibility(View.INVISIBLE);
			}
			Utility.setListViewHeightBasedOnChildren(cardlist);
			mgr.closeDB();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setOldData() {
		username_text.setText(xname);
		username_text.setEnabled(false);

		try {
			People person = mgr.queryByName(xname);

			phone_text.setText(person.getPhone());
			address_text.setText(person.getAddress());
			licence_text.setText(person.getLicence_num());
			sex.setSelection(((ArrayAdapter) sex.getAdapter())
					.getPosition(person.getSex()));
			licence_type
					.setSelection(((ArrayAdapter) licence_type.getAdapter())
							.getPosition(person.getLicence_type()));
			mgr.closeDB();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	OnClickListener save = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			People person = new People();
			person.setUsername(xname);
			person.setPhone(phone_text.getText().toString());
			person.setAddress(address_text.getText().toString());
			person.setSex(sex.getSelectedItem().toString());
			person.setLicence_type(licence_type.getSelectedItem().toString());
			person.setLicence_num(licence_text.getText().toString());
			String checkresult = checkData();
			if (checkresult.equals("")) {
				mgr.update(person);
				DialogShow.showDialog(PeopleInfoActivity.this, "更新成功");
				mgr.closeDB();
			} else {
				DialogShow.showDialog(PeopleInfoActivity.this, checkresult);
			}
		}

	};

	public String checkData() {
		String res = "";
		if (xname.equals("")) {
			res += "姓名不能为空\n";
		}
		if (phone_text.getText().toString().equals("")) {
			res += "电话不能为空\n";
		}
		if (licence_text.getText().toString().equals("")) {
			res += "证件号码不能为空\n";
		}

		return res;
	}

	OnClickListener back = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(PeopleInfoActivity.this,
					ListPeopleActivity.class));
		}
	};

	OnClickListener create_card_click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = getIntent();
			intent.setClass(PeopleInfoActivity.this, CreateCardActivity.class);

			intent.putExtra("Owner", username_text.getText().toString());
			intent.putExtra("OwnerSex", sex.getSelectedItem().toString());
			intent.putExtra("OwnerAge", "0");
			intent.putExtra("OwnerPhone", phone_text.getText().toString());
			intent.putExtra("OwnerTel", phone_text.getText().toString());
			intent.putExtra("OwnerIDCard", licence_text.getText().toString());
			intent.putExtra("OwnerAddress", address_text.getText().toString());
			intent.putExtra("OwnerEmail", "");
			intent.putExtra("xname", xname);

			startActivity(intent);

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.people_info, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mgr != null) {
			mgr.closeDB();
		}
	}
	
	
}
