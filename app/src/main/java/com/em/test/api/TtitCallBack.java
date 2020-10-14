package com.em.test.api;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/20 0020 23:59
 */
public interface TtitCallBack {
    //请求成功回调
    void onSuccessRes(String res);
    //请求失败回调
    void onFailRes(Exception e);
}
