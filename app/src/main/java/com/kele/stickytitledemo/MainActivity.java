package com.kele.stickytitledemo;

import android.os.Bundle;
import android.view.View;

import com.kele.stickytitledemo.sticky_title.StickyTitleActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_sticky_title).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sticky_title:
                StickyTitleActivity.launch(this);
                break;
        }
    }
}