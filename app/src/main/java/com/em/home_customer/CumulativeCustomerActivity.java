package com.em.home_customer;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.adapter.CustomerAdapter;
import com.em.base.BaseActivity;
import com.em.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/28 0028 9:22
 * discrption  累计客户
 */
public class CumulativeCustomerActivity extends BaseActivity<CumulativeCustomerPersent> {
    private RecyclerView customerRecyclerView;

    @Override
    public void initView() {
        customerRecyclerView = findViewById(R.id.customer_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        CustomerAdapter adapter = new CustomerAdapter(initUser());
        customerRecyclerView.setLayoutManager(manager);
        customerRecyclerView.setAdapter(adapter);
    }

    public List<User> initUser(){
        List<User> users = new ArrayList<>() ;
        for(int i=0;i<10;i++){
            User user = new User();
            users.add(user);
        }
        return users;
    }

    @Override
    public int getContextViewId() {
        return R.layout.cumulative_customer_activity;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public CumulativeCustomerPersent getmPersenterInstance() {
        return new CumulativeCustomerPersent();
    }

    @Override
    public void onClick(View v) {

    }
}
