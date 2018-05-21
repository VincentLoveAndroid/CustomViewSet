package com.example.mingren.customviewset.view.bezierview;

import android.graphics.PointF;


public class FractionPointF {
    private PointF pointF;
    private float fraction;

    public FractionPointF(PointF pointF) {
        this.pointF = pointF;
    }

    public FractionPointF() {
        pointF = new PointF();
    }

    public void set(PointF p) {
        pointF.set(p);
    }

    public void set(float x, float y) {
        pointF.set(x, y);
    }

    public void setFraction(float fraction) {
        this.fraction = fraction;
    }

    public float getX(){
        return pointF.x;
    }

    public float getY(){
        return pointF.y;
    }

    public float getFraction() {
        return fraction;
    }
}
