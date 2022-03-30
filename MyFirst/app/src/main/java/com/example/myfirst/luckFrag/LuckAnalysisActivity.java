package com.example.myfirst.luckFrag;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myfirst.R;
import com.example.myfirst.utils.LoadDataAsyncTask;
import com.example.myfirst.utils.URLContent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LuckAnalysisActivity extends AppCompatActivity implements View.OnClickListener,LoadDataAsyncTask.OnGetNetDataListener {
    ListView luckLv;
    TextView nameTv;
    ImageView backIv;
    List<LuckItemBean> luckItemBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck_analysis);

        //获取到LuckFragment传过来的星座名称
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");//获取上一级界面传递的星座名称
        //控件初始化
        luckLv = findViewById(R.id.luckanalysis_lv);
        nameTv = findViewById(R.id.title_tv);
        backIv = findViewById(R.id.title_iv_back);
        nameTv.setText(name);
        backIv.setOnClickListener(this);
        //获取网站
        String luckUrl = URLContent.getLuckUrl(name);
        //获取网络请求
        LoadDataAsyncTask task = new LoadDataAsyncTask(this,this,true);
        task.execute(luckUrl);
    }
    //返回上一级页面
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onSucess(String json) {
        if(!TextUtils.isEmpty(json)){
            //数据解析
            LuckAnalysisBean gson = new Gson().fromJson(json,LuckAnalysisBean.class);
            //整理数据
            luckItemBeans = new ArrayList<>();
            addDataToList(gson);
            //设置适配器
            LuckAnalysisAdapter adapter =new LuckAnalysisAdapter(this,luckItemBeans);
            luckLv.setAdapter(adapter);
        }


    }
    //整理数据到集合当中
    private void addDataToList(LuckAnalysisBean gson) {
        LuckItemBean lib1 = new LuckItemBean("综合运势",gson.getMima().getText().get(0), Color.BLUE);
        LuckItemBean lib2 = new LuckItemBean("爱情运势",gson.getLove().get(0),Color.GREEN);
        LuckItemBean lib3 = new LuckItemBean("事业学业",gson.getCareer().get(0),Color.RED);
        LuckItemBean lib4 = new LuckItemBean("健康运势",gson.getHealth().get(0),Color.GRAY);
        LuckItemBean lib5 = new LuckItemBean("财富运势",gson.getFinance().get(0),Color.BLACK);
        luckItemBeans.add(lib1);
        luckItemBeans.add(lib2);
        luckItemBeans.add(lib3);
        luckItemBeans.add(lib4);
        luckItemBeans.add(lib5);
    }
}