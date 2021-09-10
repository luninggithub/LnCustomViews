package com.ln.custom.library.widget.floating;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ViewCompat;

import com.ln.custom.library.utils.ScreenUtil;

/**
 * 可移动按钮、点击、吸边
 *
 * created by luninggithub
 */
public class FloatingAppButton extends AppCompatButton {

    // 避免手指刚碰到屏幕，组件就跟着移动，所以需要滑动超过一定距离再让组件跟随手移动
    private final static int MOVE_THRESHOLD = 30;

    // 退出按钮距离屏幕顶部/底部的最小值.避免距离顶部/底部太近，导致不好操作
    private final static int MIN_MARGIN_TOP = 10;

    private boolean mIsDragging = false;

    // 获取屏幕宽高，用于View拖动边界处理
    private int mScreenWidth = 0;
    private int mScreenHeight = 0;

    // 手势监听器。要通过touch事件处理View跟手拖动，同时需要响应onclick事件
    private FloatingButtonGestureDetector mButtonGestureDetector;

    // 用于处理点击效果
    private ObjectAnimator mAnimatorOut;

    public FloatingAppButton(@NonNull Context context) {
        super(context);
        initGestureDetector(context);
    }

    public FloatingAppButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initGestureDetector(context);
    }

    public FloatingAppButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGestureDetector(context);
    }

    private void initGestureDetector(Context context) {
        mAnimatorOut = ObjectAnimator.ofFloat(this, "alpha", 0.4f, 1f);
        mAnimatorOut.setDuration(100);
        mButtonGestureDetector = new FloatingButtonGestureDetector(context);
        mButtonGestureDetector.setGestureActionListener(new IGestureActionListener() {
            @Override
            public boolean onClick(MotionEvent event) {
                return performClick();
            }

            @Override
            public boolean onScroll(MotionEvent event1, MotionEvent event2) {
                if (!mIsDragging && getDistanceFrom2Event(event1, event2) > MOVE_THRESHOLD) {
                    mIsDragging = true;
                }
                if (mIsDragging && event2 != null) {
                    return handleTouchMove(event2);
                }
                return false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mButtonGestureDetector.onTouch(this, event);
        if (!result) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    return handleTouchDown();
                case MotionEvent.ACTION_UP:
                    return handleTouchUp(event);
                default:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mScreenWidth = ScreenUtil.getScreenWidth();
        mScreenHeight = ScreenUtil.getScreenHeight();
    }

    /**
     * 手指落下，清空进行中的动画，记录滑动状态为false
     */
    private boolean handleTouchDown() {
        setAlpha(0.4f);
        clearAnimation();
        mIsDragging = false;
        return true;
    }

    /**
     * 手指抬起，如果当前处于滑动状态，需要让button自动吸边。
     */
    private boolean handleTouchUp(MotionEvent event) {
        mAnimatorOut.start();
        if (mIsDragging) {
            mIsDragging = false;
            updateViewCenterLocation(event.getRawX(), event.getRawY());
            moveToScreenEdge(event);
        }
        return true;
    }

    /**
     * 自动吸边
     * 根据event位置在屏幕中央的左右，决定最终button要吸附到左边还是右边
     * 处理垂直方向的边界条件，避免button太靠近顶端跟下拉控制栏冲突，或者太靠近下边界，导致button显示不全
     */
    private void moveToScreenEdge(MotionEvent event) {
        if (!mIsDragging) {
            float eventRawX = event.getRawX();
            // 默认自动吸附到左边
            float targetX = 0;
            // 如果 手指超过屏幕中线，需要自动吸附到右边
            if (eventRawX * 2 > mScreenWidth) {
                targetX = mScreenWidth - getMeasuredWidth();
            }
            float targetY = event.getRawY();
            // 如果距离屏幕顶部或者底部太近，需要保证视图在限定区域内，避免视图展示不全 或者 和下拉控制栏等冲突。
            if (event.getRawY() < MIN_MARGIN_TOP) {
                targetY = MIN_MARGIN_TOP;
            } else if (event.getRawY() + getMeasuredHeight() > mScreenHeight) {
                targetY = mScreenHeight - 1.7f * getMeasuredHeight();
            } else {
                targetY = event.getRawY() - 0.5f * getMeasuredHeight();
            }
            ViewCompat.animate(this).x(targetX).y(targetY).setDuration(500);
        }
    }

    /**
     * 滑动距离阈值计算
     */
    private float getDistanceFrom2Event(MotionEvent event1, MotionEvent event2) {
        if (event1 != null && event2 != null) {
            return Math.abs(event1.getRawX() - event2.getRawX()) + Math.abs(event1.getRawY() - event2.getRawY());
        }
        return 0;
    }

    /**
     * 处理滑动事件，让view跟随手指移动
     */
    private boolean handleTouchMove(MotionEvent event) {
        mIsDragging = true;
        return updateViewCenterLocation(event.getRawX(), event.getRawY());
    }

    /**
     * 更新view位置，让view中心点移动到指定位置
     */
    private boolean updateViewCenterLocation(float x1, float y1) {
        setX(x1 - 0.5f * getMeasuredWidth());
        setY(y1 - 0.5f * getMeasuredHeight());
        return true;
    }
}
