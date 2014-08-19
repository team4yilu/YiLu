package com.yilu.android.app;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.database.DataSetObservable;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailedImage extends Activity {

	private boolean isTextShown = false;
	RelativeLayout lView;
	DynamicHeightImageView fulImg;
	TextView myText;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_image);
		Bundle b = getIntent().getExtras();
		String imgUrl = b.getString("imgUrl");
		Double aspectRatio = b.getDouble("aspectRatio");
		
		DynamicHeightImageView displayImage = (DynamicHeightImageView) findViewById(R.id.fullimg);
		// in xml, should use DynamicHeightImageView tag accordingly, if use ImageView, end up error

		Picasso.with(this)
		.load(imgUrl)
		.into(displayImage); // pass the url from parent activity, so load it here
		// In practice, should store imgUrls for small and large image. So in list display small image
		// But passes large image Url here for display.
		
		displayImage.setHeightRatio(aspectRatio);
		ImageView otherImage = (ImageView) findViewById(R.id.yilubutton1);
		TextView yilucount = (TextView) findViewById(R.id.yilucount1);
		yilucount.setText(Integer.toString(123));
		otherImage.setImageResource(R.drawable.rect);
		otherImage = (ImageView) findViewById(R.id.avatar1);
		otherImage.setImageResource(R.drawable.avatar);		
		
		// Comments block
		TextView comments = (TextView) findViewById(R.id.comId1);
		comments.setText("This is nice !");
		comments = (TextView) findViewById(R.id.comId2);
		comments.setText("This is nice, I cannot help to leave great comments ! Please keep posting nice pictures of yours! I'm a big fan of every single photo you post. You are a rockStar! This is nice, I cannot help to leave great comments ! Please keep posting nice pictures of yours! I'm a big fan of every single photo you post. You are a rockStar! This is nice, I cannot help to leave great comments ! Please keep posting nice pictures of yours! I'm a big fan of every single photo you post. You are a rockStar!");
		comments = (TextView) findViewById(R.id.comId3);
		comments.setText("路过路过");
			
		displayImage.setOnClickListener( new OnClickListener() {
			// onClick turns out to be much clearer than onTouch

			@Override
			public void onClick(View v) {
				if(isTextShown == false) { // If the textView doesn't exist yet, create it in front of image
					lView = (RelativeLayout) findViewById(R.id.detailViewLLID);
					fulImg = (DynamicHeightImageView) findViewById(R.id.fullimg);
					myText = new TextView(getApplicationContext());
					myText.setBackgroundColor(android.graphics.Color.WHITE);
					myText.setWidth(fulImg.getWidth());
					myText.setHeight(fulImg.getHeight());
					myText.setText("This is the Text that is going to be entered by the author. This is the Text that is going to be entered by the author. This is the Text that is going to be entered by the author.");
					// set alignment http://stackoverflow.com/questions/4638832/how-to-programmatically-set-the-layout-align-parent-right-attribute-of-a-button
					myText.setGravity(Gravity.CENTER);
					myText.setId(100); // Should have better way... but works for now
					lView.addView(myText);
					isTextShown = true;
				} 
				else { // If the textView exists, remove it upon click
					lView = (RelativeLayout) findViewById(R.id.detailViewLLID);
					myText = new TextView(getApplicationContext());
					lView.removeView(findViewById(100));
					isTextShown = false;
				}
			}			
		});			
	}
}
