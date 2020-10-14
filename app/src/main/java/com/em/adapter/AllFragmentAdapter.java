package com.em.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.em.R;
import com.em.pojo.Commodity;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.List;
/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/19 0019 23:02
 */
public class AllFragmentAdapter extends RecyclerView.Adapter<AllFragmentAdapter.ViewHolder> {

    ViewHolder viewHolder;
    private setAllOnItemClickListener listener;
    private List<Commodity> commodityList;
    private Context context;
    public AllFragmentAdapter(Context context, List<Commodity> commodityList){
        this.commodityList = commodityList;
        this.context = context;
    }

    public void setAllOnItemClickListener(setAllOnItemClickListener listener){
        this.listener = listener;
    }

    public interface setAllOnItemClickListener{
        void onClick(int position, Commodity sp);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_splist_item,parent,false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Commodity commodity = commodityList.get(position);
        holder.spPrice.setText(commodity.getMarktPrice().toString());   //设置商品价格
        holder.spFYBiLi.setText("返佣比例"+(commodity.getSaleScale()*100)+"%"); //设置返佣比例
        holder.spDiscrption.setText(commodity.getName());               //设置商品名字
        //小数点保留两位
        float sale = commodity.getMarktPrice()*commodity.getSaleScale();
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        //设置分享后预计赚多少钱
        holder.sales.setText(decimalFormat.format(sale));
        Picasso.with(context).load(commodity.getMasterImg()).into(holder.spImger);  //设置商品图片
        //点击事件
        holder.spShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onClick(position,commodity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return commodityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView spImger;          //商品图片
        private TextView spDiscrption;      //商品描述
        private TextView    spPrice;        //商品价格
        private TextView    spFYBiLi;       //返佣比例
        private LinearLayout spShare;       //分享赚钱
        private TextView sales;             //设置金额

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            spImger = itemView.findViewById(R.id.all_image);
            spDiscrption = itemView.findViewById(R.id.all_text);
            spPrice = itemView.findViewById(R.id.all_price);
            spFYBiLi = itemView.findViewById(R.id.all_bili);
            spShare = itemView.findViewById(R.id.all_share);
            sales = itemView.findViewById(R.id.all_sale);
        }
    }
}
