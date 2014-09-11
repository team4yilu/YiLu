package com.yilu.android.app;

import android.content.Intent;
import android.app.Activity;  
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Application;

import com.avos.avoscloud.LogUtil.log;
import com.yilu.android.app.BackendService;

public class MePage  extends Fragment {
    BackendService backend;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
		
		backend = ((YiLuApp)getActivity().getApplication()).backend;
		View v;
		log.d("lzw", "already LogIn");
		v = inflater.inflate(R.layout.frag_user_info, container, false);
		userInfoView(v);
	    return v;	
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
	
	
	public static MePage newInstance(String text){
		MePage f = new MePage();
		Bundle b = new Bundle();
		b.putString("msg", text);
		f.setArguments(b);
		return f;
	}
}

