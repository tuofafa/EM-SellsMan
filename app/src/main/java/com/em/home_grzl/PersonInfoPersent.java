package com.em.home_grzl;

import com.em.base.BasePersenter;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/12 0012 17:28
 */
public class PersonInfoPersent extends BasePersenter<PersonInfoActivity,PersonInfoModel> implements IPersonInfo.P {
    @Override
    public PersonInfoModel getmModelInstance() {
        return new PersonInfoModel(this);
    }
}
