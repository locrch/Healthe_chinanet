package com.pangu.neusoft.healthe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.Area;
import com.pangu.neusoft.core.models.AreaReq;
import com.pangu.neusoft.core.models.AreaResponse;



import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class AreaListActivity extends FatherActivity {
	ListView arealistView;
	WebService service;
	String xname;
	
	ArrayList<HashMap<String,String>> areaArrayList;
	//String type="";
	private ProgressDialog mProgressDialog; 
	
	public final class ViewHolder{  
        TextView texta;  
    }
	boolean connecting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setactivitytitle("选择地区");
		setContentView(R.layout.activity_list);
		arealistView=(ListView)findViewById(R.id.list);
		service=new WebService();
		areaArrayList=new ArrayList<HashMap<String,String>>();	
		
	  	mProgressDialog = new ProgressDialog(AreaListActivity.this);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
       // type=getIntent().getStringExtra("choosetype");
		xname=getIntent().getStringExtra("xname");
		
		new AsyncTask<Void, Void, Boolean>(){
		    @SuppressWarnings("deprecation")
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute();   
	            mProgressDialog.show();
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				AreaReq req=new AreaReq();
				req.setAreaID("440600");
				req.setAucode(GET.Aucode);
				SoapObject  obj = service.getAreaList(req);
				
				if(obj!=null){
					ArrayList<Area> list=new ArrayList<Area>();
					
					SoapObject areaObject=(SoapObject)obj.getProperty("areaList");
					for(int i=0;i <areaObject.getPropertyCount();i++){ 
						
						SoapObject soapChilds =(SoapObject)areaObject.getProperty(i); 
						String areaId=soapChilds.getProperty("areaId").toString();
						String areaName=soapChilds.getProperty("areaName").toString();
						Area area=new Area();
						area.setAreaId(areaId);
						area.setAreaName(areaName);
						list.add(area);
					}					
					String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
					String msg=obj.getProperty("msg").toString();//返回的信息
					
					for(Area item:list){
						HashMap<String,String> map=new HashMap<String,String>();
						map.put("areaId", item.getAreaId());
						map.put("areaName", item.getAreaName());
						areaArrayList.add(map);
					}
					//Log.e("error1", resultCode);
					//Log.e("error2", msg);
					
					return true;
				}
				else
					return false;
			}
			
			@SuppressWarnings("deprecation")
			@Override
			protected void onPostExecute(Boolean result){
				super.onPostExecute(result);
				if (!result){
					HashMap<String,String> map=new HashMap<String,String>();
					map.put("areaId", "");
					if(areaArrayList==null){
						areaArrayList=new ArrayList<HashMap<String,String>>();
						map.put("areaName", "请检查网络连接");
					}else{
						areaArrayList.clear();
						map.put("areaName", "没有数据");
					}
					areaArrayList.add(map);
					connecting=false;
					
				}else{
					connecting=true;				
				}
				showInList();
			}
			@SuppressWarnings("deprecation")
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				HashMap<String,String> map=new HashMap<String,String>();
				map.put("areaId", "");
				map.put("areaName", "取消连接。。");
				areaArrayList.add(map);
				showInList();
			}
			
		
			public void showInList(){
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
				}
				SimpleAdapter adapter=new SimpleAdapter(AreaListActivity.this,
						areaArrayList,
						R.layout.list_area_content,
						new String[]{"areaId","areaName"},
						new int[]{R.id.list_id,R.id.list_title});
				arealistView.setAdapter(adapter);
				arealistView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
					{
						
						HashMap<String,String> map=(HashMap<String,String>)arealistView.getItemAtPosition(arg2);
						String areaId=map.get("areaId"); //获得Areaid		
						String areaName=map.get("areaName"); //获得AreaName
						
						//记录地区信息。其他医院、科室、医生信息要清空
						
						SharedPreferences sp= getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
						Editor editor=sp.edit();
						editor.putString("areaId", areaId);
						editor.putString("areaName", areaName);
						
						editor.putString("hospitalId", "NG");
						editor.putString("hospitalName", "请选择医院");
				    	
				    	
						editor.putString("departmentId", "NG");
						editor.putString("departmentName", "请选择科室");
				    	
				    	
						editor.putString("doctorId", "NG");
						editor.putString("doctorName", "请选择医生");
						
						editor.commit();
						/*if(type!=null&&type.equals("userchoose")){
							
							Intent intent=getIntent();
							intent.setClass(AreaListActivity.this, HospitalChooseActivity.class);
							intent.putExtra("xname",xname);
							startActivity(intent);
							
						}else{
						*/
							startActivity(new Intent (AreaListActivity.this, HospitalListActivity.class));
						//}
					}
				});
			}
		}.execute();
	}


	  
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.area_list, menu);
		return true;
	}

}
