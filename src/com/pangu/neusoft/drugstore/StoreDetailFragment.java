package com.pangu.neusoft.drugstore;

import com.pangu.neusoft.healthe.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StoreDetailFragment extends Fragment
{
	static StoreDetailFragment newInstance() {
		StoreDetailFragment storeDetailFragment = new StoreDetailFragment();
        
        return storeDetailFragment;

    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_store_detail, container, false);
		return view;
	}
}
