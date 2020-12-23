package com.em.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.em.R;
import com.em.base.BaseFragment;
import com.em.common.Common;
import com.em.config.SpCateConstant;
import com.em.config.URLConfig;
import com.em.dialog.APPUpdateTiShiDialog;
import com.em.goods_details.GoodsDetailsActivity;
import com.em.home_frg_menu.MenuItemContentActivity;
import com.em.pojo.Commodity;
import com.em.utils.GlideImageLoader;
import com.em.utils.NetWorkUtil;
import com.em.utils.SpUtils;
import com.em.utils.SystemTools;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/30 0030 16:05
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "HomeFragment";
    //轮播图
    private Banner banner;
    private List images;
    private LinearLayout homeProduct, fangyiProduct, kouqiangProduct, haocaiProduct, yankeProduct, jijiuProduct, shouyongProduct, shiyanshiProduct, homeSouSuo;

    //添加view的容器
    private LinearLayout container;

    @Override
    public void initView(View view) {
        banner = view.findViewById(R.id.banner);
        homeProduct = view.findViewById(R.id.home_frg_jiayong_pr);
        fangyiProduct = view.findViewById(R.id.home_frg_fangyi_pr);
        kouqiangProduct = view.findViewById(R.id.home_frg_kouqiang_pr);
        haocaiProduct = view.findViewById(R.id.home_frg_haocai_pr);
        yankeProduct = view.findViewById(R.id.home_frg_yanke_pr);
        jijiuProduct = view.findViewById(R.id.home_frg_jijiu_pr);
        shouyongProduct = view.findViewById(R.id.home_frg_shouyong_pr);
        shiyanshiProduct = view.findViewById(R.id.home_frg_shiyanshi_pr);
        homeSouSuo = view.findViewById(R.id.home_frg_sousuolan);

        container = view.findViewById(R.id.container_view);

    }

    @Override
    public int getContextViewId() {
        return R.layout.home_fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initData(View view) {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        //先检查版本更新
        getSystemVersion();

        images = new ArrayList();
        //图片资源
        images.add("http://solr.emaimed.com/ejsimage/images/banner.png");
        images.add("http://solr.emaimed.com/ejsimage/images/banner2.png");
        images.add("http://solr.emaimed.com/ejsimage/images/banner3.png");
        //images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607425924911&di=a9e03bd24f19d95ad2f85f45ee133db2&imgtype=0&src=http%3A%2F%2Fpic.17qq.com%2Fuploads%2Fhhgcnncppv.jpeg");

        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.start();

        //设置轮播图的点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //Common.showToast(getContext(), position + "");
            }
        });

        //轮播图设置圆角
        banner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
            }
        });
        banner.setClipToOutline(true);


        //向服务器请求数据(加载每日精选数据)
        getRequestSP();


    }

    @Override
    public void initListener() {
        homeProduct.setOnClickListener(this);
        fangyiProduct.setOnClickListener(this);
        kouqiangProduct.setOnClickListener(this);
        haocaiProduct.setOnClickListener(this);
        yankeProduct.setOnClickListener(this);
        jijiuProduct.setOnClickListener(this);
        shouyongProduct.setOnClickListener(this);
        shiyanshiProduct.setOnClickListener(this);
        homeSouSuo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.home_frg_jiayong_pr:
                Intent jyProduct = new Intent(getContext(), MenuItemContentActivity.class);
                jyProduct.putExtra("tite", SpCateConstant.JYSP);
                startActivity(jyProduct);
                break;

            case R.id.home_frg_fangyi_pr:
                Intent fyProduct = new Intent(getContext(), MenuItemContentActivity.class);
                fyProduct.putExtra("tite", SpCateConstant.FYZQ);
                startActivity(fyProduct);
                break;

            case R.id.home_frg_kouqiang_pr:
                Intent kqProduct = new Intent(getContext(), MenuItemContentActivity.class);
                kqProduct.putExtra("tite", SpCateConstant.KQZQ);
                startActivity(kqProduct);
                break;

            case R.id.home_frg_haocai_pr:
                Intent hcProduct = new Intent(getContext(), MenuItemContentActivity.class);
                hcProduct.putExtra("tite", SpCateConstant.HCZQ);
                startActivity(hcProduct);
                break;

            case R.id.home_frg_yanke_pr:
                Intent ykProduct = new Intent(getContext(), MenuItemContentActivity.class);
                ykProduct.putExtra("tite", SpCateConstant.YKZQ);
                startActivity(ykProduct);
                break;

            case R.id.home_frg_jijiu_pr:
                Intent jjProduct = new Intent(getContext(), MenuItemContentActivity.class);
                jjProduct.putExtra("tite", SpCateConstant.JJZQ);
                startActivity(jjProduct);
                break;

            case R.id.home_frg_shouyong_pr:
                Intent syProduct = new Intent(getContext(), MenuItemContentActivity.class);
                syProduct.putExtra("tite", SpCateConstant.SYZQ);
                startActivity(syProduct);
                break;

            case R.id.home_frg_shiyanshi_pr:
                Intent sysProduct = new Intent(getContext(), MenuItemContentActivity.class);
                sysProduct.putExtra("tite", SpCateConstant.SYS);
                startActivity(sysProduct);
                break;

            case R.id.home_frg_sousuolan:

                final File file = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)));


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Bitmap bitmap = Picasso.with(getContext()).load(file).get();
                            System.out.println(66666);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                break;
        }
    }

    @Override
    public void destroy() {
    }


    //动态添加view
    public void addDataView(final List<Commodity> objects, final Context context, LinearLayout v) {
        for (int i = 0; i < 6; i++) {
            LayoutInflater inflate = LayoutInflater.from(context);
            View view = inflate.inflate(R.layout.home_frg_item, null);

            //设置宽度
            LinearLayout marth = view.findViewById(R.id.menu_item_adapter_sharp_01);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) marth.getLayoutParams();

            //引用系统适配的宽度
            int bottom = context.getResources().getDimensionPixelSize(R.dimen.dp_15);
            int ltr = context.getResources().getDimensionPixelSize(R.dimen.dp_0);
            layoutParams.setMargins(ltr, ltr, ltr, bottom);
            marth.setLayoutParams(layoutParams);


            ImageView productImger = view.findViewById(R.id.menu_item_adapter_zhutu);
            TextView productName = view.findViewById(R.id.menu_item_adapter_name);
            TextView productPrice = view.findViewById(R.id.menu_item_adapter_price);
            TextView productBiLi = view.findViewById(R.id.menu_item_adapter_bili);
            TextView productMoney = view.findViewById(R.id.menu_item_adapter_menoy);

            Picasso.with(context).load(objects.get(i).getMasterImg()).into(productImger);
            productName.setText(objects.get(i).getName());
            productBiLi.setText("返佣比例" + objects.get(i).getSaleScale() * 100 + "%");
            productPrice.setText("￥" + objects.get(i).getMarktPrice());
            //小数点保留两位
            Float sale = objects.get(i).getMarktPrice() * objects.get(i).getSaleScale();
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            productMoney.setText("立即赚钱 ￥ " + decimalFormat.format(sale));

            final int position = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:
                            Intent intent0 = new Intent(getContext(), GoodsDetailsActivity.class);
                            intent0.putExtra("commodit", objects.get(0));
                            startActivity(intent0);
                            break;
                        case 1:
                            Intent intent1 = new Intent(getContext(), GoodsDetailsActivity.class);
                            intent1.putExtra("commodit", objects.get(1));
                            startActivity(intent1);
                            break;
                        case 2:
                            Intent intent2 = new Intent(getContext(), GoodsDetailsActivity.class);
                            intent2.putExtra("commodit", objects.get(2));
                            startActivity(intent2);
                            break;
                        case 3:
                            Intent intent3 = new Intent(getContext(), GoodsDetailsActivity.class);
                            intent3.putExtra("commodit", objects.get(3));
                            startActivity(intent3);
                            break;
                        case 4:
                            Intent intent4 = new Intent(getContext(), GoodsDetailsActivity.class);
                            intent4.putExtra("commodit", objects.get(4));
                            startActivity(intent4);
                            break;
                        case 5:
                            Intent intent5 = new Intent(getContext(), GoodsDetailsActivity.class);
                            intent5.putExtra("commodit", objects.get(5));
                            startActivity(intent5);
                            break;
                        default:
                            Log.d(TAG, "onClick: 首页出错了");
                    }
                }
            });
            v.addView(view);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            //系统更新
            if (msg.what == 0x45) {
                String versionInfo = (String) msg.obj;
                try {
                    JSONObject jsonObject = new JSONObject(versionInfo);
                    String success = jsonObject.optString("success");
                    String data = jsonObject.optString("data");
                    if (success != null && !(success.equals("")) && success.equals("true")) {
                        JSONObject object = new JSONObject(data);
                        Integer versionCode = Integer.parseInt(object.optString("versionCode"));
                        String versionName = object.optString("versionName");

                        SpUtils.putVersionInfo(getContext(), versionName);

                        if (versionCode != null && !(versionName.equals("")) && !(versionName.equals("null"))) {
                            //当本地版本低于服务器版本时，系统更新
                            if (SystemTools.getVersion(getContext()) < versionCode) {

                                APPUpdateTiShiDialog dialog = new APPUpdateTiShiDialog(getContext(), versionName);
                                dialog.show();

                            } else {
                                Log.d(TAG, "目前是最新版本");
                            }
                        } else {
                            Log.d(TAG, "获取服务器版本为空");
                        }

                    } else {
                        Log.d(TAG, "请求版本自检接口失败");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            List<Commodity> commodityList = null;
            if (msg.what == 0x11) {
                commodityList = (List<Commodity>) msg.obj;

                //去掉999999.0 可询价
                for (int i = 0; i < commodityList.size(); i++) {
                    if (commodityList.get(i).getMarktPrice().toString().equals("999999.0")) {
                        commodityList.remove(commodityList.get(i));
                        i--;
                    }
                }
                addDataView(commodityList, getContext(), container);
            }
        }
    };

    public void getRequestSP() {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                super.run();
                List<Commodity> commodity = initRequestData();
                Message message = Message.obtain();
                message.obj = commodity;
                message.what = 0x11;
                handler.sendMessage(message);
            }
        }.start();
    }

    //版本自动检测
    public void getSystemVersion() {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                super.run();
                String versionInfo = NetWorkUtil.requestGet(URLConfig.INSPECTION);

                Message message = Message.obtain();
                message.obj = versionInfo;
                message.what = 0x45;
                handler.sendMessage(message);
            }
        }.start();
    }

    //请求数据并解析服务器返回的数据
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<Commodity> initRequestData() {
        List<Commodity> commodityList = new ArrayList<>();
        final String url = URLConfig.ALLSPURL;
        final String res = NetWorkUtil.requestGet(url);
        try {
            JSONObject jsonObject = new JSONObject(res);
            String success = jsonObject.getString("success");
            String message = jsonObject.getString("message");
            JSONArray jsonArray = jsonObject.getJSONArray("rows");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                Commodity commodity = new Commodity();
                commodity.setId(Integer.parseInt(jsonObject1.getString("id")));
                commodity.setName(jsonObject1.getString("name1"));
                commodity.setMarktPrice(Float.parseFloat(jsonObject1.getString("mallPcPrice")));
                commodity.setMasterImg(URLConfig.TPURL + jsonObject1.getString("masterImg"));
                commodity.setSaleScale(Float.parseFloat(jsonObject1.getString("saleScale1")));
                commodityList.add(commodity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commodityList;
    }

}
