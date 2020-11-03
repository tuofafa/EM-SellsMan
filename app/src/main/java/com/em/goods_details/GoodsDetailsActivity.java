package com.em.goods_details;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.adapter.GoodsDetailsAdapter;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.haibao_hc.HaiBaoCompoundActivity;
import com.em.pic_share.GoodsPicShareActivity;
import com.em.pojo.Commodity;
import com.em.pojo.GoodsDetailsEntity;
import com.em.utils.NetWorkUtil;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/10/26 0026 17:15
 * discrsption  商品详情页面
 */
public class GoodsDetailsActivity extends BaseActivity<GoodsDetailsPersent> {
    private static final String TAG = "GoodsDetailsActivity";
    private GoodsDetailsActivity context = GoodsDetailsActivity.this;

    private ImageView goodsZhuTu;   //商品主图
    private TextView goodsName;     //商品名称
    private TextView goodsPrice;    //商品价格
    private TextView goodsBiLi;     //返佣比例
    private TextView goodsShouYi;   //推广收益
    private RecyclerView goodsRecyclerView; //商品图片明细
    private Button tuwenCompound;   //图文合成
    private Button hbCompound;      //海报合成
    private List<Integer> typeList;
    private GoodsDetailsAdapter adapter;
    private LinearLayoutManager manager;

    private Commodity commodity;

    @Override
    public void initView() {
        goodsZhuTu = findViewById(R.id.goods_img_zhutu);
        goodsName = findViewById(R.id.goods_details_discrption);
        goodsPrice = findViewById(R.id.goods_details_price);
        goodsBiLi = findViewById(R.id.goods_details_bili);
        goodsShouYi = findViewById(R.id.goods_details_shouyi);
        tuwenCompound = findViewById(R.id.goods_details_tuwen);
        hbCompound = findViewById(R.id.goods_details_hb);
        goodsRecyclerView = findViewById(R.id.gods_details_recyclview);
        commodity = (Commodity) getIntent().getSerializableExtra("commodit");
    }

    @Override
    public int getContextViewId() {
        return R.layout.goods_details_fragment;
    }

    @Override
    public void initData() {
        if (commodity != null) {
            System.out.println("初始化数据" + commodity.toString());
            //设置图片
            Picasso.with(context).load(commodity.getMasterImg()).into(goodsZhuTu);
            goodsName.setText(commodity.getName());
            goodsPrice.setText(commodity.getMarktPrice().toString());
            goodsBiLi.setText(commodity.getSaleScale().toString());

            //goodsShouYi.setText(commodit);
        }
        String url = URLConfig.GOODS_DETAILS + commodity.getId() + ".html";
        getRequestGoodsDetails(url);
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            List<String> spWuLiao = new ArrayList<>();
            GoodsDetailsEntity detailsEntity = new GoodsDetailsEntity();
            switch (msg.what) {
                case 0x1212:
                    JSONObject object = (JSONObject) msg.obj;
                    JSONArray array = object.optJSONArray("productLeadPicList");
                    Log.d(TAG, "handleMessage: arrr" + array.length());
                    if (array.length() > 0) {
                        JSONObject jsonObject = new JSONObject();
                        for (int i = 0; i < array.length(); i++) {
                            try {
                                //1 是标志位
                                String imgUrl = "1" + (String) array.get(i);
                                spWuLiao.add(imgUrl);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Log.d(TAG, "商品物料为空……");
                    }
                    //商品详情地址 2 是标志位
                    String spText = "2" + commodity.getName() + "链接地址：" + URLConfig.PREDUCT_URL + commodity.getId();
                    spWuLiao.add(spText);
                    detailsEntity.setListImg(spWuLiao);
                    detailsEntity.setCommodity(commodity);

                    break;
            }
            adapter = new GoodsDetailsAdapter(GoodsDetailsActivity.this, detailsEntity.getListImg());
            manager = new LinearLayoutManager(context);
            //给recyclerview设置管理器
            goodsRecyclerView.setLayoutManager(manager);
            goodsRecyclerView.setAdapter(adapter);
            adapter.setGoodsOnItemClickListener(new GoodsDetailsAdapter.setGoodsOnItemClickListener() {
                @Override
                public void onItemClick(int position,String info) {
                    Common.showToast(context, "你点了" + position+"内容："+info);
                    Intent shareImg = new Intent(context, GoodsPicShareActivity.class);
                    shareImg.putExtra("imgUri",info);
                    startActivity(shareImg);
                }
            });
            adapter.setGoodsOnLongItemClickListener(new GoodsDetailsAdapter.setGoodsOnLongItemClickListener() {
                @Override
                public void onLongItemClick(int position,String info) {
                    setClipboard(info);
                    Common.showToast(context,"复制成功");
                }
            });
        }
    };

    public void getRequestGoodsDetails(final String url) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String res = NetWorkUtil.requestGet(url);
                    Log.d(TAG, "商品信息……" + res);
                    JSONObject jsonObject = new JSONObject(res);
                    String flag = jsonObject.optString("success");
                    if (flag.length() > 0 && flag.equals("true")) {
                        String data = jsonObject.optString("data");
                        JSONObject object = new JSONObject(data);

                        Message message = Message.obtain();
                        message.obj = object;
                        message.what = 0x1212;
                        handler.sendMessage(message);
                    } else {
                        Common.showToast(context, "接口请求失败……");
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "run: 商品相信接口返回错误……");
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    public void initListener() {
        tuwenCompound.setOnClickListener(this);
        hbCompound.setOnClickListener(this);
    }

    @Override
    public void destroy() {
        finish();
    }

    @Override
    public GoodsDetailsPersent getmPersenterInstance() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_details_hb:
                //跳转到海报生成页面
                Intent intent = new Intent(context, HaiBaoCompoundActivity.class);
                intent.putExtra("commodity", commodity);
                startActivity(intent);
                break;
            case R.id.goods_details_tuwen:
                Common.showToast(context, "还在开发中……");
                break;
        }

    }

    //将内容复制到剪贴板
    public void setClipboard(String args) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", args);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
}
