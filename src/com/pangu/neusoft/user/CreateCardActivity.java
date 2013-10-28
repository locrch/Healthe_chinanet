package com.pangu.neusoft.user;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.AddMemberMedicalCard;
import com.pangu.neusoft.core.models.MemberReg;
import com.pangu.neusoft.db.Cards;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.healthe.AreaListActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;
import com.pangu.neusoft.tools.DialogShow;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.pangu.neusoft.healthe.FatherActivity;
public class CreateCardActivity extends FatherActivity {

	//private EditText hospital_show;
	private EditText card_number;
	//private TextView card_type;
	private Button create_card_btn;
	private TextView xname_text;
	String xname;
	SharedPreferences sp;
	Editor editor;
	Spinner cardtype;
	DBManager mgr;
	private ProgressDialog mProgressDialog; 
	String UserName;
	String Owner;
	String OwnerSex;
	String OwnerAge;
	String OwnerPhone;
	String OwnerTel;
	String OwnerIDCard;
	String OwnerAddress;
	String OwnerEmail;
	String card_type;
	int type=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_card);
		
		sp= getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor=sp.edit();
		
		
		mgr=new DBManager(CreateCardActivity.this);
		//hospital_show=(EditText)findViewById(R.id.hospital_show);
		
		card_number=(EditText)findViewById(R.id.card_number);
		//hospital_show.setOnClickListener(chooseHospital);
		cardtype=(Spinner)findViewById(R.id.card_type);
		xname_text=(TextView)findViewById(R.id.xname);
		Intent intent=getIntent();
		
		//String hid=intent.getStringExtra("choosehispitalid");
		//String hname=intent.getStringExtra("choosehispitalname");	
		mProgressDialog = new ProgressDialog(CreateCardActivity.this);   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        
		UserName=sp.getString("username", "");
		Owner=intent.getStringExtra("Owner");
		OwnerSex=intent.getStringExtra("OwnerSex");
		OwnerAge=intent.getStringExtra("OwnerAge");
		OwnerPhone=intent.getStringExtra("OwnerPhone");
		OwnerTel=intent.getStringExtra("OwnerTel");
		OwnerIDCard=intent.getStringExtra("OwnerIDCard");
		OwnerAddress=intent.getStringExtra("OwnerAddress");
		OwnerEmail=intent.getStringExtra("OwnerEmail");
		xname=intent.getStringExtra("xname");
		
		cardtype=(Spinner)findViewById(R.id.card_type);
		//hospital_show.setText(hname);
		//card_type.setText(hid);
		
		xname_text.setText(xname);
		
		create_card_btn=(Button)findViewById(R.id.create_card_btn);
		create_card_btn.setOnClickListener(addCard);
		//card_type.setFocusable(true);
		//card_type.setFocusableInTouchMode(true);
		//card_type.requestFocus();
		
		
	}

	OnClickListener chooseHospital=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//startActivity(new Intent(CreateCardActivity.this,HospitalChooseActivity.class));
			Intent intent=getIntent();
			intent.setClass(CreateCardActivity.this, AreaListActivity.class);
			intent.putExtra("choosetype", "userchoose");
			intent.putExtra("xname",xname);
			startActivity(intent);
			
		}
		
	};
	
	OnClickListener addCard=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String msg="";
			//if(card_type.getText()==null||card_type.getText().toString().equals("")){
			//	msg+="请选择医院诊疗卡";
			//}
			if(card_number.getText()==null||card_number.getText().toString().equals("")){
				msg+="请填写诊疗卡号";
			}
			if(msg.equals("")){
				card_type=cardtype.getSelectedItem().toString();
				
				if(card_type.equals("佛山健康卡"))
					type=1;
				else
					type=2;
				
				
				new AsyncTask<Void, Void, Boolean>(){
					
					String msg="添加诊疗卡失败";
					String IsAddSuccess="false";
					String MedicalCardID="";
				    @SuppressWarnings("deprecation")
					@Override  
			        protected void onPreExecute() {   
			            super.onPreExecute();   
			            mProgressDialog.show();
			        }			
					@Override
					protected Boolean doInBackground(Void... params){
						WebService service=new WebService();
						AddMemberMedicalCard cardinf=new AddMemberMedicalCard();
						cardinf.setAucode(GET.Aucode);
						cardinf.setMedicalCardCode(card_number.getText().toString());
						cardinf.setMedicalCardTypeID(type);
						cardinf.setOwner(Owner);
						cardinf.setOwnerAddress(OwnerAddress);
						cardinf.setOwnerAge(OwnerAge);
						cardinf.setOwnerEmail(OwnerEmail);
						cardinf.setOwnerIDCard(OwnerIDCard);
						cardinf.setOwnerPhone(OwnerPhone);
						cardinf.setOwnerSex(OwnerSex);
						cardinf.setOwnerTel(OwnerTel);
						cardinf.setUserName(UserName);
						cardinf.setMedicalCardPassword("");
						 SoapObject obj= service.addMemberCard(cardinf);
						
						if(obj!=null){
							IsAddSuccess=obj.getProperty("IsAddSuccess").toString();//0000成功1111报错
							MedicalCardID=obj.getProperty("MedicalCardID").toString();//0000成功1111报错
							Log.e("MedicalCardID:",MedicalCardID);
							
							msg=obj.getProperty("msg").toString();//返回的信息
							if(IsAddSuccess.equals("true")){
								return true;
							}else{
								return false;
							}
						}
						else{
							msg="添加诊疗卡失败";
							return false;
						}
					}
					@SuppressWarnings("deprecation")
					@Override
					protected void onPostExecute(Boolean result){
						super.onPostExecute(result);
						
						if(mProgressDialog.isShowing()){
							mProgressDialog.dismiss();
						}
						
						if(result){
							/*
							 * save to db
							 * */
							Cards card=new Cards();
							card.setUsername(xname_text.getText().toString());
							//card.setCardtype(card_type.getText().toString());
							card.setCardnum(card_number.getText().toString());
							
							
							card.setHospitalname(card_type);
							card.setCardtype(card_type);
							
							mgr.addCards(card);
							mgr.closeDB();
							DialogShow.showDialog(CreateCardActivity.this, "添加成功！");
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							Intent intent=getIntent();
							intent.setClass(CreateCardActivity.this, PeopleInfoActivity.class);
							intent.putExtra("username",xname);
							startActivity(intent);
						}else{
							DialogShow.showDialog(CreateCardActivity.this, msg);
						}
												
					}
					@SuppressWarnings("deprecation")
					@Override
					protected void onCancelled()
					{
						super.onCancelled();
						if(mProgressDialog.isShowing()){
							mProgressDialog.dismiss();
						}
						DialogShow.showDialog(CreateCardActivity.this, msg);
					}	
			}.execute();
				
				
				
			}else{
				DialogShow.showDialog(CreateCardActivity.this, msg);
			}
		}
		
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_card, menu);
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
