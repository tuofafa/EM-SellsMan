package com.em.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.em.R;
import com.em.config.SpCateConstant;
import com.em.search.SearchActivity;
import com.em.utils.SelectMenu;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/30 0030 16:06
 */
public class CateFragment extends Fragment implements View.OnClickListener {

    private TextView allClick, allXHXClick;
    private TextView jiayongClick, jiayongXHXClick;
    private TextView fangyiClick, fangyiXHXClick;
    private TextView kouqiangClick, kouqiangXHXClick;
    private TextView haocaiClick, haocaiXHXClick;
    private TextView yankeClick, yankeXHXClick;
    private TextView jijiuClick, jijiuXHXClick;
    private TextView shouyongClick, shouyongXHXClick;
    private TextView shiyanshiClick, shiyanshiXHXClick;
    private ImageView cateSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cate_frgment, container, false);

        initView(view);
        initListen();
        initData();

        return view;
    }

    public void initView(View v) {
        allClick = v.findViewById(R.id.home_frg_menu_all_click);
        allXHXClick = v.findViewById(R.id.home_text_all_xiahuaxian);

        jiayongClick = v.findViewById(R.id.home_frg_menu_jiayong_click);
        jiayongXHXClick = v.findViewById(R.id.home_text_jiayong_xiahuaxian);

        fangyiClick = v.findViewById(R.id.home_frg_menu_fangyi_click);
        fangyiXHXClick = v.findViewById(R.id.home_text_fangyi_xiahuaxian);

        kouqiangClick = v.findViewById(R.id.home_frg_menu_kouqiang_click);
        kouqiangXHXClick = v.findViewById(R.id.home_text_kouqiang_xiahuaxian);

        haocaiClick = v.findViewById(R.id.home_frg_menu_haocai_click);
        haocaiXHXClick = v.findViewById(R.id.home_text_haocai_xiahuaxian);

        yankeClick = v.findViewById(R.id.home_frg_menu_yanke_click);
        yankeXHXClick = v.findViewById(R.id.home_text_yanke_xiahuaxian);

        jijiuClick = v.findViewById(R.id.home_frg_menu_jijiu_click);
        jijiuXHXClick = v.findViewById(R.id.home_text_jijiu_xiahuaxian);

        shouyongClick = v.findViewById(R.id.home_frg_menu_shouyong_click);
        shouyongXHXClick = v.findViewById(R.id.home_text_shouyong_xiahuaxian);

        shiyanshiClick = v.findViewById(R.id.home_frg_menu_shiyanshi_click);
        shiyanshiXHXClick = v.findViewById(R.id.home_text_shiyanshi_xiahuaxian);

        cateSearch = v.findViewById(R.id.cate_search);
    }

    public void initListen() {
        allClick.setOnClickListener(this);
        jiayongClick.setOnClickListener(this);
        fangyiClick.setOnClickListener(this);
        kouqiangClick.setOnClickListener(this);
        haocaiClick.setOnClickListener(this);
        yankeClick.setOnClickListener(this);
        jijiuClick.setOnClickListener(this);
        shouyongClick.setOnClickListener(this);
        shiyanshiClick.setOnClickListener(this);

        cateSearch.setOnClickListener(this);
    }

    public void initData() {
        //初始化第一个被选中
        SelectMenu.selectMenu(allClick, allXHXClick, jiayongClick, jiayongXHXClick, fangyiClick, fangyiXHXClick, kouqiangClick, kouqiangXHXClick,
                haocaiClick, haocaiXHXClick, yankeClick, yankeXHXClick, jijiuClick, jijiuXHXClick,
                shouyongClick, shouyongXHXClick, shiyanshiClick, shiyanshiXHXClick, SelectMenu.ALL_PRODUCT);

        replaceFragment(new AllPFragment());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_frg_menu_all_click:
                SelectMenu.selectMenu(allClick, allXHXClick, jiayongClick, jiayongXHXClick, fangyiClick, fangyiXHXClick, kouqiangClick, kouqiangXHXClick,
                        haocaiClick, haocaiXHXClick, yankeClick, yankeXHXClick, jijiuClick, jijiuXHXClick,
                        shouyongClick, shouyongXHXClick, shiyanshiClick, shiyanshiXHXClick, SelectMenu.ALL_PRODUCT);

                replaceFragment(new AllPFragment());
                break;
            case R.id.home_frg_menu_jiayong_click:

                SelectMenu.selectMenu(allClick, allXHXClick, jiayongClick, jiayongXHXClick, fangyiClick, fangyiXHXClick, kouqiangClick, kouqiangXHXClick,
                        haocaiClick, haocaiXHXClick, yankeClick, yankeXHXClick, jijiuClick, jijiuXHXClick,
                        shouyongClick, shouyongXHXClick, shiyanshiClick, shiyanshiXHXClick, SelectMenu.JY_PRODUCT);

                replaceFragment(new HPFragment(), SpCateConstant.JYSP);
                break;
            case R.id.home_frg_menu_fangyi_click:
                SelectMenu.selectMenu(allClick, allXHXClick, jiayongClick, jiayongXHXClick, fangyiClick, fangyiXHXClick, kouqiangClick, kouqiangXHXClick,
                        haocaiClick, haocaiXHXClick, yankeClick, yankeXHXClick, jijiuClick, jijiuXHXClick,
                        shouyongClick, shouyongXHXClick, shiyanshiClick, shiyanshiXHXClick, SelectMenu.FY_PRODUCT);

                replaceFragment(new HPFragment(), SpCateConstant.FYZQ);
                break;
            case R.id.home_frg_menu_kouqiang_click:
                SelectMenu.selectMenu(allClick, allXHXClick, jiayongClick, jiayongXHXClick, fangyiClick, fangyiXHXClick, kouqiangClick, kouqiangXHXClick,
                        haocaiClick, haocaiXHXClick, yankeClick, yankeXHXClick, jijiuClick, jijiuXHXClick,
                        shouyongClick, shouyongXHXClick, shiyanshiClick, shiyanshiXHXClick, SelectMenu.KQ_PRODUCT);

                replaceFragment(new HPFragment(), SpCateConstant.KQZQ);
                break;
            case R.id.home_frg_menu_haocai_click:
                SelectMenu.selectMenu(allClick, allXHXClick, jiayongClick, jiayongXHXClick, fangyiClick, fangyiXHXClick, kouqiangClick, kouqiangXHXClick,
                        haocaiClick, haocaiXHXClick, yankeClick, yankeXHXClick, jijiuClick, jijiuXHXClick,
                        shouyongClick, shouyongXHXClick, shiyanshiClick, shiyanshiXHXClick, SelectMenu.HC_PRODUCT);

                replaceFragment(new HPFragment(), SpCateConstant.HCZQ);
                break;

            case R.id.home_frg_menu_yanke_click:
                SelectMenu.selectMenu(allClick, allXHXClick, jiayongClick, jiayongXHXClick, fangyiClick, fangyiXHXClick, kouqiangClick, kouqiangXHXClick,
                        haocaiClick, haocaiXHXClick, yankeClick, yankeXHXClick, jijiuClick, jijiuXHXClick,
                        shouyongClick, shouyongXHXClick, shiyanshiClick, shiyanshiXHXClick, SelectMenu.YK_PRODUCT);

                replaceFragment(new HPFragment(), SpCateConstant.YKZQ);
                break;

            case R.id.home_frg_menu_jijiu_click:
                SelectMenu.selectMenu(allClick, allXHXClick, jiayongClick, jiayongXHXClick, fangyiClick, fangyiXHXClick, kouqiangClick, kouqiangXHXClick,
                        haocaiClick, haocaiXHXClick, yankeClick, yankeXHXClick, jijiuClick, jijiuXHXClick,
                        shouyongClick, shouyongXHXClick, shiyanshiClick, shiyanshiXHXClick, SelectMenu.JJ_PRODUCT);

                replaceFragment(new HPFragment(), SpCateConstant.JJZQ);
                break;

            case R.id.home_frg_menu_shouyong_click:
                SelectMenu.selectMenu(allClick, allXHXClick, jiayongClick, jiayongXHXClick, fangyiClick, fangyiXHXClick, kouqiangClick, kouqiangXHXClick,
                        haocaiClick, haocaiXHXClick, yankeClick, yankeXHXClick, jijiuClick, jijiuXHXClick,
                        shouyongClick, shouyongXHXClick, shiyanshiClick, shiyanshiXHXClick, SelectMenu.SY_PRODUCT);
                replaceFragment(new HPFragment(), SpCateConstant.JD);
                break;

            case R.id.home_frg_menu_shiyanshi_click:
                SelectMenu.selectMenu(allClick, allXHXClick, jiayongClick, jiayongXHXClick, fangyiClick, fangyiXHXClick, kouqiangClick, kouqiangXHXClick,
                        haocaiClick, haocaiXHXClick, yankeClick, yankeXHXClick, jijiuClick, jijiuXHXClick,
                        shouyongClick, shouyongXHXClick, shiyanshiClick, shiyanshiXHXClick, SelectMenu.SYS_PRODUCT);
                replaceFragment(new HPFragment(), SpCateConstant.SYS);
                break;

            case R.id.cate_search:
                Intent suosou = new Intent(getContext(), SearchActivity.class);
                startActivity(suosou);
                break;
        }
    }

    //动态添加碎片
    public void replaceFragment(Fragment fragment, Integer spName) {
        //在frangment传递数据
        Bundle bundle = new Bundle();
        bundle.putInt("cateName", spName);
        fragment.setArguments(bundle);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.sp_List, fragment);
        transaction.commit();
    }

    //动态添加碎片
    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.sp_List, fragment);
        transaction.commit();
    }
}
