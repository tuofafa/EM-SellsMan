package com.em.re_pwd;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.em.R;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.login.LoginActivity;
import com.em.pojo.ResponseData;
import com.em.pojo.User;
import com.em.utils.NetWorkUtil;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends BaseActivity<ResetPasswordPersenter> implements IResetPassword.V {

    private static final String TAG = "ResetPasswordActivity";
    private EditText reAccountName;
    private EditText rePhoneNum;
    private Button reSetButton;

    @Override
    public void initView() {
        reAccountName = findViewById(R.id.re_username);
        rePhoneNum = findViewById(R.id.re_phone_num);
        reSetButton = findViewById(R.id.reset_but);
    }
    @Override
    public int getContextViewId() {
        return R.layout.resetpassword_activity;
    }
    @Override
    public void initData() {
    }
    @Override
    public void initListener() {
        reSetButton.setOnClickListener(this);
    }
    @Override
    public void destroy() {
        finish();
    }

    @Override
    public ResetPasswordPersenter getmPersenterInstance() {
        return new ResetPasswordPersenter();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reset_but:  //重置密码按钮
                //Toast.makeText(ResetPasswordActivity.this,"重置密码",Toast.LENGTH_SHORT).show();
                Map<String,String> map = new HashMap<>();
                map.put("name",reAccountName.getText().toString());
                map.put("mobile",rePhoneNum.getText().toString());
                requestResetPwd(map);
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x111:
                    ResponseData responseData = null;
                    if(msg.obj == null){
                        Common.showToast(ResetPasswordActivity.this,"服务器返回数据为空");
                    }else {
                        responseData = (ResponseData) msg.obj;
                    }
                    if(responseData.getSuccess().equals("true")){
                        Common.showToast(ResetPasswordActivity.this,"密码重置成功，稍后将发送到您的手机上，请您注意查收……");
                        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        destroy();
                    }
                    if(responseData.getSuccess().equals("false")){
                        Common.showToast(ResetPasswordActivity.this,responseData.getMessage());
                    }
                    break;
            }
        }
    };

    //向服务器请求数据
    public void requestResetPwd(final Map<String,String> map){
        new Thread(){
            @Override
            public void run() {
                super.run();
                ResponseData responseData = new ResponseData();
                String res = NetWorkUtil.requestResetPwd(URLConfig.RESET_PWD,map);
                Message msg = Message.obtain();
                if(res.equals("") || res.equals("null")){
                    Common.showToast(ResetPasswordActivity.this,"服务器返回数据为空");
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        String success = jsonObject.getString("success");
                        String message = jsonObject.getString("message");
                        String total = jsonObject.getString("total");

                        responseData.setSuccess(success);
                        responseData.setMessage(message);
                        responseData.setTotal(total);
                        msg.what = 0x111;
                        msg.obj = responseData;

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                handler.sendMessage(msg);
            }
        }.start();
    }
    @Override
    public void requestReset(User user) {

    }
    @Override
    public void responseReset(User user) {

    }
}
