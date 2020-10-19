package com.em.home_tx_add;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.em.R;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.home_tx.CashWithdrawalActivity;
import com.em.pojo.BankEntity;
import com.em.utils.NetWorkUtil;
import com.em.utils.SpUtils;
import com.em.utils.StringUtils;

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
    private EditText bankName;      //持卡人
    private EditText cardID;        //身份证
    private EditText bankID;        //银行卡号
    private Spinner bankType;       //银行类型
    private EditText bankAdd;       //开户行地区
    private Button submit;     //提交按钮
    private TextView bankTitle;  //标题

    @Override
    public void initView() {
        bankName = findViewById(R.id.card_name);
        cardID = findViewById(R.id.card);
        bankID = findViewById(R.id.back_card);
        bankType = findViewById(R.id.back_type);
        bankAdd = findViewById(R.id.card_add);
        submit = findViewById(R.id.add_card_but);
        bankTitle = findViewById(R.id.bank_title);

        //下拉列表事件
        bankType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] backs = getResources().getStringArray(R.array.backs);
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
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        if(!(title.equals("")) && title != null){
            if(title.equals("添加银行卡")){
                bankTitle.setText("添加银行卡");
            }
            if(title.equals("修改银行卡")){
                BankEntity bankEntity = (BankEntity) intent.getSerializableExtra("bankentity");
                Log.d(TAG, "银行卡信息"+bankEntity.toString());

                bankTitle.setText("修改银行卡");
                bankName.setText(bankEntity.getName());
                cardID.setText(bankEntity.getCid());
                bankID.setText(bankEntity.getBankCode());
                bankAdd.setText(bankEntity.getBankAdd());
            }
        }
    }

    @Override
    public void initListener() {
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Map<String,String> map = new HashMap<>();
        if(v.getId() == R.id.add_card_but){
            String bankNames = bankName.getText().toString();    //持卡人
            String cardId = cardID.getText().toString();        //身份证
            String bankIDs = bankID.getText().toString();        //银行卡号
            String bankType = SpUtils.getBankName(AddBankActivity.this);
            Integer uid = SpUtils.getLoginUserId(AddBankActivity.this);
            String bankAdds = bankAdd.getText().toString();
            //验证提交银行卡信息
            if(bankName.length()>0){
                if(cardId.length()>0){
                    Log.d(TAG, "身份证==="+StringUtils.IDCardValidate(cardId));
                    if(StringUtils.IDCardValidate(cardId)){
                        if(bankIDs.length()>0){
                            if(StringUtils.isNum(bankIDs) && bankIDs.length()>15 && bankIDs.length()<20){
                                if(bankType != null && !(bankType.equals("")) && !(bankType.equals("null"))){
                                    if(bankAdds.length()>0){
                                        if(uid != null){
                                            //提交银行卡信息
                                            map.put("uid",uid+"");
                                            map.put("bankName",bankNames);
                                            map.put("cid",cardId);
                                            map.put("bankCode",bankIDs);
                                            map.put("bankType",bankType);
                                            map.put("bankAdd",bankAdds);

                                            requestAddBankCard(URLConfig.ADD_BACK_CRAD,map);
                                        }else {
                                            Log.d(TAG, "提交银行卡信息没有获取到用户id");
                                        }
                                    }else{
                                        Common.showToast(AddBankActivity.this,"开户行地区不能为空……");
                                    }
                                }else {
                                    Common.showToast(AddBankActivity.this,"请选择银行类型……");
                                }
                            }else {
                                Common.showToast(AddBankActivity.this,"请输入正确的银行卡号……");
                            }
                        }else {
                            Common.showToast(AddBankActivity.this,"银行卡号不能为空……");
                        }
                    }else {
                        Common.showToast(AddBankActivity.this,"请输入合法的身份证号码……");
                    }
                }else {
                    Common.showToast(AddBankActivity.this,"持卡人身份证信息不能为空……");
                }
            }else {
                Common.showToast(AddBankActivity.this,"持卡人姓名不能为空……");
            }
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
                           Common.showToast(AddBankActivity.this,"添加银行卡失败，请联系工作人员……");
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
