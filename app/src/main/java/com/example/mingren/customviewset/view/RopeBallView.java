package com.example.mingren.customviewset.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.example.mingren.customviewset.Ob.BounceBall;
import com.example.mingren.customviewset.Utils.DensityUtil;

import java.util.Map;

/**
 * Created by vincent on 2016/11/16.
 * email-address:674928145@qq.com
 * description:小球回弹绳子的View
 */

public class RopeBallView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Canvas mCanvas;
    private Paint mPaint;
    private SurfaceHolder mHolder;
    private boolean isDrawing = true;
    private int smallBallRadius = DensityUtil.dip2px(getContext(), 10);
    private int mWidth;
    private int mHeight;
    private float mTopY;
    private float mBottomY;
    private boolean sizeChanged;
    private int refreshDuration;
    private static int ANIM_DURATION = 2000;
    private BounceBall mBounceBall;
    private float mDistance;
    private float mPointY;
    private Path mPath;

    public RopeBallView(Context context) {
        this(context, null);
    }

    public RopeBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPath = new Path();
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        mBounceBall = new BounceBall();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        mTopY = DensityUtil.dip2px(getContext(), 30);
        mBottomY = mHeight / 2 + DensityUtil.dip2px(getContext(), 50);
        mDistance = mTopY - mBottomY;
        mBounceBall.centerX = mWidth / 2;
        mBounceBall.centerY = mTopY + smallBallRadius;
        mPointY = mHeight / 2;
        sizeChanged = true;
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            mCanvas.drawColor(Color.WHITE);//在此设置背景颜色。不能直接在xml设置背景颜色，否则其他绘制不显示了
            drawFixedBall();
            drawBounceBall();
            drawRope();
        } catch (Exception e
                ) {

        } finally {
            if(mCanvas!=null)
            mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    private void drawBounceBall() {
        mCanvas.drawCircle(mBounceBall.centerX, mBounceBall.centerY, smallBallRadius, mPaint);
    }

    @Override
    public void run() {
        if (isDrawing && sizeChanged) {
            draw();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void drawFixedBall() {
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCanvas.drawCircle(smallBallRadius, mHeight / 2, smallBallRadius, mPaint);
        mCanvas.drawCircle(mWidth - smallBallRadius, mHeight / 2, smallBallRadius, mPaint);
    }

    private void drawRope() {
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath.reset();//每次绘制的时候重置，不要每次绘制的时候都new
        mPath.moveTo(smallBallRadius * 2, mHeight / 2);
        //分开两个贝塞尔曲线画，如果用一个贝塞尔曲线画，如何确定最低点的坐标？注意控制点并不是最低点
        mPath.quadTo(mWidth / 4, mPointY, mWidth / 2, mPointY);
        mPath.quadTo(mWidth / 4 * 3, mPointY, mWidth - smallBallRadius * 2, mHeight / 2);
        mCanvas.drawPath(mPath, mPaint);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(this).start();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mTopY + smallBallRadius, mBottomY - smallBallRadius);
        valueAnimator.addUpdateListener(updateListener);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mBounceBall.centerY = (float) animation.getAnimatedValue();
            if (mBounceBall.centerY >= mHeight / 2 - smallBallRadius) {
                mPointY = mBounceBall.centerY + smallBallRadius;
            }
            RopeBallView.this.run();
        }
    };
}
