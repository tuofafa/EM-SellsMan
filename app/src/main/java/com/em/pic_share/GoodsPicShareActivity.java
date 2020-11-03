package com.em.pic_share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.em.R;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.utils.SavePicture;
import com.squareup.picasso.Picasso;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/10/29 0029 9:40
 * discrption  图片分享
 */
public class GoodsPicShareActivity extends BaseActivity<GoodsPicSharePersent> {

    private TextView shareTextHB;
    private ImageView shareImgHB;
    private Context context = GoodsPicShareActivity.this;
    @Override
    public void initView() {
        shareImgHB = findViewById(R.id.goods_details_share_img);
        //shareTextHB = findViewById(R.id.goods_details_hb_share);

        String imgUri = getIntent().getStringExtra("imgUri");
        Picasso.with(context).load(imgUri).into(shareImgHB);
    }

    @Override
    public int getContextViewId() {
        return R.layout.hb_share;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
       // shareTextHB.setOnClickListener(this);
        shareImgHB.setOnClickListener(this);
    }

    @Override
    public void destroy() {

    }

    @Override
    public GoodsPicSharePersent getmPersenterInstance() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goods_details_share_img:
                Uri uri = SavePicture.SaveJpgUri((ImageView) v,context);
                if(uri != null){
                    Intent imageIntent = new Intent(Intent.ACTION_SEND);
                    imageIntent.setType("image/*");
                    imageIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(imageIntent, "分享"));
                }else {
                    Common.showToast(context,"分享失败");
                }
                break;
           /* case R.id.goods_details_hb_share:
                Uri uri1 = SavePicture.SaveJpgUri((ImageView) v,context);
                if(uri1 != null){
                    Intent imageIntent = new Intent(Intent.ACTION_SEND);
                    imageIntent.setType("image/*");
                    imageIntent.putExtra(Intent.EXTRA_STREAM, uri1);
                    startActivity(Intent.createChooser(imageIntent, "分享"));
                }else {
                    Common.showToast(context,"分享失败");
                }
                break;*/
        }
    }



}
