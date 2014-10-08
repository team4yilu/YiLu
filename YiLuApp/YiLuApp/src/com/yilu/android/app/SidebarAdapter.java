package com.yilu.android.app;

import java.util.ArrayList;
import java.util.TreeSet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SidebarAdapter  extends BaseAdapter {
	Context context;
	private ArrayList<String> TextValue = new ArrayList<String>();
	private static final int TYPE_ITEM = 0;
	private static final int TYPE_COMMENT = 1;
	private static final int TYPE_MAX_COUNT = TYPE_COMMENT + 1;
	LayoutInflater mInflater;
	private TreeSet<Integer> mCommentSet = new TreeSet<Integer>();
	EditText commentText; // have to do it this way, otherwise cannot find view inside button.setonclicklistener
	

	public SidebarAdapter(Context context) {	    
	    this.context = context;
	    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void addItem(String text){
		TextValue.add(text);
		notifyDataSetChanged();
	}
	
	private void addItemInternally(String text) {
		TextValue.add(TextValue.size()-1,text);
		mCommentSet.clear();
		mCommentSet.add(TextValue.size()-1);
		notifyDataSetChanged();
	}
	
	public void addCommentItem(String text){
		TextValue.add(text);
		mCommentSet.add(TextValue.size()-1);
		notifyDataSetChanged();
	}
	
	@Override
    public int getItemViewType(int position) {
        return mCommentSet.contains(position) ? TYPE_COMMENT : TYPE_ITEM;
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

		System.out.println(position);		
		ViewHolder holder = null;
		int type = getItemViewType(position);
		if(coverView == null){
			holder = new ViewHolder();
			switch(type){
				case TYPE_ITEM:
					coverView = mInflater.inflate(R.layout.row_comment, null);
					holder.textView = (TextView)coverView.findViewById(R.id.text_comment);
					System.out.println(TextValue.get(position));
					break;
				case TYPE_COMMENT:
					coverView = mInflater.inflate(R.layout.row_add_comment, null);
					holder.editText = (EditText)coverView.findViewById(R.id.text_add_comment);
					commentText = holder.editText;
					Button buttonSend = (Button) coverView.findViewById(R.id.button);
					holder.button = buttonSend;
					holder.button.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							addItemInternally(commentText.getText().toString());
							// need to insert above the add_comment row
						}
						
					});
					System.out.println(TextValue.get(position));
					break;
			}
			coverView.setTag(holder);
		} else{
			holder = (ViewHolder) coverView.getTag();		
		}		
		switch(type){
			case TYPE_ITEM:
				holder.textView.setText(TextValue.get(position));
				break;
			case TYPE_COMMENT:
				break;
		}
		return coverView;
	}
	
	public static class ViewHolder{
		public TextView textView;
		public EditText editText;
		public Button button;
	}

}