package com.pangu.neusoft.core.models;

import java.util.ArrayList;

public class DS_GetPreferentialNewsResponse
{
	private ArrayList DS_PreferentialNewsList;
	private String resultCode;
	private String msg;

	public ArrayList getDS_PreferentialNewsList()
	{
		return DS_PreferentialNewsList;
	}

	public void setDS_PreferentialNewsList(ArrayList dS_PreferentialNewsList)
	{
		DS_PreferentialNewsList = dS_PreferentialNewsList;
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
