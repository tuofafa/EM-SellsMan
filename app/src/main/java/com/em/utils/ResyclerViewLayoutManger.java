package com.em.utils;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/19 0019 16:09
 */
public class ResyclerViewLayoutManger extends LinearLayoutManager {

    private boolean mCanVerticalScroll = true;

    public ResyclerViewLayoutManger(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        if (!mCanVerticalScroll){
            return false;
        }else {
            return super.canScrollVertically();
        }
    }

    @Override
    public boolean canScrollHorizontally() {
        if (!mCanVerticalScroll){
            return false;
        }else {
            return super.canScrollVertically();
        }
    }

    public void setmCanVerticalScroll(boolean b){
        mCanVerticalScroll = b;
    }
    public void setmScrollHorizontally(boolean b){
        mCanVerticalScroll = b;
    }

}
