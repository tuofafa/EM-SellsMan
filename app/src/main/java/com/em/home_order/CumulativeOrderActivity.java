package com.em.home_order;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.em.R;
import com.em.base.BaseActivity;
import com.em.fragment.AllOrderFragment;
import com.em.fragment.AlreadyCarryOrderFragment;
import com.em.fragment.CanCarryOrderFragment;
import com.em.fragment.CarryingOrderFragment;
import com.em.fragment.ExceptOrderFragment;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/25 0025 13:51
 * discrption 累计订单
 */
public class CumulativeOrderActivity extends BaseActivity<CumulativeOrderPersent> {

    private static final String TAG = "CumulativeOrderActivity";

    private ImageView imageDate;
    private TextView allOrder;
    private TextView yjsyOrder;
    private TextView ktxOrder;
    private TextView txzOrder;
    private TextView ytxOrder;


    @Override
    public void initView() {
        allOrder = findViewById(R.id.all_order);
        ktxOrder = findViewById(R.id.order_ktx);
        txzOrder = findViewById(R.id.order_txz);
        yjsyOrder = findViewById(R.id.order_yjsy);
        ytxOrder = findViewById(R.id.order_ytx);
    }

    @Override
    public int getContextViewId() {
        return R.layout.cumulative_order_activity;
    }

    @Override
    public void initData() {
        replaceFragment(new AllOrderFragment());
    }

    @Override
    public void initListener() {
        allOrder.setOnClickListener(this);
        ktxOrder.setOnClickListener(this);
        ytxOrder.setOnClickListener(this);
        yjsyOrder.setOnClickListener(this);
        txzOrder.setOnClickListener(this);
    }

    @Override
    public void destroy() {

    }

    @Override
    public CumulativeOrderPersent getmPersenterInstance() {
        return new CumulativeOrderPersent();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_order:        //全部
                replaceFragment(new AllOrderFragment());
                break;
            case R.id.order_yjsy:       //预计收益
                replaceFragment(new ExceptOrderFragment());
                break;
            case R.id.order_ktx:        //可提现
                replaceFragment(new CanCarryOrderFragment());
                break;
            case R.id.order_txz:        //提现中
                replaceFragment(new CarryingOrderFragment());
                break;
            case R.id.order_ytx:        //已提现
                replaceFragment(new AlreadyCarryOrderFragment());
                break;
        }
    }

    //动态添加碎片
    public void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.order_frameLayout,fragment);
        transaction.commit();
    }

}
