package com.example.mingren.customviewset.activity.info;

/**
 * Created by vincent on 2017/6/5.
 * 检查item的状态，所有需要检查状态的自定义ItemView继承这个接口，便于拓展
 */

public interface ItemWatcher {
    boolean isCompleted();

    boolean showEmptyTip();
}
