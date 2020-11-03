package com.em.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.em.R;
import com.em.adapter.AllFragmentAdapter;
import com.em.common.Common;
import com.em.config.URLConfig;

import com.em.goods_details.GoodsDetailsActivity;
import com.em.pojo.Commodity;
import com.em.pojo.User;
import com.em.utils.CircleTransform;
import com.em.utils.NetWorkUtil;
import com.em.utils.QRCodeUtil;
import com.em.utils.SavePicture;
import com.em.utils.SpUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/18 0018 16:49
 * discrption 全部商品
 */
public class HPFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "HPFragment";

    private RecyclerView recyclerView;

    private View inflate;
    private Dialog dialog,hbDialog,erCodeDialog;
    private TextView sptgTitle;     //商品推广dialog标题
    private ImageView sptgEWM;      //商品推广dialog二维码
    private ImageView sptgHB;       //商品推广dialog海报
    private ImageView sptgLJ;       //商品推广dialog链接
    private TextView sptgQX;        //商品推广dialog取消


    private ImageView hbZhuTu;
    private ImageView hbZTqrCode;
    private ImageView hbTouXiang;
    private TextView hbProductInstruction;
    private TextView hbProductNikeName;
    private TextView hbProductPrice;

    private ImageView qrCode;

    private LinearLayoutManager manager;
    private String data;
    private AllFragmentAdapter adapter;

    private FloatingActionButton actionButton;  //专题分享
    private Context mcontext;
    private Integer spType ;    //商品分类

    @Nullable
    @Override
    //这个方法加载fragment的布局文件
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.type_spf_fragment,container,false);
        recyclerView = view.findViewById(R.id.all_splist);
        actionButton = view.findViewById(R.id.fab_cate);

        actionButton.setOnClickListener(this);

        Bundle bundle = getArguments();
        if(bundle != null){
            spType = bundle.getInt("cateName");
            //向服务器请求数据
            getRequestSP(spType);
        }else {
            Toast.makeText(getActivity(),"分类名称为空",Toast.LENGTH_SHORT).show();
        }
        mcontext = getActivity();
        return view;
    }

    //本页面事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_cate:
                //Common.showToast(getContext(),"点击了"+spType);
                Log.d(TAG, "悬浮按钮点击"+URLConfig.TYPE_PREDUCT_URL+spType);
                showZhuanTiDialog(URLConfig.TYPE_PREDUCT_URL+spType);
                break;
        }
    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            List<Commodity> commodityList = null;
            if(msg.what == 0x11){
                commodityList = (List<Commodity>) msg.obj;
            }
            manager = new LinearLayoutManager(getActivity());
            adapter = new AllFragmentAdapter(getContext(),commodityList);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
            final List<Commodity> finalCommodityList = commodityList;
            adapter.setAllOnItemClickListener(new AllFragmentAdapter.setAllOnItemClickListener() {
                @Override
                public void onClick(int position, Commodity sp) {
                    //showSptgDialog(sp);
                    Intent intent = new Intent(getContext(), GoodsDetailsActivity.class);
                    intent.putExtra("commodit",sp);
                    startActivity(intent);
                }
            });
        }
    };

    //向服务器请求数据发送到Handler中
    public void getRequestSP(final Integer spType){
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<Commodity> commodity = initData(spType);
                Message message = Message.obtain();
                message.obj = commodity;
                message.what = 0x11;
                handler.sendMessage(message);
            }
        }.start();

    }

    //请求数据并解析服务器返回的数据
    public List<Commodity> initData(Integer spTypeName){
        List<Commodity> commodityList = new ArrayList<>();
        final String url = URLConfig.SPURL+spTypeName+".html";

        final String res = NetWorkUtil.requestGet(url);
        try {
            JSONObject jsonObject = new JSONObject(res);
            String success = jsonObject.getString("success");
            String message = jsonObject.getString("message");
            JSONArray jsonArray = jsonObject.getJSONArray("rows");
            for(int i = 0;i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                Commodity commodity = new Commodity();
                commodity.setId(Integer.parseInt(jsonObject1.getString("id")));
                commodity.setName(jsonObject1.getString("name1"));
                commodity.setMarktPrice(Float.parseFloat(jsonObject1.getString("mallPcPrice")));
                commodity.setMasterImg(URLConfig.TPURL+jsonObject1.getString("masterImg"));
                commodity.setSaleScale(Float.parseFloat(jsonObject1.getString("saleScale1")));
                commodityList.add(commodity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commodityList;
    }

    //按主题显示不同的Dialog
    public void showSptgDialog(final Commodity commodity){

        dialog = new Dialog(getActivity(),R.style.ActionSheetDialogStyle);

        //填充对话框的布局
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.sptg_item_dialog,null);
        //初始化控件
        sptgTitle = inflate.findViewById(R.id.title_sptg);
        sptgEWM = (ImageView) inflate.findViewById(R.id.erweima_sptg);
        sptgHB =(ImageView) inflate.findViewById(R.id.haibao_sptg);
        sptgLJ =(ImageView) inflate.findViewById(R.id.lianjie_sptg);
        sptgQX =(TextView) inflate.findViewById(R.id.sptg_qx);

        //预估分享后赚多少钱
        float sales = commodity.getMarktPrice()*commodity.getSaleScale();
        DecimalFormat decimalFormat = new DecimalFormat(".00");

        String str1 = "分享后预计可赚<font color= \"#FF6C00\">￥ "+decimalFormat.format(sales)+"</font>";
        sptgTitle.setText(Html.fromHtml(str1));
        final String productURL = URLConfig.PREDUCT_URL+commodity.getId();

        //生成二维码
        sptgEWM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQRCodeDialog(productURL);
                dialog.cancel();
            }
        });

        //生成海报
        sptgHB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.showToast(getContext(),"海报生成完成……");
                Bitmap qcCode = QRCodeUtil.createQRCodeBitmap(productURL,300,300);
                showSptgHBDialog(commodity,qcCode);
                dialog.cancel();
            }
        });
        //生成链接
        sptgLJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(productURL);
                Common.showToast(getActivity(),"链接复制成功……");
                Log.d(TAG, "单个商品分享"+productURL);
                dialog.cancel();
            }
        });
        //取消
        sptgQX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
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

    //专题分享Dialog
    public void showZhuanTiDialog(final String url){
        dialog = new Dialog(getActivity(),R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.sptg_item_dialog,null);
        //初始化控件
        sptgTitle = inflate.findViewById(R.id.title_sptg);
        sptgEWM = (ImageView) inflate.findViewById(R.id.erweima_sptg);      //二维码
        sptgHB =(ImageView) inflate.findViewById(R.id.haibao_sptg);         //海报
        sptgLJ =(ImageView) inflate.findViewById(R.id.lianjie_sptg);        //链接
        sptgQX =(TextView) inflate.findViewById(R.id.sptg_qx);              //取消

        sptgTitle.setText("分享专题给好友，按比例获得分销奖励");

        sptgEWM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQRCodeDialog(url);
                dialog.cancel();
            }
        });
        sptgHB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.showToast(getContext(),"商品分类图未提供……");

            }
        });
        sptgLJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(url);
                Log.d(TAG, "onClick: "+url);
                Common.showToast(getContext(),"链接已经复制成功……");
                dialog.cancel();
            }
        });
        //取消
        sptgQX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
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


    //生成商品海报
    public void showSptgHBDialog(Commodity commodity,Bitmap Qrcode){
        hbDialog = new Dialog(getActivity(),R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.haibao_sptg_dialog,null);
        //初始化控件
        hbZhuTu = inflate.findViewById(R.id.product_zhutu_img);
        hbZTqrCode = inflate.findViewById(R.id.product_qercode);
        hbTouXiang = inflate.findViewById(R.id.product_touxiang);
        hbProductInstruction = inflate.findViewById(R.id.product_insturctions);
        hbProductNikeName = inflate.findViewById(R.id.product_nickname);
        hbProductPrice = inflate.findViewById(R.id.product_price);

        //设置商品主图
        Picasso.with(getContext()).load(commodity.getMasterImg()).into(hbZhuTu);
        //设置二维码
        hbZTqrCode.setImageBitmap(Qrcode);
        //设置商品描述
        hbProductInstruction.setText(commodity.getName());
        //设置商品价格
        hbProductPrice.setText("￥ "+commodity.getMarktPrice().toString());

        User user = SpUtils.getLoginInfo(getContext());
        //设置用户昵称
        if(!(user.getNickName().equals("")) && !(user.getNickName().equals("null")) && user.getNickName().length()>0){
            hbProductNikeName.setText(user.getNickName());
        }else {
            hbProductNikeName.setText("医麦合伙人");
        }
        //设置用户头像
        if(user.getHeadImg().length()>0 && !(user.getHeadImg().equals("")) && !(user.getHeadImg().equals("null"))){
            Picasso.with(getContext()).load(URLConfig.TPURL+user.getHeadImg()).transform(new CircleTransform()).into(hbTouXiang);
        }else {
            Bitmap touxaing = BitmapFactory.decodeResource(getResources(),R.mipmap.touxiang);
            hbTouXiang.setImageBitmap(touxaing);
        }
        //将布局设置给Dialog
        hbDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = hbDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        //设置弹框的宽度
        lp.width=(int)(metrics.widthPixels*0.8);
        lp.y = 400;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        hbDialog.show();//显示对话框
    }

    //二维码生成dialog
    public void showQRCodeDialog(String spURL){
        Bitmap bitmap = QRCodeUtil.createQRCodeBitmap(spURL,650,650);
        erCodeDialog = new Dialog(getActivity(),R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.erweima_sptg_dialog,null);
        qrCode = inflate.findViewById(R.id.erweima_sptg_dialog);
        qrCode.setImageBitmap(bitmap);
        qrCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                boolean flag = SavePicture.SaveJpg((ImageView) v, getContext());
                if(flag){
                    Common.showToast(getContext(),"图片保存成功");
                    erCodeDialog.cancel();
                    //Log.d(TAG, "onLongClick: 图片保存成功");
                }else {
                    Common.showToast(getContext(),"图片保存失败");
                    //Log.d(TAG, "onLongClick: 图片保存失败");
                }
                return false;
            }
        });
        //将布局设置给Dialog
        erCodeDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = erCodeDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        //设置弹框的宽度
        lp.width=(int)(metrics.widthPixels*0.9);
        lp.y = 600;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        erCodeDialog.show();//显示对话框
    }

    //将内容复制到剪贴板
    public void setClipboard(String url){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", url);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

}
