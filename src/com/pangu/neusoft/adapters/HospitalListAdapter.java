package com.pangu.neusoft.adapters;



import java.util.List;  





import com.pangu.neusoft.healthe.HospitalDetailActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.Setting;
import com.pangu.neusoft.healthe.R.color;
import com.pangu.neusoft.tools.AsyncBitmapLoader;
import com.pangu.neusoft.tools.AsyncBitmapLoader.ImageCallBack;
  
  
  



import android.R.integer;
import android.app.Activity;  
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;  
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.View.OnClickListener;
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;  
import android.widget.ImageButton;
import android.widget.ImageView;  
import android.widget.ListView;  
import android.widget.TextView;  
  
public class HospitalListAdapter extends ArrayAdapter<HospitalList> {  
		
          
        private AsyncBitmapLoader asyncImageLoader;  
        
        
        public HospitalListAdapter(Activity activity, List<HospitalList> imageAndTexts) {  
            super(activity, 0, imageAndTexts);  
            
            asyncImageLoader = new AsyncBitmapLoader();  
        }  
  
        public View getView(int position, View convertView, ViewGroup parent) {  
            final Activity activity = (Activity) getContext();  
          
            
            // Inflate the views from XML  
            View rowView = convertView;  
            HospitalListViewHolder viewCache;  
            if (rowView == null) {  
                LayoutInflater inflater = activity.getLayoutInflater();  
                rowView = inflater.inflate(R.layout.list_hospital_content, null);  
                viewCache = new HospitalListViewHolder(rowView);  
                rowView.setTag(viewCache);  
            } else {  
                viewCache = (HospitalListViewHolder) rowView.getTag();  
            }  
            final HospitalList imageAndText = getItem(position);  
            
            
            //设置列表隔行换色
            if (position%2!=0)
			{
            	//灰色
            	rowView.setBackgroundColor(Color.LTGRAY);
			}
            else {
            	rowView.setBackgroundColor(Color.WHITE);
			}
            
            
            // Load the image and set it on the ImageView  
            String imageUrl = imageAndText.getImageUrl();  
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
                imageView.setImageResource(R.drawable.booking_hosp);  
            }else{  
            	imageView.setImageBitmap(bitmap); 
            }  
            SharedPreferences sp = activity.getSharedPreferences(Setting.spfile, Context.MODE_PRIVATE);
            int width=sp.getInt("screen_width", 0);
            int height=sp.getInt("screen_height", 0);            
            imageView.getLayoutParams().width=width/6;
            imageView.getLayoutParams().height=height/6;
            //imageView.getLayoutParams().height=width/4;
            // Set the text on the TextView  
            TextView textView = viewCache.getTextView();  
            textView.setText(imageAndText.getText());  
            textView.setTextSize(width/Setting.fontsizex);
            
            TextView idView = viewCache.getIdView();
            idView.setText(imageAndText.getId());
            
            
            TextView levelView = viewCache.getLevelView();  
            levelView.setText(imageAndText.getLevel());  
            levelView.setTextSize(width/Setting.fontsizes);
            levelView.setTextColor(Color.RED);
            
            TextView gradeView = viewCache.getGradeView();  
            gradeView.setText(imageAndText.getGrade());   
            gradeView.setTextSize(width/Setting.fontsizes);
            gradeView.setTextColor(Color.RED);
            
            TextView addressView = viewCache.getAddressView();  
            addressView.setText("地址："+imageAndText.getAddress());  
            addressView.setTextSize(width/Setting.fontsizes);
            addressView.setTextColor(Color.GRAY);
            
            TextView versionView = viewCache.getVersionView();  
            versionView.setText(imageAndText.getVersion());  
            
            ImageView moreInfo = viewCache.getMoreInfoView();
            moreInfo.getLayoutParams().width=width/Setting.fontsizey;
            moreInfo.getLayoutParams().height=height/Setting.fontsizey;
            moreInfo.setClickable(true);
           // moreInfo.setFocusable(true);
            moreInfo.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// 显示详细内容
					if(activity.getLocalClassName().equals("HospitalListActivity")){
						Intent intent=new Intent(activity,HospitalDetailActivity.class);
						intent.putExtra("hospitalId", imageAndText.getId());
						intent.putExtra("hospitalVersion", imageAndText.getVersion());
						activity.startActivity(intent);
		            }
				}
            	
            });            
            
            return rowView;  
        }  
  
     
        
}  
