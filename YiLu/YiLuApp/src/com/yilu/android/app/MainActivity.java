package com.yilu.android.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {// the main activity is Fragment
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // load in main activity, which is placeholder for a ViewPager
        
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager())); //setting the ViewPager
    }  
}

