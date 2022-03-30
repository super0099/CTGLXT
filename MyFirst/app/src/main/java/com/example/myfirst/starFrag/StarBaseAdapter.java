package com.example.myfirst.starFrag;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.myfirst.R;
import com.example.myfirst.bean.StarBean;
import com.example.myfirst.utils.AssetsUtils;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;
import java.util.Map;

public class StarBaseAdapter extends BaseAdapter {
    Context context;
    List<StarBean.StarinfoBean> mDatas;
    Map<String, Bitmap>logoMap;
    public StarBaseAdapter(Context context,List<StarBean.StarinfoBean>mDatas){
        this.context = context;
        this.mDatas=mDatas;
        logoMap = AssetsUtils.getLogoImgMap();
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
        ViewHolder holder = null;
        if(holder==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_star_gv,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
//        获取指定位置的数据
        StarBean.StarinfoBean starInfoBean = mDatas.get(position);
        holder.tv.setText(starInfoBean.getName());
//        根据图片名称在内存当中进行获取到图片
        String logoname = starInfoBean.getLogoname();
        Bitmap bitmap =logoMap.get(logoname);
        holder.iv.setImageBitmap(bitmap);
        return convertView;
    }
//    对于item当中的控件进行声明和初始化操作
    class ViewHolder{
        CircleImageView iv;
        TextView tv;
        public ViewHolder(View view){
            iv = view.findViewById(R.id.item_star_iv);
            tv = view.findViewById(R.id.item_star_tv);
        }
    }
}
