package com.yilu.android.app;

import android.app.Activity;  
import android.os.Bundle;  
import android.text.Editable;  
import android.text.InputType;  
import android.text.TextWatcher;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.*;  

import com.yilu.android.app.BackendService;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public class LogInView  extends Activity{

    private LinearLayout mainLayout=null;  
    private RelativeLayout layout1=null;      
    private TextView tv11=null;     //请输入账号       
    private TextView tv12=null;     //显示账号可输入剩余字符数  
    private EditText et1=null;      //账号框  
    private RelativeLayout layout2=null;  
    private TextView tv21=null;     //请输入密码  
    private TextView tv22=null;     //显示密码可输入剩余字符数  
    private EditText et2=null;      //密码输入框  
    private LinearLayout layout3=null;  
    private Button button1=null;    //按钮1：取消  
    private Button button2=null;    //按钮2：登陆  
    private BackendService backend;
    
    private final int MAX_PWD_LENGTH = 60;
    private final int MAX_USR_LENGTH = 40;
    
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        backend = ((YiLuApp)getApplication()).backend;
        mainLayout_init();  
        setContentView(mainLayout);  
    }  
    /*mainLayout初始化*/  
    void mainLayout_init(){  
        mainLayout=new LinearLayout(this);  
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1, -1);  
        mainLayout.setLayoutParams(lp);  
        mainLayout.setOrientation(LinearLayout.VERTICAL);  
        layout1_init();  
        mainLayout.addView(layout1);  
        et1_init();  
        mainLayout.addView(et1);  
        layout2_init();  
        mainLayout.addView(layout2);  
        et2_init();  
        mainLayout.addView(et2);  
        layout3_init();  
        mainLayout.addView(layout3);  
    }  
    /*layout1初始化*/  
    void layout1_init(){  
        layout1=new RelativeLayout(this);  
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(-1, -2);  
        layout1.setLayoutParams(lp);  
        tv11_init();  
        layout1.addView(tv11);  
        tv12_init();  
        layout1.addView(tv12);  
    }  
    /*tv11初始化*/  
    void tv11_init(){  
        tv11=new TextView(this);  
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(-2, -2);  
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);  
        tv11.setLayoutParams(lp);  
        tv11.setTextSize(30);  
        tv11.setText("账号:");  
    }  
    /*tv12初始化*/  
    void tv12_init(){  
        tv12=new TextView(this);  
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(-2, -2);  
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);  
        tv12.setLayoutParams(lp);  
        tv12.setTextSize(30);  
        tv12.setText("12");  
    }  
    /*et1初始化*/  
    void et1_init(){  
        et1=new EditText(this);  
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);  
        et1.setLayoutParams(lp);  
        et1.setSingleLine(true);  
        TextWatcher tw=new TextWatcher(){  
            public void afterTextChanged(Editable s) {  
                //限制输入  
                if(et1.length()>MAX_USR_LENGTH){  
                    //截取字符串，舍弃最后一个  
                    et1.setText((et1.getText()).subSequence(0, MAX_USR_LENGTH));  
                    //设置光标。由于用setText函数会导致光标复位，所以重新设置光标到末尾  
                    et1.setSelection(MAX_USR_LENGTH);  
                }  
                //显示剩余可输入字符数  
                tv12.setText(String.valueOf(MAX_USR_LENGTH-et1.length()));  
            }  
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}  
            public void onTextChanged(CharSequence s, int start, int before,int count) {}  
        };  
        et1.addTextChangedListener(tw);  
    }  
    /*layout2初始化*/  
    void layout2_init(){  
        layout2=new RelativeLayout(this);  
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1, -2);  
        layout2.setLayoutParams(lp);  
        tv21_init();  
        layout2.addView(tv21);  
        tv22_init();  
        layout2.addView(tv22);  
    }  
    /*tv21初始化*/  
    void tv21_init(){  
        tv21=new TextView(this);  
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(-2, -2);  
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);  
        tv21.setLayoutParams(lp);  
        tv21.setTextSize(30);  
        tv21.setText("密码:");  
    }  
    /*tv22初始化*/  
    void tv22_init(){  
        tv22=new TextView(this);  
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(-2, -2);  
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);  
        tv22.setLayoutParams(lp);  
        tv22.setTextSize(30);  
        tv22.setText("12");  
    }  
    /*et2初始化*/  
    void et2_init(){  
        et2=new EditText(this);  

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1, -2);  
        et2.setLayoutParams(lp);  
        et2.setSingleLine();  
        //设置输入模式为密码模式  
        et2.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);  
        TextWatcher tw=new TextWatcher(){  
            public void afterTextChanged(Editable s) {  
                //限制输入  
                if(et2.length()>MAX_PWD_LENGTH){  
                    //截取字符串，舍弃最后一个  
                    et2.setText((et2.getText()).subSequence(0, MAX_PWD_LENGTH));  
                    //设置光标。由于用setText函数会导致光标复位，所以重新设置光标到末尾  
                    et2.setSelection(MAX_PWD_LENGTH);  
                }  
                //显示剩余可输入字符数  
                tv22.setText(String.valueOf(MAX_PWD_LENGTH-et2.length()));  
            }  
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}  
            public void onTextChanged(CharSequence s, int start, int before,int count) {}  
        };  
        et2.addTextChangedListener(tw);  
    }  
    /*layout3初始化*/  
    void layout3_init(){  
        layout3=new LinearLayout(this);  
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);  
        layout3.setLayoutParams(lp);  
        layout3.setOrientation(LinearLayout.HORIZONTAL);  
        button1_init();  
        layout3.addView(button1);  
        button2_init();  
        layout3.addView(button2);  
    }  
    /*button1初始化*/  
    void button1_init(){  
        button1=new Button(this);  
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-2, -2);  
        button1.setLayoutParams(lp);  
        button1.setTextSize(30);  
        button1.setText("取消");  
        OnClickListener ol=new OnClickListener(){  
            public void onClick(View v) {  
                //go back to main activity/last activity
            	finish();
            }  
        };  
        button1.setOnClickListener(ol);  
    }  
    /*button2初始化*/  
    void button2_init(){  
        button2=new Button(this);  
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1, -2);  
        button2.setLayoutParams(lp);  
        button2.setTextSize(30);  
        button2.setText("登录");  
        OnClickListener ol=new OnClickListener(){  
            public void onClick(View v) {  
                //Backend Login, store user info
            	String userName = et1.getText().toString();
            	String password = et2.getText().toString();
            	backend.UserLogIn(userName, password, new UserLogInCallback(){
            		public void LogInSuccess(){
            			finish();
            		};
            		public void LogInFail(){
            			//show text login fail
            		};
            	});
            }  
        };  
        button2.setOnClickListener(ol);  
    }  
      
}  