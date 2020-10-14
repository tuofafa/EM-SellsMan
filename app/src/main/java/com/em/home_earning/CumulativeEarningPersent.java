package com.em.home_earning;

import com.em.base.BasePersenter;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/28 0028 11:22
 */
public class CumulativeEarningPersent extends BasePersenter<CumulativeEarningActivity,CumulativeEarningModel> {
    @Override
    public CumulativeEarningModel getmModelInstance() {
        return new CumulativeEarningModel(this);
    }
}
