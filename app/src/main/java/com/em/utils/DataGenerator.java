package com.em.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.em.R;
import com.em.fragment.CateFragment;
import com.em.fragment.HomeFragment;
import com.em.fragment.MyFragment;
import com.em.home.HomeActivity;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/30 0030 16:20
 */
public class DataGenerator {

    public static final int []mTabRes = new int[]{R.drawable.home_false,R.drawable.categree_false,R.drawable.my_false};
    public static final int []mTabResPressed = new int[]{R.drawable.home_true,R.drawable.categree_true,R.drawable.my_true};
    public static final String []mTabTitle = new String[]{"首页","分类","我的"};

    public static Fragment[] getFragments(){
        Fragment fragments[] = new Fragment[3];
        fragments[0] = new HomeFragment();
        fragments[1] = new CateFragment();
        fragments[2] = new MyFragment();
        return fragments;
    }

    /**
     * 获取Tab 显示的内容
     * @param context
     * @param position
     * @return
     */
    public static View getTabView(Context context, int position){
        View view = LayoutInflater.from(context).inflate(R.layout.customer_tabbar_item,null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(DataGenerator.mTabRes[position]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }
}
