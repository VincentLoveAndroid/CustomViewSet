package com.example.mingren.customviewset.Ob;

/**
 * Created by vincent on 2016/10/19.
 * email-address:674928145@qq.com
 */

import android.animation.TimeInterpolator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.CoordinatorLayout;
import android.view.animation.LinearInterpolator;

import java.util.List;
import java.util.Random;

/**
 * Created by MingRen on 2016/9/18.
 */
public class Wave {
    public int radius = 20;//半径
    public int alpha = 255;//透明度
    public int color = createRandomColor();//颜色
    private long startTime;
    private int duration = 2000;
    private TimeInterpolator mTimeInterpolation = new LinearInterpolator();

    public void play(Canvas canvas, Paint paint, int mcx, int mcy, List waveList) {
        if (startTime == 0)
            startTime = System.currentTimeMillis();

        if (System.currentTimeMillis() - startTime > duration) {
            waveList.remove(this);
            return;
        }

        paint.setColor(getColor());
        paint.setAlpha(getAlpha());
        canvas.drawCircle(mcx, mcy, getRadius(), paint);
    }

    //设置插值器
    public void setInterpolator(TimeInterpolator interpolator) {
        mTimeInterpolation = interpolator;
    }

    //根据不同的插值器得到即时Fraction
    private float getFraction() {
        float fraction = (System.currentTimeMillis() - startTime) * 1.0f / duration;
        return mTimeInterpolation.getInterpolation(fraction);

    }

    public int getRadius() {
        radius = (int) (20 + getFraction() * (200 - 20));
        if (radius > 200) radius = 200;
        return radius;
    }

    public int getAlpha() {
        alpha = (int) (255 - getFraction() * 255);
        if (alpha < 0) alpha = 0;
        return alpha;
    }

    public int getColor() {
        return color;
    }

    /**
     * 产生随机颜色
     *
     * @return
     */
    private int createRandomColor() {
        Random random = new Random();
        int alpha = 255;// 设置透明度
        int red = random.nextInt(190) + 30;// 30-220
        int green = random.nextInt(190) + 30;
        int blue = random.nextInt(190) + 30;
        int argb = Color.argb(alpha, red, green, blue);
        return argb;

    }
}
