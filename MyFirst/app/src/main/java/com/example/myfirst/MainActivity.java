package com.example.myfirst;


import android.os.Bundle;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.myfirst.bean.StarBean;
import com.example.myfirst.luckFrag.LuckFragment;
import com.example.myfirst.meFrag.MeFragment;
import com.example.myfirst.parnterFrag.PairingFragment;
import com.example.myfirst.starFrag.StarFragment;
import com.example.myfirst.utils.AssetsUtils;
import com.google.gson.Gson;

//主页活动,继承活动的基类,并且实现按钮组的点击事件
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    RadioGroup mainRg;//初始化控件,按钮组控件
    //声明四个按钮对应的Fargment对象
    Fragment starFrag,luckFrag,partnerFrag,meFrag;
    private FragmentManager manager;//创建碎片对象容器
    @Override
    protected void onCreate(Bundle savedInstanceState){
        //加载主页面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化按钮组,根据id获取
        mainRg = findViewById(R.id.main_rg);
        //设置监听点击了那个单选按钮
        mainRg.setOnCheckedChangeListener(this);
        //1.创建对象之前加载相关的数据,StarInfoBean是用Gson创建的一个vo类存放数据字段,调用方法
        StarBean infoBean = loadDate();
        //将StarInfoBean对象封装到Bundle中,相当于session的功能
        Bundle bundle = new Bundle();
        //保存StarBean数据到bundle中
        bundle.putSerializable("info",infoBean);

        //创建碎片对象,并且将已经封装好的Bundle赋值给碎片对象,也就是四个页面
        //将StarInfoBean,又因为将StarInfoBean封装在Bundle里,所以把Bundle传给下面四个对象,
        starFrag = new StarFragment();
        starFrag.setArguments(bundle);
        partnerFrag = new PairingFragment();
        partnerFrag.setArguments(bundle);
        luckFrag = new LuckFragment();
        luckFrag.setArguments(bundle);
        meFrag =new MeFragment();
        meFrag.setArguments(bundle);
//        将四个Fragment进行动态加载,一起加载到布局当中
        addFragment();
    }

    /**
     *读取文件夹下的json文件
     * @return 返回json格式数据
     */
    private StarBean loadDate() {
        //获取json文件中的字符串
        String json = AssetsUtils.getJsonFromAssets(this,"xzcontent/xzcontent.json");
        //解析Gson
        Gson gson = new Gson();
        //创建好的类,通过Gson解析,将字符串转成对象,json为字符串,StarBean对象对应的字段
        StarBean infoBean = gson.fromJson(json, StarBean.class);
        //将要用到的图片加载到内存当中
        AssetsUtils.saveBitmapAssets(this,infoBean);
        return infoBean;
    }

    /**
     * 将主页当中的碎片一起加载进入布局,有用的显示,暂时无用的隐藏
     */
    private void addFragment() {
//        创建碎片管理者对象
        manager = getSupportFragmentManager();
//        创建碎片处理事务对象
        FragmentTransaction transaction = manager.beginTransaction();
//        将四个Fragment统一的添加到布局中
        transaction.add(R.id.main_layout_center,starFrag);
        transaction.add(R.id.main_layout_center,partnerFrag);
        transaction.add(R.id.main_layout_center,luckFrag);
        transaction.add(R.id.main_layout_center,meFrag);
//        只显示一个隐藏后面三个
        transaction.hide(partnerFrag);
        transaction.hide(luckFrag);
        transaction.hide(meFrag);
//        提交事务
        transaction.commit();
    }

    /**
     *按钮切换页面
     * @param group 按钮组
     * @param checkedId 按钮Id
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = manager.beginTransaction();
        //监听按钮的点击事件并且,点击之后的操作
        switch (checkedId){
            case R.id.main_rb_star:
                transaction.hide(partnerFrag);
                transaction.hide(luckFrag);
                transaction.hide(meFrag);
                transaction.show(starFrag);
                break;
            case R.id.main_rb_luck:
                transaction.hide(partnerFrag);
                transaction.hide(starFrag);
                transaction.hide(meFrag);
                transaction.show(luckFrag);
                break;
            case R.id.main_rb_pairing:
                transaction.show(partnerFrag);
                transaction.hide(luckFrag);
                transaction.hide(meFrag);
                transaction.hide(starFrag);
                break;
            case R.id.main_rb_me:
                transaction.hide(partnerFrag);
                transaction.hide(luckFrag);
                transaction.show(meFrag);
                transaction.hide(starFrag);
                break;
        }
        transaction.commit();
    }
}

























































