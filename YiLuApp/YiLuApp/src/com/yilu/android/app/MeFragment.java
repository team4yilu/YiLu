package com.yilu.android.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.avos.avoscloud.LogUtil.log;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
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
		backend = ((YiLuApp)getActivity().getApplication()).backend;
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
				
				Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);

				Log.d("lzw", "version" + Build.VERSION.SDK_INT);
				if (Build.VERSION.SDK_INT < 19) {  
					Log.d("lzw", "setAction：Intent.ACTION_GET_CONTENT");
					photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);  
				}else {  
					Log.d("lzw", "setAction：Intent.ACTION_OPEN_DOCUMENT");
					photoPickerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);  
				}  
				photoPickerIntent.setType("image/*");
				startActivityForResult(Intent.createChooser(photoPickerIntent, "选择要上传的图片"),
						REQUEST_SELECT_PHOTO);
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
	
	private String resizeImage(Uri processedImageUri) throws IOException{
		Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),processedImageUri);
		final int raw_height = bm.getHeight();
		final int raw_width = bm.getWidth();
		Log.d("lzw", "resizeImage。 bm：H: " + raw_height + ", W:" + raw_width);	
		Bitmap resizeBmp;
		Matrix matrix = new Matrix();
		matrix.postScale(1,1);
		if(raw_height > 800 && raw_height > raw_width){ 
			matrix.postScale((float)800/raw_height,(float)800/raw_height);
			resizeBmp = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,true);
			Log.d("lzw", "resize bm：H: " + resizeBmp.getHeight() + ", W:" + resizeBmp.getWidth());		
		}
		else if(raw_width > 800 && raw_height < raw_width){
			matrix.postScale((float)800/raw_width,(float)800/raw_width);
			resizeBmp = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,true);
			Log.d("lzw", "resize bm：H: " + resizeBmp.getHeight() + ", W:" + resizeBmp.getWidth());		
		}
		else{
			resizeBmp = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,true);
			Log.d("lzw", "resize bm：H: " + resizeBmp.getHeight() + ", W:" + resizeBmp.getWidth());	
		}

		String tmpfilePath = Environment.getExternalStorageDirectory().getPath() 
				+ File.separator +"com.yilu.andriod.app" + File.separator +"tmp.jpg";
		File outFile = new File(tmpfilePath);
		FileOutputStream outStream = new FileOutputStream(outFile);
		resizeBmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
		outStream.flush();
		outStream.close();
		return tmpfilePath;
	}
	
	
	@SuppressWarnings("deprecation")
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
					Uri uri = intent.getData();

					Log.d("lzw", "Uri："+ uri.toString());
//					try {
//						Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
//						final int height = bm.getHeight();
//						final int width = bm.getWidth();
//						Log.d("lzw", "bm：H: " + height + ", W:" + width);
//					} catch (FileNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					Log.d("lzw", "Uri："+ uri.getPath()); 
					String caption = new String("test, should be textable for user");

					String tmpfile;
					try {
						Log.d("lzw", "testse");
						tmpfile = resizeImage(uri);
						backend.ImgUpload(caption, tmpfile);					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		        } else {
		        	
		        	// The empty file will be created before taking the pic, it's deleted here 
		        }
				
		}
        
    }
}
