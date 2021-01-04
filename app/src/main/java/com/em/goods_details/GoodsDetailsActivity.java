package com.em.goods_details;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.adapter.GoodsDetailsAdapter;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.dialog.PictureAllredeTiShiDialog;
import com.em.dialog.PictureDownloadDialog;
import com.em.haibao_hc.HaiBaoCompoundActivity;
import com.em.pic_share.GoodsPicShareActivity;
import com.em.pojo.Commodity;
import com.em.pojo.GoodsDetailsEntity;
import com.em.utils.NetWorkUtil;
import com.em.utils.SavePicture;
import com.google.zxing.qrcode.QRCodeReader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/10/26 0026 17:15
 * discrsption  商品详情页面
 */
public class GoodsDetailsActivity extends BaseActivity<GoodsDetailsPersent> {
    private static final String TAG = "GoodsDetailsActivity";
    private GoodsDetailsActivity context = GoodsDetailsActivity.this;
    private GoodsDetailsEntity detailsEntity;

    PictureDownloadDialog downloadDialog;
    PictureAllredeTiShiDialog tiShiDialog;

    private ImageView goodsZhuTu;   //商品主图
    private TextView goodsName;     //商品名称
    private TextView goodsPrice;    //商品价格
    private TextView goodsBiLi;     //返佣比例
    private TextView goodsShouYi;   //推广收益
    private RecyclerView goodsRecyclerView; //商品图片明细
    private LinearLayout tuwenCompound;   //图文合成
    private LinearLayout hbCompound;      //海报合成
    private List<Integer> typeList;
    private GoodsDetailsAdapter adapter;
    private LinearLayoutManager manager;

    private Commodity commodity;
    private String url;

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
        return R.layout.goods_details_fragment2;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initData() {
        if (commodity != null) {
            System.out.println("初始化数据" + commodity.toString());
            //设置图片
            //Picasso.with(context).load(commodity.getMasterImg()).into(goodsZhuTu);
            goodsName.setText(commodity.getName());
            goodsPrice.setText("￥" + commodity.getMarktPrice().toString());
            goodsBiLi.setText("返佣比例" + (commodity.getSaleScale() * 100) + "%");
            Float sale = commodity.getMarktPrice() * commodity.getSaleScale();
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            goodsShouYi.setText("收益￥" + decimalFormat.format(sale));
        }
        url = URLConfig.GOODS_DETAILS + commodity.getId() + ".html";
        System.out.println("测试商品地址：" + url);
        getRequestGoodsDetails(url);

        //设置批量分享图文按钮不能点击
        tuwenCompound.setClickable(false);
        tuwenCompound.setBackground(this.getDrawable(R.drawable.but_shape_no_click_bg));

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(@NonNull Message msg) {
            List<String> spWuLiao = new ArrayList<>();
            detailsEntity = new GoodsDetailsEntity();
            switch (msg.what) {
                case 0x1212:
                    List<String> array = (List<String>) msg.obj;
                    //商品详情地址 2 是标志位
                    String spText = "2" + commodity.getName() + "链接地址：" + URLConfig.PREDUCT_URL + commodity.getId();
                    spWuLiao.add(spText);

                    detailsEntity.setListImg(array);
                    detailsEntity.setCommodity(commodity);

                    adapter = new GoodsDetailsAdapter(GoodsDetailsActivity.this, detailsEntity.getListImg());
                    manager = new LinearLayoutManager(context);
                    adapter.notifyDataSetChanged();
                    //给recyclerview设置管理器
                    goodsRecyclerView.setLayoutManager(manager);
                    goodsRecyclerView.setAdapter(adapter);

                    //设置等数据加载完毕批量分享图文按钮才可以使用
                    if (array.size() == (msg.arg1 - 1)) {
                        tuwenCompound.setClickable(true);
                        tuwenCompound.setBackground(getDrawable(R.drawable.but_shape));
                    }

                    //直接滑倒最后一个item
                    //goodsRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
                    goodsRecyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);

                    //设置点击图片事件得回调
                    adapter.setGoodsOnItemClickListener(new GoodsDetailsAdapter.setGoodsOnItemClickListener() {
                        @Override
                        public void onItemClick(int position, String info) {
                            Intent shareImg = new Intent(context, GoodsPicShareActivity.class);
                            shareImg.putExtra("imgUri", info);
                            startActivity(shareImg);
                        }
                    });


                    //设置点击下载按钮事件得回调
                    adapter.setItemImgerDownloadListener(new GoodsDetailsAdapter.SetGoodsOnItemImgerDownloadListener() {
                        @Override
                        public void onItemDownload(int position, final String imgUrl) {
                            getImgerUrlOfBitmap(imgUrl);
                            Common.showToast(context, "图片已保存至相册");
                        }
                    });

                    //设置点击复制按钮事件回调
                    adapter.setItemTextCopyListener(new GoodsDetailsAdapter.SetGoodsOnItemTextCopyListener() {
                        @Override
                        public void onItemTextCopy(int position, String content) {

                            setClipboard(content);

                            Common.showToast(context, "内容已复制到剪贴板");

                        }
                    });

                    //设置长按事件得回调
                    adapter.setGoodsOnLongItemClickListener(new GoodsDetailsAdapter.setGoodsOnLongItemClickListener() {
                        @Override
                        public void onLongItemClick(int position, String info) {

                            setClipboard(info);
                            Common.showToast(context, "内容已复制到剪贴板");
                        }
                    });
                    break;

                case 0x1213:
                    List<String> arraytuwen = (List<String>) msg.obj;
                    //商品详情地址 2 是标志位
                    String spTexttuwen = "2" + commodity.getName() + "链接地址：" + URLConfig.PREDUCT_URL + commodity.getId();
                    spWuLiao.add(spTexttuwen);

                    detailsEntity.setListImg(arraytuwen);
                    detailsEntity.setCommodity(commodity);
                    break;

                case 0x456:
                    final String currentNum = String.valueOf(msg.arg2);

                    downloadDialog.setCurrentNum(currentNum);

                    tiShiDialog = new PictureAllredeTiShiDialog(context);
                    if (msg.arg1 == msg.arg2) {
                        try {
                            Thread.sleep(1000);
                            tiShiDialog.show();
                            downloadDialog.cancel();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    //利用Picasso框架将在线Url格式的图片转换成bitmap
    private void getImgerUrlOfBitmap(final String imgUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Bitmap bitmap = null;
                try {
                    bitmap = Picasso.with(context).load(imgUrl).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                SavePicture.saveBitmap(bitmap, context);

            }
        }).start();
    }

    public void getRequestGoodsDetails(final String url) {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                List<String> spWuLiao = new ArrayList<>();
                super.run();
                try {
                    String res = NetWorkUtil.requestGet(url);
                    Log.d(TAG, "商品信息……" + res);
                    JSONObject jsonObject = new JSONObject(res);
                    String flag = jsonObject.optString("success");
                    if (flag.length() > 0 && flag.equals("true")) {
                        String data = jsonObject.optString("data");
                        JSONObject object = new JSONObject(data);

                        JSONArray array = object.optJSONArray("shareList");

                        Log.d("Test", "handleMessage: arrr  " + array.length());

                        if (array.length() > 0) {

                            JSONObject jsonObject1 = new JSONObject();
                            for (int i = 0; i < array.length(); i++) {
                                final JSONObject object1 = (JSONObject) array.get(i);
                                //解析商品物料类型： 1主图  2 附图  3.文本物料
                                String shareType = object1.optString("shareType");

                                System.out.println(shareType);
                                if (shareType.equals("1")) {
                                    System.out.println("*****" + object1.optString("shareContent"));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Picasso.with(context).load(object1.optString("shareContent")).into(goodsZhuTu);
                                        }
                                    });
                                }
                                if (shareType.equals("2")) {
                                    String imgurl = "1" + object1.optString("shareContent");
                                    spWuLiao.add(imgurl);
                                    Message message = Message.obtain();
                                    message.obj = spWuLiao;

                                    message.arg1 = array.length();

                                    message.what = 0x1212;
                                    handler.sendMessage(message);
                                    sleep(2500);
                                }
                                if (shareType.equals("3")) {
                                    String imgurl = "2" + object1.optString("shareContent");
                                    spWuLiao.add(imgurl);
                                    Message message = Message.obtain();
                                    message.obj = spWuLiao;
                                    message.arg1 = array.length();
                                    message.what = 0x1212;
                                    handler.sendMessage(message);
                                    sleep(2500);
                                }

                            }
                        }
                    } else {
                        Common.showToast(context, "接口请求失败……");
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "run: 商品相信接口返回错误……");
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    class TestThread implements Callable<String>{
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public String call() throws Exception {
            String res = "";
            try {
                res = NetWorkUtil.requestGet(url);
                Log.d(TAG, "商品信息……" + res);
                JSONObject jsonObject = new JSONObject(res);
                String flag = jsonObject.optString("success");
            } catch (JSONException e) {
                Log.d(TAG, "run: 商品相信接口返回错误……");
                e.printStackTrace();
            }
            return res;
        }
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
            case R.id.goods_details_hb:             //海报合成
                //跳转到海报生成页面
                Intent intent = new Intent(context, HaiBaoCompoundActivity.class);
                intent.putExtra("commodity", commodity);
                startActivity(intent);
                break;

            case R.id.goods_details_tuwen:          //图文批量分享

                ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
                TestThread testThread = new TestThread();
                Future<String> stringFuture = poolExecutor.submit(testThread);
                //保存图片的地址
                List<String> strImgerUrl = new ArrayList<>();
                //保存文案的内容
                String textContent = null;
                try {
                    String s = stringFuture.get();
                    JSONObject jsonObject = new JSONObject(s);
                    String data = jsonObject.optString("data");
                    JSONObject object = new JSONObject(data);
                    JSONArray array = object.optJSONArray("shareList");
                    System.out.println("array"+array.length());
                    for (int i = 0; i <array.length() ; i++) {
                        JSONObject objecti = array.getJSONObject(i);
                        String shareType = objecti.optString("shareType");

                        if(shareType.equals("2")){
                            strImgerUrl.add(objecti.optString("shareContent"));
                        }

                        if(shareType.equals("3")){
                            textContent = objecti.optString("shareContent");
                        }
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //去掉商品文案中的html代码
                textContent = textContent.replaceAll("<p>","");
                textContent = textContent.replaceAll("</p>","");

                //复制文案到剪贴板
                setClipboard(textContent.replaceAll("<br/>",""));


                downloadDialog = new PictureDownloadDialog(context, "/" + strImgerUrl.size());
                downloadDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int index = 0; index < strImgerUrl.size(); index++) {
                            int i = index;

                            getImgerUrlOfBitmap(strImgerUrl.get(index));

                            handler.obtainMessage(0x456, strImgerUrl.size(), i + 1).sendToTarget();

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
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
