package com.example.mingren.customviewset.view.info;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.view.tkRefreshLayout.RefreshActivity;

/**
 * Created by vincent on 2017/5/25.
 * 显示选中凹下阴影效果的item
 */

public class ShadeItemView extends FrameLayout{

    private Paint mPaint;
    private boolean isSelected;
    private TextView tvName;

    public ShadeItemView(Context context) {
        this(context,null);
    }

    public ShadeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_info_select_item, this, true);
        tvName = (TextView) findViewById(R.id.tv_name);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(context,R.color.gray4_color_1));

        setWillNotDraw(false);//清除ViewGroup不绘制的标志

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isSelected) {
            canvas.drawRect(0,0,getMeasuredWidth(),10,mPaint);
            canvas.drawRect(0,0,10,getMeasuredHeight(),mPaint);
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        postInvalidate();
    }

    public void setName(String name) {
        tvName.setText(name);
    }

}
