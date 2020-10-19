package com.em.register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
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

public class RegisterActivity extends BaseActivity<RegisterPersenter> implements IRegister.P {
    private static final int REGISTER = 0x101;      //注册消息代码
    private static final int SENDMSG = 0x102;       //发送短信的验证码
    private static final String TAG = "RegisterActivity";
    private EditText username;
    private EditText phoneNum;
    private Button sendYzm;
    private EditText inputYzm;
    private EditText password;
    private EditText rePassword;
    private Button regButton;
    private TextView regXY;

    public void initView() {
        username = findViewById(R.id.reg_username);
        phoneNum = findViewById(R.id.reg_phone_num);
        sendYzm = findViewById(R.id.reg_send_phonenum);
        inputYzm = findViewById(R.id.reg_input_yzm);
        password = findViewById(R.id.reg_pwd);
        rePassword = findViewById(R.id.reg_pwd_qr);
        regButton = findViewById(R.id.register_but);
        regXY = findViewById(R.id.reg_xieyi);
    }
    @Override
    public int getContextViewId() {
        return R.layout.register_activity;
    }
    @Override
    public void initData() {

    }
    @Override
    public void initListener() {
        regButton.setOnClickListener(this);
        sendYzm.setOnClickListener(this);
        regXY.setOnClickListener(this);
    }

    @Override
    public void destroy() {

    }

    @Override
    public RegisterPersenter getmPersenterInstance() {
        return new RegisterPersenter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reg_send_phonenum:  //发送验证码
                //校验用户是否输入，输入的是否是手机号码？
                String phone1 = phoneNum.getText().toString();
                if(phone1.length()>0){
                    String param = "?mob="+phone1;
                    boolean flag = StringUtils.isPhone(phone1);
                    Log.d(TAG, "验证手机号"+flag);
                    if(StringUtils.isPhone(phone1)){
                        getRequestSendMSG(param);
                    }else {
                        Common.showToast(RegisterActivity.this,"请输入合法的手机号……");
                    }

                }else {
                    Common.showToast(RegisterActivity.this,"手机号码不能为空，请输入手机号码……");
                }

                break;
            case R.id.reg_xieyi:          //阅读协议
                Toast.makeText(RegisterActivity.this,"阅读协议",Toast.LENGTH_SHORT).show();
                break;
            case R.id.register_but:       //注册
                //校验用户输入的信息,两次密码是否一样
                User user = new User();
                String name = username.getText().toString();
                String phone = phoneNum.getText().toString();
                String yzm = inputYzm.getText().toString();
                String pwd = password.getText().toString();
                String rePwd = rePassword.getText().toString();

                //验证注册信息
                if(yzm.length()>0 && pwd.length()>0 && rePwd.length()>0){

                    //验证验证码是否合法
                    if(yzm.length() == 6 && StringUtils.isNum(yzm)){

                        //验证用户名是否合法
                        if(StringUtils.isUserName(name) && name.length()>7 && name.length()<17){

                          //验证密码是否合法
                          if(StringUtils.isUserName(pwd) && pwd.length()>7 && pwd.length()<17){
                              if(pwd.equals(rePwd)){
                                  user.setAccountName(name);
                                  user.setPhoneNum(phone);
                                  user.setPassword(pwd);
                                  user.setVerificationCode(yzm);

                                  //进行注册
                                  requestRegisterInfo(user);
                                  break;
                              }else {
                                  Common.showToast(RegisterActivity.this,"两次输入密码不一致，请重新输入密码……");
                              }
                          }else {
                              Common.showToast(RegisterActivity.this,"密码只能包含字母、数字和下划线组成，并且长度为8-16位");
                          }
                        }else {
                            Common.showToast(RegisterActivity.this,"用户名只能包含字母、数字和下划线组成，并且长度为8-16位");
                        }
                    }else {
                        Common.showToast(RegisterActivity.this,"请输入合法的验证码……");
                    }
                }else {
                    Common.showToast(RegisterActivity.this,"请完善注册信息……");
                }
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case SENDMSG:
                    try {
                        System.out.println("短信发送");
                        String sendMsg = String.valueOf(msg.obj);
                        ResponseData response = getSendMsgHandle(sendMsg);
                        if(response.getSuccess().equals("true")){
                            Toast.makeText(RegisterActivity.this,"短信已发送成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegisterActivity.this,"请检查当前网络状态是否良好",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case REGISTER:
                    try {
                        String registerInfo = (msg.obj).toString();
                        System.out.println(registerInfo);
                        ResponseData response = getRegisterHandle(registerInfo);

                        if(response.getSuccess().equals("true")){
                            Common.showToast(RegisterActivity.this,"注册成功……");
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else {
                            //具体是什么原因导致注册失败（手机号已被注册，验证码过期，密码长度不合格）
                            Common.showToast(RegisterActivity.this,response.getMessage());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("没有合适的消息代码");
            }
        }
    };

    //发送注册信息
    public void getRequestRegisterServer(final User user){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String res = NetWorkUtil.requestRegisterPost(URLConfig.RegisterURL,user);
                //新建一个Message作为传送消息的载体
                Message message = new Message();
                //消息代码
                message.what = REGISTER;
                //将消息放到载体上
                message.obj = res;
                //发送消息
                handler.sendMessage(message);
            }
        }.start();
    }
    //发送短息
    public void getRequestSendMSG(final String param){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {

                    String msg = NetWorkUtil.requestSendMSG(URLConfig.sendMSG+param);
                    Message message = Message.obtain();
                    message.what = SENDMSG;
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
        if(datas.equals("") || datas.equals(null)){
            Log.d(TAG, "getSendMsgHandle: 发送短信息返回值为空");
        }else {
            JSONObject jsonObject = new JSONObject(datas);
            String success = jsonObject.getString("success");
            responseData.setSuccess(success);
        }
        return responseData;
    }

    //处理注册返回的JSON字符串
    public ResponseData getRegisterHandle(String registerInfo) throws JSONException {
        ResponseData responseData = new ResponseData();
        JSONObject jsonObject = new JSONObject(registerInfo);
        String success =  jsonObject.getString("success");
        String message = jsonObject.getString("message");
        responseData.setSuccess(success);
        responseData.setMessage(message);
        return responseData;
    }

    @Override
    public void requestRegisterInfo(User user) {
        getRequestRegisterServer(user);
    }

    @Override
    public void responseRegisterInfo(User user) {

    }

}
