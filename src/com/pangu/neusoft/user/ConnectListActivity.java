package com.pangu.neusoft.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.adapters.DoctorList;
import com.pangu.neusoft.adapters.DoctorListAdapter;
import com.pangu.neusoft.adapters.HospitalList;
import com.pangu.neusoft.adapters.HospitalListAdapter;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.ConnectDoctor;
import com.pangu.neusoft.core.models.Doctor;
import com.pangu.neusoft.core.models.DoctorReq;
import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.healthe.FatherActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.ScheduleListActivity;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.tools.AsyncBitmapLoader;
import com.pangu.neusoft.tools.AsyncBitmapLoader.ImageCallBack;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ConnectListActivity extends FatherActivity {
	ListView doctorlistView;
	WebService service;
	List<DoctorList> doctorList;
	
	List<ConnectDoctor> doctors;
	SharedPreferences sp;
	Editor editor;
	
	public final class ViewHolder{  
        TextView texta;  
    }
	boolean connecting;
	
	HashMap<String,ConnectDoctor> doctormap=new HashMap<String,ConnectDoctor>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		doctorlistView=(ListView)findViewById(R.id.area_list);
		service=new WebService();
		doctorList=new ArrayList<DoctorList>();	
		
		sp= getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor=sp.edit();
		
		DBManager mgr=new DBManager(ConnectListActivity.this);
		doctors=mgr.queryConnectDoctors();
		mgr.closeDB();
		for(ConnectDoctor doctor:doctors){
			DoctorList list=new DoctorList();
			list.setId(doctor.getHospitalid()+"|"+doctor.getDepartmentid()+"|"+doctor.getDoctorid());
			list.setImageUrl(doctor.getImageUrl());
			list.setLevel(doctor.getLevel());
			list.setText(doctor.getHospitalname()+"\n"+doctor.getDepartmentname()+"\n"+doctor.getDoctorname()+"\n"+doctor.getHospitalid());
			list.setVersion(doctor.getVersion());
			doctormap.put(doctor.getDoctorid()+doctor.getDoctorname(), doctor);
			doctorList.add(list);
		}	
		showInList();
		
	}


	public void showInList(){
		 
	
		DoctorListAdapter adapter=new DoctorListAdapter(ConnectListActivity.this, doctorList);
		
		doctorlistView.setAdapter(adapter);
		doctorlistView.setClickable(true);
		doctorlistView.setFocusable(true);
		doctorlistView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
			{
				DoctorList map=(DoctorList)doctorlistView.getItemAtPosition(arg2);
				
				String doctorId=map.getId(); //获得Areaid		
				String doctorText=map.getText(); //获得AreaName
				String[] arraydoctorText=doctorText.split("\n");
				String doctorName=arraydoctorText[2];
				
				String version=map.getVersion();
				
				ConnectDoctor doctor=doctormap.get(doctorId+doctorName);
				if(doctor!=null){
				
					//记录医生信息
					editor.putString("hospitalId", doctor.getHospitalid());
					editor.putString("hospitalName", doctor.getHospitalname());
					editor.putString("departmentId", doctor.getDepartmentid());
					editor.putString("departmentName", doctor.getDepartmentname());
				
					editor.putString("doctorId", doctorId);
					editor.putString("doctorName", doctorName);
					
					editor.commit();
					startActivity(new Intent (ConnectListActivity.this, ScheduleListActivity.class));
				}
			}
		});
	}
	  
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.area_list, menu);
		return true;
	}
	
	
	

}
