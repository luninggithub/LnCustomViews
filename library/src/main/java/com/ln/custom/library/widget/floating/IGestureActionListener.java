package com.ln.custom.library.widget.floating;

import android.view.MotionEvent;

/**
 * created by luninggithub
 *
 * 用户屏幕交互动作回调,ExitButtonGestureDetector识别到指定行为后，回调通知监听器
 */
public interface IGestureActionListener {

    /**
     * 单击事件
     * @param event
     * @return
     */
    boolean onClick(MotionEvent event);

    /**
     * 拖动事件
     * @param event1
     * @param event2
     * @return
     */
    boolean onScroll(MotionEvent event1, MotionEvent event2);

}
