package com.em.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/10/14 0014 10:08
 */
public class SystemUpdate {

    private static   DownloadManager downloadManager;
    private static long mTaskId;

    //使用系统下载器下载
    public static long downloadAPK(Context context, String versionUrl, String versionName) {
        //创建request对象
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse("http://47.111.184.21/app-release.apk"));
        //设置什么网络情况下可以下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏的标题
        request.setTitle("download");
        //设置通知栏的message
        request.setDescription("测试demo正在下载.....");
        //设置漫游状态下是否可以下载
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS,versionName);

        // 设置 Notification 信息
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);
        request.allowScanningByMediaScanner();
        request.setMimeType("application/vnd.android.package-archive");
        //获取系统服务
        downloadManager= (android.app.DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        //进行下载
        mTaskId = downloadManager.enqueue(request);

        //下载完成向系统发送一条广播
       /* this.registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));*/

       return mTaskId;
    }
}
