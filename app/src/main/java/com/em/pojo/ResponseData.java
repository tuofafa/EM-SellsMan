package com.em.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/11 0011 12:50
 */
public class ResponseData implements Serializable {
    private String backUrl;
    private String data;
    private String footer;
    private String message;
    private List<Commodity> commodities;
    private String success;
    private String total;

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Commodity> getCommodities() {
        return commodities;
    }

    public void setCommodities(List<Commodity> commodities) {
        this.commodities = commodities;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "backUrl='" + backUrl + '\'' +
                ", data='" + data + '\'' +
                ", footer='" + footer + '\'' +
                ", message='" + message + '\'' +
                ", commodities=" + commodities +
                ", success='" + success + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
