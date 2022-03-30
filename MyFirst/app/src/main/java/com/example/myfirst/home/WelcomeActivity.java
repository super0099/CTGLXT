package com.example.myfirst.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myfirst.MainActivity;
import com.example.myfirst.R;

public class WelcomeActivity extends AppCompatActivity {
    TextView tv;
    int count=3;
    //计时器,每秒发送信息
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==1){
                count--;
                if(count==0){
                    //判断是否第一次进入
                    Intent intent=new Intent();
                    boolean ifFirst = pref.getBoolean("isFirst",true);
                    if(ifFirst){
                        intent.setClass(WelcomeActivity.this, GuideActivity.class);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("isFirst",false);
                        editor.commit();
                    }else {
                        intent.setClass(WelcomeActivity.this, MainActivity.class);
                    }
                    startActivity(intent);
                    finish();//销毁welcome页面,不然点击返回也会跳到主页
                }else {
                    tv.setText(String.valueOf(count));
                    handler.sendEmptyMessageDelayed(1,1000);
                }
            }
        }
    };
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        tv = findViewById(R.id.welcome_tv);
        pref = getSharedPreferences("first_pref", MODE_PRIVATE);
        handler.sendEmptyMessageDelayed(1,1000);
    }
}