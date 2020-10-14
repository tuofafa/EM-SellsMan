package com.em.pojo;

import java.io.Serializable;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/30 0030 15:07
 * discrption 提现接口返回信息
 */
public class EarnEntity implements Serializable {

    private String bankAdd;             //开户行地址
    private String bankCode;            //银行卡号
    private String bankName;            //持卡人名
    private String bankType;            //开户银行
    private String certificateCode;     //身份证
    private String createTime;          //提现时间
    private String memberId;            //用户id
    private String memberName;          //用户名
    private String money;               //提现金额
    private String status;              //提现状态   0 已提交提现申请  1 提现申请已审核


    public String getBankAdd() {
        return bankAdd;
    }

    public void setBankAdd(String bankAdd) {
        this.bankAdd = bankAdd;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EarnEntity{" +
                "bankAdd='" + bankAdd + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankType='" + bankType + '\'' +
                ", certificateCode='" + certificateCode + '\'' +
                ", createTime='" + createTime + '\'' +
                ", memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", money='" + money + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
