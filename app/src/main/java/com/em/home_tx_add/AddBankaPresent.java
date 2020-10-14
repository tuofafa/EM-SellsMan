package com.em.home_tx_add;

import com.em.base.BasePersenter;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/29 0029 11:04
 */
public class AddBankaPresent extends BasePersenter<AddBankActivity,AddBankaModel> {

    @Override
    public AddBankaModel getmModelInstance() {
        return new AddBankaModel(this);
    }
}
