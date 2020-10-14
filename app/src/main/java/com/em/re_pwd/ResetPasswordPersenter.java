package com.em.re_pwd;
import com.em.base.BasePersenter;
import com.em.pojo.User;

public class ResetPasswordPersenter extends BasePersenter implements IResetPassword.P {
    @Override
    public ResetPasswordModel getmModelInstance() {
        return new ResetPasswordModel(this);
    }

    @Override
    public void requestReset(User user) {

    }

    @Override
    public void responseReset(User user) {

    }
}
