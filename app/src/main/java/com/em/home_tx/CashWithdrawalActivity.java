package com.em.home_tx;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.em.R;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.home.HomeActivity;
import com.em.home_tx_add.AddBankActivity;
import com.em.pojo.BankEntity;
import com.em.pojo.EarnEntity;
import com.em.tx_record.EarnRecordActivity;
import com.em.utils.NetWorkUtil;
import com.em.utils.SpUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/13 0013 12:21
 * discrption  提现页面
 */
public class CashWithdrawalActivity extends BaseActivity<CashWithdrawalPersent> implements ICashWithdrawal.V {

    private static final String TAG = "CashWithdrawalActivity";
    private View inflate;
    private Dialog dialog;
    private Button txSuccess;

    private ImageView TJYHK;  //添加银行卡
    private EditText cashMoney;
    private TextView editCash;
    private TextView cashAll;
    private Button cashSure;
    private TextView cashJL; //提现记录
    private TextView bankStatus; //银行卡状态
    private String canEran;

    @Override
    public void initView() {
        TJYHK = findViewById(R.id.cash_tj_yhk);
        cashMoney = findViewById(R.id.cash_money);
        cashAll = findViewById(R.id.cash_all);
        editCash = findViewById(R.id.edit_cash);
        cashSure = findViewById(R.id.cash_sure);
        cashJL = findViewById(R.id.tx_jl);
        bankStatus = findViewById(R.id.bank_status);

    }

    @Override
    public int getContextViewId() {
        return R.layout.cash_activity;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        canEran = intent.getStringExtra("canEran");
        editCash.setText("可体现金额"+canEran+"元");
        Log.d(TAG, "canEran: "+canEran);

        String url = "?memberId="+ SpUtils.getLoginUserId(CashWithdrawalActivity.this);
        requestSelectBankInfo(URLConfig.SELECT_BANK_STATUS+url);
    }

    @Override
    public void initListener() {
        TJYHK.setOnClickListener(this);
        cashAll.setOnClickListener(this);
        cashSure.setOnClickListener(this);
        cashJL.setOnClickListener(this);
    }
    @Override
    public void destroy() {
        finish();
    }
    @Override
    public CashWithdrawalPersent getmPersenterInstance() {
        return new CashWithdrawalPersent();

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cash_tj_yhk:      //设置银行卡
                Intent backCrad = new Intent(CashWithdrawalActivity.this, AddBankActivity.class);
                startActivity(backCrad);
                break;
            case R.id.cash_all:         //全部提现
                Toast.makeText(CashWithdrawalActivity.this,"全部提现",Toast.LENGTH_SHORT).show();
                cashMoney.setText(canEran);
                break;
            case R.id.cash_sure:        //提交提现按钮
                Integer uid = SpUtils.getLoginUserId(CashWithdrawalActivity.this);
                requestEranInfo(URLConfig.TX_URL,uid);
                //showTXSuccessDialog();
                break;
            case R.id.tx_jl:            //查看提现记录
                Intent record = new Intent(CashWithdrawalActivity.this, EarnRecordActivity.class);
                startActivity(record);
                break;
            case R.id.tx_success_but:   //提现成功，关闭dialog
                Toast.makeText(CashWithdrawalActivity.this,"提现成功",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 0x11){
                try {
                    String res = (String) msg.obj;
                    JSONObject jsonObject = new JSONObject(res);
                    String mess = jsonObject.optString("message");
                    String success = jsonObject.optString("success");
                    if(success.equals("null") || success.equals("") || success.equals(null) || success == null){
                        Log.d(TAG, "handleMessage:"+"接口请求错误");
                    }else {
                        //提现成功
                        if(success.equals("true")){
                            Common.showToast(CashWithdrawalActivity.this,"提现成功");
                            Intent intent = new Intent(CashWithdrawalActivity.this, HomeActivity.class);
                            startActivity(intent);
                            destroy();

                        }else {

                            Common.showToast(CashWithdrawalActivity.this,mess);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    };


    //向服务器提交提现信息
    public void requestEranInfo(final String url, final Integer uid){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String res = NetWorkUtil.requestTXMoney(url,uid);
                Message message = Message.obtain();
                message.what = 0x11;
                message.obj = res;
                handler.sendMessage(message);
            }
        }.start();
    }

    //向服务器发送添加银行卡信息
    public void requestSelectBankInfo(final String url){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    final BankEntity bankEntity = getInitBankInfo(url);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(bankEntity.getBankStatus().equals("1")){
                                bankStatus.setText("请添加银行卡");

                            }else if(bankEntity.getBankStatus().equals("2")){
                                //提交申请
                                bankStatus.setText("您以添加尾号为"+6666+"的银行卡");
                            }else if(bankEntity.getBankStatus().equals("3")){
                                //审核通过
                                bankStatus.setText(bankEntity.getBankName()+"6666");
                            }else {
                                //审核失败
                                bankStatus.setText("审核失败，请重新添加");
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    //向服务器请求数据并解析
    public BankEntity getInitBankInfo(String url) throws JSONException {
        BankEntity bankEntity = new BankEntity();
        String resBankInfo = NetWorkUtil.requestGet(url);

        JSONObject jsonObject = new JSONObject(resBankInfo);
        String data = jsonObject.optString("data");
        JSONObject jsonObject1 = new JSONObject(data);
        String saleMember = jsonObject1.optString("saleMember");
        JSONObject object = new JSONObject(saleMember);

        String bankName = object.optString("bankType");
        String bankAdd = object.optString("bankAdd");
        String bankCode = object.optString("bankCode");
        String  name = object.optString("bankName");        //持卡人姓名
        String cid = object.optString("certificateCode");
        String bankStatus = object.optString("state");

        if(bankName != null && !(bankName.equals("null"))){
            bankEntity.setBankName(bankName);
        }
        if(bankAdd != null && !(bankAdd.equals("null"))){
            bankEntity.setBankAdd(bankAdd);
        }
        if(bankCode != null && !(bankCode.equals("null"))){
            bankEntity.setBankCode(bankCode);
        }
        if(name != null && !(name.equals("null"))){
            bankEntity.setName(name);
        }
        if(cid != null && !(cid.equals("null"))){
            bankEntity.setCid(cid);
        }
        if(bankStatus != null && !(bankStatus.equals("null"))){
            bankEntity.setBankStatus(bankStatus);
        }

       return bankEntity;
    }

    //提现成功dialog页面
    public void showTXSuccessDialog(){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle2);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.tx_dialog,null);
        //初始化控件
        txSuccess = (Button) inflate.findViewById(R.id.tx_success_but);
        txSuccess.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        //设置弹框的宽度
        lp.width=(int)(metrics.widthPixels*0.8);
        lp.y = 400;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }
}
