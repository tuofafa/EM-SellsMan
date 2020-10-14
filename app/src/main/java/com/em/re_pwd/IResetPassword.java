package com.em.re_pwd;

import com.em.pojo.User;

public interface IResetPassword {

    public interface M{
        void requestReset(User user);
    }
    public interface V{
        void requestReset(User user);
        void responseReset(User user);
    }
    public interface P{
        void requestReset(User user);
        void responseReset(User user);
    }
}
