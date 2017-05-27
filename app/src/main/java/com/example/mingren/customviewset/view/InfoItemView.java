package com.example.mingren.customviewset.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mingren.customviewset.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vincent on 2017/5/25.
 */

public class InfoItemView extends RelativeLayout implements View.OnClickListener{

    @Bind(R.id.tv_item_name)
    TextView tv_item_name;
    @Bind(R.id.et_input)
    TextView et_input;
    @Bind(R.id.tv_input_postfix)
    TextView tv_input_postfix;
    @Bind(R.id.iv_arrow)
    TextView iv_arrow;
    private String sName;
    private String sHint;
    private String sPostfix;
    private boolean has_arrow;
    private boolean clickable;
    private int maxLength;


    public InfoItemView(Context context) {
        this(context,null);
    }

    public InfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
        initView(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InfoItemView);
        sName = a.getString(R.styleable.InfoItemView_item_name);
        sHint = a.getString(R.styleable.InfoItemView_input_hint);
        sPostfix = a.getString(R.styleable.InfoItemView_input_postfix);
        maxLength = a.getInt(R.styleable.InfoItemView_input_max_length, 0);
        has_arrow = a.getBoolean(R.styleable.InfoItemView_has_arrow, false);
        clickable = a.getBoolean(R.styleable.InfoItemView_clickable, false);
        a.recycle();
    }
    private void initView(Context context) {
        View.inflate(context, R.layout.view_info_item, this);
        ButterKnife.bind(this);
        tv_item_name.setText(sName);
        if(maxLength!=0)//限定输入字符数
        et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        et_input.setHint(sHint);
        tv_input_postfix.setText(sPostfix);
        iv_arrow.setVisibility(has_arrow?View.VISIBLE:View.GONE);
        if(clickable) setOnClickListener(this);
    }

    public void setInputText(String text) {
        et_input.setText(text);
    }

    public String getInputText() {
        return et_input.getText()==null?"":et_input.getText().toString().trim();
    }


    @Override
    public void onClick(View v) {

    }
}
