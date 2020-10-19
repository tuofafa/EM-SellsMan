package com.em.utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.em.pojo.User;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/11 0011 9:04
 */
public class NetWorkUtil {

    //public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    //登录的Post方法
    public static String requestLoginPost(String url,User user){

        OkHttpClient client = new OkHttpClient();
        RequestBody body1 = new FormBody.Builder()
                .add("userName",user.getAccountName())
                .add("password",user.getPassword())
                .add("ip",user.getIpAddress())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body1)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //注册的Post方法
    public static String requestRegisterPost(String url,User user){

        OkHttpClient client = new OkHttpClient();
        RequestBody body1 = new FormBody.Builder()
                .add("userName",user.getAccountName())
                .add("password",user.getPassword())
                .add("smsCode",user.getVerificationCode())
                .add("phone",user.getPhoneNum())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body1)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //发送短信的验证码消息
    public static String requestSendMSG(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }catch (IOException e){
            e.printStackTrace();
        }
        return url;
    }

    //get请求方式
    public static String requestGet(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }catch (IOException e){
            e.printStackTrace();
        }
        return url;
    }

    //修改个人信息--昵称
    public static String requestModifyPersonNickname(String url, Map<String,String> map){
        OkHttpClient client = new OkHttpClient();
        RequestBody body1 = new FormBody.Builder()
                .add("memberId",map.get("uid"))
                .add("nickname",map.get("nickname"))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body1)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //修改个人信息--手机号
    public static String requestModifyPersonPhone(String url, Map<String,String> map){
        OkHttpClient client = new OkHttpClient();
        RequestBody body1 = new FormBody.Builder()
                .add("memberId",map.get("uid"))
                .add("phone",map.get("phone"))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body1)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //修改个人信息--头像图片地址
    public static String requestModifyImg(String url,Integer uid,String imgPath){
        OkHttpClient client = new OkHttpClient();
        RequestBody body1 = new FormBody.Builder()
                .add("memberId",uid+"")
                .add("headimg",imgPath)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body1)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //修改个人信息--微信号
    public static String requestModifyPersonWeChat(String url, Map<String,String> map){
        OkHttpClient client = new OkHttpClient();
        RequestBody body1 = new FormBody.Builder()
                .add("memberId",map.get("uid"))
                .add("weixin",map.get("weChat"))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body1)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //密码重置接口
    public static String requestResetPwd(String url, Map<String,String> map){
        OkHttpClient client = new OkHttpClient();
        RequestBody body1 = new FormBody.Builder()
                .add("name",map.get("name"))
                .add("mobile",map.get("mobile"))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body1)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //添加银行卡
    public static String requestAddBankCard(String url, Map<String,String> map){
        OkHttpClient client = new OkHttpClient();
        RequestBody body1 = new FormBody.Builder()
                .add("memberId",map.get("uid"))                          //用户id
                .add("certificateCode",map.get("cid"))                  // 身份证号
                .add("bankAdd",map.get("bankAdd"))                       // 开户行地址
                .add("bankCode",map.get("bankCode"))                      // 银行卡号
                .add("bankName",map.get("bankName"))                      // 持卡人姓名
                .add("bankType",map.get("bankType"))                      // 开户类型
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body1)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //提现接口
    public static String requestTXMoney(String url,Integer uid){
        OkHttpClient client = new OkHttpClient();
        RequestBody body1 = new FormBody.Builder()
                .add("memberId",uid+"")                          //用户id
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body1)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //上传用户头像接口
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String requestUploadPic(Integer uid, String url, String imgPath){
        OkHttpClient client = new OkHttpClient();
        File file = new File(imgPath);
        RequestBody body = RequestBody.create(MediaType.parse("image/png"),file);

        RequestBody requestBody1 = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("memberId",uid+"")
                .addFormDataPart("pic",imgPath,body)
                .build();

        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("memberId",uid+"")
                .addFormDataPart("pic",imgPath,body)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
