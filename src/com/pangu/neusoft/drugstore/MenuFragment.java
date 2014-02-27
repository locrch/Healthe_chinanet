package com.pangu.neusoft.drugstore;

import com.pangu.neusoft.healthe.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MenuFragment extends ListFragment{
	
	OnCityListSelectedListener mCallback;
	
	public static String[] CITYLIST = {"北京","上海","广州","深圳","武汉","南京","苏州","杭州","天津","福州","厦门"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, CITYLIST));
	}

    // Container Activity must implement this interface
    public interface OnCityListSelectedListener {
        public void onCityListSelected(int position);
    }
  
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnCityListSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.frist_menu) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_fragment_layout, container, false);
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		// Send the event to the host activity
        mCallback.onCityListSelected(position);
     // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
		Toast.makeText(getActivity(),
				"You have selected " + CITYLIST[position], Toast.LENGTH_SHORT).show();
	}

}
