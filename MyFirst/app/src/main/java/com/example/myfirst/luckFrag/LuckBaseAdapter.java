package com.example.myfirst.luckFrag;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.myfirst.R;
import com.example.myfirst.bean.StarBean;
import com.example.myfirst.starFrag.StarAnalysisBean;
import com.example.myfirst.utils.AssetsUtils;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;
import java.util.Map;

public class LuckBaseAdapter extends BaseAdapter {
    private final Map<String, Bitmap>contentLogoImgMap;
    Context context;
    List<StarBean.StarinfoBean>mDatas;

    public LuckBaseAdapter(Context context, List<StarBean.StarinfoBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        contentLogoImgMap = AssetsUtils.getContentLogoImgMap();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //初始化View
        ViewHolder holder =null;
        if(convertView==null){

            convertView = LayoutInflater.from(context).inflate(R.layout.item_luck_gv,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        //数据回填到控件中
        StarBean.StarinfoBean bean = mDatas.get(position);
        holder.starIv.setImageBitmap(contentLogoImgMap.get(bean.getLogoname()));
        holder.starTv.setText(bean.getName());
        return convertView;
    }

    class ViewHolder{
        CircleImageView starIv;
        TextView starTv;
        public ViewHolder(View view){
            starIv = view.findViewById(R.id.item_luck_iv);
            starTv = view.findViewById(R.id.item_luck_tv);
        }
    }
}
