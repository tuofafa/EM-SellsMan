package com.em.home_frg_menu;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.em.R;
import com.em.base.BaseActivity;
import com.em.utils.SelectMenu;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/12/9 0009 10:28
 */
public class MenuActivity extends BaseActivity<MenuPresent> {
    private Context context = MenuActivity.this;
    private static final String TAG = "MenuActivity";


    private TextView allClick,allXHXClick;
    private TextView jiayongClick,jiayongXHXClick;
    private TextView fangyiClick,fangyiXHXClick;
    private TextView kouqiangClick,kouqiangXHXClick;
    private TextView haocaiClick,haocaiXHXClick;
    private TextView yankeClick,yankeXHXClick;
    private TextView jijiuClick,jijiuXHXClick;
    private TextView shouyongClick,shouyongXHXClick;
    private TextView shiyanshiClick,shiyanshiXHXClick;


    @Override
    public void initView() {
        allClick = findViewById(R.id.home_frg_menu_all_click);
        allXHXClick = findViewById(R.id.home_text_all_xiahuaxian);

        jiayongClick = findViewById(R.id.home_frg_menu_jiayong_click);
        jiayongXHXClick = findViewById(R.id.home_text_jiayong_xiahuaxian);

        fangyiClick = findViewById(R.id.home_frg_menu_fangyi_click);
        fangyiXHXClick = findViewById(R.id.home_text_fangyi_xiahuaxian);

        kouqiangClick = findViewById(R.id.home_frg_menu_kouqiang_click);
        kouqiangXHXClick = findViewById(R.id.home_text_kouqiang_xiahuaxian);

        haocaiClick = findViewById(R.id.home_frg_menu_haocai_click);
        haocaiXHXClick = findViewById(R.id.home_text_haocai_xiahuaxian);

        yankeClick = findViewById(R.id.home_frg_menu_yanke_click);
        yankeXHXClick = findViewById(R.id.home_text_yanke_xiahuaxian);

        jijiuClick = findViewById(R.id.home_frg_menu_jijiu_click);
        jijiuXHXClick = findViewById(R.id.home_text_jijiu_xiahuaxian);

        shouyongClick = findViewById(R.id.home_frg_menu_shouyong_click);
        shouyongXHXClick = findViewById(R.id.home_text_shouyong_xiahuaxian);

        shiyanshiClick = findViewById(R.id.home_frg_menu_shiyanshi_click);
        shiyanshiXHXClick = findViewById(R.id.home_text_shiyanshi_xiahuaxian);
    }
    @Override
    public int getContextViewId() {
        return R.layout.cate_frgment;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        //点击事件监听器
        allClick.setOnClickListener(this);
        jiayongClick.setOnClickListener(this);
        fangyiClick.setOnClickListener(this);
        kouqiangClick.setOnClickListener(this);
        haocaiClick.setOnClickListener(this);
        yankeClick.setOnClickListener(this);
        jijiuClick.setOnClickListener(this);
        shouyongClick.setOnClickListener(this);
        shiyanshiClick.setOnClickListener(this);

    }

    @Override
    public void destroy() {

    }

    @Override
    public MenuPresent getmPersenterInstance() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_frg_menu_all_click:
                SelectMenu.selectMenu(allClick,allXHXClick, jiayongClick,jiayongXHXClick,fangyiClick,fangyiXHXClick,kouqiangClick,kouqiangXHXClick,
                        haocaiClick,haocaiXHXClick,yankeClick,yankeXHXClick,jijiuClick,jijiuXHXClick,
                        shouyongClick,shouyongXHXClick,shiyanshiClick,shiyanshiXHXClick,SelectMenu.ALL_PRODUCT);

                break;
            case R.id.home_frg_menu_jiayong_click:

                SelectMenu.selectMenu(allClick,allXHXClick, jiayongClick,jiayongXHXClick,fangyiClick,fangyiXHXClick,kouqiangClick,kouqiangXHXClick,
                        haocaiClick,haocaiXHXClick,yankeClick,yankeXHXClick,jijiuClick,jijiuXHXClick,
                        shouyongClick,shouyongXHXClick,shiyanshiClick,shiyanshiXHXClick,SelectMenu.JY_PRODUCT);
                break;
            case R.id.home_frg_menu_fangyi_click:
                SelectMenu.selectMenu(allClick,allXHXClick,jiayongClick,jiayongXHXClick,fangyiClick,fangyiXHXClick,kouqiangClick,kouqiangXHXClick,
                        haocaiClick,haocaiXHXClick,yankeClick,yankeXHXClick,jijiuClick,jijiuXHXClick,
                        shouyongClick,shouyongXHXClick,shiyanshiClick,shiyanshiXHXClick,SelectMenu.FY_PRODUCT);
                break;
            case R.id.home_frg_menu_kouqiang_click:
                SelectMenu.selectMenu(allClick,allXHXClick,jiayongClick,jiayongXHXClick,fangyiClick,fangyiXHXClick,kouqiangClick,kouqiangXHXClick,
                        haocaiClick,haocaiXHXClick,yankeClick,yankeXHXClick,jijiuClick,jijiuXHXClick,
                        shouyongClick,shouyongXHXClick,shiyanshiClick,shiyanshiXHXClick,SelectMenu.KQ_PRODUCT);
                break;
            case R.id.home_frg_menu_haocai_click:
                SelectMenu.selectMenu(allClick,allXHXClick,jiayongClick,jiayongXHXClick,fangyiClick,fangyiXHXClick,kouqiangClick,kouqiangXHXClick,
                        haocaiClick,haocaiXHXClick,yankeClick,yankeXHXClick,jijiuClick,jijiuXHXClick,
                        shouyongClick,shouyongXHXClick,shiyanshiClick,shiyanshiXHXClick,SelectMenu.HC_PRODUCT);
                break;

            case R.id.home_frg_menu_yanke_click:
                SelectMenu.selectMenu(allClick,allXHXClick,jiayongClick,jiayongXHXClick,fangyiClick,fangyiXHXClick,kouqiangClick,kouqiangXHXClick,
                        haocaiClick,haocaiXHXClick,yankeClick,yankeXHXClick,jijiuClick,jijiuXHXClick,
                        shouyongClick,shouyongXHXClick,shiyanshiClick,shiyanshiXHXClick,SelectMenu.YK_PRODUCT);
                break;

            case R.id.home_frg_menu_jijiu_click:
                SelectMenu.selectMenu(allClick,allXHXClick,jiayongClick,jiayongXHXClick,fangyiClick,fangyiXHXClick,kouqiangClick,kouqiangXHXClick,
                        haocaiClick,haocaiXHXClick,yankeClick,yankeXHXClick,jijiuClick,jijiuXHXClick,
                        shouyongClick,shouyongXHXClick,shiyanshiClick,shiyanshiXHXClick,SelectMenu.JJ_PRODUCT);
                break;

            case R.id.home_frg_menu_shouyong_click:
                SelectMenu.selectMenu(allClick,allXHXClick,jiayongClick,jiayongXHXClick,fangyiClick,fangyiXHXClick,kouqiangClick,kouqiangXHXClick,
                        haocaiClick,haocaiXHXClick,yankeClick,yankeXHXClick,jijiuClick,jijiuXHXClick,
                        shouyongClick,shouyongXHXClick,shiyanshiClick,shiyanshiXHXClick,SelectMenu.SY_PRODUCT);
                break;

            case  R.id.home_frg_menu_shiyanshi_click:
                SelectMenu.selectMenu(allClick,allXHXClick,jiayongClick,jiayongXHXClick,fangyiClick,fangyiXHXClick,kouqiangClick,kouqiangXHXClick,
                        haocaiClick,haocaiXHXClick,yankeClick,yankeXHXClick,jijiuClick,jijiuXHXClick,
                        shouyongClick,shouyongXHXClick,shiyanshiClick,shiyanshiXHXClick,SelectMenu.SYS_PRODUCT);
                break;
        }
    }



}
