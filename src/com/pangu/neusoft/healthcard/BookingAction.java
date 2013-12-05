package com.pangu.neusoft.healthcard;

import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.BookingInfos;
import com.pangu.neusoft.core.models.BookingReq;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.healthe.BookingConfirmActivity;
import com.pangu.neusoft.healthe.BookingMainActivity;
import com.pangu.neusoft.healthe.FatherActivity;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.tools.DialogShow;

public class BookingAction {
	private ProgressDialog mProgressDialog;
	private Activity activity;
	//private DBManager mgr;
	private String SerialNumber;//预约流水号
	private SharedPreferences sp;
	private Editor editor;
	
	BookingReq req ;
	
	public BookingAction(Activity activity){
		this.activity=activity;
		mProgressDialog = new ProgressDialog(activity);
		mProgressDialog.setMessage("正在加载数据...");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setCanceledOnTouchOutside(false);// 设置进度条是否可以按退回键取消
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		//mgr = new DBManager(activity);
		sp = activity.getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		
	}
	
	public void setBookingdata(){
		req = new BookingReq();
		req.setAucode(GET.Aucode);
		req.setBookingWayID("3");
		//选择的东西
		req.setHospitalId(sp.getString("hospitalId", ""));
		req.setDepartmentId(sp.getString("departmentId", ""));
		req.setDoctorId(sp.getString("doctorId", ""));
		req.setMemberId(sp.getString("username", ""));
		req.setScheduleID(sp.getString("ScheduleID", ""));
		req.setScheduleID("");
		req.setSchState(sp.getString("SchState", ""));
		req.setPhoneNumber(sp.getString("phonenumber", ""));
		req.setRegId(sp.getString("RegId", ""));
		req.setReserveDate(sp.getString("ReserveDate", ""));
		req.setReserveTime(sp.getString("ReserveTime", ""));
		req.setIdType(sp.getString("idtype", ""));
		// req.setIdType("");
		req.setIdCode(sp.getString("idnumber", ""));
		//健康卡信息
		req.setCardNum(sp.getString("cardnum", ""));
		req.setPatientName(sp.getString("owner", ""));
		req.setMedicalCardTypeID(sp.getString("cardtype", ""));
		
		Setting.bookingdata = new BookingInfos();
		
		Setting.bookingdata.setCardnumber(req.getCardNum());
		Setting.bookingdata.setDepartmentid(req.getDepartmentId());
		Setting.bookingdata.setDepartmentname(sp.getString(
				"departmentName", ""));
		Setting.bookingdata.setDoctorid(req.getDoctorId());
		Setting.bookingdata.setDoctorname(sp
				.getString("doctorName", ""));
		Setting.bookingdata.setHospitalid(req.getHospitalId());
		Setting.bookingdata.setHospitalname(sp.getString("hospitalName", ""));
		Setting.bookingdata.setIdcode(req.getIdCode());
		Setting.bookingdata.setIdtype(req.getIdType());
		Setting.bookingdata.setMemberid(req.getMemberId());
		Setting.bookingdata.setPhonenumber(req.getPhoneNumber());
		Setting.bookingdata.setRegid(req.getRegId());
		Setting.bookingdata.setReservedate(req.getReserveDate());
		Setting.bookingdata.setReservetime(req.getReserveTime());
		Setting.bookingdata.setScheduleid(req.getScheduleID());
		Setting.bookingdata.setSchstate(req.getSchState());
		Setting.bookingdata.setUsername(req.getPatientName());
		
		
	}
	
	public void booking_action() {
		setBookingdata();
		if(sp.getString("cardnum", "").equals("")||sp.getString("owner", "").equals("")){
			activity.startActivity(new Intent(activity,ListCardActivity.class));
			activity.finish();
		}else{
			new AsyncTask<Void, Void, Boolean>() {
				String booking_msg = "";
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					mProgressDialog.show();
				}
	
				@Override
				protected Boolean doInBackground(Void... params) {
					WebService service = new WebService();
					// 发送预定
					
					
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
	
							DBManager mgr = new DBManager(activity);
							
							// 设置对象
							Setting.bookingdata.setCancelid(recodeid);						
							Setting.bookingdata.setSerialNumber(obj.getProperty("SerialNumber").toString());
							mgr.addBookingRecord(Setting.bookingdata);
							booking_msg = "预约成功";
							mgr.closeDB();
							Setting.bookingdata=null;//清除本次预约数据
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
					DialogShow.showDialog(activity, booking_msg);
					
					if (booking_msg.equals("预约成功")) {
						//Toast.makeText(activity, booking_msg, Toast.LENGTH_SHORT);	
						final Timer t = new Timer();
							t.schedule(new TimerTask() {
								public void run() {
									activity.startActivity(new Intent(activity,ShowHistoryActivity.class));
									activity.finish();
									t.cancel(); 
								}
							}, Setting.dialogtimeout+1000);
	
					} else {	
						
						//DialogShow.showDialog(activity, booking_msg);
						final Timer t = new Timer();
						t.schedule(new TimerTask() {
							public void run() {
								Intent intent=new Intent();
								intent.setClass(activity,BookingMainActivity.class);
								intent.putExtra("error_message", "预约失败："+booking_msg);
								activity.startActivity(intent);
								
								activity.finish();
								t.cancel(); 
							}
						}, Setting.dialogtimeout+1000);
					}
	
				}
	
				@Override
				protected void onCancelled() {
					super.onCancelled();
	
				}
	
			}.execute();
		}
	}
	
	
		public void confirmBooking() {
			
		setBookingdata();
			
		activity.startActivity(new Intent(activity,BookingConfirmActivity.class));
		
	}
}
