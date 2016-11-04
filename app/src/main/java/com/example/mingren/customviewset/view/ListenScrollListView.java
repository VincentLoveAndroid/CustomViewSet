package com.example.mingren.customviewset.view;

/**
 * Created by vincent on 2016/10/19.
 * email-address:674928145@qq.com
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by MingRen on 2016/10/8.
 * 能监听滑动的距离的ListView，能实现滑动隐藏标题栏等效果
 */
public class ListenScrollListView extends ListView implements AbsListView.OnScrollListener {
    public ListenScrollListView(Context context) {
        super(context);
        init();
    }

    public ListenScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
    }

    public void registerScrollListener() {
        if (headerHeight == 0 || itemHeight == 0) {//headerHeight,itemHeight初始化后才能设置监听
            throw new IllegalArgumentException("to listener scroll,headerHeight and itemHeight must be >0,you can call setHeaderItemHeight(int headerHeight, int itemHeight) first");
        }
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    private float mLastScrollY;
    private int headerHeight = 0;
    private int itemHeight = 0;
    private int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();//判断移动的最小距离

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        View topChild = view.getChildAt(0);
        float currentScrollY;
        if (topChild == null) {
            currentScrollY = 0;
        } else {
            int diff = 0;
            if (firstVisibleItem > 0)
                diff = (firstVisibleItem - 1) * itemHeight + headerHeight;
            currentScrollY = topChild.getTop() - diff;
        }

        System.out.println("currentScrollY:" + currentScrollY + " mLastScrollY:" + mLastScrollY);
        if (currentScrollY - mLastScrollY > touchSlop) {
            if (scrollListener != null) scrollListener.scrollDown(mLastScrollY, currentScrollY);
            mLastScrollY = currentScrollY;
        } else {
            if (currentScrollY - mLastScrollY < -touchSlop) {//滑动后currentScrollY可能已经不变了，仍然会继续调用onScroll，因此，要将currentScrollY和mLastScrollY相等的情况除外
                if (scrollListener != null)
                    scrollListener.scrollUp(mLastScrollY, currentScrollY);
                mLastScrollY = currentScrollY;
            }
        }
    }

    private ScrollListener scrollListener;

    public interface ScrollListener {
        void scrollUp(float lastScrollY, float currentScrollY);

        void scrollDown(float lastScrollY, float currentScrollY);
    }

    public void setScrollListener(ScrollListener listener) {
        this.scrollListener = listener;
    }

    public void setHeaderItemHeight(int headerHeight, int itemHeight) {
        this.headerHeight = headerHeight;
        this.itemHeight = itemHeight;
    }
}

