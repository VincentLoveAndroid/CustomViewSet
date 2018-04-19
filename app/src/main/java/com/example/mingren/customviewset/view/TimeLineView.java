package com.example.mingren.customviewset.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.mingren.customviewset.R;


/**
 * Created by Vincent on 2017/8/25.
 * 时光轴效果
 */

public class TimeLineView extends FrameLayout {
    private View v_line_top;
    private View v_line_bottom;
    private ImageView iv_progress_dot;

    public TimeLineView(@NonNull Context context) {
        this(context, null);
    }

    public TimeLineView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.view_time_line, this);
        iv_progress_dot = (ImageView) findViewById(R.id.iv_progress_dot);
        v_line_top = findViewById(R.id.v_line_top);
        v_line_bottom = findViewById(R.id.v_line_bottom);
    }

    public void setDotColor(int inSideColor, int strokeWidth, int strokeColor) {
        GradientDrawable drawable = (GradientDrawable) iv_progress_dot.getDrawable();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColor(inSideColor);
            drawable.setStroke(strokeWidth, strokeColor);
        }
    }

    public void setDotSize(int radius) {
        ViewGroup.LayoutParams layoutParams = iv_progress_dot.getLayoutParams();
        layoutParams.width = layoutParams.height = radius;
        iv_progress_dot.requestLayout();
    }

    public void setTopLineVisibility(int visibility) {
        v_line_top.setVisibility(visibility);
    }

    public void setBottomLineVisibility(int visibility) {
        v_line_bottom.setVisibility(visibility);
    }

    public void changeDotByState(int state) {
        if (state == 1) {

        } else if (state == 2) {

        } else if (state == 3) {

        }
    }
}
