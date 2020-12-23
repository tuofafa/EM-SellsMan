package com.em.modify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.em.R;
import com.em.base.BaseActivity;

import com.em.common.Common;
import com.em.config.URLConfig;

import com.em.home_grzl.PersonInfoActivity;
import com.em.pojo.ResponseData;
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
 * @date 2020/9/23 0023 14:51
 */
public class ModifyPersonActivity extends BaseActivity<ModifyPersonPersent> {

    private static final String TAG = "ModifyPersonActivity";
    private TextView modifyTitle;
    private LinearLayout submit;
    private EditText modifyInfo;
    private TextView modifyTiShi;

    @Override
    public void initView() {
        modifyTitle = findViewById(R.id.modify_name);
        modifyInfo = findViewById(R.id.modify_info);
        submit = findViewById(R.id.modify_submit);
        modifyTiShi = findViewById(R.id.modify_tishi);

    }

    @Override
    public int getContextViewId() {
        return R.layout.modify_person_activity;
    }

    @Override
    public void initData() {
       Intent intent =  getIntent();
        String title = intent.getStringExtra("title");
        String info = intent.getStringExtra("info");
        String tishi = intent.getStringExtra("tishi");
        if(title.equals("") || info.equals("") || title.equals("null") || info.equals("null")){

        }else {
            modifyTitle.setText(title);
            modifyInfo.setText(info);
            modifyTiShi.setText(tishi);
        }
    }

    @Override
    public void initListener() {
        submit.setOnClickListener(this);
    }

    @Override
    public void destroy() {
        finish();
    }

    @Override
    public ModifyPersonPersent getmPersenterInstance() {
        return new ModifyPersonPersent();
    }

    @Override
    public void onClick(View v) {

        Integer uid = SpUtils.getLoginUserId(ModifyPersonActivity.this);

        if(modifyTitle.getText().toString().equals("修改昵称")){
            /*if(modifyInfo.getText().toString().equals("") || modifyInfo.getText().toString().equals("null")){

            }else {

            }*/
            String nickName = modifyInfo.getText().toString();
            if(nickName.length()>0){
                if(nickName.length()>3 && nickName.length()<11){
                    Map<String,String> nickMap = new HashMap<>();
                    nickMap.put("uid",uid.toString());
                    nickMap.put("nickname",modifyInfo.getText().toString());
                    postModifyPersonNickname(nickMap);
                }else {
                    Common.showToast(this,"昵称的长度必须为4-10字符之间");
                }
            }else {
                Common.showToast(this,"昵称为空");
            }
        }

        if(modifyTitle.getText().toString().equals("修改手机号")){

            String phoneNum = modifyInfo.getText().toString();
            if(phoneNum.length()>0){
                if(StringUtils.isPhone(phoneNum)){
                    Map<String,String> phoneMap = new HashMap<>();
                    phoneMap.put("uid",uid.toString());
                    phoneMap.put("phone",phoneNum);
                    postModifyPersonPhone(phoneMap);
                }else {
                    Common.showToast(this,"请输入合法的手机号");
                }
            }else {
                Common.showToast(this,"手机号为空");
            }
        }

        if(modifyTitle.getText().toString().equals("修改微信号")){

            if(modifyInfo.getText().toString().equals("") || modifyInfo.getText().toString().equals("null") || modifyInfo.getText().toString().length()<6 || modifyInfo.getText().toString().length()>20){
                Common.showToast(this,"请输入正确的微信号");
                //这里应该做微信号鉴别
            }else {
                Map<String,String> weChatMap = new HashMap<>();
                weChatMap.put("uid",uid.toString());
                weChatMap.put("weChat",modifyInfo.getText().toString());
                postModifyPersonWeChat(weChatMap);
            }
        }
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x111:
                    try {
                        String nickname = (String) msg.obj;
                        ResponseData responseData = getRegisterHandle(nickname);
                       if(responseData.getSuccess().equals("null") || responseData.getSuccess().equals("")){
                           Common.showToast(ModifyPersonActivity.this,"昵称修改失败，请检查当前网络环境");
                       }else {
                           Common.showToast(ModifyPersonActivity.this,"昵称修改完成");
                           Intent intent = new Intent(ModifyPersonActivity.this, PersonInfoActivity.class);
                           startActivity(intent);
                           destroy();
                       }
                    } catch (JSONException e) {
                        Log.d(TAG, "昵称修改失败");
                        e.printStackTrace();
                    }
                    break;
                case 0x112:
                    try {
                        String phone = (String) msg.obj;
                        ResponseData responseData = getRegisterHandle(phone);
                        if(responseData.getSuccess().equals("null") || responseData.getSuccess().equals("")){
                            Common.showToast(ModifyPersonActivity.this,"手机号修改失败，请检查当前网络环境");
                        }else {
                            Common.showToast(ModifyPersonActivity.this,"手机号修改完成");
                            Intent intent = new Intent(ModifyPersonActivity.this, PersonInfoActivity.class);
                            startActivity(intent);
                            destroy();
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "手机号修改失败");
                        e.printStackTrace();
                    }
                    break;
                case 0x113:
                    try {
                        String weChat = (String) msg.obj;
                        ResponseData responseData = getRegisterHandle(weChat);
                        if(responseData.getSuccess().equals("null") || responseData.getSuccess().equals("")){
                            Common.showToast(ModifyPersonActivity.this,"微信号修改失败，请检查当前网络环境");
                        }else {
                            Common.showToast(ModifyPersonActivity.this,"微信号修改完成");
                            Intent intent = new Intent(ModifyPersonActivity.this, PersonInfoActivity.class);
                            startActivity(intent);
                            destroy();
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "微信号修改失败");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    //
    public void postModifyPersonNickname(final Map<String,String> map){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String res = NetWorkUtil.requestModifyPersonNickname(URLConfig.UPDATE_PERSON,map);
                Message message = Message.obtain();
                message.what = 0x111;
                message.obj = res;
                handler.sendMessage(message);
            }
        }.start();
    }
    public void postModifyPersonPhone(final Map<String,String> map){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String res = NetWorkUtil.requestModifyPersonPhone(URLConfig.UPDATE_PERSON,map);
                Message message = Message.obtain();
                message.what = 0x112;
                message.obj = res;
                handler.sendMessage(message);
            }
        }.start();
    }

    public void postModifyPersonWeChat(final Map<String,String> map){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String res = NetWorkUtil.requestModifyPersonWeChat(URLConfig.UPDATE_PERSON,map);
                Message message = Message.obtain();
                message.what = 0x113;
                message.obj = res;
                handler.sendMessage(message);
            }
        }.start();
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

}
