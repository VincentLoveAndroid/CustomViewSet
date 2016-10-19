package com.example.mingren.customviewset.base;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.example.mingren.customviewset.Ob.BaseOb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MingRen on 2016/9/24.
 */
public abstract class BaseCustomViewGroup<T extends BaseOb> extends RelativeLayout implements View.OnClickListener {
    protected Paint mPaint;
    protected List<T> obList = new ArrayList<>();
    protected int obCount;

    public BaseCustomViewGroup(Context context) {
        this(context, null);
    }

    public BaseCustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        initSomething();
    }

    private void registerOnClick() {
        setOnClickListener(this);
    }

    protected abstract void initSomething();
}
