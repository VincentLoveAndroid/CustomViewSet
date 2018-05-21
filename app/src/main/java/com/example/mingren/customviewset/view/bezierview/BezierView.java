package com.example.mingren.customviewset.view.bezierview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.example.mingren.customviewset.R;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class BezierView extends RelativeLayout {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_SPECIAL = 1;
    // 图片数组
    private Drawable[] specialDrawable;
    private Drawable[] normalDrawable;
    // 图片的宽高
//    private int mWidth;
//    private int mHeight;
    // 屏幕的宽高
    private int cWidth;
    private int cHeight;
    private int[] startPos = new int[2];
    // 添加到当前view的参数
    private LayoutParams mParams;
    // 随机对象
    private Random mRandom;
    // 渐变动画执行的时间
    private long mPDuration = 500;
    // 贝塞尔执行的时间
    private long mBDuration = 8000;
    //插补器集 用于随机插补器
    Interpolator[] interpolators = new Interpolator[3];
    public static final int TYPE_CAPACITY = 30;
    private ImageViewPools imageViewPools;
    private BlockingQueue<Integer> typeStash = new LinkedBlockingQueue<>();
    private WorkingTask workingTask;

    private boolean isChangeBubble;

    private List<Animator> animators = new CopyOnWriteArrayList<>();

    public BezierView(Context context) {
        super(context);
        init(context);
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private boolean anchorMode;

    public void setAnchorMode(boolean anchorMode) {
        this.anchorMode = anchorMode;
    }

    private void init(Context context) {
        normalDrawable = new Drawable[7];
        specialDrawable = new Drawable[7];

        initIconDrawable(context);

        mRandom = new Random();

        imageViewPools = new ImageViewPools(context, TYPE_CAPACITY);
        for (int i = 0; i < TYPE_CAPACITY; i++) {
            ImageView view = imageViewPools.allocImageView();
            Drawable drawable = null;
            int nextInt = mRandom.nextInt(normalDrawable.length - 1);
            drawable = normalDrawable[nextInt];
            int width = 0;
            int height = 0;
            if (drawable != null) {
                width = drawable.getIntrinsicWidth();
                height = drawable.getIntrinsicHeight();
            }
            float scale = mRandom.nextFloat() * (1f - 0.6f) + 0.6f;
            width = (int) (scale * width);
            height = (int) (scale * height);
            mParams = new LayoutParams(width, height);
            mParams.leftMargin = startPos[0] - width / 2;
            mParams.bottomMargin = cHeight - startPos[1];
            mParams.addRule(ALIGN_PARENT_BOTTOM);

            view.setLayoutParams(mParams);
            view.setVisibility(GONE);
            addView(view);
        }

        for (int i = 0; i < TYPE_CAPACITY; i++) {
            imageViewPools.releaseImageView((ImageView) getChildAt(i));
        }
    }

    public void initIconDrawable(Context context) {

            normalDrawable[0] = ContextCompat.getDrawable(context, R.mipmap.icon_ya1);
            normalDrawable[1] = ContextCompat.getDrawable(context, R.mipmap.icon_ya2);
            normalDrawable[2] = ContextCompat.getDrawable(context, R.mipmap.icon_ya3);
            normalDrawable[3] = ContextCompat.getDrawable(context, R.mipmap.icon_ya4);
            normalDrawable[4] = ContextCompat.getDrawable(context, R.mipmap.icon_ya5);
            normalDrawable[5] = ContextCompat.getDrawable(context, R.mipmap.icon_ya6);
            normalDrawable[6] = ContextCompat.getDrawable(context, R.mipmap.icon_ya7);

            specialDrawable[0] = ContextCompat.getDrawable(context, R.mipmap.icon_ya1);
            specialDrawable[1] = ContextCompat.getDrawable(context, R.mipmap.icon_ya2);
            specialDrawable[2] = ContextCompat.getDrawable(context, R.mipmap.icon_ya3);
            specialDrawable[3] = ContextCompat.getDrawable(context, R.mipmap.icon_ya4);
            specialDrawable[4] = ContextCompat.getDrawable(context, R.mipmap.icon_ya5);
            specialDrawable[5] = ContextCompat.getDrawable(context, R.mipmap.icon_ya6);
            specialDrawable[6] = ContextCompat.getDrawable(context, R.mipmap.icon_ya7);
    }

    public void setStartPos(int[] startPos) {
        this.startPos = startPos;
    }

    public synchronized void start() {
        if (workingTask == null) {
            workingTask = new WorkingTask();
        }
        running = true;
        workingTask.start();
    }

    public synchronized void clear() {
        typeStash.clear();
        for (Animator animator : animators) {
            animator.cancel();
        }
    }

    public synchronized void stop() {
        running = false;
        imageViewPools.shutdown();
        if (workingTask != null) {
            workingTask.interrupt();
        }
        for (Animator animator : animators) {
            animator.cancel();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        cWidth = MeasureSpec.getSize(widthMeasureSpec);
        cHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    private AnimatorSet getAnimatorSet(final ImageView view) {
        // 创建动画
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator trax = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1f);
        ObjectAnimator tray = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1f);

        // 三个动画一起执行
        AnimatorSet enterSet = new AnimatorSet();
        enterSet.setDuration(mPDuration);
        enterSet.playTogether(trax, tray, alpha);

        // 创建贝塞尔动画
        ValueAnimator bezierAnimator = getBezierAnimator(view);

        // 所有动画一起执行
        set.playTogether(enterSet, bezierAnimator);
        set.setTarget(view);

        animators.add(set);

        // 给动画添加一个执行的状态监听,当动画执行结束的时候把view释放掉.
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                removeView(view);
                view.setVisibility(GONE);
                animators.remove(animation);
                imageViewPools.releaseImageView(view);
            }
        });
        return set;
    }

    /**
     * 添加一个View.
     */
    public void addBezierView(int type) {
        if(anchorMode){
            return;
        }
        try {
            if (typeStash.size() <= 500) {
                typeStash.put(type);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取贝塞尔动画
     *
     * @param view
     * @return
     */
    private ValueAnimator getBezierAnimator(final ImageView view) {

        // 初始化贝塞尔动画的几个点

        PointF pointF0 = new PointF(startPos[0] - view.getLayoutParams().width / 2, startPos[1] - view.getLayoutParams().height / 2);
        PointF pointF1 = getTogglePointF(1, view);
        PointF pointF2 = getTogglePointF(2, view);
        PointF pointF3 = new PointF(mRandom.nextInt(cWidth), 0);

        // 贝塞尔动画的路径由 一个估值器来表示.
        // 获取一个估值器,估值器的点集为pointF1,pointF2;
        BezierEvaluator bezierEvaluator = new BezierEvaluator(pointF1, pointF2);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(bezierEvaluator,
                new FractionPointF(pointF0), new FractionPointF(pointF3));
        valueAnimator.setDuration(mBDuration);

        // 给动画添加一个动画的进度监听;在动画执行的过程中动态的改变view的位置;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                FractionPointF pointF = (FractionPointF) animation.getAnimatedValue();
                ViewCompat.setX(view, pointF.getX());
                ViewCompat.setY(view, pointF.getY());

                // 设置view的透明度,达到动画执行过程view逐渐透明效果;
                ViewCompat.setAlpha(view, 1 - pointF.getFraction());
            }
        });
        return valueAnimator;
    }

    /**
     * 生成不同的PointF;
     *
     * @param i 从上到下标记依次为1-....
     * @return
     */
    private PointF getTogglePointF(int i, ImageView view) {

        PointF pointF = new PointF();
        pointF.x = mRandom.nextInt(cWidth);
        float nextFloat = mRandom.nextFloat();
        float nextFloat2 = mRandom.nextFloat();

        if (nextFloat > 0.5)
            nextFloat /= 2;

        if (nextFloat2 < 0.5)
            nextFloat2 /= 0.5;

        if (i == 1) {
            pointF.y = (float) (cHeight * nextFloat);
        } else if (i == 2) {
            pointF.y = (float) ((cHeight - view.getLayoutParams().height) * nextFloat2);
        }
        return pointF;
    }


    private boolean running;

    private class WorkingTask extends Thread {
        @Override
        public void run() {
            while (running) {
                try {
                    int type = typeStash.take();
                    final ImageView view = imageViewPools.allocImageView();
                    if (view == null) {
                        break;
                    }

                    Drawable drawable = null;
                    if (type == TYPE_NORMAL) {
                        int nextInt = mRandom.nextInt(normalDrawable.length - 1);
                        drawable = normalDrawable[nextInt];
                    } else {
                        int nextInt = mRandom.nextInt(specialDrawable.length - 1);
                        drawable = specialDrawable[nextInt];
                    }

                    if (drawable != null) {//判空防止服务器没传相应的图片
                        ((Activity) getContext()).runOnUiThread(new AnimatUiThread(drawable, view));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private class AnimatUiThread implements Runnable {
        private Drawable drawable;
        private ImageView view;

        public AnimatUiThread(Drawable drawable, ImageView view) {
            this.drawable = drawable;
            this.view = view;
        }

        @Override
        public void run() {
            if (running) {
                view.setImageDrawable(drawable);
//                addView(view);
                view.setVisibility(VISIBLE);
                AnimatorSet animatorSet = getAnimatorSet(view);
                animatorSet.setInterpolator(interpolators[mRandom.nextInt(interpolators.length - 1)]);
                animatorSet.start();
            }
        }
    }
}
