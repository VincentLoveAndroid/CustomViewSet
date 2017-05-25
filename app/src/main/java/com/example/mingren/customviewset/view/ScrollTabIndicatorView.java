package com.example.mingren.customviewset.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.DensityUtil;


import java.lang.ref.WeakReference;

/**
 * Created by vincent on 2016/10/13.
 * email-address:674928145@qq.com
 * 自定义滑动指示器，监听RecycleView滑动距离，适用于多个Fragment的切换换，带动画效果
 */

public class ScrollTabIndicatorView extends RelativeLayout implements ScrollTabAdapter.OnTabClickListener {

    private RecyclerView rcvContent;
    private ImageView ivArrow;
    private LinearLayoutManager mLinearLayoutManager;
    private float arrowLeft = 0;
    private float oldScrollDx;
    private int currentPosition = -1;
    private boolean isMoving;

    public ScrollTabIndicatorView(Context context) {
        super(context);
        init();
    }

    public ScrollTabIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.view_scroll_tab_indicator, this);
        ivArrow = (ImageView) findViewById(R.id.iv_arrow);
        rcvContent = (RecyclerView) findViewById(R.id.rv_content);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvContent.setLayoutManager(mLinearLayoutManager);
        rcvContent.addItemDecoration(itemDecoration);

        ScrollTabAdapter scrollTabAdapter = new ScrollTabAdapter(getContext());
        rcvContent.setAdapter(scrollTabAdapter);
        rcvContent.addOnScrollListener(scrollListener);
        scrollTabAdapter.setOnTabClickListener(this);
    }


    public int getScrollDx() {
        int position = mLinearLayoutManager.findFirstVisibleItemPosition();
        View firstVisibleChildView = mLinearLayoutManager.findViewByPosition(position);
        int itemWidth = firstVisibleChildView.getWidth();
        return firstVisibleChildView.getLeft() - position * itemWidth;
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            arrowLeft = arrowLeft + (getScrollDx() - oldScrollDx);
            oldScrollDx = getScrollDx();
        }
    };

    private RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.top = 0;
            outRect.left = DensityUtil.dip2px(getContext(), 20);
            outRect.bottom = 0;
            outRect.right = 0;
        }
    };


    private class MyAnimatorListenerAdapter extends AnimatorListenerAdapter {

        private WeakReference<ImageView> ivWeakReference;
        private WeakReference<TextView> tvWeakReference;

        public MyAnimatorListenerAdapter(ImageView ivArrows, TextView tvTxt) {
            ivWeakReference = new WeakReference(ivArrows);
            tvWeakReference = new WeakReference(tvTxt);
        }

        @Override
        public void onAnimationStart(Animator animation) {
            isMoving = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isMoving = false;
            ImageView ivArrows = ivWeakReference.get();
            TextView tvTxt = tvWeakReference.get();
            if (ivArrow == null || ivArrows == null || tvTxt == null) return;
            arrowLeft = ivArrow.getTranslationX();
            ivArrows.setVisibility(View.VISIBLE);
            ivArrow.setVisibility(View.INVISIBLE);
            tvTxt.setTextColor(Color.BLUE);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            isMoving = false;
        }
    }


    @Override
    public void onTabClick(int tabLeft, int currentArrowPosition, final ImageView ivArrows, final TextView tvTxt) {
        if (isMoving || currentPosition == currentArrowPosition) return;
        currentPosition = currentArrowPosition;
        int childCount = rcvContent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = rcvContent.getChildAt(i);
            child.findViewById(R.id.iv_arrow).setVisibility(View.INVISIBLE);
            ((TextView) child.findViewById(R.id.tv_text)).setTextColor(Color.BLACK);
        }
        ivArrow.setVisibility(View.VISIBLE);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(ivArrow, "TranslationX", arrowLeft, tabLeft).setDuration(300);
        translationX.addListener(new MyAnimatorListenerAdapter(ivArrows, tvTxt));
        translationX.start();
        Toast.makeText(getContext(), "点解啦" + currentArrowPosition, Toast.LENGTH_SHORT).show();
    }
}
