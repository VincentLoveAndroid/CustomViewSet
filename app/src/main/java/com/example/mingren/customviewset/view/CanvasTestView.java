package com.example.mingren.customviewset.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.mingren.customviewset.Utils.PaintUtil;

import java.util.Map;

/**
 * Created by vincent on 2016/11/17.
 * email-address:674928145@qq.com
 * description:
 */

public class CanvasTestView extends View {

    private Paint mPaint;

    public CanvasTestView(Context context) {
        super(context);
        init();
    }

    public CanvasTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = PaintUtil.getDefaultPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);

        // y 方向上倾斜45 度
        canvas.skew(0,1);
        mPaint.setColor(0x8800ff00);
        canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);

    }
}
