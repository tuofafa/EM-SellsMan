package com.em.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.config.URLConfig;
import com.em.pojo.OrderEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/25 0025 14:54
 * discrption 累计订单的RecyclerView适配器
 */
public class OrderFragmentAdapter extends RecyclerView.Adapter<OrderFragmentAdapter.ViewHolder> {

    private Context context;
    private List<OrderEntity> entityList;
    public OrderFragmentAdapter(Context context, List<OrderEntity> entityList){
        this.entityList = entityList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_fragment_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderEntity orderEntity = entityList.get(position);
        holder.orderYJStatus.setText(orderEntity.getYognJinStatus());
        holder.orderDiscrption.setText(orderEntity.getDiscrption());
        holder.orderPrice.setText(orderEntity.getYjMoney());
        holder.orderBuyName.setText(orderEntity.getButName());
        holder.orderBuyTime.setText(orderEntity.getBuyDate());

        Picasso.with(context).load(URLConfig.TPURL+orderEntity.getImgPath()).into(holder.orderImg);

    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView orderYJStatus;
        private ImageView orderImg;
        private TextView orderDiscrption;
        private TextView orderPrice;
        private TextView orderBuyName;
        private TextView orderBuyTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderBuyName = itemView.findViewById(R.id.order_buyname);
            orderYJStatus = itemView.findViewById(R.id.order_status);
            orderDiscrption = itemView.findViewById(R.id.order_discrption);
            orderPrice = itemView.findViewById(R.id.order_price);
            orderBuyTime = itemView.findViewById(R.id.order_time);
            orderImg = itemView.findViewById(R.id.order_img);
        }
    }
}
