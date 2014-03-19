package com.pangu.neusoft.core.models;

import java.util.ArrayList;

public class DS_DrugShopResponse
{
	private ArrayList DS_DrugShop;
	private String resultCode;
	private String msg;

	public ArrayList getDS_DrugShop()
	{
		return DS_DrugShop;
	}

	public void setDS_DrugShop(ArrayList dS_DrugShop)
	{
		DS_DrugShop = dS_DrugShop;
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
