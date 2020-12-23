package com.em.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.em.R;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/11/26 0026 10:36
 */
public class DataLoadDialog extends Dialog {

    private Context context = null;
    private int anim=0;
    private static DataLoadDialog dataLoadDialog = null;


    public DataLoadDialog(@NonNull Context context) {
        super(context);
    }
    public DataLoadDialog(Context context, int theme, int anim) {
        super(context, theme);
        this.anim=anim;
    }
    public static DataLoadDialog createDialog(Context context,int anim){
        dataLoadDialog = new DataLoadDialog(context, R.style.Custom_Progress ,anim);
        dataLoadDialog.setContentView(R.layout.progress_custom);
        dataLoadDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        return dataLoadDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus){

        if (dataLoadDialog == null){
            return;
        }

        ImageView imageView = (ImageView) dataLoadDialog.findViewById(R.id.spinnerImageView);
        if(anim!=0) {
            imageView.setBackgroundResource(anim);
        }
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    /**
     * 设置标题
     * @param strTitle
     * @return
     */
    public DataLoadDialog setTitile(String strTitle){
        return dataLoadDialog;
    }

    /**
     * 设置提示内容
     * @param strMessage
     * @return
     */
    public DataLoadDialog setMessage(String strMessage){
        TextView tvMsg = (TextView)dataLoadDialog.findViewById(R.id.message);

        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }

        return dataLoadDialog;
    }

    /**屏蔽返回键**/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }
}
