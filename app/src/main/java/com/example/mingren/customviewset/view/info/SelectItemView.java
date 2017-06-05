package com.example.mingren.customviewset.view.info;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.StringUtils;

/**
 * Created by vincent on 2017/5/31.
 */

public class SelectItemView extends FrameLayout implements View.OnClickListener {

    private TextView tvTitle;
    private RecyclerView rcvList;


    public SelectItemView(Context context) {
        this(context, null);
    }

    public SelectItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_info_select_pop_window, this, true);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rcvList = (RecyclerView) findViewById(R.id.rcv_select_list);
        rcvList.setLayoutManager(new LinearLayoutManager(getContext()));
        findViewById(R.id.tv_close).setOnClickListener(this);
    }

    public void setData(String title, RecyclerView.Adapter adapter) {
        if (StringUtils.isEmpty(title)) return;
        tvTitle.setText(title);
        rcvList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_close) {
            if (operateListener != null) operateListener.onClose();
        }
    }

    public interface OperateListener {
        void onSelect(String itemName);

        void onClose();
    }

    private OperateListener operateListener;

    public void setOnOperateListener(OperateListener listener) {
        this.operateListener = listener;
    }
}
