package com.pangu.neusoft.core.models;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class MedicalCard implements KvmSerializable{
	private int MedicalCardTypeID;
	private String MedicalCardCode;
	private String MedicalCardPassword;
	private String Owner;
	private String OwnerSex;
	private int OwnerAge;
	private String OwnerPhone;
	private String OwnerTel;
	private String OwnerIDCard;
	private String OwnerEmail;
	private String OwnerAddress;
	
	
	public int getMedicalCardTypeID() {
		return MedicalCardTypeID;
	}
	public void setMedicalCardTypeID(int medicalCardTypeID) {
		MedicalCardTypeID = medicalCardTypeID;
	}
	public String getMedicalCardCode() {
		return MedicalCardCode;
	}
	public void setMedicalCardCode(String medicalCardCode) {
		MedicalCardCode = medicalCardCode;
	}
	public String getMedicalCardPassword() {
		return MedicalCardPassword;
	}
	public void setMedicalCardPassword(String medicalCardPassword) {
		MedicalCardPassword = medicalCardPassword;
	}
	public String getOwner() {
		return Owner;
	}
	public void setOwner(String owner) {
		Owner = owner;
	}
	public String getOwnerSex() {
		return OwnerSex;
	}
	public void setOwnerSex(String ownerSex) {
		OwnerSex = ownerSex;
	}
	public int getOwnerAge() {
		return OwnerAge;
	}
	public void setOwnerAge(int ownerAge) {
		OwnerAge = ownerAge;
	}
	public String getOwnerPhone() {
		return OwnerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		OwnerPhone = ownerPhone;
	}
	public String getOwnerTel() {
		return OwnerTel;
	}
	public void setOwnerTel(String ownerTel) {
		OwnerTel = ownerTel;
	}
	public String getOwnerIDCard() {
		return OwnerIDCard;
	}
	public void setOwnerIDCard(String ownerIDCard) {
		OwnerIDCard = ownerIDCard;
	}
	public String getOwnerEmail() {
		return OwnerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		OwnerEmail = ownerEmail;
	}
	public String getOwnerAddress() {
		return OwnerAddress;
	}
	public void setOwnerAddress(String ownerAddress) {
		OwnerAddress = ownerAddress;
	}
	@Override
	public Object getProperty(int arg0) {
		switch(arg0){
		case 0:
			return MedicalCardTypeID;
		case 1:
			return MedicalCardCode;
		case 2:
			return MedicalCardPassword;
		case 3:
			return Owner;
		case 4:
			return OwnerSex;
		case 5:
			return OwnerAge;
		case 6:
			return OwnerPhone;
		case 7:
			return OwnerTel;
		case 8:
			return OwnerIDCard;
		case 9:
			return OwnerEmail;
		case 10:
			return OwnerAddress;
		}
		return null;
	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 11;
	}
	@Override
	public void getPropertyInfo(int index, Hashtable arg1,
			PropertyInfo info) {
		// TODO Auto-generated method stub
		 switch(index){
	        case 0:
	        info.type=PropertyInfo.INTEGER_CLASS;//设置info type的类型
	        info.name="MedicalCardTypeID";
	        break;
	        case 1:
	        info.type=PropertyInfo.STRING_CLASS;
	        info.name="MedicalCardCode";
	        break;
	        case 2:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="MedicalCardPassword";
		        break;
	        case 3:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="Owner";
		        break;
	        case 4:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="OwnerSex";
		        break;
	        case 5:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="OwnerAge";
		        break;
	        case 6:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="OwnerPhone";
		        break;
	        case 7:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="OwnerTel";
		        break;
	        case 8:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="OwnerIDCard";
		        break;
	        case 9:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="OwnerEmail";
		        break;
	        case 10:
		        info.type=PropertyInfo.STRING_CLASS;
		        info.name="OwnerAddress";
		        break;
		        
	        
	        default:
	        break;
	        }
		
	}
	@Override
	public void setProperty(int index, Object value) {
		 switch(index){
		 case 0:
				MedicalCardTypeID=Integer.parseInt(value.toString());
			case 1:
				MedicalCardCode=value.toString();
			case 2:
				MedicalCardPassword=value.toString();
			case 3:
				Owner=value.toString();
			case 4:
				OwnerSex=value.toString();
			case 5:
				OwnerAge=Integer.parseInt(value.toString());
			case 6:
				OwnerPhone=value.toString();
			case 7:
				OwnerTel=value.toString();
			case 8:
				OwnerIDCard=value.toString();
			case 9:
				OwnerEmail=value.toString();
			case 10:
				OwnerAddress=value.toString();
        default:
        break;
         }
		
	}
 }