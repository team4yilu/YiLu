package com.yilu.android.app;

import com.viewpagerindicator.IconPagerAdapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class YiluPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter {	

	private Activity activity;
	
	private static final int[] ICONS = new int[] {
        R.drawable.perm_group_group,
        R.drawable.perm_group_yilu,
        R.drawable.perm_group_me,
	};	
	
	public YiluPagerAdapter(FragmentManager fm, Activity activity) {
		super(fm);
		this.activity = activity;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return null;
	}

	@Override
	public Fragment getItem(int pos) {
		switch(pos){
			case 0: return SecondFragment.newInstance("This is placeholder for chat groups");
			case 1: return YiluMainListFragment.newInstance("Main Display. You wont see this text");
			case 2: {
				if(((YiLuApp)activity.getApplication()).backend.isUserLogIn())
					return MeFragment.newInstance("This is placeholder for me page");
				else
					return LogInFragment.newInstance("This is placeholder for LogIn page");
			}
			default: return MeFragment.newInstance("This is placeholder for me page"); 
			// Keep adding instances if needed
		}
	}

	@Override
	public int getCount() {
		return 3; // two fragment pages, consistent with # pages in getItem
	}

	@Override
	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		return ICONS[index];
	}	
}

