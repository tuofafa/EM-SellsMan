package com.em.login;

import com.em.base.BasePersenter;
import com.em.pojo.User;
/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/10 0010 9:24
 */
public class LoginPersenter extends BasePersenter<LoginActivity,LoginModel> implements ILogin.P {
    @Override
    public LoginModel getmModelInstance() {
        return new LoginModel(this) ;
    }

    @Override
    public void requestLogin(User user) {
        mModel.requestLogin(user);
    }

    @Override
    public User responseLogin(String loginInfo) {
        if(loginInfo.equals("") && loginInfo.equals(null)){
            //解析json 封装成User
            System.out.println("mPersent: "+loginInfo);

        }
        return  null;
    }

}
