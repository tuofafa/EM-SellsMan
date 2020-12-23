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
 * @date 2020/12/8 0008 14:25
 */
public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {

    private Context context;
    private List<Commodity> commodityList;
    private setOnClickListen clickListen;

    public MenuItemAdapter(){}

    public MenuItemAdapter(Context mContext, List<Commodity> commodityList){
        this.context = mContext;
        this.commodityList = commodityList;
    }

    public void setClickListen(setOnClickListen clickListen){
        this.clickListen = clickListen;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_adapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //判断是否是最后一个元素
        /*if(commodityList.size()-1 == position){
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.menuItemSharp.getLayoutParams();


             int rightAndLeft = context.getResources().getDimensionPixelSize(R.dimen.dp_15);

            layoutParams.setMargins(rightAndLeft,rightAndLeft,rightAndLeft,rightAndLeft);
            holder.menuItemSharp.setLayoutParams(layoutParams);
        }*/
       final Commodity commodity = commodityList.get(position);
       holder.menuItemProductName.setText(commodity.getName());
        //小数点保留两位
        Float sale = commodity.getMarktPrice()*commodity.getSaleScale();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

       holder.menuItemProductMenoy.setText("立即赚钱 ￥ "+decimalFormat.format(sale));
       holder.menuItemProductBiLi.setText("返佣比例"+(commodity.getSaleScale()*100)+"%");
       holder.menuItemProductPrice.setText("￥"+commodity.getMarktPrice());
       Picasso.with(context).load(commodity.getMasterImg()).into(holder.menuItemProductZhuTu);
        holder.menuItemSharp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListen != null){
                    clickListen.onClick(position,commodity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return commodityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout menuItemSharp;
        ImageView menuItemProductZhuTu;
        TextView menuItemProductName;
        TextView menuItemProductPrice;
        TextView menuItemProductBiLi;
        TextView menuItemProductMenoy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            menuItemSharp = itemView.findViewById(R.id.menu_item_adapter_sharp);
            menuItemProductZhuTu = itemView.findViewById(R.id.menu_item_adapter_zhutu);
            menuItemProductName = itemView.findViewById(R.id.menu_item_adapter_name);
            menuItemProductPrice = itemView.findViewById(R.id.menu_item_adapter_price);
            menuItemProductBiLi = itemView.findViewById(R.id.menu_item_adapter_bili);
            menuItemProductMenoy = itemView.findViewById(R.id.menu_item_adapter_menoy);

        }
    }

    public interface setOnClickListen{
        void onClick(int position,Commodity commodity);
    }

}
