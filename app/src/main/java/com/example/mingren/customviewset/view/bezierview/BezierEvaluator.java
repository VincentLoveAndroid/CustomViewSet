package com.example.mingren.customviewset.view.bezierview;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class BezierEvaluator implements TypeEvaluator<FractionPointF> {
    private PointF pointF1;
    private PointF pointF2;

    public BezierEvaluator(PointF pointF1, PointF pointF2) {

        this.pointF1 = pointF1;
        this.pointF2 = pointF2;

    }

    @Override
    public FractionPointF evaluate(float fraction, FractionPointF pointF0, FractionPointF pointF3) {
        FractionPointF pointF = new FractionPointF();
        //贝塞尔曲线的实现.根据贝塞尔曲线的公式得到x.y的值
        float x = (float) ((pointF0.getX() * (Math.pow((1 - fraction), 3))) + 3
                * pointF1.x * fraction * (Math.pow((1 - fraction), 2)) + 3
                * pointF2.x * (Math.pow(fraction, 2) * (1 - fraction)) + pointF3.getX()
                * (Math.pow(fraction, 3)));

        float y = (float) ((pointF0.getY() * (Math.pow((1 - fraction), 3))) + 3
                * pointF1.y * fraction * (Math.pow((1 - fraction), 2)) + 3
                * pointF2.y * (Math.pow(fraction, 2) * (1 - fraction)) + pointF3.getY()
                * (Math.pow(fraction, 3)));
        pointF.set(x, y);
        pointF.setFraction(fraction);
        return pointF;
    }
}
