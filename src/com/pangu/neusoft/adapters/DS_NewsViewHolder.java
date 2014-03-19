package com.pangu.neusoft.adapters;

import com.pangu.neusoft.healthe.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DS_NewsViewHolder
{
	private View baseView;
	private TextView title,expireTime;
	
	public DS_NewsViewHolder(View baseView) {  
        this.baseView = baseView;  
    }
	
	public TextView getTitle()
	{
		title = (TextView)baseView.findViewById(R.id.list_otherstore_3);
		
		return title;
	}
	public void setTitle(TextView title)
	{
		this.title = title;
	}
	public TextView getExpireTime()
	{
		
			expireTime = (TextView)baseView.findViewById(R.id.list_otherstore_1);
		
		
		return expireTime;
	}
	public void setExpireTime(TextView expireTime)
	{
		this.expireTime = expireTime;
	}
	public ImageView getImg()
	{
		img = (ImageView)baseView.findViewById(R.id.list_dsnews_img);
		
		return img;
	}
	public void setImg(ImageView img)
	{
		this.img = img;
	}
	private ImageView img;
}
