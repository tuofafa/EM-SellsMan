package com.em.test;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/20 0020 22:48
 */
public class Test {
    String url = "https://www.baidu.com/sugrec?prod=pc_his&from=pc_web&json=1&sid=32617_1453_32744_7567_31253_7580_32705_7621_32116&hisdata=%5B%7B%22time%22%3A1598851902%2C%22kw%22%3A%22%E5%AE%89%E5%8D%93%E5%BC%80%E5%8F%91%E7%9A%84%E4%B8%BB%E6%B5%81%E6%96%B9%E5%BC%8F%22%7D%2C%7B%22time%22%3A1598862318%2C%22kw%22%3A%22super.oncreate(savedinstancestate)%3B%22%7D%2C%7B%22time%22%3A1598865600%2C%22kw%22%3A%22android%20resources%20linking%20failed%22%7D%2C%7B%22time%22%3A1598920786%2C%22kw%22%3A%22android%2025api%20%E5%AF%BC%E5%85%A5%E9%97%AE%E9%A2%98%22%7D%2C%7B%22time%22%3A1598921518%2C%22kw%22%3A%22noinspection%20gradlecompatible%22%7D%2C%7B%22time%22%3A1598923388%2C%22kw%22%3A%22stackoverflow%22%7D%2C%7B%22time%22%3A1598924736%2C%22kw%22%3A%22android%E5%BC%80%E5%8F%91%E7%A4%BE%E5%8C%BA%22%7D%2C%7B%22time%22%3A1598939249%2C%22kw%22%3A%22android%20%E5%AF%BC%E5%85%A5%E4%B8%80%E4%B8%AA%E8%80%81%E9%A1%B9%E7%9B%AE%22%7D%2C%7B%22time%22%3A1598945732%2C%22kw%22%3A%22android%E5%BC%80%E5%8F%91%E6%A1%86%E6%9E%B6mvp%22%7D%2C%7B%22time%22%3A1598949552%2C%22kw%22%3A%22%E7%AC%AC%E4%B8%80%E8%A1%8C%E4%BB%A3%E7%A0%81%22%7D%5D&_t=1600617379014&req=2&bs=%E5%8C%97%E4%BA%AC%E5%A4%A9%E6%B0%94%E9%A2%84%E6%8A%A5&csor=0";
    private void get(){

        //第一步 获取OkhttpClient对象
        OkHttpClient client = new OkHttpClient();

        //第二步 构建request对象
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        //第三步 构建Call对象
        Call call = client.newCall(request);
        //第四步 异步get请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("s", "onFailure: ");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = response.body().toString();
                Log.i("s", "onResponse: "+res);


            }
        });
    }
}
