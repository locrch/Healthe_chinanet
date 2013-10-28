package com.pangu.neusoft.core.models;


import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class MemberReg implements KvmSerializable{
	private String UserName;
	private String Password;
	private String CAPTCHA;
	private String Aucode;
	private String MemberName;
	private String IDCardNo;
	private int Sex;
	
	public String getMemberName() {
		return MemberName;
	}
	public void setMemberName(String memberName) {
		MemberName = memberName;
	}
	public String getIDCardNo() {
		return IDCardNo;
	}
	public void setIDCardNo(String iDCardNo) {
		IDCardNo = iDCardNo;
	}
	public int getSex() {
		return Sex;
	}
	public void setSex(int sex) {
		Sex = sex;
	}
	public String getAucode() {
		return Aucode;
	}
	public void setAucode(String aucode) {
		Aucode = aucode;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
	
	public String getCAPTCHA() {
		return CAPTCHA;
	}
	public void setCAPTCHA(String cAPTCHA) {
		CAPTCHA = cAPTCHA;
	}
	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
		case 0:
			return UserName;
		case 1:
			return Password;
		case 2:
			return CAPTCHA;
		case 3:
			return Aucode;
		case 4:
			return MemberName;
		case 5:
			return IDCardNo;
		case 6:
			return Sex;
		}
		return null;
	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 7;
	}
	@Override
	public void getPropertyInfo(int index, Hashtable arg1,
			PropertyInfo info) {
		// TODO Auto-generated method stub
		 switch(index){
	        case 0:
		        info.type=PropertyInfo.STRING_CLASS;//设置info type的类型
		        info.name="UserName";
		        break;
	        case 1:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="Password";
		        break;
	        case 2:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="CAPTCHA";
		        break;
	        
	        case 3:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="Aucode";
		        break;
	        case 4:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="MemberName";
		        break;
	        case 5:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="IDCardNo";
		        break;
	        case 6:
		        info.type=PropertyInfo.INTEGER_CLASS;
		        info.name="Sex";
		        break;
		        
	        default:
	        break;
	        }
		
	}
	@Override
	public void setProperty(int index, Object value) {
		 switch(index){
         case 0:
	        UserName=value.toString();
	        break;
         case 1:
	        Password=value.toString();
	        break;
         case 2:
        	 CAPTCHA=value.toString();
             break;
         case 3:
             Aucode=value.toString();
             break;
         case 4:
        	 MemberName=value.toString();
             break;
         case 5:
        	 IDCardNo=value.toString();
             break;
         case 6:
        	 Sex=Integer.parseInt(value.toString());
             break;
        default:
        break;
         }
		
	}
 }