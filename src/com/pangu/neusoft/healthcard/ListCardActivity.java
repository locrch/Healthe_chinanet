package com.pangu.neusoft.healthcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.serialization.SoapObject;

import com.pangu.neusoft.adapters.CardListAdapter;
import com.pangu.neusoft.adapters.HospitalList;
import com.pangu.neusoft.adapters.HospitalListAdapter;
import com.pangu.neusoft.core.GET;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.CardListReq;
import com.pangu.neusoft.core.models.DeleteCardReq;
import com.pangu.neusoft.core.models.HospitalReq;
import com.pangu.neusoft.core.models.MedicalCard;
import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.db.Cards;
import com.pangu.neusoft.db.DBConn;
import com.pangu.neusoft.db.DBManager;
import com.pangu.neusoft.db.People;
import com.pangu.neusoft.healthe.BookingConfirmActivity;
import com.pangu.neusoft.healthe.BookingMainActivity;
import com.pangu.neusoft.healthe.DepartmentListActivity;
import com.pangu.neusoft.healthe.HospitalListActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.healthe.R.layout;
import com.pangu.neusoft.healthe.R.menu;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.pangu.neusoft.healthe.FatherActivity;
import com.pangu.neusoft.tools.DialogShow;
public class ListCardActivity extends FatherActivity {
	private SharedPreferences sp;
	private Editor editor;
	private ProgressDialog mProgressDialog;
	private ListView list; 
	private Button create_card_btn;
	private Button card_booking_btn;
	private boolean empty=true;
	public static CheckBox temp;
	//int countnum=0;
	//private Button login_btn;
	
	public static Map<String,MedicalCard> cards=new HashMap<String,MedicalCard>();
	private LinearLayout add_card_help;
	//private RelativeLayout activity_list_card;
	private SlidingDrawer slidingDrawer1;
	private ImageView handler_btn;
	WebService service;
	String action="history";
	  List<String> arr=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setactivitytitle("选择健康卡");
		sp = getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
		editor = sp.edit();
		if(!sp.getBoolean("loginsuccess", false)){
			startActivity(new Intent(ListCardActivity.this,LoginActivity.class));
			finish();
		}
		service=new WebService();
		
		Intent intent = getIntent();		
		action=intent.getStringExtra("action");		
		setContentView(R.layout.activity_list_card);
		
		//activity_list_card = (RelativeLayout)findViewById(R.id.activity_list_card);
		add_card_help = (LinearLayout)findViewById(R.id.add_card_help);
		//slidingDrawer1=(SlidingDrawer)findViewById(R.id.slidingDrawer2);
		//handler_btn = (ImageView)findViewById(R.id.handle_btn);
		create_card_btn=(Button)findViewById(R.id.create_card_btn);
		create_card_btn.setOnClickListener(addcard);
		
		card_booking_btn=(Button)findViewById(R.id.card_booking_btn);
		card_booking_btn.setOnClickListener(card_booking);
		//login_btn=(Button)findViewById(R.id.login_btn);
	//	login_btn.setOnClickListener(login);
		
		showListView();
			
//		slidingDrawer1.setOnDrawerOpenListener(new android.widget.SlidingDrawer.OnDrawerOpenListener() {		 
//            public void onDrawerOpened() {// 当抽屉打开时执行此操作  
//            	handler_btn.setImageResource(R.drawable.button_style);
//            	
//            }  
//        }); 
//		
//		slidingDrawer1.setOnDrawerCloseListener(new android.widget.SlidingDrawer.OnDrawerCloseListener() { 
//            public void onDrawerClosed() {// 抽屉关闭时执行此操作 
//            	handler_btn.setImageResource(R.drawable.button_style);
//            } 
//        });
	}

	
	OnClickListener addcard=new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			addCard();
		}
		
	};
	
	public void addCard(){
		 if(arr.size()<5)
			 startActivity(new Intent(ListCardActivity.this,CreateCardActivity.class));
		 else
			 Toast.makeText(ListCardActivity.this,"最多添加5张健康卡！",Toast.LENGTH_SHORT).show();
	}
	
	OnClickListener card_booking=new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			Setting.state="booking";
			if(Setting.bookingdata==null||Setting.bookingdata.getUsername()==null||Setting.bookingdata.getUsername().equals("")){
		    	Intent intent = new Intent(ListCardActivity.this,BookingMainActivity.class);
				Setting.state="booking";
				startActivity(intent);
		    	return;
		    }
				BookingAction booking=new BookingAction(ListCardActivity.this);
				//设置预约基本信息
				Setting.setDefaultCardNumber(sp,editor);
				booking.confirmBooking();
			
		}
		
	};

//	OnClickListener login=new OnClickListener(){
//
//		@Override
//		public void onClick(View arg0) {
//			// TODO Auto-generated method stub
//			 
//			 startActivity(new Intent(ListCardActivity.this,LoginActivity.class));
//		}
//		
//	};
	
	
	
	public void showListView(){
		arr.clear();
		mProgressDialog = new ProgressDialog(ListCardActivity.this);   
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
				CardListReq req=new CardListReq();
				
				req.setUserName(sp.getString("username", ""));
				req.setAucode(GET.Aucode);
				SoapObject  obj = service.getCardList(req);
				Log.e("user",sp.getString("username", ""));
				if(obj!=null){
					try{
						SoapObject areaObject=(SoapObject)obj.getProperty("MedicalCards");
						
						for(int i=0;i <areaObject.getPropertyCount();i++){ 
							
							SoapObject soapChilds =(SoapObject)areaObject.getProperty(i); 
							int MedicalCardTypeID=Integer.parseInt(soapChilds.getProperty("MedicalCardTypeID").toString());
							String MedicalCardCode=soapChilds.getProperty("MedicalCardCode").toString();
							String MedicalCardPassword=soapChilds.getProperty("MedicalCardPassword").toString();
							String Owner=soapChilds.getProperty("Owner").toString();
							//String OwnerSex=soapChilds.getProperty("OwnerSex").toString();
							int OwnerAge=Integer.parseInt(soapChilds.getProperty("OwnerAge").toString());
							//String OwnerPhone=soapChilds.getProperty("OwnerPhone").toString();
							//String OwnerTel=soapChilds.getProperty("OwnerTel").toString();
							//String OwnerIDCard=soapChilds.getProperty("OwnerIDCard").toString();
							//String OwnerEmail=soapChilds.getProperty("OwnerEmail").toString();
							//String OwnerAddress=soapChilds.getProperty("OwnerAddress").toString();
							
							
							MedicalCard card=new MedicalCard();						
							card.setMedicalCardCode(MedicalCardCode);
							card.setMedicalCardPassword(MedicalCardPassword);
							card.setMedicalCardTypeID(MedicalCardTypeID);
							card.setOwner(Owner);
							//card.setOwnerAddress(OwnerAddress);
							card.setOwnerAge(OwnerAge);
							//card.setOwnerEmail(OwnerEmail);
							//card.setOwnerIDCard(OwnerIDCard);
							card.setOwnerPhone(sp.getString("username", ""));
							//map.setOwnerSex(OwnerSex);
							//card.setOwnerTel(OwnerTel);
							
							
							
							String showing="";
							if(MedicalCardTypeID==1){
								showing+=("(佛山健康卡)");
							}else{
								showing+=("(居民健康卡)");
							}
							//showing+=(MedicalCardCode+"\n");//卡号
							showing=Owner+showing;
							cards.put(showing, card);
							editor.putString("card"+i+"_"+"owner", card.getOwner());
							editor.putString("card"+i+"_"+"cardnum", card.getMedicalCardCode());
							editor.putString("card"+i+"_"+"cardtype", card.getMedicalCardTypeID()+"");
							editor.putString("card"+i+"_"+"idnumber", card.getOwnerIDCard());
							editor.putString("card"+i+"_"+"idtype", "1");
							editor.putString("card"+i+"_"+"phonenumber", card.getOwnerPhone());
							if(sp.getString("defaultcardno", "").equals("")){
								editor.putString("defaultcardno", "0");
							}
							editor.commit();
							arr.add(showing);							
							//hospitalList.add(map);
							//countnum++;
							//arr.add(showing+"1");
							//arr.add(showing+"2");
							//arr.add(showing+"3");
							
						}	
						empty=false;
					
					}catch(Exception ex){
						Log.e("error",ex.toString());
						empty=true;
						arr.add("请添加健康卡");
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
				final Timer t = new Timer();
				t.schedule(new TimerTask() {
					public void run() {
						runOnUiThread(new Runnable()    
				        {    
				            public void run()    
				            {    
				            	showInList();
				            	if( sp.getString("bookingcard", "").equals("yes")){
				            		 editor.putString("bookingcard", "no");
				            		 
				            	}
				            }    
				    
				        });   
						t.cancel(); 
					}
				}, Setting.dialogtimeout+1000);
				
				
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
			
				list=(ListView)findViewById(R.id.list_card);
		    	if(empty){
					ListAdapter adapter=new ArrayAdapter<String>(ListCardActivity.this,android.R.layout.simple_expandable_list_item_1,arr);
					list.setAdapter(adapter);
					list.setOnItemClickListener(addcard_action);
					setListViewHeightBasedOnChildren(list);
		    	}
		    	else{
					ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String,Object>>();  
			        for(String name:arr){  
			            HashMap<String, Object> tempHashMap = new HashMap<String, Object>();  
			            tempHashMap.put("cardname", name);  
			            arrayList.add(tempHashMap); 
			        }  				
					
			        CardListAdapter adapter = new CardListAdapter(ListCardActivity.this, arrayList, R.layout.list_card_content,
				                new String[]{"cardname"}, new int[]{R.id.cardname});
					
				    list.setAdapter(adapter);	
				    setListViewHeightBasedOnChildren(list);
//				    int height=sp.getInt("screen_height", 0);
//				    Log.e("height", ""+height);
//				    list.getLayoutParams().height=height/8*arr.size();
		    	}
			}
		}.execute();
	}
	
	public int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		//item的高度
		int itemHeight = 46;

		if (listAdapter == null) {
		    return;
		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {
		totalHeight += Dp2Px(getApplicationContext(),itemHeight)+listView.getDividerHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;

		listView.setLayoutParams(params);
	}
	
	OnItemClickListener addcard_action=new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			addCard();
		}
	};
	
//	OnItemClickListener choosecard=new OnItemClickListener(){
//
//		@Override
//		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			String namecard=(String)list.getItemAtPosition(arg2);
//			
//			
//			
//			if(!namecard.equals("请添加健康卡")){
//				MedicalCard card=cards.get(namecard);
//				editor.putString("owner", card.getOwner());
//				editor.putString("cardnum", card.getMedicalCardCode());
//				editor.putString("cardtype", card.getMedicalCardTypeID()+"");
//				editor.putString("idnumber", card.getOwnerIDCard());
//				editor.putString("idtype", "1");
//				editor.putString("phonenumber", card.getOwnerPhone());
//				editor.commit();
//				
//				Log.e("erxsd:",sp.getString("now_state", ""));
//				if(sp.getString("now_state", "").equals("booking")){
//					BookingAction action=new BookingAction(ListCardActivity.this);
//					action.confirmBooking();
//				}else{
//					if(action!=null&&action.equals("history")){
//						Intent intent=new Intent();
//						intent.setClass(ListCardActivity.this, HistoryViewActivity.class);
//						intent.putExtra("username", sp.getString("owner", ""));
//						startActivity(intent);
//					}else{
//						showCardDialog(namecard);
//					}
//				}
//			}
//		     
//		}
//		
//	};
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_people, menu);
		return true;
	}
	
	

//	public void showCardDialog(final String name) {
//		
//		Dialog dialog = new AlertDialog.Builder(this)
//				.setIcon(android.R.drawable.btn_star)
//				.setTitle("提示")
//				.setMessage("以下是针对健康卡的操作，请选择：")
//				.setPositiveButton("详情",
//						new android.content.DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								dialog.dismiss();
//								Intent intent=new Intent();
//								intent.setClass(ListCardActivity.this, CardInfoActivity.class);
//								startActivity(intent);
//							}
//						})
//				.setNegativeButton("取消",
//						new android.content.DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								dialog.dismiss();
//								
//								
//							}
//						})
//				.setNeutralButton("删除健康卡",
//						new android.content.DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// TODO Auto-generated method stub
//								dialog.dismiss();
//								confirmDeleteDialog(name);
//							}
//						}).create();
//
//		dialog.show();
//	}
	
	
	public void showinfo(String name){
		MedicalCard card=cards.get(name);
	
		
		editor.putString("owner", card.getOwner());
		
		if(card.getMedicalCardTypeID()==1){
			editor.putString("cardtype", "佛山健康卡");
		}else{
			editor.putString("cardtype", "居民健康卡");
		}
		editor.putString("cardnum", card.getMedicalCardCode());
		editor.putString("idnumber", card.getOwnerIDCard());
		
    	startActivity(new Intent(ListCardActivity.this,CardInfoActivity.class));
	}

	public void confirmDeleteDialog(String name) {
		MedicalCard card=cards.get(name);
		
		final String type=card.getMedicalCardTypeID()+"";
		final String num=card.getMedicalCardCode();
		
		AlertDialog.Builder builder = new Builder(ListCardActivity.this);
		builder.setMessage("确认要删除健康卡吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();						
						deleteCard(num,type);						
					}
				});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}

	//发送删除诊疗卡
	
	public void deleteCard(String medicalid,String cardtype){
		final String mediaid=medicalid;
		final String cardtypes=cardtype;
		new AsyncTask<Void, Void, Boolean>(){
			
			String msg="删除诊疗卡失败";
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
				DeleteCardReq req=new DeleteCardReq();
				req.setAucode(GET.Aucode);
				req.setMedicalCardID(mediaid);
				
				String username=sp.getString("username", ""); 
				req.setUserName(username);
				
				 SoapObject obj= service.deleteMemberCard(req);
				
				if(obj!=null){
					IsAddSuccess=obj.getProperty("IsRemoveSuccess").toString();//0000成功1111报错
					msg=obj.getProperty("msg").toString();//0000成功1111报错
					
					if(IsAddSuccess.equals("true")){
						msg="删除健康卡成功";
						
						return true;
					}else{
						return false;
					}
				}
				else{
					msg="删除健康卡失败";
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
				DialogShow.showDialog(ListCardActivity.this, msg);
				final Timer t = new Timer();
				t.schedule(new TimerTask() {
					public void run() {
						showListView();
						t.cancel(); 
					}
				}, Setting.dialogtimeout+1000);
							
			}
			@SuppressWarnings("deprecation")
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
				}
				DialogShow.showDialog(ListCardActivity.this, msg);
			}	
	}.execute();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mProgressDialog.isShowing()){
			mProgressDialog.dismiss();
		}
	}
	
}
