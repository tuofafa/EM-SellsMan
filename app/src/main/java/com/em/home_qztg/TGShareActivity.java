package com.em.home_qztg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.em.R;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.haibao_hc.HaiBaoCompoundActivity;
import com.em.home_tgsp.TGCommodityPersent;
import com.em.pojo.User;
import com.em.utils.CircleTransform;
import com.em.utils.QRCodeUtil;
import com.em.utils.SavePicture;
import com.em.utils.SpUtils;
import com.squareup.picasso.Picasso;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/20 0020 18:29
 */
public class TGShareActivity extends BaseActivity<TGCommodityPersent> {
    private TextView qgShareNickName, qgShareMenu;
    private ImageView qgShareTouXiang, qgShareErWeiMa;
    private LinearLayout qgShareViewImg;
    private Context context = TGShareActivity.this;

    @Override
    public void initView() {
        qgShareNickName = findViewById(R.id.qg_share_nickname);
        qgShareTouXiang = findViewById(R.id.qg_share_tuoxiang);
        qgShareErWeiMa = findViewById(R.id.qg_share_erweima);
        qgShareViewImg = findViewById(R.id.qg_share_view_img);
        qgShareMenu = findViewById(R.id.qg_share_menu);
    }

    @Override
    public int getContextViewId() {
        return R.layout.tgshare_layout;
    }

    @Override
    public void initData() {

        String flag = getIntent().getStringExtra("type");
        String url = getIntent().getStringExtra("url");


        User user = SpUtils.getLoginInfo(context);
        if (user != null) {
            //设置头像
            Picasso.with(context).load(URLConfig.TPURL + user.getHeadImg()).transform(new CircleTransform()).into(qgShareTouXiang);
            //设置昵称
            qgShareNickName.setText(user.getNickName());
        } else {
            System.out.println("设置默认的信息");
            qgShareNickName.setText("医麦合伙人");
        }

        if (flag.equals("11")) {
            //设置二维码
            Bitmap erweima = QRCodeUtil.createQRCodeBitmap(url, 200, 200);
            qgShareErWeiMa.setImageBitmap(erweima);
        }
        //家用商品
        if (flag.equals("96")) {
            //设置二维码
            Bitmap erweima = QRCodeUtil.createQRCodeBitmap(url, 200, 200);
            qgShareErWeiMa.setImageBitmap(erweima);
        }
        //口腔专区
        if (flag.equals("95")) {
            //设置二维码
            Bitmap erweima = QRCodeUtil.createQRCodeBitmap(url, 200, 200);
            qgShareErWeiMa.setImageBitmap(erweima);
        }
        //防疫专区
        if (flag.equals("92")) {
            //设置二维码
            Bitmap erweima = QRCodeUtil.createQRCodeBitmap(url, 200, 200);
            qgShareErWeiMa.setImageBitmap(erweima);

        }
        //耗材专区
        if (flag.equals("94")) {
            //设置二维码
            Bitmap erweima = QRCodeUtil.createQRCodeBitmap(url, 200, 200);
            qgShareErWeiMa.setImageBitmap(erweima);
        }
        //眼科专区
        if (flag.equals("180")) {
            //设置二维码
            Bitmap erweima = QRCodeUtil.createQRCodeBitmap(url, 200, 200);
            qgShareErWeiMa.setImageBitmap(erweima);
        }
        //实验室
        if (flag.equals("179")) {
            //设置二维码
            Bitmap erweima = QRCodeUtil.createQRCodeBitmap(url, 200, 200);
            qgShareErWeiMa.setImageBitmap(erweima);
        }
        //兽用专区
        if (flag.equals("174")) {
            //设置二维码
            Bitmap erweima = QRCodeUtil.createQRCodeBitmap(url, 200, 200);
            qgShareErWeiMa.setImageBitmap(erweima);
        }
        //急救专区
        if (flag.equals("128")) {
            //设置二维码
            Bitmap erweima = QRCodeUtil.createQRCodeBitmap(url, 200, 200);
            qgShareErWeiMa.setImageBitmap(erweima);
        }
    }

    @Override
    public void initListener() {
        qgShareMenu.setOnClickListener(this);
        qgShareViewImg.setOnClickListener(this);
    }

    @Override
    public void destroy() {

    }

    @Override
    public TGCommodityPersent getmPersenterInstance() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qg_share_menu:
                Bitmap bitmap = getViewBitmap(qgShareViewImg);
                Uri uri = SavePicture.saveBitmap(bitmap, context);
                if (uri != null) {
                    Intent imageIntent = new Intent(Intent.ACTION_SEND);
                    imageIntent.setType("image/*");
                    //imageIntent.setPackage("com.tencent.mm");  //设置分享到固定的应用程序
                    imageIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(imageIntent, "分享"));
                } else {
                    Common.showToast(context, "分享失败");
                }
                break;
            case R.id.qg_share_view_img:
                Bitmap bitmap1 = getViewBitmap(qgShareViewImg);
                Uri uri1 = SavePicture.saveBitmap(bitmap1, context);
                if (uri1 != null) {
                    Intent imageIntent = new Intent(Intent.ACTION_SEND);
                    imageIntent.setType("image/*");
                    imageIntent.putExtra(Intent.EXTRA_STREAM, uri1);
                    startActivity(Intent.createChooser(imageIntent, "分享"));
                } else {
                    Common.showToast(context, "分享失败");
                }
                break;
        }
    }

    public Bitmap viewAndBitmap(View view) {
        int me = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(me, me);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

}
