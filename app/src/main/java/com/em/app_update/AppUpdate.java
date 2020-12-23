package com.em.app_update;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.em.R;

import java.io.File;
import java.io.IOException;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/3 0003 16:28
 */
public class AppUpdate{
    private DownloadDialog  downloadDialog;

    private String url = "http://app.emaimed.com/app-release.apk";
    private Context context;
    private String pathstr;
    private String name = "zqy.apk";

    public AppUpdate(Context context){
        this.context = context;
    }

    private void showDialog() {
        if(downloadDialog==null){
            downloadDialog = new DownloadDialog(context);
        }
        if(!downloadDialog.isShowing()){
            downloadDialog.show();
        }
    }

    private void canceledDialog() {
        if(downloadDialog!=null&&downloadDialog.isShowing()){
            downloadDialog.dismiss();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DownloadManager.STATUS_SUCCESSFUL:     //下载完成
                    downloadDialog.setProgress(100);
                    canceledDialog();
                    Toast.makeText(context, "下载任务已经完成！", Toast.LENGTH_SHORT).show();

                    installAPK();
                    break;

                case DownloadManager.STATUS_RUNNING:        //正在下载
                    //int progress = (int) msg.obj;
                    downloadDialog.setProgress((int) msg.obj);
                    //canceledDialog();
                    break;

                case DownloadManager.STATUS_FAILED:     //下载失败
                    canceledDialog();
                    break;

                case DownloadManager.STATUS_PENDING:    //等待下载
                    showDialog();
                    break;
            }
        }
    };


    public void download() {
        showDialog();
        //最好是用单线程池，或者intentService
        new Thread(new DownLoadRunnable(context,url, handler)).start();
    }

    public class DownLoadRunnable implements Runnable {
        private String url;
        private Handler handler;
        private Context mContext;

        public DownLoadRunnable(Context context, String url, Handler handler) {
            this.mContext = context;
            this.url = url;
            this.handler = handler;
        }
        @Override
        public void run() {
            //设置线程优先级为后台，这样当多个线程并发后很多无关紧要的线程分配的CPU时间将会减少，有利于主线程的处理
            //Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            //具体下载方法
            startDownload();
        }

        private long startDownload() {
            //获得DownloadManager对象
            DownloadManager downloadManager=(DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            //获得下载id，这是下载任务生成时的唯一id，可通过此id获得下载信息
            long requestId= downloadManager.enqueue(CreateRequest(url));
            //查询下载信息方法
            queryDownloadProgress(requestId,downloadManager);
            return  requestId;
        }

        private void queryDownloadProgress(long requestId, DownloadManager downloadManager) {

            DownloadManager.Query query=new DownloadManager.Query();
            //根据任务编号id查询下载任务信息
            query.setFilterById(requestId);
            try {
                boolean isGoging=true;
                while (isGoging) {
                    Cursor cursor = downloadManager.query(query);
                    if (cursor != null && cursor.moveToFirst()) {

                        //获得下载状态
                        int state = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        switch (state) {
                            case DownloadManager.STATUS_SUCCESSFUL://下载成功
                                isGoging=false;
                                handler.obtainMessage(downloadManager.STATUS_SUCCESSFUL).sendToTarget();//发送到主线程，更新ui
                                break;
                            case DownloadManager.STATUS_FAILED://下载失败
                                isGoging=false;
                                handler.obtainMessage(downloadManager.STATUS_FAILED).sendToTarget();//发送到主线程，更新ui
                                break;

                            case DownloadManager.STATUS_RUNNING://下载中
                                /**
                                 * 计算下载下载率；
                                 */
                                int totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                                int currentSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                                int progress = (int) (((float) currentSize) / ((float) totalSize) * 100);
                                handler.obtainMessage(downloadManager.STATUS_RUNNING, progress).sendToTarget();//发送到主线程，更新ui
                                break;

                            case DownloadManager.STATUS_PAUSED://下载停止
                                handler.obtainMessage(DownloadManager.STATUS_PAUSED).sendToTarget();
                                break;

                            case DownloadManager.STATUS_PENDING://准备下载
                                handler.obtainMessage(DownloadManager.STATUS_PENDING).sendToTarget();
                                break;
                        }
                    }
                    if(cursor!=null){
                        cursor.close();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        private DownloadManager.Request CreateRequest(String url) {

            //创建下载任务
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            //移动网络情况下是否允许漫游
            request.setAllowedOverRoaming(false);
            //在通知栏中显示，默认就是显示的
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setTitle("医麦合伙人正在下载中");
            request.setDescription("正在更新中");
            request.setVisibleInDownloadsUi(true);
            //设置下载的路径
            File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), name);
            request.setDestinationUri(Uri.fromFile(file));
            pathstr = file.getAbsolutePath();
            Log.d("下载路径", "CreateRequest: "+pathstr);
            return  request;
        }
    }
    private void installAPK() {
        setPermission(pathstr);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Android 7.0以上要使用FileProvider
        if (Build.VERSION.SDK_INT >= 24) {
            File file = (new File(pathstr));
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.em.fileprovider",file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS, name)), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    //修改文件权限
    private void setPermission(String absolutePath) {
        String command = "chmod " + "777" + " " + absolutePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}