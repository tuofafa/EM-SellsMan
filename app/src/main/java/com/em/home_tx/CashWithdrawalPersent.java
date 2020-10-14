package com.em.home_tx;

import com.em.base.BasePersenter;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/13 0013 12:22
 */
public class CashWithdrawalPersent extends BasePersenter<CashWithdrawalActivity,CashWithdrawalModel> implements ICashWithdrawal.P {

    @Override
    public CashWithdrawalModel getmModelInstance() {
        return new CashWithdrawalModel(this);
    }

}
