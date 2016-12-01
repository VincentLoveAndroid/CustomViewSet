package com.example.mingren.customviewset.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.mingren.customviewset.Utils.DensityUtil;
import com.example.mingren.customviewset.Utils.PaintUtil;

/**
 * Created by vincent on 2016/11/17.
 * email-address:674928145@qq.com
 * description:
 */

public class ScaleView extends View {

    public static final int SCALE_COUNT = 101;
    private int shortTop = DensityUtil.dip2px(getContext(), 10);
    private int MiddleTop = DensityUtil.dip2px(getContext(), 15);
    private int longTop = DensityUtil.dip2px(getContext(), 20);

    private Paint mPaint;
    private Path mPath;

    public ScaleView(Context context) {
        this(context, null);
    }

    public ScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = PaintUtil.getDefaultPaint();
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawRect(canvas);
        drawScale(canvas);
        drawText(canvas);
    }
    //绘制文字
    private void drawText(Canvas canvas) {
        canvas.save();
        canvas.translate(120, 400);
        mPaint.setTextSize(20);
        mPaint.setStrokeWidth(1);
        for (int i = 0; i < SCALE_COUNT; i++) {
            if (i % 10 == 0) {
                float textWidth = mPaint.measureText(i / 10 + "");
                int num = i / 10;
                canvas.drawText(num + "", -textWidth / 2, -longTop - 10
                        , mPaint);
                canvas.translate(50, 0);
            }
        }
        canvas.restore();
    }
    //绘制刻度
    private void drawScale(Canvas canvas) {
        canvas.save();
        canvas.translate(120, 400);
        int top;
        for (int i = 0; i < SCALE_COUNT; i++) {
            if (i % 10 == 0) {
                top = longTop;
                mPaint.setStrokeWidth(2);
            } else if (i % 5 == 0) {
                top = MiddleTop;
                mPaint.setStrokeWidth(1.5f);
            } else {
                top = shortTop;
                mPaint.setStrokeWidth(1);
            }
            canvas.drawLine(0, 0, 0, -top, mPaint);
            canvas.translate(5, 0);
        }
        canvas.restore();
    }
    //绘制边框
    private void drawRect(Canvas canvas) {
        canvas.save();
        canvas.translate(100, 300);
        mPath.lineTo(540, 0);
        mPath.lineTo(540, 100);
        mPath.lineTo(0, 100);
        mPath.lineTo(0, 0);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }
}
