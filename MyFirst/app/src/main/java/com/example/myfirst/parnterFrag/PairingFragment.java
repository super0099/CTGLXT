package com.example.myfirst.parnterFrag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myfirst.R;
import com.example.myfirst.bean.StarBean;
import com.example.myfirst.utils.AssetsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PairingFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ImageView manIv,womanIv;
    Spinner manSp,womanSp;
    Button prizeBtn,analysisBtn;
    List<StarBean.StarinfoBean> info;
    List<String> nameList;
    private Map<String, Bitmap> contentLogoImgMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pairing, container, false);
        //初始化控件
        initview(view);
        //获取activity传过来的数据
        Bundle bundle = getArguments();
        StarBean bean = (StarBean) bundle.getSerializable("info");
        info = bean.getStarinfo();
        //获取适配器所需要的数据
        nameList = new ArrayList<>();
        for (int i = 0; i < info.size(); i++) {
            String logoname = info.get(i).getName();
            nameList.add(logoname);
        }
        //创建适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.item_parnter_sp,R.id.item_parnter_tv,nameList);
        //设置适配器
        manSp.setAdapter(adapter);
        womanSp.setAdapter(adapter);
        //获取第一个图片显示在imageView上
        String logoName = info.get(0).getLogoname();
        contentLogoImgMap = AssetsUtils.getContentLogoImgMap();
        Bitmap bitmap = contentLogoImgMap.get(logoName);
        manIv.setImageBitmap(bitmap);
        womanIv.setImageBitmap(bitmap);
        return view;
    }

    private void initview(View view) {
        manIv = view.findViewById(R.id.parnterfrag_iv_man);
        womanIv = view.findViewById(R.id.parnterfrag_iv_woman);
        manSp = view.findViewById(R.id.parnterfrag_sp_man);
        womanSp = view.findViewById(R.id.parnterfrag_sp_woman);
        prizeBtn = view.findViewById(R.id.parnterfrag_prize);
        analysisBtn = view.findViewById(R.id.parnterfrag_analysis);
        prizeBtn.setOnClickListener(this);
        analysisBtn.setOnClickListener(this);
        manSp.setOnItemSelectedListener(this);
        womanSp.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.parnterfrag_prize:
                break;
            case R.id.parnterfrag_analysis:
                //获取spinner选中的星座和图片
                int manSelpos = manSp.getSelectedItemPosition();
                int wonmanSelpos = womanSp.getSelectedItemPosition();
                //跳转传值
                Intent intent = new Intent(getContext(),ParnterAnalysisActivity.class);
                intent.putExtra("man_name",info.get(manSelpos).getName());
                intent.putExtra("woman_name",info.get(wonmanSelpos).getName());
                intent.putExtra("man_logoName",info.get(manSelpos).getLogoname());
                intent.putExtra("woman_logoName",info.get(wonmanSelpos).getLogoname());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.parnterfrag_sp_man:
                String manName = info.get(position).getLogoname();
                Bitmap manbitmap = contentLogoImgMap.get(manName);
                manIv.setImageBitmap(manbitmap);
                break;
            case R.id.parnterfrag_sp_woman:
                String womanName = info.get(position).getLogoname();
                Bitmap womanbitmap = contentLogoImgMap.get(womanName);
                womanIv.setImageBitmap(womanbitmap);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}