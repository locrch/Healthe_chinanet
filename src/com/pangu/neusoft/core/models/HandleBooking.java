package com.pangu.neusoft.core.models;


import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class HandleBooking implements KvmSerializable {

	private String CardNum;
	public String getCardNum() { 
		return CardNum;
	}

	public void setCardNum(String CardNum) { 
		this.CardNum = CardNum;
	}

	private String OrderNum;
	public String getOrderNum() { 
		return OrderNum;
	}

	public void setOrderNum(String OrderNum) { 
		this.OrderNum = OrderNum;
	}

	private String HospitalId;
	public String getHospitalId() { 
		return HospitalId;
	}

	public void setHospitalId(String HospitalId) { 
		this.HospitalId = HospitalId;
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

	private String CancleID;
	public String getCancleID() { 
		return CancleID;
	}

	public void setCancleID(String CancleID) { 
		this.CancleID = CancleID;
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
				return CardNum;
			case 1:
				return OrderNum;
			case 2:
				return HospitalId;
			case 3:
				return ReserveDate;
			case 4:
				return ReserveTime;
			case 5:
				return CancleID;
			case 6:
				return Aucode;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 7;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch (index) {
			case 0:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "CardNum";
			break;
			case 1:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "OrderNum";
			break;
			case 2:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "HospitalId";
			break;
			case 3:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "ReserveDate";
			break;
			case 4:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "ReserveTime";
			break;
			case 5:
				info.type = PropertyInfo.STRING_CLASS;
			info.name = "CancleID";
			break;
			case 6:
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
				CardNum = value.toString();
			break;
			case 1:
				OrderNum = value.toString();
			break;
			case 2:
				HospitalId = value.toString();
			break;
			case 3:
				ReserveDate = value.toString();
			break;
			case 4:
				ReserveTime = value.toString();
			break;
			case 5:
				CancleID = value.toString();
			break;
			case 6:
				Aucode = value.toString();
			break;
			default:
				break;
		}
	}

}
