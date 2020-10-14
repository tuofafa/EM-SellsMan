package com.em.api;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/21 0021 9:03
 * discrption 请求数据接口回调
 */
public interface RequestCallBack {

    void onSuccess(String res);

    void onFaild(Exception e);
}
