package com.em.utils;
import android.graphics.Color;
import android.widget.TextView;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/12/9 0009 16:31
 */
public class LoginSelectMenu {

    public static final String PWD_LOGIN = "0X00";     //密码登录
    public static final String YZM_LOGIN = "0X01";     //验证码登录


    public static void selectMenu(TextView pwdLogin,TextView pwdLoginXHX,TextView yzmLogin,TextView yzmLoginXHX,String selector){

        switch (selector){


            case LoginSelectMenu.PWD_LOGIN:
                //设置选中的
                pwdLogin.setTextColor(Color.parseColor("#06C061"));
                pwdLoginXHX.setBackgroundColor(Color.parseColor("#06C061"));
                //设置未选中的文字颜色
                yzmLogin.setTextColor(Color.parseColor("#666666"));
                //设置未选中的下划线颜色
                yzmLoginXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                break;

            case LoginSelectMenu.YZM_LOGIN:
                //设置选中的
                yzmLogin.setTextColor(Color.parseColor("#06C061"));
                yzmLoginXHX.setBackgroundColor(Color.parseColor("#06C061"));
                //设置未选中的文字颜色
                pwdLogin.setTextColor(Color.parseColor("#666666"));
                //设置未选中的下划线颜色
                pwdLoginXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                break;
            default:
                break;
        }
    }
}
