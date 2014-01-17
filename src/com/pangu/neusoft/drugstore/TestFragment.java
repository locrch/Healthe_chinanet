package com.pangu.neusoft.drugstore;


import com.pangu.neusoft.healthe.R;
import com.pangu.neusoft.healthe.SetTextSizeActivity;
import com.pangu.neusoft.healthe.TabActivity2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TestFragment extends Fragment {
    private static final String TAG = "TestFragment";
    private String hello;// = "hello android";
    private String defaultHello = "default value";

    static TestFragment newInstance(String s) {
        TestFragment newFragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        
        return newFragment;

    }
    
    static TestFragment newActivity(Context context) {
        TestFragment newFragment = new TestFragment();
        
        newFragment.startActivity(new Intent(context, SetTextSizeActivity.class));
        
        
        
        return newFragment;

    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "TestFragment-----onCreate");
        Bundle args = getArguments();
        hello = args != null ? args.getString("hello") : defaultHello;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d(TAG, "TestFragment-----onCreateView");
        View view = inflater.inflate(R.layout.lay1, container, false);
        TextView viewhello = (TextView) view.findViewById(R.id.tv_hello);
        viewhello.setText(hello);
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "TestFragment-----onDestroy");
    }

}
