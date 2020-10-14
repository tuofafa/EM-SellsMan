package com.em.modify;

import com.em.base.BasePersenter;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/23 0023 14:51
 */
public class ModifyPersonPersent extends BasePersenter<ModifyPersonActivity,ModifyPersonModel> {
    @Override
    public ModifyPersonModel getmModelInstance() {
        return new ModifyPersonModel(this);
    }
}
