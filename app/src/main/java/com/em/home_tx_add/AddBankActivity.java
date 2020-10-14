package com.em.home_tx_add;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.em.R;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.home_tx.CashWithdrawalActivity;
import com.em.utils.NetWorkUtil;
import com.em.utils.SpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/29 0029 11:04
 */
public class AddBankActivity extends BaseActivity<AddBankaPresent> {

    private static final String TAG = "AddBankActivity";
    private EditText backName;      //持卡人
    private EditText cardID;        //身份证
    private EditText backID;        //银行卡号
    private Spinner backType;       //银行类型
    private EditText backAdd;       //开户行地区
    private Button submit;     //提交按钮

    @Override
    public void initView() {
        backName = findViewById(R.id.card_name);
        cardID = findViewById(R.id.card);
        backID = findViewById(R.id.back_card);
        backType = findViewById(R.id.back_type);
        backAdd = findViewById(R.id.card_add);
        submit = findViewById(R.id.add_card_but);

        //
        backType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] backs = getResources().getStringArray(R.array.backs);
                Common.showToast(AddBankActivity.this,"你选择的是："+backs[position]);
                SpUtils.putBankName(AddBankActivity.this,backs[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public int getContextViewId() {
        return R.layout.add_bank_activity;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Map<String,String> map = new HashMap<>();
        if(v.getId() == R.id.add_card_but){
            Common.showToast(AddBankActivity.this,"点击了");
            String bankName = backName.getText().toString();    //持卡人
            String cardId = cardID.getText().toString();        //身份证
            String bankID = backID.getText().toString();        //银行卡号
            String bankType = SpUtils.getBankName(AddBankActivity.this);
            Integer uid = SpUtils.getLoginUserId(AddBankActivity.this);
            String bankAdd = backAdd.getText().toString();
            if(uid != null){
                map.put("uid",uid+"");
            }
            if(!(bankName.equals("")) && !(bankName.equals("null")) && bankName!= null){
                map.put("bankName",bankName);
            }
            if(!(cardId.equals("")) && !(cardId.equals("null")) && cardId!= null){
                map.put("cid",cardId);
            }
            if(!(bankID.equals("")) && !(bankID.equals("null")) && bankID!= null){
                map.put("bankCode",bankID);
            }
            if(!(bankType.equals("")) && !(bankType.equals("null")) && bankType!= null){
                map.put("bankType",bankType);
            }
            if(!(bankAdd.equals("")) && !(bankAdd.equals("null")) && bankAdd!= null){
                map.put("bankAdd",bankAdd);
            }
            requestAddBankCard(URLConfig.ADD_BACK_CRAD,map);
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
                   if(!(jsonObject.optString("success").equals("")) && !(jsonObject.optString("success").equals("null"))){
                       String flag = jsonObject.optString("success");
                       if(flag.equals("true")){
                           Common.showToast(AddBankActivity.this,"银行卡添加成功……");
                           Intent intent = new Intent(AddBankActivity.this, CashWithdrawalActivity.class);
                           startActivity(intent);
                           destroy();
                       }else {
                           Log.d(TAG, "handleMessage: "+"银行卡添加失败");
                       }
                   }else {
                       Log.d(TAG, "handleMessage: "+"服务器端返回数据格式错误……");
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
        }
    };

    //向服务器请求数据
    public void requestAddBankCard(final String url, final Map<String,String> map){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String res = NetWorkUtil.requestAddBankCard(url,map);
                Log.d(TAG, "run: "+res);
                Message message = Message.obtain();
                message.what = 0x11;
                message.obj = res;
                handler.sendMessage(message);
            }
        }.start();
    }

    @Override
    public void destroy() {
        finish();
    }
    @Override
    public AddBankaPresent getmPersenterInstance() {
        return new AddBankaPresent();
    }
}
