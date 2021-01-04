package com.em.notice;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.adapter.NoticeAdapter;
import com.em.base.BaseActivity;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2021/1/4 0004 11:26
 */
public class NoticeCentActivity extends BaseActivity<NoticeCentPresent> {

    private RecyclerView noticeRecyclerView;
    private LinearLayoutManager manager;
    private NoticeAdapter adapter;


    @Override
    public void initView() {
        noticeRecyclerView = findViewById(R.id.notice_recycler_view);

    }

    @Override
    public int getContextViewId() {
        return R.layout.notice_layout
                ;
    }

    @Override
    public void initData() {

        manager = new LinearLayoutManager(this);
        adapter = new NoticeAdapter();
        noticeRecyclerView.setLayoutManager(manager);
        noticeRecyclerView.setAdapter(adapter);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public NoticeCentPresent getmPersenterInstance() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}
