package com.em.home_grzl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import com.em.R;
import com.em.base.BaseActivity;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.modify.ModifyPersonActivity;
import com.em.pojo.User;
import com.em.utils.CircleTransform;
import com.em.utils.NetWorkUtil;
import com.em.utils.SpUtils;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/12 0012 17:27
 */
public class PersonInfoActivity extends BaseActivity<PersonInfoPersent> implements IPersonInfo.V{
    private static final String TAG = "PersonInfoActivity";

    private LinearLayout editTouXiang;
    private LinearLayout editNickame;
    private LinearLayout editWeChat;
    private LinearLayout editPhone;
    private ImageView setTouXiang;
    private TextView setNickname;
    private TextView setPhone;
    private TextView setWeChat;

    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Dialog dialog;
    private TextView cancel;

    @Override
    public void initView() {
        if (ActivityCompat.checkSelfPermission(PersonInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PersonInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        editTouXiang = findViewById(R.id.edit_touxiang);
        editNickame = findViewById(R.id.edit_nickname);
        editPhone = findViewById(R.id.edit_phone);
        editWeChat = findViewById(R.id.edit_weChat);

        setTouXiang = findViewById(R.id.set_touxaing);
        setNickname = findViewById(R.id.set_nickname);
        setPhone = findViewById(R.id.set_phone);
        setWeChat = findViewById(R.id.set_wechat);
    }

    @Override
    public int getContextViewId() {
        return R.layout.personinfo_activity;
    }
    @Override
    public void initData() {
        //获取当前用户的id
        Integer uid = SpUtils.getLoginUserId(this);
        //拼接URL查询用户信息的URL地址
        String url ="?memberId="+ uid;
        //向服务器更新最新的用户数据，将用户修改后的数据实时刷新到页面显示
        updateUserInfo(url);

    }

    //向服务器请求用户更新后的数据
    public void updateUserInfo(final String url){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String res = NetWorkUtil.requestGet(URLConfig.SELECT_PERSON+url);
                try {
                    User user = new User();
                    JSONObject jsonObject = new JSONObject(res);
                    String data = jsonObject.getString("data");
                    JSONObject object = new JSONObject(data);
                    String uid = object.getString("id");
                    String headImg = object.getString("headimg");
                    String nickname = object.getString("nickname");
                    String phone = object.getString("phone");
                    String weiChat = object.getString("weixin");

                    user.setAccountId(Integer.parseInt(uid));
                    user.setPhoneNum(phone);
                    user.setHeadImg(headImg);
                    user.setWeChat(weiChat);
                    user.setNickName(nickname);
                    //将最新的用户信息保存在SharedPreference中
                    SpUtils.putLoginInfo(PersonInfoActivity.this,user);
                    //在子线程中更新主线程，做到实时更新
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            User spUser = SpUtils.getLoginInfo(PersonInfoActivity.this);
                            if(spUser.getHeadImg() == null || spUser.getHeadImg().equals("null")){
                                Log.d(TAG, "run: "+spUser.toString());
                                //设置默认头像
                                Bitmap touxiang = BitmapFactory.decodeResource(getResources(),R.mipmap.touxiang);
                                setTouXiang.setImageBitmap(touxiang);
                            }else {
                                String img = URLConfig.TPURL+spUser.getHeadImg();
                                //Picasso.with(PersonInfoActivity.this).load(img).into(setTouXiang);
                                //设置成圆型
                                Picasso.with(PersonInfoActivity.this).load(img).transform(new CircleTransform()).into(setTouXiang);
                            }

                            if(spUser.getNickName() == null || spUser.getNickName().equals("null")){
                                setNickname.setText("医麦合伙人");
                            }else {
                                setNickname.setText(spUser.getNickName());
                            }

                            if(spUser.getPhoneNum() == null || spUser.getPhoneNum().equals("null")){
                                setPhone.setText("快来完善信息哦!");
                            }else {
                                setPhone.setText(spUser.getPhoneNum());
                            }
                            if(spUser.getWeChat() == null || spUser.getWeChat().equals("null")){
                                setWeChat.setText("快来完善信息哦!");
                            }else {
                                setWeChat.setText(spUser.getWeChat());
                            }
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void initListener() {
        editTouXiang.setOnClickListener(this);
        editNickame.setOnClickListener(this);
        editPhone.setOnClickListener(this);
        editWeChat.setOnClickListener(this);
    }

    @Override
    public void destroy() {
        finish();
    }
    @Override
    public PersonInfoPersent getmPersenterInstance() {
        return new PersonInfoPersent();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit_touxiang:        //编辑头像
                show();
                break;
            case R.id.edit_nickname:        //编辑昵称
                Intent intent = new Intent(PersonInfoActivity.this, ModifyPersonActivity.class);
                intent.putExtra("title","修改昵称");
                String nickName = setNickname.getText().toString();
                if(nickName.equals("") || nickName.equals("null") || nickName == null){
                    nickName = "请添加您的昵称……";
                }
                intent.putExtra("info",nickName);
                startActivity(intent);
                destroy();
                break;
            case R.id.edit_phone:           //编辑手机号
                Intent phone = new Intent(PersonInfoActivity.this, ModifyPersonActivity.class);
                phone.putExtra("title","修改手机号");
                String phoneNum = setPhone.getText().toString();
                if(phoneNum.equals("") || phoneNum.equals("null") || phoneNum == null){
                    phoneNum = "请添加您的手机号……";
                }
                phone.putExtra("info",phoneNum);
                startActivity(phone);
                destroy();
                break;
            case R.id.edit_weChat:          //编辑微信号
                Intent weChat = new Intent(PersonInfoActivity.this, ModifyPersonActivity.class);
                weChat.putExtra("title","修改微信号");
                String wechat = setWeChat.getText().toString();
                if(wechat.equals("") || wechat.equals("null") || wechat == null){
                    wechat = "请添加您的微信号……";
                }
                weChat.putExtra("info",wechat);
                startActivity(weChat);
                destroy();
                break;
            case R.id.take_phote:           //拍照
                Common.showToast(PersonInfoActivity.this,"正在开发中");
                break;
            case R.id.xiangce:              //相册
                Toast.makeText(PersonInfoActivity.this,"相册",Toast.LENGTH_SHORT).show();
                openSysAlbum();
                dialog.cancel();
                break;
            case R.id.cancel:               //取消
                Toast.makeText(PersonInfoActivity.this,"取消",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    //编辑个人头像弹窗
    public void show(){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.touxaing_dialog, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.xiangce);
        takePhoto = (TextView) inflate.findViewById(R.id.take_phote);
        cancel = (TextView)inflate.findViewById(R.id.cancel);

        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        cancel.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        //设置弹框的宽度
        lp.width=(int)(metrics.widthPixels*0.9);
        lp.y = 100;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Integer uid = SpUtils.getLoginUserId(PersonInfoActivity.this);
            if(msg.what == 0x11){
                String imgPath = (String) msg.obj;
                uploadPic(uid,URLConfig.UPLOAD_TOUXIANG,imgPath);
            }
            if(msg.what == 0x12){
                try {
                    JSONObject jsonObject = new JSONObject((String) msg.obj);
                    String imgUrl =jsonObject.optString("data");
                    uploadImgPath(URLConfig.UPDATE_PERSON,uid,imgUrl);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(msg.what==0x13){
                try {
                    JSONObject jsonObject = new JSONObject((String) msg.obj);
                    String flag = jsonObject.optString("success");
                    if(flag.equals("true")){
                        Common.showToast(PersonInfoActivity.this,"上传头像成功");
                    }else{
                        Common.showToast(PersonInfoActivity.this,"上传头像失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    //上传头像
    public void uploadPic(final Integer uid, final String url, final String imgPath){
        new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                super.run();
                String res = requestUploadPic(uid,url,imgPath);
                Message message = Message.obtain();
                message.what = 0x12;
                message.obj = res;
                handler.sendMessage(message);
            }
        }.start();
    }

    //上头头像的图片地址
    public void uploadImgPath(final String url, final Integer uid, final String imgPath){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String res = submitImgPath(url,uid,imgPath);
                Message message = Message.obtain();
                message.what = 0x13;
                message.obj = res;
                handler.sendMessage(message);
            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String requestUploadPic(Integer uid, String url, String imgPath){
        String res = NetWorkUtil.requestUploadPic(uid,url,imgPath);
        return res;
    }

    public String submitImgPath(String url,Integer uid,String imgPath){
        String res = NetWorkUtil.requestModifyImg(url,uid,imgPath);
        return res;
    }

    public static int ALBUM_RESULT_CODE = 0x999 ;

    /**
     * 打开系统相册
     * 定义Intent跳转到特定图库的Uri下挑选，然后将挑选结果返回给Activity
     * */
    private void openSysAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, ALBUM_RESULT_CODE);
    }

    //重载onActivityResult方法，获取相应数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleImageOnKitKat(data);
    }

    //这部分的代码目前没有理解，只知道作用是根据条件的不同去获取相册中图片的url
    //这一部分是从其他博客中查询的
    @TargetApi(value = 19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content: //downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);

            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();

        }
        // 根据图片路径显示图片
        displayImage(imagePath);

        Message message = Message.obtain();
        message.what = 0x11;
        message.obj = imagePath;
        handler.sendMessage(message);
    }


    /**获取图片的路径*/
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if(cursor != null){

            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;

    }

    /**展示图片*/
    private void displayImage(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        bitmap = CircleTransform.toRoundBitmap(bitmap);
        setTouXiang.setImageBitmap(bitmap);
    }

}
