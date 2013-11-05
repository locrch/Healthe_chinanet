package com.pangu.neusoft.core.models;


import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class CardListReq implements KvmSerializable{
	private String UserName;
	
	private String Aucode;
	
	
	
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
	
	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
		case 0:
			return UserName;
		case 1:
			return Aucode;
		}
		return null;
	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 2;
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
		        info.name="Aucode";
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
        	 Aucode=value.toString();
	        break;
        default:
        	break;
        }
		
	}
 }