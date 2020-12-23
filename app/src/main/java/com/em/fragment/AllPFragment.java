package com.em.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.em.R;
import com.em.adapter.AllFragmentAdapter;
import com.em.common.Common;
import com.em.config.URLConfig;
import com.em.goods_details.GoodsDetailsActivity;
import com.em.home.HomeActivity;
import com.em.pojo.Commodity;
import com.em.pojo.User;
import com.em.utils.CircleTransform;
import com.em.utils.NetWorkUtil;
import com.em.utils.QRCodeUtil;
import com.em.utils.SavePicture;
import com.em.utils.SpUtils;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/9/18 0018 16:49
 * discrption 全部商品
 */
public class AllPFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AllPFragment";
    private RecyclerView recyclerView;

    private View inflate;
    private Dialog dialog,hbDialog,erCodeDialog;

    //主弹窗属性
    private TextView sptgTitle;     //商品推广dialog标题
    private ImageView sptgEWM;      //商品推广dialog二维码
    private ImageView sptgHB;       //商品推广dialog海报
    private ImageView sptgLJ;       //商品推广dialog链接
    private TextView sptgQX;        //商品推广dialog取消

    //海报弹出框
    private ImageView hbZhuTu;
    private ImageView hbZTqrCode;
    private ImageView hbTouXiang;
    private TextView hbProductInstruction;
    private TextView hbProductNikeName;
    private TextView hbProductPrice;

    private ImageView qrCode;

    private LinearLayoutManager manager;
    private AllFragmentAdapter adapter;
    private Context mcontext;

    @Nullable
    @Override
    //这个方法加载fragment的布局文件
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.allspf_fragment,container,false);
        recyclerView = view.findViewById(R.id.all_splist);


        //向服务器请求数据
        getRequestSP();
        mcontext = getActivity();
        return view;
    }

    private void checkNeedPermissions(){
        //6.0以上需要动态申请权限
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //多个权限一起申请
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
        }
    }

    //本页面事件
    @Override
    public void onClick(View view) {

    }
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            List<Commodity> commodityList = null;
            if(msg.what == 0x11){
                commodityList = (List<Commodity>) msg.obj;
            }
            //去掉999999.0 可询价
            for(int i=0;i<commodityList.size();i++){
                if(commodityList.get(i).getMarktPrice().toString().equals("999999.0")){
                   commodityList.remove(commodityList.get(i));
                   i--;
                }
            }
            manager = new LinearLayoutManager(getActivity());
            adapter = new AllFragmentAdapter(getContext(),commodityList);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
            adapter.setAllOnItemClickListener(new AllFragmentAdapter.setAllOnItemClickListener() {
                @Override
                public void onClick(int position, Commodity sp) {

                    Intent intent = new Intent(getContext(), GoodsDetailsActivity.class);
                    intent.putExtra("commodit",sp);
                    startActivity(intent);
                }
            });
        }
    };

    //向服务器请求数据发送到Handler中
    public void getRequestSP(){
        new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                super.run();
                List<Commodity> commodity = initData();
                Message message = Message.obtain();
                message.obj = commodity;
                message.what = 0x11;
                handler.sendMessage(message);
            }
        }.start();
    }

    //请求数据并解析服务器返回的数据
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<Commodity> initData(){
        List<Commodity> commodityList = new ArrayList<>();
        final String url = URLConfig.ALLSPURL;
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

    //全部商品Dialog
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
    public void showSptgHBDialog(Commodity commodity, Bitmap Qrcode){

        hbDialog = new Dialog(getActivity(),R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.haibao_sptg_dialog,null,false);
        //初始化控件
        hbZhuTu = inflate.findViewById(R.id.product_zhutu_img);
        hbZTqrCode = inflate.findViewById(R.id.product_qercode);
        hbTouXiang = inflate.findViewById(R.id.product_touxiang);
        hbProductInstruction = inflate.findViewById(R.id.product_insturctions);
        hbProductNikeName = inflate.findViewById(R.id.product_nickname);
        hbProductPrice = inflate.findViewById(R.id.product_price);

        //定义高度和宽度
        int height = 2040 ;
        int width = 1080 ;
        final View viewById = inflate.findViewById(R.id.haibao);

        SavePicture.layoutView(inflate,width,height);

        //商品id
        System.out.println(commodity.getId());

       // viewById.setDrawingCacheEnabled(true);
        //viewById.buildDrawingCache(true);
        final Bitmap bitmap = SavePicture.createBitmapFromView(inflate);
        hbZTqrCode.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                boolean flag = SavePicture.SaveJpg((ImageView) v,getContext());
                if(flag){
                    Common.showToast(getContext(),"海报保存成功……");
                }else {
                    Common.showToast(getContext(),"海报保存失败……");
                }
                return false;
            }
        });

        //设置商品主图
        Picasso.with(getContext()).load(commodity.getMasterImg()).into(hbZhuTu);
        //设置二维码
        hbZTqrCode.setImageBitmap(Qrcode);
        //设置商品描述
        hbProductInstruction.setText(commodity.getName());
        //设置商品价格
        hbProductPrice.setText("￥ "+commodity.getMarktPrice().toString());

        User user = SpUtils.getLoginInfo(getContext());
        if(user != null){
            Log.d(TAG, "user"+user.toString());
        }else {
            Log.d(TAG, "showSptgHBDialog: user为空");
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

        //二维码的长按事件
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

    //动态添加碎片
    public void replaceFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.replace(R.id,fragment);
        transaction.commit();
    }

}
