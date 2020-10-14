package com.em.pojo;

import android.content.Intent;

import java.io.Serializable;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/25 0025 16:10
 */
public class OrderEntity implements Serializable {

    private Integer uid;            //用户id
    private String yognJinStatus;  //佣金状态
    private String imgPath;         //图片路径
    private String discrption;      //产品描述
    private String yjMoney;          //佣金
    private String butName;         //购买客户
    private String buyDate;         //购买时间

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDiscrption() {
        return discrption;
    }

    public void setDiscrption(String discrption) {
        this.discrption = discrption;
    }

    public String getButName() {
        return butName;
    }

    public void setButName(String butName) {
        this.butName = butName;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getYognJinStatus() {
        return yognJinStatus;
    }

    public void setYognJinStatus(String yognJinStatus) {
        this.yognJinStatus = yognJinStatus;
    }

    public String getYjMoney() {
        return yjMoney;
    }

    public void setYjMoney(String yjMoney) {
        this.yjMoney = yjMoney;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "uid=" + uid +
                ", yognJinStatus='" + yognJinStatus + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", discrption='" + discrption + '\'' +
                ", yjMoney='" + yjMoney + '\'' +
                ", butName='" + butName + '\'' +
                ", buyDate='" + buyDate + '\'' +
                '}';
    }
}
