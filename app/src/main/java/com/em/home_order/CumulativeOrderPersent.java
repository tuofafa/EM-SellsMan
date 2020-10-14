package com.em.home_order;

import com.em.base.BasePersenter;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/25 0025 13:51
 */
public class CumulativeOrderPersent extends BasePersenter<CumulativeOrderActivity,CumulativeOrderModel> {


    @Override
    public CumulativeOrderModel getmModelInstance() {
        return new CumulativeOrderModel(this);
    }
}
