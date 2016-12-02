package com.example.mingren.customviewset.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.DensityUtil;
import com.example.mingren.customviewset.Utils.PaintUtil;

import java.lang.reflect.Field;

/**
 * Created by vincent on 2016/11/29.
 * email-address:674928145@qq.com
 * description:访支付宝密码输入控件
 */

public class PayEditText extends EditText {

    private static final String TAG = "PayEditText";

    private Paint mRectPaint;
    private Paint mDividerPaint;
    private Paint mCirclePaint;
    private int mGap = DensityUtil.dip2px(getContext(), 40);
    private int mMaxRadius = DensityUtil.dip2px(getContext(), 6);
    private int mCircleRound = DensityUtil.dip2px(getContext(), 8);
    private float currentRadius;

    private float mInterpolatedTime;
    private CircleAnimation circleAnimation;

    public static final int STATE_INPUT = 1;//输入状态
    public static final int STATE_DELETE = 0;//删除状态
    public static final int STATE_COMMON = -1;//普通状态

    private int state = STATE_COMMON;

    private int mTextLength;//当前文本长度

    private int maxLength;

    private int mStartX;
    private int mStartY;

    public PayEditText(Context context) {
        this(context, null);
    }

    public PayEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PayEditText);
        maxLength = a.getInt(R.styleable.PayEditText_android_maxLength, 6);

        mRectPaint = PaintUtil.getDefaultPaint();
        mRectPaint.setStrokeWidth(2);
        mDividerPaint = PaintUtil.getDefaultPaint();
        mCirclePaint = PaintUtil.getDefaultPaint();
        mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        circleAnimation = new CircleAnimation();
        circleAnimation.setDuration(300);

    }

    public static void main(String arg[]) {
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        //super.onDraw(canvas);//不让其绘制光标
        drawRect(canvas);
        drawDivider(canvas);
        drawCircle(canvas);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mStartX = width / 2 - mGap * maxLength / 2;
        mStartY = height / 2 - mGap / 2;
    }

    private void drawRect(Canvas canvas) {
        RectF rectF = new RectF(mStartX, mStartY, mStartX + mGap * maxLength, mStartY + mGap);
        canvas.drawRoundRect(rectF, mCircleRound, mCircleRound, mRectPaint);
    }


    private void drawDivider(Canvas canvas) {
        for (int i = 0; i < maxLength - 1; i++) {
            int x = mStartX + mGap * (i + 1);
            int y = mStartY + mGap;
            canvas.drawLine(x, mStartY, x, y, mDividerPaint);
        }
    }

    private void drawCircle(Canvas canvas) {
        if (state != STATE_COMMON) {
            for (int i = 0; i < maxLength; i++) {
                final int centerX = mStartX + mGap / 2 + mGap * i;
                final int centerY = mStartY + mGap / 2;
                if (state == STATE_INPUT) {
                    currentRadius = mInterpolatedTime * mMaxRadius;
                    if (i == mTextLength - 1) {
                        canvas.drawCircle(centerX, centerY, currentRadius, mCirclePaint);
                        break;
                    }
                } else if (state == STATE_DELETE) {
                    currentRadius = mMaxRadius - mInterpolatedTime * mMaxRadius;
                    if (i == mTextLength) {
                        canvas.drawCircle(centerX, centerY, currentRadius, mCirclePaint);
                        break;
                    }
                }
                canvas.drawCircle(centerX, centerY, mMaxRadius, mCirclePaint);
                Log.i(TAG, "onDraw: currentRadius" + currentRadius);
            }
        }
    }

    /**
     * 通过反射获取maxLength
     *
     * @return
     */
    private int getMaxLength() {
        maxLength = 6;
        InputFilter[] filters = getFilters();
        for (InputFilter filter : filters) {
            if ("android.text.InputFilter$LengthFilter".equals(filter.getClass().getName())) {
                Class<? extends InputFilter> aClass = filter.getClass();
                try {
                    Field[] declaredFields = aClass.getDeclaredFields();
                    for (Field field : declaredFields) {
                        if (field.getName().equals("mMax")) {
                            field.setAccessible(true);
                            maxLength = field.getInt(filter);
                            Log.i(TAG, "getMaxLength: 反射取得maxLength值 " + maxLength);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return maxLength;
    }


    @Override
    protected void onTextChanged(final CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);


        mTextLength = text.toString().length();
        if (lengthAfter == lengthBefore) {
            state = STATE_COMMON;
        } else if (lengthAfter > lengthBefore) {
            state = STATE_INPUT;
            clearAnimation();
            startAnimation(circleAnimation);
        } else {
            state = STATE_DELETE;
            clearAnimation();
            startAnimation(circleAnimation);
        }

        if (mTextLength == maxLength && mTextLength > 0) {
            Toast.makeText(getContext(), "你输入的密码是" + text, Toast.LENGTH_SHORT).show();
        }

        Log.i(TAG, "onTextChanged: mTextLength" + mTextLength);

    }


    private class CircleAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mInterpolatedTime = interpolatedTime;
            postInvalidate();//及时刷新
            Log.i(TAG, "applyTransformation: " + mInterpolatedTime);
        }
    }
}
