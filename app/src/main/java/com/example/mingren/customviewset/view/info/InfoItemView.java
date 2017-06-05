package com.example.mingren.customviewset.view.info;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.view.popupwindow.PopupWindowHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vincent on 2017/5/25.
 */

public class InfoItemView extends FrameLayout implements View.OnClickListener, TextWatcher, SelectItemView.OperateListener {

    @Bind(R.id.tv_item_name)
    TextView tv_item_name;
    @Bind(R.id.et_input)
    TextView et_input;
    @Bind(R.id.tv_input_postfix)
    TextView tv_input_postfix;
    @Bind(R.id.iv_arrow)
    ImageView iv_arrow;
    private String sName;
    private String sHint;
    private String sPostfix;
    private boolean has_arrow;
    private boolean clickable;
    private boolean show_key_board;
    private String itemTitle;
    private int maxLength;
    List<InfoSelectBean> itemList = new ArrayList<>();
    private CharSequence[] items;
    private PopupWindowHelper popupWindowHelper;


    public InfoItemView(Context context) {
        this(context, null);
    }

    public InfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
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
        show_key_board = a.getBoolean(R.styleable.InfoItemView_show_key_board, true);
        itemTitle = a.getString(R.styleable.InfoItemView_select_item_title);
        items = a.getTextArray(R.styleable.InfoItemView_select_item_arr);
        a.recycle();
    }

    private void initItemList() {
        if (items == null || items.length == 0) return;
        for (CharSequence s : items) {
            InfoSelectBean bean = new InfoSelectBean(false, s.toString());
            itemList.add(bean);
            System.out.println(s);
        }
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.view_info_item, this);
        ButterKnife.bind(this);
        tv_item_name.setText(sName);
        if (maxLength != 0)//限定输入字符数
            et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        et_input.setHint(sHint);
        et_input.addTextChangedListener(this);
        if (!show_key_board) {
//            et_input.setInputType(InputType.TYPE_NULL);
            setOnClickListener(this);
        }
        tv_input_postfix.setText(sPostfix);
        iv_arrow.setVisibility(has_arrow ? View.VISIBLE : View.GONE);
        if (clickable) setOnClickListener(this);

        initItemList();
    }

    public void setInputText(String text) {
        et_input.setText(text);
    }

    public String getInputText() {
        return et_input.getText() == null ? "" : et_input.getText().toString().trim();
    }

    public boolean isCompleted() {
        return getInputText().isEmpty();
    }


    @Override
    public void onClick(View v) {
//        View popupView=View.inflate(getContext(), R.layout.view_info_select_pop_window, null);
//        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.view_info_select_pop_window,,false);
        showSelectPopupWindow();

    }

    private void showSelectPopupWindow() {
        SelectItemView popupView = new SelectItemView(getContext());
        popupView.setOnOperateListener(this);
        ShadeItemAdapter adapter = new ShadeItemAdapter(itemList,this);
        popupView.setData(itemTitle, adapter);
        if (popupWindowHelper == null) {
            popupWindowHelper = new PopupWindowHelper(getContext(), popupView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        }
        popupWindowHelper.showAsDropDown(this);
    }

    public void dismissPopupWindow() {
        if (popupWindowHelper != null) popupWindowHelper.dismiss();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Toast.makeText(getContext(), "填充完成--" + getInputText() + "-text Empty-" + isCompleted(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSelect(String itemName) {
        dismissPopupWindow();
        et_input.setText(itemName);
    }

    @Override
    public void onClose() {
        dismissPopupWindow();
    }
}
