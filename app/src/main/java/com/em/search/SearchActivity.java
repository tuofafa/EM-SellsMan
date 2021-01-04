package com.em.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.em.R;
import com.em.adapter.SearchAdapter;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.goods_details.GoodsDetailsActivity;
import com.em.pojo.Commodity;
import com.em.utils.NetWorkUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/12/28 0028 9:27
 */
public class SearchActivity extends BaseActivity<SearchPresent> {

    private static final String TAG = "SearchActivity";
    private Context context = SearchActivity.this;
    private EditText searchContent;
    private TextView searchBtn;
    private RecyclerView searchRecyclerView;
    private LinearLayout noData;

    private SearchAdapter adapter;
    private LinearLayoutManager manger;

    @Override
    public void initView() {

        searchContent = findViewById(R.id.search_keyword);
        searchBtn = findViewById(R.id.search_btn);
        searchRecyclerView = findViewById(R.id.search_recyclerview);
        noData = findViewById(R.id.no_data);

    }

    @Override
    public int getContextViewId() {
        return R.layout.search_activity;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        searchBtn.setOnClickListener(this);
    }

    @Override
    public void destroy() {

    }

    @Override
    public SearchPresent getmPersenterInstance() {
        return null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.search_btn:
                if(searchContent.length()>0){
                    getRequestByKeyWord(searchContent.getText().toString());

                    System.out.println(searchContent.getText().toString());
                }else {
                    Common.showToast(context,"请输入要搜索的关键字");
                }

                break;
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0x11){
                String res = (String) msg.obj;
                System.out.println(res);
                List<Commodity> commodityList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String success = jsonObject.optString("success");
                    System.out.println("测试数据 "+success);
                    //Integer total = Integer.parseInt(jsonObject.optString("total"));
                    if(success.equals("true")){
                        //接口调用成功并且有数据返回
                        JSONArray arrays = jsonObject.optJSONArray("rows");
                        for (int i = 0; i <arrays.length(); i++) {
                            JSONObject object = arrays.getJSONObject(i);
                            String zhutu = object.optString("masterImg");   //主图
                            String name = object.optString("name1");        //商品名称
                            String mallPcPrice = object.optString("mallPcPrice");   //销售价格
                            String saleScale1 = object.optString("saleScale1");   //分佣比例
                            String id = object.optString("id");         //商品id

                            Commodity commodity = new Commodity();
                            if(zhutu.length()>0){
                                commodity.setMasterImg(URLConfig.TPURL+zhutu);
                            }
                            if(name.length()>0){
                                commodity.setName(name);
                            }else {
                                commodity.setName("null");
                            }
                            if (mallPcPrice.length()>0 && !mallPcPrice.equals("null")){
                                commodity.setMarktPrice(Float.parseFloat(mallPcPrice));
                            }else {
                                commodity.setMarktPrice(0.00F);
                            }
                            if (saleScale1.length()>0 && !saleScale1.equals("null")){
                                commodity.setSaleScale(Float.parseFloat(saleScale1));
                            }else {
                                commodity.setSaleScale(0.00F);
                            }
                            if (id.length()>0){
                                commodity.setId(Integer.parseInt(id));
                            }
                            commodityList.add(commodity);
                        }

                    }else {
                        //接口调用失败或没有数据返回
                        //如果搜索栏输入为表情，则服务器会报500错误，在此处拦截
                        JSONObject object = new JSONObject(res);
                        String status = object.optString("status");
                        if (status.equals("500")){
                            //显示没有数据页面
                            searchRecyclerView.setVisibility(View.GONE);
                            noData.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

                manger = new LinearLayoutManager(context);

                if(commodityList.size()>1){

                    noData.setVisibility(View.GONE);
                    searchRecyclerView.setVisibility(View.VISIBLE);

                    //去掉999999.0 可询价
                    for(int i=0;i<commodityList.size();i++){
                        if(commodityList.get(i).getMarktPrice().toString().equals("999999.0")){
                            commodityList.remove(commodityList.get(i));
                            i--;
                        }
                    }

                    adapter = new SearchAdapter(context,commodityList);
                    searchRecyclerView.setLayoutManager(manger);
                    searchRecyclerView.setAdapter(adapter);
                    adapter.setAllOnItemClickListener(new SearchAdapter.setAllOnItemClickListener() {
                        @Override
                        public void onClick(int position, Commodity sp) {
                            Intent intent = new Intent(context, GoodsDetailsActivity.class);

                            //过滤掉搜索中关键字的颜色标识
                            String regex = "<font color="+"\"red\">";
                            String regex1 = "</font>";
                            System.out.println(regex);
                            sp.setName(sp.getName().replaceAll(regex,""));
                            sp.setName(sp.getName().replaceAll(regex1,""));

                            intent.putExtra("commodit",sp);
                            startActivity(intent);
                        }
                    });

                }else {

                    //没有搜索到数据时显示另一个页面
                    searchRecyclerView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }


            }

        }
    };

    public void getRequestByKeyWord(String keyword){
        new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
               String res =  NetWorkUtil.requestGet(URLConfig.SEARCH_KEYWORD+"keyword="+keyword);
               Message message = Message.obtain();
               message.what = 0x11;
               message.obj = res;
               handler.sendMessage(message);
            }
        }.start();

    }

}
