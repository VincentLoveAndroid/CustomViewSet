package com.example.mingren.customviewset;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mingren.customviewset.Utils.ScreenUtils;
import com.example.mingren.customviewset.view.NestedScrollingListView;

import java.util.TreeMap;

public class MainActivity extends Activity {

    private CoordinatorLayout coordinatorLayout;
    private float startX;
    private float startY;
    private int l;
    private int t;
    private int scaledTouchSlop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CollapsingToolbarLayout cll = (CollapsingToolbarLayout) findViewById(R.id.cdl_test);
        cll.setExpandedTitleColor(Color.BLACK);
        cll.setExpandedTitleColor(Color.WHITE);
        //coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cdl_test);
        RecyclerView rcv = (RecyclerView) findViewById(R.id.rcv_test);
        LinearLayoutManager lm = new LinearLayoutManager(MainActivity.this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setLayoutManager(lm);

        rcv.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView tv = new TextView(MainActivity.this);
                return new MyViewHolder(tv);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                TextView tv = (TextView) holder.itemView;
                tv.setText("哈哈" + position);
            }

            @Override
            public int getItemCount() {
                return 100;
            }
        });


        final int statusBarHeight = ScreenUtils.getStatusBarHeight(this);

        scaledTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
//        tvDependency.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        startX = event.getX();
//                        startY = event.getY();
//                        System.out.println("startX" + startX);
//                        System.out.println("startY" + startY);
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        if (isMove(event)) {
//                            l = (int) (event.getRawX() - startX);
//                            t = (int) (event.getRawY() - startY - statusBarHeight);
//                            tvDependency.layout(l, t, l + tvDependency.getWidth(), t + tvDependency.getHeight());
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        break;
//                }
//                return true;
//            }
//        });
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private boolean isMove(MotionEvent event) {
        int moveX = (int) event.getX();
        int moveY = (int) event.getY();
        if (Math.abs(moveX - startX) > scaledTouchSlop / 2 || Math.abs(moveY - startY) > scaledTouchSlop / 2) {
            return true;
        }
        return false;
    }
}
