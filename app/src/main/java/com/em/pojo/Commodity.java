package com.em.pojo;

import java.io.Serializable;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/19 0019 22:56
 */
public class Commodity implements Serializable {

    private Integer id;         //商品id
    private String description; //商品描述
    private Float marktPrice;   //商品价格
    private String masterImg;   //商品图片路径
    private String name;        //商品名字
    private Float saleScale;    //分销比例

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getMarktPrice() {
        return marktPrice;
    }

    public void setMarktPrice(Float marktPrice) {
        this.marktPrice = marktPrice;
    }

    public String getMasterImg() {
        return masterImg;
    }

    public void setMasterImg(String masterImg) {
        this.masterImg = masterImg;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Float getSaleScale() {
        return saleScale;
    }

    public void setSaleScale(Float saleScale) {
        this.saleScale = saleScale;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", marktPrice=" + marktPrice +
                ", masterImg='" + masterImg + '\'' +
                ", name='" + name + '\'' +
                ", saleScale=" + saleScale +
                '}';
    }
}
