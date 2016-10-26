package com.example.mingren.customviewset.adapter;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by vincent on 2016/10/25.
 * email-address:674928145@qq.com
 */

public class MessageItemTouchCallback extends ItemTouchHelper.Callback {

    public static final int RESET = 1;
    public static final int RESET1 = 1 << 2;
    public static final int RESET2 = 1 << 3;

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int movementFlags = makeMovementFlags(dragFlags, swipeFlags);
        return movementFlags;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //recyclerView.getA
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
