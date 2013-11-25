package com.pangu.neusoft.adapters;
import com.pangu.neusoft.healthe.R;

import android.view.View;  
import android.widget.ImageButton;
import android.widget.ImageView;  
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;  
  
public class DoctorListViewHolder {  
  
        private View baseView;  
        private TextView textView;  
        private ImageView imageView;  
        private TextView idView;
        private TextView levelView;
        private LinearLayout scheduleView;
        private LinearLayout scheduleDetailView;
        private TextView scheduletext;
        private TextView versionView;
        private TableLayout scheduledays;
        
        private ImageView moreInfoView;
        
        public DoctorListViewHolder(View baseView) {  
            this.baseView = baseView;  
        }  
  
        public TextView getIdView() {  
            if (idView == null) {  
            	idView = (TextView) baseView.findViewById(R.id.doctor_list_id);  
            }  
            return idView;  
        }  
        
        public TextView getScheduleText(){
        	if (scheduletext == null) {  
        		scheduletext = (TextView) baseView.findViewById(R.id.schedule_text);  
            }  
            return scheduletext;
        }
        
        public TextView getTextView() {  
            if (textView == null) {  
                textView = (TextView) baseView.findViewById(R.id.doctor_list_title);  
            }  
            return textView;  
        }  
  
        public ImageView getImageView() {  
            if (imageView == null) {  
                imageView = (ImageView) baseView.findViewById(R.id.doctor_list_image);  
            }  
            return imageView;  
        }  
        
        public TextView getLevelView() {  
            if (levelView == null) {  
                levelView = (TextView) baseView.findViewById(R.id.doctor_list_level);  
            }  
            return levelView;  
        }  
     
        public TextView getVersionView() {  
            if (versionView == null) {  
                versionView = (TextView) baseView.findViewById(R.id.doctor_version);  
            }  
            return versionView;  
        }  
        
        public ImageView getMoreInfoView() {  
            if (moreInfoView == null) {  
            	moreInfoView = (ImageView) baseView.findViewById(R.id.doctor_list_moreinfo);  
            }  
            return moreInfoView;  
        } 
        public LinearLayout getScheduleView() {  
            if (scheduleView == null) {  
            	scheduleView = (LinearLayout) baseView.findViewById(R.id.schedule_list_layout);  
            }  
            return scheduleView;  
        } 
        
        public LinearLayout getScheduleDetailView() {  
            if (scheduleDetailView == null) {  
            	scheduleDetailView = (LinearLayout) baseView.findViewById(R.id.scheduledetail);  
            }  
            return scheduleDetailView;  
        } 
        
        public TableLayout getScheduledays(){
        	if (scheduledays == null) {  
        		scheduledays = (TableLayout) baseView.findViewById(R.id.scheduledays);  
            }  
            return scheduledays;
        	
        }
  
}  