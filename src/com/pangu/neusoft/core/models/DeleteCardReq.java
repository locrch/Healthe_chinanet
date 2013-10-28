package com.pangu.neusoft.core.models;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class DeleteCardReq implements KvmSerializable {
	private String UserName;
	private String MedicalCardID;
	private String Aucode;
	
	

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getMedicalCardID() {
		return MedicalCardID;
	}

	public void setMedicalCardID(String medicalCardID) {
		MedicalCardID = medicalCardID;
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
			return UserName;
		case 1:
			return MedicalCardID;
		case 2:
			return Aucode;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "UserName";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "MedicalCardID";
			break;	
		case 2:
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
			UserName = value.toString();
			break;
		case 1:
			MedicalCardID = value.toString();
			break;	
		case 2:
			Aucode = value.toString();
			break;	
		default:
			break;
		}
	}

}
