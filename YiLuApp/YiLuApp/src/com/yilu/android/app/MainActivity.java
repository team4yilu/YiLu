package com.yilu.android.app;

import com.viewpagerindicator.TabPageIndicator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {// the main activity is Fragment
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // load in main activity, which is placeholder for a ViewPager
        
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new YiluPagerAdapter(getSupportFragmentManager(), this)); //setting the ViewPager
        
        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        indicator.setBackgroundColor(Color.rgb(242, 242, 242));
        //setCurrentItem has to stay behind, otherwise no effect
        pager.setCurrentItem(1,false); // with that, the first fragment page to display will be the 2nd page
        
    }  
}

