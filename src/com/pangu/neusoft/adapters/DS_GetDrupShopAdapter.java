package com.pangu.neusoft.adapters;

import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import com.geniusgithub.lazyloaddemo.cache.ImageLoader;
import com.pangu.neusoft.core.WebService;
import com.pangu.neusoft.core.models.DS_GetPreferentialNewsReq;
import com.pangu.neusoft.drugstore.NewsFragment;
import com.pangu.neusoft.healthe.HospitalDetailActivity;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.tools.StringMethods;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DS_GetDrupShopAdapter extends ArrayAdapter<DS_GetDrupShopList>
{	
	String[] args;
	TextView phone;
	public DS_GetDrupShopAdapter(Activity activity, List<DS_GetDrupShopList> imageAndTexts)
	{
		super(activity, 0,imageAndTexts);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public  View getView(int position, View convertView, ViewGroup parent)
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
        name.setText("分店名："+imageAndText.getDrupShopName());
        
        
    	TextView address = viewCache.getaddress();  
    	address.setText("地址："+imageAndText.getAddress());
        
        phone = viewCache.getphone();  
        phone.setText("联系电话："+imageAndText.getTelephone());
        
        phone.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				showCallView(imageAndText.getTelephone());
			}
		});
        
		return rowView;
	}
	
	public void showCallView(String num){
		final Activity activity = (Activity) getContext();
		String phone=num;
		List<Map<String,String>> res= StringMethods.getPhone(phone);
		args=new String[res.size()];
		int i=0;
		for(Map<String,String> a:res){
		 for (Map.Entry<String, String> entry : a.entrySet()) {
			   String key = entry.getKey().toString();
			   String value = entry.getValue().toString();
			   args[i]=value;			 
		  }
		 i++;
		}
		
		new AlertDialog.Builder(activity).setTitle("点击拨打").setItems(
				args, new DialogInterface.OnClickListener() {  
					 
                    @Override 
                    public void onClick(DialogInterface dialog, int which) {  
                    	String phone=args[which];
						
						  Intent myIntentDial = new Intent(  
	                                Intent.ACTION_CALL,Uri.parse("tel:"+phone)  
	                        );  
	                          
	                        activity.startActivity(myIntentDial);  
	                      
                    }  
                }).setNegativeButton(
				"确定", null).show();

	}
}
