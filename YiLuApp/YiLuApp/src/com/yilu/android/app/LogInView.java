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
    private TextView tv11=null;     //�������˺�       
    private TextView tv12=null;     //��ʾ�˺ſ�����ʣ���ַ���  
    private EditText et1=null;      //�˺ſ�  
    private RelativeLayout layout2=null;  
    private TextView tv21=null;     //����������  
    private TextView tv22=null;     //��ʾ���������ʣ���ַ���  
    private EditText et2=null;      //���������  
    private LinearLayout layout3=null;  
    private Button button1=null;    //��ť1��ȡ��  
    private Button button2=null;    //��ť2����½  
    private BackendService backend;
    
    private final int MAX_PWD_LENGTH = 60;
    private final int MAX_USR_LENGTH = 40;
    
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        backend = ((YiLuApp)getApplication()).backend;
        mainLayout_init();  
        setContentView(mainLayout);  
    }  
    /*mainLayout��ʼ��*/  
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
    /*layout1��ʼ��*/  
    void layout1_init(){  
        layout1=new RelativeLayout(this);  
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(-1, -2);  
        layout1.setLayoutParams(lp);  
        tv11_init();  
        layout1.addView(tv11);  
        tv12_init();  
        layout1.addView(tv12);  
    }  
    /*tv11��ʼ��*/  
    void tv11_init(){  
        tv11=new TextView(this);  
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(-2, -2);  
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);  
        tv11.setLayoutParams(lp);  
        tv11.setTextSize(30);  
        tv11.setText("�˺�:");  
    }  
    /*tv12��ʼ��*/  
    void tv12_init(){  
        tv12=new TextView(this);  
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(-2, -2);  
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);  
        tv12.setLayoutParams(lp);  
        tv12.setTextSize(30);  
        tv12.setText("12");  
    }  
    /*et1��ʼ��*/  
    void et1_init(){  
        et1=new EditText(this);  
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);  
        et1.setLayoutParams(lp);  
        et1.setSingleLine(true);  
        TextWatcher tw=new TextWatcher(){  
            public void afterTextChanged(Editable s) {  
                //��������  
                if(et1.length()>MAX_USR_LENGTH){  
                    //��ȡ�ַ������������һ��  
                    et1.setText((et1.getText()).subSequence(0, MAX_USR_LENGTH));  
                    //���ù�ꡣ������setText�����ᵼ�¹�긴λ�������������ù�굽ĩβ  
                    et1.setSelection(MAX_USR_LENGTH);  
                }  
                //��ʾʣ��������ַ���  
                tv12.setText(String.valueOf(MAX_USR_LENGTH-et1.length()));  
            }  
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}  
            public void onTextChanged(CharSequence s, int start, int before,int count) {}  
        };  
        et1.addTextChangedListener(tw);  
    }  
    /*layout2��ʼ��*/  
    void layout2_init(){  
        layout2=new RelativeLayout(this);  
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1, -2);  
        layout2.setLayoutParams(lp);  
        tv21_init();  
        layout2.addView(tv21);  
        tv22_init();  
        layout2.addView(tv22);  
    }  
    /*tv21��ʼ��*/  
    void tv21_init(){  
        tv21=new TextView(this);  
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(-2, -2);  
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);  
        tv21.setLayoutParams(lp);  
        tv21.setTextSize(30);  
        tv21.setText("����:");  
    }  
    /*tv22��ʼ��*/  
    void tv22_init(){  
        tv22=new TextView(this);  
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(-2, -2);  
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);  
        tv22.setLayoutParams(lp);  
        tv22.setTextSize(30);  
        tv22.setText("12");  
    }  
    /*et2��ʼ��*/  
    void et2_init(){  
        et2=new EditText(this);  

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1, -2);  
        et2.setLayoutParams(lp);  
        et2.setSingleLine();  
        //��������ģʽΪ����ģʽ  
        et2.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);  
        TextWatcher tw=new TextWatcher(){  
            public void afterTextChanged(Editable s) {  
                //��������  
                if(et2.length()>MAX_PWD_LENGTH){  
                    //��ȡ�ַ������������һ��  
                    et2.setText((et2.getText()).subSequence(0, MAX_PWD_LENGTH));  
                    //���ù�ꡣ������setText�����ᵼ�¹�긴λ�������������ù�굽ĩβ  
                    et2.setSelection(MAX_PWD_LENGTH);  
                }  
                //��ʾʣ��������ַ���  
                tv22.setText(String.valueOf(MAX_PWD_LENGTH-et2.length()));  
            }  
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}  
            public void onTextChanged(CharSequence s, int start, int before,int count) {}  
        };  
        et2.addTextChangedListener(tw);  
    }  
    /*layout3��ʼ��*/  
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
    /*button1��ʼ��*/  
    void button1_init(){  
        button1=new Button(this);  
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-2, -2);  
        button1.setLayoutParams(lp);  
        button1.setTextSize(30);  
        button1.setText("ȡ��");  
        OnClickListener ol=new OnClickListener(){  
            public void onClick(View v) {  
                //go back to main activity/last activity
            	finish();
            }  
        };  
        button1.setOnClickListener(ol);  
    }  
    /*button2��ʼ��*/  
    void button2_init(){  
        button2=new Button(this);  
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1, -2);  
        button2.setLayoutParams(lp);  
        button2.setTextSize(30);  
        button2.setText("��¼");  
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