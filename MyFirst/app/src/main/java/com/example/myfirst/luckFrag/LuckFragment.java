package com.example.myfirst.luckFrag;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myfirst.R;
import com.example.myfirst.bean.StarBean;

import java.util.List;

public class LuckFragment extends Fragment {
    /**
     * 运势页面
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    GridView luckGv;
    List<StarBean.StarinfoBean>mDatas;
    private LuckBaseAdapter luckBaseAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_luck,container,false);
        luckGv = view.findViewById(R.id.luckfrag_gv);
        //获取Gv的数据
        Bundle bundle = getArguments();
        StarBean starBean = (StarBean)bundle.getSerializable("info");
        mDatas = starBean.getStarinfo();
        //创建适配器对象
        luckBaseAdapter = new LuckBaseAdapter(getContext(),mDatas);
        luckGv.setAdapter(luckBaseAdapter);
        //设置每一个星座的监听事件
        setListener();
        return view;
    }

    private void setListener() {
        luckGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //首先先要获取到,用户点击的是那个星座对象,并且获取到星座名称
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StarBean.StarinfoBean bean = mDatas.get(position);
                //获取到星座名称
                String name = bean.getName();
                //传值,把星座名称的值传到LuckAnalysisActivity的类中去
                Intent intent = new Intent(getContext(),LuckAnalysisActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }
}