package com.em.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/19 0019 16:33
 */
public class CumulativeMenuItemAdapter extends RecyclerView.Adapter<CumulativeMenuItemAdapter.ViewHolder> {

    private List<String> dateMenu;
    private List<Boolean> isClick;

    public CumulativeMenuItemAdapter(List<String> dateMenu){
        this.dateMenu = dateMenu;
        isClick = new ArrayList<>();
        for(int i=0;i<dateMenu.size();i++){
            isClick.add(false);
        }
        isClick.set(0,true);
    }
    public setOnItemClickMenu listener;

    public void setOnItemClickListener(setOnItemClickMenu listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cumu_earn_menu_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String date = dateMenu.get(position);
        System.out.println(date);
        holder.textView.setText(date);

        if(isClick.get(position)){
            holder.textView.setTextColor(Color.parseColor("#FF06C061"));
            holder.itemEarn.setBackgroundColor(Color.parseColor("#FF06C061"));
        }else{
            holder.textView.setTextColor(Color.parseColor("#666666"));
            holder.itemEarn.setBackgroundColor(Color.parseColor("#F7F8FA"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    for(int i = 0; i <isClick.size();i++){
                        isClick.set(i,false);
                    }
                    isClick.set(position,true);
                    notifyDataSetChanged();

                    listener.onClick(position,date);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateMenu.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        TextView itemEarn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.all1_earning);
            itemEarn = itemView.findViewById(R.id.item_earn);
        }
    }

    public interface setOnItemClickMenu{
        void onClick(int position,String dateMu);
    }

}
