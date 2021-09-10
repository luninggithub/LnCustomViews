package com.ln.custom.library.widget.alpha;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

/**
 * 可点击的RelativeLayout，具有点击态效果
 *
 * created by luninggithub
 */
public class AlphaRelativeLayout extends RelativeLayout {

    private ObjectAnimator mAnimatorOut;

    private boolean mIsClick = false;

    public AlphaRelativeLayout(Context context) {
        super(context);
        init();
    }

    public AlphaRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlphaRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AlphaRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mAnimatorOut = ObjectAnimator.ofFloat(this, "alpha", 0.4f, 1f);
        mAnimatorOut.setDuration(100);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mIsClick = l != null;
        super.setOnClickListener(l);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsClick && isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    this.setAlpha(0.4f);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mAnimatorOut.start();
                    break;
            }
        }
        return super.onTouchEvent(event);
    }
}
