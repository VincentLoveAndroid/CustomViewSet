package com.example.mingren.customviewset.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by vincent on 2016/12/19.
 * email-address:674928145@qq.com
 * description:
 */

public class MeasureView extends View {

    private static final String TAG = "MeasureView";

    public MeasureView(Context context) {
        super(context);
    }

    public MeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure: ");
        int measureHeight = getMySize("height", 100, heightMeasureSpec);
        int measureWidth = getMySize("width", 50, widthMeasureSpec);

        setMeasuredDimension(measureWidth, measureHeight);

    }

    private int getMySize(String tag, int defaultSize, int measureSpec) {
        int size = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED://这种情况一般用于系统内部，表示一种测量的状态。高两位00,0<<30
                size = defaultSize;
                Log.i(TAG, tag + "--MeasureSpec.UNSPECIFIED" + " size" + size);
                break;
            case MeasureSpec.EXACTLY://View的最终大小就是SpecSize所指定的值。它对应于LayoutParams中的match_parent和具体的数值这两种模式。
                // 高两位01,1 <<30
                size = specSize;
                Log.i(TAG, tag + "--MeasureSpec.EXACTLY" + " size" + size);
                break;
            case MeasureSpec.AT_MOST://View的大小不能大于这个值，具体是什么值要看不同View的具体实现。它对应于LayoutParams中的wrap_content,高两位11,2<<30
                size = defaultSize;
                Log.i(TAG, tag + "--MeasureSpec.AT_MOST" + " size" + size);
                break;
        }
        return size;

    }
}
