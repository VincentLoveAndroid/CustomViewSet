package com.example.mingren.customviewset.view.colorFilter_matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.PaintUtil;

/**
 * Created by vincent on 2017/4/13.
 * email-address:674928145@qq.com
 * description:
 */

public class MatrixView extends View {

    private Paint mFilterPaint;
    private Paint mOrignalPaint;
    private Bitmap mBitmap;
    private ColorMatrixColorFilter mColorMatrixColorFilter;
    public static final int FILTER_COLOR_RED = 1 << 1;
    public static final int FILTER_COLOR_GREEN = 1 << 2;
    public static final int FILTER_COLOR_BLUE = 1 << 3;
    public static final int FILTER_COLOR_DEEP_GREEN = 1 << 4;
    public static final int FILTER_COLOR_REVERSE = 1 << 5;
    public static final int FILTER_COLOR_INVERSION = 1 << 6;//反相效果
    public static final int FILTER_COLOR_STRONG_ALL = 1 << 7;
    public static final int FILTER_COLOR_ADVERSE = 1 << 8;//反色效果

    public MatrixView(Context context) {
        this(context, null);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mOrignalPaint = PaintUtil.getDefaultPaint();
        mOrignalPaint.setStyle(Paint.Style.FILL);

        mFilterPaint = PaintUtil.getDefaultPaint();
        mFilterPaint.setStyle(Paint.Style.FILL);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);

    }

    public void setColorMatrixColorFilter(int type) {
        float[] matrix = null;
        switch (type) {
            case FILTER_COLOR_RED:
                matrix = new float[]{
                        0, 0, 0, 0, 0,//R
                        0, 1, 0, 0, 0,//G
                        0, 0, 1, 0, 0,//B
                        0, 0, 0, 1, 0,//A
                };
                break;
            case FILTER_COLOR_GREEN:
                matrix = new float[]{
                        1, 0, 0, 0, 0,//R
                        0, 0, 0, 0, 0,//G
                        0, 0, 1, 0, 0,//B
                        0, 0, 0, 1, 0,//A
                };
                break;
            case FILTER_COLOR_BLUE:
                matrix = new float[]{
                        1, 0, 0, 0, 0,//R
                        0, 1, 0, 0, 0,//G
                        0, 0, 0, 0, 0,//B
                        0, 0, 0, 1, 0,//A
                };
                break;
            case FILTER_COLOR_DEEP_GREEN:
                matrix = new float[]{
                        1, 0, 0, 0, 0,//R 1*R+100
                        0, 1, 0, 0, 100,//G
                        0, 0, 1, 0, 0,//B
                        0, 0, 0, 1, 0,//A
                };
                break;

            case FILTER_COLOR_REVERSE:
                matrix = new float[]{
                        1, 0, 0, 0, 0,//R
                        0, 1, 0, 0, 0,//G
                        0, 0, 1, 0, 0,//B
                        0, 0, 0, 1, 0,//A
                };
                break;

            case FILTER_COLOR_INVERSION:
                matrix = new float[]{
                        -1, 0, 0, 0, 255,//R
                        0, -1, 0, 0, 255,//G
                        0, 0, -1, 0, 255,//B
                        0, 0, 0, 1, 0,//A
                };
                break;
            case FILTER_COLOR_STRONG_ALL:
                matrix = new float[]{
                        1.3f, 0, 0, 0, 0,//R
                        0, 1.3f, 0, 0, 0,//G
                        0, 0, 1.3f, 0, 0,//B
                        0, 0, 0, 1.3f, 0,//A
                };
                new ColorMatrix().setScale(1.3f, 1.3f, 1.3f, 1);
                break;
            case FILTER_COLOR_ADVERSE:
                matrix = new float[]{
                        0, 1f, 0, 0, 0,//R
                        1f, 0, 0, 0, 0,//G
                        0, 1f, 0, 0, 0,//B
                        0, 0, 0, 1, 0,//A
                };
                break;

        }
        ColorMatrix colorMatrix = new ColorMatrix(matrix);
//        ColorMatrix colorMatrix = new ColorMatrix();
//        colorMatrix.set(matrix);
//        colorMatrix.setScale(1.3f,1.3f,1.3f,1);//色彩缩放
//        colorMatrix.reset();//重置
//        colorMatrix.setSaturation(0);//色彩饱和度，0灰色，1，保持原来不变，>1增加饱和度
//        colorMatrix.setRotate(axis,degrees);//色彩旋转
        mColorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        
        /*------------------------------颜色RGB的滤镜处理------------------------------*/
        //需要关闭硬件加速，没有关闭则没效果
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //设置矩阵颜色过滤器
//        canvas.drawRect(100, 100, 300, 300, mFilterPaint);
        canvas.drawBitmap(mBitmap, 100, 100, mOrignalPaint);

        mFilterPaint.setColorFilter(mColorMatrixColorFilter);

        canvas.drawBitmap(mBitmap, 100, 600, mFilterPaint);
    }
}
