package com.em.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/7 0010 16:24
 */
public abstract class BaseActivity<P extends BasePersenter> extends AppCompatActivity implements View.OnClickListener {

    public P mPersenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(getContextViewId());


        initView();
        initListener();
        initData();
        this.mPersenter = getmPersenterInstance();
    }

    //初始化view
    public abstract void initView();


    public abstract int getContextViewId();

    //初始化数据
    public abstract void initData();

    //初始化监听器
    public abstract void initListener();

    public abstract void destroy();

    public abstract P getmPersenterInstance();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    /***
     * 禁用修改手机系统随着app字体的变化
     */

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
}
