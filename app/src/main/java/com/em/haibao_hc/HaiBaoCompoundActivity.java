package com.em.haibao_hc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.em.R;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.pojo.Commodity;
import com.em.pojo.User;
import com.em.utils.QRCodeUtil;
import com.em.utils.SavePicture;
import com.em.utils.SpUtils;
import com.squareup.picasso.Picasso;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/10/27 0027 17:18
 */
public class HaiBaoCompoundActivity extends BaseActivity<HaiBaoCompoundPersent>{

    private TextView shareHB;
    private ImageView hbZhuTu;
    private ImageView hbZTqrCode;
    private ImageView hbTouXiang;
    private TextView hbProductInstruction;
    private TextView hbProductNikeName;
    private TextView hbProductPrice;

    private LinearLayout view;

    private Context context = HaiBaoCompoundActivity.this;
    private Commodity commodity;
    private static final String TAG = "HaiBaoCompoundActivity";
    @Override
    public void initView(){
        hbZhuTu = findViewById(R.id.haibao_zhutu_img);
        hbZTqrCode = findViewById(R.id.haibao_qercode);
        hbTouXiang = findViewById(R.id.haibao_touxiang);
        hbProductInstruction = findViewById(R.id.haibao_insturctions);
        hbProductNikeName = findViewById(R.id.haibao_nickname);
        hbProductPrice = findViewById(R.id.haibao_price);
        shareHB = findViewById(R.id.share_hb);
        view = findViewById(R.id.test);
    }

    @Override
    public int getContextViewId() {
        return R.layout.haibai_compount;
    }

    @Override
    public void initData() {
        commodity = (Commodity) getIntent().getSerializableExtra("commodity");
        if(commodity!= null){
            Picasso.with(context).load(commodity.getMasterImg()).into(hbZhuTu);
            hbProductInstruction.setText(commodity.getName());
            hbProductPrice.setText(commodity.getMarktPrice().toString());
            //商品二维码
            String qErCode = URLConfig.PREDUCT_URL+commodity.getId();
            Bitmap ewCode = QRCodeUtil.createQRCodeBitmap(qErCode,400,400);
            hbZTqrCode.setImageBitmap(ewCode);
        }
        //现在返回为空
        User loginInfo = SpUtils.getLoginInfo(context);
        if(loginInfo != null){
            //设置头像
            Picasso.with(context).load(URLConfig.TPURL+loginInfo.getHeadImg()).into(hbTouXiang);
            Log.d(TAG, "头像地址链接"+loginInfo.getHeadImg());
        }
    }

    @Override
    public void initListener() {
        view.setOnClickListener(this);
        shareHB.setOnClickListener(this);
    }

    @Override
    public void destroy() {
        finish();
    }

    @Override
    public HaiBaoCompoundPersent getmPersenterInstance() {
        return null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.test:
                Bitmap bitmap = viewAndBitmap(view);
                Uri uri = SavePicture.saveBitmap(bitmap,context);
                if(uri != null){
                    Intent imageIntent = new Intent(Intent.ACTION_SEND);
                    imageIntent.setType("image/*");
                    imageIntent.setPackage("com.tencent.mm");  //设置分享到固定的应用程序
                    imageIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(imageIntent, "分享"));
                }else {
                    Common.showToast(context,"分享失败");
                }
                break;

            case R.id.share_hb:

                Bitmap bitmap1 = viewAndBitmap(view);
                Uri uri1 = SavePicture.saveBitmap(bitmap1,context);
                if(uri1 != null){
                    Intent imageIntent = new Intent(Intent.ACTION_SEND);
                    imageIntent.setType("image/*");
                    imageIntent.putExtra(Intent.EXTRA_STREAM, uri1);
                    startActivity(Intent.createChooser(imageIntent, "分享"));
                }else {
                    Common.showToast(context,"分享失败");
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

}
