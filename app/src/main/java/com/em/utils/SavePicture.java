package com.em.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

import static android.view.View.DRAWING_CACHE_QUALITY_HIGH;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/10/18 0018 10:00
 */
public class SavePicture {

    //保存图片到相册
    public static boolean SaveJpg(ImageView view, Context context) {

        try {
            Drawable drawable = view.getDrawable();
            if (drawable == null) {
                return false;
            }

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

            Uri dataUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri fileUri = view.getContext().getContentResolver().insert(dataUri, values);

            // 如果保存不成功，insert没有任何错误信息，此时调用update会有错误信息提示
//            view.getContext().getContentResolver().update(dataUri, values, "", null);

            if (fileUri == null) {
                //LogHelper.ShowLog("fileUri == null");
                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                return false;
            }

            OutputStream outStream = view.getContext().getContentResolver().openOutputStream(fileUri);

            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();

            view.getContext().sendBroadcast(new Intent("com.android.camera.NEW_PICTURE", fileUri));
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    //将以给布局转换成一个bitmap
    public static Bitmap getLinearLayoutBitmap2(LinearLayout linearLayout, int screenWidth, int screenHeight) {


        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        linearLayout.measure(width, height);

        int w = linearLayout.getMeasuredWidth(); // 获取宽度;
        int h = linearLayout.getMeasuredHeight(); // 获取高度 ;
        Log.d("ceshi", "getLinearLayoutBitmap2: " + w + "    " + h);
        // 创建对应大小的bitmap
        // linearLayout.measure(0, 0);
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        linearLayout.draw(canvas);
        return bitmap;
    }

    //保存图片到相册
    public static Uri saveBitmap(Bitmap bitmap, Context context) {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

            Uri dataUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri fileUri = context.getContentResolver().insert(dataUri, values);
            System.out.println(""+fileUri);
            if (fileUri == null) {
                return null;
            }
            OutputStream outStream = context.getContentResolver().openOutputStream(fileUri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            context.sendBroadcast(new Intent("com.android.camera.NEW_PICTURE", fileUri));
            return fileUri;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap takeScreenShotOfView(View view) {
       /* Bitmap b = Bitmap.createBitmap(v.getDrawingCache());

        v.setDrawingCacheEnabled(false);
        return b;*/

        DisplayMetrics metrics = new DisplayMetrics();


        Log.d("height ", "view高度: " + view.getMeasuredHeight());
        Log.d("width ", "view宽度: " + view.getMeasuredWidth());
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache(true);
        view.setDrawingCacheQuality(DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public static void layoutView(View v, int width, int height) {

        // 指定整个View的大小 参数是左上角 和右下角的坐标
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
        /** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
         * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。

         */
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Log.d("getMeasuredWidth", "layoutView: "+v.getMeasuredWidth());
        Log.d("hei", "layoutView: "+v.getWidth());
    }


    public static Bitmap createBitmapFromView(View view) {
        Bitmap bitmap = null;
        //开启view缓存bitmap
        view.setDrawingCacheEnabled(true);
        //设置view缓存Bitmap质量
        view.setDrawingCacheQuality(DRAWING_CACHE_QUALITY_HIGH);
        //获取缓存的bitmap
        Bitmap cache = view.getDrawingCache();
        if (cache != null && !cache.isRecycled()) {
            bitmap = Bitmap.createBitmap(cache);
        }
        //销毁view缓存bitmap
        view.destroyDrawingCache();
        Log.d("55", "createBitmapFromView: "+view.getWidth());
        //关闭view缓存bitmap
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }

    public static Bitmap loadBitmapFromView(View v) {

        int w = v.getWidth();

        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);

        Canvas c=new Canvas(bmp);

        c.drawColor(Color.WHITE);

/** 如果不设置canvas画布为白色，则生成透明 */
        v.layout(0,0,w,h);
        v.draw(c);
        return bmp;
    }
}
