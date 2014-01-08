package com.pangu.neusoft.healthe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.models.BookingInfos;
import com.pangu.neusoft.core.models.BookingReq;
import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.core.models.ScheduleButton;
import com.pangu.neusoft.healthcard.BookingAction;
import com.pangu.neusoft.healthcard.ListCardActivity;
import com.pangu.neusoft.healthcard.LoginActivity;
import com.pangu.neusoft.tools.SortListByItem;

public class SchedultLayout {

	public LinearLayout layout;
	private Activity activity;
	private List<Schedule> scheduleList;
	SortListByItem sort;
	
	 LinearLayout scheduleDetailLayout;//from out
	 TableLayout scheduleLayout;//from out
	 TextView scheduleText;
	 
	  List<LinearLayout> alllayout;
	  LayoutInflater inflater ;
	  View nowButton;
	  boolean showing=false;
	  
	  SharedPreferences sp;
	 	Editor editor;
	  
	  public SchedultLayout(Activity activity,List<Schedule> scheduleList,LinearLayout scheduleDetailLayout,TableLayout scheduleLayout,TextView scheduleText){
		  sp = activity.getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
          editor=sp.edit();
		  sort=new SortListByItem();
		  inflater= activity.getLayoutInflater();
		  alllayout=new ArrayList<LinearLayout>();
		  this.activity=activity;
		  this.scheduleList=scheduleList;
		  this.scheduleDetailLayout=scheduleDetailLayout;
		  this.scheduleLayout=scheduleLayout;
		  this.scheduleText=scheduleText;
	  } 
	  
	  
	public void getView(){
		
		
		
		 if(scheduleList!=null&&scheduleList.size()>0){
         	
         	
         	//Map<String,String> maps=new LinkedHashMap<String,String>();
         
         	List<String> daylist=new ArrayList<String>();
         	
	            for(Schedule schedule:scheduleList){
	            	String day=schedule.getOutcallDate();
	            	daylist.add(day);
	            }

	         
	            HashSet h  =   new  HashSet(daylist); 
	            daylist.clear(); 
	            daylist.addAll(h); 
	            daylist=sort.sortScheduleByTime(daylist);
	            
	            int totaldays=daylist.size();
	            
	            
	            int totalrows=totaldays/Setting.cols;
	            if(totalrows%Setting.cols!=0){
	            	totalrows+=1;
	            }
	            if(totalrows==0)
	            	totalrows=1;
	           
	            int count=0;
	            for(int i=0;i<totalrows;i++){
	            	TableRow row = new TableRow(activity);
	            	row.setId(i);  
	            	for(int j=0;j<Setting.cols;j++){
	            		if(count<daylist.size()){	            		
	            			String day=daylist.get(count);
	            			
	    	            	//TextView days=new TextView(getContext());
	            			  
	 		                View days = inflater.inflate(R.layout.schedule_day, null); 
	 		                
	 		                TextView dayview=(TextView) days.findViewById(R.id.schedule_day);
	 		                
	 		                dayview.setText(day);
	    	            	
	    	            	
	    	            	final ScheduleButton newButtonInfo=new ScheduleButton();
	   		                newButtonInfo.setScheduleList(scheduleList);
	   		                newButtonInfo.setDay(day);
	   		                newButtonInfo.setScheduleDetailLayout(scheduleDetailLayout);
	   		                alllayout.add((LinearLayout)days.findViewById(R.id.schedule_day_btn_set));
	   		                
	    	            	//days.setTag(newButtonInfo);
	    	            	days.setOnClickListener(new OnClickListener(){
	 							@SuppressLint("NewApi")
								@Override
	 							public void onClick(View arg0) {
	 								 LinearLayout scheduleDetailLayout=newButtonInfo.getScheduleDetailLayout();
	 								 scheduleDetailLayout.removeAllViews();
	 								 for(LinearLayout layout:alllayout){
	 									layout.setBackgroundDrawable(layout.getResources().getDrawable(R.layout.schedule_btn_style_layout));
	 								 }
	 								 LinearLayout layout=(LinearLayout)arg0.findViewById(R.id.schedule_day_btn_set);
	 								  if(arg0.equals(nowButton)&&showing==true){
	 									 nowButton=arg0;
	 									  showing=false;
	 								  }else{
	 								 nowButton=arg0;
	 								showing=true;
	 								layout.setBackgroundDrawable(layout.getResources().getDrawable(R.layout.schedule_btn_style_layout_click));
	 								// ScheduleButton newButtonInfo=( ScheduleButton)arg0.getTag();
	 								
	 								 List<Schedule> scheduleList=newButtonInfo.getScheduleList();
	 								 String day=newButtonInfo.getDay();
	 								//显示当日可排班内容
	 								 
	 								scheduleList=sort.sortScheduleByTimeRange(scheduleList);//排序
	 								//获得schedule内容。写到列表中
	 								for(int i=0;i<scheduleList.size();i++){
	 									Schedule schedule=scheduleList.get(i);
	 									Log.e("ScheduleList:",schedule.getDoctorName()+schedule.getDoctorId()+":"+schedule.getTimeRange()+" 可预约数："+schedule.getAvailableNum());
	 									int numi=Integer.parseInt(schedule.getAvailableNum());
	 									//if(schedule.getOutcallDate().equals(day)&&numi>0){
	 									if(schedule.getOutcallDate().equals(day)){
	 										Button oneButton=new Button(scheduleDetailLayout.getContext());
	 										oneButton.setPadding(10, 5, 10, 5);
	 										
	 										oneButton.setText(schedule.getTimeRange()+" 可预约数："+schedule.getAvailableNum());
	 										oneButton.setTag(schedule);
	 										oneButton.setBackgroundDrawable(layout.getResources().getDrawable(R.layout.schedule_btn_style_layout_click));
	 										
	 										oneButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, oneButton.getResources().getDimension(R.dimen.button_textsize));
	 										
	 										LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	 										lp.setMargins(0, 3, 3, 0);
	 										oneButton.setLayoutParams(lp);
	 										
	 										
	 										oneButton.setOnClickListener(new OnClickListener(){
	 											@Override
	 											public void onClick(View arg0) {
	 												arg0.setBackgroundDrawable(arg0.getResources().getDrawable(R.layout.schedule_btn_style_layout));
	 												Schedule schedule=(Schedule)arg0.getTag();
	 												String doctorId=schedule.getDoctorId();
	 												String doctorName=schedule.getDoctorName();
	 												editor.putString("now_state", "booking");
	 												//记录医生信息要清空					 
	 												editor.putString("doctorId", doctorId);
	 												editor.putString("doctorName", doctorName);
	 												editor.putString("SchState", schedule.getSchState());
	 								      			editor.putString("ScheduleID", schedule.getScheduleID());
	 								      			editor.putString("RegId", schedule.getRegId());
	 								      			editor.putString("ReserveDate", schedule.getOutcallDate());
	 								      			editor.putString("ReserveTime", schedule.getTimeRange());
	 								      			editor.putString("IdType", "");
	 								      			editor.putString("IdCode", "");
													editor.commit();
													
														BookingReq req = new BookingReq();
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
													
	 												//Log.e("erorr",schedule.getDoctorName()+schedule.getDoctorId()+":"+schedule.getTimeRange()+" 可预约数："+schedule.getAvailableNum());
	 												if(sp.getString("username", "").equals("")){
	 													Toast.makeText(activity, "请先登录", Toast.LENGTH_SHORT).show();
	 													//if(!sp.getBoolean("loginsuccess", false)){
	 														activity.startActivity(new Intent(activity,LoginActivity.class));
	 													//}
	 												}else{
		 												if(!sp.getString("defaultcardno","").equals("")){
		 													
		 													Setting.setDefaultCardNumber(sp,editor);
		 													BookingAction action=new BookingAction(activity);
		 													action.confirmBooking();
		 												}else{
	 														activity.startActivity(new Intent(activity,ListCardActivity.class));
		 												}
	 												}
	 											}
	 										});
		 										scheduleDetailLayout.addView(oneButton);
		 									}
		 								}
	 								}
	 							}});
	    	            	
	    	            	row.addView(days);
	    	            	count++;
	    	            	
	            		}
	            	}
	            	scheduleLayout.addView(row);
	            	
	            }
	            scheduleText.setText("scheduleList.size()"+scheduleList.size());//有排班
	            scheduleText.setVisibility(View.GONE);
		            
         } else{
        	 	scheduleText.setTextColor(Color.RED);
	            scheduleText.setText("（暂无排班信息）");//没有排班
	            scheduleText.setVisibility(View.VISIBLE);
	          //  scheduleDetailLayout.setVisibility(View.GONE);
         }

	}
	
	
}
