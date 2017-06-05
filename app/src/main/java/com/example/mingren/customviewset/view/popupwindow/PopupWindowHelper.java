package com.example.mingren.customviewset.view.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.mingren.customviewset.R;

/**
 * Created by vincent on 2015/8/3.
 */
public class PopupWindowHelper {
    private View popupView;
    private PopupWindow mPopupWindow;
    private Context context;
    private PopupWindow.OnDismissListener onDismissListener;
    private boolean dimbackground = true;
    private boolean cancelable = true;
    private int width = ViewGroup.LayoutParams.MATCH_PARENT;
    private int height = ViewGroup.LayoutParams.WRAP_CONTENT;

    public PopupWindowHelper(Context context, View view) {
        this.context = context;
        popupView = view;
    }

    public PopupWindowHelper(Context context, View popupView, int width, int height) {
        this.context = context;
        this.popupView = popupView;
        this.width = width;
        this.height = height;
    }

    /**
     * 统一初始化入口
     */
    public void initPopupWindow() {
        mPopupWindow = new PopupWindow(popupView, width, height);
        mPopupWindow.setOnDismissListener(internalOnDismissListener);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelableInternal(cancelable);
    }

    public void showAsDropDown(View anchor) {
        showAsDropDown(anchor, 0, 0);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        initPopupWindow();
        mPopupWindow.showAsDropDown(anchor, xoff, yoff);
    }

    public void showAsPopUp(View anchor) {
        showAsPopUp(anchor, 0, 0);
    }

    public void showAsPopUp(final View anchor, final int xoff, final int yoff) {
        initPopupWindow();
        mPopupWindow.setAnimationStyle(R.style.AnimationUpPopup);
        popupView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                popupView.getViewTreeObserver().removeOnPreDrawListener(this);
                mPopupWindow.dismiss();

                int height = popupView.getMeasuredHeight();
                int[] location = new int[2];
                anchor.getLocationInWindow(location);
                if (dimbackground) {
                    backgroundAlpha(0.7F, true);
                }

                mPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.TOP, location[0] + xoff, location[1] - height + yoff);
                return true;
            }
        });

        mPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    public void dismiss() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }


    public void setDimbackground(boolean dimbackground) {
        this.dimbackground = dimbackground;
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        initPopupWindow();
        mPopupWindow.showAtLocation(parent, gravity, x, y);
    }

    public void showAsPopUpCenter(View anchor) {
        showAsPopUpCenter(anchor, 0, 0);
    }

    public void showAsPopUpCenter(final View anchor, final int xoff, final int yoff) {
        initPopupWindow();
        mPopupWindow.setAnimationStyle(R.style.AnimationUpPopup);

        popupView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                popupView.getViewTreeObserver().removeOnPreDrawListener(this);
                mPopupWindow.dismiss();

                int height = popupView.getMeasuredHeight();
                int width = popupView.getMeasuredWidth();
                int[] location = new int[2];
                anchor.getLocationInWindow(location);
                if (dimbackground) {
                    backgroundAlpha(0.7F, true);
                }

                mPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.TOP, location[0] - width / 2 + xoff, location[1] - height + yoff);
                return true;
            }
        });

        mPopupWindow.showAtLocation(anchor, Gravity.LEFT | Gravity.TOP, 0, 0);
    }

    public void showFromBottom(View parent) {
        initPopupWindow();
        if (dimbackground) {
            backgroundAlpha(0.7F, true);
        }
        mPopupWindow.setAnimationStyle(R.style.AnimationFromBottom);
        mPopupWindow.showAtLocation(parent, Gravity.LEFT | Gravity.BOTTOM, 0, 0);
    }

    public void showFromTop(View parent) {
        initPopupWindow();
        if (dimbackground) {
            backgroundAlpha(0.7F, true);
        }
        mPopupWindow.setAnimationStyle(R.style.AnimationFromTop);
        mPopupWindow.showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 0, getStatusBarHeight());
    }

    /**
     * touch outside dismiss the popupwindow, default is ture
     *
     * @param isCancelable
     */
    private void setCancelableInternal(boolean isCancelable) {
        if (isCancelable) {
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
        } else {
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setFocusable(false);
        }
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha, boolean in) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
        if (in) {
            ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    private int getStatusBarHeight() {
        return Math.round(25 * Resources.getSystem().getDisplayMetrics().density);
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    private PopupWindow.OnDismissListener internalOnDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            if (dimbackground) {
                backgroundAlpha(1.0f, false);
            }

            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
        }
    };
}
