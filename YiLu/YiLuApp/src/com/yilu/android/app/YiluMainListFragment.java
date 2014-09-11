package com.yilu.android.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


import com.etsy.android.grid.StaggeredGridView;
import com.yilu.android.app.R;

import android.app.Application;
import com.yilu.android.app.BackendService;

public class YiluMainListFragment extends Fragment {	

	private boolean debug = false;
	private DataAdapter mAdapter;
	private StaggeredGridView mGridView;
    private BackendService backend;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
		View v = inflater.inflate(R.layout.frag_yilu_mainlist, container, false); // inflate this fragment instance
		mGridView = (StaggeredGridView) v.findViewById(R.id.grid_view); // this grid_view must be a member in the first_frag.xml view. otherwise Null pointer error
		if(!debug) {
			backend = ((YiLuApp)(getActivity().getApplication())).backend;
			mAdapter = new DataAdapter(getActivity(), R.layout.list_item_sample, backend.dataList, true);
			backend.GetImgList(new ImageListCallBack(){
				public void onImageListUpdated() {
					mGridView.setAdapter(mAdapter);
					Log.d("lzw", "callback setAdapter");
				}
			});
		} else {
			mAdapter = new DataAdapter(getActivity(), R.layout.list_item_sample, SampleData.generateSampleData(), true);
			// "this" won't work here, has to use getActivity()	
			mGridView.setAdapter(mAdapter); // set the StaggeredGridView adapter
		}		
		mGridView.setOnItemClickListener(new OnItemClickListener(){// Listen for clicking, and display corresponding image in detailedView
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getActivity(), "Item Clicked" + position, Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(), DetailedImage.class); // Again, "this" won't work here, because this is only an instance of fragment, not class
				// http://stackoverflow.com/questions/3913592/start-an-activity-with-a-parameter
				// start new activity with parameters
				Bundle b = new Bundle();	
				if(!debug) {
					b.putString("imgUrl", backend.dataList.get(position).imageUrl); // Should pass large image Url. That is, should store both Url on server
					b.putDouble("aspectRatio", backend.dataList.get(position).aspectRatio);
				} 
				else {
					b.putString("imgUrl", "http://test.com"); // Should pass large image Url. That is, should store both Url on server
					b.putDouble("aspectRatio", 1.2);
				}
				intent.putExtras(b);
				startActivity(intent);
			}	    	
		});
		return v;
	}

	public static YiluMainListFragment newInstance(String text){ // this class is static, can be accessed as YiluMainListFragment.newInstance. No dynamic variable should be assigned values here
		YiluMainListFragment f = new YiluMainListFragment(); // Creating a new Instance
		Bundle b = new Bundle();
		b.putString("msg", text); // dummy text as parameter
		f.setArguments(b);	
		return f; // return the created instance of fragment
	}
}
