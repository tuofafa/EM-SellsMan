package com.em.test.api;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/21 0021 0:01
 */
public class Api {

    private static   OkHttpClient client;
    private static String url;
    private static Map<String,Object> param;
    private static Api api = new Api();

    private Api(){

    }
    public static Api config(String urls, Map<String,Object> params){
        client = new OkHttpClient();
        url = urls;
        param = params;
        return api;
    }

    public static void postRequest(final TtitCallBack callBack){

        JSONObject jsonObject = new JSONObject(param);
        String json = jsonObject.toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json);

        //第二步 构建request对象
        Request request = new Request.Builder()
                .url(url)
                .addHeader("contentType","application/json;charset=utf-8")
                .post(requestBody)
                .build();

        //第三步 构建Call对象
        Call call = client.newCall(request);
        //第四步 异步get请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //回调
               callBack.onFailRes(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = response.body().toString();
                //回调
                callBack.onSuccessRes(res);
            }
        });
    }
}
