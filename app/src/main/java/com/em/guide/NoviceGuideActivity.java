package com.em.guide;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.em.R;
import com.em.base.BaseActivity;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/12/24 0024 10:06
 */
public class NoviceGuideActivity extends BaseActivity<NoviceGuidePersent> {
    private LinearLayout click1,click2,click3,click4,click5,click6,click7,click8,click9,click10;
    private LinearLayout content1,content2,content3,content4,content5,content6,content7,content8,content9,content10;

    @Override
    public void initView() {

        click1 = findViewById(R.id.img_click1);
        content1 = findViewById(R.id.click_content1);

        click2 = findViewById(R.id.img_click2);
        content2 = findViewById(R.id.click_content2);

        click3 = findViewById(R.id.img_click3);
        content3 = findViewById(R.id.click_content3);

        click4 = findViewById(R.id.img_click4);
        content4 = findViewById(R.id.click_content4);

        click5 = findViewById(R.id.img_click5);
        content5 = findViewById(R.id.click_content5);

        click6 = findViewById(R.id.img_click6);
        content6 = findViewById(R.id.click_content6);

        click6 = findViewById(R.id.img_click6);
        content6 = findViewById(R.id.click_content6);

        click7 = findViewById(R.id.img_click7);
        content7 = findViewById(R.id.click_content7);

        click8 = findViewById(R.id.img_click8);
        content8 = findViewById(R.id.click_content8);


        click9 = findViewById(R.id.img_click9);
        content9 = findViewById(R.id.click_content9);

        click10 = findViewById(R.id.img_click10);
        content10 = findViewById(R.id.click_content10);

    }

    @Override
    public int getContextViewId() {
        return R.layout.novice_guide;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        click1.setOnClickListener(this);
        click2.setOnClickListener(this);
        click3.setOnClickListener(this);
        click4.setOnClickListener(this);
        click5.setOnClickListener(this);
        click6.setOnClickListener(this);

        click7.setOnClickListener(this);
        click8.setOnClickListener(this);
        click9.setOnClickListener(this);
        click10.setOnClickListener(this);
    }

    @Override
    public void destroy() {

    }

    @Override
    public NoviceGuidePersent getmPersenterInstance() {
        return null;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.img_click1:
                //View.INVISIBLE  4
                //View.VISIBLE 0
                //View.GONE 8
                if(content1.getVisibility() == 8){

                    //设置在同一时刻只能打开一个item，打开下一个时关闭上一个
                    selectMenu(content1, content2, content3, content4, content5, content6, content7, content8, content9, content10, 1);


                }else if(content1.getVisibility() == 0){
                    content1.setVisibility(View.GONE);
                }
                break;
            case R.id.img_click2:

                if(content2.getVisibility() == 8){
                    selectMenu(content1, content2, content3, content4, content5, content6, content7, content8, content9, content10, 2);

                }else if(content2.getVisibility() == 0){
                    content2.setVisibility(View.GONE);
                }
                break;
            case R.id.img_click3:

                if(content3.getVisibility() == 8){
                    selectMenu(content1, content2, content3, content4, content5, content6, content7, content8, content9, content10, 3);

                }else if(content3.getVisibility() == 0){
                    content3.setVisibility(View.GONE);
                }
                break;
            case R.id.img_click4:

                if(content4.getVisibility() == 8){
                    selectMenu(content1, content2, content3, content4, content5, content6, content7, content8, content9, content10, 4);

                }else if(content4.getVisibility() == 0){
                    content4.setVisibility(View.GONE);
                }
                break;
            case R.id.img_click5:

                if(content5.getVisibility() == 8){
                    selectMenu(content1, content2, content3, content4, content5, content6, content7, content8, content9, content10, 5);

                }else if(content5.getVisibility() == 0){
                    content5.setVisibility(View.GONE);
                }
                break;
            case R.id.img_click6:

                if(content6.getVisibility() == 8){
                    selectMenu(content1, content2, content3, content4, content5, content6, content7, content8, content9, content10, 6);

                }else if(content6.getVisibility() == 0){
                    content6.setVisibility(View.GONE);
                }
                break;

            case R.id.img_click7:

                if(content7.getVisibility() == 8){
                    selectMenu(content1, content2, content3, content4, content5, content6, content7, content8, content9, content10, 7);

                }else if(content7.getVisibility() == 0){
                    content7.setVisibility(View.GONE);
                }
                break;

            case R.id.img_click8:

                if(content8.getVisibility() == 8){
                    selectMenu(content1, content2, content3, content4, content5, content6, content7, content8, content9, content10, 8);

                }else if(content8.getVisibility() == 0){
                    content8.setVisibility(View.GONE);
                }
                break;
            case R.id.img_click9:

                if(content9.getVisibility() == 8){
                    selectMenu(content1, content2, content3, content4, content5, content6, content7, content8, content9, content10, 9);

                }else if(content9.getVisibility() == 0){
                    content9.setVisibility(View.GONE);
                }
                break;
            case R.id.img_click10:

                if(content10.getVisibility() == 8){
                    selectMenu(content1, content2, content3, content4, content5, content6, content7, content8, content9, content10, 10);

                }else if(content10.getVisibility() == 0){
                    content10.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void selectMenu(LinearLayout content1,LinearLayout content2,LinearLayout content3
    ,LinearLayout content4,LinearLayout content5,LinearLayout content6,LinearLayout content7,
                            LinearLayout content8,LinearLayout content9,LinearLayout content10,int type) {

        switch (type){
            case 1:
                content1.setVisibility(View.VISIBLE);
                content2.setVisibility(View.GONE);
                content3.setVisibility(View.GONE);
                content4.setVisibility(View.GONE);
                content5.setVisibility(View.GONE);
                content6.setVisibility(View.GONE);
                content7.setVisibility(View.GONE);
                content8.setVisibility(View.GONE);
                content9.setVisibility(View.GONE);
                content10.setVisibility(View.GONE);
                break;
            case 2:

                    content1.setVisibility(View.GONE);
                    content2.setVisibility(View.VISIBLE);
                    content3.setVisibility(View.GONE);
                    content4.setVisibility(View.GONE);
                    content5.setVisibility(View.GONE);
                    content6.setVisibility(View.GONE);
                    content7.setVisibility(View.GONE);
                    content8.setVisibility(View.GONE);
                    content9.setVisibility(View.GONE);
                    content10.setVisibility(View.GONE);
                    break;

            case 3:
                content1.setVisibility(View.GONE);
                content2.setVisibility(View.GONE);
                content3.setVisibility(View.VISIBLE);
                content4.setVisibility(View.GONE);
                content5.setVisibility(View.GONE);
                content6.setVisibility(View.GONE);
                content7.setVisibility(View.GONE);
                content8.setVisibility(View.GONE);
                content9.setVisibility(View.GONE);
                content10.setVisibility(View.GONE);

                break;

            case 4:

                content1.setVisibility(View.GONE);
                content2.setVisibility(View.GONE);
                content3.setVisibility(View.GONE);
                content4.setVisibility(View.VISIBLE);
                content5.setVisibility(View.GONE);
                content6.setVisibility(View.GONE);
                content7.setVisibility(View.GONE);
                content8.setVisibility(View.GONE);
                content9.setVisibility(View.GONE);
                content10.setVisibility(View.GONE);

                break;

            case 5:

                content1.setVisibility(View.GONE);
                content2.setVisibility(View.GONE);
                content3.setVisibility(View.GONE);
                content4.setVisibility(View.GONE);
                content5.setVisibility(View.VISIBLE);
                content6.setVisibility(View.GONE);
                content7.setVisibility(View.GONE);
                content8.setVisibility(View.GONE);
                content9.setVisibility(View.GONE);
                content10.setVisibility(View.GONE);
                break;
            case 6:
                content1.setVisibility(View.GONE);
                content2.setVisibility(View.GONE);
                content3.setVisibility(View.GONE);
                content4.setVisibility(View.GONE);
                content5.setVisibility(View.GONE);
                content6.setVisibility(View.VISIBLE);
                content7.setVisibility(View.GONE);
                content8.setVisibility(View.GONE);
                content9.setVisibility(View.GONE);
                content10.setVisibility(View.GONE);
                break;
            case 7:
                content1.setVisibility(View.GONE);
                content2.setVisibility(View.GONE);
                content3.setVisibility(View.GONE);
                content4.setVisibility(View.GONE);
                content5.setVisibility(View.GONE);
                content6.setVisibility(View.GONE);
                content7.setVisibility(View.VISIBLE);
                content8.setVisibility(View.GONE);
                content9.setVisibility(View.GONE);
                content10.setVisibility(View.GONE);
                break;
            case 8:
                content1.setVisibility(View.GONE);
                content2.setVisibility(View.GONE);
                content3.setVisibility(View.GONE);
                content4.setVisibility(View.GONE);
                content5.setVisibility(View.GONE);
                content6.setVisibility(View.GONE);
                content7.setVisibility(View.GONE);
                content8.setVisibility(View.VISIBLE);
                content9.setVisibility(View.GONE);
                content10.setVisibility(View.GONE);
                break;
            case 9:
                content1.setVisibility(View.GONE);
                content2.setVisibility(View.GONE);
                content3.setVisibility(View.GONE);
                content4.setVisibility(View.GONE);
                content5.setVisibility(View.GONE);
                content6.setVisibility(View.GONE);
                content7.setVisibility(View.GONE);
                content8.setVisibility(View.GONE);
                content9.setVisibility(View.VISIBLE);
                content10.setVisibility(View.GONE);
                break;
            case 10:
                content1.setVisibility(View.GONE);
                content2.setVisibility(View.GONE);
                content3.setVisibility(View.GONE);
                content4.setVisibility(View.GONE);
                content5.setVisibility(View.GONE);
                content6.setVisibility(View.GONE);
                content7.setVisibility(View.GONE);
                content8.setVisibility(View.GONE);
                content9.setVisibility(View.GONE);
                content10.setVisibility(View.VISIBLE);
                break;

        }
    }
}
