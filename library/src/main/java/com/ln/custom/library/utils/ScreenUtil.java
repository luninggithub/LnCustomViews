package com.ln.custom.library.utils;

import android.content.res.Resources;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

/**
 * 屏幕工具类
 *
 * @author luninggithub
 */
public class ScreenUtil {

    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param dpValue
     */
    public static int dp2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @param pxValue
     */
    public static int px2dp(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 模拟MotionEvent.DOWN事件
     *
     * @param view 被点击的view
     * @param x x坐标
     * @param y y坐标
     */
    public static void simulateTouchEvent(View view, float x, float y) {
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, x, y, 0);
        view.dispatchTouchEvent(downEvent);
        downEvent.recycle();
    }
}
