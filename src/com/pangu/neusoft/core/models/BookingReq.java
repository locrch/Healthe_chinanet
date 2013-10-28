package com.pangu.neusoft.core.models;


import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class BookingReq implements KvmSerializable {

	private String MedicalCardTypeID;
	
	
	public String getMedicalCardTypeID() {
		return MedicalCardTypeID;
	}

	public void setMedicalCardTypeID(String medicalCardTypeID) {
		MedicalCardTypeID = medicalCardTypeID;
	}

	private String PhoneNumber;
	public String getPhoneNumber() { 
		return PhoneNumber;
	}

	public void setPhoneNumber(String PhoneNumber) { 
		this.PhoneNumber = PhoneNumber;
	}

	private String HospitalId;
	public String getHospitalId() { 
		return HospitalId;
	}

	public void setHospitalId(String HospitalId) { 
		this.HospitalId = HospitalId;
	}

	private String DepartmentId;
	public String getDepartmentId() { 
		return DepartmentId;
	}

	public void setDepartmentId(String DepartmentId) { 
		this.DepartmentId = DepartmentId;
	}

	private String DoctorId;
	public String getDoctorId() { 
		return DoctorId;
	}

	public void setDoctorId(String DoctorId) { 
		this.DoctorId = DoctorId;
	}

	private String SchState;
	public String getSchState() { 
		return SchState;
	}

	public void setSchState(String SchState) { 
		this.SchState = SchState;
	}

	private String RegId;
	public String getRegId() { 
		return RegId;
	}

	public void setRegId(String RegId) { 
		this.RegId = RegId;
	}

	private String ReserveDate;
	public String getReserveDate() { 
		return ReserveDate;
	}

	public void setReserveDate(String ReserveDate) { 
		this.ReserveDate = ReserveDate;
	}

	private String ReserveTime;
	public String getReserveTime() { 
		return ReserveTime;
	}

	public void setReserveTime(String ReserveTime) { 
		this.ReserveTime = ReserveTime;
	}

	private String IdType;
	public String getIdType() { 
		return IdType;
	}

	public void setIdType(String IdType) { 
		this.IdType = IdType;
	}

	private String IdCode;
	public String getIdCode() { 
		return IdCode;
	}

	public void setIdCode(String IdCode) { 
		this.IdCode = IdCode;
	}

	private String CardNum;
	public String getCardNum() { 
		return CardNum;
	}

	public void setCardNum(String CardNum) { 
		this.CardNum = CardNum;
	}

	private String PatientName;
	public String getPatientName() { 
		return PatientName;
	}

	public void setPatientName(String PatientName) { 
		this.PatientName = PatientName;
	}

	private String MemberId;
	public String getMemberId() { 
		return MemberId;
	}

	public void setMemberId(String MemberId) { 
		this.MemberId = MemberId;
	}

	private String BookingWayID;
	public String getBookingWayID() { 
		return BookingWayID;
	}

	public void setBookingWayID(String BookingWayID) { 
		this.BookingWayID = BookingWayID;
	}

	private String ScheduleID;
	public String getScheduleID() { 
		return ScheduleID;
	}

	public void setScheduleID(String ScheduleID) { 
		this.ScheduleID = ScheduleID;
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
				return PhoneNumber;
			case 1:
				return HospitalId;
			case 2:
				return DepartmentId;
			case 3:
				return DoctorId;
			case 4:
				return SchState;
			case 5:
				return RegId;
			case 6:
				return ReserveDate;
			case 7:
				return ReserveTime;
			case 8:
				return IdType;
			case 9:
				return IdCode;
			case 10:
				return CardNum;
			case 11:
				return PatientName;
			case 12:
				return MemberId;
			case 13:
				return BookingWayID;
			case 14:
				return ScheduleID;
			case 15:
				return Aucode;
			case 16:
				return MedicalCardTypeID;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 17;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch (index) {
			case 0:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "PhoneNumber";
			break;
			case 1:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "HospitalId";
			break;
			case 2:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "DepartmentId";
			break;
			case 3:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "DoctorId";
			break;
			case 4:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "SchState";
			break;
			case 5:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "RegId";
			break;
			case 6:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "ReserveDate";
			break;
			case 7:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "ReserveTime";
			break;
			case 8:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "IdType";
			break;
			case 9:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "IdCode";
			break;
			case 10:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "CardNum";
			break;
			case 11:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "PatientName";
			break;
			case 12:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "MemberId";
			break;
			case 13:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "BookingWayID";
			break;
			case 14:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "ScheduleID";
			break;
			case 15:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "Aucode";
			
			break;
			case 16:
				info.type = PropertyInfo.STRING_CLASS;
				info.name = "MedicalCardTypeID";
			break;			
			default:
				break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
			case 0:
				PhoneNumber = value.toString();
			break;
			case 1:
				HospitalId = value.toString();
			break;
			case 2:
				DepartmentId = value.toString();
			break;
			case 3:
				DoctorId = value.toString();
			break;
			case 4:
				SchState = value.toString();
			break;
			case 5:
				RegId = value.toString();
			break;
			case 6:
				ReserveDate = value.toString();
			break;
			case 7:
				ReserveTime = value.toString();
			break;
			case 8:
				IdType = value.toString();
			break;
			case 9:
				IdCode = value.toString();
			break;
			case 10:
				CardNum = value.toString();
			break;
			case 11:
				PatientName = value.toString();
			break;
			case 12:
				MemberId = value.toString();
			break;
			case 13:
				BookingWayID = value.toString();
			break;
			case 14:
				ScheduleID = value.toString();
			break;
			case 15:
				Aucode = value.toString();
			break;
			case 16:
				MedicalCardTypeID = value.toString();
			break;
			default:
				break;
		}
	}

}
