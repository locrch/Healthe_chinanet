package com.pangu.neusoft.adapters;
import com.pangu.neusoft.healthe.R;

import android.view.View;  
import android.widget.ImageButton;
import android.widget.ImageView;  
import android.widget.TextView;  
  
public class HospitalListViewHolder {  
  
        private View baseView;  
        private TextView textView;  
        private ImageView imageView;  
        private TextView idView;
        private TextView levelView;
        private TextView gradeView;
        private TextView addressView;
        private TextView versionView;
        
        private ImageView moreInfoView;
        
        public HospitalListViewHolder(View baseView) {  
            this.baseView = baseView;  
        }  
  
        public TextView getIdView() {  
            if (idView == null) {  
            	idView = (TextView) baseView.findViewById(R.id.cache_list_id);  
            }  
            return idView;  
        }  
        
        
        public TextView getTextView() {  
            if (textView == null) {  
                textView = (TextView) baseView.findViewById(R.id.cache_list_title);  
            }  
            return textView;  
        }  
  
        public ImageView getImageView() {  
            if (imageView == null) {  
                imageView = (ImageView) baseView.findViewById(R.id.cache_list_image);  
            }  
            return imageView;  
        }  
        
        public TextView getLevelView() {  
            if (levelView == null) {  
                levelView = (TextView) baseView.findViewById(R.id.cache_list_level);  
            }  
            return levelView;  
        }  
        public TextView getGradeView() {  
            if (gradeView == null) {  
                gradeView = (TextView) baseView.findViewById(R.id.cache_list_grade);  
            }  
            return gradeView;  
        }  
        public TextView getAddressView() {  
            if (addressView == null) {  
                addressView = (TextView) baseView.findViewById(R.id.cache_list_address);  
            }  
            return addressView;  
        }  
        public TextView getVersionView() {  
            if (versionView == null) {  
                versionView = (TextView) baseView.findViewById(R.id.cache_version);  
            }  
            return versionView;  
        }  
        
        public ImageView getMoreInfoView() {  
            if (moreInfoView == null) {  
            	moreInfoView = (ImageView) baseView.findViewById(R.id.cache_list_moreinfo);  
            }  
            return moreInfoView;  
        } 
        
  
}  