package com.em.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.pojo.Categorize;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/17 0017 10:06
 */
public class CategorizeResyclerviewAdapter extends RecyclerView.Adapter<CategorizeResyclerviewAdapter.ViewHolder>{

    ViewHolder viewHolder;
    private List<Categorize> categorizes;
    public CategorizeResyclerviewAdapter(List<Categorize> categorizes){
        this.categorizes = categorizes;
    }

    public setOnItemClickListener listener;

    public void setOnItemClickListener(setOnItemClickListener listener){
        this.listener = listener;
    }

    public interface setOnItemClickListener{
        void onClick(int position,Categorize sp);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView cName ;
        public ViewHolder(View view){
            super(view);
            cName = view.findViewById(R.id.categorize_item_id);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorize_item,parent,false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategorizeResyclerviewAdapter.ViewHolder holder, final int position) {
        final Categorize c = categorizes.get(position);
        holder.cName.setText(c.getcName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onClick(position,c);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categorizes.size();
    }


}
