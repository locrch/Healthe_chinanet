package com.pangu.neusoft.adapters;

public class HospitalList {
	  private String imageUrl;  
      private String text;
      private String id;

      private String grade;
	  private String level;
	  private String address;
	  private String version;

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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}  
      
      
      
}
