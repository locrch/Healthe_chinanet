package com.pangu.neusoft.core.models;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class FindHospitalListReq implements KvmSerializable
{
	private String AreaID,HospitalName,Aucode;
	public String getAreaID()
	{
		return AreaID;
	}

	public void setAreaID(String areaID)
	{
		AreaID = areaID;
	}

	public String getHospitalName()
	{
		return HospitalName;
	}

	public void setHospitalName(String hospitalName)
	{
		HospitalName = hospitalName;
	}

	public String getAucode()
	{
		return Aucode;
	}

	public void setAucode(String aucode)
	{
		Aucode = aucode;
	}

	@Override
	public Object getProperty(int arg0)
	{
		// TODO Auto-generated method stub
		switch(arg0) {
		case 0:
			return AreaID;
		case 1:
			return HospitalName;
		case 2:
			return Aucode;
		}
		return null;
	}

	@Override
	public int getPropertyCount()
	{
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info)
	{
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "AreaID";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "HospitalName";
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
	public void setProperty(int index, Object value)
	{
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			AreaID = value.toString();
		break;
		case 1:
			HospitalName = value.toString();
		break;
		case 2:
			Aucode = value.toString();
		break;
		default:
			break;
	}
	}

}
