package com.pangu.neusoft.core.models;

import java.util.ArrayList;

public class DS_DrugCompanyResponse
{
	private ArrayList DrugCompanyList;
	private String resultCode;
	private String msg;

	public ArrayList getDrugCompanyList()
	{
		return DrugCompanyList;
	}

	public void setDrugCompanyNameList(ArrayList drugCompanyList)
	{
		DrugCompanyList = drugCompanyList;
	}

	public String getResultCode()
	{
		return resultCode;
	}

	public void setResultCode(String resultCode)
	{
		this.resultCode = resultCode;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

}
