package com.pangu.neusoft.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.pangu.neusoft.db.DBConn;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.db.People;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
import com.pangu.neusoft.tools.DialogShow;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import com.pangu.neusoft.healthe.FatherActivity;
public class CreatePeopleActivity extends FatherActivity {

	private DBManager mgr;
	private Spinner sex;
	private Spinner licence_type;
	
	private ArrayAdapter selectadapter_sex;
	private ArrayAdapter selectadapter_lic;
	
	private EditText username_text;
	private EditText phone_text;
	private EditText address_text;
	private EditText licence_text;
	
	
	private Button submit;
	private Button back_to_people_list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_create_people);
		
		mgr=new DBManager(CreatePeopleActivity.this);
		sex=(Spinner)findViewById(R.id.people_sex);
		licence_type=(Spinner)findViewById(R.id.people_licence);
		
		selectadapter_sex = ArrayAdapter.createFromResource(this, R.array.sex, android.R.layout.simple_spinner_item);  
        //设置下拉列表的风格   
		selectadapter_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        //将adapter 添加到spinner中  
        sex.setAdapter(selectadapter_sex);
        
        selectadapter_lic = ArrayAdapter.createFromResource(this, R.array.licences_type, android.R.layout.simple_spinner_item);  
        //设置下拉列表的风格   
        selectadapter_lic.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        //将adapter 添加到spinner中  
        licence_type.setAdapter(selectadapter_lic);
        
        username_text=(EditText)findViewById(R.id.people_name);
        phone_text=(EditText)findViewById(R.id.people_phone);
        address_text=(EditText)findViewById(R.id.people_address);
        licence_text=(EditText)findViewById(R.id.people_licence_num);
        back_to_people_list=(Button)findViewById(R.id.back_to_people_list);
        back_to_people_list.setOnClickListener(back);
        submit=(Button)findViewById(R.id.create_people_btn);
        submit.setOnClickListener(click);
        
        username_text.clearFocus();
        back_to_people_list.setFocusable(true);
        back_to_people_list.setFocusableInTouchMode(true);
        back_to_people_list.requestFocus();
        
	}
	
	
	
	String username;
	String phone;
	String address;
	String licence_num;
	String sex_text;
	String licence_type_text;
	
	OnClickListener back=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(CreatePeopleActivity.this,ListPeopleActivity.class));
		}
	};
	
	OnClickListener click=new OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//DBConn dbconn =new DBConn(CreatePeopleActivity.this);
			
			username=username_text.getText().toString();
			phone=phone_text.getText().toString();
			address=address_text.getText().toString();
			licence_num=licence_text.getText().toString();
			sex_text=sex.getSelectedItem().toString();
			licence_type_text=licence_type.getSelectedItem().toString();
			String checkresult=checkData();
			if(checkresult.equals("")){
				
			     try{			    	 
			    	People people=mgr.queryByName(username);
			    	 if(people!=null){
			    		 DialogShow.showDialog(CreatePeopleActivity.this, "姓名已经存在");
			    		 mgr.closeDB();
			    	 }else{
			    		 People person=new People();
			    		 person.setUsername(username);
			    		 person.setSex(sex_text);
			    		 person.setPhone(phone);
			    		 person.setAddress(address);
			    		 person.setLicence_type(licence_type_text);
			    		 person.setLicence_num(licence_num);
			    		 mgr.add(person);
			    		 DialogShow.showDialog(CreatePeopleActivity.this, "添加成功");
			    		 mgr.closeDB();
			    		 startActivity(new Intent(CreatePeopleActivity.this,ListPeopleActivity.class));
			    	 }
			     }catch(Exception ex){
			    	 ex.printStackTrace();
			     }
			   
				
			}else{
				DialogShow.showDialog(CreatePeopleActivity.this, checkresult);
				
			}
			
			
		}
		
	};
	public String checkData(){
		String res="";
		if(username.equals("")){
			res+="姓名不能为空\n";
		}
		if(phone.equals("")){
			res+="电话不能为空\n";
		}
		if(licence_num.equals("")){
			res+="证件号码不能为空\n";
		}
		
		return res;
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_people, menu);
		return true;
	}
	@Override  
	protected void onDestroy() {  
	    super.onDestroy();  
	    if (mgr  != null) {  
	    	mgr.closeDB();  
	    }  
	}  

}

