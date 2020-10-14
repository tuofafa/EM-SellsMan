package com.em.re_pwd;

import com.em.base.BaseModel;
import com.em.pojo.User;

public class ResetPasswordModel extends BaseModel<ResetPasswordPersenter> implements IResetPassword.M {
    public ResetPasswordModel(ResetPasswordPersenter mPersenter) {
        super(mPersenter);
    }

    @Override
    public void requestReset(User user) {

    }
}
