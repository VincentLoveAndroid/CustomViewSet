package com.example.mingren.customviewset.activity.info;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vincent on 2017/6/9.
 */

public abstract class BaseSubmitInfoActivity extends Activity {

    ViewGroup container;

    protected boolean isAllItemFill() {
        if (!isAllInfoItemFill(false)) return false;
        isOtherItemFill();
        return true;
    }

    /**
     * 遍历检查InfoItem是否未填写
     */
    protected boolean isAllInfoItemFill(boolean showEmptyTip) {
        container = getContainer();
        if (container == null) {
            return false;
        }
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child.getVisibility() == View.VISIBLE && child instanceof ItemWatcher) {
                ItemWatcher watcher = ((ItemWatcher) child);
                if (!watcher.isCompleted()) {
                    if (showEmptyTip) watcher.showEmptyTip();
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean checkAllItemFillAndTips() {
        if (!isAllInfoItemFill(true)) return false;
        checkOtherItemFillAndTips();
        return true;
    }

    public abstract void submitData();

    public abstract ViewGroup getContainer();

    /**
     * 如需要，在这里检查没有实现ItemWatcher接口的控件
     */
    public boolean isOtherItemFill() {
        return true;
    }

    /**
     * 如需要，在这里检查没有实现ItemWatcher接口的控件并提示
     */
    public boolean checkOtherItemFillAndTips() {
        return true;
    }
}
