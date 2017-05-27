package com.example.mingren.customviewset.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.view.tkRefreshLayout.RefreshActivity;

/**
 * Created by vincent on 2017/5/25.
 * 显示选中凹下阴影效果的item
 */

public class ShadeItemView extends RelativeLayout implements View.OnClickListener{

    private Paint mPaint;
    private boolean isSelected;

    public ShadeItemView(Context context) {
        this(context,null);
    }

    public ShadeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_info_select_item, this, true);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(context,R.color.gray4_color_1));

        setWillNotDraw(false);//清除ViewGroup不绘制的标志

        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isSelected) {
            canvas.drawRect(0,0,getMeasuredWidth(),10,mPaint);
            canvas.drawRect(0,0,10,getMeasuredHeight(),mPaint);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
        postInvalidate();
    }

    @Override
    public void onClick(View v) {
        if(isSelected)setSelected(false);
        else setSelected(true);
    }
}
