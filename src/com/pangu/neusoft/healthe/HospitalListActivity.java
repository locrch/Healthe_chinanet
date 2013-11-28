package com.pangu.neusoft.healthe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.adapters.HospitalList;
import com.pangu.neusoft.adapters.HospitalListAdapter;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.FindHospitalListReq;
import com.pangu.neusoft.core.models.Hospital;
import com.pangu.neusoft.core.models.HospitalReq;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.color;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
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

public class HospitalListActivity extends FatherActivity {
	ListView hospitallistView;
	WebService service;
	List<HospitalList> hospitalList;
	SharedPreferences sp;
	private ProgressDialog mProgressDialog;
	Editor editor;
	private String who;
	public final class ViewHolder{  
        TextView texta;  
    }
	boolean connecting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		super.setactivitytitle("选择医院");
		hospitallistView=(ListView)findViewById(R.id.list);
		service=new WebService();
		hospitalList=new ArrayList<HospitalList>();	
		
		sp= getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor=sp.edit();
		
		mProgressDialog = new ProgressDialog(HospitalListActivity.this);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		new AsyncTask<Void, Void, Boolean>(){
		    
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute(); 
	            mProgressDialog.show();
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				
				Intent intent = getIntent();

				who = intent.getExtras().getString("who");
				
				if (who.equals("serach")){
					FindHospitalListReq req=new FindHospitalListReq();
					
					req.setHospitalName(sp.getString("serach_hosp", ""));
					
					
					req.setAucode(GET.Aucode);
					
					SoapObject  obj = service.findHospitalList(req);
					
					if(obj!=null){
						
						SoapObject areaObject=(SoapObject)obj.getProperty("hospitalList");
						for(int i=0;i <areaObject.getPropertyCount();i++){ 
							
							SoapObject soapChilds =(SoapObject)areaObject.getProperty(i); 
							String hospitalId=soapChilds.getProperty("hospitalId").toString();
							String hospitalName=soapChilds.getProperty("hospitalName").toString();
							String grade=soapChilds.getProperty("grade").toString();
							String level=soapChilds.getProperty("level").toString();
							String address=soapChilds.getProperty("address").toString();
							String version=soapChilds.getProperty("version").toString();
							
							String imageUrl;
							try{
								if(soapChilds.getProperty("pictureUrl")==null){
									imageUrl=Setting.DEFAULT_DOC_url;
									
								}else{
									imageUrl=soapChilds.getProperty("pictureUrl").toString();
								}
							}catch(Exception ex){
								imageUrl=Setting.DEFAULT_DOC_url;
							}
							
							HospitalList map=new HospitalList();						
							map.setId(hospitalId);
							map.setText(hospitalName);
							map.setImageUrl(imageUrl);
							map.setGrade(grade);
							map.setLevel(level);
							map.setAddress(address);
							map.setVersion(version);
							
							hospitalList.add(map);
						}					
						String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
						String msg=obj.getProperty("msg").toString();//返回的信息
						
						
						Log.e("error1", resultCode);
						Log.e("error2", msg);
						
						return true;
					}
					else
						return false;
					
				}
				
				HospitalReq req=new HospitalReq();
				
				req.setAreaID(sp.getString("areaId", ""));
				req.setAucode(GET.Aucode);
				SoapObject  obj = service.getHospitalList(req);
				
				if(obj!=null){
					
					SoapObject areaObject=(SoapObject)obj.getProperty("hospitalList");
					for(int i=0;i <areaObject.getPropertyCount();i++){ 
						
						SoapObject soapChilds =(SoapObject)areaObject.getProperty(i); 
						String hospitalId=soapChilds.getProperty("hospitalId").toString();
						String hospitalName=soapChilds.getProperty("hospitalName").toString();
						String grade=soapChilds.getProperty("grade").toString();
						String level=soapChilds.getProperty("level").toString();
						String address=soapChilds.getProperty("address").toString();
						String version=soapChilds.getProperty("version").toString();
						
						String imageUrl;
						try{
							if(soapChilds.getProperty("pictureUrl")==null){
								imageUrl=Setting.DEFAULT_DOC_url;
								
							}else{
								imageUrl=soapChilds.getProperty("pictureUrl").toString();
							}
						}catch(Exception ex){
							imageUrl=Setting.DEFAULT_DOC_url;
						}
						
						HospitalList map=new HospitalList();						
						map.setId(hospitalId);
						map.setText(hospitalName);
						map.setImageUrl(imageUrl);
						map.setGrade(grade);
						map.setLevel(level);
						map.setAddress(address);
						map.setVersion(version);
						
						hospitalList.add(map);
					}					
					String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
					String msg=obj.getProperty("msg").toString();//返回的信息
					
					
					Log.e("error1", resultCode);
					Log.e("error2", msg);
					
					return true;
				}
				else
					return false;
			}
			
			@Override
			protected void onPostExecute(Boolean result){
				super.onPostExecute(result);
				if (!result){
					connecting=false;
					
				}else{
					connecting=true;				
				}
				showInList();
			}
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
		
			}
			
		
			
			
			
			public void showInList(){
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
				} 
			
				HospitalListAdapter adapter=new HospitalListAdapter(HospitalListActivity.this, hospitalList);
				
				hospitallistView.setAdapter(adapter);
				hospitallistView.setClickable(true);
				hospitallistView.setFocusable(true);
				hospitallistView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
					{
						HospitalList map=(HospitalList)hospitallistView.getItemAtPosition(arg2);
						
						String hospitalId=map.getId(); //获得Areaid		
						String hospitalName=map.getText(); //获得AreaName
						String version=map.getVersion();
						
						
							//记录地区信息。其他医院、科室、医生信息要清空		
							
							editor.putString("hospitalId", hospitalId);
							editor.putString("hospitalName", hospitalName);			    	
					    	
							editor.putString("departmentId", "NG");
							editor.putString("departmentName", "请选择科室");
					    				    	
							editor.putString("doctorId", "NG");
							editor.putString("doctorName", "请选择医生");
							
							editor.commit();
							startActivity(new Intent (HospitalListActivity.this, DepartmentListActivity.class));
						
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
