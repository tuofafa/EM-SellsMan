package com.em.tx_record;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.adapter.EarnRecordAdaptor;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.pojo.EarnEntity;
import com.em.utils.NetWorkUtil;
import com.em.utils.SpUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/30 0030 16:12
 * discrption 提现记录
 */
public class EarnRecordActivity extends BaseActivity<EarnRecordPresent> {

    private static final String TAG = "EarnRecordActivity";
    private Context context = EarnRecordActivity.this;
    private LinearLayoutManager manager;
    private EarnRecordAdaptor adaptor;

    private RefreshLayout refreshLayout;


    private RecyclerView recordView;
    public static Integer page = 1;

    @Override
    public void initView() {
        recordView = findViewById(R.id.record_recyclerview);
        refreshLayout = findViewById(R.id.earn_recoed_refreshLayout);


        Integer uid = SpUtils.getLoginUserId(EarnRecordActivity.this);
        String url = URLConfig.TX_RECORD + "?memberId=" + uid;

        getRequestData(url);

        //上拉事件
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            Integer uid = SpUtils.getLoginUserId(EarnRecordActivity.this);
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                String url = URLConfig.TX_RECORD + "?memberId=" + uid+"&page="+(page+1);
                refreshlayout.finishLoadmore(1000);
                getRequestData(url);
                Log.d(TAG, "pageSize: "+page);
            }
        });

        //下拉事件
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            Integer uid = SpUtils.getLoginUserId(EarnRecordActivity.this);
            String url = URLConfig.TX_RECORD + "?memberId=" + uid;
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                refreshlayout.finishRefresh(1000);
                getRequestData(url);
            }
        });

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x11) {
                List<EarnEntity> earnEntities = new ArrayList<>();
                JSONArray array = (JSONArray) msg.obj;
                if (array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        EarnEntity earnEntity = new EarnEntity();

                        earnEntity.setBankAdd(object.optString("bankAdd"));
                        earnEntity.setBankCode(object.optString("bankCode"));
                        earnEntity.setBankName(object.optString("bankName"));
                        earnEntity.setBankType(object.optString("bankType"));
                        earnEntity.setCreateTime(object.optString("createTime"));
                        earnEntity.setMoney(object.optString("money"));
                        earnEntities.add(earnEntity);
                    }
                    System.out.println(earnEntities.size());
                    //设置布局和设配器
                    manager = new LinearLayoutManager(EarnRecordActivity.this);
                    adaptor = new EarnRecordAdaptor(earnEntities);
                    recordView.setLayoutManager(manager);
                    recordView.setAdapter(adaptor);
                } else {
                   Common.showToast(context,"没有数据啦……");
                }
            }
        }
    };

    //向服务器请求提现记录信息
    public void getRequestData(final String url) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String res = NetWorkUtil.requestGet(url);
                if (res.length() > 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        String flag = jsonObject.optString("success");
                        if (flag.length() > 0 && flag.equals("true")) {
                            String data = jsonObject.optString("data");
                            JSONObject object = new JSONObject(data);
                            JSONArray array = object.optJSONArray("saleApplyMoneys");
                            Log.d(TAG, "array=====" + array);
                            Message message = Message.obtain();
                            message.obj = array;
                            message.what = 0x11;
                            handler.sendMessage(message);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Common.showToast(EarnRecordActivity.this, "请检查当前网路状态……");
                }
                Log.d(TAG, "提现记录信息=====" + res);
            }
        }.start();
    }

    @Override
    public int getContextViewId() {
        return R.layout.earn_record_activity;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void destroy() {
        finish();
    }

    @Override
    public EarnRecordPresent getmPersenterInstance() {
        return new EarnRecordPresent();
    }

    @Override
    public void onClick(View v) {

    }
}
