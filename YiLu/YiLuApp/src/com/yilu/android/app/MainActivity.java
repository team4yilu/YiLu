package com.yilu.android.app;

import com.astuetz.PagerSlidingTabStrip;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {// the main activity is Fragment
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // load in main activity, which is placeholder for a ViewPager
        
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new YiluPagerAdapter(getSupportFragmentManager())); //setting the ViewPager
    
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager); // To avoid NullPointer error, getPageTitle() has to be set in PagerAdapter.java
        int color = 0xFFFF6666;
        tabs.setIndicatorColor(color);
        tabs.setIndicatorHeight(8);
        tabs.setTextSize(30);
        tabs.setTextColor(color);
    }  
}

