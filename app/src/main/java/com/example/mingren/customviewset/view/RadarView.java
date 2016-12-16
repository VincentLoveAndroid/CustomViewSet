package com.example.mingren.customviewset.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.example.mingren.customviewset.Ob.RadarItem;
import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.Utils.DensityUtil;
import com.example.mingren.customviewset.Utils.PaintUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 2016/12/1.
 * email-address:674928145@qq.com
 * description:访芝麻信用雷达图
 */

public class RadarView extends View {
    private Paint mPaintInternal;
    private Paint mPaintLine;
    private Paint mPaintText;
    private Paint mPaintScore;
    private String[] arrText = {"履约能力", "身份特质", "行为偏好", "人脉关系", "信用历史"};
    private int[] iconDrawable = {R.mipmap.ic_performance, R.mipmap.ic_identity, R.mipmap.ic_behavior, R.mipmap.ic_contacts, R.mipmap.ic_history};

    private int internalLineLength = DensityUtil.dip2px(getContext(), 80);
    private int bitmapLineLength = DensityUtil.dip2px(getContext(), 120);

    private int textMargin = DensityUtil.dip2px(getContext(), 20);//文字左下角距离图片的距离

    private int mCenterX;
    private int mCenterY;
    private Path mPath;
    private int itemCount = 5;

    public int itemTopScore = 190;

    public static final int TYPE_PERFORMANCE = 0;
    public static final int TYPE_IDENTITY = 1;
    public static final int TYPE_BEHAVIOR = 2;
    public static final int TYPE_CONTACTS = 3;
    public static final int TYPE_HISTORY = 4;

    private List<RadarItem> itemList = new ArrayList<>();


    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init() {
        mPaintInternal = PaintUtil.getDefaultPaint();

        mPaintLine = PaintUtil.getDefaultPaint();
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setColor(getResources().getColor(R.color.color_radar_line));

        mPaintInternal = PaintUtil.getDefaultPaint();
        mPaintInternal.setColor(getResources().getColor(R.color.color_radar_bg_internal));
        mPaintInternal.setStyle(Paint.Style.FILL_AND_STROKE);

        mPaintText = PaintUtil.getDefaultPaint();
        mPaintText.setColor(getResources().getColor(R.color.color_radar_line));
        mPaintText.setTextSize(23);


        mPaintScore = PaintUtil.getDefaultPaint();
        mPaintScore.setColor(getResources().getColor(R.color.color_radar_line));
        mPaintScore.setTextSize(60);

        mPath = new Path();

        for (int i = 0; i < itemCount; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), iconDrawable[i]);
            String itemName = arrText[i];

            RadarItem item = new RadarItem();
            item.iconBitmap = bitmap;
            item.itemName = itemName;
            item.type = i;

            itemList.add(item);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        mCenterX = width / 2;
        mCenterY = height / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mCenterX, mCenterY);

        drawOutAndInternalLine(canvas);
        drawInternal(canvas);
        drawBitmapAndText(canvas);
        drawScoreAndTips(canvas);
    }

    /**
     * 绘制总分
     */
    private void drawScoreAndTips(Canvas canvas) {
        int score = 0;
        for (RadarItem item : itemList) {
            score += item.score;
        }
        float ScoreTextWidth = mPaintScore.measureText(score + "");
        canvas.drawText(score + "", 0 - ScoreTextWidth / 2, mPaintScore.getTextSize() / 3, mPaintScore);

        String tips = "芝麻分是根据以下五个维度综合评估而来";
        mPaintScore.setTextSize(35);
        float tipsTextWidth = mPaintScore.measureText(tips);
        canvas.drawText(tips, 0 - tipsTextWidth / 2, -350, mPaintScore);
    }

    /**
     * 绘制五边形和内部线条
     */
    private void drawOutAndInternalLine(Canvas canvas) {
        mPath.reset();
        for (int i = 0; i < 5; i++) {
            float pointX = (float) (internalLineLength * Math.cos(Math.PI / 10 + i * 2 * Math.PI / 5));
            float pointY = -(float) (internalLineLength * Math.sin(Math.PI / 10 + i * 2 * Math.PI / 5));
            if (i == 0) mPath.moveTo(pointX, pointY);
            else mPath.lineTo(pointX, pointY);
            canvas.drawLine(0, 0, pointX, pointY, mPaintLine);
        }
        mPath.close();
        canvas.drawPath(mPath, mPaintLine);
    }

    /**
     * 绘制分数五边形
     */
    private void drawInternal(Canvas canvas) {
        mPath.reset();
        for (int i = 0; i < itemList.size(); i++) {
            //通过控制scoreLength来实现各个条目点的具体位置
            RadarItem item = itemList.get(i);
            float scoreLength = internalLineLength * (item.score * 1.0f / itemTopScore);
            float pointX = (float) (scoreLength * Math.cos(Math.PI / 10 + i * 2 * Math.PI / 5));
            float pointY = -(float) (scoreLength * Math.sin(Math.PI / 10 + i * 2 * Math.PI / 5));
            if (i == 0) mPath.moveTo(pointX, pointY);
            else mPath.lineTo(pointX, pointY);
        }
        mPath.close();
        canvas.drawPath(mPath, mPaintInternal);
    }

    /**
     * 绘制图片和文字
     */
    private void drawBitmapAndText(Canvas canvas) {
        for (int i = 0; i < itemList.size(); i++) {
            RadarItem radarItem = itemList.get(i);
            Bitmap bitmap = radarItem.iconBitmap;
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float pointX = (float) (bitmapLineLength * Math.cos(Math.PI / 10 + i * 2 * Math.PI / 5)) - width / 2;
            float pointY = -(float) (bitmapLineLength * Math.sin(Math.PI / 10 + i * 2 * Math.PI / 5)) - height / 2;
            canvas.drawBitmap(bitmap, pointX, pointY, mPaintInternal);

            String text = radarItem.itemName;
            float textWidth = mPaintText.measureText(text);
            float textStartX = (int) (pointX + width / 2 - textWidth / 2);

            float bitmapBottom = pointY + height;
            float textStartY = bitmapBottom + textMargin;
            canvas.drawText(text, textStartX, textStartY, mPaintText);

        }
    }

    /**
     * 改变各项分值并刷新控件
     */
    public void refreshScore(int type, int currentScore) {
        for (RadarItem radarItem : itemList) {
            if (radarItem.type == type) {
                radarItem.score = currentScore;
            }
        }
        invalidate();
    }
}
