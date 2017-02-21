package com.example.mingren.customviewset.view.tkRefreshLayout;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.adapter.ScrollTabAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

public class RefreshActivity extends AppCompatActivity {

    private static final String TAG = "vincent";
    private ScrollTabAdapter scrollTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        TwinklingRefreshLayout refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh);

        final HomepageFooterView footerView = new HomepageFooterView(this);

        refreshLayout.setBottomView(footerView);
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setAutoLoadMore(true);


        RecyclerView rvTest = (RecyclerView) findViewById(R.id.rcv_test);

        rvTest.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                footerView.showGettingData();
            }

            @Override
            public void onLoadmoreCanceled() {
                super.onLoadmoreCanceled();
            }

            @Override
            public void onFinishLoadMore() {
                super.onFinishLoadMore();
            }
        });
//        rvTest.addOnScrollListener(new OnRcvScrollListener() {
//            @Override
//            public void onLastItemVisible() {
//                Log.i(TAG, "onLastItemVisible: " + "移到地步啦");
//            }
//        });
        scrollTabAdapter = new ScrollTabAdapter(this);
        rvTest.setAdapter(scrollTabAdapter);


    }
}
