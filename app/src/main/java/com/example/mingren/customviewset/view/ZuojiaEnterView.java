package com.example.mingren.customviewset.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.ImageUtil;
import com.example.mingren.customviewset.Utils.ScreenUtils;

import java.io.IOException;

/**
 * Created by vincent on 2016/12/16.
 * email-address:674928145@qq.com
 * description:直播间座驾进场展示
 */

public class ZuojiaEnterView extends RelativeLayout {
    private int mScreenWidth;

    private float exitSpeed = 0.47f;//每毫秒移动的像素
    private ImageView iv_enter_motoring;
    private ImageView iv_enter_star;
    private TextView tv_enter_tips;
    private RelativeLayout rl_enter_stage;

    private float tipsWidth;//提示宽
    private float stageWidth;//舞台宽
    private AnimatorSet showAnimatorSet;
    private ObjectAnimator showCarAnimator;
    private ObjectAnimator showTipAnimator;
    private ObjectAnimator exitCarAnimator;
    private ObjectAnimator exitTipAnimator;
    private AnimatorSet exitAnimatorSet;

    public ZuojiaEnterView(Context context) {
        super(context);
        init();
    }

    public ZuojiaEnterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mScreenWidth = ScreenUtils.getScreenWidth(getContext());

        View.inflate(getContext(), R.layout.view_zuojia_enter, this);
        iv_enter_motoring = (ImageView) findViewById(R.id.iv_enter_motoring);
        iv_enter_star = (ImageView) findViewById(R.id.iv_enter_star);
        tv_enter_tips = (TextView) findViewById(R.id.tv_enter_tips);
        rl_enter_stage = (RelativeLayout) findViewById(R.id.rl_enter_stage);
        setVisibility(INVISIBLE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        tipsWidth = tv_enter_tips.getMeasuredWidth();
        stageWidth = rl_enter_stage.getMeasuredWidth();
    }

    public void initMotoringMsg(String tips, int id) {

        cancelAnimator();

        tv_enter_tips.setText(tips);
        try {
            Bitmap motoringBitmap = ImageUtil.decodeBitmapFromStream(getResources(), getContext().getAssets().open("icon_car_test.png"), 320);
            iv_enter_motoring.setImageBitmap(motoringBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        show();
    }

    public void show() {
        iv_enter_star.setVisibility(View.INVISIBLE);

        showCarAnimator = getShowCarAnimator();
        showTipAnimator = getShowTipAnimator();
        exitCarAnimator = getCarExitAnimator();
        exitTipAnimator = getTipExitAnimator();

        exitCarAnimator.addListener(carExitAnimatorListener);
        showTipAnimator.addListener(tipAnimatorListener);

        exitAnimatorSet = new AnimatorSet();
        exitAnimatorSet.setStartDelay(1300);//星星闪1.3秒后再执行退场动画
        exitAnimatorSet.playTogether(exitCarAnimator, exitTipAnimator);

        showAnimatorSet = new AnimatorSet();
        showAnimatorSet.play(showCarAnimator);
        showAnimatorSet.play(showTipAnimator);
        showAnimatorSet.play(exitAnimatorSet).after(showTipAnimator);
        showAnimatorSet.start();

    }

    private void cancelAnimator() {
        if (showAnimatorSet != null) showAnimatorSet.cancel();
        if (exitAnimatorSet != null) showAnimatorSet.cancel();
        if (showCarAnimator != null) showCarAnimator.cancel();
        if (showTipAnimator != null) showTipAnimator.cancel();
        if (exitCarAnimator != null) exitCarAnimator.cancel();
        if (exitTipAnimator != null) exitTipAnimator.cancel();
    }

    @NonNull
    private ObjectAnimator getShowTipAnimator() {
        ObjectAnimator showTipAnimator = ObjectAnimator.ofFloat(tv_enter_tips, "translationX", -tipsWidth, 0);
        showTipAnimator.setDuration(400);
        showTipAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        return showTipAnimator;
    }

    @NonNull
    private ObjectAnimator getShowCarAnimator() {
        ObjectAnimator showCarAnimator = ObjectAnimator.ofFloat(rl_enter_stage, "translationX", -stageWidth, tipsWidth - stageWidth);
        showCarAnimator.setDuration(500);
        showCarAnimator.setStartDelay(200);
        showCarAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        return showCarAnimator;
    }

    @NonNull
    private ObjectAnimator getTipExitAnimator() {
        ObjectAnimator TipsAnimatorExit = ObjectAnimator.ofFloat(tv_enter_tips, "translationX", mScreenWidth);
        TipsAnimatorExit.setDuration((long) (mScreenWidth / exitSpeed));
        return TipsAnimatorExit;
    }

    @NonNull
    private ObjectAnimator getCarExitAnimator() {
        ObjectAnimator carAnimatorExit = ObjectAnimator.ofFloat(rl_enter_stage, "translationX", mScreenWidth + tipsWidth - stageWidth);
        carAnimatorExit.setDuration((long) (mScreenWidth / exitSpeed));
        return carAnimatorExit;
    }


    private AnimatorListenerAdapter carExitAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            iv_enter_star.setVisibility(View.INVISIBLE);
        }
    };

    private AnimatorListenerAdapter tipAnimatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationStart(Animator animation) {
            setVisibility(VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            iv_enter_star.setVisibility(View.VISIBLE);
            AnimationDrawable drawable = (AnimationDrawable) iv_enter_star.getDrawable();
            if (drawable != null && !drawable.isRunning()) drawable.start();
        }
    };
}
