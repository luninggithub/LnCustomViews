package com.ln.custom.library.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.ln.custom.library.widget.transform.CustomShapeTransformation;

/**
 * 采用glide库动态加载指定url的图片，并裁剪成目标形状
 * @see com.ln.custom.library.widget.transform.CustomShapeTransformation
 *
 * @author luning03
 */
public class ImageLoader {

    public static void loadTransformImage(Context context, ImageView view, String url) {
        RequestOptions options = new RequestOptions()
                .transform(new CustomShapeTransformation(context))
                .priority(Priority.IMMEDIATE);
        Glide.with(context).load(url).apply(options).into(view);
    }

}
