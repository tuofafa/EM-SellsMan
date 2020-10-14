package com.em.tx_record;

import com.em.base.BasePersenter;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/30 0030 16:12
 * discrption 提现记录
 */
public class EarnRecordPresent extends BasePersenter<EarnRecordActivity,EarnRecordModel> {
    @Override
    public EarnRecordModel getmModelInstance() {
        return new EarnRecordModel(this);
    }
}
