package com.em.home_customer;

import com.em.base.BasePersenter;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/28 0028 9:22
 */
public class CumulativeCustomerPersent extends BasePersenter<CumulativeCustomerActivity,CumulativeCustomerModel> {
    @Override
    public CumulativeCustomerModel getmModelInstance() {
        return new CumulativeCustomerModel(this);
    }
}
