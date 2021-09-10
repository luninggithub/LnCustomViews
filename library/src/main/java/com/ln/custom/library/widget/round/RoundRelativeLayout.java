package com.ln.custom.library.widget.round;

import android.content.Context;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.RelativeLayout;

import com.ln.custom.library.utils.ScreenUtil;

/**
 * 一个可以把RelativeLayout裁剪成圆角的自定义RelativeLayout布局
 * 由于layout里面的子view同样会被裁剪，例如VideoView，ImageView等，很实用
 *
 * created by luninggithub
 */
public class RoundRelativeLayout extends RelativeLayout {

    private static final int DEFAULT_ROUND_SIZE = 20;

    public RoundRelativeLayout(Context context) {
        this(context, null);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        RoundViewOutlineProvider outlineProvider = new RoundViewOutlineProvider(ScreenUtil.dp2px(DEFAULT_ROUND_SIZE));
        setOutlineProvider(outlineProvider);
        setClipToOutline(true);
    }

    /**
     * 圆角ViewOutlineProvider
     */
    private static class RoundViewOutlineProvider extends ViewOutlineProvider {
        private final int roundSize;

        public RoundViewOutlineProvider() {
            this(DEFAULT_ROUND_SIZE);
        }

        public RoundViewOutlineProvider(int roundSize) {
            this.roundSize = roundSize;
        }

        @Override
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), roundSize);
        }
    }

}
