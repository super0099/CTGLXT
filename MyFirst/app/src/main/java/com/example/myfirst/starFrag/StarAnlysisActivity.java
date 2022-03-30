package com.example.myfirst.starFrag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myfirst.R;
import com.example.myfirst.bean.StarBean;
import com.example.myfirst.utils.AssetsUtils;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StarAnlysisActivity extends AppCompatActivity implements View.OnClickListener {
    TextView titleTv;
    ImageView backIv;
    CircleImageView iconIv;
    TextView nameTv,dataTv;
    ListView analysisLv;
    StarBean.StarinfoBean bean;
    private Map<String, Bitmap>contentlogoImgMap;
    private TextView footerTv;//是ListView底部布局当中需要改变的TextView;
    List<StarAnalysisBean>mDatas;
    AnalysisBaseAnapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_anlysis);
        Intent intent = getIntent();
        //获取在其他页面保存的星座信息数据
        bean = (StarBean.StarinfoBean) intent.getSerializableExtra("star");
        //初始化控件
        initView();
        mDatas = new ArrayList<>();//初始化显示在Listview的数据
        adapter = new AnalysisBaseAnapter(this,mDatas);
        analysisLv.setAdapter(adapter);
        addDataToList();
    }
    /*加载issView当中的数据内容*/
    private void addDataToList() {
        StarAnalysisBean sab1 = new StarAnalysisBean("性格特点 :",bean.getTd(),R.color.lightblue);
        StarAnalysisBean sab2 = new StarAnalysisBean("掌管宫位 :",bean.getGw(),R.color.lightpink);
        StarAnalysisBean sab3 = new StarAnalysisBean("显阴阳性 :",bean.getYy(),R.color.lightgreen);
        StarAnalysisBean sab4 = new StarAnalysisBean("最大特征 :",bean.getTz(),R.color.purple);
        StarAnalysisBean sab5 = new StarAnalysisBean("主管星球 :",bean.getZg(),R.color.orange);
        StarAnalysisBean sab6 = new StarAnalysisBean("幸运颜色 :",bean.getYs(),R.color.purple_200);
        StarAnalysisBean sab7 = new StarAnalysisBean("搭配珠宝 :",bean.getZb(),R.color.lightred);
        StarAnalysisBean sab8 = new StarAnalysisBean("幸运号码 :",bean.getHm(),R.color.teal_200);
        StarAnalysisBean sab9 = new StarAnalysisBean("相配金属 :",bean.getJs(),R.color.lightblue);
        mDatas.add(sab1);
        mDatas.add(sab2);
        mDatas.add(sab3);
        mDatas.add(sab4);
        mDatas.add(sab5);
        mDatas.add(sab6);
        mDatas.add(sab7);
        mDatas.add(sab8);
        mDatas.add(sab9);
//        数据源发生变化,提示适配器更新
        adapter.notifyDataSetChanged();
    }

    //    初始化控件
    private void initView() {
        titleTv = findViewById(R.id.title_tv);
        backIv = findViewById(R.id.title_iv_back);
        iconIv = findViewById(R.id.staranlysis_iv);
        nameTv = findViewById(R.id.staranlysis_tv_name);
        dataTv = findViewById(R.id.staranlysis_tv_data);
        analysisLv = findViewById(R.id.staranalysis_lv);

        //将底部的View添加到布局当中
        View footerView =  LayoutInflater.from(this).inflate(R.layout.footer_star_anlysis,null);
        analysisLv.addFooterView(footerView);
        footerTv = footerView.findViewById(R.id.footerstar_tv_info);


        //设置标题名称
        titleTv.setText("星座详情");
        //返回按钮点击事件
        backIv.setOnClickListener(this::onClick);
        nameTv.setText(bean.getName());
        dataTv.setText(bean.getDate());
        //获取保存在Map的所有图片
        contentlogoImgMap = AssetsUtils.getContentLogoImgMap();
        //根据图片名称拿到对应的图片,根据k获取v值
        Bitmap bitmap = contentlogoImgMap.get(bean.getLogoname());
        //改变logo的背景图片,该图片位于星座页面的12个按钮,点开后的logo
        iconIv.setImageBitmap(bitmap);
        footerTv.setText(bean.getInfo());
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}