package com.pangu.neusoft.core.models;

import java.util.ArrayList;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class AreaResponse {
	private ArrayList<Area> areaList;
	private String resultCode;
	private String msg;
	public ArrayList<Area> getAreaList() {
		return areaList;
	}
	public void setAreaList(ArrayList<Area> areaList) {
		this.areaList = areaList;
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
