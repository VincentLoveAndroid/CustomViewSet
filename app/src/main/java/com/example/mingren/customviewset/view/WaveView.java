package com.example.mingren.customviewset.view;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;

import com.example.mingren.customviewset.Ob.Wave;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by MingRen on 2016/9/18.
 * 水波纹效果，波纹颜色随机
 */
public class WaveView extends View {

    private boolean playWave;
    private Paint mPaint;
    private int mcx;
    private int mcy;
    private List<Wave> waveList = new CopyOnWriteArrayList<>();
    private int invalideDuration = 16;//16ms刷新一次 相当于60fps
    private long lastPlayTime;//记录上次播放的时间
    private TimeInterpolator mTimeInterplator=new LinearInterpolator();

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                mcx = getWidth() / 2;//获取中心点
                mcy = getHeight() / 2;
                return false;
            }
        });

    }

    @Override
    protected void onDraw(final Canvas canvas) {

        if (playWave) {
            if (waveList.isEmpty()) return;
            for (Wave wave : waveList) {
                wave.setInterpolator(mTimeInterplator);
                wave.play(canvas, mPaint, mcx, mcy, waveList);
            }
            postDelayed(waveRuunable, invalideDuration);
        }
    }

    /**
     * 生产波纹
     */
    private void createWave() {
        Wave wave = new Wave();
        waveList.add(wave);
    }

    public void playWave() {
        if (System.currentTimeMillis() - lastPlayTime < 500) return;//控制间隔必须大于500ms
        lastPlayTime = System.currentTimeMillis();
        playWave = true;
        createWave();
        invalidate();
    }

    public void stopWave() {
        playWave = false;
        removeCallbacks(waveRuunable);
    }

    private Runnable waveRuunable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopWave();
    }
}
