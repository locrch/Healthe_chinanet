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

public class DS_GetDrupShopAdapter extends ArrayAdapter<DS_GetDrupShopList>
{
	public DS_GetDrupShopAdapter(Activity activity, List<DS_GetDrupShopList> imageAndTexts)
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
        DS_GetDrupShopViewHolder viewCache;  
        if (rowView == null) {  
            LayoutInflater inflater = activity.getLayoutInflater();  
            rowView = inflater.inflate(R.layout.list_otherstore_content, null);  
            viewCache = new DS_GetDrupShopViewHolder(rowView);  
            rowView.setTag(viewCache);  
        } else {  
            viewCache = (DS_GetDrupShopViewHolder) rowView.getTag();  
        }  
        final DS_GetDrupShopList imageAndText = getItem(position);
		
        
        TextView name = viewCache.getname();  
        name.setText(imageAndText.getDrupShopName());
        
        
    	TextView address = viewCache.getaddress();  
    	address.setText(imageAndText.getAddress());
        
        TextView phone = viewCache.getphone();  
        phone.setText(imageAndText.getTelephone());
        
        
		return rowView;
	}
}
