package com.example.myfirst.starFrag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.myfirst.R;

import java.util.List;

public class AnalysisBaseAnapter extends BaseAdapter {
    Context context;
    List<StarAnalysisBean>mDatas;

    public AnalysisBaseAnapter(Context context, List<StarAnalysisBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
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

        ViewHolder holder =null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_star_anlysis,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        StarAnalysisBean bean = mDatas.get(position);
        holder.titleTv.setText(bean.getTitle());
        holder.content.setText(bean.getContent());
        holder.content.setBackgroundResource(bean.getColor());
        return convertView;
    }
    //初始化控件
    class ViewHolder{
        TextView titleTv,content;
        public ViewHolder(View view){
            titleTv = view.findViewById(R.id.itemstar_tv_title);
            content = view.findViewById(R.id.itemstar_tv_content);
        }
    }
}
