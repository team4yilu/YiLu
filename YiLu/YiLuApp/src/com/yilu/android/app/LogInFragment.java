package com.yilu.android.app;

import android.content.Intent;
import android.app.Activity;  
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

public class LogInFragment  extends Fragment {
    BackendService backend;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
		
		backend = ((YiLuApp)getActivity().getApplication()).backend;
		View v;
	    v = inflater.inflate(R.layout.frag_login, container, false);
		logInView(v);
	    return v;	
	}
	
	
	public void logInView(View v){

	    final EditText userNameET = (EditText)v.findViewById(R.id.UserName);
	    final EditText passwordET = (EditText)v.findViewById(R.id.Password);

        Button LogInButton =(Button)v.findViewById(R.id.LogIn); 
        OnClickListener ol=new OnClickListener(){  
            public void onClick(View v) {  
                //Backend Login, store user info
            	String userName = userNameET.getText().toString();
            	String password = passwordET.getText().toString();
            	backend.UserLogIn(userName, password, new UserLogInCallback(){
            		public void LogInSuccess(){
            			log.d("lzw", "LogInSuccess");
            			//Todo:¡¡jump to new fragement to show user info
            			MePage userInfo = new MePage();
            			Bundle b = new Bundle();
            			b.putString("msg", "UserInfo page");
            			userInfo.setArguments(b);
            			            			
            			FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            			transaction.replace(R.id.viewPager, userInfo);
            			transaction.addToBackStack(null);
            			transaction.commit();
            		};
            		public void LogInFail(){
            			log.d("lzw", "LogInFail");
            			//show text login fail
            		};
            	});
            }  
        };  
        LogInButton.setOnClickListener(ol);
        
	}
	
	public static LogInFragment newInstance(String text){
		LogInFragment f = new LogInFragment();
		Bundle b = new Bundle();
		b.putString("msg", text);
		f.setArguments(b);
		return f;
	}
}


