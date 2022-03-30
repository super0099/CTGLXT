package com.example.myfirst.starFrag;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.ViewPager;
import com.example.myfirst.R;
import com.example.myfirst.bean.StarBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 星座的Fragment
 *
 */
public class StarFragment extends Fragment {
    ViewPager starVp;
    GridView starGv;
    LinearLayout pointLayout;
    private List<StarBean.StarinfoBean>mDatas;
    private StarBaseAdapter starBaseAdapter;
    private StarPagerAdapter starPagerAdapter;
//    声明轮播图片数组
    int[]imgIds = {R.mipmap.pic_guanggao,R.mipmap.pic_star};
//    声明ViewPager的数据资源
    List<ImageView> ivList;
//    声明管理器指示器的小圆点集合
    List<ImageView> pointList;
//    设置轮播定时切换
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                //获取当前图片的页数
                int currentItem = starVp.getCurrentItem();
                if(currentItem==ivList.size()-1){
                    starVp.setCurrentItem(0);
                }else {
                    currentItem++;
                    starVp.setCurrentItem(currentItem);
                }
                //接收的同时也要发送消息,从而达到循环的效果
                handler.sendEmptyMessageDelayed(1,5000);
            }
        }
    };
//onCreateView,当activity要得到fragment的layout时，调用此方法，fragment在其中创建自己的layout(界面)
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_star,container,false);
        //初始化控件
        starVp = view.findViewById(R.id.starfrag_vp);//轮播
        starGv = view.findViewById(R.id.starfrag_gv);//12星座
        pointLayout = view.findViewById(R.id.starfrag_layout);//小按钮
        //获取保存的图片数据和文字数据
        Bundle bundle = getArguments();
        StarBean infoBean = (StarBean)bundle.getSerializable("info");
        mDatas = infoBean.getStarinfo();//获取星座信息的集合数据
        //创建适配器
        starBaseAdapter = new StarBaseAdapter(getContext(),mDatas);
        starGv.setAdapter(starBaseAdapter);
        initPager();
        setVPListener();
        setGvListener();
        //每五秒钟发送一条可以切换轮播图片的信息
        handler.sendEmptyMessageDelayed(1,5000);
        return view;
    }

    private void setGvListener() {
        starGv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StarBean.StarinfoBean bean = mDatas.get(position);
                Intent intent = new Intent(getContext(),StarAnlysisActivity.class);
                intent.putExtra("star",bean);
                //把数据传到下一个页面
                startActivity(intent);
            }
        });
    }
    //轮播设置
    private void initPager() {
        ivList = new ArrayList<>();
        pointList = new ArrayList<>();
        //循环两张图片
        for (int i = 0; i < imgIds.length; i++) {
           ImageView iv = new ImageView(getContext());
           iv.setImageResource(imgIds[i]);
           iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //设置图片的高度
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(lp);
            //将图片加载到集合当中
            ivList.add(iv);
            //创建图片对应的指示器小圆点
            ImageView piv = new ImageView(getContext());
            piv.setImageResource(R.mipmap.point_normal);
            LinearLayout.LayoutParams plp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置外边距
            plp.setMargins(20,0,0,0);
            piv.setLayoutParams(plp);
            //将小圆点添加到布局里
            pointLayout.addView(piv);
            //为了方便管理统一将小圆点添加到集合中
            pointList.add(piv);
        }
        //默认第一个小圆点为选中状态
        pointList.get(0).setImageResource(R.mipmap.point_focus);
        starPagerAdapter = new StarPagerAdapter(getContext(),ivList);
        starVp.setAdapter(starPagerAdapter);
    }
    //设置轮播改变时的监听事件
    private void setVPListener() {
        starVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < pointList.size(); i++) {
                    pointList.get(i).setImageResource(R.mipmap.point_normal);
                }
                pointList.get(position).setImageResource(R.mipmap.point_focus);
            }
        });
    }
}
