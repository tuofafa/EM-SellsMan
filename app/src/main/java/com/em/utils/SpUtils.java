package com.em.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.em.pojo.BankEntity;
import com.em.pojo.HomeEntity;
import com.em.pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/23 0023 10:52
 */
public class SpUtils {

    private static SharedPreferences sp;

    //保存用户信息
    public static void putLoginInfo(Context context, User user){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("userInfo",json);
        editor.commit();
    }

    //获取用户信息
    public static User getLoginInfo(Context context){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("userInfo","null");
        Type type = new TypeToken<User>(){}.getType();
        User user = gson.fromJson(json,type);
        return user;
    }

    //保存用户的id
    public static void putLoginUserId(Context context,Integer uid){
        sp = context.getSharedPreferences("config",Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("uid",uid);
        editor.commit();
    }

    //获取用户的id
    public static Integer getLoginUserId(Context context){
        sp = context.getSharedPreferences("config",Context.MODE_MULTI_PROCESS);
        Integer uid = sp.getInt("uid",-1);
        return uid;
    }

    //存取用户的累计收益
    public static void putCumulativeMoney(Context context,  HomeEntity home){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(home);
        editor.putString("home",json);
        editor.commit();
    }
    //获取用户的累计收益
    public static HomeEntity getCumulativeMoney(Context context){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("home","null");
        Type type = new TypeToken<HomeEntity>(){}.getType();
        HomeEntity homeEntity = gson.fromJson(json,type);
        return homeEntity;
    }

    //保存用户的银行卡类型
    public static void putBankName(Context context,String bankName){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("bankName",bankName);
        editor.commit();
    }

    //获取用户的银行卡类型
    public static String getBankName(Context context){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        String bankName = sp.getString("bankName",null);
        return bankName;
    }

    //保存用户的头像
    public static void putImgPath(Context context,String imgPath){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("imgPath",imgPath);
        editor.commit();
    }

    //获取用户的头像
    public static String getImgPath(Context context){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        String imgPath = sp.getString("imgPath",null);
        return imgPath;
    }

    //保存用户的邀请码
    public static void putUserCode(Context context,String userCode){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userCode",userCode);
        editor.commit();
    }

    //获取用户的邀请码
    public static String getUserCode(Context context){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        String userCode = sp.getString("userCode",null);
        return userCode;
    }


    //保存银行卡信息
    public static void putBankInfo(Context context, BankEntity bankEntity){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bankEntity);
        editor.putString("bankInfo",json);
        editor.commit();
    }

    //获取银行卡信息
    public static BankEntity getBackInfo(Context context){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("bankInfo","null");
        Type type = new TypeToken<BankEntity>(){}.getType();
        BankEntity bankEntity = gson.fromJson(json,type);
        return bankEntity;
    }


    //保存用户的银行卡类型
    public static void putVersionInfo(Context context,String versionName){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("versionName",versionName);
        editor.commit();
    }

    //获取用户的银行卡类型
    public static String getVersionInfo(Context context){
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        String bankName = sp.getString("versionName",null);
        return bankName;
    }

}
