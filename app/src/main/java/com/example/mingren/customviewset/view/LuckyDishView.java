package com.example.mingren.customviewset.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.example.mingren.customviewset.Ob.LuckyDishArc;
import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by MingRen on 2016/9/21.
 */
public class LuckyDishView extends View implements View.OnClickListener {

    private Paint mPaint;
    private int centerX;
    private int centerY;
    private List<LuckyDishArc> luckyDishArcList = new ArrayList<>();
    private RectF dishRectF;
    private RectF TextRectF;
    private Bitmap nodeBitmap;
    private ValueAnimator rotateValueAnimator;
    private int randomAngle;
    private int mRadius;
    private int prizeBitmapWidth = 100;

    private int[] arrPic = {R.mipmap.iphone, R.mipmap.meizu, R.mipmap.huawei, R.mipmap.macbook, R.mipmap.xiaomi, R.mipmap.image_one};
    private String[] arrName = {"iphone7", "魅族", "华为", "macbook", "小米", "谢谢惠顾"};

    private int itemCount = arrName.length;
    private int textEachArcLength;
    private GestureDetector mGestureDetector;
    private double startAtan;
    private int startX;
    private int startY;
    private int getScaledTouchSlop;
    private int startAngle;

    public LuckyDishView(Context context) {
        super(context);
        init();
    }

    public LuckyDishView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setDither(true);

        nodeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.node);

        initArc();
        intRotateAnimator();

        //mGestureDetector = new GestureDetector(getContext(), new GestureListenerImpl());
        getScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        setOnClickListener(this);
    }

    private void initArc() {
        for (int i = 0; i < itemCount; i++) {
            LuckyDishArc luckyDishArc = new LuckyDishArc();
            luckyDishArc.color = createRandomColor();
            luckyDishArc.name = arrName[i];
            luckyDishArc.angle = luckyDishArc.OrignalAngle = i * (360 / itemCount);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), arrPic[i], options);
            options.inSampleSize = options.outWidth / prizeBitmapWidth;
            Log.e("sampleSize", options.inSampleSize + "");
            Log.e("outWidth", options.outWidth + "");
            options.inJustDecodeBounds = false;
            luckyDishArc.prizeBitmap = BitmapFactory.decodeResource(getResources(), arrPic[i], options);
            luckyDishArcList.add(luckyDishArc);//添加到集合
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawArc(canvas);
        drawPrizeBitmap(canvas);
        drawTextOnPath(canvas);
        canvas.drawBitmap(nodeBitmap, centerX - nodeBitmap.getWidth() / 2,
                centerY - nodeBitmap.getHeight() / 2, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        dishRectF = new RectF(0, 0, getWidth(), getHeight());//放这里防止多次new对象
        int textRectSize = getWidth() * 3 / 4;

        //文字弧长所在的矩形
        TextRectF = new RectF(centerX - textRectSize / 2, centerY - textRectSize / 2, centerX + textRectSize / 2, centerY + textRectSize / 2);
        textEachArcLength = (int) (Math.PI * textRectSize / itemCount);//每一项文字弧长
    }


    private void drawPrizeBitmap(Canvas canvas) {
        for (LuckyDishArc arc : luckyDishArcList) {
            //Math.cos(),Math.sin()传的都是弧度，不是角度
            double radian = -2 * Math.PI * (arc.angle + 360 / itemCount / 2) * 1.0 / 360;//对应的弧度值，顺时针为负

            int topLeftX = centerX + (int) (mRadius / 2 * Math.cos(radian)) - prizeBitmapWidth / 2;//cos为正，在centerX的右边，应该增加
            int topRightY = centerY - (int) (mRadius / 2 * Math.sin(radian)) - prizeBitmapWidth / 2;//sin为正，在centerY的上面，应该减少

            canvas.drawBitmap(arc.prizeBitmap, topLeftX, topRightY, mPaint);
        }
    }

    private void drawArc(Canvas canvas) {
        for (int i = 0; i < luckyDishArcList.size(); i++) {
            mPaint.setColor(luckyDishArcList.get(i).color);
            canvas.drawArc(dishRectF, luckyDishArcList.get(i).angle, 360 / itemCount, true, mPaint);
        }
    }


    private void drawTextOnPath(Canvas canvas) {
        for (LuckyDishArc arc : luckyDishArcList) {
            Path path = new Path();
            path.addArc(TextRectF, arc.angle, 360 / itemCount);
            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize(30);
            //测量出文字长度
            float textLength = mPaint.measureText(arc.name);
            //弧长减去文字长度/2等于修正值的偏移量
            canvas.drawTextOnPath(arc.name, path, (textEachArcLength - textLength) / 2, 0, mPaint);
        }
    }

    private int createRandomColor() {
        Random random = new Random();
        int alpha = 255;// 设置透明度
        int red = random.nextInt(190) + 30;// 30-220
        int green = random.nextInt(190) + 30;
        int blue = random.nextInt(190) + 30;
        int argb = Color.argb(alpha, red, green, blue);
        return argb;
    }

    private void intRotateAnimator() {
        rotateValueAnimator = ValueAnimator.ofFloat(0, 1);
        rotateValueAnimator.setInterpolator(new LinearInterpolator());
        rotateValueAnimator.addUpdateListener(updateListener);
        rotateValueAnimator.setDuration(3000);
        rotateValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    private void playRotate() {
        //每次转盘转动随机一个指定范围的角度l
        randomAngle = randomAngle();
        rotateValueAnimator.start();
    }

    private int randomAngle() {
        Random random = new Random();
        return 3600 + random.nextInt(360);
    }

    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float fraction = animation.getAnimatedFraction();
            for (LuckyDishArc arc : luckyDishArcList) {
                arc.angle = arc.OrignalAngle + randomAngle * fraction;//每次播放
                if (fraction == 1) {
                    arc.OrignalAngle = arc.angle;//动画完成，更新OrignalAngle否则用的还是最开始的，会发生抖动
                    if (arc.angle % 360 <= 270 && arc.angle % 360 + 360 / itemCount >= 270) {//因为画圆弧按顺时针方向，这里按瞬时针方向算，假设go对应的位置是270度
                        if (arrName[itemCount - 1].equals(arc.name)) {
                            Toast.makeText(getContext(), "很遗憾你未中奖", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "恭喜你获得" + arc.name + "一部", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            invalidate();
        }

    };

    @Override
    public void onClick(View v) {
        if (!rotateValueAnimator.isRunning()) playRotate();
    }


    /*------------------------------手势监听------------------------------*/
//    private void updateDishAngle(int angle) {
//        System.out.println("updateangle" + angle);
//        for (LuckyDishArc arc : luckyDishArcList) {
//            arc.angle = arc.OrignalAngle + angle;
//        }
//        invalidate();
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //return mGestureDetector.onTouchEvent(event);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startX = (int) event.getRawX();
//                startY = (int) event.getRawY();
//                startAtan = Math.atan((startY - 300) * 1.0f / (startX - 300));
//                startAngle = (int) (57.3 * startAtan);
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                int moveX = (int) event.getRawX();
//                int moveY = (int) event.getRawX();
//                System.out.println("moveX" + moveX);
//                System.out.println("moveY" + moveY);
//                if (isMove(event)) {
//                    double moveAtan = Math.atan((moveY - 300) * 1.0f / (moveX - 300));
//                    int moveAngle = (int) (57.3 * moveAtan) - startAngle;
//                    updateDishAngle(moveAngle);
//                }
//
//                return true;
//            case MotionEvent.ACTION_UP:
//                for (LuckyDishArc arc : luckyDishArcList) {
//                    arc.OrignalAngle = arc.angle;
//                }
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
//
//
//    private boolean isMove(MotionEvent event) {
//        int moveX = (int) event.getRawX();
//        int moveY = (int) event.getRawY();
//        if (Math.abs(moveX - startX) > getScaledTouchSlop || Math.abs(moveY - startY) > getScaledTouchSlop) {
//            return true;
//        }
//        return false;
//    }
//
//    private class GestureListenerImpl implements GestureDetector.OnGestureListener {
//
//        @Override
//        public boolean onDown(MotionEvent e) {
//            System.out.println("onDown");
//            return false;
//        }
//
//        @Override
//        public void onShowPress(MotionEvent e) {
//            System.out.println("onShowPress");
//        }
//
//        @Override
//        public boolean onSingleTapUp(MotionEvent e) {
//            System.out.println("onSingleTapUp");
//            return false;
//        }
//
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            System.out.println("onScroll");
//            return false;
//        }
//
//        @Override
//        public void onLongPress(MotionEvent e) {
//            System.out.println("onLongPress");
//        }
//
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            System.out.println("onFling");
//            return false;
//        }
//    }
}
