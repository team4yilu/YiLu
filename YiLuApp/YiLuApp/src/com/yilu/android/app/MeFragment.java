package com.yilu.android.app;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.avos.avoscloud.LogUtil.log;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MeFragment extends Fragment {
	
	private static final int REQUEST_TAKE_PHOTO = 1;
	private static final int REQUEST_SELECT_PHOTO = 2;
	Camera camera;	
	String mCurrentPhotoPath;
	BackendService backend;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
		View v = inflater.inflate(R.layout.frag_me_page, container, false);
		
		Button camButton = (Button) v.findViewById(R.id.tvFragMeButton);
		Button galleryButton = (Button) v.findViewById(R.id.tvFragMeButtonPick);
		
		camButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
			    	File photoFile = null;
			        try {
			            photoFile = createImageFile();
			        } catch (IOException ex) {
			            // Error occurred while creating the File
			            Log.e("Cam: ", "Fail");
			        }
			        // Continue only if the File was successfully created
			        if (photoFile != null) {
			            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
			                    Uri.fromFile(photoFile));
			            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
			            // if getActivity used, onActivityResults will be bypassed
			            System.out.println(mCurrentPhotoPath);
			        }
			    }
			}		
		});
		
		galleryButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, REQUEST_SELECT_PHOTO);
			}		
		});
		
		return v;
	}
	
	public static MeFragment newInstance(String text){
		MeFragment f = new MeFragment();
		Bundle b = new Bundle();
		b.putString("msg", text);
		f.setArguments(b);
		return f;
	}
	
	public void userInfoView(View v){
		Button LogInButton =(Button)v.findViewById(R.id.LogOut);
		OnClickListener ol=new OnClickListener(){  
            public void onClick(View v) {  
                //Backend Login, store user info
            	backend.userLogOut();
            	log.d("lzw", "Logout");
            }  
        };  
        LogInButton.setOnClickListener(ol);
	}

	private File createImageFile() throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
	    File imageFile = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    
	    mCurrentPhotoPath = imageFile.getAbsolutePath();
	    return imageFile;
	}
	
	@Override 
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		switch(requestCode) {
			case REQUEST_TAKE_PHOTO:
				if (resultCode== Activity.RESULT_OK){
		        	
		        } else {
		        	File tempFile = new File(mCurrentPhotoPath);
		        	boolean deleted = tempFile.delete();
		        	// The empty file will be created before taking the pic, it's deleted here 
		        }
			case REQUEST_SELECT_PHOTO:
				if (resultCode== Activity.RESULT_OK){
		        	
		        } else {
		        	
		        	// The empty file will be created before taking the pic, it's deleted here 
		        }
				
		}
        
    }
}
