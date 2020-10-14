package com.em.home_grzl;

import com.em.base.BaseModel;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/12 0012 17:28
 */
public class PersonInfoModel extends BaseModel<PersonInfoPersent> implements IPersonInfo.M {

    public PersonInfoModel(PersonInfoPersent mPersenter) {
        super(mPersenter);
    }
}
