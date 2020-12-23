package com.em.home_earning;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.adapter.CumulativeMenuItemAdapter;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.SpCateConstant;
import com.em.fragment.AllEarningFragment;
import com.em.fragment.EarningFragment;
import com.em.home_customer.CumulativeCustomerActivity;
import com.em.home_order.CumulativeOrderActivity;
import com.em.pojo.HomeEntity;
import com.em.utils.ResyclerViewLayoutManger;
import com.em.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/28 0028 11:22
 */
public class CumulativeEarningActivity extends BaseActivity<CumulativeEarningPersent> {

    private static final String TAG = "CumulativeEarningActivi";
    private Context context = CumulativeEarningActivity.this;

    private TextView customer,order;
    private LinearLayout cumucativeCustomer,cumucativeOrder;
    private RecyclerView cumulativeMenuItem;
    public List<String> dateMenu = new ArrayList<>();


    private String[] dateStr = {"全部","今日","昨日","近七日"};

    public List<String> initDateMenu(String[] strings){
        for (int i=0;i<strings.length;i++){
            System.out.println(strings[i]);
            dateMenu.add(strings[i]);
        }
        return dateMenu;
    }

    @Override
    public void initView() {
        order = findViewById(R.id.order);
        customer = findViewById(R.id.customer);
        cumucativeCustomer = findViewById(R.id.cumucative_customer);
        cumucativeOrder = findViewById(R.id.cumucative_order);
        cumulativeMenuItem = findViewById(R.id.cumuative_menu_item);


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

        List<String> list = initDateMenu(dateStr);
        ResyclerViewLayoutManger manger = new ResyclerViewLayoutManger(this);
        CumulativeMenuItemAdapter adapter = new CumulativeMenuItemAdapter(list);
        //禁止水平方向滑动
        manger.setmScrollHorizontally(false);
        //水平显示
        manger.setOrientation(LinearLayoutManager.HORIZONTAL);
        cumulativeMenuItem.setLayoutManager(manger);
        cumulativeMenuItem.setAdapter(adapter);
        adapter.setOnItemClickListener(new CumulativeMenuItemAdapter.setOnItemClickMenu() {
            @Override
            public void onClick(int position, String dateMu) {
                if(position == 0){
                    replaceFragment(new AllEarningFragment());
                }
                if(position == 1){
                    replaceFragment(SpCateConstant.ToDay,new EarningFragment());
                }
                if(position == 2){
                    replaceFragment(SpCateConstant.YestDay,new EarningFragment());
                }
                if(position == 3){
                    replaceFragment(SpCateConstant.QTDay,new EarningFragment());
                }
            }
        });


    }
    @Override
    public void initListener() {
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