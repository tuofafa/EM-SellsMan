package com.em.register;

import com.em.pojo.User;

public interface IRegister {

    public interface M{
        void requestRegisterInfo(User user);
    }
    public interface V{
        void requestRegisterInfo(User user);
        void responseRegisterInfo(User user);
    }
    public interface P{
        void requestRegisterInfo(User user);
        void responseRegisterInfo(User user);
    }
}
