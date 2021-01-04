package com.em.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.em.R;
import com.em.base.BaseFragment;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.dialog.DataLoadDialog;
import com.em.main.MainAPP;
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
 * @date 2020/12/22 0022 17:40
 */
public class VerificationCodeFragment extends BaseFragment implements View.OnClickListener {

    private  final int SMS_SEND = 0x11;
    private  final int VERIFICATION_CODE_LOGIN = 0x23;
    private static final String TAG = "VerificationCodeFragmen";
    private DataLoadDialog dataLoadDialog;
    private EditText account,password;
    private LinearLayout sendYZM;
    private TextView sendYZMText,forwordPassword,register;
    private Button loginBtn;
    private Integer uidSMS;

    @Override
    public void initView(View view) {

        account = view.findViewById(R.id.yzm_login_account);
        password = view.findViewById(R.id.yzm_login_yanzhengma);

        sendYZM = view.findViewById(R.id.yzm_login_send_bnt);
        sendYZMText = view.findViewById(R.id.yzm_login_send_text);
        forwordPassword = view.findViewById(R.id.yzm_forword_password);
        register = view.findViewById(R.id.yzm_register);
        loginBtn = view.findViewById(R.id.yzm_login_btn);

    }

    @Override
    public int getContextViewId() {
        return R.layout.login_activity_frg_phone;
    }

    @Override
    public void initData(View view) {
        String reg = "<font color= \"#FFC2C2C2\">没有账户？</font><font color= \"#00cc66\">立即注册</font>";
        register.setText(Html.fromHtml(reg));
    }

    @Override
    public void initListener() {

        sendYZM.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        register.setOnClickListener(this);
        forwordPassword.setOnClickListener(this);

    }

    @Override
    public void destroy() {
        timer.cancel();     //这里必须取消定时任务，否则会报空指针异常
        System.out.println("取消定时任务");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.yzm_login_send_bnt:
                //校验用户是否输入，输入的是否是手机号码？
                String phone1 = account.getText().toString();
                if (phone1.length() > 0) {

                    boolean flag = StringUtils.isPhone(phone1);
                    Log.d(TAG, "验证手机号" + flag);
                    if (StringUtils.isPhone(phone1)) {
                        uidSMS = getRandom();
                        String param = "?mob=" + phone1 + "&uid=" + uidSMS;
                        getRequestSendMSG(param);
                        timer.start();

                    } else {
                        Common.showToast(getContext(), "请输入合法的手机号");
                    }
                } else {
                    Common.showToast(getContext(), "手机号码不能为空，请输入手机号码");
                }
                break;

            case R.id.yzm_forword_password:
                Intent intent = new Intent(getContext(), ResetPasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.yzm_register:

                Intent register = new Intent(getContext(), RegisterActivity.class);
                startActivity(register);
                break;

            case R.id.yzm_login_btn:

                String phone = account.getText().toString();
                String verifivationCode = password.getText().toString();
                if(phone.length()>0 && verifivationCode.length()>0){
                    if(StringUtils.isPhone(phone)){
                        if(StringUtils.isNum(verifivationCode) && verifivationCode.length() == 6){

                            //具体的登录事件接口
                            User user = new User();

                            user.setPhoneNum(phone);
                            user.setAccountName(phone);
                            user.setIpAddress("127.0.0.1");

                            user.setVerificationCode(verifivationCode);
                            if (uidSMS!= null){
                                user.setSmsUID(uidSMS);

                                System.out.println("timer已经取消");
                                getRequestVerificationCodeLogin(URLConfig.VerificationCodeLogin,user);
                            }else {
                                Common.showToast(getContext(),"验证码错误，请重新获取");
                            }

                        }else {
                            Common.showToast(getContext(),"请输入正确的验证码");
                        }

                    }else {
                        Common.showToast(getContext(),"请输入正确的手机号");
                    }
                }else {
                    Common.showToast(getContext(),"手机号或验证码为空");
                }

                break;
        }
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SMS_SEND:
                    try {
                        System.out.println("短信发送");
                        String sendMsg = String.valueOf(msg.obj);
                        ResponseData response = getSendMsgHandle(sendMsg);
                        if (response.getSuccess().equals("true")) {
                            Toast.makeText(getContext(), "短信已发送成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "请检查当前网络状态是否良好", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case VERIFICATION_CODE_LOGIN:
                    try {
                        String loginInfo = String.valueOf(msg.obj);
                        Log.d(TAG, "登录信息接口返回数据====" + loginInfo);
                        ResponseData loginData = getJSON(loginInfo);
                        //判断当前用户是否是合法用户
                        if (loginData.getSuccess().equals("true")) {
                            //获取用户id
                            Integer uid = SpUtils.getLoginUserId(getContext());
                            //查询用户的邀请码
                            getUserDistributionCode(URLConfig.GRYQ_CODE + "?memberId=" + uid);

                            loginRequest.start();

                            Intent intent = new Intent(getContext(), MainAPP.class);
                            startActivity(intent);
                        } else {
                            //登录失败
                            Common.showToast(getContext(), loginData.getMessage());
                            //清空用户名和密码，当登录失败的时候
                            account.setText("");
                            password.setText("");
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "登录请求接口返回数据为空……");
                        e.printStackTrace();
                    }
                    break;
                    default:
                        System.out.println("没有合适的消息代码");
                        break;
            }

        }
    };


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
                    message.what = SMS_SEND;
                    message.obj = msg;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    Log.d(TAG, "run: 短息发送失败");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getRequestVerificationCodeLogin(String url, User user){
        new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                String res = NetWorkUtil.verificationCodeLogin(url,user);
                Message message = Message.obtain();
                message.obj = res;
                message.what = VERIFICATION_CODE_LOGIN;
                handler.sendMessage(message);
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

    CountDownTimer loginRequest = new CountDownTimer(1 * 1000, 1000) {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onTick(long millisUntilFinished) {
            if (dataLoadDialog == null) {
                dataLoadDialog = DataLoadDialog.createDialog(getContext(), R.drawable.spinner);
            }
            dataLoadDialog.setMessage("Loading···");
            dataLoadDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onFinish() {
            dataLoadDialog.dismiss();
        }
    };

    //倒计时发送验证码
    CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onTick(long millisUntilFinished) {
            sendYZMText.setText("还剩" + millisUntilFinished / 1000 + "秒");
            //设置按钮不可点击
            sendYZM.setClickable(false);
            //sendYzm.setBackgroundColor(Color.parseColor("#F7F7F7"));
            sendYZM.setBackground(getContext().getDrawable(R.drawable.send_phonenum_1));
            sendYZMText.setTextColor(Color.parseColor("#BABABA"));
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onFinish() {
            sendYZMText.setText("获取验证码");
            sendYZM.setClickable(true);
            sendYZM.setBackground(getContext().getDrawable(R.drawable.send_phonenum));
            sendYZMText.setTextColor(Color.parseColor("#06C061"));
        }
    };


    //处理登录返回的json字符串
    public ResponseData getJSON(String datas) throws JSONException {

        ResponseData responseData = new ResponseData();

        JSONObject jsonObject = new JSONObject(datas);
        String success = jsonObject.optString("success");
        String data = jsonObject.optString("data");
        String message = jsonObject.optString("message");
        String total = jsonObject.optString("total");
        responseData.setSuccess(success);
        responseData.setMessage(message);
        if (!("".equals(data)) && !("null".equals(data)) && data != null) {
            responseData.setData(data);

            JSONObject object = new JSONObject(data);
            String uid = object.getString("id");
            //保存用户信息
            SpUtils.putLoginUserId(getContext(), Integer.parseInt(uid));
        }
        responseData.setTotal(total);
        return responseData;
    }

    //根据用户的id来查询用户的邀请码
    public void getUserDistributionCode(final String url) {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

                    SpUtils.putUserCode(getContext(), code);
                    Log.d(TAG, "code" + code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
