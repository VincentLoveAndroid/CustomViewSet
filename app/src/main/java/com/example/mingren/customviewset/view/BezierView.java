package com.example.mingren.customviewset.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.example.mingren.customviewset.Ob.Bubble;
import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.DensityUtil;
import com.example.mingren.customviewset.adapter.BezierTypeEvaluator;
import com.example.mingren.customviewset.base.BaseCustomView;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Vincent on 2018/3/1.
 * 贝塞尔曲线实现直播间泡泡效果
 */
public class BezierView extends BaseCustomView {
    private int bubbleCount = 0;
    protected List<Bubble> bubbleList = new CopyOnWriteArrayList<>();
    protected List<Animator> animatorList = new CopyOnWriteArrayList<>();
    private int[] arrPic = {R.mipmap.icon_ya1, R.mipmap.icon_ya2, R.mipmap.icon_ya3, R.mipmap.icon_ya4,
            R.mipmap.icon_ya5, R.mipmap.icon_ya6, R.mipmap.icon_ya7};

    private Bubble p0;//起始点
    private Bubble p3;//起始点
    private Matrix mMatrix;


    public BezierView(Context context) {
        super(context);
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initSomething() {
        mMatrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        p0 = new Bubble(getRight() - DensityUtil.dip2px(getContext(), 50), getBottom() - DensityUtil.dip2px(getContext(), 20));
        p3 = new Bubble(getRight() - DensityUtil.dip2px(getContext(), 50), getTop() + DensityUtil.dip2px(getContext(), 30));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Bubble bubble : bubbleList) {
            mPaint.setAlpha(bubble.alpha);
            mMatrix.setScale(bubble.scaleX, bubble.scaleY); //长和宽放大缩小的比例，
            mMatrix.postTranslate(bubble.x, bubble.y);
            canvas.drawBitmap(bubble.picBitmap, mMatrix, mPaint);
//            //导致性能问题
//            try {
//                Bitmap bitmap = Bitmap.createBitmap(bubble.picBitmap, 0, 0, bubble.picBitmap.getWidth(), bubble.picBitmap.getHeight(), mMatrix, true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                bubbleCount++;

                Bubble bubble = new Bubble(500, 1000);
                Bubble p1 = new Bubble(event.getX(), event.getY());//手指点击的地方作为其中一个控制点
                System.out.println("X" + event.getX());
                System.out.println("Y" + event.getY());
                bubble.picBitmap = BitmapFactory.decodeResource(getResources(), arrPic[(bubbleCount - 1) % 7]);
                bubbleList.add(bubble);

                playBubble(bubble, p1);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void playBubble(final Bubble bubble, Bubble p1) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierTypeEvaluator(new PointF(p1.x, p1.y), new PointF(getRandom(), getRandom())), p0, p3);
        valueAnimator.addUpdateListener(new BubbleUpdateListener(bubble));
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(5000);
        valueAnimator.start();

        animatorList.add(valueAnimator);
    }


    private class BubbleUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        private WeakReference<Bubble> weakReference;//弱引用，防止内存泄露

        public BubbleUpdateListener(Bubble bubble) {
            weakReference = new WeakReference<>(bubble);
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            Bubble bubble = weakReference.get();
            Bubble bezierBubble = ((Bubble) animation.getAnimatedValue());
            bubble.x = bezierBubble.x;
            bubble.y = bezierBubble.y;
            bubble.scaleX = bezierBubble.scaleX;
            bubble.scaleY = bezierBubble.scaleY;
            bubble.alpha = bezierBubble.alpha;
            if (animation.getAnimatedFraction() == 1 && bubbleCount > 0) {
                bubbleList.remove(bubble);
                animatorList.remove(animation);
                bubbleCount--;
            }
            invalidate();
        }
    }

    private int getRandom() {
        Random random = new Random();
        return random.nextInt(500) + 200;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    private void stop() {
        for (Animator animator : animatorList) animator.cancel();
        for (int i = 0; i < bubbleList.size(); i++) {
            Bubble bubble = bubbleList.get(i);
            bubble.picBitmap = null;//退出清空对bitmap的引用
        }
        bubbleList.clear();
        animatorList.clear();
        bubbleCount = 0;
    }
}
