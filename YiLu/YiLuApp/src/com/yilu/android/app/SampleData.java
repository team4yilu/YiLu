package com.yilu.android.app;

import java.util.ArrayList;
import java.util.Random;

public class SampleData {

	public static final int SAMPLE_DATA_ITEM_COUNT = 9;

public static ArrayList<Data> generateSampleData() {
	String repeat = " repeat";
	final ArrayList<Data> datas = new ArrayList<Data>();
	for (int i = 0; i < SAMPLE_DATA_ITEM_COUNT; i++) {
		Data data = new Data();
		String url = "https://s3-us-west-1.amazonaws.com/yilutest/test" +(i+1)+ ".jpg";
		data.imageUrl = url;
		data.title = "Pinterest Card 9";
		data.description = "Super awesome description" + i;
		Random ran = new Random();
		int x = ran.nextInt(5); 
		for (int j = 0; j < x; j++)
			data.description += repeat;
		datas.add(data);
	}
	return datas;
}

}
