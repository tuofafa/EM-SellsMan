package com.em.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.em.R;
import com.em.base.BaseFragment;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.guide.NoviceGuideActivity;
import com.em.home_customer.CumulativeCustomerActivity;
import com.em.home_earning.CumulativeEarningActivity;
import com.em.home_grzl.PersonInfoActivity;
import com.em.home_order.CumulativeOrderActivity;
import com.em.home_qztg.TGShareActivity;
import com.em.home_tgsp.TGCommodityActivity;
import com.em.home_tx.CashWithdrawalActivity;
import com.em.home_zhgl.AccountMangerActivity;
import com.em.notice.NoticeCentActivity;
import com.em.pojo.HomeEntity;
import com.em.pojo.User;
import com.em.utils.CircleTransform;
import com.em.utils.NetWorkUtil;
import com.em.utils.SpUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/30 0030 16:06
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "HomeActivity";

    private TextView LJSYTextview,DTXJETextview,LJKHTextview,LJDDTextview,homeNickName;
    private LinearLayout TGCPImageview,QZTGImageview,KHBBImageview,versionCheck;
    private LinearLayout TZZXImageview,XSYDImageview,ZHGLImageview;
    private Button LJMButton,CPLJMButton,WYTXButton;
    private LinearLayout LJSYClick, LJKHClick, LJDDClick;
    private ImageView GRZLImageview,homeTouXiang;

    @Override
    public void initView(View view) {

        LJMButton = view.findViewById(R.id.home_ljm);            //邀请码
        CPLJMButton = view.findViewById(R.id.home_cp_ljm);       //复制邀请码
        GRZLImageview = view.findViewById(R.id.home_grzl);       //个人资料
        LJSYTextview = view.findViewById(R.id.home_ljsy_text);        //累计收益
        LJKHTextview = view.findViewById(R.id.home_ljkh_text);        //累计客户
        LJDDTextview = view.findViewById(R.id.home_ljdd_text);        //累计订单
        DTXJETextview = view.findViewById(R.id.home_dtxje);      //待提现金额
        WYTXButton = view.findViewById(R.id.home_tx_but);        //提现按钮
        TGCPImageview = view.findViewById(R.id.home_cptg);       //产品推广
        QZTGImageview = view.findViewById(R.id.home_qztg);       //全站推广
        KHBBImageview = view.findViewById(R.id.home_khbb);       //客户报备
        TZZXImageview = view.findViewById(R.id.home_tzzx);       //通知中心
        XSYDImageview = view.findViewById(R.id.home_xsyd);       //新手引导
        ZHGLImageview = view.findViewById(R.id.home_zhgl);       //账号管理
        homeNickName = view.findViewById(R.id.home_nickName);    //昵称
        homeTouXiang = view.findViewById(R.id.home_touxiang);    //头像

        LJSYClick = view.findViewById(R.id.my_fragment_leiji_sy);
        LJKHClick = view.findViewById(R.id.my_fragment_leiji_kehu);
        LJDDClick = view.findViewById(R.id.my_fragment_leiji_dingdan);
        versionCheck = view.findViewById(R.id.version_update);


    }

    @Override
    public void onStart() {
        super.onStart();
        Integer uid = SpUtils.getLoginUserId(getContext());
        //获取当前用户的邀请码
        String code = SpUtils.getUserCode(getContext());
        Log.d(TAG, "homeActivityCode " + code);
        LJMButton.setText(code);

        //初始化主页面数据
        serachPersonInfo(URLConfig.SELECT_PERSON + "?memberId=" + uid);

    }

    @Override
    public int getContextViewId() {
        return R.layout.my_fragment;
    }

    @Override
    public void initData(View view) {

        //获取当前用户的id
        Integer uid = SpUtils.getLoginUserId(getContext());
        if (uid != null) {
            String url = "?memberId=" + uid;
            //向服务器请求数据
            getRequest(url);
        }
    }

    @Override
    public void initListener() {
        GRZLImageview.setOnClickListener(this);
        WYTXButton.setOnClickListener(this);
        QZTGImageview.setOnClickListener(this);
        TGCPImageview.setOnClickListener(this);
        LJKHTextview.setOnClickListener(this);  //累计用户
        LJSYTextview.setOnClickListener(this);  //累计收益
        CPLJMButton.setOnClickListener(this);   //复制邀请码
        ZHGLImageview.setOnClickListener(this);

        LJSYClick.setOnClickListener(this);
        LJDDClick.setOnClickListener(this);
        LJKHClick.setOnClickListener(this);

        versionCheck.setOnClickListener(this);
        XSYDImageview.setOnClickListener(this);
        TZZXImageview.setOnClickListener(this);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_cp_ljm:       //复制邀请码
                setClipboard(SpUtils.getUserCode(getContext()));
                Common.showToast(getContext(), "邀请码复制成功");
                break;
            case R.id.home_grzl:    //修改个人资料
                Intent intent = new Intent(getContext(), PersonInfoActivity.class);
                startActivity(intent);
                break;

            case R.id.my_fragment_leiji_sy:    //累计收益
                Intent earning = new Intent(getContext(), CumulativeEarningActivity.class);
                startActivity(earning);
                break;

            case R.id.my_fragment_leiji_kehu:    //累计客户
                Intent customer = new Intent(getContext(), CumulativeCustomerActivity.class);
                startActivity(customer);
                break;
            case R.id.my_fragment_leiji_dingdan:     //累计订单
                Intent cOrder = new Intent(getContext(), CumulativeOrderActivity.class);
                startActivity(cOrder);
                break;

            case R.id.home_tx_but:  //提现
                Intent cashActivity = new Intent(getContext(), CashWithdrawalActivity.class);
                String tx = DTXJETextview.getText().toString();
                cashActivity.putExtra("canEran", tx);
                startActivity(cashActivity);
                break;
            case R.id.home_cptg:        //产品推广
                Intent homeCPTG = new Intent(getContext(), TGCommodityActivity.class);
                startActivity(homeCPTG);
                break;
            case R.id.home_qztg:        //全站推广
                Intent qgShare = new Intent(getContext(), TGShareActivity.class);
                String url = "http://m.emaimed.com/#/?sc=" + SpUtils.getUserCode(getContext());
                qgShare.putExtra("type", "11");
                qgShare.putExtra("url", url);
                startActivity(qgShare);
                break;
            case R.id.home_tzzx:
                Intent notice = new Intent(getContext(), NoticeCentActivity.class);
                startActivity(notice);
                break;

            case R.id.qztg_qx:
                Toast.makeText(getContext(), "取消", Toast.LENGTH_SHORT).show();
                break;
            case R.id.home_zhgl:        //账号管理
                Intent account = new Intent(getContext(), AccountMangerActivity.class);
                startActivity(account);
                break;

            case R.id.version_update:
                String versionInfo = SpUtils.getVersionInfo(getContext());
                if (!versionInfo.equals(null) && versionInfo.length() > 0) {
                    Common.showToast(getContext(), "当前为最新版v" + versionInfo);
                } else {
                    System.out.println("versionInfo 为空");
                }
                break;

            case R.id.home_xsyd:
                Intent noviceGuide = new Intent(getContext(), NoviceGuideActivity.class);
                startActivity(noviceGuide);
                break;

            default:
                break;
        }

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //系统更新

            //页面数据更新（用户头像和名称）
            if (msg.what == 0x78) {
                User user = (User) msg.obj;
                System.out.println("user用户信息" + user.toString());
                if (user.getNickName().equals("null") || user.getNickName().equals("") || user.getNickName() == null) {
                    //设置默认的用户昵称
                    homeNickName.setText("医麦合伙人");
                } else {
                    //显示用户已经设置好的用户昵称
                    homeNickName.setText(user.getNickName());
                }
                if (user.getHeadImg().equals("null") || user.getHeadImg().equals("") || user.getHeadImg() == null) {
                    //设置默认的用户头像
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.touxiang);
                    homeTouXiang.setImageBitmap(bitmap);
                } else {
                    //设置用户已经设置好的用户头像
                    Picasso.with(getContext()).load(URLConfig.TPURL + user.getHeadImg()).transform(new CircleTransform()).into(homeTouXiang);
                }
                //保存当前用户信息
                SpUtils.putLoginInfo(getContext(), user);
            }
        }
    };

    //向服务器请求数据
    public void getRequest(final String url) {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                super.run();
                final HomeEntity homeEntity = initPageData(url);
                //更新主线程UI控件
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LJSYTextview.setText(homeEntity.getCumulativeMoney().toString());
                        LJKHTextview.setText(homeEntity.getCumulativeUser().toString());
                        LJDDTextview.setText(homeEntity.getCumulativeOrder().toString());
                        DTXJETextview.setText(homeEntity.getCanCarryMoney().toString());
                        //累计金额存入Sp
                        SpUtils.putCumulativeMoney(getActivity(), homeEntity);
                    }
                });
            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public HomeEntity initPageData(String url) {
        HomeEntity homeEntity = new HomeEntity();
        String res = NetWorkUtil.requestGet(URLConfig.HOME_URL + url);
        try {
            JSONObject jsonObject = new JSONObject(res);
            String flag = jsonObject.optString("success");
            String data = jsonObject.optString("data");
            if (!(flag.equals("null")) && !(flag.equals("")) && flag.equals("true")) {
                JSONObject object = new JSONObject(data);
                Log.d(TAG, "······页面数据+====" + object);
                Integer cumulativeOrder = object.optInt("saleOrdersCount");     //累计订单
                Integer cumulativeUser = object.optInt("saleMembersConut");     //累计用户
                String sy = object.optString("sumState4");                      //累计收益
                String money2 = object.optString("sumState2");                  //可提现金额
                String money3 = object.optString("sumState3");                  //提现中
                String money1 = object.optString("sumState1");                  //预计收益
                String toDayCumuMoney = object.optString("dayTimeMoney");       //今日累计收益
                String yestDayCumuMoney = object.optString("yesTimeMoney");     //昨日累计收益
                String weekCumuMoney = object.optString("weekTimeMoney");       //近一周累计收益

                //累计收益
                if (sy.equals(null) || sy.equals("null") || sy == null) {
                    homeEntity.setCumulativeMoney(0.00F);
                } else {
                    homeEntity.setCumulativeMoney(Float.parseFloat(sy));
                }

                //可提现金额
                if (money2.equals(null) || money2.equals("null") || money2 == null) {
                    homeEntity.setCanCarryMoney(0.00F);
                } else {
                    homeEntity.setCanCarryMoney(Float.parseFloat(money2));
                }

                //预计收益
                if (money1.equals("null") || money1.equals(null) || money1 == null) {
                    homeEntity.setExceptMoney(0.00F);
                } else {
                    homeEntity.setExceptMoney(Float.parseFloat(money1));
                }
                //提现中
                if (money3.equals("null") || money3.equals(null) || money3 == null) {
                    homeEntity.setCarryingMoney(0.00F);
                } else {
                    homeEntity.setCarryingMoney(Float.parseFloat(money3));
                }

                //累计订单
                if (cumulativeOrder != null) {
                    homeEntity.setCumulativeOrder(cumulativeOrder);
                }
                if (cumulativeUser != null) {
                    homeEntity.setCumulativeUser(cumulativeUser);
                }
                //接口中默认返回0，所以不用判空
                homeEntity.setToDayCumuMoney(Float.parseFloat(toDayCumuMoney));
                homeEntity.setYestDayCumuMoney(Float.parseFloat(yestDayCumuMoney));
                homeEntity.setWeekCumuMoney(Float.parseFloat(weekCumuMoney));

            } else {
                Log.d(TAG, "HomeActivityInitPageData: " + "服务器返回数据格式错误……");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "initPageData: " + homeEntity.toString());
        return homeEntity;
    }

    //查询个人信息
    public void serachPersonInfo(final String url) {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                super.run();
                String personInfo = NetWorkUtil.requestGet(url);
                Log.d(TAG, "home个人信息" + personInfo);
                User user = new User();
                try {
                    JSONObject jsonObject = new JSONObject(personInfo);
                    String flag = jsonObject.optString("success");
                    if (flag != null && !("".equals(flag)) && flag.equals("true")) {
                        JSONObject object = new JSONObject(jsonObject.optString("data"));
                        String headimg = object.optString("headimg");   //头像地址链接
                        String nickName = object.optString("nickname"); //昵称
                        String weChat = object.optString("weixin");     //微信号
                        String phoneNum = object.optString("mobile");   //手机号
                        if (!(headimg.equals("")) && !(headimg.equals("null")) && headimg != null) {
                            user.setHeadImg(headimg);
                        } else {
                            user.setHeadImg("null");
                        }
                        if (!(nickName.equals("")) && !(nickName.equals("null")) && nickName != null) {
                            user.setNickName(nickName);
                        } else {
                            user.setNickName("医麦合伙人");
                        }
                        if (weChat.length() > 0) {
                            user.setWeChat(weChat);
                        }
                        if (phoneNum.length() > 0) {
                            user.setPhoneNum(phoneNum);
                        }

                        Message message = Message.obtain();
                        message.what = 0x78;
                        message.obj = user;
                        handler.sendMessage(message);
                    } else {
                        Common.showToast(getContext(), "页面初始化数据失败，请确认当前网络是否良好");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //将内容复制到剪贴板
    public void setClipboard(String args) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", args);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
}
