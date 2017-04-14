package com.example.mingren.customviewset.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.mingren.customviewset.R;
import com.example.mingren.customviewset.view.colorFilter_matrix.MatrixView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ColorMatrixFilterActivity extends Activity {

    @Bind(R.id.view_color_matrix)
    MatrixView matrixView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix_filter);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8})
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                matrixView.setColorMatrixColorFilter(MatrixView.FILTER_COLOR_RED);
            break;
            case R.id.button2:
                matrixView.setColorMatrixColorFilter(MatrixView.FILTER_COLOR_GREEN);
                break;
            case R.id.button3:
                matrixView.setColorMatrixColorFilter(MatrixView.FILTER_COLOR_BLUE);
                break;
            case R.id.button4:
                matrixView.setColorMatrixColorFilter(MatrixView.FILTER_COLOR_DEEP_GREEN);
                break;
            case R.id.button5:
                matrixView.setColorMatrixColorFilter(MatrixView.FILTER_COLOR_REVERSE);
                break;
            case R.id.button6:
                matrixView.setColorMatrixColorFilter(MatrixView.FILTER_COLOR_INVERSION);
                break;
            case R.id.button7:
                matrixView.setColorMatrixColorFilter(MatrixView.FILTER_COLOR_STRONG_ALL);
                break;
            case R.id.button8:
                matrixView.setColorMatrixColorFilter(MatrixView.FILTER_COLOR_ADVERSE);
                break;
        }
    }
}
