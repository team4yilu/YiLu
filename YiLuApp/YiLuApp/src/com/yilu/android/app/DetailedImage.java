package com.yilu.android.app;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.DataSetObservable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.widget.DrawerLayout;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class DetailedImage extends SwipeBackActivity implements
		View.OnClickListener {

	private boolean isTextShown = false;
	RelativeLayout lView;
	DynamicHeightImageView fulImg;
	TextView myText;
	ImageView myImg;
	boolean liked;

	private static final int VIBRATE_DURATION = 20;
	private SwipeBackLayout mSwipeBackLayout;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_image);
		liked = false;

		// This is the part that calls SwipeBack Library 
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);
		mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
			@Override
			public void onScrollStateChange(int state, float scrollPercent) {
			}
			@Override
			public void onEdgeTouch(int edgeFlag) {
				vibrate(VIBRATE_DURATION);
			}
			@Override
			public void onScrollOverThreshold() {
				vibrate(VIBRATE_DURATION);
			}
		});
		
		// This is the part for side bar to display comments
		SidebarAdapter mAdapter = new SidebarAdapter(this);
	     final DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
	     final ListView navList = (ListView) findViewById(R.id.drawer);
	     
	     for (int i = 1; i < 15; i++) {
	         mAdapter.addItem("item " + i);         
	     }
	     mAdapter.addCommentItem("dummy");
	     navList.setAdapter(mAdapter);
		
		// Gets input from the calling activity
		Bundle b = getIntent().getExtras();
		String imgUrl = b.getString("imgUrl");
		Double aspectRatio = b.getDouble("aspectRatio");
		DynamicHeightImageView displayImage = (DynamicHeightImageView) findViewById(R.id.fullimg);
		// in xml, should use DynamicHeightImageView tag accordingly, if use
		// ImageView, end up error

		Picasso.with(this).load(imgUrl).into(displayImage); // pass the url from
															// parent activity,
															// so load it here
		// In practice, should store imgUrls for small and large image. So in
		// list display small image
		// But passes large image Url here for display.

		displayImage.setHeightRatio(aspectRatio);
		
		// The line that contains avatar, heart, share and liked
		// avatar
		ImageView avatarImage = (ImageView) findViewById(R.id.avatar1);
		avatarImage.setImageResource(R.drawable.avatar);
		// yilu button
		ImageView bottonImage = (ImageView) findViewById(R.id.yilubutton1);
		TextView yilucount = (TextView) findViewById(R.id.yilucount1);
		yilucount.setText(Integer.toString(123));
		bottonImage.setImageResource(R.drawable.rect);
		// username
		TextView nameText = (TextView) findViewById(R.id.name);
		nameText.setText("小娇焦");
		// level
		TextView levelText = (TextView) findViewById(R.id.level);
		levelText.setText("万人瞩目");

//		// Comments block
//		TextView comments = (TextView) findViewById(R.id.comId1);
//		comments.setText("This is nice !");
//		comments = (TextView) findViewById(R.id.comId2);
//		comments.setText("This is nice, I cannot help to leave great comments ! Please keep posting nice pictures of yours! I'm a big fan of every single photo you post. You are a rockStar! This is nice, I cannot help to leave great comments ! Please keep posting nice pictures of yours! I'm a big fan of every single photo you post. You are a rockStar! This is nice, I cannot help to leave great comments ! Please keep posting nice pictures of yours! I'm a big fan of every single photo you post. You are a rockStar!");
//		comments = (TextView) findViewById(R.id.comId3);
//		comments.setText("路过路过");

		displayImage.setOnClickListener(new OnClickListener() {
			// onClick turns out to be much clearer than onTouch
			@Override
			public void onClick(View v) {
				if (isTextShown == false) { // If the textView doesn't exist yet, create it in front of image
					lView = (RelativeLayout) findViewById(R.id.detailViewLLID);
					fulImg = (DynamicHeightImageView) findViewById(R.id.fullimg);
					myText = new TextView(getApplicationContext());
					myText.setBackgroundColor(android.graphics.Color.WHITE);
					myText.setWidth(fulImg.getWidth());
					myText.setHeight(fulImg.getHeight());
					myText.setText("This is the Text that is going to be entered by the author. This is the Text that is going to be entered by the author. This is the Text that is going to be entered by the author.");
					// set alignment
					// http://stackoverflow.com/questions/4638832/how-to-programmatically-set-the-layout-align-parent-right-attribute-of-a-button
					myText.setGravity(Gravity.CENTER);
					myText.setId(100); // Should have better way... but works for now
					lView.addView(myText);
					isTextShown = true;
				} else { // If the textView exists, remove it upon click
					lView = (RelativeLayout) findViewById(R.id.detailViewLLID);
					myText = new TextView(getApplicationContext());
					lView.removeView(findViewById(100));
					isTextShown = false;
				}
			}
		});
		
		bottonImage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(liked == false) {
					lView = (RelativeLayout) findViewById(R.id.detailViewLLID);
					myImg = new ImageView(getApplicationContext());
					myImg.setImageResource(R.drawable.stamp);
					// http://www.makepic.com/ppic.php?act=b&style=1 stamp from here
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.fullimg);
					params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.fullimg);
					myImg.setLayoutParams(params);
					lView.addView(myImg);
					System.out.println("Yilu clicked");
					liked = true;
				}
			}			
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		restoreTrackingMode();
	}

	private void restoreTrackingMode() {
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);
	}

	private void vibrate(long duration) {
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long[] pattern = { 0, duration };
		vibrator.vibrate(pattern, -1);
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}
}
