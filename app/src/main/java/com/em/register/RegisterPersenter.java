package com.em.register;

import com.em.base.BasePersenter;
import com.em.pojo.User;

public class RegisterPersenter extends BasePersenter<RegisterActivity,RegisterModel> implements IRegister.P {
    @Override
    public RegisterModel getmModelInstance() {
        return new RegisterModel(this);
    }

    @Override
    public void requestRegisterInfo(User user) {

    }

    @Override
    public void responseRegisterInfo(User user) {

    }
}
