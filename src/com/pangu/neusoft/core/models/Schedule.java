package com.pangu.neusoft.core.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Schedule  implements Parcelable{
		private String schState;
	    private String doctorId;
	    private String doctorName;
	    private String regId;
	    private String regName;
	    private String isSuspend;
	    private String outcallDate;
	    private String dayOfWeek;
	    private String timeRange;
	    private String availableNum;
	    private String consultationFee;
	    private String scheduleID;
	    
	  //必须的空构造器，因为下面有一个私有的构造器，否则不能new对象   
	    public Schedule() {   
	  
	    }   
	    
		public String getSchState() {
			return schState;
		}
		public void setSchState(String schState) {
			this.schState = schState;
		}
		public String getDoctorId() {
			return doctorId;
		}
		public void setDoctorId(String doctorId) {
			this.doctorId = doctorId;
		}
		public String getDoctorName() {
			return doctorName;
		}
		public void setDoctorName(String doctorName) {
			this.doctorName = doctorName;
		}
		public String getRegId() {
			return regId;
		}
		public void setRegId(String regId) {
			this.regId = regId;
		}
		public String getRegName() {
			return regName;
		}
		public void setRegName(String regName) {
			this.regName = regName;
		}
		public String getIsSuspend() {
			return isSuspend;
		}
		public void setIsSuspend(String isSuspend) {
			this.isSuspend = isSuspend;
		}
		public String getOutcallDate() {
			return outcallDate;
		}
		public void setOutcallDate(String outcallDate) {
			this.outcallDate = outcallDate;
		}
		public String getDayOfWeek() {
			return dayOfWeek;
		}
		public void setDayOfWeek(String dayOfWeek) {
			this.dayOfWeek = dayOfWeek;
		}
		public String getTimeRange() {
			return timeRange;
		}
		public void setTimeRange(String timeRange) {
			this.timeRange = timeRange;
		}
		public String getAvailableNum() {
			return availableNum;
		}
		public void setAvailableNum(String availableNum) {
			this.availableNum = availableNum;
		}
		public String getConsultationFee() {
			return consultationFee;
		}
		public void setConsultationFee(String consultationFee) {
			this.consultationFee = consultationFee;
		}
		public String getScheduleID() {
			return scheduleID;
		}
		public void setScheduleID(String scheduleID) {
			this.scheduleID = scheduleID;
		}
		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			// TODO Auto-generated method stub
			dest.writeString(schState);
		    dest.writeString(doctorId);
		    dest.writeString(doctorName);
		    dest.writeString(regId);
		    dest.writeString(regName);
		    dest.writeString(isSuspend);
		    dest.writeString(outcallDate);
		    dest.writeString(dayOfWeek);
		    dest.writeString(timeRange);
		    dest.writeString(availableNum);
		    dest.writeString(consultationFee);
		    dest.writeString(scheduleID);
		}
		
		//关键的事情  
	    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>() {  
	        public Schedule createFromParcel(Parcel in) {  
	            return new Schedule(in);  
	        }  
	  
	        public Schedule[] newArray(int size) {  
	            return new Schedule[size];  
	        }  
	    };  
	//  
	    private Schedule(Parcel in) {  
	          
	        
	        schState= in.readString();
		    doctorId= in.readString();
		    doctorName= in.readString();
		    regId= in.readString();
		    regName= in.readString();
		    isSuspend= in.readString();
		    outcallDate= in.readString();
		    dayOfWeek= in.readString();
		    timeRange= in.readString();
		    availableNum= in.readString();
		    consultationFee= in.readString();
		   scheduleID= in.readString();
	    }  
	    
}
