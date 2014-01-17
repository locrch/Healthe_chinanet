package com.pangu.neusoft.drugstore;

import com.pangu.neusoft.healthe.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class ShowFragment extends Fragment
{
	ImageView offer;
	
	
	static ShowFragment newInstance() {
		ShowFragment showFragment = new ShowFragment();
        
        return showFragment;

    }
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_drugstore_news, container, false);
		
		offer = (ImageView)view.findViewById(R.id.store_offer_button);
		
		offer.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(),StoreOfferActivity.class));
			}
		});
		
		return view;
	}
	
	
	
}
