package com.example.pqq.circlebar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private CircleBarView circleBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleBarView = (CircleBarView) findViewById(R.id.circle_bar);
        circleBarView.startAnim();
    }
}
