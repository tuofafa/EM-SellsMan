package com.em.login;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.em.home.HomeActivity;
import com.em.pojo.ResponseData;
import com.em.pojo.User;
import com.em.re_pwd.ResetPasswordActivity;
import com.em.register.RegisterActivity;
import com.em.utils.NetWorkUtil;
import com.em.utils.SpUtils;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/10 0010 9:24
 */
public class LoginActivity extends BaseActivity<LoginPersenter> implements ILogin.V {

    private static final String TAG = "LoginActivity";
    private static final int LOGIN = 0x100;             //登录消息代码
    private EditText accountName;
    private EditText password;
    private Button loginBtn;
    private TextView passwordWJ;
    private TextView register;

    @Override
    public void initView() {
        accountName = findViewById(R.id.log_username);
        password = findViewById(R.id.log_password);
        loginBtn = findViewById(R.id.log_button);
        passwordWJ = findViewById(R.id.log_wjpwd);
        register = findViewById(R.id.log_register);

    }

    @Override
    public int getContextViewId() {
        return R.layout.login_activity;
    }

    @Override
    public void initData() {
    }
    @Override
    public void initListener() {
        loginBtn.setOnClickListener(this);
        passwordWJ.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //登录
            case R.id.log_button:
                try {
                    User user = new User();
                    String username = accountName.getText().toString();
                    String pwd = password.getText().toString();
                    if(username.length()>0 && pwd.length()>0){
                        //获取当前本模拟器的IP地址
                        user.setIpAddress("127.0.0.1");
                        user.setAccountName(username);
                        user.setPassword(pwd);
                        requestLogin(user);
                    }else {
                        Common.showToast(LoginActivity.this,"用户名或密码为空");
                    }
                } catch (IOException e) {
                    Log.d(TAG, "onClick: 登录方法异常");
                    e.printStackTrace();
                }
                break;
            case R.id.log_wjpwd:
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.log_register:
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
                break;
            default:
                Toast.makeText(LoginActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void requestLogin(User user) throws IOException {
        getRequestLoginServer(user);
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case LOGIN:
                    try {
                        String loginInfo = String.valueOf(msg.obj);
                        Log.d(TAG, "登录信息接口返回数据===="+loginInfo);
                        ResponseData loginData = getJSON(loginInfo);
                        //判断当前用户是否是合法用户
                        if(loginData.getSuccess().equals("true")){
                            //获取用户id
                            Integer uid = SpUtils.getLoginUserId(LoginActivity.this);
                            //查询用户的邀请码
                            getUserDistributionCode(URLConfig.GRYQ_CODE+"?memberId="+uid);
                            //跳转到主页
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }else {
                            //登录失败
                            Common.showToast(LoginActivity.this,loginData.getMessage());
                            //清空用户名和密码，当登录失败的时候
                            accountName.setText("");
                            password.setText("");
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "登录请求接口返回数据为空……");
                        e.printStackTrace();
                    }
                    break;
                default:
                    Log.d(TAG, "handleMessage: 没有合适的消息代码适配");
                    throw new IllegalStateException("Unexpected value: " + msg.what);
            }
        }
    };

    //向服务器请求数据
    public void getRequestLoginServer(final User user){
        new Thread(){
            @Override
            public void run() {
                super.run();
                    String res = NetWorkUtil.requestLoginPost(URLConfig.LoginURL,user);
                    //新建一个Message作为传送消息的载体
                    Message message = new Message();
                    //消息代码
                    message.what = LOGIN;
                    //将消息放到载体上
                    message.obj = res;
                    //发送消息
                    handler.sendMessage(message);
            }
        }.start();
    }

    //处理服务端返回的json字符串
    public ResponseData getJSON(String datas) throws JSONException {

        ResponseData responseData = new ResponseData();

        JSONObject jsonObject = new JSONObject(datas);
        String success = jsonObject.optString("success");
        String data = jsonObject.optString("data");
        String message = jsonObject.optString("message");
        String total = jsonObject.optString("total");
        responseData.setSuccess(success);
        responseData.setMessage(message);
        if(!("".equals(data)) && !("null".equals(data)) && data != null){
            responseData.setData(data);

            JSONObject object = new JSONObject(data);
            String uid = object.getString("id");
            //保存用户信息
            SpUtils.putLoginUserId(this,Integer.parseInt(uid));
        }
        responseData.setTotal(total);
        return responseData;
    }

    //根据用户的id来查询用户的邀请码
    public void getUserDistributionCode(final String url){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String res = NetWorkUtil.requestGet(url);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String data = jsonObject.optString("data");
                    JSONObject jsonObject1 = new JSONObject(data);
                    String saleMember = jsonObject1.optString("saleMember");
                    JSONObject object = new JSONObject(saleMember);
                    String code = object.optString("saleCode");

                    SpUtils.putUserCode(LoginActivity.this,code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    public User responseLogin() {
        // System.out.println(mPersenter.responseLogin(null));
        return null;
    }

    @Override
    public void destroy() {
    }
    @Override
    public LoginPersenter getmPersenterInstance() {
        return new LoginPersenter();
    }


}
