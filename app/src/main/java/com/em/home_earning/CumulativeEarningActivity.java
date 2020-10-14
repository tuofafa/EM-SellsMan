package com.em.home_earning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.em.R;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.SpCateConstant;
import com.em.fragment.AllEarningFragment;
import com.em.fragment.EarningFragment;
import com.em.home_customer.CumulativeCustomerActivity;
import com.em.home_order.CumulativeOrderActivity;
import com.em.pojo.HomeEntity;
import com.em.utils.SpUtils;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/28 0028 11:22
 */
public class CumulativeEarningActivity extends BaseActivity<CumulativeEarningPersent> {

    private static final String TAG = "CumulativeEarningActivi";

    private TextView allEarning,toDayEarning,yestDayEarning,qiTianEarning,customer,order;
    private LinearLayout cumucativeCustomer,cumucativeOrder;

    @Override
    public void initView() {
        allEarning = findViewById(R.id.all_earning);
        toDayEarning = findViewById(R.id.today_earning);
        yestDayEarning = findViewById(R.id.yestday_earning);
        qiTianEarning = findViewById(R.id.qitian_earning);
        customer = findViewById(R.id.customer);
        order = findViewById(R.id.order);

        cumucativeCustomer = findViewById(R.id.cumucative_customer);
        cumucativeOrder = findViewById(R.id.cumucative_order);
    }

    @Override
    public int getContextViewId() {
        return R.layout.cumulative_earning_activity;

    }

    @Override
    public void initData() {
        //默认加载时就显示这个碎片
        replaceFragment(new AllEarningFragment());
        HomeEntity homeEntity = SpUtils.getCumulativeMoney(this);
        customer.setText(homeEntity.getCumulativeUser().toString());
        order.setText(homeEntity.getCumulativeOrder().toString());
    }
    @Override
    public void initListener() {
        allEarning.setOnClickListener(this);
        toDayEarning.setOnClickListener(this);
        yestDayEarning.setOnClickListener(this);
        qiTianEarning.setOnClickListener(this);

        cumucativeOrder.setOnClickListener(this);
        cumucativeCustomer.setOnClickListener(this);
    }

    @Override
    public void destroy() {
        finish();
    }

    @Override
    public CumulativeEarningPersent getmPersenterInstance() {
        return new CumulativeEarningPersent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_earning:  //全部
                replaceFragment(new AllEarningFragment());
                break;
            case R.id.today_earning:    //今天
                replaceFragment(SpCateConstant.ToDay,new EarningFragment());
                break;
            case R.id.yestday_earning:  //昨天
                replaceFragment(SpCateConstant.YestDay,new EarningFragment());
                break;
            case R.id.qitian_earning:   //近七天
                replaceFragment(SpCateConstant.QTDay,new EarningFragment());
                break;
            case R.id.cumucative_customer:  //累计客户
                Intent cumuCustomer = new Intent(CumulativeEarningActivity.this, CumulativeCustomerActivity.class);
                startActivity(cumuCustomer);
                destroy();
                break;
            case R.id.cumucative_order:     //累计订单
                Intent cumuOrder = new Intent(CumulativeEarningActivity.this, CumulativeOrderActivity.class);
                startActivity(cumuOrder);
                destroy();
                break;

        }
    }
    //动态添加碎片
    public void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.shouyi_item,fragment);
        transaction.commit();
    }
    //动态添加碎片
    public void replaceFragment(Integer date,Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putInt("type",date);
        fragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.shouyi_item,fragment);
        transaction.commit();
    }

}