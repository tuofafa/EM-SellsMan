package com.em.home_tgsp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.em.R;
import com.em.adapter.CategorizeResyclerviewAdapter;
import com.em.base.BaseActivity;
import com.em.config.SpCateConstant;
import com.em.fragment.AllPFragment;
import com.em.fragment.HPFragment;
import com.em.home_qztg.TGShareActivity;
import com.em.pojo.Categorize;
import com.em.search.SearchActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/17 0017 9:14
 */
public class TGCommodityActivity extends BaseActivity<TGCommodityPersent> implements ICommodity.V {

    private static final String TAG = "TGCommodityActivity";
    private RecyclerView spCategorize;  //商品分类
    private RecyclerView spList;        //商品列表
    private ImageView spSouSuo;         //搜索框
    private FloatingActionButton actionButton;  //悬浮按钮

    //商品主题分类
    private String[] categorize = {
            "精选商品",
            "家用商品",
            "防疫物资",
            "口腔专区",
            "耗材专区",
            "眼科专区",
            "实验室",
            "京东大卖场",
            "急救专区"
    };
    @Override
    public void initView() {
        //加载布局
        spSouSuo = findViewById(R.id.home_tgsp_sousuo);
        spCategorize = (RecyclerView) findViewById(R.id.sp_categorize);

        //初始化数据
        List<Categorize> list = initList(categorize);
        //设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        //设置布局管理器
        spCategorize.setLayoutManager(manager);
        //创建一个ResyclerView适配器
        CategorizeResyclerviewAdapter adapter = new CategorizeResyclerviewAdapter(list);
        //设置适配器
        spCategorize.setAdapter(adapter);
        //事件监听器
        adapter.setOnItemClickListener(new CategorizeResyclerviewAdapter.setOnItemClickListener(){
            @Override
            public void onClick(int position, Categorize sp, TextView tv) {
                String typeName;
                switch (position){
                    case 0:
                        //全部商品(放在这里需要点击一下全部商品菜单才会出现，不会有默认加载的情况)
                        replaceFragment(new AllPFragment());
                        break;
                    case 1:
                        //家用商品
                        replaceFragment(new HPFragment(), SpCateConstant.JYSP);
                        break;
                    case 2:
                        //防疫物资
                        replaceFragment(new HPFragment(),SpCateConstant.FYZQ);
                        break;
                    case 3:
                        //口腔专区
                        replaceFragment(new HPFragment(),SpCateConstant.KQZQ);
                         break;
                    case 4:
                        //耗材专区
                        replaceFragment(new HPFragment(),SpCateConstant.HCZQ);
                        break;
                    case 5:
                        //眼科专区
                        replaceFragment(new HPFragment(),SpCateConstant.YKZQ);
                        break;
                    case 6:
                        //实验室
                        replaceFragment(new HPFragment(),SpCateConstant.SYS);
                        break;
                    case 7:
                        //兽用专区
                        replaceFragment(new HPFragment(),SpCateConstant.JD);
                        break;
                    case 8:
                        //急救专区
                        replaceFragment(new HPFragment(),SpCateConstant.JJZQ);
                        break;
                    default:
                        Log.d(TAG, "没有合适的商品目录");
                        break;
                }

            }
        });
    }

    //初始化数据
    public List<Categorize> initList(String[] strings){
        List<Categorize> stringList = new ArrayList<>();
        for(int i=0;i<strings.length;i++){
            Categorize c = new Categorize();
            c.setcName(strings[i]);
            stringList.add(c);
        }
        return stringList;
    }


    @Override
    public int getContextViewId() {
        return R.layout.home_tgsp_activity;
    }

    @Override
    public void initData() {
        //默认加载全部商品
        replaceFragment(new AllPFragment());

    }

    @Override
    public void initListener() {
        spSouSuo.setOnClickListener(this);
    }

    @Override
    public void destroy() {

    }

    @Override
    public TGCommodityPersent getmPersenterInstance() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_tgsp_sousuo:
                Intent suosou = new Intent(TGCommodityActivity.this, SearchActivity.class);
                startActivity(suosou);
            break;
            default:
                break;
        }
    }
    //动态添加碎片
    public void replaceFragment(Fragment fragment,Integer spName){
        //在frangment传递数据
        Bundle bundle = new Bundle();
        bundle.putInt("cateName",spName);
        fragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.sp_List,fragment);
        transaction.commit();
    }

    //动态添加碎片
    public void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.sp_List,fragment);
        transaction.commit();
    }


}
