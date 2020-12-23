package com.em.app_update;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.em.R;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/3 0003 20:44
 */
public class DownloadDialog extends AlertDialog {

    private Context mContext;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private View view;

    protected DownloadDialog(Context context) {
        super(context);
        this.mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置对话框样式
        setStyle();
        //初始化控件
        initView();
    }

    @SuppressLint("CutPasteId")
    private void initView() {
        view = View.inflate(mContext, R.layout.download_dialog,null);
        mTextView = (TextView)view.findViewById(R.id.mTextView);
        mProgressBar = (ProgressBar)view.findViewById(R.id.mProgressBar);
        setContentView(view);
    }

    private void setStyle() {
        //设置对话框不可取消
        this.setCancelable(false);
        //设置触摸对话框外面不可取消
        this.setCanceledOnTouchOutside(false);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        //设置dialog 的背景色为透明
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //获得应用窗口大小
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        //设置对话框居中显示
        layoutParams.gravity = Gravity.CENTER;

        int width = mContext.getResources().getDimensionPixelSize(R.dimen.dp_220);
        int height = mContext.getResources().getDimensionPixelSize(R.dimen.dp_190);
        layoutParams.width = width;
        layoutParams.height = height;

    }

    //设置进度条
    @SuppressLint("SetTextI18n")
    public void setProgress(int progress){

        String htmlStr = "新版本已更新至<font color=\"#06C061\">"+progress+"</font>"+"%，请稍等";

        mTextView.setText(Html.fromHtml(htmlStr));

        mProgressBar.setProgress(progress);
    }
}
