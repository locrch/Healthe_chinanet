package com.pangu.neusoft.healthe;

import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.adapters.HospitalList;
import com.pangu.neusoft.adapters.HospitalListAdapter;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.Coordinates;
import com.pangu.neusoft.core.models.Hospital;
import com.pangu.neusoft.core.models.HospitalInfoReq;
import com.pangu.neusoft.core.models.HospitalReq;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.drawable;
import com.pangu.neusoft.healthe.R.id;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
import com.pangu.neusoft.tools.AsyncBitmapLoader;
import com.pangu.neusoft.tools.AsyncBitmapLoader.ImageCallBack;
import com.pangu.neusoft.tools.StringMethods;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HospitalDetailActivity extends FatherActivity {
	private String hospitalId;
	private String hospitalVersion;
	private WebService service;
	private ProgressDialog mProgressDialog; 
	Hospital hospital=new Hospital();
	SharedPreferences sp;
	Editor editor;
	String[] args;
	
	private ImageView pic;
	private TextView hospitalIdText;
	private TextView hospitalNameText;
	private TextView hospitallevelText;
	private TextView hospitalInfoText;
	private TextView hospitalAddressText;
	private TextView hospitalZipCodeText;
	private TextView hospitalTelephoneText;
	private TextView hospitalFaxText;
	private TextView hospitalWebSiteText;
	private AsyncBitmapLoader asyncImageLoader; 
	
	private ImageButton call_btn;
	private ImageButton location_btn;
	private Button booking_btn;
	
	private String latitude;
	private String longitude;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp= getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor=sp.edit();
		service=new WebService();
		setContentView(R.layout.detail_hospital);
		super.setactivitytitle("医院信息");
		hospitalId=this.getIntent().getStringExtra("hospitalId");
		hospitalVersion=this.getIntent().getStringExtra("hospitalVersion");
		
		location_btn=(ImageButton)findViewById(R.id.dingwei);
		location_btn.setOnClickListener(location);
		call_btn=(ImageButton)findViewById(R.id.hospital_tell);
		call_btn.setOnClickListener(call);
		
		hospitalIdText=(TextView)findViewById(R.id.hospital_detail_id);
		hospitalNameText=(TextView)findViewById(R.id.hospital_detail_name);
		hospitallevelText=(TextView)findViewById(R.id.hospital_detail_level_grade);
		hospitalInfoText=(TextView)findViewById(R.id.hospital_detail_intro);
		hospitalAddressText=(TextView)findViewById(R.id.hospital_detail_address);
		hospitalZipCodeText=(TextView)findViewById(R.id.hospital_detail_zipcode);
		hospitalTelephoneText=(TextView)findViewById(R.id.hospital_detail_phone);
		hospitalFaxText=(TextView)findViewById(R.id.hospital_detail_fax);
		hospitalWebSiteText=(TextView)findViewById(R.id.hospital_detail_website);
		pic=(ImageView)findViewById(R.id.hospital_detail_pictureurl);
		asyncImageLoader = new AsyncBitmapLoader();  
		
		//call_btn = (Button)findViewById(R.id.hospital_detail_call_btn);
		//call_btn.setVisibility(View.INVISIBLE);
		//location_btn = (Button)findViewById(R.id.hospital_detail_location_btn);
		//location_btn.setVisibility(View.INVISIBLE);
		
		booking_btn = (Button)findViewById(R.id.hospital_detail_booking_btn);
		
		int width=sp.getInt("screen_width", 0);
        int height=sp.getInt("screen_height", 0); 
		
		pic.getLayoutParams().width=width;
		pic.getLayoutParams().height=height/3;
		
		booking_btn.setOnClickListener(booking);
		getDataFromInternet(); 
	}

	
	OnClickListener call=new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(!hospitalTelephoneText.equals("TextView")){
				showCallView();
			}
		}
	};
	OnClickListener location=new OnClickListener(){
		@Override
		public void onClick(View v) {
				showLocationView();
		}
	};
	OnClickListener booking=new OnClickListener(){
		@Override
		public void onClick(View v) {
			//记录地区信息。其他医院、科室、医生信息要清空		
			if(hospitalId!=null&&!hospitalNameText.getText().equals("TextView")){
				editor.putString("hospitalId", hospitalId);
				editor.putString("hospitalName", hospitalNameText.getText().toString());			    	
		    	
				editor.putString("departmentId", "NG");
				editor.putString("departmentName", "请选择科室");
		    				    	
				editor.putString("doctorId", "NG");
				editor.putString("doctorName", "请选择医生");
				
				editor.commit();
				startActivity(new Intent (HospitalDetailActivity.this, DepartmentListActivity.class));
			}else{
				startActivity(new Intent (HospitalDetailActivity.this, DepartmentListActivity.class));
			}
		}
	};
	
	public void showCallView(){
		String phone=hospitalTelephoneText.getText().toString();
		List<Map<String,String>> res= StringMethods.getPhone(phone);
		args=new String[res.size()];
		int i=0;
		for(Map<String,String> a:res){
		 for (Map.Entry<String, String> entry : a.entrySet()) {
			   String key = entry.getKey().toString();
			   String value = entry.getValue().toString();
			   args[i]=value;			 
		  }
		 i++;
		}
		
		new AlertDialog.Builder(this).setTitle("电话表").setItems(
				args, new DialogInterface.OnClickListener() {  
					 
                    @Override 
                    public void onClick(DialogInterface dialog, int which) {  
                    	String phone=args[which];
						
						  Intent myIntentDial = new Intent(  
	                                Intent.ACTION_CALL,Uri.parse("tel:"+phone)  
	                        );  
	                          
	                        HospitalDetailActivity.this.startActivity(myIntentDial);  
	                      
                    }  
                }).setNegativeButton(
				"确定", null).show();

	}
	
	public void showLocationView(){
		Intent intent=getIntent();
		intent.setClass(HospitalDetailActivity.this, MapActivity.class);
		intent.putExtra("address", hospitalAddressText.getText().toString());
		intent.putExtra("latitude", latitude);
		intent.putExtra("longitude", longitude);
		
		
		startActivity(intent);
	}
	
	
	public void getDataFromInternet(){

		mProgressDialog = new ProgressDialog(HospitalDetailActivity.this);   
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
				HospitalInfoReq req=new HospitalInfoReq();
				
				req.setHospitalId(hospitalId);
				req.setAucode(GET.Aucode);
				SoapObject  obj = service.getHospitalDetail(req);
				
				if(obj!=null){					
					SoapObject areaObject=(SoapObject)obj.getProperty("hospitaldetail");					
						String hospitalId=areaObject.getProperty("hospitalId").toString();
						String hospitalName=areaObject.getProperty("hospitalName").toString();
						String grade=areaObject.getProperty("grade").toString();
						String level=areaObject.getProperty("level").toString();
						String address=areaObject.getProperty("address").toString();
						String intro= areaObject.getProperty("intro").toString();
						String zipCode=areaObject.getProperty("zipCode").toString();
						String telephone=areaObject.getProperty("telephone").toString();
						String fax=areaObject.getProperty("fax").toString();
						String website=areaObject.getProperty("website").toString();
						
						SoapObject coordinatesObj=(SoapObject)areaObject.getProperty("coordinates");
							 latitude=coordinatesObj.getProperty("latitude").toString();
							 longitude=coordinatesObj.getProperty("longitude").toString();
							
							Coordinates coordinates=new Coordinates();
							int version=0;
							try{
								coordinates.setLatitude(Double.parseDouble(latitude));
								coordinates.setLongitude(Double.parseDouble(longitude));
								version=Integer.parseInt(areaObject.getProperty("version").toString());		
							}catch(Exception ex){
								
							}
						String imageUrl;
						try{
							if(areaObject.getProperty("pictureUrl")==null){
								imageUrl=Setting.DEFAULT_DOC_url;
							}else{
								imageUrl=areaObject.getProperty("pictureUrl").toString();
							}
						}catch(Exception ex){
							imageUrl=Setting.DEFAULT_DOC_url;
						}
						
						hospital.setHospitalId(hospitalId);
						hospital.setHospitalName(hospitalName);
						hospital.setAddress(address);
						hospital.setCoordinates(coordinates);
						hospital.setFax(fax);
						hospital.setGrade(grade);
						hospital.setIntro(intro);
						hospital.setLevel(level);
						hospital.setPictureUrl(imageUrl);
						hospital.setTelephone(telephone);
						hospital.setVersion(version);
						hospital.setWebsite(website);
						hospital.setZipCode(zipCode);
						
						
					String resultCode=obj.getProperty("resultCode").toString();//0000成功1111报错
					String msg=obj.getProperty("msg").toString();//返回的信息
					return true;
				}
				else
					return false;
			}
			
			@Override
			protected void onPostExecute(Boolean result){
				super.onPostExecute(result);
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
				} 
				if (result){
					hospitalIdText.setText(hospital.getHospitalId());
					hospitalNameText.setText(hospital.getHospitalName());
					hospitallevelText.setText(hospital.getLevel()+hospital.getGrade());
					hospitalInfoText.setText(hospital.getIntro());
					hospitalAddressText.setText(hospital.getAddress());
					hospitalZipCodeText.setText(hospital.getZipCode());
					hospitalTelephoneText.setText(hospital.getTelephone());
					hospitalFaxText.setText(hospital.getFax());
					hospitalWebSiteText.setText(hospital.getWebsite());
					//pic.setText(hospital.getHospitalId());
					
					  Bitmap bitmap=asyncImageLoader.loadBitmap(pic, hospital.getPictureUrl(), new ImageCallBack() {  
			                
			                @Override  
			                public void imageLoad(ImageView imageView, Bitmap bitmap) {  
			                    // TODO Auto-generated method stub  
			                    imageView.setImageBitmap(bitmap);  
			                }  
			            });  
			            
			      
			            if (bitmap == null) {  
			                pic.setImageResource(R.drawable.booking_hosp);  
			            }else{  
			            	pic.setImageBitmap(bitmap); 
			            }  
					
				}
			}
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
			}
		}.execute();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hospital_detail, menu);
		return true;
	}



}
