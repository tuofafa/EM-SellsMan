package com.em.home_frg_menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.em.adapter.MenuItemAdapter;
import com.em.base.BaseActivity;
import com.em.config.SpCateConstant;
import com.em.config.URLConfig;
import com.em.goods_details.GoodsDetailsActivity;
import com.em.pojo.Commodity;
import com.em.search.SearchActivity;
import com.em.utils.NetWorkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/12/10 0010 10:34
 */
public class MenuItemContentActivity extends BaseActivity<MenuItemContentPresent> {

    private static final String TAG = "MenuItemContentActivity";
    private Context context = MenuItemContentActivity.this;

    private TextView menuItemTitle;
    private RecyclerView menuItemDataList;
    private int tite;
    private LinearLayoutManager manager;
    private MenuItemAdapter adapter;
    private LinearLayout layoutBG;
    private ImageView menuItemSearch;

    @Override
    public void initView() {
        menuItemTitle = findViewById(R.id.home_frg_menu_title);
        menuItemDataList = findViewById(R.id.home_frg_menu_data_list);
        layoutBG = findViewById(R.id.menu_content_bg);
        menuItemSearch = findViewById(R.id.menu_item_search);
    }

    @Override
    public int getContextViewId() {
        return R.layout.menu_item_content;
    }

    @Override
    public void initData() {
        //设置背景色为红色
        layoutBG.setBackgroundColor(Color.parseColor("#F53437"));

        tite = getIntent().getIntExtra("tite",0);
        Log.d(TAG, "initData: tite"+tite);

        if(SpCateConstant.JYSP == tite){
            menuItemTitle.setText("家用产品");
        }else if(SpCateConstant.FYZQ == tite){
            menuItemTitle.setText("防疫物资");
        }else if(SpCateConstant.KQZQ == tite){
            menuItemTitle.setText("口腔专区");
        }else if(SpCateConstant.HCZQ == tite){
            menuItemTitle.setText("耗材专区");
        }else if(SpCateConstant.YKZQ == tite){
            menuItemTitle.setText("眼科专区");
        }else if (SpCateConstant.JJZQ == tite){
            menuItemTitle.setText("急救专区");
        }else if(SpCateConstant.JD == tite){
            menuItemTitle.setText("京东大卖场");
        }else if (SpCateConstant.SYS == tite){
            menuItemTitle.setText("实验室");
        }else {
            Log.d(TAG, "页面刷新出错");
        }

        //请求数据
        getRequestSP(tite+".html");
    }

    @Override
    public void initListener() {
        menuItemSearch.setOnClickListener(this);
    }

    @Override
    public void destroy() {

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            List<Commodity> commodityList = null;
            if(msg.what == 0x11){
                commodityList = (List<Commodity>) msg.obj;
            }
            //去掉999999.0 可询价
            for(int i=0;i<commodityList.size();i++){
                if(commodityList.get(i).getMarktPrice().toString().equals("999999.0")){
                    commodityList.remove(commodityList.get(i));
                    i--;
                }
            }
            manager = new LinearLayoutManager(context);
            adapter = new MenuItemAdapter(context,commodityList);
            menuItemDataList.setLayoutManager(manager);
            menuItemDataList.setAdapter(adapter);
            adapter.setClickListen(new MenuItemAdapter.setOnClickListen() {
                @Override
                public void onClick(int position, Commodity commodity) {
                    Intent intent = new Intent(context, GoodsDetailsActivity.class);
                    intent.putExtra("commodit",commodity);
                    startActivity(intent);
                }
            });
        }
    };

    @Override
    public MenuItemContentPresent getmPersenterInstance() {
        return null;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            //跳转到搜索页面
            case R.id.menu_item_search:
                Intent suosou = new Intent(context, SearchActivity.class);
                startActivity(suosou);
                break;
        }
    }

    //向服务器请求数据发送到Handler中
    public void getRequestSP(final String url){
        new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                super.run();
                List<Commodity> commodity = initRequestData(url);
                Message message = Message.obtain();
                message.obj = commodity;
                message.what = 0x11;
                handler.sendMessage(message);
            }
        }.start();
    }

    //请求数据并解析服务器返回的数据
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<Commodity> initRequestData(String Url){
        List<Commodity> commodityList = new ArrayList<>();
        final String url = URLConfig.SPURL+Url;
        final String res = NetWorkUtil.requestGet(url);
        try {
            JSONObject jsonObject = new JSONObject(res);
            String success = jsonObject.getString("success");
            String message = jsonObject.getString("message");
            JSONArray jsonArray = jsonObject.getJSONArray("rows");
            for(int i = 0;i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                Commodity commodity = new Commodity();
                commodity.setId(Integer.parseInt(jsonObject1.getString("id")));
                commodity.setName(jsonObject1.getString("name1"));
                commodity.setMarktPrice(Float.parseFloat(jsonObject1.getString("mallPcPrice")));
                commodity.setMasterImg(URLConfig.TPURL+jsonObject1.getString("masterImg"));
                commodity.setSaleScale(Float.parseFloat(jsonObject1.getString("saleScale1")));
                commodityList.add(commodity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commodityList;
    }
}
