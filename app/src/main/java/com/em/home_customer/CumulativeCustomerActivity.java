package com.em.home_customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.adapter.CustomerAdapter;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.fragment.NoDataFragment;
import com.em.home_order.CumulativeOrderActivity;
import com.em.pojo.CustomerEntity;
import com.em.pojo.User;
import com.em.utils.NetWorkUtil;
import com.em.utils.SpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/28 0028 9:22
 * discrption  累计客户
 */
public class CumulativeCustomerActivity extends BaseActivity<CumulativeCustomerPersent> {

    private static final String TAG = "CumulativeCustomerActiv";
    private Context context = CumulativeCustomerActivity.this;
    private RecyclerView customerRecyclerView;


    LinearLayoutManager manager;
    CustomerAdapter adapter;

    @Override
    public void initView() {
        customerRecyclerView = findViewById(R.id.customer_recyclerview);

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override

        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x11) {
                List<CustomerEntity> entityList = (List<CustomerEntity>) msg.obj;
                manager = new LinearLayoutManager(context);
                adapter = new CustomerAdapter(entityList);
                customerRecyclerView.setLayoutManager(manager);
                customerRecyclerView.setAdapter(adapter);
            }
        }
    };


    @Override
    public int getContextViewId() {
        return R.layout.cumulative_customer_activity;
    }

    @Override
    public void initData() {
        Integer uid = SpUtils.getLoginUserId(context);
        String url = URLConfig.LJ_CUSTOMER + "?memberId=" + uid;
        requestServerInitDataCustomer(url);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void destroy() {
        finish();
    }

    public void requestServerInitDataCustomer(final String url) {
        new Thread() {
            @Override
            public void run() {
                super.run();


                try {
                    String res = NetWorkUtil.requestGet(url);
                    List<CustomerEntity> customerEntities = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(res);
                    String flag = jsonObject.optString("success");
                    if (flag.length() > 0 && flag.equals("true")) {
                        String data = jsonObject.optString("data");
                        JSONObject jsonObject1 = new JSONObject(data);
                        JSONArray array = jsonObject1.optJSONArray("saleMembers");
                        if (array.length() > 0) {

                            for (int i = 0; i < array.length(); i++) {
                                CustomerEntity customerEntity = new CustomerEntity();
                                JSONObject object = array.getJSONObject(i);
                                String memberName = object.optString("memberName");
                                String moneyAll = object.optString("moneyAll");
                                String moneyGet = object.optString("moneyGet");
                                String orderNum = object.optString("orderNum");
                                String referrerCode = object.optString("referrerCode");
                                String createTime = object.optString("createTime");

                                if (memberName.length() > 0) {
                                    customerEntity.setMemberName(memberName);
                                } else {
                                    customerEntity.setMemberName("null");
                                }

                                if (moneyAll.length() > 0) {
                                    customerEntity.setMoneyAll(moneyAll);
                                } else {
                                    customerEntity.setMoneyAll("0.00");
                                }
                                if (moneyGet.length() > 0) {
                                    customerEntity.setMoneyGet(moneyGet);
                                } else {
                                    customerEntity.setMoneyGet("0.00");
                                }

                                if (orderNum.length() > 0) {
                                    customerEntity.setOrderNum(orderNum);
                                } else {
                                    customerEntity.setOrderNum("0");
                                }

                                if (referrerCode.length() > 0) {
                                    customerEntity.setReferrerCode(referrerCode);
                                } else {
                                    customerEntity.setReferrerCode("null");
                                }

                                if (createTime.length() > 0) {
                                    customerEntity.setCreateTime(createTime.substring(0, 10));
                                } else {
                                    customerEntity.setCreateTime("null");
                                }
                                customerEntities.add(customerEntity);
                            }
                        }

                        Message message = Message.obtain();
                        message.obj = customerEntities;
                        message.what = 0x11;
                        handler.sendMessage(message);
                    } else {
                        Common.showToast(context, "累计客户请求接口数据失败");
                        Log.d(TAG, "累计客户请求接口数据失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
    @Override
    public CumulativeCustomerPersent getmPersenterInstance() {
        return new CumulativeCustomerPersent();
    }

    @Override
    public void onClick(View v) {

    }
}
