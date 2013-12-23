package com.pangu.neusoft.core.models;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class GetCAPTCHAReg  implements KvmSerializable {
	private String PhoneNumber;
	private String Aucode;
	
	
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getAucode() {
		return Aucode;
	}
	public void setAucode(String aucode) {
		Aucode = aucode;
	}
	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
		case 0:
			return PhoneNumber;
		case 1:
			return Aucode;
		}
		return null;
	}
	@Override
	public int getPropertyCount() {
		return 2;
	}
	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "PhoneNumber";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "Aucode";
			break;		
		default:
			break;
		}

	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			PhoneNumber = value.toString();
			break;
		case 1:
			Aucode = value.toString();
			break;	
		default:
			break;
		}
	}
	
	
}
