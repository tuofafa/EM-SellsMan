package com.em.utils;
import android.graphics.Color;
import android.widget.TextView;
/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/12/9 0009 16:31
 */
public class SelectMenu {

    public static final String ALL_PRODUCT = "0X00";     //全部商品
    public static final String JY_PRODUCT = "0X000";     //家用产品
    public static final String FY_PRODUCT = "0X001";     //防疫物资
    public static final String KQ_PRODUCT = "0X010";     //口腔专区
    public static final String HC_PRODUCT = "0X011";     //耗材专区
    public static final String YK_PRODUCT = "0X100";     //眼科专区
    public static final String JJ_PRODUCT = "0X101";     //急救专区
    public static final String SY_PRODUCT = "0X110";     //兽用专区
    public static final String SYS_PRODUCT = "0X111";    //实验室


    public static void selectMenu(TextView all,TextView allXHX,TextView jiayong,TextView jiayongXHX,TextView fangyi,TextView fangyiXHX,
                                  TextView kouqiang,TextView kouqiangXHX,TextView haocai,TextView haocaiXHX,
                                  TextView yanke,TextView yankeXHX,TextView jijiu,TextView jijiuXHX,
                                  TextView shouyong,TextView shouyongXHX, TextView shiyanshi,TextView shiyanshiXHX,
                                  String selector){

        switch (selector){

            case SelectMenu.ALL_PRODUCT:
                //设置选中的
                all.setTextColor(Color.parseColor("#06C061"));
                allXHX.setBackgroundColor(Color.parseColor("#06C061"));

                //设置未选中的文字颜色
                jiayong.setTextColor(Color.parseColor("#666666"));
                fangyi.setTextColor(Color.parseColor("#666666"));
                kouqiang.setTextColor(Color.parseColor("#666666"));
                haocai.setTextColor(Color.parseColor("#666666"));
                yanke.setTextColor(Color.parseColor("#666666"));
                jijiu.setTextColor(Color.parseColor("#666666"));
                shouyong.setTextColor(Color.parseColor("#666666"));
                shiyanshi.setTextColor(Color.parseColor("#666666"));

                //设置未选中的下划线颜色
                jiayongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                fangyiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                kouqiangXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                haocaiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                yankeXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                jijiuXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shouyongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shiyanshiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));

                break;
            case SelectMenu.JY_PRODUCT:
                //设置选中的
                jiayong.setTextColor(Color.parseColor("#06C061"));
                jiayongXHX.setBackgroundColor(Color.parseColor("#06C061"));

                //设置未选中的文字颜色

                fangyi.setTextColor(Color.parseColor("#666666"));
                kouqiang.setTextColor(Color.parseColor("#666666"));
                haocai.setTextColor(Color.parseColor("#666666"));
                yanke.setTextColor(Color.parseColor("#666666"));
                jijiu.setTextColor(Color.parseColor("#666666"));
                shouyong.setTextColor(Color.parseColor("#666666"));
                shiyanshi.setTextColor(Color.parseColor("#666666"));

                //设置未选中的下划线颜色
                fangyiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                kouqiangXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                haocaiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                yankeXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                jijiuXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shouyongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shiyanshiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));

                all.setTextColor(Color.parseColor("#666666"));
                allXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                break;

            case SelectMenu.FY_PRODUCT:
                //设置选中的
                fangyi.setTextColor(Color.parseColor("#06C061"));
                fangyiXHX.setBackgroundColor(Color.parseColor("#06C061"));

                //设置未选中的文字颜色
                jiayong.setTextColor(Color.parseColor("#666666"));
                kouqiang.setTextColor(Color.parseColor("#666666"));
                haocai.setTextColor(Color.parseColor("#666666"));
                yanke.setTextColor(Color.parseColor("#666666"));
                jijiu.setTextColor(Color.parseColor("#666666"));
                shouyong.setTextColor(Color.parseColor("#666666"));
                shiyanshi.setTextColor(Color.parseColor("#666666"));

                //设置未选中的下划线颜色
                jiayongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                kouqiangXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                haocaiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                yankeXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                jijiuXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shouyongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shiyanshiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));

                all.setTextColor(Color.parseColor("#666666"));
                allXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                break;

            case SelectMenu.KQ_PRODUCT:
                //设置选中的
                kouqiang.setTextColor(Color.parseColor("#06C061"));
                kouqiangXHX.setBackgroundColor(Color.parseColor("#06C061"));

                //设置未选中的文字颜色
                fangyi.setTextColor(Color.parseColor("#666666"));
                jiayong.setTextColor(Color.parseColor("#666666"));
                haocai.setTextColor(Color.parseColor("#666666"));
                yanke.setTextColor(Color.parseColor("#666666"));
                jijiu.setTextColor(Color.parseColor("#666666"));
                shouyong.setTextColor(Color.parseColor("#666666"));
                shiyanshi.setTextColor(Color.parseColor("#666666"));

                //设置未选中的下划线颜色
                jiayongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                fangyiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                haocaiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                yankeXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                jijiuXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shouyongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shiyanshiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));

                all.setTextColor(Color.parseColor("#666666"));
                allXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                break;

            case SelectMenu.HC_PRODUCT:
                //设置选中的
                haocai.setTextColor(Color.parseColor("#06C061"));
                haocaiXHX.setBackgroundColor(Color.parseColor("#06C061"));

                //设置未选中的文字颜色
                fangyi.setTextColor(Color.parseColor("#666666"));
                kouqiang.setTextColor(Color.parseColor("#666666"));
                jiayong.setTextColor(Color.parseColor("#666666"));
                yanke.setTextColor(Color.parseColor("#666666"));
                jijiu.setTextColor(Color.parseColor("#666666"));
                shouyong.setTextColor(Color.parseColor("#666666"));
                shiyanshi.setTextColor(Color.parseColor("#666666"));

                //设置未选中的下划线颜色
                jiayongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                kouqiangXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                fangyiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                yankeXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                jijiuXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shouyongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shiyanshiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));

                all.setTextColor(Color.parseColor("#666666"));
                allXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                break;
            case SelectMenu.YK_PRODUCT:
                //设置选中的
                yanke.setTextColor(Color.parseColor("#06C061"));
                yankeXHX.setBackgroundColor(Color.parseColor("#06C061"));

                //设置未选中的文字颜色
                fangyi.setTextColor(Color.parseColor("#666666"));
                kouqiang.setTextColor(Color.parseColor("#666666"));
                haocai.setTextColor(Color.parseColor("#666666"));
                jiayong.setTextColor(Color.parseColor("#666666"));
                jijiu.setTextColor(Color.parseColor("#666666"));
                shouyong.setTextColor(Color.parseColor("#666666"));
                shiyanshi.setTextColor(Color.parseColor("#666666"));

                //设置未选中的下划线颜色
                jiayongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                kouqiangXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                haocaiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                fangyiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                jijiuXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shouyongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shiyanshiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));

                all.setTextColor(Color.parseColor("#666666"));
                allXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                break;
            case SelectMenu.JJ_PRODUCT:
                //设置选中的
                jijiu.setTextColor(Color.parseColor("#06C061"));
                jijiuXHX.setBackgroundColor(Color.parseColor("#06C061"));

                //设置未选中的文字颜色
                fangyi.setTextColor(Color.parseColor("#666666"));
                kouqiang.setTextColor(Color.parseColor("#666666"));
                haocai.setTextColor(Color.parseColor("#666666"));
                yanke.setTextColor(Color.parseColor("#666666"));
                jiayong.setTextColor(Color.parseColor("#666666"));
                shouyong.setTextColor(Color.parseColor("#666666"));
                shiyanshi.setTextColor(Color.parseColor("#666666"));

                //设置未选中的下划线颜色
                jiayongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                kouqiangXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                haocaiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                yankeXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                fangyiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shouyongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shiyanshiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));

                all.setTextColor(Color.parseColor("#666666"));
                allXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                break;

            case SelectMenu.SY_PRODUCT:
                //设置选中的
                shouyong.setTextColor(Color.parseColor("#06C061"));
                shouyongXHX.setBackgroundColor(Color.parseColor("#06C061"));

                //设置未选中的文字颜色
                fangyi.setTextColor(Color.parseColor("#666666"));
                kouqiang.setTextColor(Color.parseColor("#666666"));
                haocai.setTextColor(Color.parseColor("#666666"));
                yanke.setTextColor(Color.parseColor("#666666"));
                jijiu.setTextColor(Color.parseColor("#666666"));
                jiayong.setTextColor(Color.parseColor("#666666"));
                shiyanshi.setTextColor(Color.parseColor("#666666"));

                //设置未选中的下划线颜色
                jiayongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                kouqiangXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                haocaiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                yankeXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                jijiuXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                fangyiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shiyanshiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));

                all.setTextColor(Color.parseColor("#666666"));
                allXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                break;
            case SelectMenu.SYS_PRODUCT:
                //设置选中的
                shiyanshi.setTextColor(Color.parseColor("#06C061"));
                shiyanshiXHX.setBackgroundColor(Color.parseColor("#06C061"));

                //设置未选中的文字颜色
                fangyi.setTextColor(Color.parseColor("#666666"));
                kouqiang.setTextColor(Color.parseColor("#666666"));
                haocai.setTextColor(Color.parseColor("#666666"));
                yanke.setTextColor(Color.parseColor("#666666"));
                jijiu.setTextColor(Color.parseColor("#666666"));
                shouyong.setTextColor(Color.parseColor("#666666"));
                jiayong.setTextColor(Color.parseColor("#666666"));

                //设置未选中的下划线颜色
                jiayongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                kouqiangXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                haocaiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                yankeXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                jijiuXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                shouyongXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                fangyiXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));

                all.setTextColor(Color.parseColor("#666666"));
                allXHX.setBackgroundColor(Color.parseColor("#F7F8FA"));
                break;
            default:
                break;
        }
    }
}
