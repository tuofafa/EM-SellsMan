package com.em.protocol;

import android.view.View;
import android.webkit.WebView;

import com.em.R;
import com.em.base.BaseActivity;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/20 0020 16:15
 */
public class RegisterProtocol extends BaseActivity<RegisterProtocolPresenter> {

    private WebView procotol;

    @Override
    public void initView() {
        procotol = findViewById(R.id.protocol);

    }

    @Override
    public int getContextViewId() {
        return R.layout.protocol_layout;
    }

    @Override
    public void initData() {
        //记载项目当中的html文件
        procotol.loadUrl("file:///android_asset/protocol.html");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public RegisterProtocolPresenter getmPersenterInstance() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}
