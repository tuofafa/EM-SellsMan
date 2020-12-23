package com.em.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.em.R;
import com.em.pojo.EarnEntity;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/30 0030 16:54
 * discrption 提现记录的适配器
 */
public class EarnRecordAdaptor extends RecyclerView.Adapter<EarnRecordAdaptor.ViewHolder> {

    private static final String TAG = "EarnRecordAdaptor";
    private List<EarnEntity> earnEntityList;

    public EarnRecordAdaptor(List<EarnEntity> earnEntityList){
        this.earnEntityList = earnEntityList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.earn_record_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EarnEntity earnEntity = earnEntityList.get(position);
        System.out.println("***********"+earnEntity.toString());

        //截取字符串
        String code = earnEntity.getBankCode().substring(earnEntity.getBankCode().length()-4);
        String[] strings = earnEntity.getCreateTime().split(" ");

        //数据绑定
        holder.earnDate.setText(strings[0]);
        holder.earnMoney.setText("￥"+earnEntity.getMoney());
        holder.earnTime.setText(strings[1]);
        holder.earnBankInfo.setText(earnEntity.getBankType()+"(尾号"+code+")");

    }

    @Override
    public int getItemCount() {
        return earnEntityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView earnDate,earnTime,earnMoney,earnBankInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            earnDate = itemView.findViewById(R.id.earn_date);
            earnTime = itemView.findViewById(R.id.earn_time);
            earnMoney = itemView.findViewById(R.id.earn_money);
            earnBankInfo = itemView.findViewById(R.id.earn_backinfo);
        }
    }
}
