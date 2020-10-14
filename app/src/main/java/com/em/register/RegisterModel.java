package com.em.register;

import com.em.base.BaseModel;
import com.em.pojo.User;

public class RegisterModel extends BaseModel<RegisterPersenter> implements IRegister.M {
    public RegisterModel(RegisterPersenter mPersenter) {
        super(mPersenter);
    }

    @Override
    public void requestRegisterInfo(User user) {

    }
}
