package com.pangu.neusoft.core.models;

import java.util.ArrayList;

public class HospitalResponse {
	private ArrayList hospitalList;
	private String resultCode;
	private String msg;
	public ArrayList getHospitalList() {
		return hospitalList;
	}
	public void setHospitalList(ArrayList hospitalList) {
		this.hospitalList = hospitalList;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
