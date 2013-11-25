package com.pangu.neusoft.adapters;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;  
import java.util.Map;
import java.util.TreeMap;








import com.pangu.neusoft.core.models.ConnectDoctor;
import com.pangu.neusoft.core.models.Schedule;
import com.pangu.neusoft.core.models.ScheduleButton;
import com.pangu.neusoft.healthcard.BookingAction;
import com.pangu.neusoft.healthcard.CreateCardActivity;
import com.pangu.neusoft.healthcard.ListCardActivity;
import com.pangu.neusoft.healthcard.LoginActivity;
import com.pangu.neusoft.healthe.DoctorDetailActivity;
import com.pangu.neusoft.healthe.HospitalDetailActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.tools.AsyncBitmapLoader;
import com.pangu.neusoft.tools.AsyncBitmapLoader.ImageCallBack;
import com.pangu.neusoft.tools.SortListByItem;
  
  
  






import android.R.integer;
import android.app.Activity;  
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;  
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.View.OnClickListener;
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;  
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;  
import android.widget.LinearLayout;
import android.widget.ListView;  
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;  
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
  
public class DoctorListAdapter extends ArrayAdapter<DoctorList> {  
  
	 	SharedPreferences sp;
	 	Editor editor;
	 	 SortListByItem sort=new SortListByItem();
	    
        private AsyncBitmapLoader asyncImageLoader;
      //  LinearLayout scheduleDetailLayout;
       // private List<Schedule> scheduleListx;
        
        public DoctorListAdapter(Activity activity, List<DoctorList> imageAndTexts) {  
            super(activity, 0, imageAndTexts);  
            
            asyncImageLoader = new AsyncBitmapLoader();  
        }  
  
        public View getView(int position, View convertView, ViewGroup parent) {  
            final Activity activity = (Activity) getContext();  
          
            
            // Inflate the views from XML  
            View rowView = convertView;  
            DoctorListViewHolder viewCache;  
            
            if (rowView == null) {  
                LayoutInflater inflater = activity.getLayoutInflater();  
                rowView = inflater.inflate(R.layout.list_doctor_content, null);  
                viewCache = new DoctorListViewHolder(rowView);  
                rowView.setTag(viewCache);  
            } else {  
                viewCache = (DoctorListViewHolder) rowView.getTag();  
            }  
            final DoctorList doctorlist = getItem(position);  
            LayoutInflater inflater = activity.getLayoutInflater();
            // Load the image and set it on the ImageView  
            String imageUrl = doctorlist.getImageUrl();  
            
            String level=doctorlist.getLevel();
            
            
            ImageView imageView = viewCache.getImageView();  
            imageView.setTag(imageUrl);  
            
            Bitmap bitmap=asyncImageLoader.loadBitmap(imageView, imageUrl, new ImageCallBack() {  
                
                @Override  
                public void imageLoad(ImageView imageView, Bitmap bitmap) {  
                    // TODO Auto-generated method stub  
                		imageView.setImageBitmap(bitmap);  
                }  
            });  
            if (bitmap == null) {  
            	if(level.contains("(男)")){
            		imageView.setImageResource(R.drawable.doc_man_img);            		
            	}else if(level.contains("(女)")){
            		imageView.setImageResource(R.drawable.doc_women_img);
            	}else {
            		imageView.setImageResource(R.drawable.doc_def_img);
            	}
            	
                  
            }else{  
            	imageView.setImageBitmap(bitmap); 
            }  
            sp = activity.getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
            editor=sp.edit();
            
            int width=sp.getInt("screen_width", 0);
            int height=sp.getInt("screen_height", 0);            
            imageView.getLayoutParams().width=width/6;
            
            imageView.getLayoutParams().height=height/6;
            //imageView.getLayoutParams().height=width/4;
            // Set the text on the TextView  
            TextView textView = viewCache.getTextView();  
            textView.setText(doctorlist.getText());  
            //textView.setTextSize(width/Setting.fontsizex);
            
            TextView idView = viewCache.getIdView();
            idView.setText(doctorlist.getId());
            
          
            
            TextView levelView = viewCache.getLevelView();  
            levelView.setText(doctorlist.getLevel());  
            //levelView.setTextSize(width/Setting.fontsizes);
            levelView.setTextColor(Color.RED);
            
            
            TextView versionView = viewCache.getVersionView();  
            versionView.setText(doctorlist.getVersion());  
            
            ImageView moreInfo = viewCache.getMoreInfoView();
            //moreInfo.getLayoutParams().width=width/Setting.fontsizey;
            //moreInfo.getLayoutParams().height=height/Setting.fontsizey;
            moreInfo.setClickable(true);
            
            //动态生成按钮
            
            //LinearLayout scheduleLayout=viewCache.getScheduleView();
            TableLayout scheduleLayout=viewCache.getScheduledays();
            scheduleLayout.removeAllViews();
            
            LinearLayout scheduleDetailLayout=viewCache.getScheduleDetailView();
            scheduleDetailLayout.removeAllViews();
            
           
            
            List<Schedule> scheduleList=doctorlist.getScheduleList();
            scheduleList=sort.sortScheduleByDay(scheduleList);
            
            if(scheduleList!=null&&scheduleList.size()>0){
            	
            	
            	//Map<String,String> maps=new LinkedHashMap<String,String>();
            
            	List<String> daylist=new ArrayList<String>();
            	
	            for(Schedule schedule:scheduleList){
	            	String day=schedule.getOutcallDate();
//	            	String number=schedule.getAvailableNum();
//	            	String now=maps.get(day);
//	            	if(now!=null){
//	            		int res=Integer.parseInt(now)+Integer.parseInt(number);
//	            		number=res+"";
//	            	}
//	            	maps.put(day, number);
	            	daylist.add(day);
	            }
	            //maps.clear();//暂时不在列表显示排班表
	            
	            
//	            TreeMap<String,String> treemap = new TreeMap<String,String>(maps);
//	            String[] alldays=new String[maps.size()];
//	            Iterator it = treemap.entrySet().iterator();
//	            int dayx=0;
//	           
//	    		  while (it.hasNext()){ 
//		    		   Map.Entry entry =(Map.Entry) it.next();
//		    		   String key = (String) entry.getKey();
//		    		   alldays[dayx]=key;
//		    		   dayx++;
//	    		  }
	            
	         
	            HashSet h  =   new  HashSet(daylist); 
	            daylist.clear(); 
	            daylist.addAll(h); 
	            daylist=sort.sortScheduleByTime(daylist);
	            
	            int totaldays=daylist.size();
	            
	            
	            int totalrows=totaldays/Setting.cols;
	            if(totalrows%Setting.cols!=0){
	            	totalrows+=1;
	            }
	            if(totalrows==0)
	            	totalrows=1;
	            
	            int count=0;
	            for(int i=0;i<totalrows;i++){
	            	TableRow row = new TableRow(getContext());
	            	row.setId(i);  
	            	for(int j=0;j<Setting.cols;j++){
	            		if(count<daylist.size()){	            		
	            			String day=daylist.get(count);
	            			
	    	            	//TextView days=new TextView(getContext());
	            			  
	 		                View days = inflater.inflate(R.layout.schedule_day, null); 
	 		                
	 		                TextView dayview=(TextView) days.findViewById(R.id.schedule_day);
	 		                
	 		                dayview.setText(day);
	    	            	
	    	            	
	    	            	final ScheduleButton newButtonInfo=new ScheduleButton();
	   		                newButtonInfo.setScheduleList(scheduleList);
	   		                newButtonInfo.setDay(day);
	   		                newButtonInfo.setScheduleDetailLayout(scheduleDetailLayout);
	    	            	
	    	            	//days.setTag(newButtonInfo);
	    	            	days.setOnClickListener(new OnClickListener(){
	 							@Override
	 							public void onClick(View arg0) {
	 								  
	 								// ScheduleButton newButtonInfo=( ScheduleButton)arg0.getTag();
	 								 LinearLayout scheduleDetailLayout=newButtonInfo.getScheduleDetailLayout();
	 								 scheduleDetailLayout.removeAllViews();
	 								 List<Schedule> scheduleList=newButtonInfo.getScheduleList();
	 								 String day=newButtonInfo.getDay();
	 								//显示当日可排班内容
	 								 
	 								
	 								//获得schedule内容。写到列表中
	 								for(Schedule schedule:scheduleList){
	 									Log.e("ScheduleList:",schedule.getDoctorName()+schedule.getDoctorId()+":"+schedule.getTimeRange()+" 可预约数："+schedule.getAvailableNum());
	 									 scheduleList=sort.sortScheduleByDay(scheduleList);
	 									if(schedule.getOutcallDate().equals(day)){
	 										Button oneButton=new Button(scheduleDetailLayout.getContext());
	 										oneButton.setPadding(10, 5, 10, 5);
	 										oneButton.setText(schedule.getTimeRange()+" 可预约数："+schedule.getAvailableNum());
	 										oneButton.setTag(schedule);
	 										oneButton.setOnClickListener(new OnClickListener(){
	 											@Override
	 											public void onClick(View arg0) {
	 												Schedule schedule=(Schedule)arg0.getTag();
	 												String doctorId=schedule.getDoctorId();
	 												String doctorName=schedule.getDoctorName();
	 												editor.putString("now_state", "booking");
	 												//记录医生信息要清空					 
	 												editor.putString("doctorId", doctorId);
	 												editor.putString("doctorName", doctorName);
	 												editor.putString("SchState", schedule.getSchState());
	 								      			editor.putString("ScheduleID", schedule.getScheduleID());
	 								      			editor.putString("RegId", schedule.getRegId());
	 								      			editor.putString("ReserveDate", schedule.getOutcallDate());
	 								      			editor.putString("ReserveTime", schedule.getTimeRange());
	 								      			editor.putString("IdType", "");
	 								      			editor.putString("IdCode", "");
													editor.commit();
	 												//Log.e("erorr",schedule.getDoctorName()+schedule.getDoctorId()+":"+schedule.getTimeRange()+" 可预约数："+schedule.getAvailableNum());
	 												if(sp.getString("username", "").equals("")){
	 													Toast.makeText(activity, "请先登录", Toast.LENGTH_SHORT);
	 													//if(!sp.getBoolean("loginsuccess", false)){
	 														activity.startActivity(new Intent(activity,LoginActivity.class));
	 													//}
	 												}else{
		 												if(!sp.getString("defaultcardno","").equals("")){
		 													
		 													editor.putString("owner", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"owner",""));
		 													editor.putString("cardnum", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"cardnum",""));
		 													editor.putString("cardtype", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"cardtype",""));
		 													editor.putString("idnumber", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"idnumber",""));
		 													editor.putString("idtype", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"idtype",""));
		 													editor.putString("phonenumber", sp.getString("card"+sp.getString("defaultcardno","")+"_"+"phonenumber",""));
		 													editor.commit();
		 													BookingAction action=new BookingAction(activity);
		 													action.confirmBooking();
		 												}else{
	 														activity.startActivity(new Intent(activity,ListCardActivity.class));
		 												}
	 												}
	 											}
	 										});
	 									
	 										scheduleDetailLayout.addView(oneButton);
	 										
	 									}
	 								}
	 							}});
	    	            	
	    	            	row.addView(days);
	    	            	count++;
	            		}
	            	}
	            	scheduleLayout.addView(row);
	            }
	            
//		            int count=0;
//		            //int rowid=-1;
//		            //String scheduletext="可预约日期：\n";
//		           // TableRow row = new TableRow(getContext());
//		            
//		            for (String key : maps.keySet()) {
//		            	//scheduletext+=key+" ";
//		            	
//		            	if(count==Setting.schedule_show_num)
//		            		break;
//		                String value= maps.get(key);
//		                
//		                LayoutInflater inflater = activity.getLayoutInflater();  
//		                View scheduleView = inflater.inflate(R.layout.schedule_day_number, null); 
//		                
//		                TextView day=(TextView) scheduleView.findViewById(R.id.schedule_day);
//		                TextView number=(TextView) scheduleView.findViewById(R.id.schedule_number);
//		                day.setText(key);
//		                
//		                number.setText(value);
//		                scheduleView.setPadding(2, 2, 2, 2);
//		                
//		                TableRow row = new TableRow(getContext());
//		                row.addView(scheduleView);
//		                row.setId(count);  
//		                scheduleLayout.addView(row);
//		                
//		                
//		                ScheduleButton newButtonInfo=new ScheduleButton();
//		                newButtonInfo.setScheduleList(scheduleList);
//		                newButtonInfo.setDay(key);
//		                newButtonInfo.setScheduleDetailLayout(scheduleDetailLayout);
//		                
//		                scheduleView.setTag(newButtonInfo);
//		                scheduleView.setOnClickListener(new OnClickListener(){
//							@Override
//							public void onClick(View arg0) {
//								  
//								 ScheduleButton newButtonInfo=( ScheduleButton)arg0.getTag();
//								 LinearLayout scheduleDetailLayout=newButtonInfo.getScheduleDetailLayout();
//								 scheduleDetailLayout.removeAllViews();
//								 List<Schedule> scheduleList=newButtonInfo.getScheduleList();
//								 String day=newButtonInfo.getDay();
//								//显示当日可排班内容
//								 
//								
//								//获得schedule内容。写到列表中
//								for(Schedule schedule:scheduleList){
//									Log.e("ScheduleList:",schedule.getDoctorName()+schedule.getDoctorId()+":"+schedule.getTimeRange()+" 可预约数："+schedule.getAvailableNum());
//									if(schedule.getOutcallDate().equals(day)){
//										Button oneButton=new Button(getContext());
//										oneButton.setPadding(10, 5, 10, 5);
//										oneButton.setText(schedule.getTimeRange()+" 可预约数："+schedule.getAvailableNum());
//										oneButton.setTag(schedule);
//										oneButton.setOnClickListener(new OnClickListener(){
//											@Override
//											public void onClick(View arg0) {
//												Schedule schedule=(Schedule)arg0.getTag();
//												Log.e("erorr",schedule.getDoctorName()+schedule.getDoctorId()+":"+schedule.getTimeRange()+" 可预约数："+schedule.getAvailableNum());
//											}
//										});
//									
//										scheduleDetailLayout.addView(oneButton);
//									}
//								}
//							}});
//		               count++;
//		                  
//		            }
		            TextView scheduleText=viewCache.getScheduleText();
		            scheduleText.setText("");//显示有排班的日期：
		           // scheduleText.setVisibility(View.GONE);
		            
	           
            } else{
            	TextView scheduleText=viewCache.getScheduleText();
	            scheduleText.setText("没有排班表");//显示有排班的日期：
	          //  scheduleDetailLayout.setVisibility(View.GONE);
            }
           
           
            
           // moreInfo.setFocusable(true);
            moreInfo.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// 显示详细内容
					if(activity.getLocalClassName().equals("DoctorListActivity")){
						Intent intent=new Intent(activity,DoctorDetailActivity.class);
						intent.putExtra("doctorId", doctorlist.getId());
						intent.putExtra("doctorVersion", doctorlist.getVersion());
						activity.startActivity(intent);
		            }else{
		            	
		            	Intent intent=new Intent(activity,DoctorDetailActivity.class);
						
						
		            			
						String doctorText=doctorlist.getText(); //获得AreaName
						//获得名称
						String[] arraydoctorText=doctorText.split("\n");
						String hospitalName=arraydoctorText[0];
						String departmentName=arraydoctorText[1];
						String doctorName=arraydoctorText[2];
						//获得ID
						String doctorIds=doctorlist.getId();
						String[] arraydoctorId=doctorIds.split("\\|");
						String hospitalId=arraydoctorId[0];
						String departmentId=arraydoctorId[1];
						String doctorId=arraydoctorId[2];
						
						SharedPreferences sp= activity.getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
						Editor editor=sp.edit();;
						
						editor.putString("hospitalId", hospitalId);
						editor.putString("hospitalName", hospitalName);
						editor.putString("departmentId", departmentId);
						editor.putString("departmentName", departmentName);
						editor.commit();
						
						intent.putExtra("doctorId", doctorId);
						intent.putExtra("doctorVersion", doctorlist.getVersion());
						activity.startActivity(intent);
		            	
		            }
					
				}
            	
            });            
            
            return rowView;  
        }  
  

     
 
}  
