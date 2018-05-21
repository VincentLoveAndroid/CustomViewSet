package com.example.mingren.customviewset.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.mingren.customviewset.Utils.PaintUtil;

/**
 * Created by Vincent on 2018/5/2.
 * description:支持Padding和Wrap_content的View
 */

public class SupportPaddingWrapView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public SupportPaddingWrapView(Context context) {
        this(context, null);
    }

    public SupportPaddingWrapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SupportPaddingWrapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = PaintUtil.getDefaultPaint();
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        supportWrap(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        supportPadding(canvas);
    }

    /**
     * 让自定义View支持wrapContent
     */
    private void supportWrap(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);


        setMeasuredDimension(widthSpecMode == MeasureSpec.AT_MOST ? 200 : widthSpecSize,
                heightSpecMode == MeasureSpec.AT_MOST ? 200 : heightSpecSize);
    }

    /**
     * 让自定义View支持padding
     */
    private void supportPadding(Canvas canvas) {
        int paddingLeft = getPaddingLeft();//得到的就是转换为像素的paddingLeft
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int width = mWidth - paddingLeft - paddingRight;
        int height = mHeight - paddingTop - paddingBottom;

        //选择宽高中较小的作为原的半径
        int radius = Math.min(width, height) / 2;

        canvas.drawCircle(paddingLeft + width / 2, paddingTop + height / 2, radius, mPaint);
    }


}
