package com.pangu.neusoft.adapters;

import java.util.List;

import com.pangu.neusoft.core.models.Schedule;

public class DoctorList {
	  private String imageUrl;  
      private String text;
      private String id;
      private String departmentname;
      private String sex;
      
      
      
      public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex = sex;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public String getHospitalname() {
		return hospitalname;
	}
	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}
	private String hospitalname;
	  private String level;
	  
	  private String version;

	  private List<Schedule> scheduleList;
	  
	  
      public String getImageUrl() {  
          return imageUrl;  
      }  
      public String getText() {  
          return text;  
      }
      
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public List<Schedule> getScheduleList() {
		return scheduleList;
	}
	public void setScheduleList(List<Schedule> scheduleList) {
		this.scheduleList = scheduleList;
	}  
      
      
      
}
