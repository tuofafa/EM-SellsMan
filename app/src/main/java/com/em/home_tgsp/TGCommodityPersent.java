package com.em.home_tgsp;

import com.em.base.BasePersenter;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/17 0017 9:14
 */
public class TGCommodityPersent extends BasePersenter<TGCommodityActivity,TGCommodityModel> implements ICommodity.P {
    @Override
    public TGCommodityModel getmModelInstance() {
        return new TGCommodityModel(this);
    }
}
