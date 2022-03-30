package com.example.myfirst.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.example.myfirst.MainActivity;
import com.example.myfirst.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    ViewPager guideVp;
    //把引导页面的三张图片存放到数组当中
    int resIds[] ={R.mipmap.loading1,R.mipmap.loading2,R.mipmap.loading3};
    List<ImageView> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        guideVp = findViewById(R.id.guide_vp);
        mDatas = new ArrayList<>();
        initPage();
        //为第三张图片设置监听事件
        setListener();
    }

    private void setListener() {
        int size = mDatas.size();
        //获取到最后一张图片,并且给它设置点击事件
        ImageView view = mDatas.get(size-1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initPage() {
        for (int i = 0; i < resIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(resIds[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);
            mDatas.add(imageView);
        }
        GuideAdapter adapter = new GuideAdapter(mDatas);
        //设置适配器
        guideVp.setAdapter(adapter);
    }
}