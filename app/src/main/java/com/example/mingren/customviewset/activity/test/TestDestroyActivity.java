package com.example.mingren.customviewset.activity.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.example.mingren.customviewset.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Vincent on 2017/7/19.
 */

public class TestDestroyActivity extends Activity {

    @Bind(R.id.tv_one)
    TextView tvOne;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        System.out.println("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_destroy);
        ButterKnife.bind(this);
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOne.setText("改变了状态");
                tvOne.setTextColor(ContextCompat.getColor(TestDestroyActivity.this, R.color.blue));
            }
        });
//        if (savedInstanceState != null) {
//            String text = savedInstanceState.getString("text");
//            tvOne.setText(text);
//        }

    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
////        System.out.println("onSaveInstanceState--" + outState);
////        outState.putString("text", tvOne.getText().toString());
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        System.out.println("onRestoreInstanceState--" + savedInstanceState);
////        if (savedInstanceState != null) {
////            String text = savedInstanceState.getString("text");
////            tvOne.setText(text);
////        }
//    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop");
    }

    public static void main(String arg[]) {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        a.remove(a.size() - 1);
        System.out.println(a.toString());
    }
}
