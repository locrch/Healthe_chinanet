package com.pangu.neusoft.core.models;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class DS_GetDrugCompanyReq implements KvmSerializable
{
	private String DrugCompanyID, Type;

	public String getDrugCompanyID()
	{
		return DrugCompanyID;
	}

	public void setDrugCompanyID(String drugCompanyID)
	{
		DrugCompanyID = drugCompanyID;
	}

	public String getType()
	{
		return Type;
	}

	public void setType(String type)
	{
		Type = type;
	}

	@Override
	public Object getProperty(int arg0)
	{
		// TODO Auto-generated method stub
		switch (arg0)
		{
		case 0:
			return DrugCompanyID;
		case 1:
			return Type;

		}
		return null;
	}

	@Override
	public int getPropertyCount()
	{
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info)
	{
		// TODO Auto-generated method stub
		switch (index)
		{
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "DrugCompanyID";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "Type";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int index, Object value)
	{
		// TODO Auto-generated method stub
		switch (index)
		{
		case 0:
			DrugCompanyID = (String) value;
			break;
		case 1:
			Type = (String) value;
			break;
		default:
			break;
		}
	}

}
