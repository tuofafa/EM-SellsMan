package com.em.modify_pwd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.em.R;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.login.LoginActivity;
import com.em.pojo.ResponseData;
import com.em.pojo.User;
import com.em.re_pwd.ResetPasswordActivity;
import com.em.register.RegisterActivity;
import com.em.utils.NetWorkUtil;
import com.em.utils.SpUtils;
import com.em.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/12/31 0031 14:36
 */
public class ModifyPasswordActivity extends BaseActivity<ModifyPasswordPresent> {

    private TextView mpPhoneNum,mpVireficationText,mpSubmit;
    private EditText mpVireficationCode,mpNewPwd,mpAgrinPwd;
    private LinearLayout mpSendVireficationCode;
    private User loginInfo;
    private static final String TAG = "ModifyPasswordActivity";
    private Integer uid;


    @Override
    public void initView() {

        mpPhoneNum = findViewById(R.id.mp_phone);
        mpVireficationText = findViewById(R.id.mp_send_virefication_text);
        mpSubmit = findViewById(R.id.mp_submit);

        mpVireficationCode = findViewById(R.id.mp_input_virefication);
        mpNewPwd = findViewById(R.id.mp_input_newpwd);
        mpAgrinPwd = findViewById(R.id.mp_input_agrin_pwd);
        mpSendVireficationCode = findViewById(R.id.mp_send_virefication);

    }

    @Override
    public int getContextViewId() {
        return R.layout.modify_password;
    }

    @Override
    public void initData() {
        loginInfo = SpUtils.getLoginInfo(ModifyPasswordActivity.this);
        if(loginInfo.getPhoneNum() != null){
            mpPhoneNum.setText("+86  "+loginInfo.getPhoneNum());
        }

    }

    @Override
    public void initListener() {

        mpSendVireficationCode.setOnClickListener(this);
        mpSubmit.setOnClickListener(this);
    }

    @Override
    public void destroy() {

    }

    @Override
    public ModifyPasswordPresent getmPersenterInstance() {
        return null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.mp_send_virefication:
                if(mpPhoneNum.getText().toString().length()>0){
                    uid = getRandom();
                    String param = "?mob=" + loginInfo.getPhoneNum() + "&uid=" + uid;
                    getRequestSendMSG(param);
                    timer.start();
                }

                break;

            case R.id.mp_submit:

                String vireficationCode = mpVireficationCode.getText().toString();
                String password = mpNewPwd.getText().toString();
                String agrinPwd = mpAgrinPwd.getText().toString();
                if(vireficationCode.length() == 6 ){

                    if(password.length()>5 && StringUtils.isUserName(password)){

                        if(agrinPwd.length() >5 && StringUtils.isUserName(password)){


                            if(password.equals(agrinPwd)){

                                //重置密码事件
                                User user = new User();

                                user.setAccountName(loginInfo.getPhoneNum());          //用户名
                                user.setPhoneNum(loginInfo.getPhoneNum());             //手机号
                                user.setVerificationCode(vireficationCode); //验证码
                                user.setPassword(password);             //新密码

                                if(uid != null){
                                    user.setSmsUID(uid);                 //验证码随机数
                                    //请求重置密码接口
                                    requestResetPwd(user);
                                }else {
                                    Common.showToast(ModifyPasswordActivity.this,"验证码错误，请重新获取");
                                }

                            }else {
                                Common.showToast(ModifyPasswordActivity.this,"两次输入的密码不一致");
                            }
                        }else {
                            Common.showToast(ModifyPasswordActivity.this,"请输入合法的密码");
                        }

                    }else {
                        Common.showToast(ModifyPasswordActivity.this,"请输入合法的密码");
                    }

                }else {
                    Common.showToast(ModifyPasswordActivity.this,"验证码不正确");
                }

                break;

        }

    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x11:
                    try {
                        System.out.println("短信发送");
                        String sendMsg = String.valueOf(msg.obj);
                        ResponseData response = getSendMsgHandle(sendMsg);
                        if (response.getSuccess().equals("true")) {

                            Toast.makeText(ModifyPasswordActivity.this, "短信已发送成功", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(ModifyPasswordActivity.this, "请检查当前网络状态是否良好", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0x12:
                    ResponseData responseData = null;
                    if(msg.obj == null){
                        Common.showToast(ModifyPasswordActivity.this,"服务器返回数据为空");
                    }else {
                        responseData = (ResponseData) msg.obj;
                    }
                    if(responseData.getSuccess().equals("true")){
                        Common.showToast(ModifyPasswordActivity.this,"密码重置成功");
                        Intent intent = new Intent(ModifyPasswordActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        destroy();
                    }
                    if(responseData.getSuccess().equals("false")){
                        Common.showToast(ModifyPasswordActivity.this,responseData.getMessage());
                    }
                    break;
            }

        }
    };

    //向服务器请求数据
    public void requestResetPwd(User user){
        new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                super.run();
                ResponseData responseData = new ResponseData();
                String res = NetWorkUtil.requestResetPwd(URLConfig.RESET_PWD,user);

                System.out.println("重置密码接口返回信息："+res);
                Message msg = Message.obtain();
                if(res.equals("") || res.equals("null")){
                    Common.showToast(ModifyPasswordActivity.this,"服务器返回数据为空");
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        String success = jsonObject.getString("success");
                        String message = jsonObject.getString("message");
                        String total = jsonObject.getString("total");

                        responseData.setSuccess(success);
                        responseData.setMessage(message);
                        responseData.setTotal(total);
                        msg.what = 0x12;
                        msg.obj = responseData;

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                handler.sendMessage(msg);
            }
        }.start();
    }


    //发送短息
    public void getRequestSendMSG(final String param) {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                super.run();
                try {

                    String msg = NetWorkUtil.requestSendMSG(URLConfig.sendMSG + param);
                    Message message = Message.obtain();
                    message.what = 0x11;
                    message.obj = msg;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    Log.d(TAG, "run: 短息发送失败");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //处理发送短信JSON字符串
    public ResponseData getSendMsgHandle(String datas) throws JSONException {
        ResponseData responseData = new ResponseData();
        if (datas.equals("") || datas.equals(null)) {
            Log.d(TAG, "getSendMsgHandle: 发送短信息返回值为空");
        } else {
            JSONObject jsonObject = new JSONObject(datas);
            String success = jsonObject.getString("success");
            responseData.setSuccess(success);
        }
        return responseData;
    }


    public int getRandom() {
        int number = 0;
        while (true) {
            number = (int) (Math.random() * 1000);
            if (number >= 100 && number < 1000) {
                break;
            }
        }
        return number;
    }


    //倒计时发送验证码
    CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onTick(long millisUntilFinished) {
            mpVireficationText.setText("还剩" + millisUntilFinished / 1000 + "秒");
            //设置按钮不可点击
            mpSendVireficationCode.setClickable(false);
            //sendYzm.setBackgroundColor(Color.parseColor("#F7F7F7"));
            mpSendVireficationCode.setBackground(getDrawable(R.drawable.send_phonenum_1));
            mpVireficationText.setTextColor(Color.parseColor("#BABABA"));
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onFinish() {
            mpVireficationText.setText("获取验证码");
            mpSendVireficationCode.setClickable(true);
            mpSendVireficationCode.setBackgroundDrawable(getDrawable(R.drawable.send_phonenum));
            mpVireficationText.setTextColor(Color.parseColor("#06C061"));
        }
    };

}
