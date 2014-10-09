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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

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
	private EditText commentText;
	private SidebarAdapter mAdapter;
	private InputMethodManager inputManager;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_image);
		liked = false;

		// This is the part that calls SwipeBack Library
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
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
		mAdapter = new SidebarAdapter(this);
		final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		final LinearLayout drawerLayout = (LinearLayout) findViewById(R.id.left_drawer_layout);
		Button addComment = (Button) findViewById(R.id.button);
		commentText = (EditText) findViewById(R.id.add_comment);
		final ListView navList = (ListView) findViewById(R.id.drawer);

		for (int i = 1; i < 15; i++) {
			mAdapter.addItem("item " + i);
		}
		navList.setAdapter(mAdapter);
		// quit soft keyboard if drawer closed
		drawer.setDrawerListener(new mDrawLayoutListener());
		// this is to quit soft keyboard if comment has been posted
		inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
		commentText.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					if (commentText.getText().toString().isEmpty()) {
						Toast.makeText(getBaseContext(),
								"Comment field empty!", Toast.LENGTH_SHORT)
								.show();
					} else {
						mAdapter.addItemInternally(commentText.getText()
								.toString());
						inputManager.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS); 
						commentText.clearFocus();
						// quit keyboard and clear focus
					}
					commentText.setText(null); // Reset editText
					return true;
				}
				return false;
			}

		});

		addComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (commentText.getText().toString().isEmpty()) {
					Toast.makeText(getBaseContext(), "Comment field empty!",
							Toast.LENGTH_SHORT).show();
				} else {
					mAdapter.addItemInternally(commentText.getText().toString());
					inputManager.hideSoftInputFromWindow(getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS); // this is to
																	// quit soft
																	// keyboard
					commentText.clearFocus();
				}
				commentText.setText(null); // Reset editText
			}

		});

		// Temporarily use share button for open/close side bar
		ImageView shareButton = (ImageView) findViewById(R.id.share);
		shareButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				drawer.openDrawer(drawerLayout);
			}

		});

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

		displayImage.setOnClickListener(new OnClickListener() {
			// onClick turns out to be much clearer than onTouch
			@Override
			public void onClick(View v) {
				if (isTextShown == false) { // If the textView doesn't exist
											// yet, create it in front of image
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
					myText.setId(100); // Should have better way... but works
										// for now
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

		bottonImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (liked == false) {
					lView = (RelativeLayout) findViewById(R.id.detailViewLLID);
					myImg = new ImageView(getApplicationContext());
					myImg.setImageResource(R.drawable.stamp);
					// http://www.makepic.com/ppic.php?act=b&style=1 stamp from
					// here
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
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
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
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
	
	private class mDrawLayoutListener implements android.support.v4.widget.DrawerLayout.DrawerListener {
        @Override
        public void onDrawerClosed(View view) {
        	inputManager.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
        }

		@Override
		public void onDrawerSlide(View arg0, float arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDrawerStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDrawerOpened(View arg0) {
			// TODO Auto-generated method stub
			
		}
    }
}
