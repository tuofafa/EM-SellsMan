package com.em.login;

import com.em.pojo.User;

import java.io.IOException;
/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/10 0010 9:24
 */
public interface ILogin {

    public interface M{
        void requestLogin(User user);
    }

    public interface V{
        void requestLogin(User user) throws IOException;
        User responseLogin();

    }
    public interface P{
        void requestLogin(User user) throws IOException;
        User responseLogin(String user);
    }



}
