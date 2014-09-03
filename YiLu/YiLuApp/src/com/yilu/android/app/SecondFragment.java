package com.yilu.android.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
		View v = inflater.inflate(R.layout.frag_second_page, container, false);
		TextView tv = (TextView) v.findViewById(R.id.tvFragSecond);
		tv.setText(getArguments().getString("msg"));
		return v;
	}
	
	public static SecondFragment newInstance(String text){
		SecondFragment f = new SecondFragment();
		Bundle b = new Bundle();
		b.putString("msg", text);
		f.setArguments(b);
		return f;
	}
}
