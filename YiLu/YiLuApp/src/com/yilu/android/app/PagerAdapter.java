package com.yilu.android.app;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {	
	public PagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int pos) {
		switch(pos){
			case 0: return YiluMainListFragment.newInstance("This is Frag 1, instance 1");
			case 1: return SecondFragment.newInstance("This is Frag 2, instance 1");
			default: return SecondFragment.newInstance("This is Frag 2, instance Default"); 
			// Keep adding instances if needed
		}
	}

	@Override
	public int getCount() {
		return 2; // two fragment pages, consistent with # pages in getItem
	}	
}

