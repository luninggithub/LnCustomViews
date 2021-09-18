package com.ln.custom.library.widget.alpha;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 可点击的TextView，具有点击态效果
 *
 * @author luninggithub
 */
public class AlphaTextView extends AppCompatTextView {

    private ObjectAnimator mObjectAnimator;

    private boolean mIsClick = false;

    public AlphaTextView(Context context) {
        super(context);
        init();
    }

    public AlphaTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlphaTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mObjectAnimator = ObjectAnimator.ofFloat(this, "alpha", 0.6f, 1f);
        mObjectAnimator.setDuration(100);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mIsClick = l != null;
        super.setOnClickListener(l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsClick && isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    this.setAlpha(0.6f);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mObjectAnimator.start();
                    break;
            }
        }
        return super.onTouchEvent(event);
    }
}
