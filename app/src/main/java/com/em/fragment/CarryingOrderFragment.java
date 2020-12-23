package com.em.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.adapter.OrderFragmentAdapter;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.pojo.OrderEntity;
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
 * @date 2020/9/27 0027 14:07
 *  discrption 累计订单模块  提现中fragment
 */
public class CarryingOrderFragment extends Fragment {

    private static final String TAG ="CarryingOrderFragment";
    private RecyclerView orderRecycler;

    private LinearLayoutManager manager;
    private OrderFragmentAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment,container,false);
        orderRecycler = view.findViewById(R.id.order_recyclerview);
        Integer uid = SpUtils.getLoginUserId(getContext());
        if(uid != null){
            String url = "?memberId="+uid;
            //向服务器请求数据
            getRequestOrder(url);
        }
        return view;
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            List<OrderEntity> orderEntities = null;
            switch (msg.what){
                case 0x11:
                    orderEntities = (List<OrderEntity>) msg.obj;
                    break;
            }
            //是否有数据，没有跳转到指定页面
            if(orderEntities.size()>0){
                manager = new LinearLayoutManager(getActivity());
                adapter = new OrderFragmentAdapter(getContext(),orderEntities);
                orderRecycler.setAdapter(adapter);
                orderRecycler.setLayoutManager(manager);
            }else {
                replaceFragment(new NoDataFragment());
            }
        }
    };

    //在子线程中访问接口数据
    public void getRequestOrder(final String url){
        new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                super.run();
                List<OrderEntity> orderEntityList = initData(url);
                Message message = Message.obtain();
                message.obj = orderEntityList;
                message.what = 0x11;
                handler.sendMessage(message);
            }
        }.start();
    }

    //请求服务器数据
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<OrderEntity> initData(String url){
        List<OrderEntity> list = new ArrayList<>();
        String res = NetWorkUtil.requestGet(URLConfig.LJ_ORDER_URL +url);
        if(res.equals(null) || res.equals("") || res.equals("null")){
            Log.d(TAG, "initData: "+"服务器返回数据异常");
            Common.showToast(getContext(),"服务器请求异常，请检查当前网络是否流畅……");
        }
        try {
            JSONObject jsonObject = new JSONObject(res);
            String flag = jsonObject.getString("success");
            String data = jsonObject.getString("data");
            if(flag.equals("true") && !(data.equals("null"))){
                JSONObject object = new JSONObject(data);
                int pageSize = object.getInt("pageSize");
                JSONArray array = object.getJSONArray("saleOrders");
                if(pageSize>0){
                    for(int i=0;i<array.length();i++){
                        OrderEntity order = new OrderEntity();
                        JSONObject object1 = array.getJSONObject(i);
                        String actMoney = object1.optString("saleMoney");    //佣金金额
                        String buyName = object1.optString("buyName");      //购买客户
                        String orderTime = object1.optString("orderTime");  //购买时间
                        String productName = object1.optString("productName");  //购买产品
                        Integer saleState = object1.optInt("saleState");    //佣金状体
                        Integer uid = object1.optInt("memberId");
                        //用户id
                        String proImage = object1.optString("masterImg");   //商品图片

                        if(saleState != null && saleState == 3){
                            order.setYognJinStatus("提现中");
                            if(actMoney != null && !(actMoney.equals("")) && !(actMoney.equals("null"))){
                                order.setYjMoney(actMoney);
                            }
                            if(buyName != null && !(buyName.equals("")) && !(buyName.equals("null"))){
                                order.setButName(buyName);
                            }
                            if( orderTime!= null && !(orderTime.equals("")) && !(orderTime.equals("null"))){
                                order.setBuyDate(orderTime);
                            }
                            if( productName!= null && !(productName.equals("")) && !(productName.equals("null"))){
                                order.setDiscrption(productName);
                            }
                            if(uid != null){
                                order.setUid(uid);
                            }
                            if(!(proImage.equals("null")) && !(proImage.equals("")) ){
                                order.setImgPath(proImage);
                            }
                            list.add(order);
                        }
                    }
                }
            }else {
                Log.d(TAG, "initData: "+ URLConfig.LJ_ORDER_URL +"接口请求失败");
                Common.showToast(getContext(),"接口请求失败");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //动态添加碎片
    public void replaceFragment(Fragment fragment){
        FragmentManager manager =getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.order_frameLayout,fragment);
        transaction.commit();

    }
}
