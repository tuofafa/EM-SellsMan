package com.em.utils;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.youth.banner.loader.*;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * @author fafatuo
 * @version 1.0
 * @date 2020/12/7 0007 17:11
 *  重写图片加载器
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /*Glide.with(context)
                .load(path)
                .into(imageView);*/
        Picasso.with(context)
                .load((String) path)
                //.transform(new RoundTransform())
                .into(imageView);

    }
}
