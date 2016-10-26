package com.example.mingren.customviewset.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.mingren.customviewset.R;

/**
 * Created by Administrator on 2016/10/23.
 */
public class ParallaxListView extends ListView {

    private int mHeaderHeight;

    private ImageView mIvHeaderBg;
    private int maxHeaderHeight;
    private int minHeaderHeight;
    private boolean isTouch;//标志是否还在触摸

    public ParallaxListView(Context context) {
        super(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.parallax_header_height);
        maxHeaderHeight = (mHeaderHeight * 2);
        minHeaderHeight = mHeaderHeight;
    }

    public void setmHeaderbg(ImageView mivHeaderbg) {
        this.mIvHeaderBg = mivHeaderbg;
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (mIvHeaderBg != null) {
            if (deltaY < 0) {//Y轴方向过度滚动量，下滑过度为负，上滑过度为正，想象坐标的移动,都是每次过度滑动的增量，较小
                if (maxHeaderHeight > mIvHeaderBg.getHeight()) {
                    int height = Math.min(maxHeaderHeight, mIvHeaderBg.getHeight() - deltaY);
                    changeHeight(height);
                }
            } else {
                if (isOverMinHeight()) {
                    int height = mIvHeaderBg.getHeight() - deltaY;
                    changeHeight(height);
                }

            }
            //快滑惯性造成deltaY较大停止的时候，也执行回弹动画
            if (mIvHeaderBg.getHeight() > mHeaderHeight && !isTouch) {
                playSpringAnimation();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    private void changeHeight(int height) {
        mIvHeaderBg.getLayoutParams().height = height;
        mIvHeaderBg.requestLayout();
    }

    private void playSpringAnimation() {
        SpringAnimation springAnimation = new SpringAnimation(mIvHeaderBg.getHeight(), mIvHeaderBg);
        springAnimation.setDuration(500);
        mIvHeaderBg.startAnimation(springAnimation);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        ViewGroup parent = (ViewGroup) mIvHeaderBg.getParent();
        int top = parent.getTop();//得到header的瞬时偏移量
        if (top < 0 && isOverMinHeight()) {
            int height = Math.max(minHeaderHeight, mIvHeaderBg.getHeight() + top);
            mIvHeaderBg.getLayoutParams().height = height;
            //很重要，让头部一直位于ListView的顶部，此时getTop实际上是相当于每次移动都是很小的偏移量，因为每次回重新让header至于头部位置
            parent.layout(getLeft(), 0, getLeft() + parent.getWidth(), getHeight());
            mIvHeaderBg.requestLayout();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            isTouch = true;
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            isTouch = false;
            if (isOverMinHeight()) {
                playSpringAnimation();
            }
        }
        return super.onTouchEvent(ev);
    }

    private boolean isOverMinHeight() {
        return mIvHeaderBg.getHeight() > minHeaderHeight;
    }

    /**
     * 回弹动画
     */
    private class SpringAnimation extends Animation {

        private int mDistance;
        private int mCurrentHeight;
        private ImageView iv;

        public SpringAnimation(int mCurrentHeight, ImageView iv) {
            this.mCurrentHeight = mCurrentHeight;
            this.iv = iv;
            mDistance = mCurrentHeight - mHeaderHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (mDistance < 0) return;
            iv.getLayoutParams().height = (int) (mCurrentHeight - mDistance * interpolatedTime);
            iv.requestLayout();
        }
    }
}
