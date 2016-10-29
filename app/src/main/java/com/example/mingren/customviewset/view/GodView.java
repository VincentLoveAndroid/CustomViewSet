package com.example.mingren.customviewset.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.mingren.customviewset.Utils.GeometryUtil;

/**
 * Created by vincent on 2016/10/19.
 * email-address:674928145@qq.com
 * 粘性控件
 */

public class GodView extends View {

    private Paint paint;
    private PointF mStickCenter;
    private PointF mDragCenter;
    private Float mStickRadius;
    private Float mDragRadius;
    private float poinfK;
    private PointF mControlPointF;
    private PointF[] mStickPointF;
    private PointF[] mDragPointF;
    private float dragX;
    private float dragY;
    private boolean isDisappeared;// 是否清楚画布
    private boolean isTogether;// 两圆是否重叠在一起
    private PointF orignalPointF;
    private int godRange;

    public GodView(Context context) {
        this(context, null);
    }

    public GodView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GodView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // 初始化画笔等属性
    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);

        mDragRadius = 18f;
        mStickRadius = 14f;
        mStickPointF = new PointF[2];
        mDragPointF = new PointF[2];
        // 注意一定要把这四个点new出来，不然直接mStickPointFs[0].x会空指针
        mStickPointF[0] = new PointF();
        mStickPointF[1] = new PointF();
        mDragPointF[0] = new PointF();
        mDragPointF[1] = new PointF();
        //控件原始的圆中心
        orignalPointF = new PointF(360, 360);
        //初始化的时候固定圆和拖拽圆同心
        mStickCenter = new PointF(orignalPointF.x, orignalPointF.y);
        mDragCenter = new PointF(orignalPointF.x, orignalPointF.y);
        //维持粘性连接的范围
        godRange = 340;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        poinfK = -1
                * 1.0f
                / ((mStickCenter.y - mDragCenter.y) * 1.0f / (mStickCenter.x - mDragCenter.x));
       /*--------------- 固定圆的四个关键点 ---------------*/

        mStickPointF[0].x = (float) Math.sqrt(Math.pow(mStickRadius, 2)
                / (poinfK * poinfK + 1))
                + mStickCenter.x;
        mStickPointF[1].x = -(float) Math.sqrt(Math.pow(mStickRadius, 2)
                / (poinfK * poinfK + 1))
                + mStickCenter.x;
        mStickPointF[0].y = -(float) Math.sqrt(Math.pow(mStickRadius, 2)
                / (1 / (poinfK * poinfK) + 1))
                + mStickCenter.y;
        mStickPointF[1].y = (float) Math.sqrt(Math.pow(mStickRadius, 2)
                / (1 / (poinfK * poinfK) + 1))
                + mStickCenter.y;
       /*---------------  拖拽圆的四个关键点 ---------------*/
        mDragPointF[0].x = (float) Math.sqrt(Math.pow(mDragRadius, 2)
                / (poinfK * poinfK + 1))
                + mDragCenter.x;
        mDragPointF[1].x = -(float) Math.sqrt(Math.pow(mDragRadius, 2)
                / (poinfK * poinfK + 1))
                + mDragCenter.x;
        mDragPointF[0].y = -(float) Math.sqrt(Math.pow(mDragRadius, 2)
                / (1 / (poinfK * poinfK) + 1))
                + mDragCenter.y;
        mDragPointF[1].y = (float) Math.sqrt(Math.pow(mDragRadius, 2)
                / (1 / (poinfK * poinfK) + 1))
                + mDragCenter.y;
       /*--------------- 控制点 ---------------*/
        mControlPointF = new PointF((mStickCenter.x + mDragCenter.x) / 2,
                (mStickCenter.y + mDragCenter.y) / 2);
        // 画出范围圆
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(360, 360, godRange, paint);
        // 通过标志位实现必要时候清空画布的功能
        if (!isDisappeared) {
            drawGodView(canvas);
        }
    }

    /**
     * @param canvas
     * @author vincentchan
     * @E-mail vincentchan@hyx.com
     * @version 2015-11-21 下午6:12:09
     * @Description 开始画粘性控件
     */
    private void drawGodView(Canvas canvas) {
        // 画连接部分
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Path path = new Path();
        path.moveTo(mStickPointF[0].x, mStickPointF[0].y);
        path.quadTo(mControlPointF.x, mControlPointF.y, mDragPointF[0].x,
                mDragPointF[0].y);
        path.lineTo(mDragPointF[1].x, mDragPointF[1].y);
        path.quadTo(mControlPointF.x, mControlPointF.y, mStickPointF[1].x,
                mStickPointF[1].y);
        path.close();// 有什么作用？
        canvas.drawPath(path, paint);
        // 拖拽圆
        canvas.drawCircle(mDragCenter.x, mDragCenter.y, mDragRadius, paint);
        // 固定圆
        canvas.drawCircle(mStickCenter.x, mStickCenter.y, mStickRadius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isDisappeared = false;// 重置可见性，再次点击可以出现
        mStickCenter.set(360, 360);// 重置位置
        dragX = event.getRawX();
        dragY = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println(dragX + ":" + dragY);
                if (dragX < 450 && dragX > 300 && dragY < 450 && dragY > 300)
                    return true;
                break;
            case MotionEvent.ACTION_MOVE:
                mDragCenter.set(dragX, dragY);
                mStickRadius = (float) (14f * (1 - Math
                        .sqrt((dragX - orignalPointF.x) * (dragX - orignalPointF.x)
                                + (dragY - orignalPointF.y)
                                * (dragY - orignalPointF.y))
                        * 1.0f / godRange));
                if (isTogether)
                    updateStickCenter(dragX, dragY);
                if ((dragX - orignalPointF.x) * (dragX - orignalPointF.x)
                        + (dragY - orignalPointF.y) * (dragY - orignalPointF.y) > godRange
                        * godRange) {
                    updateStickCenter(dragX, dragY);
                    isTogether = true;
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                float upX = event.getRawX();
                float upY = event.getRawY();
                if ((upX - orignalPointF.x) * (upX - orignalPointF.x)
                        + (upY - orignalPointF.y) * (upY - orignalPointF.y) < godRange
                        * godRange) {
                    // 固定圆和边线消失
                    // mStickCenter.set(360,360);
                    // mDragCenter.set(360, 360);
                    // c. 拖拽没超出范围, 松手,弹回去，属性动画
                    springAnimation();
                } else {
                    isDisappeared = true;
                }
                isTogether = false;
                invalidate();
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * @author vincentchan
     * @E-mail vincentchan@hyx.com
     * @version 2015-11-21 下午6:07:51
     * @Description 弹性动画
     */
    private void springAnimation() {
        final PointF tempDragCenter = new PointF(mDragCenter.x, mDragCenter.y);

        ValueAnimator mAnim = ValueAnimator.ofFloat(1.0f);
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator mAnim) {
                // 0.0 -> 1.0f
                float percent = mAnim.getAnimatedFraction();
                PointF p = GeometryUtil.getPointByPercent(tempDragCenter,
                        mStickCenter, percent);
                mDragCenter.set(p.x, p.y);
                invalidate();
            }
        });
        mAnim.setInterpolator(new OvershootInterpolator(4));
        mAnim.setDuration(500);
        mAnim.start();
    }

    private void updateStickCenter(float x, float y) {
        mStickCenter.set(x, y);
    }
}
