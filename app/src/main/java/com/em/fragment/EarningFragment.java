package com.em.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.em.config.SpCateConstant;
import com.em.pojo.HomeEntity;
import com.em.utils.SpUtils;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/28 0028 14:28
 */
public class EarningFragment extends Fragment{
    private static final String TAG = "EarningFragment";
    private TextView cumucativeMoney;
    private LinearLayout menu;
    private Integer type;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.earning_item,container,false);
        cumucativeMoney = view.findViewById(R.id.cumucative_money);

        Bundle bundle = getArguments();
        type = bundle.getInt("type");

        HomeEntity homeEntity = SpUtils.getCumulativeMoney(getContext());
        if(type != null && type == SpCateConstant.ToDay){ //今天
            Log.d(TAG, "toDay: "+homeEntity.getToDayCumuMoney());
            cumucativeMoney.setText(homeEntity.getToDayCumuMoney().toString());
        }
        if(type != null && type == SpCateConstant.YestDay){ //昨天
            Log.d(TAG, "yestDay: "+homeEntity.getYestDayCumuMoney());
            cumucativeMoney.setText(homeEntity.getYestDayCumuMoney().toString());
        }
        if(type != null && type == SpCateConstant.QTDay){ //一周
            Log.d(TAG, "weekDay: "+homeEntity.getWeekCumuMoney());
            cumucativeMoney.setText(homeEntity.getWeekCumuMoney().toString());
        }
        return view;
    }

}
