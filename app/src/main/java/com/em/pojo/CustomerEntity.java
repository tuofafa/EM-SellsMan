package com.em.pojo;

import java.io.Serializable;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/3 0003 11:14
 * discrption 我的客户
 */
public class CustomerEntity implements Serializable {

    private String memberName;     //客户名称
    private String moneyAll;       //成交金额
    private String moneyGet;       //总返佣
    private String orderNum;       //订单数量
    private String referrerCode;   //推荐码
    private String createTime;     //最近下单时间

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMoneyAll() {
        return moneyAll;
    }

    public void setMoneyAll(String moneyAll) {
        this.moneyAll = moneyAll;
    }

    public String getMoneyGet() {
        return moneyGet;
    }

    public void setMoneyGet(String moneyGet) {
        this.moneyGet = moneyGet;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getReferrerCode() {
        return referrerCode;
    }

    public void setReferrerCode(String referrerCode) {
        this.referrerCode = referrerCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
