package com.example.myfirst.parnterFrag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myfirst.R;
import com.example.myfirst.utils.AssetsUtils;
import com.example.myfirst.utils.LoadDataAsyncTask;
import com.example.myfirst.utils.URLContent;
import com.google.gson.Gson;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.Map;

public class ParnterAnalysisActivity extends AppCompatActivity implements View.OnClickListener,LoadDataAsyncTask.OnGetNetDataListener {
    TextView manTv,womanTv,pdTv,vsTv,pfTv,bzTv,jxTv,zyTv,titleTv;
    CircleImageView manIv,womanIv;
    ImageView backIv;
    private String man_name;
    private String woman_name;
    private String man_logoname;
    private String woman_logoname;
    private Map<String, Bitmap> logoImgMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parnter_analysis);
        initView();
        //获取Intent保存的值
        getLastDate();
        //获取网络路径的地址,通过聚合数据提供的网络路径去配合,下拉框选择的数据可以合成准确的url
        String parnterUrl = URLContent.getParnterUrl(man_name,woman_name);
        //加载网络数据
        //创建自定义的异步任务对象
        LoadDataAsyncTask task = new LoadDataAsyncTask(this,this,true);
        //执行异步任务
        task.execute(parnterUrl);

    }

    private void getLastDate() {
        Intent intent = getIntent();
        man_name = intent.getStringExtra("man_name");
        woman_name = intent.getStringExtra("woman_name");
        man_logoname = intent.getStringExtra("man_logoName");
        woman_logoname = intent.getStringExtra("woman_logoName");
        logoImgMap = AssetsUtils.getContentLogoImgMap();
        manIv.setImageBitmap(logoImgMap.get(man_logoname));
        womanIv.setImageBitmap(logoImgMap.get(woman_logoname));
        manTv.setText(man_name);
        womanTv.setText(woman_name);
        pdTv.setText("星座配对: "+man_name+"和"+woman_name+"配对");
        vsTv.setText(man_name+"vs"+woman_name);
        //返回上一页
        backIv.setOnClickListener(this);
    }
    //初始化控件
    private void initView(){
        manIv = findViewById(R.id.parnteranalysis_iv_man);
        womanIv = findViewById(R.id.parnteranalysis_iv_woman);
        manTv = findViewById(R.id.parnteranalysis_tv_man);
        womanTv = findViewById(R.id.parnteranalysis_tv_woman);
        pdTv = findViewById(R.id.parnteranalysis_tv_pd);
        vsTv = findViewById(R.id.parnteranalysis_tv_vs);
        pfTv = findViewById(R.id.parnteranalysis_tv_pf);
        bzTv = findViewById(R.id.parnteranalysis_tv_bz);
        jxTv = findViewById(R.id.parnteranalysis_tv_jx);
        zyTv = findViewById(R.id.parnteranalysis_tv_zy);
        titleTv = findViewById(R.id.title_tv);
        backIv = findViewById(R.id.title_iv_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_iv_back:
                finish();
                break;
        }
    }
    //通过实现LoadDataAsyncTask公共类的接口,获取到保存在接口里的方法数据
    @Override
    public void onSucess(String json) {
        if (!TextUtils.isEmpty(json)){
            ParnterAnalysisBean analysisBean = new Gson().fromJson(json,ParnterAnalysisBean.class);
            ParnterAnalysisBean.ResultBean resultBean = analysisBean.getResult();
            pfTv.setText("配对评分: "+resultBean.getZhishu()+" "+resultBean.getJieguo());
            bzTv.setText("星座比重: "+resultBean.getBizhong());
            jxTv.setText("解析: \n\n"+resultBean.getLianai());
            zyTv.setText("主页事项: \n\n"+resultBean.getZhuyi());
        }
    }
}