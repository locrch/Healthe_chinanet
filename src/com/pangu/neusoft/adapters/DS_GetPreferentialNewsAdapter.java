package com.pangu.neusoft.adapters;

import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.geniusgithub.lazyloaddemo.cache.ImageLoader;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.DS_GetPreferentialNewsReq;
import com.pangu.neusoft.drugstore.NewsFragment;
import com.pangu.neusoft.healthe.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DS_GetPreferentialNewsAdapter extends ArrayAdapter<DS_GetPreferentialNewsList>
{
	public DS_GetPreferentialNewsAdapter(Activity activity, List<DS_GetPreferentialNewsList> imageAndTexts)
	{
		super(activity, 0,imageAndTexts);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		final Activity activity = (Activity) getContext();
		
		
		View rowView = convertView;  
        DS_NewsViewHolder viewCache;  
        if (rowView == null) {  
            LayoutInflater inflater = activity.getLayoutInflater();  
            rowView = inflater.inflate(R.layout.list_dsnews_content, null);  
            viewCache = new DS_NewsViewHolder(rowView);  
            rowView.setTag(viewCache);  
        } else {  
            viewCache = (DS_NewsViewHolder) rowView.getTag();  
        }  
        final DS_GetPreferentialNewsList imageAndText = getItem(position);
		
        String imageUrl = imageAndText.getPictureUrl();
		ImageView imageView = viewCache.getImg();
        imageView.setTag(imageUrl);
        
        ImageLoader mImageLoader = new ImageLoader(activity);
    	mImageLoader.DisplayImage(imageUrl, imageView, false);
    	
    	TextView Title = viewCache.getTitle();  
        Title.setText(imageAndText.getTitle());
        
        TextView ExpireTime = viewCache.getExpireTime();  
        ExpireTime.setText(imageAndText.getExpireTime());
        
        
		return rowView;
	}
}
