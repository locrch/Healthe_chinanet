package com.pangu.neusoft.adapters;

import com.pangu.neusoft.healthe.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DS_GetDrupShopViewHolder
{
	private View baseView;
	private TextView name,address,phone;
	
	public DS_GetDrupShopViewHolder(View baseView) {  
        this.baseView = baseView;  
    }
	
	public TextView getname()
	{
		name = (TextView)baseView.findViewById(R.id.list_otherstore_name);
		
		return name;
	}
	public void setname(TextView name)
	{
		this.name = name;
	}
	public TextView getaddress()
	{
		
		address = (TextView)baseView.findViewById(R.id.list_otherstore_address);
		
		
		return address;
	}
	public void setaddress(TextView address)
	{
		this.address = address;
	}
	public TextView getphone()
	{
		phone = (TextView)baseView.findViewById(R.id.list_otherstore_phone);
		
		return phone;
	}
	public void setphone(TextView phone)
	{
		this.phone = phone;
	}
	
}
