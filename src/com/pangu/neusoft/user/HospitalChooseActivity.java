package com.pangu.neusoft.user;
import com.pangu.neusoft.healthe.FatherActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.adapters.HospitalList;
import com.pangu.neusoft.adapters.HospitalListAdapter;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.Hospital;
import com.pangu.neusoft.core.models.HospitalReq;
import com.pangu.neusoft.healthe.AreaListActivity;
import com.pangu.neusoft.healthe.BookingMainActivity;
import com.pangu.neusoft.healthe.R;
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

public class HospitalChooseActivity extends FatherActivity {
	ListView hospitallistView;
	WebService service;
	List<HospitalList> hospitalList;
	SharedPreferences sp;
	private ProgressDialog mProgressDialog; 
	private String xname;
	
	public final class ViewHolder{  
        TextView texta;  
    }
	boolean connecting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		hospitallistView=(ListView)findViewById(R.id.area_list);
		service=new WebService();
		hospitalList=new ArrayList<HospitalList>();	
		sp= getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		mProgressDialog = new ProgressDialog(HospitalChooseActivity.this);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		xname = getIntent().getStringExtra("xname");
		
		new AsyncTask<Void, Void, Boolean>(){
		    
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute(); 
	            mProgressDialog.show();
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				String areaid=sp.getString("areaId", "");
				if(areaid.equals("")){
					return false;
				}else{
				
					HospitalReq req=new HospitalReq();
					
					req.setAreaID(areaid);
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
									imageUrl=Setting.default_pic_url;
								}else{
									imageUrl=soapChilds.getProperty("pictureUrl").toString();
								}
							}catch(Exception ex){
								imageUrl=Setting.default_pic_url;
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
			}
			
			@Override
			protected void onPostExecute(Boolean result){
				super.onPostExecute(result);
				if (!result){
					connecting=false;
					Intent intent=getIntent();
					intent.setClass(HospitalChooseActivity.this, AreaListActivity.class);
					intent.putExtra("choosetype", "userchoose");
					intent.putExtra("xname",xname);
					startActivity(intent);
					
				}else{
					connecting=true;	
					showInList();
				}
				
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
			
				HospitalListAdapter adapter=new HospitalListAdapter(HospitalChooseActivity.this, hospitalList);
				
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
						
						
							//记录他医院、
						Intent intent=getIntent();
						intent.setClass(HospitalChooseActivity.this, CreateCardActivity.class);
						intent.putExtra("choosehispitalid", hospitalId);
						intent.putExtra("choosehispitalname", hospitalName);
						intent.putExtra("xname",xname);
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
