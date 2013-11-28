package com.pangu.neusoft.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.pangu.neusoft.core.models.AddMemberMedicalCard;
import com.pangu.neusoft.core.models.Area;
import com.pangu.neusoft.core.models.AreaReq;
import com.pangu.neusoft.core.models.AreaResponse;
import com.pangu.neusoft.core.models.BookingReq;
import com.pangu.neusoft.core.models.CardListReq;
import com.pangu.neusoft.core.models.DeleteCardReq;
import com.pangu.neusoft.core.models.DepartmentReq;
import com.pangu.neusoft.core.models.DoctorInfoReq;
import com.pangu.neusoft.core.models.DoctorReq;
import com.pangu.neusoft.core.models.FindDoctorListReq;
import com.pangu.neusoft.core.models.FindHospitalListReq;
import com.pangu.neusoft.core.models.HandleBooking;
import com.pangu.neusoft.core.models.HospitalInfoReq;
import com.pangu.neusoft.core.models.HospitalReq;
import com.pangu.neusoft.core.models.Member;
import com.pangu.neusoft.core.models.MemberReg;
import com.pangu.neusoft.core.models.SchedulingReq;




import android.util.Log;

public class WebService implements GET{
	
	
	public SoapObject getAreaList(AreaReq req){		 
		 //获得区域列表
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("getAreaList"));
		 request.addProperty("req",req);		 
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
         envelope.addMapping(NAMESPACE, "req", AreaReq.class);          
         return getSoapObject(request,"getAreaList",envelope);
	}
	
	public SoapObject sendMemberData(MemberReg req,String method){		 
		 //发送用户数据
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName(method));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", MemberReg.class);
		 return getSoapObject(request,method,envelope);
	}
	
	public SoapObject getHospitalList(HospitalReq req){		 
		 //获得医院列表
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("getHospitalList"));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", HospitalReq.class);
		 return getSoapObject(request,"getHospitalList",envelope);
	}
	
	public SoapObject getCardList(CardListReq req){		 
		 //获得医院列表
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("getMemberCardList"));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", CardListReq.class);
		 return getSoapObject(request,"getMemberCardList",envelope);
	}
	
	public SoapObject getHospitalDetail(HospitalInfoReq req){		 
		 //获得医院详细信息
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("getHospitalInfo"));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", HospitalInfoReq.class);
		 return getSoapObject(request,"getHospitalInfo",envelope);
	}
	
	public SoapObject getDoctorDetail(DoctorInfoReq req){		 
		 //获得医院详细信息
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("getDoctorInfo"));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", DoctorInfoReq.class);
		 return getSoapObject(request,"getDoctorInfo",envelope);
	}
	
	public SoapObject getDepartmentList(DepartmentReq req){		 
		 //获得医院科室列表
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("getDepartmentList"));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", DepartmentReq.class);
		 return getSoapObject(request,"getDepartmentList",envelope);
	}
	
	public SoapObject getDepartmentInfo(DoctorReq req){		 
		 //获得医院科室列表
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("getDepartmentInfo"));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", DoctorReq.class);
		 return getSoapObject(request,"getDepartmentInfo",envelope);
	}
	
	public SoapObject booking(BookingReq req){		 
		 //booking
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("booking"));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", BookingReq.class);
		 return getSoapObject(request,"booking",envelope);
	}
	
	public SoapObject getDoctorList(DoctorReq req){		 
		 //获得医院科室医生列表
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("getDoctorList"));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", DoctorReq.class);
		 return getSoapObject(request,"getDoctorList",envelope);
	}
	
	public SoapObject getSchedulingList(SchedulingReq req){		 
		 //获得排班表
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("getSchedulingList"));
		 request.addProperty("req",req);		 
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.addMapping(NAMESPACE, "req", SchedulingReq.class);          
        return getSoapObject(request,"getSchedulingList",envelope);
	}
	
	public SoapObject cancelBooking(HandleBooking req){		 
		 //获得医院科室列表
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("cancleBooking"));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", HandleBooking.class);
		 return getSoapObject(request,"cancleBooking",envelope);
	}
	
	public SoapObject findDoctorList(FindDoctorListReq req){		 
		 //搜索医生列表
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("findDoctorList"));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", FindDoctorListReq.class);
		 return getSoapObject(request,"findDoctorList",envelope);
	}
	
	public SoapObject findHospitalList(FindHospitalListReq req){		 
		 //搜索医院列表
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("findHospitalList"));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", FindHospitalListReq.class);
		 return getSoapObject(request,"findHospitalList",envelope);
	}
	
	public SoapObject getSoapObject(SoapObject request,String method,SoapSerializationEnvelope envelope){	         
	        envelope.implicitTypes = true;
	        envelope.setOutputSoapObject(request);
	        envelope.dotNet = true;	       
	        //注册envelope
	        MarshalBase64 md = new MarshalBase64();
	        md.register(envelope);
	        //创建HttpTransportSE对象。通过HttpTransportSE类的构造方法可以指定WebService的WSDL文档的URL
	        //使用call方法调用WebService方法  
	        try {
	       	HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
				androidHttpTransport.call(NAMESPACE+index.GetMethodName(method), envelope);
				SoapObject  obj = (SoapObject)envelope.getResponse();
				return obj;
			} catch (IOException e) {
				Log.e("errr",e.toString());
				return null;
			} catch (XmlPullParserException e) {
				Log.e("errr2",e.toString());
				return null;
			} 
	}
	
	public SoapObject addMemberCard(AddMemberMedicalCard req){		 
		 //发送用户数据
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("addMemberCard"));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", AddMemberMedicalCard.class);
		 return getSoapObject(request,"addMemberCard",envelope);
	}
	
	public SoapObject deleteMemberCard(DeleteCardReq req){		 
		 //发送用户数据
		 SoapObject request = new SoapObject(NAMESPACE, index.GetMethodName("deleteMemberCard"));
		 request.addProperty("req",req);
		 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.addMapping(NAMESPACE, "req", DeleteCardReq.class);
		 return getSoapObject(request,"deleteMemberCard",envelope);
	}
	
	public boolean getPremObject(SoapObject request,String method,SoapSerializationEnvelope envelope){	         
        envelope.implicitTypes = true;
        envelope.setOutputSoapObject(request);
        envelope.dotNet = true;	       
        //注册envelope
        MarshalBase64 md = new MarshalBase64();
        md.register(envelope);
        //创建HttpTransportSE对象。通过HttpTransportSE类的构造方法可以指定WebService的WSDL文档的URL
        //使用call方法调用WebService方法  
        try {
       	HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(NAMESPACE+index.GetMethodName(method), envelope);
			SoapPrimitive  res = (SoapPrimitive)envelope.getResponse();
			
			
			boolean res_boolean=Boolean.parseBoolean(res.toString());
			return res_boolean;
		} catch (IOException e) {
			Log.e("errr",e.toString());
			return false;
		} catch (XmlPullParserException e) {
			Log.e("errr2",e.toString());
			return false;
		} 
}
}
