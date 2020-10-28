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
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/10/27 0027 11:32
 */
public class GoodsDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //设置常量
    private static final int TYPE_IMG = 1;
    private static final int TYPE_TEXT = 2;
    private Context context;

    public final List<String> typeList;
    public setGoodsOnItemClickListener onItemClickListener;
    public setGoodsOnLongItemClickListener onLongItemClickListener;

    public GoodsDetailsAdapter(Context context, List<String> typeList) {
        this.context = context;
        this.typeList = typeList;
    }

    public void setGoodsOnItemClickListener(setGoodsOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setGoodsOnLongItemClickListener(setGoodsOnLongItemClickListener onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    /**
     * 根据不同的position，设置不同的ViewType
     * position表示当前是第几个Item，通过position拿到当前的Item对象，然后判断这个item对象需要那种视图
     */
    @Override
    public int getItemViewType(int position) {
        String str = typeList.get(position);
        //截取標志位
        int flag = Integer.parseInt(str.substring(0, 1));
        if (flag == 1) {
            return TYPE_IMG;
        } else if (flag == 2) {
            return TYPE_TEXT;
        }
        return 0;
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder(当RecyclerView需要一个ViewHolder时会回调该方法，如果有可复用的View不会回调)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_IMG) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_recyclerview_item_img, parent, false);
            ImgViewHolder viewHolder = new ImgViewHolder(view);
            return viewHolder;
        }
        if (viewType == TYPE_TEXT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_recyclerview_item_text, parent, false);
            TextViewHolder viewHolder = new TextViewHolder(view);
            return viewHolder;
        }
        return null;
    }

    //数据绑定到view控件上
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ImgViewHolder) {
            final String strImgUrl = URLConfig.TPURL + typeList.get(position).substring(1, typeList.get(position).length());

            Picasso.with(context).load(strImgUrl).into(((ImgViewHolder) holder).goodsImgZhuTu);
            ((ImgViewHolder) holder).goodsImgZhuTu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position,strImgUrl);
                    }
                }
            });
        } else if (holder instanceof TextViewHolder) {
            final String strText = typeList.get(position).substring(1, typeList.get(position).length());
            ((TextViewHolder) holder).goodsText.setText(strText);
            ((TextViewHolder) holder).goodsText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onLongItemClickListener != null) {
                        onLongItemClickListener.onLongItemClick(position,strText);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    //图片的viewholder类
    public class ImgViewHolder extends RecyclerView.ViewHolder {
        public ImageView goodsImgZhuTu;

        public ImgViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImgZhuTu = itemView.findViewById(R.id.goods_details_item_zhutu);
        }
    }

    //文字的viewHolder类
    public class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView goodsText;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsText = itemView.findViewById(R.id.goods_details_item_text);
        }
    }

    //点击事件做接口回调
    public interface setGoodsOnItemClickListener {
        void onItemClick(int position,String info);
    }

    public interface setGoodsOnLongItemClickListener {
        void onLongItemClick(int position,String info);
    }


}
