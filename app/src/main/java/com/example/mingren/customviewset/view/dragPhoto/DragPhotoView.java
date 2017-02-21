package com.example.mingren.customviewset.view.dragPhoto;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.mingren.customviewset.Utils.DensityUtil;
import com.example.mingren.customviewset.Utils.PaintUtil;
import com.example.mingren.customviewset.Utils.ScreenUtils;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by vincent on 2017/1/3.
 * email-address:674928145@qq.com
 * description仿微信
 */

public class DragPhotoView extends PhotoView {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mAlpha = 255;
    private float mTranslationX;
    private float mTranslationY;
    private float mScale = 1;
    private float downRawX;
    private float downRawY;
    private float desDistance = 200;

    private int l;
    private int t;
    private int mScreenHeight;
    private float downX;
    private float downY;

    public float minScale = DensityUtil.dip2px(getContext(),200) * 1.0f / ScreenUtils.getScreenWidth(getContext());

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public float getmTranslationX() {
        return mTranslationX;
    }

    public void setmTranslationX(float mTranslationX) {
        this.mTranslationX = mTranslationX;
    }

    public float getmTranslationY() {
        return mTranslationY;
    }

    public void setmTranslationY(float mTranslationY) {
        this.mTranslationY = mTranslationY;
    }

    private static final String TAG = "DragPhotoView";

    public DragPhotoView(Context context) {
        this(context, null);
    }

    public DragPhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public DragPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        initView();
    }

    private void initView() {
        mPaint = PaintUtil.getDefaultPaint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.BLACK);

        mScreenHeight = ScreenUtils.getScreenHeight(getContext());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(0, 0, 2000, 2000, mPaint);//在绘制图片之前绘制黑色背景,不然会覆盖图像
        canvas.translate(mTranslationX, mTranslationY);
        canvas.scale(l, mScale, mWidth / 2, mHeight / 2);
        mPaint.setAlpha(mAlpha);
        super.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downRawX = event.getRawX();
                downRawY = event.getRawY();
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mTranslationX = event.getRawX() - this.downRawX;
                mTranslationY = event.getRawY() - downRawY;
                float fraction = mTranslationY * 1.0f / desDistance;
                fraction = Math.max(Math.min(fraction, 1), 0);
                mAlpha = (int) (255 - fraction * 255);
                mScale = 1 - fraction * (1 - minScale);
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (event.getRawY() > mScreenHeight / 5 * 4) {
                    setVisibility(INVISIBLE);
                    reverseListener.onFinish(event.getRawX() - downX / 2, event.getRawY() - downY / 2);
                } else {
                    playScaleTranslatinAnimator(event);
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private void playScaleTranslatinAnimator(final MotionEvent event) {
        final float upTranslationX = mTranslationX;
        final float upTranslationY = mTranslationY;
        final float upAlpha = mAlpha;
        final float changeAlpha = 255 - upAlpha;
        final float upScale = mScale;
        final float changeScale = 1 - mScale;

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                mTranslationX = upTranslationX * (1 - fraction);
                mTranslationY = upTranslationY * (1 - fraction);
                if (event.getRawY() < mScreenHeight / 5 * 4) {
                    mAlpha = (int) (upAlpha + changeAlpha * fraction);
                    mScale = upScale + changeScale * fraction;
                }
                postInvalidate();
                Log.i(TAG, "onAnimationUpdate: " + "mTranslationX " + mTranslationX + " mTranslationY" + mTranslationY + " mAlpha" + mAlpha + " mScale" + mScale);
            }
        });
        valueAnimator.start();
    }

    private void playReverseAnimator(final float currentX, final float currentY) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();

                postInvalidate();
                Log.i(TAG, "onAnimationUpdate: " + "mTranslationX " + mTranslationX + " mTranslationY" + mTranslationY + " mAlpha" + mAlpha + " mScale" + mScale);
            }
        });
        valueAnimator.start();
    }


    private ReverseListener reverseListener;

    public void setOnReverseListener(ReverseListener listener) {
        this.reverseListener = listener;
    }
}
