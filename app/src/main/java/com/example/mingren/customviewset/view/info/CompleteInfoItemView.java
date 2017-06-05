package com.example.mingren.customviewset.view.info;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.StringUtils;

/**
 * Created by vincent on 2017/6/2.
 */

public class CompleteInfoItemView extends FrameLayout {

    private TextView tvName;
    private ImageView ivIcon;
    private ImageView ivIndicator;
    private TextView tvTip;
    private String sItemName;

    public CompleteInfoItemView(@NonNull Context context) {
        this(context,null);
    }

    public CompleteInfoItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        intAttrs(attrs);
        initView();
    }

    private void intAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CompleteInfoItemView);
        sItemName = a.getString(R.styleable.CompleteInfoItemView_complete_item_name);
        a.recycle();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_complete_info_item, this);
        tvName = (TextView) findViewById(R.id.tv_item_name);
        ivIcon = (ImageView) findViewById(R.id.iv_item_icon);
        ivIndicator = (ImageView) findViewById(R.id.iv_item_indicator);
        tvTip = (TextView) findViewById(R.id.tv_item_tip);

        if(!StringUtils.isEmpty(sItemName)) setsItemName(sItemName);
    }

    public void setsItemName(String name) {
        tvName.setText(name);
    }

    public void setItemTip(String name) {
        tvTip.setText(name);
    }

    public void setItemIcon(int resId) {
        ivIcon.setImageResource(resId);
    }

    public void setItemIndicator(int resId) {
        ivIndicator.setImageResource(resId);
    }
}
