package com.yilu.android.app;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class YiluPagerAdapter extends FragmentPagerAdapter {	
	
	private final String[] TITLES = { "����", "����", "Ⱥ", "����", "��", "Top New Paid",
			"Top New Free", "Trending" };
	
	public YiluPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return TITLES[position];
	}

	@Override
	public Fragment getItem(int pos) {
		switch(pos){
			case 0: return YiluMainListFragment.newInstance("Main Display. You wont see this text");
			case 1: return YiluMainListFragment.newInstance("Main Display. You wont see this text");
			//case 1: return SecondFragment.newInstance("This is placeholder for another sorting option");
			case 2: return SecondFragment.newInstance("This is placeholder for chat groups");
			case 3: return OfflineListFragment.newInstance("This is placeholder for offline pics");
			case 4: return SecondFragment.newInstance("This is placeholder for me page");
			default: return SecondFragment.newInstance("This is placeholder for me page"); 
			// Keep adding instances if needed
		}
	}

	@Override
	public int getCount() {
		return 5; // two fragment pages, consistent with # pages in getItem
	}	
}

