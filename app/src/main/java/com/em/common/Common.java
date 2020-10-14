package com.em.common;

import android.content.Context;
import android.widget.Toast;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/22 0022 10:42
 * discrption 封装一些常用的方法
 */

public class Common{

    //toast
    public static void showToast(Context context, String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }
}
