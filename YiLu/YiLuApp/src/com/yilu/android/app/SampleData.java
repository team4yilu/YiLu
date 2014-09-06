package com.yilu.android.app;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

import android.os.Environment;

public class SampleData {

	public static final int SAMPLE_DATA_ITEM_COUNT = 10;

public static ArrayList<Data> generateSampleData() {
	final ArrayList<Data> datas = new ArrayList<Data>();
	String ImgURLList[] = new String[10];
	// The Urls are outdated, won't load
	ImgURLList[0] = "http://ac-i6xp9je0.qiniudn.com/SmOigcCtISmz16GtoyxLm4zQ13xgFiPQKVHHhziF.jpg";
	ImgURLList[1] = "http://ac-i6xp9je0.qiniudn.com/trCgrJ8sMwabACu383MotaCHv35WvWGsDY0PeIfs.jpg";
	ImgURLList[2] = "http://ac-i6xp9je0.qiniudn.com/iMwuSWLqK5D8A2hJYa1k0s02KatWVm9WCqxKvXj2.jpg";
	ImgURLList[3] = "http://ac-i6xp9je0.qiniudn.com/thUBdENWxuEK8cQBUOBMKTNYSeKy5q2srydqVBz9.jpg";
	ImgURLList[4] = "http://ac-i6xp9je0.qiniudn.com/mYnCUxqPHzuOGIIIfVmAlU2VEMtOPQK6jAl377yq.jpg";
	ImgURLList[5] = "http://ac-i6xp9je0.qiniudn.com/uMXqumu4DE88uOXHQy9PLK1YQuqtDLtcF5I3Q3Bt.jpg";
	ImgURLList[6] = "http://ac-i6xp9je0.qiniudn.com/kuvkkb5ygBg2tNVOrkIkrzSjdbGe7h8uqLWhaUdW.jpg";
	ImgURLList[7] = "http://ac-i6xp9je0.qiniudn.com/hSDx6E4T1t1XPANrhF9V4g4FPaelDrFg9Y6k6VAA.jpg";
	ImgURLList[8] = "http://ac-i6xp9je0.qiniudn.com/clHwBvIJdtc0vqepRKN115D6FptG8uQQTNJvw03H.jpg";
	
	URI processedImageUri;
	try {
		processedImageUri = new URI(Environment.getExternalStorageDirectory().getPath() + File.separator +"com.yilu.andriod.app" + File.separator +"test1.jpg");
		ImgURLList[9] = processedImageUri.getPath();
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	for (int i = 0; i < SAMPLE_DATA_ITEM_COUNT; i++) {
		Data data = new Data();
		data.imageUrl = ImgURLList[i];
		data.aspectRatio = 1.2; // to be implemented on server end
		data.title = "Imag " + i;
		data.description = "With Imag on avos Cloud" + i;
		data.yilucount = 123;
		datas.add(data);
	}
	return datas;
}

public static ArrayList<Data> generateOfflineData() {
	final ArrayList<Data> datas = new ArrayList<Data>();
	String ImgURLList[] = new String[10];
	URI processedImageUri;
	try {
		processedImageUri = new URI(Environment.getExternalStorageDirectory().getPath() + File.separator +"com.yilu.andriod.app" + File.separator +"aaa.jpg");
		ImgURLList[0] = processedImageUri.getPath();
		processedImageUri = new URI(Environment.getExternalStorageDirectory().getPath() + File.separator +"com.yilu.andriod.app" + File.separator +"bbb.jpg");
		ImgURLList[1] = processedImageUri.getPath();
		processedImageUri = new URI(Environment.getExternalStorageDirectory().getPath() + File.separator +"com.yilu.andriod.app" + File.separator +"ccc.jpg");
		ImgURLList[2] = processedImageUri.getPath();
		processedImageUri = new URI(Environment.getExternalStorageDirectory().getPath() + File.separator +"com.yilu.andriod.app" + File.separator +"ddd.jpg");
		ImgURLList[3] = processedImageUri.getPath();
	} catch (URISyntaxException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	ImgURLList[4] = "http://ac-i6xp9je0.qiniudn.com/mYnCUxqPHzuOGIIIfVmAlU2VEMtOPQK6jAl377yq.jpg";
	ImgURLList[5] = "http://ac-i6xp9je0.qiniudn.com/uMXqumu4DE88uOXHQy9PLK1YQuqtDLtcF5I3Q3Bt.jpg";
	ImgURLList[6] = "http://ac-i6xp9je0.qiniudn.com/kuvkkb5ygBg2tNVOrkIkrzSjdbGe7h8uqLWhaUdW.jpg";
	ImgURLList[7] = "http://ac-i6xp9je0.qiniudn.com/hSDx6E4T1t1XPANrhF9V4g4FPaelDrFg9Y6k6VAA.jpg";
	ImgURLList[8] = "http://ac-i6xp9je0.qiniudn.com/clHwBvIJdtc0vqepRKN115D6FptG8uQQTNJvw03H.jpg";
	
	
	try {
		processedImageUri = new URI(Environment.getExternalStorageDirectory().getPath() + File.separator +"com.yilu.andriod.app" + File.separator +"test1.jpg");
		ImgURLList[9] = processedImageUri.getPath();
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	for (int i = 0; i < SAMPLE_DATA_ITEM_COUNT; i++) {
		Data data = new Data();
		data.imageUrl = ImgURLList[i];
		data.aspectRatio = 1.2; // to be implemented on server end
		data.title = "Imag " + i;
		data.description = "With Imag on avos Cloud" + i;
		data.yilucount = 123;
		datas.add(data);
	}
	return datas;
}

}
