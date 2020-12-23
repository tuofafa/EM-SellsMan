package com.em.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/12/1 0001 9:38
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContextViewId(),container,false);

        initView(view);
        initData(view);
        initListener();
        destroy();

        return view;
    }

    //初始化view
    public abstract void initView(View view);


    public abstract int getContextViewId();

    //初始化数据
    public abstract void initData(View view);

    //初始化监听器
    public abstract void initListener();

    public abstract void destroy();


    @Override
    public void onDestroy() {
        super.onDestroy();
        destroy();
    }

}
