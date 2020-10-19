package com.em.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.em.R;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.home_customer.CumulativeCustomerActivity;
import com.em.home_earning.CumulativeEarningActivity;
import com.em.home_grzl.PersonInfoActivity;
import com.em.home_order.CumulativeOrderActivity;
import com.em.home_tgsp.TGCommodityActivity;
import com.em.home_tx.CashWithdrawalActivity;
import com.em.pojo.HomeEntity;
import com.em.pojo.User;
import com.em.utils.CircleTransform;
import com.em.utils.NetWorkUtil;
import com.em.utils.SavePicture;
import com.em.utils.SpUtils;
import com.em.utils.SystemTools;
import com.em.utils.SystemUpdate;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/8 0010 16:24
 */
public class HomeActivity extends BaseActivity<HomePersenter> implements IHome.V {

    private static final String TAG = "HomeActivity";
    private View inflate;
    private Dialog dialog;
    private ImageView LJ;
    private ImageView HB;
    private TextView QX;

    private ImageView haibaoDialog;

    private Button LJMButton;
    private Button CPLJMButton;
    private ImageView GRZLImageview;
    private TextView LJSYTextview;
    private TextView LJKHTextview;
    private TextView LJDDTextview;
    private TextView DTXJETextview;
    private Button WYTXButton;
    private ImageView TGCPImageview;
    private ImageView QZTGImageview;
    private ImageView KHBBImageview;
    private ImageView TZZXImageview;
    private ImageView XSYDImageview;
    private ImageView ZHGLImageview;
    private LinearLayout ljOrder;
    private TextView homeNickName;
    private ImageView homeTouXiang;

    @Override
    public void initView() {

        LJMButton = findViewById(R.id.home_ljm);            //邀请码
        CPLJMButton = findViewById(R.id.home_cp_ljm);       //复制邀请码
        GRZLImageview = findViewById(R.id.home_grzl);       //个人资料
        LJSYTextview = findViewById(R.id.home_ljsy);        //累计收益
        LJKHTextview = findViewById(R.id.home_ljkh);        //累计客户
        LJDDTextview = findViewById(R.id.home_ljdd);        //累计订单
        DTXJETextview = findViewById(R.id.home_dtxje);      //待提现金额
        WYTXButton = findViewById(R.id.home_tx_but);        //提现按钮
        TGCPImageview = findViewById(R.id.home_cptg);       //产品推广
        QZTGImageview = findViewById(R.id.home_qztg);       //全站推广
        KHBBImageview = findViewById(R.id.home_khbb);       //客户报备
        TZZXImageview = findViewById(R.id.home_tzzx);       //通知中心
        XSYDImageview = findViewById(R.id.home_xsyd);       //新手引导
        ZHGLImageview = findViewById(R.id.home_zhgl);       //账号管理
        ljOrder = findViewById(R.id.lj_order);
        homeNickName = findViewById(R.id.home_nickName);    //昵称
        homeTouXiang = findViewById(R.id.home_touxiang);    //头像
    }

    @Override
    public int getContextViewId() {
        return R.layout.home_activity;
    }

    @Override
    public void initData() {

        //先进性版本检测
        getSystemVersion();

        //获取当前用户的id
        Integer uid = SpUtils.getLoginUserId(this);
        if (uid != null) {
            String url = "?memberId=" + uid;
            //向服务器请求数据
            getRequest(url);
        }
        //获取当前用户的邀请码
        String code = SpUtils.getUserCode(HomeActivity.this);
        LJMButton.setText(code);

        //初始化主页面数据
        serachPersonInfo(URLConfig.SELECT_PERSON + "?memberId=" + uid);

    }

    @Override
    public void initListener() {
        GRZLImageview.setOnClickListener(this);
        WYTXButton.setOnClickListener(this);
        QZTGImageview.setOnClickListener(this);
        TGCPImageview.setOnClickListener(this);
        ljOrder.setOnClickListener(this);       //累计订单
        LJKHTextview.setOnClickListener(this);  //累计用户
        LJSYTextview.setOnClickListener(this);  //累计收益
        CPLJMButton.setOnClickListener(this);   //复制邀请码

    }

    @Override
    public void destroy() {
        finish();
    }

    @Override
    public HomePersenter getmPersenterInstance() {
        return new HomePersenter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_cp_ljm:       //复制邀请码
                setClipboard(SpUtils.getUserCode(HomeActivity.this));
                Common.showToast(HomeActivity.this, "邀请码复制成功");
                break;

            case R.id.home_grzl:    //修改个人资料
                Intent intent = new Intent(HomeActivity.this, PersonInfoActivity.class);
                startActivity(intent);
                break;

            case R.id.home_ljsy:    //累计收益
                Intent earning = new Intent(HomeActivity.this, CumulativeEarningActivity.class);
                startActivity(earning);
                break;

            case R.id.home_ljkh:    //累计客户
                Intent customer = new Intent(HomeActivity.this, CumulativeCustomerActivity.class);
                startActivity(customer);
                break;

            case R.id.lj_order:     //累计订单
                Intent cOrder = new Intent(HomeActivity.this, CumulativeOrderActivity.class);
                startActivity(cOrder);
                break;

            case R.id.home_tx_but:  //提现
                Intent cashActivity = new Intent(HomeActivity.this, CashWithdrawalActivity.class);
                String tx = DTXJETextview.getText().toString();
                cashActivity.putExtra("canEran", tx);
                startActivity(cashActivity);
                break;

            case R.id.home_cptg:        //产品推广
                Intent homeCPTG = new Intent(HomeActivity.this, TGCommodityActivity.class);
                startActivity(homeCPTG);
                break;

            case R.id.home_qztg:        //全站推广
                showTZTGDialog();
                break;
            case R.id.qztg_hb:          //全站推广海报
                hiabaoShow();
                break;
            case R.id.qztg_lj:          //全站推广链接
                setClipboard("http://h5.em616.cn");
                Toast.makeText(HomeActivity.this, "分享链接成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.qztg_qx:
                Toast.makeText(HomeActivity.this, "取消", Toast.LENGTH_SHORT).show();
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
                        if (versionCode != null && !(versionName.equals("")) && !(versionName.equals("null"))) {
                            //当本地版本低于服务器版本时，系统更新
                            if (SystemTools.getVersion(HomeActivity.this) < versionCode) {
                                Log.d(TAG, "ServerVersionCode" + versionCode);
                                Log.d(TAG, "systemCode" + SystemTools.getVersion(HomeActivity.this));
                                long downloadId = SystemUpdate.downloadAPK(HomeActivity.this, URLConfig.SYSTEM_APK, "emaimed.apk");
                                Log.d(TAG, "downloadId" + downloadId);
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
            //页面数据更新（用户头像和名称）
            if (msg.what == 0x78) {
                User user = (User) msg.obj;
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
                    Picasso.with(HomeActivity.this).load(URLConfig.TPURL + user.getHeadImg()).transform(new CircleTransform()).into(homeTouXiang);
                }
            }
        }
    };

    //版本自动检测
    public void getSystemVersion() {
        new Thread() {
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

    //向服务器请求数据
    public void getRequest(final String url) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                final HomeEntity homeEntity = initPageData(url);
                //更新主线程UI控件
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LJSYTextview.setText(homeEntity.getCumulativeMoney().toString());
                        LJKHTextview.setText(homeEntity.getCumulativeUser().toString());
                        LJDDTextview.setText(homeEntity.getCumulativeOrder().toString());
                        DTXJETextview.setText(homeEntity.getCanCarryMoney().toString());

                        //累计金额存入Sp
                        SpUtils.putCumulativeMoney(HomeActivity.this, homeEntity);
                    }
                });
            }
        }.start();
    }

    public HomeEntity initPageData(String url) {
        HomeEntity homeEntity = new HomeEntity();
        String res = NetWorkUtil.requestGet(URLConfig.HOME_URL + url);
        try {
            JSONObject jsonObject = new JSONObject(res);
            String flag = jsonObject.optString("success");
            String data = jsonObject.optString("data");
            if (!(flag.equals("null")) && !(flag.equals("")) && flag.equals("true")) {
                JSONObject object = new JSONObject(data);
                Log.d(TAG, "······页面数据+===="+object);
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
                    Log.d(TAG, "initPageData: null" + money3);
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
                        String nickName = object.optString("nickname");
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
                        Message message = Message.obtain();
                        message.what = 0x78;
                        message.obj = user;
                        handler.sendMessage(message);
                    } else {
                        Common.showToast(HomeActivity.this, "页面初始化数据失败，请确认当前网络是否良好");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //全站推广Dialog
    public void showTZTGDialog() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle2);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.qztg_dialog, null);
        //初始化控件
        LJ = (ImageView) inflate.findViewById(R.id.qztg_lj);
        HB = (ImageView) inflate.findViewById(R.id.qztg_hb);
        QX = (TextView) inflate.findViewById(R.id.qztg_qx);

        LJ.setOnClickListener(this);
        QX.setOnClickListener(this);
        HB.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        //设置弹框的宽度
        lp.width = (int) (metrics.widthPixels * 0.9);
        lp.y = 100;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    //海报dialog生成
    public void hiabaoShow() {
        //这一两行代码主要是向用户请求权限
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle2);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.haibao_dialog, null);
        //初始化控件
        haibaoDialog = inflate.findViewById(R.id.set_haibao);
        //长按事件保存
        haibaoDialog.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Toast.makeText(HomeActivity.this, "长按事件", Toast.LENGTH_SHORT).show();
                //调用保存图片的方法
                boolean flag = SavePicture.SaveJpg((ImageView) view,HomeActivity.this);
                if(flag){
                    Common.showToast(HomeActivity.this,"图片保存成功");
                    //Log.d(TAG, "onLongClick: 图片保存成功");
                }else {
                    Common.showToast(HomeActivity.this,"图片保存失败");
                    //Log.d(TAG, "onLongClick: 图片保存失败");
                }
                return false;
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        //设置弹框的宽度
        lp.width = (int) (metrics.widthPixels * 0.9);
        lp.y = 350;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
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
