package com.pangu.neusoft.core.models;

import java.util.List;

import android.widget.LinearLayout;

public class ScheduleButton {
	LinearLayout scheduleDetailLayout;
	List<Schedule> scheduleList;
	String day;
	public LinearLayout getScheduleDetailLayout() {
		return scheduleDetailLayout;
	}
	public void setScheduleDetailLayout(LinearLayout scheduleDetailLayout) {
		this.scheduleDetailLayout = scheduleDetailLayout;
	}
	public List<Schedule> getScheduleList() {
		return scheduleList;
	}
	public void setScheduleList(List<Schedule> scheduleList) {
		this.scheduleList = scheduleList;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
}
