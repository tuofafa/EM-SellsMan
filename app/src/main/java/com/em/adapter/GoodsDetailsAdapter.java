package com.em.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.em.R;
import com.em.config.URLConfig;
import com.em.utils.SpUtils;
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

    //点击图片事件
    public setGoodsOnItemClickListener onItemClickListener;

    //长按文字事件
    public setGoodsOnLongItemClickListener onLongItemClickListener;

    //点击下载按钮下载图片事件
    public SetGoodsOnItemImgerDownloadListener itemImgerDownloadListener;

    //点击复制按钮复制文本内容
    public SetGoodsOnItemTextCopyListener itemTextCopyListener;


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

    public void setItemImgerDownloadListener(SetGoodsOnItemImgerDownloadListener itemImgerDownloadListener){
        this.itemImgerDownloadListener = itemImgerDownloadListener;
    }

    public void setItemTextCopyListener(SetGoodsOnItemTextCopyListener itemTextCopyListener){
        this.itemTextCopyListener = itemTextCopyListener;
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

            final String strImgUrl =typeList.get(position).substring(1, typeList.get(position).length());

            Picasso.with(context).load(strImgUrl).into(((ImgViewHolder) holder).goodsImgZhuTu);

            //设置点击图片的接口回调
            ((ImgViewHolder) holder).goodsImgZhuTu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position,strImgUrl);
                    }
                }
            });

            //设置点击下载按钮的接口回调
            ((ImgViewHolder) holder).imgDwonload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemImgerDownloadListener != null){
                        itemImgerDownloadListener.onItemDownload(position,strImgUrl);
                    }
                }
            });
        } else if (holder instanceof TextViewHolder) {

            final String strWeb = typeList.get(position).substring(1, typeList.get(position).length());
            System.out.println("适配器："+strWeb);
            //获取邀请码
            final String userCode = SpUtils.getUserCode(context);
            //setText(strText+"&sc="+userCode);
            //String html = "<p><img src=\"http://img.baidu.com/hi/face/i_f13.gif\"/>新华医疗</p>";
            ((TextViewHolder) holder).goodsWebview.loadDataWithBaseURL(null,strWeb,"text/html","utf-8",null);

            //设置长按文字的接口回调
            ((TextViewHolder) holder).goodsWebview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onLongItemClickListener != null) {
                        onLongItemClickListener.onLongItemClick(position,strWeb+"&sc="+userCode);
                    }
                    return false;
                }
            });

            //设置点击复制按钮的接口回调
            ((TextViewHolder) holder).textCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemTextCopyListener != null){
                        itemTextCopyListener.onItemTextCopy(position,strWeb+"&sc="+userCode);
                    }
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
        public LinearLayout imgDwonload;

        public ImgViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImgZhuTu = itemView.findViewById(R.id.goods_details_item_zhutu);
            goodsImgZhuTu.setAdjustViewBounds(true);
            imgDwonload = itemView.findViewById(R.id.share_img_download);

        }
    }

    //文字的viewHolder类
    public class TextViewHolder extends RecyclerView.ViewHolder {
        public WebView goodsWebview;
        public LinearLayout textCopy;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsWebview = itemView.findViewById(R.id.goods_details_item_webview);
            textCopy = itemView.findViewById(R.id.share_text_copy);
        }


    }

    //点击图片事件做接口回调
    public interface setGoodsOnItemClickListener {
        void onItemClick(int position,String info);
    }
    //点击下载按钮接口事件
    public interface SetGoodsOnItemImgerDownloadListener{
        void onItemDownload(int position,String imgUrl);
    }

    //长按文字事件接口
    public interface setGoodsOnLongItemClickListener {
        void onLongItemClick(int position,String info);
    }

    //点击复制按钮接口事件
    public interface SetGoodsOnItemTextCopyListener{
        void onItemTextCopy(int position,String content);
    }


}
