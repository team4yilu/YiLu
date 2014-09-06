package com.yilu.android.app;

import com.etsy.android.grid.StaggeredGridView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class OfflineListFragment extends Fragment {
	
	private DataAdapter mAdapter;
	private StaggeredGridView mGridView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
		View v = inflater.inflate(R.layout.frag_yilu_offlinelist, container, false); // inflate this fragment instance
		mGridView = (StaggeredGridView) v.findViewById(R.id.grid_view_offline); // this grid_view must be a member in the first_frag.xml view. otherwise Null pointer error
		mAdapter = new DataAdapter(getActivity(), R.layout.list_item_sample, SampleData.generateOfflineData());
		mGridView.setAdapter(mAdapter); // set the StaggeredGridView adapter

		mGridView.setOnItemClickListener(new OnItemClickListener(){// Listen for clicking, and display corresponding image in detailedView
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getActivity(), "Item Clicked" + position, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(), DetailedImage.class); // Again, "this" won't work here, because this is only an instance of fragment, not class
				Bundle b = new Bundle();	
				
				b.putString("imgUrl", "http://test.com"); // Should pass large image Url. That is, should store both Url on server
				b.putDouble("aspectRatio", 1.2);				
				intent.putExtras(b);
				startActivity(intent);
			}	    	
		});
		return v;
	}
	
	public static OfflineListFragment newInstance(String text){ // this class is static, can be accessed as YiluMainListFragment.newInstance. No dynamic variable should be assigned values here
		OfflineListFragment f = new OfflineListFragment(); // Creating a new Instance
		Bundle b = new Bundle();
		b.putString("msg", text); // dummy text as parameter
		f.setArguments(b);	
		return f; // return the created instance of fragment
	}
}
