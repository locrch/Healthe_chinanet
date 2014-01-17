package com.pangu.neusoft.drugstore;

import com.pangu.neusoft.healthe.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ContentFragment extends Fragment implements OnClickListener {

	public final static String CITY_LIST_POSITION = "position";
	int mCurrentPosition = -1;
	OnMenuClickedListener mCallback;
	
	// Container Activity must implement this interface
    public interface OnMenuClickedListener {
        public void onMenuButtonClicked();
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnMenuClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMenuButtonClickedListener");
        }
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(CITY_LIST_POSITION);
		}
		View view = inflater.inflate(R.layout.content_fragment_layout, null);
		
		Button bt_menu = (Button) view.findViewById(R.id.bt_menu);
		bt_menu.setOnClickListener(this);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			updateContentView(args.getInt(CITY_LIST_POSITION));
		} else if (mCurrentPosition != -1) {
			updateContentView(mCurrentPosition);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Save the current article selection in case we need to recreate the
		// fragment
		outState.putInt(CITY_LIST_POSITION, mCurrentPosition);
	}

	public void updateContentView(int position) {
		TextView article = (TextView) getActivity()
				.findViewById(R.id.tv_result);
		article.setText(MenuFragment.CITYLIST[position]);
		mCurrentPosition = position;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_menu:
			Log.i("ContentFragment", "����� �˵�");
			mCallback.onMenuButtonClicked();
			break;

		default:
			break;
		}
	}

}
