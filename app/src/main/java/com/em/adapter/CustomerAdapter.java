package com.em.adapter;

import android.text.Html;
import android.util.Log;
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
           /* Log.d("Test", "customerEntity.getMoneyAll(): "+customerEntity.getMoneyAll());
            Log.d("Test", "customerEntity.getOrderNum(): "+customerEntity.getOrderNum());
            String cjMoney = customerEntity.getMoneyAll();
            String orderSum = customerEntity.getOrderNum();
            String cj = "<font color= \"#FFFFFF\">"+cjMoney+"</font><font color= \"#999999\">元</font>";
            String orderNum = "<font color= \"#FFFFFF\">"+orderSum+"</font><font color= \"#999999\">单</font>";
            String fyMoney = "<font color= \"#FF6C00\">"+customerEntity.getMoneyGet()+"</font><font color= \"#FF6C00\">元</font>";
*/
            if(customerEntity.getMoneyAll().equals("null")){
                holder.customerCJMoney.setText("成交金额: "+0+"元");
            }else {
                holder.customerCJMoney.setText("成交金额: "+customerEntity.getMoneyAll()+"元");
            }
            if(customerEntity.getMoneyGet().equals("null")){
                holder.customerFYMoney.setText(0+"元");
            }else {
                holder.customerFYMoney.setText(customerEntity.getMoneyGet()+"元");
            }

            holder.customerOrderNum.setText(customerEntity.getOrderNum()+"单");
            holder.customerTime.setText(customerEntity.getCreateTime());
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
