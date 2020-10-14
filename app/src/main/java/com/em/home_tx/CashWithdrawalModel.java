package com.em.home_tx;

import com.em.base.BaseModel;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/13 0013 12:22
 */
public class CashWithdrawalModel extends BaseModel<CashWithdrawalPersent> implements ICashWithdrawal.M{
    public CashWithdrawalModel(CashWithdrawalPersent mPersenter) {
        super(mPersenter);
    }
}
