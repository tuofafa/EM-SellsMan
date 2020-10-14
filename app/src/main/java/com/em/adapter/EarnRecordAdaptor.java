package com.em.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    }

    @Override
    public int getItemCount() {
        return earnEntityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
