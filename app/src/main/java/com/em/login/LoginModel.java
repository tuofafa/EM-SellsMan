package com.em.login;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.em.R;
import com.em.base.BaseModel;
import com.em.config.URLConfig;
import com.em.pojo.User;
import com.em.utils.NetWorkUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/10 0010 9:24
 */
public class LoginModel extends BaseModel<LoginPersenter> implements ILogin.M {

    private static String loginInfo = "";

    OkHttpClient client = new OkHttpClient();


    public LoginModel(LoginPersenter mPersenter) {
        super(mPersenter);
    }

    @Override
    public void requestLogin(User user) {
        //请求后天服务器返回数据在传送给Persenter
       // mPersenter.responseLogin(loginInfo);
    }



}
