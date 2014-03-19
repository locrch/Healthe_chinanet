package com.pangu.neusoft.core.models;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class GetPreferentialNewsReq implements KvmSerializable
{
	private int DrugCompanyID, PreferentialNewsID, Type;

	public int getDrugCompanyID()
	{
		return DrugCompanyID;
	}

	public void setDrugCompanyID(int drugCompanyID)
	{
		DrugCompanyID = drugCompanyID;
	}

	public int getPreferentialNewsID()
	{
		return PreferentialNewsID;
	}

	public void setPreferentialNewsID(int preferentialNewsID)
	{
		PreferentialNewsID = preferentialNewsID;
	}

	public int getType()
	{
		return Type;
	}

	public void setType(int type)
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
			return PreferentialNewsID;
		case 2:
			return Type;
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
		switch (index)
		{
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "DrugCompanyID";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "PreferentialNewsID";
			break;
		case 2:
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
			DrugCompanyID = (Integer) value;
			break;
		case 1:
			PreferentialNewsID = (Integer) value;
			break;
		case 2:
			Type = (Integer) value;
			break;
		default:
			break;
		}
	}

}
