package com.yilu.android.app;

import java.util.ArrayList;
import java.util.TreeSet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SidebarAdapter  extends BaseAdapter  {
	Context context;
	private ArrayList<String> TextValue = new ArrayList<String>();
	private static final int TYPE_ITEM = 0;
	private static final int TYPE_MAX_COUNT = 1;
	LayoutInflater mInflater;
	EditText commentText; // have to do it this way, otherwise cannot find view inside button.setonclicklistener
	

	public SidebarAdapter(Context context) {	    
	    this.context = context;
	    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void addItem(String text){
		TextValue.add(text);
		notifyDataSetChanged();
	}
	
	public void addItemInternally(String text) {
		TextValue.add(0,text);// insert into the top of list
		notifyDataSetChanged();
	}
	
	@Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public int getCount() {
        return TextValue.size();
    }
    
    @Override
    public String getItem(int position) {
        return TextValue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
	
	@Override
	public View getView(int position, View coverView, ViewGroup parent) {

		ViewHolder holder = null;
		int type = getItemViewType(position);
		if(coverView == null){
			holder = new ViewHolder();			
			coverView = mInflater.inflate(R.layout.row_comment, null);
			holder.textView = (TextView)coverView.findViewById(R.id.text_comment);				
			coverView.setTag(holder);
		} else{
			holder = (ViewHolder) coverView.getTag();		
		}		
		holder.textView.setText(TextValue.get(position));
		return coverView;
	}
	
	public static class ViewHolder{
		public TextView textView;
		public EditText editText;
		public Button button;
	}


}