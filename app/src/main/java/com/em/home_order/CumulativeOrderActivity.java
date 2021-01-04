package com.em.home_order;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.adapter.CumuOrderMenuItemAdapter;
import com.em.base.BaseActivity;
import com.em.fragment.AllOrderFragment;
import com.em.fragment.AlreadyCarryOrderFragment;
import com.em.fragment.CanCarryOrderFragment;
import com.em.fragment.CarryingOrderFragment;
import com.em.fragment.ExceptOrderFragment;
import com.em.utils.HorizontalItemDecoration;
import com.em.utils.ResyclerViewLayoutManger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/25 0025 13:51
 * discrption 累计订单
 */
public class CumulativeOrderActivity extends BaseActivity<CumulativeOrderPersent> {

    private static final String TAG = "CumulativeOrderActivity";
    private RecyclerView recyclerViewCumuOrderMenu;
    private ResyclerViewLayoutManger manger;
    private CumuOrderMenuItemAdapter adapter;


    private String[] dateStr = {"全部","预计收益","可提现","提现中","已提现"};
    private List<String> dateMenu = new ArrayList<>();

    public List<String> initDateMenu(String[] strings){
        for (int i=0;i<strings.length;i++){
            System.out.println(strings[i]);
            dateMenu.add(strings[i]);
        }
        return dateMenu;
    }

    @Override
    public void initView() {
        recyclerViewCumuOrderMenu = findViewById(R.id.recycler_cumulative_order);

        manger = new ResyclerViewLayoutManger(this);
        manger.setOrientation(LinearLayoutManager.HORIZONTAL);
        manger.setmScrollHorizontally(false);
        List<String> list = initDateMenu(dateStr);
        adapter = new CumuOrderMenuItemAdapter(list);
    }

    @Override
    public int getContextViewId() {
        return R.layout.cumulative_order_activity;
    }

    @Override
    public void initData() {
        replaceFragment(new AllOrderFragment());

        recyclerViewCumuOrderMenu.setLayoutManager(manger);
        recyclerViewCumuOrderMenu.setAdapter(adapter);
        int item = this.getResources().getDimensionPixelSize(R.dimen.dp_4);
        recyclerViewCumuOrderMenu.addItemDecoration(new HorizontalItemDecoration(item,this));
        adapter.setOnItemClickListener(new CumuOrderMenuItemAdapter.setOnItemClickMenu() {
            @Override
            public void onClick(int position, String dateMu) {
               switch (position){
                   case 0:
                       replaceFragment(new AllOrderFragment());
                       break;
                   case 1:
                        replaceFragment(new ExceptOrderFragment());
                        break;
                   case 2:
                       replaceFragment(new CanCarryOrderFragment());
                       break;
                   case 3:
                       replaceFragment(new CarryingOrderFragment());
                       break;
                   case 4:
                       replaceFragment(new AlreadyCarryOrderFragment());
                       break;
               }
            }
        });

    }

    @Override
    public void initListener() {

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

    }

    //动态添加碎片
    public void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.order_frameLayout,fragment);
        transaction.commit();
    }

}
