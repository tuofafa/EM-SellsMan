package com.em.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
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

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/12/22 0022 17:39
 */
public class PasswordFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "PasswordFragment";
    private DataLoadDialog dataLoadDialog;

    private EditText account, password;
    private LinearLayout yanjingClick;
    private ImageView yanjingSet;
    private Button loginBtn;
    private TextView forwordPassword, register;

    //眼睛的状态
    private boolean yanFlag = true;


    @Override
    public void initView(View view) {

        account = view.findViewById(R.id.pwd_login_account);
        password = view.findViewById(R.id.pwd_login_passwrod);

        yanjingClick = view.findViewById(R.id.pwd_yanjing_state);
        yanjingSet = view.findViewById(R.id.pwd_yanjing);

        loginBtn = view.findViewById(R.id.pwd_login_btn);

        forwordPassword = view.findViewById(R.id.pwd_forword_password);
        register = view.findViewById(R.id.pwd_register);
    }

    @Override
    public int getContextViewId() {
        return R.layout.login_activity_frg_pwd;
    }

    @Override
    public void initData(View view) {

        String reg = "<font color= \"#FFC2C2C2\">没有账户？</font><font color= \"#00cc66\">立即注册</font>";
        register.setText(Html.fromHtml(reg));

    }

    @Override
    public void initListener() {

        yanjingClick.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        forwordPassword.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void destroy() {
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.pwd_yanjing_state:

                if (yanFlag) {

                    //设置光标在最未端
                    password.setSelection(password.getText().length());
                    password.requestFocus();

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.biyan);
                    yanjingSet.setImageBitmap(bitmap);
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    password.setTransformationMethod(method);
                    yanFlag = false;

                } else {

                    password.setSelection(password.getText().length());
                    password.requestFocus();

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.kaiyan);
                    yanjingSet.setImageBitmap(bitmap);
                    HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
                    password.setTransformationMethod(method);

                    yanFlag = true;
                }
                break;

            case R.id.pwd_forword_password:
                Intent intent = new Intent(getContext(), ResetPasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.pwd_register:
                Intent register = new Intent(getContext(), RegisterActivity.class);
                startActivity(register);
                break;

            case R.id.pwd_login_btn:
                User user = new User();
                String username = account.getText().toString();
                String pwd = password.getText().toString();
                if (username.length() > 0 && pwd.length() > 0) {


                    if (StringUtils.isPhone(username)) {
                        if (StringUtils.isUserName(pwd) && pwd.length() > 5 && pwd.length() < 21) {

                            //获取当前本模拟器的IP地址
                            user.setIpAddress("127.0.0.1");
                            user.setAccountName(username);
                            user.setPassword(pwd);
                            getRequestLoginServer(user);

                        } else {
                            Common.showToast(getContext(), "密码错误，请输入正确的密码");
                        }
                    } else {
                        Common.showToast(getContext(), "请输入正确的手机号");
                    }
                } else {
                    Common.showToast(getContext(), "手机号或密码为空");
                }
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0x11:
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
                    Log.d(TAG, "handleMessage: 没有合适的消息代码适配");
                    throw new IllegalStateException("Unexpected value: " + msg.what);
            }
        }
    };

    //向服务器请求数据
    public void getRequestLoginServer(final User user) {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                super.run();
                String res = NetWorkUtil.requestLoginPost(URLConfig.PasswordLoginURL, user);
                //新建一个Message作为传送消息的载体
                Message message = new Message();
                //消息代码
                message.what = 0x11;
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
}
