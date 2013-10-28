package com.pangu.neusoft.core.models;


import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class AddMemberMedicalCard implements KvmSerializable {

	private String UserName;
	public String getUserName() { 
		return UserName;
	}

	public void setUserName(String UserName) { 
		this.UserName = UserName;
	}

	private int MedicalCardTypeID;
	public int getMedicalCardTypeID() { 
		return MedicalCardTypeID;
	}

	public void setMedicalCardTypeID(int MedicalCardTypeID) { 
		this.MedicalCardTypeID = MedicalCardTypeID;
	}

	private String MedicalCardCode;
	public String getMedicalCardCode() { 
		return MedicalCardCode;
	}

	public void setMedicalCardCode(String MedicalCardCode) { 
		this.MedicalCardCode = MedicalCardCode;
	}

	private String MedicalCardPassword;
	public String getMedicalCardPassword() { 
		return MedicalCardPassword;
	}

	public void setMedicalCardPassword(String MedicalCardPassword) { 
		this.MedicalCardPassword = MedicalCardPassword;
	}

	private String Owner;
	public String getOwner() { 
		return Owner;
	}

	public void setOwner(String Owner) { 
		this.Owner = Owner;
	}

	private String OwnerSex;
	public String getOwnerSex() { 
		return OwnerSex;
	}

	public void setOwnerSex(String OwnerSex) { 
		this.OwnerSex = OwnerSex;
	}

	private String OwnerAge;
	public String getOwnerAge() { 
		return OwnerAge;
	}

	public void setOwnerAge(String OwnerAge) { 
		this.OwnerAge = OwnerAge;
	}

	private String OwnerPhone;
	public String getOwnerPhone() { 
		return OwnerPhone;
	}

	public void setOwnerPhone(String OwnerPhone) { 
		this.OwnerPhone = OwnerPhone;
	}

	private String OwnerTel;
	public String getOwnerTel() { 
		return OwnerTel;
	}

	public void setOwnerTel(String OwnerTel) { 
		this.OwnerTel = OwnerTel;
	}

	private String OwnerIDCard;
	public String getOwnerIDCard() { 
		return OwnerIDCard;
	}

	public void setOwnerIDCard(String OwnerIDCard) { 
		this.OwnerIDCard = OwnerIDCard;
	}

	private String OwnerEmail;
	public String getOwnerEmail() { 
		return OwnerEmail;
	}

	public void setOwnerEmail(String OwnerEmail) { 
		this.OwnerEmail = OwnerEmail;
	}

	private String OwnerAddress;
	public String getOwnerAddress() { 
		return OwnerAddress;
	}

	public void setOwnerAddress(String OwnerAddress) { 
		this.OwnerAddress = OwnerAddress;
	}

	private String Aucode;
	public String getAucode() { 
		return Aucode;
	}

	public void setAucode(String Aucode) { 
		this.Aucode = Aucode;
	}

	@Override
	public Object getProperty(int arg0) { 
		switch(arg0) {
			case 0:
				return UserName;
			case 1:
				return MedicalCardTypeID;
			case 2:
				return MedicalCardCode;
			case 3:
				return MedicalCardPassword;
			case 4:
				return Owner;
			case 5:
				return OwnerSex;
			case 6:
				return OwnerAge;
			case 7:
				return OwnerPhone;
			case 8:
				return OwnerTel;
			case 9:
				return OwnerIDCard;
			case 10:
				return OwnerEmail;
			case 11:
				return OwnerAddress;
			case 12:
				return Aucode;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 13;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch (index) {
			case 0:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "UserName";
			break;
			case 1:
				info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "MedicalCardTypeID";
			break;
			case 2:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "MedicalCardCode";
			break;
			case 3:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "MedicalCardPassword";
			break;
			case 4:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "Owner";
			break;
			case 5:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "OwnerSex";
			break;
			case 6:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "OwnerAge";
			break;
			case 7:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "OwnerPhone";
			break;
			case 8:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "OwnerTel";
			break;
			case 9:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "OwnerIDCard";
			break;
			case 10:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "OwnerEmail";
			break;
			case 11:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "OwnerAddress";
			break;
			case 12:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "Aucode";
			break;
			default:
				break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
			case 0:
				UserName = value.toString();
			break;
			case 1:
				MedicalCardTypeID = Integer.parseInt(value.toString());
			break;
			case 2:
				MedicalCardCode = value.toString();
			break;
			case 3:
				MedicalCardPassword = value.toString();
			break;
			case 4:
				Owner = value.toString();
			break;
			case 5:
				OwnerSex = value.toString();
			break;
			case 6:
				OwnerAge = value.toString();
			break;
			case 7:
				OwnerPhone = value.toString();
			break;
			case 8:
				OwnerTel = value.toString();
			break;
			case 9:
				OwnerIDCard = value.toString();
			break;
			case 10:
				OwnerEmail = value.toString();
			break;
			case 11:
				OwnerAddress = value.toString();
			break;
			case 12:
				Aucode = value.toString();
			break;
			default:
				break;
		}
	}

}
