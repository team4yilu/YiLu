package com.yilu.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.etsy.android.grid.StaggeredGridView;
import com.yilu.android.app.R;
import com.yilu.android.app.BackendService;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements AbsListView.OnItemClickListener {

    private StaggeredGridView mGridView;
    private DataAdapter mAdapter;
    private BackendService backend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("YiLu Main Layout");

 /*       AVOSCloud.initialize(this, "i6xp9je0wdny22t0k5m13564nh1cloby9oih6xg29s9tpy96", 
        		"pbfveozcasgmn37uw4yne2k6892dio4z32g2o8wb6y26lcb9");
        AVObject testObject = new AVObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();*/
        
        backend = new BackendService(this);
        mAdapter = new DataAdapter(this, R.layout.list_item_sample, backend.dataList);
        backend.GetImgList(new ImageListCallBack(){
    		    public void onImageListUpdated() {
    		        mGridView.setAdapter(mAdapter);
    		        Log.d("lzw", "callback setAdapter");
    		    }
        });
//        mAdapter = new DataAdapter(this, R.layout.list_item_sample, SampleData.generateSampleData());
        // Sample data didn't work while backend works. I believe the links are outdated
		
        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);

        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
//    	String userName = new String("test");
//    	String pwd = new String ("test");
//    	String mail = new String("test@mail.com");
//    	String phone = new String("111111111111");
//    	backend.UserSignUp(userName, pwd, mail, phone);
//    	
//    	String fileUri = Environment.getExternalStorageDirectory().getPath() + File.separator +"com.yilu.andriod.app" + File.separator +"test1.jpg";
//    	URI processedImageUri;
//		try {
//			processedImageUri = new URI(fileUri);
//    	    backend.ImgUpload("test CAP", processedImageUri);
//    	} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this, "Item Clicked" + position, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(MainActivity.this, DetailedImage.class);
		// http://stackoverflow.com/questions/3913592/start-an-activity-with-a-parameter
		// start new activity with parameters
		Bundle b = new Bundle();		
		b.putString("imgUrl", backend.dataList.get(position).imageUrl); // Should pass large image Url. That is, should store both Url on server
		b.putDouble("aspectRatio", backend.dataList.get(position).aspectRatio);
		intent.putExtras(b);
		startActivity(intent);
    }
}

