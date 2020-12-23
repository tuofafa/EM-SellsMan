package com.em.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.em.R;
import com.em.app_update.AppUpdate;
import com.em.common.Common;
import com.em.login.LoginActivity;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/26 0026 10:36
 * discrption APP 更新类提示dialog
 */
public class APPUpdateTiShiDialog extends AlertDialog implements View.OnClickListener {

    private Context mContext;
    private TextView mTextView, clickUpdate;
    private View view, appUpdateBg;
    private String version;

    public APPUpdateTiShiDialog(Context context, String version) {
        super(context);
        this.mContext = context;
        this.version = version;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        view = View.inflate(mContext, R.layout.app_update_tishi, null);
        mTextView = (TextView) view.findViewById(R.id.app_update_dialog_text);
        clickUpdate = view.findViewById(R.id.qucliy_update_app);
        setContentView(view);
    }

    private void setStyle() {
        //设置对话框不可取消
        this.setCancelable(false);
        //设置触摸对话框外面不可取消
        this.setCanceledOnTouchOutside(false);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        //获得应用窗口大小
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        //设置对话框居中显示
        layoutParams.gravity = Gravity.CENTER;
        int width = mContext.getResources().getDimensionPixelSize(R.dimen.dp_245);
        int height = mContext.getResources().getDimensionPixelSize(R.dimen.dp_300);
        layoutParams.height = height;
        layoutParams.width = width;

    }

    public void initData() {
        mTextView.setText("发现新版本v" + version);
    }

    public void initListen() {
        clickUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qucliy_update_app:
                AppUpdate appUpdate = new AppUpdate(mContext);
                appUpdate.download();
                this.cancel();
                break;
        }
    }
}
