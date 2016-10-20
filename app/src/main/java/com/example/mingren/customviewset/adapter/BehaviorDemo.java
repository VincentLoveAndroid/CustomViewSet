package com.example.mingren.customviewset.adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.DensityUtil;
import com.example.mingren.customviewset.Utils.ScreenUtils;

/**
 * Created by vincent on 2016/10/19.
 * email-address:674928145@qq.com
 */

public class BehaviorDemo extends CoordinatorLayout.Behavior {
    private int screenWidth;
    private int screenHeight;
    private int stateHeight;

    public BehaviorDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
        screenWidth = ScreenUtils.getScreenWidth(context);
        screenHeight = ScreenUtils.getScreenHeight(context);
        stateHeight = ScreenUtils.getStatusHeight(context);

    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int top = dependency.getTop();
        int left = dependency.getLeft();
        setLocation(child, left, top);
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        //return dependency.getId() == R.id.tv_dependent;
        return false;
    }

    public void setLocation(View child, int left, int top) {
//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//        params.leftMargin = left + 200;
//        params.topMargin = top + 200;
        int l = screenWidth - left - child.getWidth();
        int t = screenHeight - stateHeight - top - child.getHeight();
        child.layout(l, t, l+child.getWidth(),t+child.getHeight());
    }
}
