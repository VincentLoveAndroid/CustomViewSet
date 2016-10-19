package com.example.mingren.customviewset.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.mingren.customviewset.Ob.BaseOb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by MingRen on 2016/9/24.
 */
public abstract class BaseCustomView<T extends BaseOb> extends View implements View.OnClickListener {
    protected Paint mPaint;
    protected List<T> obList = new CopyOnWriteArrayList<>();
    protected int obCount;

    public BaseCustomView(Context context) {
        super(context);
        init();
    }

    public BaseCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
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
