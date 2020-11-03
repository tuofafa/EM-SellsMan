package com.em.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.em.R;
import com.em.common.Common;
import com.em.pojo.HomeEntity;
import com.em.utils.SpUtils;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/28 0028 14:28
 */
public class AllEarningFragment extends Fragment implements View.OnClickListener {
    private TextView allCumucativeMoney;
    private TextView allExceptEarn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_earning_item,container,false);
        allCumucativeMoney = view.findViewById(R.id.all_cumucative_money);
        allExceptEarn = view.findViewById(R.id.all_except_money);

        HomeEntity homeEntity = SpUtils.getCumulativeMoney(getContext());
        if(homeEntity.getCumulativeMoney()!= null){
            allCumucativeMoney.setText(homeEntity.getCumulativeMoney().toString());
        }
        allExceptEarn.setText("预计收益"+homeEntity.getExceptMoney()+"元");
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
