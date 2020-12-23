package com.em.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.em.R;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/26 0026 10:36
 * discrption APP 更新类提示dialog
 */
public class PictureAllredeTiShiDialog extends AlertDialog implements View.OnClickListener {

    private Context mContext;
    private View view;
    private TextView iKnow;


    public PictureAllredeTiShiDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置背景为透明
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //设置对话框样式
        setStyle();
        //初始化控件
        initView();
        initListen();
        initData();
    }

    @SuppressLint("CutPasteId")
    private void initView() {
        view = View.inflate(mContext, R.layout.picture_allrede_tishi, null);
        setContentView(view);
        iKnow = view.findViewById(R.id.picture_tishi_know);
    }

    private void setStyle() {
        //设置对话框不可取消
        this.setCancelable(false);
        //设置触摸对话框外面不可取消
        this.setCanceledOnTouchOutside(false);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        Window window = this.getWindow();
        //获得应用窗口大小
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        //设置对话框居中显示
        layoutParams.gravity = Gravity.CENTER;

        window.setAttributes(layoutParams);
    }



    public void initData() {

    }

    public void initListen() {
        iKnow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.picture_tishi_know:
                cancel();
                break;
        }
    }
}
