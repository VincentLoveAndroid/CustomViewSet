package com.example.mingren.customviewset.helper;

/**
 * Created by vincent on 2017/2/25.
 * email-address:674928145@qq.com
 * description:
 */

public class ListenerHelper {
    private static ListenerHelper listenerHelper;

    public static ClickListener clickListener;

    public static ListenerHelper getInstance() {
        if (listenerHelper == null) {
            listenerHelper = new ListenerHelper();
        }
        return listenerHelper;
    }


    public void doClick(ClickListener listener) {
        clickListener = listener;
    }
    public interface ClickListener {
        void doClick();
    }
}
