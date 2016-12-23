package com.example.mingren.customviewset.view.swipeCard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

/**
 * Created by vincent on 2016/12/19.
 * email-address:674928145@qq.com
 * description:
 */

public class SwipeLayoutManager extends RecyclerView.LayoutManager {

    private float downX;
    private float downY;
    private float dx;
    private float dy;
    private float oldDx;
    private float oldDY;
    private int l;
    private int t;

    private static final String TAG = "SwipeLayoutManager";


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
//
//        return null;
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return layoutParams;
    }

    @Override
    public void onLayoutChildren(final RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);//将所有View放到Scrap里面，不移除
        int count = getItemCount();
        for (int i = 0; i < count; i++) {
            final View child = recycler.getViewForPosition(i);
            measureChildWithMargins(child, 0, 0);
            addView(child);
            int width = getDecoratedMeasuredWidth(child);
            int height = getDecoratedMeasuredHeight(child);
            layoutDecorated(child, 0, 0, width, height);
            if (i < getItemCount() - 1) {
                child.setScaleX(0.8f);
                child.setScaleY(0.8f);
            } else {
                child.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                downX = event.getRawX();
                                downY = event.getRawY();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                //if (isMove(event)) {
                                dx = event.getRawX() - downX;
                                dy = event.getRawY() - downY;
                                v.setTranslationX(dx + oldDx);
                                v.setTranslationY(dy + oldDY);
                                updateItemScale(getChildAt(getItemCount() - 2), dy);

//                                }
                                break;

                            case MotionEvent.ACTION_UP:
//                            oldDx = v.getTranslationX();
//                            oldDY = v.getTranslationY();

                                int centerX = v.getWidth() / 4;
                                int centerY = v.getHeight() / 4;
                                if (v.getX() < -centerX && v.getY() < -centerY) {
                                    v.animate().translationX(-1000).translationY(-1000).setDuration(800).setListener(listener).setInterpolator(new OvershootInterpolator());
                                    removeAndRecycleView(child, recycler);
                                    if (itemRemoveListener != null)
                                        itemRemoveListener.onItemRemove();
                                } else if (v.getX() > centerX && v.getY() < -centerY) {
                                    v.animate().translationX(1000).translationY(-1000).setDuration(800).setListener(listener).setInterpolator(new OvershootInterpolator());
                                    removeAndRecycleView(child, recycler);
                                    if (itemRemoveListener != null)
                                        itemRemoveListener.onItemRemove();
                                } else {
                                    v.animate().x(0).y(0).setDuration(500).setListener(listener);
                                }
                                break;
                        }
                        return true;
                    }
                });
            }
        }
    }

    private void updateItemScale(View v, float dy) {
        float scale = 0.8f;
        float fraction = Math.abs(dy) * 1.0f / 120;
        if (fraction > 1) fraction = 1;
        scale += 0.2f * fraction;
        v.setScaleX(scale);
        v.setScaleY(scale);
        Log.i(TAG, "updateItemScale: " + scale);
    }

    private ItemRemoveListener itemRemoveListener;

    public void setItemRemoveListener(ItemRemoveListener listener) {
        this.itemRemoveListener = listener;
    }

    private Animator.AnimatorListener listener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            oldDx = 0;
            oldDY = 0;
        }
    };

    private boolean isMove(MotionEvent event) {
        int moveX = (int) event.getX();
        int moveY = (int) event.getY();
        if (Math.abs(moveX - downX) > 8 || Math.abs(moveY - downY) > 8) {
            return true;
        }
        return false;
    }
}
