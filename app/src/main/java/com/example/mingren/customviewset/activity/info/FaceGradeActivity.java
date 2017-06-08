package com.example.mingren.customviewset.activity.info;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mingren.customviewset.R;

public class FaceGradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_grade);
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, FaceGradeActivity.class);
        context.startActivity(intent);
    }
}
