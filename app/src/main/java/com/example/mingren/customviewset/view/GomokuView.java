package com.example.mingren.customviewset.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.mingren.customviewset.Utils.PaintUtil;

/**
 * Created by vincent on 2017/2/2.
 * email-address:674928145@qq.com
 * description:五子棋View
 */

public class GomokuView extends FrameLayout {

    private Paint mPaint;

    public GomokuView(Context context) {
        super(context);
    }

    public GomokuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GomokuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = PaintUtil.getDefaultPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
