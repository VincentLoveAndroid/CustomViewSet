package com.example.mingren.customviewset.interfaces;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by vincent on 2016/10/25.
 * email-address:674928145@qq.com
 * description:处理拖拽和滑动删除的回调
 */

public class MessageItemTouchCallback extends ItemTouchHelper.Callback {

    private ItemDragCallback itemDragCallback;

    public MessageItemTouchCallback(ItemDragCallback itemDragCallback) {
        this.itemDragCallback = itemDragCallback;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int movementFlags = makeMovementFlags(dragFlags, swipeFlags);
        return movementFlags;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        itemDragCallback.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        // System.out.println("move" + viewHolder.getAdapterPosition() + " " + target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        itemDragCallback.onItemSwiped(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;//支持长按拖拽
    }
}
