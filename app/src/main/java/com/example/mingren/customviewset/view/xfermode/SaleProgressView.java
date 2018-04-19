package com.example.mingren.customviewset.view.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.PaintUtil;

/**
 * Created by Vincent on 2017/9/22.
 * 仿淘宝抢购进度条
 */

public class SaleProgressView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;

    public SaleProgressView(Context context) {
        this(context, null);
    }

    public SaleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint = PaintUtil.getDefaultPaint();
        mPaint.setColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shape_sale_progress_stroke);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
