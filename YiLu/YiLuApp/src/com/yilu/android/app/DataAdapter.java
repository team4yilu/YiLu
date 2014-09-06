package com.yilu.android.app;

import java.io.File;
import java.util.List;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.yilu.android.app.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class DataAdapter extends ArrayAdapter<Data> {

	Activity activity;
	int resource;
	List<Data> datas;
	private boolean online;	

	public DataAdapter(Activity activity, int resource, List<Data> objects, boolean online) {
		super(activity, resource, objects);
		this.activity = activity;
		this.resource = resource;
		this.datas = objects;
		this.online = online;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final DealHolder holder;

		if (row == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			row = inflater.inflate(resource, parent, false);
			
			holder = new DealHolder();
			holder.image = (DynamicHeightImageView) row.findViewById(R.id.image);
			holder.yilubutton = (ImageView) row.findViewById(R.id.yilubutton);
			holder.avatar = (ImageView) row.findViewById(R.id.avatar);
			holder.yilucount = (TextView) row.findViewById(R.id.yilucount);
			// holder.title = (TextView)row.findViewById(R.id.title);
			// holder.description = (TextView)row.findViewById(R.id.description);

			row.setTag(holder);
		}
		else {
			holder = (DealHolder) row.getTag();
		}

		final Data data = datas.get(position);
		
		if(online) {
			Picasso.with(this.getContext())
			.load(data.imageUrl)
			.into(holder.image);	// the execution of picasso is not in sequential with the rest of code. So trying to read img dimens using Target() then set imgView will never work		
		} 
		else {
			File imgFile = new File(data.imageUrl);
			Picasso.with(this.getContext()).load(imgFile).into(holder.image);
			// have to first convert url into file, otherwise Picasso won't load
		}
	
		holder.image.setHeightRatio(data.aspectRatio); // aspectRatio is stored as double along with the image on server
		holder.yilubutton.setBackgroundResource(R.drawable.rect);
		holder.avatar.setImageResource(R.drawable.avatar);
		holder.avatar.setColorFilter(android.graphics.Color.GRAY, Mode.OVERLAY);
		holder.yilucount.setText(Integer.toString(data.yilucount));
		// Each of the data has to be filled in BackendService, otherwise lead to NullPointer error in DataAdapter
		// holder.title.setText(data.title);
		// holder.description.setText(data.description);
		return row; 
	}

	static class DealHolder {
		DynamicHeightImageView image;
		ImageView avatar;
		ImageView yilubutton;
		TextView yilucount;
		TextView title;
		TextView description;
	}
}
