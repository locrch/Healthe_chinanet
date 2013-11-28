package com.pangu.neusoft.core.models;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class FindDoctorListReq implements KvmSerializable 
{
	private String HospitalId,DepartmentId,DoctorName;
	
	
	
	public String getHospitalId()
	{
		return HospitalId;
	}

	public void setHospitalId(String hospitalId)
	{
		HospitalId = hospitalId;
	}

	public String getDepartmentId()
	{
		return DepartmentId;
	}

	public void setDepartmentId(String departmentId)
	{
		DepartmentId = departmentId;
	}

	public String getDoctorName()
	{
		return DoctorName;
	}

	public void setDoctorName(String doctorName)
	{
		DoctorName = doctorName;
	}
	
	private String Aucode;
	public String getAucode() { 
		return Aucode;
	}

	public void setAucode(String Aucode) { 
		this.Aucode = Aucode;
	}

	@Override
	public Object getProperty(int arg0)
	{
		// TODO Auto-generated method stub
		switch(arg0) {
		case 0:
			return HospitalId;
		case 1:
			return DepartmentId;
		case 2:
			return DoctorName;
		case 3:
			return Aucode;
	}
		return null;
	}

	@Override
	public int getPropertyCount()
	{
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info)
	{
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "HospitalId";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "DepartmentId";
			break;
		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "DoctorName";
			break;
		case 3:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "Aucode";
			break;
		default:
			break;
	}
	}

	@Override
	public void setProperty(int index, Object value)
	{
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			HospitalId = value.toString();
		break;
		case 1:
			DepartmentId = value.toString();
		break;
		case 2:
			DoctorName = value.toString();
		break;
		case 3:
			Aucode = value.toString();
		break;
		default:
			break;
	}
	}

}
