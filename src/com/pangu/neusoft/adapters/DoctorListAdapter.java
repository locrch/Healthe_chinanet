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
import com.pangu.neusoft.healthe.SchedultLayout;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.tools.AsyncBitmapLoader;
import com.pangu.neusoft.tools.AsyncBitmapLoader.ImageCallBack;
import com.pangu.neusoft.tools.SortListByItem;
  
  
  






import android.R.integer;
import android.annotation.SuppressLint;
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
	 	 View nowButton;
	 	 boolean showing=false;
	    List<LinearLayout> alllayout=new ArrayList<LinearLayout>();
	    
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
            
            //imageView.getLayoutParams().width=width/4;
            //imageView.getLayoutParams().height=height/4;
            //imageView.getLayoutParams().height=width/4;
            // Set the text on the TextView  
            TextView textView = viewCache.getTextView();  
            
           
            
            if (doctorlist.getText().equals(null)||doctorlist.getText().equals(""))
			{
				textView.setVisibility(View.GONE);
			}
            //textView.setTextSize(width/Setting.fontsizex);
            TextView idView = viewCache.getIdView();
            idView.setText(doctorlist.getId());        
            TextView levelView = viewCache.getLevelView();  
            levelView.setText(doctorlist.getLevel());  
            //levelView.setTextSize(width/Setting.fontsizes);
            levelView.setTextColor(Color.RED);
            TextView versionView = viewCache.getVersionView();  
            versionView.setText(doctorlist.getVersion());  
            
            TextView hospitalText = viewCache.getHospitalText();  
            TextView departmentText = viewCache.getDepartmentText();
            String texts=doctorlist.getText();
            String names[]=texts.split("\\|");
            if(names.length==3){
            	textView.setText(names[2]);
                hospitalText.setText(names[0]);
                departmentText.setText(names[1]);
            }else{
	            textView.setText(doctorlist.getText());
	            hospitalText.setText(sp.getString("hospitalName",""));
	            departmentText.setText(sp.getString("departmentName",""));
            }
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
            TextView scheduleText=viewCache.getScheduleText();
            SchedultLayout schedule=new SchedultLayout(activity,scheduleList,scheduleDetailLayout,scheduleLayout,scheduleText);
            schedule.getView();
            
           // moreInfo.setFocusable(true);
            moreInfo.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// 显示详细内容
//					if(activity.getLocalClassName().equals("DoctorListActivity")||activity.getLocalClassName().equals("BookingMainActivity")){
						Intent intent=new Intent(activity,DoctorDetailActivity.class);
						String allid=doctorlist.getId();
						String[] ids=allid.split("\\|");
						if(ids.length==3){
							editor.putString("hospitalId", ids[0]);
							editor.putString("departmentId", ids[1]);
							editor.commit();	
							intent.putExtra("doctorId", ids[2]);
						}else{
							intent.putExtra("doctorVersion", doctorlist.getId());
						}
						
						 String texts=doctorlist.getText();
				            String names[]=texts.split("\\|");
				            if(names.length==3){
				            	editor.putString("hospitalName", names[0]);
								editor.putString("departmentName", names[1]);
								editor.commit();
				            }
		                
						
						intent.putExtra("doctorVersion", doctorlist.getVersion());
						activity.startActivity(intent);
//		            }else{
//		            	
//		            	Intent intent=new Intent(activity,DoctorDetailActivity.class);
//						
//						
//		            			
//						String doctorText=doctorlist.getText(); //获得AreaName
//						//获得名称
//						String[] arraydoctorText=doctorText.split("\n");
//						String hospitalName=arraydoctorText[0];
//						String departmentName=arraydoctorText[1];
//						String doctorName=arraydoctorText[2];
//						//获得ID
//						String doctorIds=doctorlist.getId();
//						String[] arraydoctorId=doctorIds.split("\\|");
//						String hospitalId=arraydoctorId[0];
//						String departmentId=arraydoctorId[1];
//						String doctorId=arraydoctorId[2];
//						
//						SharedPreferences sp= activity.getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
//						Editor editor=sp.edit();;
//						
//						editor.putString("hospitalId", hospitalId);
//						editor.putString("hospitalName", hospitalName);
//						editor.putString("departmentId", departmentId);
//						editor.putString("departmentName", departmentName);
//						editor.commit();
//						
//						intent.putExtra("doctorId", doctorId);
//						intent.putExtra("doctorVersion", doctorlist.getVersion());
//						activity.startActivity(intent);
//		            	
//		            }
					
				}
            	
            });            
            
            return rowView;  
        }  
  

     
 
}  
