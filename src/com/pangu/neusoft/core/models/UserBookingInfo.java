package com.pangu.neusoft.core.models;


import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class UserBookingInfo implements KvmSerializable {

	private String cancleID;
	public String getCancleID() { 
		return cancleID;
	}

	public void setCancleID(String cancleID) { 
		this.cancleID = cancleID;
	}

	private String phoneNumber;
	public String getPhoneNumber() { 
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) { 
		this.phoneNumber = phoneNumber;
	}

	private String hospitalId;
	public String getHospitalId() { 
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) { 
		this.hospitalId = hospitalId;
	}

	private String doctorId;
	public String getDoctorId() { 
		return doctorId;
	}

	public void setDoctorId(String doctorId) { 
		this.doctorId = doctorId;
	}

	private String departmentId;
	public String getDepartmentId() { 
		return departmentId;
	}

	public void setDepartmentId(String departmentId) { 
		this.departmentId = departmentId;
	}

	private String reserveDate;
	public String getReserveDate() { 
		return reserveDate;
	}

	public void setReserveDate(String reserveDate) { 
		this.reserveDate = reserveDate;
	}

	private String reserveTime;
	public String getReserveTime() { 
		return reserveTime;
	}

	public void setReserveTime(String reserveTime) { 
		this.reserveTime = reserveTime;
	}

	private String cardNum;
	public String getCardNum() { 
		return cardNum;
	}

	public void setCardNum(String cardNum) { 
		this.cardNum = cardNum;
	}

	private String patientName;
	public String getPatientName() { 
		return patientName;
	}

	public void setPatientName(String patientName) { 
		this.patientName = patientName;
	}

	private String orderNum;
	public String getOrderNum() { 
		return orderNum;
	}

	public void setOrderNum(String orderNum) { 
		this.orderNum = orderNum;
	}

	private String canclestate;
	public String getCanclestate() { 
		return canclestate;
	}

	public void setCanclestate(String canclestate) { 
		this.canclestate = canclestate;
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
				return cancleID;
			case 1:
				return phoneNumber;
			case 2:
				return hospitalId;
			case 3:
				return doctorId;
			case 4:
				return departmentId;
			case 5:
				return reserveDate;
			case 6:
				return reserveTime;
			case 7:
				return cardNum;
			case 8:
				return patientName;
			case 9:
				return orderNum;
			case 10:
				return canclestate;
			case 11:
				return Aucode;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 12;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch (index) {
			case 0:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "cancleID";
			break;
			case 1:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "phoneNumber";
			break;
			case 2:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "hospitalId";
			break;
			case 3:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "doctorId";
			break;
			case 4:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "departmentId";
			break;
			case 5:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "reserveDate";
			break;
			case 6:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "reserveTime";
			break;
			case 7:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "cardNum";
			break;
			case 8:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "patientName";
			break;
			case 9:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "orderNum";
			break;
			case 10:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "canclestate";
			break;
			case 11:
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
				cancleID = value.toString();
			break;
			case 1:
				phoneNumber = value.toString();
			break;
			case 2:
				hospitalId = value.toString();
			break;
			case 3:
				doctorId = value.toString();
			break;
			case 4:
				departmentId = value.toString();
			break;
			case 5:
				reserveDate = value.toString();
			break;
			case 6:
				reserveTime = value.toString();
			break;
			case 7:
				cardNum = value.toString();
			break;
			case 8:
				patientName = value.toString();
			break;
			case 9:
				orderNum = value.toString();
			break;
			case 10:
				canclestate = value.toString();
			break;
			case 11:
				Aucode = value.toString();
			break;
			default:
				break;
		}
	}

}
