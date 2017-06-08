package com.example.mingren.customviewset.view.info;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.ToastUtils;
import com.example.mingren.customviewset.activity.info.ItemWatcher;

import java.io.PipedReader;

/**
 * Created by vincent on 2017/6/6.
 */

public class HomePhoneItemView extends FrameLayout implements ItemWatcher {
    private EditText et_area_code;
    private EditText et_home_phone;

    public HomePhoneItemView(@NonNull Context context) {
        this(context, null);
    }

    public HomePhoneItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.view_home_phone_item, this);
        et_area_code = (EditText) findViewById(R.id.et_area_code);
        et_home_phone = (EditText) findViewById(R.id.et_home_phone);
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(et_area_code.getText()) && !TextUtils.isEmpty(et_home_phone.getText());
    }

    @Override
    public boolean showEmptyTip() {
        if (TextUtils.isEmpty(et_area_code.getText())) {
            ToastUtils.showToast(getContext(),"请输入区号");
            return true;
        }
        if (TextUtils.isEmpty(et_home_phone.getText())) {
            ToastUtils.showToast(getContext(),"请输入电话号码");
            return true;
        }
        return false;
    }

    public String getHomePhone() {
        return et_area_code.getText().toString().trim() + "-" + et_home_phone.getText().toString().trim();
    }
}
