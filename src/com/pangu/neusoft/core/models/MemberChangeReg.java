package com.pangu.neusoft.core.models;


import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class MemberChangeReg implements KvmSerializable{
	private String UserName;
	private String PhoneNumber;
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
	
	
	
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
		case 0:
			return UserName;
		case 1:
			return PhoneNumber;
		case 2:
			return Aucode;
		case 3:
			return MemberName;
		case 4:
			return IDCardNo;
		case 5:
			return Sex;
		}
		return null;
	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 6;
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
		        info.name="PhoneNumber";
		        break;	        
	        case 2:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="Aucode";
		        break;
	        case 3:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="MemberName";
		        break;
	        case 4:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="IDCardNo";
		        break;
	        case 5:
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
        	 PhoneNumber=value.toString();
	        break;
         case 2:
             Aucode=value.toString();
             break;
         case 3:
        	 MemberName=value.toString();
             break;
         case 4:
        	 IDCardNo=value.toString();
             break;
         case 5:
        	 Sex=Integer.parseInt(value.toString());
             break;
        default:
        break;
         }
		
	}
 }