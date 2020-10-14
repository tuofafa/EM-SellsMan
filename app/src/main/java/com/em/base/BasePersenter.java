package com.em.base;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/7 0010 16:24
 */
public abstract class BasePersenter<V extends BaseActivity,M extends BaseModel> {

    public V mView;

    public M mModel;

    public BasePersenter(){
        this.mModel = getmModelInstance();
    }
/*******   和activity建立关系  ******/
    //绑定View
    public void bindView(V mView){
        this.mView = mView;
    }
    //解绑
    public void unBindView(){
        this.mView = null;
    }

    /***********   和Model建立关系   ******/
    public abstract M getmModelInstance();

}
