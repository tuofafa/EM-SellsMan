package com.em.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/10/18 0018 10:00
 */
public class SavePicture {
    public static boolean SaveJpg(ImageView view, Context context) {

        try{
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

            if(fileUri == null){
                //LogHelper.ShowLog("fileUri == null");
                Toast.makeText(context,"null",Toast.LENGTH_SHORT).show();
                return false;
            }

            OutputStream outStream = view.getContext().getContentResolver().openOutputStream(fileUri);

            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();

            view.getContext().sendBroadcast(new Intent("com.android.camera.NEW_PICTURE", fileUri));
            //Toast.makeText(context,"保存图片到相册完毕",Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
