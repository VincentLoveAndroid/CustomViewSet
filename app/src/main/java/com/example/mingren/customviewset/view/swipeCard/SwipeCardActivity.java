package com.example.mingren.customviewset.view.swipeCard;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.ColorUtil;

public class SwipeCardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_swipe_card);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_test);
        SwipeLayoutManager swipeLayoutManager = new SwipeLayoutManager();
        recyclerView.setLayoutManager(swipeLayoutManager);
        SwipeCardAdapter swipeCardAdapter = new SwipeCardAdapter();
        swipeCardAdapter.setDataList();
        swipeLayoutManager.setItemRemoveListener(swipeCardAdapter);
        recyclerView.setAdapter(swipeCardAdapter);
    }
}
