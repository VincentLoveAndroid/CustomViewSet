package com.example.mingren.customviewset.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.mingren.customviewset.Utils.DensityUtil;
import com.example.mingren.customviewset.Utils.PaintUtil;

/**
 * Created by vincent on 2017/2/2.
 * email-address:674928145@qq.com
 * description:带锯齿的自定义卡券效果
 */

public class CouponView extends RelativeLayout {

    private Paint mPaint;
    private int mGap = DensityUtil.dip2px(getContext(), 5);
    private int mRadius = DensityUtil.dip2px(getContext(), 5);
    private int mCircleNum;
    private int mWidth;
    private int mHeight;

    public CouponView(Context context) {
        this(context, null);
    }

    public CouponView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CouponView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = PaintUtil.getDefaultPaint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setDither(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        mCircleNum = mWidth / (2 * mRadius + mGap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        for (int i = 0; i < mCircleNum; i++) {
            int cx = mGap + mRadius + (2 * mRadius + mGap) * i;
            int TopCy = 0;
            int BottomCy = mHeight;
            canvas.drawCircle(cx, TopCy, mRadius, mPaint);//绘制上面锯齿
            canvas.drawCircle(cx, BottomCy, mRadius, mPaint);//绘制下面锯齿
        }
    }
}
