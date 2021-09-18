package com.ln.custom.library.widget.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.ln.custom.library.R;

import java.security.MessageDigest;

/**
 * 将要加载的图片裁剪成目标图（R.drawable.transform_shape）形状
 * 适用于特殊形状的icon裁剪，比如聊天气泡，五角星等
 *
 * @author luninggithub
 */
public class CustomShapeTransformation extends BitmapTransformation {
    private static final byte[] ID_BYTES;
    private final PorterDuffXfermode mSRCINMode;
    private final PorterDuffXfermode mDSTATOPMode;
    private final Paint mPaint;
    private final Context mContext;

    public CustomShapeTransformation(Context context) {
        this.mSRCINMode = new PorterDuffXfermode(Mode.SRC_IN);
        this.mDSTATOPMode = new PorterDuffXfermode(Mode.DST_OVER);
        this.mContext = context;
        this.mPaint = new Paint();
    }

    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        this.mPaint.reset();
        Bitmap destBitmap = pool.get(outWidth, outHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(destBitmap);
        Bitmap shapeBitmap = this.getShapeBitmap(pool, outWidth, outHeight, false);
        canvas.drawBitmap(shapeBitmap, 0.0F, 0.0F, this.mPaint);
        this.mPaint.setXfermode(this.mSRCINMode);
        int left = -(toTransform.getWidth() / 2 - outWidth / 2);
        int top = -(toTransform.getHeight() / 2 - outHeight / 2);
        canvas.drawBitmap(toTransform, (float) left, (float) top, this.mPaint);
        this.mPaint.setXfermode(this.mDSTATOPMode);
        Bitmap bgBitmap = this.getShapeBitmap(pool, outWidth, outHeight, true);
        canvas.drawBitmap(bgBitmap, 0.0F, 0.0F, this.mPaint);
        return destBitmap;
    }

    private Bitmap getShapeBitmap(BitmapPool pool, int outWidth, int outHeight, boolean bgNoBorder) {
        Bitmap bitmap = pool.get(outWidth, outHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable mask = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.transform_shape, null);
        int diff = bgNoBorder ? 1 : 0;
        mask.setBounds(diff, diff, outWidth - diff, outHeight - diff);
        mask.draw(canvas);
        return bitmap;
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    static {
        ID_BYTES = "com.ln.custom.library.widget.transform.CustomShapeTransformation".getBytes(CHARSET);
    }
}
