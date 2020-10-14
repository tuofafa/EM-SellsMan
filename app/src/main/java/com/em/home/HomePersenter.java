package com.em.home;

import com.em.base.BasePersenter;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/8 0010 16:24
 */
public class HomePersenter extends BasePersenter<HomeActivity,HomeModel> implements IHome.V,IHome.M  {
    @Override
    public HomeModel getmModelInstance() {
        return new HomeModel(this);
    }
}
