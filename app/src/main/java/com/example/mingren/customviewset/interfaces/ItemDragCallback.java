package com.example.mingren.customviewset.interfaces;

/**
 * Created by vincent on 2016/10/26.
 * email-address:674928145@qq.com
 */

public interface ItemDragCallback {

    void onItemMove(int oldPosition, int targetPosition);

    void onItemSwiped(int position);
}
