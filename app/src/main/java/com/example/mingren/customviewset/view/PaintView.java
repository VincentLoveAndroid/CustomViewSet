package com.example.mingren.customviewset.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.example.mingren.customviewset.Utils.DensityUtil;
import com.example.mingren.customviewset.Utils.PaintUtil;

/**
 * Created by vincent on 2016/11/29.
 * email-address:674928145@qq.com
 * description:
 */

public class PaintView extends View {

    private Paint mPaint;
    private Path mPath;
    private int mGap = DensityUtil.dip2px(getContext(), 40);
    private int mPassWordCount = 6;

    public PaintView(Context context) {
        super(context);
        init();
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = PaintUtil.getDefaultPaint();

        mPath = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(1f);
        mPaint.setColor(Color.BLUE);
//        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(mGap * mPassWordCount, 0);
        mPath.lineTo(mGap * mPassWordCount, mGap);
        mPath.lineTo(0, mGap);
//
//        mPath.lineTo(300, 0);
//        mPath.lineTo(300, 50);
//        mPath.lineTo(0, 50);
       mPath.close();
//        mPath.lineTo(0, 0);
       // mPath.lineTo(0, 0);
        canvas.drawPath(mPath, mPaint);
    }
}
