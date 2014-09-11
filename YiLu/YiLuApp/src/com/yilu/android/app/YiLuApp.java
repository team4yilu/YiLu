package com.yilu.android.app;

import android.app.Application;
import com.yilu.android.app.BackendService;

public class YiLuApp extends Application {

	public BackendService backend;
	@Override
	public void onCreate(){
		super.onCreate(); 
		backend = new BackendService(this);
	}
}
