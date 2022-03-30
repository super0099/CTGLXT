package com.example.myfirst.starFrag;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StarPagerAdapter extends PagerAdapter {
    //跳转页面用
    Context context;
    List<ImageView>imgList;

    public StarPagerAdapter(Context context, List<ImageView> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container,int position){
        ImageView imageView = imgList.get(position);
        container.addView(imageView);
        return imageView;
    }
    @Override
    public void destroyItem(@NotNull ViewGroup container,int position,@NotNull Object object){
        ImageView imageView = imgList.get(position);
        container.removeView(imageView);
    }
}
