package com.pangu.neusoft.adapters;

public class DS_DrugCompanyList {
	
	private static String DrugCompanyID,DrugCompanyName,LogoURL,Introduction,Address,Telephone;
	  
	

	public String getDrugCompanyID()
	{
		return DrugCompanyID;
	}

	public void setDrugCompanyID(String drugCompanyID)
	{
		DrugCompanyID = drugCompanyID;
	}

	public String getDrugCompanyName()
	{
		return DrugCompanyName;
	}

	public void setDrugCompanyName(String drugCompanyName)
	{
		this.DrugCompanyName = drugCompanyName;
	}

	public String getLogoURL()
	{
		return LogoURL;
	}

	public void setLogoURL(String logoURL)
	{
		this.LogoURL = logoURL;
	}

	public String getIntroduction()
	{
		return Introduction;
	}

	public void setIntroduction(String introduction)
	{
		this.Introduction = introduction;
	}

	public String getAddress()
	{
		return Address;
	}

	public void setAddress(String address)
	{
		this.Address = address;
	}

	public String getTelephone()
	{
		return Telephone;
	}

	public void setTelephone(String telephone)
	{
		this.Telephone = telephone;
	}
      
      
      
}
