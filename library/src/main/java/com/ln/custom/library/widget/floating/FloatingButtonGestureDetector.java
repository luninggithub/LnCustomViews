package com.ln.custom.library.widget.floating;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 退出按钮滑动手势监听器,需要回调的行为有两个
 * 1，单击事件，用于响应button click
 * 2，滑动事件，用于处理button跟手滑动
 *
 * created by luninggithub
 */
public class FloatingButtonGestureDetector extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {

    private GestureDetector mGestureDetector;

    private IGestureActionListener mGestureActionListener;

    public FloatingButtonGestureDetector(Context context) {
        mGestureDetector = new GestureDetector(context, this);
    }

    public void setGestureActionListener(IGestureActionListener listener) {
        mGestureActionListener = listener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (mGestureActionListener != null) {
            return mGestureActionListener.onScroll(e1, e2);
        }
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (mGestureActionListener != null) {
            return mGestureActionListener.onClick(e);
        }
        return super.onSingleTapUp(e);
    }
}
