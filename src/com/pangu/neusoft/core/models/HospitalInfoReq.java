package com.pangu.neusoft.core.models;


import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class HospitalInfoReq implements KvmSerializable {

	private String HospitalId;
	public String getHospitalId() { 
		return HospitalId;
	}

	public void setHospitalId(String HospitalId) { 
		this.HospitalId = HospitalId;
	}

	private String Aucode;
	public String getAucode() { 
		return Aucode;
	}

	public void setAucode(String Aucode) { 
		this.Aucode = Aucode;
	}

	@Override
	public Object getProperty(int arg0) { 
		switch(arg0) {
			case 0:
				return HospitalId;
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
		switch (index) {
			case 0:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "HospitalId";
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
				HospitalId = value.toString();
			break;
			case 1:
				Aucode = value.toString();
			break;
			default:
				break;
		}
	}

}
