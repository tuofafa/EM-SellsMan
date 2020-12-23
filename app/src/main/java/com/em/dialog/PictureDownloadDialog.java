package com.em.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.em.R;
import com.em.app_update.AppUpdate;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/26 0026 10:36
 * discrption APP 更新类提示dialog
 */
public class PictureDownloadDialog extends AlertDialog implements View.OnClickListener {

    private Context mContext;
    private View view;
    private String totalNum;
    private TextView currentNumText,totalNumText;

    public PictureDownloadDialog(Context context,String totalNum) {
        super(context);
        this.mContext = context;
        this.totalNum = totalNum;
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
        view = View.inflate(mContext, R.layout.picture_download_dialog, null);
        setContentView(view);

        currentNumText = view.findViewById(R.id.current_item_num);
        totalNumText = view.findViewById(R.id.total_num);

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

        //设置透明度 范围是0-1，0表示完全透明，1表示完全不透明
        layoutParams.alpha = 0.5f;
        window.setAttributes(layoutParams);
    }

    //更新进度条的数据
    public void setCurrentNum(String currentNum){
        currentNumText.setText(currentNum);
    }

    public void initData() {
        totalNumText.setText(totalNum);
    }

    public void initListen() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
