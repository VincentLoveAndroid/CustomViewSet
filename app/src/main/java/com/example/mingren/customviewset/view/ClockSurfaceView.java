package com.example.mingren.customviewset.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.mingren.customviewset.Utils.DensityUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vincent on 2016/10/10.
 * email-address 674928145@qq.com
 */

public class ClockSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private boolean mIsDrawing;
    private Paint mPaint;
    private int mRadius;
    private int currentPosition = 0;//记录目前所画第几个刻度点，从3点开始逆时针
    private int mCenterX;
    private int mCenterY;
    private int hourHandLength;
    private int MinuteHandLength;
    private int SecondHandLength;
    private double secondRadian;
    private boolean sizeChanged;
    private int mMinute;
    private int mSecond;
    private int mHour;
    private SimpleDateFormat mFormat;
    public static final double intervalRadian = 2 * Math.PI / 60;
    private static final double HALF_PI = Math.PI / 2;
    private Context mContext;


    public ClockSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public ClockSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLACK);

        mRadius = DensityUtil.dip2px(mContext, 300) / 2 - 10;
        hourHandLength = mRadius / 3;
        MinuteHandLength = mRadius / 2;
        SecondHandLength = (int) (mRadius / 1.2);

        mFormat = new SimpleDateFormat("HH:mm:ss", Locale.CHINESE);


    }

    /**
     * 这里获取的宽高一般是测量不变后的宽高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;
        sizeChanged = true;
    }

    private void draw() {
        try {
            //draw sth
            mCanvas = mHolder.lockCanvas();
            mCanvas.drawColor(Color.WHITE);//注意一定要刷屏
            mPaint.setColor(Color.BLACK);
            mCanvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mPaint);

            drawScaleAndNum();

            drawClockHand();

            drawTime();

        } catch (Exception e) {

        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);//放在finally保证每次都能将代码提交
            }
        }
    }

    private void drawTime() {
        mCanvas.drawText(formatNum(mHour) + ":" + formatNum(mMinute) + ":" + formatNum(mSecond), DensityUtil.dip2px(mContext, 10), DensityUtil.dip2px(mContext, 10), mPaint);
    }

    private String formatNum(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return "" + num;
    }

    /**
     * 画刻度和数字
     */
    private void drawScaleAndNum() {
        for (double i = HALF_PI; i > -1.5 * Math.PI; i -= intervalRadian) {
            currentPosition += 1;
            float outCircleX = calculateX(mRadius, i);
            float outCircleY = calculateY(mRadius, i);//弧度的Y轴和Android的Y轴方向相反，因此取反
            float inCircleX;
            float intCircleY;
            if ((currentPosition - 1) / 5 < 12 && (currentPosition - 1) % 5 == 0) {//整点
                inCircleX = calculateX(mRadius - DensityUtil.dip2px(mContext, 12.5f), i);
                intCircleY = calculateY(mRadius - DensityUtil.dip2px(mContext, 12.5f), i);
                int hourNum = (currentPosition - 1) / 5 == 0 ? 12 : (currentPosition - 1) / 5;
                drawNum(mCanvas, hourNum + "", calculateX(mRadius - DensityUtil.dip2px(mContext, 30), i), calculateY(mRadius - DensityUtil.dip2px(mContext, 30), i));
                mPaint.setStrokeWidth(4);
            } else {
                inCircleX = calculateX(mRadius - DensityUtil.dip2px(mContext, 5), i);
                intCircleY = calculateY(mRadius - DensityUtil.dip2px(mContext, 5), i);
                mPaint.setStrokeWidth(2);
            }
            mCanvas.drawLine(outCircleX, outCircleY, inCircleX, intCircleY, mPaint);
        }
        currentPosition = 0;
    }

    private void drawClockHand() {

        mCanvas.drawLine(mCenterX, mCenterY, calculateX(SecondHandLength, secondRadian + HALF_PI), calculateY(SecondHandLength, secondRadian + HALF_PI), mPaint);
        mPaint.setColor(Color.BLUE);
        mCanvas.drawLine(mCenterX, mCenterY, calculateX(MinuteHandLength, secondRadian / 60 + HALF_PI), calculateY(MinuteHandLength, secondRadian / 60 + HALF_PI), mPaint);

        mPaint.setColor(Color.RED);
        //注意，这里除以的是720不是3600,因为时针走一小格为12分钟，相当于分针走12格，秒针走720格
        mCanvas.drawLine(mCenterX, mCenterY, calculateX(hourHandLength, secondRadian / 720 + HALF_PI), calculateY(hourHandLength, secondRadian / 720 + HALF_PI), mPaint);
    }

    private float calculateX(int radius, double radian) {
        return (float) (radius * Math.cos(radian)) + mCenterX;
    }

    private float calculateY(int radius, double radian) {
        return -(float) (radius * Math.sin(radian)) + mCenterY;
    }


    private void drawNum(Canvas canvas, String text, float textX, float textY) {
        mPaint.setTextSize(DensityUtil.dip2px(mContext, 12));
        float textWidth = mPaint.measureText(text);
        canvas.drawText(text, textX - textWidth / 2, textY + DensityUtil.dip2px(mContext, 4), mPaint);//绘制数字并修正数字的位置
    }

    /**
     * 更新当前时间和刻度值
     */
    private void updateCurrentTime() {
        mFormat.format(new Date());
        mHour = mFormat.getCalendar().get(Calendar.HOUR_OF_DAY);
        mMinute = mFormat.getCalendar().get(Calendar.MINUTE);
        mSecond = mFormat.getCalendar().get(Calendar.SECOND);
        int totalS = mHour * 3600 + mMinute * 60 + mSecond;
        secondRadian = -totalS * intervalRadian;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;

    }

    @Override
    public void run() {
        while (mIsDrawing && sizeChanged) {
            try {
                updateCurrentTime();
                long drawBefore = System.currentTimeMillis();
                draw();
                long drawTime = System.currentTimeMillis() - drawBefore;
                if (drawTime < 1000) {
                    Thread.sleep(1000 - drawTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
