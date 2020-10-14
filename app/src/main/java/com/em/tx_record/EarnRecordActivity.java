package com.em.tx_record;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.adapter.EarnRecordAdaptor;
import com.em.base.BaseActivity;
import com.em.pojo.EarnEntity;

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
    private RecyclerView recordView;
    @Override
    public void initView() {
        recordView = findViewById(R.id.record_recyclerview);
        //设置布局和设配器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        EarnRecordAdaptor adaptor = new EarnRecordAdaptor(getInitData());
        recordView.setLayoutManager(manager);
        recordView.setAdapter(adaptor);
    }

    public List<EarnEntity> getInitData(){
        List<EarnEntity> earnEntities = new ArrayList<>();
        for(int i=0;i<40;i++){
            EarnEntity earnEntity = new EarnEntity();
            earnEntities.add(earnEntity);
        }
        return earnEntities;
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
