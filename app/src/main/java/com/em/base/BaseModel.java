package com.em.base;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/7 0010 16:24
 */
public class BaseModel<P extends BasePersenter>  {
    public P mPersenter;

    public BaseModel(P mPersenter){
        this.mPersenter = mPersenter;
    }

}
