package com.pangu.neusoft.core.models;

import java.math.BigDecimal;
import java.util.List;


public class Doctor {
	   protected String doctorId;
	    protected String doctorName;
	    protected String sex;
	    protected String title;
	    protected String info;
	    protected int version;
	    protected List<Schedule> scheduleList;
	    protected String pictureUrl;
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
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
		public int getVersion() {
			return version;
		}
		public void setVersion(int version) {
			this.version = version;
		}
		public List<Schedule> getScheduleList() {
			return scheduleList;
		}
		public void setScheduleList(List<Schedule> scheduleList) {
			this.scheduleList = scheduleList;
		}
		public String getPictureUrl() {
			return pictureUrl;
		}
		public void setPictureUrl(String pictureUrl) {
			this.pictureUrl = pictureUrl;
		}
	    
}
