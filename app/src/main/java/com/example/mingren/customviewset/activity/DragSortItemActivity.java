package com.example.mingren.customviewset.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.DataUtil;
import com.example.mingren.customviewset.adapter.DragMessageItemAdapter;
import com.example.mingren.customviewset.interfaces.MessageItemTouchCallback;

/**
 * 实现了滑动拖拽排序效果
 * fixme：某些情况下会出现重复的条目
 */
public class DragSortItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_sort_item);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_test);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DragMessageItemAdapter adapter = new DragMessageItemAdapter(DataUtil.getQQMessageList());
        ItemTouchHelper helper = new ItemTouchHelper(new MessageItemTouchCallback(adapter));
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

    }
}
