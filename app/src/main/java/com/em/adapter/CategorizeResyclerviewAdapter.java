package com.em.adapter;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.pojo.Categorize;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/17 0017 10:06
 */
public class CategorizeResyclerviewAdapter extends RecyclerView.Adapter<CategorizeResyclerviewAdapter.ViewHolder>{

    //ViewHolder viewHolder;
    private List<Categorize> categorizes;
    private List<Boolean> isClick;
    public CategorizeResyclerviewAdapter(List<Categorize> categorizes){
        this.categorizes = categorizes;
        isClick = new ArrayList<>();
        for(int i=0;i<categorizes.size();i++){
            isClick.add(false);
        }
        isClick.set(0,true);
    }

    public setOnItemClickListener listener;

    public void setOnItemClickListener(setOnItemClickListener listener){
        this.listener = listener;
    }

    public interface setOnItemClickListener{
        void onClick(int position,Categorize sp,TextView tv);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView cName ;
        TextView itemBg;
        public ViewHolder(View view){
            super(view);
            cName = view.findViewById(R.id.categorize_item_id);
            itemBg = view.findViewById(R.id.item);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorize_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CategorizeResyclerviewAdapter.ViewHolder holder, final int position) {
        final Categorize c = categorizes.get(position);
        System.out.println(c.getcName());
        holder.cName.setText(c.getcName());

        if(isClick.get(position)){
            holder.cName.setTextColor(Color.parseColor("#FF06C061"));
            holder.itemBg.setBackgroundColor(Color.parseColor("#FF06C061"));
        }else{
            holder.cName.setTextColor(Color.parseColor("#666666"));
            holder.itemBg.setBackgroundColor(Color.parseColor("#F7F8FA"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    for(int i = 0; i <isClick.size();i++){
                        isClick.set(i,false);
                    }
                    isClick.set(position,true);
                    notifyDataSetChanged();
                    listener.onClick(position,c,holder.cName);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categorizes.size();
    }


}
