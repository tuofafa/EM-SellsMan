package com.em.main;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.em.R;
import com.em.base.BaseActivity;
import com.em.utils.DataGenerator;
import com.google.android.material.tabs.TabLayout;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/30 0030 16:31
 */
public class MainAPP extends BaseActivity<MainPresent> {

    private TabLayout mTabLayout;
    private Fragment[]mFragmensts;

    @Override
    public void initView() {

    }

    @Override
    public int getContextViewId() {
        return R.layout.main_layout;
    }

    @Override
    public void initData() {
        mFragmensts = DataGenerator.getFragments();
        initTabbarView();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void destroy() {
        finish();
    }

    @Override
    public MainPresent getmPersenterInstance() {
        return null;
    }

    @Override
    public void onClick(View v) {


    }

    private void initTabbarView() {
        mTabLayout = (TabLayout) findViewById(R.id.bottom_tab_layout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());
                // Tab 选中之后，改变各个Tab的状态
                for (int i=0;i<mTabLayout.getTabCount();i++){
                    View view = mTabLayout.getTabAt(i).getCustomView();
                    ImageView icon = (ImageView) view.findViewById(R.id.tab_content_image);
                    TextView text = (TextView) view.findViewById(R.id.tab_content_text);
                    if(i == tab.getPosition()){ // 选中状态
                        //这里还有问题
                        icon.setImageResource(DataGenerator.mTabResPressed[i]);
                        text.setTextColor(Color.parseColor("#06C061"));
                    }else{// 未选中状态
                        icon.setImageResource(DataGenerator.mTabRes[i]);
                        //text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                        text.setTextColor(Color.parseColor("#333333"));
                    }

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // 提供自定义的布局添加Tab
        for(int i=0;i<mFragmensts.length;i++){
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(DataGenerator.getTabView(this,i)));
        }
    }
    private void onTabItemSelected(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = mFragmensts[0];
                break;
            case 1:
                fragment = mFragmensts[1];
                break;
            case 2:
                fragment = mFragmensts[2];
                break;
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container, fragment).commit();
        }

    }
}
