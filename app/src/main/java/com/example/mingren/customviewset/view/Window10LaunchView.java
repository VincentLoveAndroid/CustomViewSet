package com.example.mingren.customviewset.view;

import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mingren.customviewset.Ob.Win10Circle;
import com.example.mingren.customviewset.Utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MingRen on 2016/9/22.
 */
public class Window10LaunchView extends View implements View.OnClickListener {

    private Paint mPaint;
    private int mCenterX;
    private int mRadius = DensityUtil.dip2px(getContext(), 100);
    private List<Win10Circle> circleList = new ArrayList<>();
    private ValueAnimator valueAnimator;
    private boolean play;

    public Window10LaunchView(Context context) {
        super(context);
        init();
    }

    public Window10LaunchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(30);
        // mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);


        initValueAnimator();

        setOnClickListener(this);

        for (int i = 0; i < 6; i++) {
            Win10Circle win10Circle = new Win10Circle();
            circleList.add(win10Circle);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = getWidth() / 2;

        for (Win10Circle circle : circleList) {
            circle.origanlCenterX = mCenterX;
            circle.color = Color.WHITE;
        }

    }

    private void initValueAnimator() {
        valueAnimator = ValueAnimator.ofInt(0, 1);
        valueAnimator.addUpdateListener(listener);
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);

    }

    private ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            updateCirclePosition(animation);
            invalidate();
        }
    };

    /**
     * 更新原点的位置
     *
     * @param animation
     */
    private void updateCirclePosition(ValueAnimator animation) {
        float fraction = animation.getAnimatedFraction();
        for (Win10Circle circle : circleList) {
            //插值器
            float currentFraction = circleTrackInterpolator(fraction, circleList.indexOf(circle));

            circle.centerX = (circle.origanlCenterX + Math.sin(2 * Math.PI * currentFraction) * mRadius);
            double sqrtY = Math.sqrt(Math.pow(mRadius, 2) - Math.pow(circle.centerX - mCenterX, 2));
            //修正Y轴位置开方后的正负关系
            if (currentFraction < 0.25 || currentFraction > 0.75) {
                sqrtY = -sqrtY;
            }
            circle.centerY = sqrtY + mCenterX;
            if (currentFraction == 1) {
                circle.origanlCenterX = circle.centerX;
            }
        }
    }

    /**
     * 改变随时间流逝的百分比，类似各种插值器的实现
     *
     * @param fraction
     * @param position
     * @return
     */
    private float circleTrackInterpolator(float fraction, int position) {

        return (float) Math.pow(fraction, position + 1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!play) return;
        for (Win10Circle circle : circleList) {
            mPaint.setColor(circle.color);
            canvas.drawCircle((float) circle.centerX, (float) circle.centerY, 10, mPaint);
        }
    }

    @Override
    public void onClick(View v) {
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
            play = true;
        }
    }
}
