package com.example.myfirst.meFrag;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myfirst.R;
import com.example.myfirst.bean.StarBean;
import com.example.myfirst.luckFrag.LuckBaseAdapter;
import com.example.myfirst.utils.AssetsUtils;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;
import java.util.Map;

public class MeFragment extends Fragment implements View.OnClickListener{
    CircleImageView iconIv;
    TextView nameTv;
    private Map<String, Bitmap> ImgMap;
    private List<StarBean.StarinfoBean> mDatas;
    private SharedPreferences star_pref;//共享参数的存储对象,可存可取
    //保存选择的星座,不然退出app后又从新选择白羊,共享参数
    int selectpos = 0;
    //获取上一个页面传过来的值
    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        StarBean infoBean = (StarBean) bundle.getSerializable("info");
        mDatas = infoBean.getStarinfo();
        //共享参数的存储对象,可存可取
        star_pref = getContext().getSharedPreferences("star_pref", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_me, container, false);
        iconIv = view.findViewById(R.id.mefrag_me);
        nameTv = view.findViewById(R.id.mefrag_tv_name);
        iconIv.setOnClickListener(this);
        //进行初始化
        ImgMap = AssetsUtils.getContentLogoImgMap();
        //读取共享参数的存储对象所保存的值
        String name = star_pref.getString("name","白羊座");
        String logoName = star_pref.getString("logoname","baiyang");

        iconIv.setImageBitmap(ImgMap.get(logoName));
        nameTv.setText(name);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mefrag_me:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        Dialog dialog = new Dialog(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.me_dialog,null);
        dialog.setContentView(dialogView);
        dialog.setTitle("请选择您的星座");
        GridView dialogGv = dialogView.findViewById(R.id.mefrag_dialog_gv);
        //设置适配器
        LuckBaseAdapter adapter = new LuckBaseAdapter(getContext(),mDatas);
        dialogGv.setAdapter(adapter);
        //设置可以被取消
        dialog.setCancelable(true);
        //设置点外部的控件可以取消显示
        dialog.setCanceledOnTouchOutside(true);
        //设置弹出层的每一项可以被点击
        dialogGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StarBean.StarinfoBean starBean = mDatas.get(position);
                String name =  starBean.getName();
                String logoName = starBean.getLogoname();
                nameTv.setText(name);
                iconIv.setImageBitmap(ImgMap.get(logoName));
                selectpos = position;//保存选择的位置
                dialog.cancel();
            }
        });
        dialog.show();
    }
    //获取用户点击的星座信息,并且保存在共享参数中
    @Override
    public void onPause() {
        super.onPause();
        StarBean.StarinfoBean bean = mDatas.get(selectpos);
        String name = bean.getName();
        String logoname = bean.getLogoname();
        SharedPreferences.Editor editor = star_pref.edit();//获取向共享参数中写入数据的对象
        editor.putString("name",name);
        editor.putString("logoname",logoname);
        editor.commit();
    }
}