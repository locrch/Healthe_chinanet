package com.pangu.neusoft.healthe;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.adapters.DepartmentListAdapter;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.Department;
import com.pangu.neusoft.core.models.DepartmentReq;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
import com.pangu.neusoft.tools.GB2Alpha;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.AsyncQueryHandler;
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

public class DepartmentListActivity extends FatherActivity {
	ListView departmentlistView;
	WebService service;
	List<Department> departmentList;
	SharedPreferences sp;
	private ProgressDialog mProgressDialog; 
	TextView infos_text;
	
	Editor editor;
	GB2Alpha getAlpha = new GB2Alpha();
	public final class ViewHolder{  
        TextView texta;  
    }
	boolean connecting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		super.setactivitytitle("选择科室");
		departmentlistView=(ListView)findViewById(R.id.list);
		service=new WebService();
		departmentList=new ArrayList<Department>();	
		  
		
		sp= getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor=sp.edit();
		
		infos_text=(TextView)findViewById(R.id.infos_text);
		infos_text.setText(sp.getString("hospitalName", ""));
	
		 
		mProgressDialog = new ProgressDialog(DepartmentListActivity.this);   
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
				DepartmentReq req=new DepartmentReq();
				
				req.setHospitalId(sp.getString("hospitalId", ""));
				req.setAucode(GET.Aucode);
				SoapObject  obj = service.getDepartmentList(req);
				
				if(obj!=null){
					
					SoapObject areaObject=(SoapObject)obj.getProperty("departmentList");
					for(int i=0;i <areaObject.getPropertyCount();i++){ 
						
						SoapObject soapChilds =(SoapObject)areaObject.getProperty(i); 
						String departmentId=soapChilds.getProperty("departmentId").toString();
						String departmentName=soapChilds.getProperty("departmentName").toString();
						String version=soapChilds.getProperty("version").toString();
						
						
						
						Department map=new Department();						
						map.setDepartmentId(departmentId);
						map.setDepartmentName(departmentName);
						map.setVersion(version);
						map.setSortKey(getAlpha.getFirstCapitalWork(departmentName));
						
						departmentList.add(map);
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
				int width=sp.getInt("screen_width", 0);
				int height=sp.getInt("screen_height", 0);
				
				String hospitalId=sp.getString("hospitalId", "");
				DepartmentListAdapter adapter=new DepartmentListAdapter(DepartmentListActivity.this, departmentList,width,height,hospitalId);
				
				departmentlistView.setAdapter(adapter);
				departmentlistView.setClickable(true);
				departmentlistView.setFocusable(true);
				departmentlistView.setOnItemClickListener(new OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
					{
						Department map=(Department)departmentlistView.getItemAtPosition(arg2);
						
						String departmentId=map.getDepartmentId(); //获得Areaid		
						String departmentName=map.getDepartmentName(); //获得AreaName
						String version=map.getVersion();
						
						
							//记录地区信息。其他医院、科室、医生信息要清空		
							
							editor.putString("departmentId", departmentId);
							editor.putString("departmentName", departmentName);			    	
					    				    	
							editor.putString("doctorId", "NG");
							editor.putString("doctorName", "请选择医生");
							
							editor.commit();
							
							Intent intent = new Intent (DepartmentListActivity.this, DoctorListActivity.class);
							
							intent.putExtra("who", "dep");
							
							startActivity(intent);
						
							
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
