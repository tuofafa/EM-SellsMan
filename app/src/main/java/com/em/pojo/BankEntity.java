package com.em.pojo;

import java.io.Serializable;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/29 0029 16:28
 */
public class BankEntity implements Serializable {

    private String bankCode;
    private String bankName;
    private String bankStatus;
    private String name;
    private String bankAdd;
    private String cid;

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

    public String getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(String bankStatus) {
        this.bankStatus = bankStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankAdd() {
        return bankAdd;
    }

    public void setBankAdd(String bankAdd) {
        this.bankAdd = bankAdd;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "BankEntity{" +
                "bankCode='" + bankCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankStatus='" + bankStatus + '\'' +
                ", name='" + name + '\'' +
                ", bankAdd='" + bankAdd + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}
