package com.example.mingren.customviewset.adapter;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import com.example.mingren.customviewset.Ob.Bubble;

/**
 * Created by vincent on 2016/10/19.
 * email-address:674928145@qq.com
 */

public class BezierTypeEvaluator implements TypeEvaluator<Bubble> {

    private PointF p1;
    private PointF p2;

    public BezierTypeEvaluator(PointF p1, PointF p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public Bubble evaluate(float fraction, Bubble p0, Bubble p3) {

        Bubble bubble = new Bubble();
        //三阶贝塞尔曲线
        bubble.x = (float) (p0.x * Math.pow(1 - fraction, 3) + 3 * p1.x * fraction * Math.pow(1 - fraction, 2) + 3 *
                p2.x * Math.pow(fraction, 2) * (1 - fraction) + p3.x * Math.pow(fraction, 3));

        bubble.y = (float) (p0.y * Math.pow(1 - fraction, 3) + 3 * p1.y * fraction * Math.pow(1 - fraction, 2) + 3 *
                p2.y * Math.pow(fraction, 2) * (1 - fraction) + p3.y * Math.pow(fraction, 3));

        bubble.scaleX = (float) (0.5 + 0.5 * fraction);
        bubble.scaleY = (float) (0.5 + 0.5 * fraction);
        bubble.alpha = (int) (255 * Math.sin(Math.PI * fraction));

        return bubble;
    }
}
