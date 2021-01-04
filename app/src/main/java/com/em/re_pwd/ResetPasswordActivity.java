package com.em.re_pwd;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.em.utils.NetWorkUtil;
import com.em.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends BaseActivity<ResetPasswordPersenter> implements IResetPassword.V {

    private static final String TAG = "ResetPasswordActivity";
    private Context context = ResetPasswordActivity.this;

    //眼睛的状态
    private boolean yanFlag = true;

    private EditText account,verificationCode ,password;
    private LinearLayout sendYZMClick,yanjingClick;
    private TextView sendYZMText;
    private ImageView setYanJing;
    private Button resetPassword;

    private Integer uidSMS;

    @Override
    public void initView() {

        account = findViewById(R.id.repwd_account);
        verificationCode = findViewById(R.id.repwd_yanzhengma);
        password = findViewById(R.id.repwd_passwrod);


        sendYZMClick = findViewById(R.id.repwd_send_yzm_state);
        yanjingClick = findViewById(R.id.repwd_yanjing_state);

        sendYZMText = findViewById(R.id.repwd_send_yzm_text);
        setYanJing = findViewById(R.id.repwd_yanjing);
        resetPassword = findViewById(R.id.repwd_btn);

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
       sendYZMClick.setOnClickListener(this);
       yanjingClick.setOnClickListener(this);
       resetPassword.setOnClickListener(this);
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

            case R.id.repwd_send_yzm_state:
                //校验用户是否输入，输入的是否是手机号码？
                String phone = account.getText().toString();
                if (phone.length() > 0) {

                    boolean flag = StringUtils.isPhone(phone);
                    Log.d(TAG, "验证手机号" + flag);
                    if (StringUtils.isPhone(phone)) {
                        uidSMS = getRandom();
                        String param = "?mob=" + phone + "&uid=" + uidSMS;
                        System.out.println("短信发送UIDSMS"+uidSMS);
                        getRequestSendMSG(param);
                        timer.start();
                    } else {
                        Common.showToast(context, "请输入合法的手机号");
                    }

                } else {
                    Common.showToast(context, "手机号码不能为空，请输入手机号码");
                }
                break;

            case R.id.repwd_yanjing_state:  //设置密码是否隐藏的眼睛切换状态
                if (yanFlag) {

                    //设置光标在最未端
                    password.setSelection(password.getText().length());
                    password.requestFocus();

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.biyan);
                    setYanJing.setImageBitmap(bitmap);
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    password.setTransformationMethod(method);
                    yanFlag = false;

                } else {
                    password.setSelection(password.getText().length());
                    password.requestFocus();

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.kaiyan);
                    setYanJing.setImageBitmap(bitmap);
                    HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
                    password.setTransformationMethod(method);
                    yanFlag = true;
                }
                break;

            case R.id.repwd_btn:

                String phoneNum = account.getText().toString();                 //手机号
                String verification = verificationCode.getText().toString();    //验证码
                String inputPwd = password.getText().toString();                //新密码

                if(phoneNum.length()>0 && verification.length()>0 && inputPwd.length()>0){
                    if(StringUtils.isPhone(phoneNum)){
                        if(StringUtils.isNum(verification) && verification.length() == 6){
                            if(StringUtils.isUserName(inputPwd) && inputPwd.length()>5 && inputPwd.length()<21){

                                //重置密码事件
                                User user = new User();

                                user.setAccountName(phoneNum);          //用户名
                                user.setPhoneNum(phoneNum);             //手机号
                                user.setVerificationCode(verification); //验证码
                                user.setPassword(inputPwd);             //新密码

                                if(uidSMS != null){
                                    user.setSmsUID(uidSMS);                 //验证码随机数
                                    //请求重置密码接口
                                    requestResetPwd(user);
                                }else {
                                    Common.showToast(context,"验证码错误，请重新获取");
                                }


                            }else {
                                Common.showToast(context,"请输入正确的密码");
                            }
                        }else {
                            Common.showToast(context,"请输入正确的验证码");
                        }
                    }else {
                        Common.showToast(context,"请输入正确的手机号");
                    }
                }else {
                    Common.showToast(context,"请完善注册信息");
                }
                break;
        }
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
                    message.what = 0x112;
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

    //倒计时发送验证码
    CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onTick(long millisUntilFinished) {
            sendYZMText.setText("还剩" + millisUntilFinished / 1000 + "秒");
            //设置按钮不可点击
            sendYZMClick.setClickable(false);
            //sendYzm.setBackgroundColor(Color.parseColor("#F7F7F7"));
            sendYZMClick.setBackground(getDrawable(R.drawable.send_phonenum_1));
            sendYZMText.setTextColor(Color.parseColor("#BABABA"));
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onFinish() {
            sendYZMText.setText("获取验证码");
            sendYZMClick.setClickable(true);
            sendYZMClick.setBackgroundDrawable(getDrawable(R.drawable.send_phonenum));
            sendYZMText.setTextColor(Color.parseColor("#06C061"));
        }
    };

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x112:
                    try {
                        System.out.println("短信发送");
                        String sendMsg = String.valueOf(msg.obj);
                        ResponseData response = getSendMsgHandle(sendMsg);
                        if (response.getSuccess().equals("true")) {
                            Toast.makeText(context, "短信已发送成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "请检查当前网络状态是否良好", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0x111:
                    ResponseData responseData = null;
                    if(msg.obj == null){
                        Common.showToast(ResetPasswordActivity.this,"服务器返回数据为空");
                    }else {
                        responseData = (ResponseData) msg.obj;
                    }
                    if(responseData.getSuccess().equals("true")){
                        Common.showToast(ResetPasswordActivity.this,"密码重置成功");
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