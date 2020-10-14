package com.em.pojo;

import java.io.Serializable;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/27 0027 14:40
 */
public class HomeEntity implements Serializable {

    private Integer cumulativeOrder;    //累计订单
    private Integer cumulativeUser;     //累计用户
    private Float cumulativeMoney;      //累计收益  4
    private Float canCarryMoney;        //可提现金额 2
    private Float carryingMoney;        //提现中  3
    private Float exceptMoney;          //预计收益 1
    private Float toDayCumuMoney;  //今日累计收益
    private Float yestDayCumuMoney ;    //昨日累计收益
    private Float weekCumuMoney;        //一周累计收益

    public Integer getCumulativeOrder() {
        return cumulativeOrder;
    }

    public void setCumulativeOrder(Integer cumulativeOrder) {
        this.cumulativeOrder = cumulativeOrder;
    }

    public Integer getCumulativeUser() {
        return cumulativeUser;
    }

    public void setCumulativeUser(Integer cumulativeUser) {
        this.cumulativeUser = cumulativeUser;
    }

    public Float getCumulativeMoney() {
        return cumulativeMoney;
    }

    public void setCumulativeMoney(Float cumulativeMoney) {
        this.cumulativeMoney = cumulativeMoney;
    }

    public Float getCanCarryMoney() {
        return canCarryMoney;
    }

    public void setCanCarryMoney(Float canCarryMoney) {
        this.canCarryMoney = canCarryMoney;
    }

    public Float getCarryingMoney() {
        return carryingMoney;
    }

    public void setCarryingMoney(Float carryingMoney) {
        this.carryingMoney = carryingMoney;
    }

    public Float getExceptMoney() {
        return exceptMoney;
    }

    public void setExceptMoney(Float exceptMoney) {
        this.exceptMoney = exceptMoney;
    }

    public Float getToDayCumuMoney() {
        return toDayCumuMoney;
    }

    public void setToDayCumuMoney(Float toDayCumuMoney) {
        this.toDayCumuMoney = toDayCumuMoney;
    }

    public Float getYestDayCumuMoney() {
        return yestDayCumuMoney;
    }

    public void setYestDayCumuMoney(Float yestDayCumuMoney) {
        this.yestDayCumuMoney = yestDayCumuMoney;
    }

    public Float getWeekCumuMoney() {
        return weekCumuMoney;
    }

    public void setWeekCumuMoney(Float weekCumuMoney) {
        this.weekCumuMoney = weekCumuMoney;
    }

    @Override
    public String toString() {
        return "HomeEntity{" +
                "cumulativeOrder=" + cumulativeOrder +
                ", cumulativeUser=" + cumulativeUser +
                ", cumulativeMoney=" + cumulativeMoney +
                ", canCarryMoney=" + canCarryMoney +
                ", carryingMoney=" + carryingMoney +
                ", exceptMoney=" + exceptMoney +
                ", toDayCumuMoney=" + toDayCumuMoney +
                ", yestDayCumuMoney=" + yestDayCumuMoney +
                ", weekCumuMoney=" + weekCumuMoney +
                '}';
    }
}
