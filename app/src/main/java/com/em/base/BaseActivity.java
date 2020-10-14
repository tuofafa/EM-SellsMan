package com.em.base;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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


}
