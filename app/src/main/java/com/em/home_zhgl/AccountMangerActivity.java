package com.em.home_zhgl;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.em.R;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.login.LoginActivity;
import com.em.modify_pwd.ModifyPasswordActivity;

import java.time.format.DecimalStyle;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/27 0027 8:56
 */
public class AccountMangerActivity extends BaseActivity<AccountMangerPersent> {
    private LinearLayout modifyPwd;
    private Button registerAccount,logoutAccount;
    private static final String TAG = "AccountMangerActivity";
    private Context context = AccountMangerActivity.this;

    @Override
    public void initView() {
        modifyPwd = findViewById(R.id.modify_pwd_account_manger);
        registerAccount = findViewById(R.id.register_account_manger);
        logoutAccount = findViewById(R.id.logout_account_manger);

    }

    @Override
    public int getContextViewId() {
        return R.layout.account_manger;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        modifyPwd.setOnClickListener(this);
        registerAccount.setOnClickListener(this);
        logoutAccount.setOnClickListener(this);

    }

    @Override
    public void destroy() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public AccountMangerPersent getmPersenterInstance() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.modify_pwd_account_manger:
                Intent modifyPwd = new Intent(context, ModifyPasswordActivity.class);
                startActivity(modifyPwd);
                break;

            case R.id.register_account_manger:
            case R.id.logout_account_manger:
                Intent swichAccount = new Intent(context, LoginActivity.class);
                //清空所有的任务栈TaskStack
                swichAccount.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(swichAccount);
                break;
        }
    }
}
