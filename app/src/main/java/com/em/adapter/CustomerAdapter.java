package com.em.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.pojo.CustomerEntity;

import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/28 0028 9:39
 *
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    private List<?> t;
    public CustomerAdapter(List<?> list){
        this.t = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(t.get(position) instanceof CustomerEntity){
            CustomerEntity customerEntity = (CustomerEntity) t.get(position);
            holder.customerCJMoney.setText(customerEntity.getMoneyAll());
            holder.customerOrderNum.setText(customerEntity.getOrderNum());
            holder.customerTime.setText(customerEntity.getCreateTime());
            holder.customerFYMoney.setText(customerEntity.getMoneyGet());
            holder.customerName.setText(customerEntity.getMemberName());
        }
    }

    @Override
    public int getItemCount() {
        return t.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView customerImg;  //头像
        private TextView customerCJMoney;   //成交金额
        private TextView customerFYMoney;   //返佣总金额
        private TextView customerTime;      //最近下单时间
        private TextView customerOrderNum;  //订单数量
        private TextView customerName;      //客户名称

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerImg = itemView.findViewById(R.id.customer_img);
            customerCJMoney = itemView.findViewById(R.id.customer_cj_money);
            customerFYMoney = itemView.findViewById(R.id.customer_fy_money);
            customerTime = itemView.findViewById(R.id.customer_time);
            customerOrderNum = itemView.findViewById(R.id.customer_order_num);
            customerName = itemView.findViewById(R.id.customer_name);
        }
    }
}
