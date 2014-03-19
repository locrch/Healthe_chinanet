package com.pangu.neusoft.drugstore;

import com.geniusgithub.lazyloaddemo.cache.ImageLoader;
import com.pangu.neusoft.CustomView.MulitPointTouchListener;
import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreOfferActivity extends FatherSonDrugActivity
{
	SharedPreferences sp;
	Editor editor;
	ImageView img;
	TextView newscontent;
	private void init()
	{
		// TODO Auto-generated method stub
		img = (ImageView)findViewById(R.id.offerdetail_dsnews_img);
		newscontent = (TextView)findViewById(R.id.offerdetail_dsnews_newscontent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_offer);
		
		init();
		
		sp = this.getSharedPreferences("SP", 0);

		editor = sp.edit();
		
		String PictureUrl = sp.getString("PictureUrl", "");
		
		String NewsContent = sp.getString("NewsContent", "");
		
		//img.setImageBitmap(getHttpBitmap(PictureUrl));
		
		ImageLoader mImageLoader = new ImageLoader(this);
    	mImageLoader.DisplayImage(PictureUrl, img, false);
		
		newscontent.setText(NewsContent);
		
		img.setOnTouchListener(new MulitPointTouchListener(img));
	}
	
	

}
