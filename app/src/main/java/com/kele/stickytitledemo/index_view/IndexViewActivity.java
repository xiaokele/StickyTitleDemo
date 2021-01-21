package com.kele.stickytitledemo.index_view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.kele.stickytitledemo.R;
import com.kele.stickytitledemo.index_view.weight.IndexView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Des: 指示索引Activity
 * Created by kele on 2021/1/21.
 * E-mail:984127585@qq.com
 */
public class IndexViewActivity extends AppCompatActivity {

    public static void launch(AppCompatActivity act) {
        Intent intent = new Intent(act, IndexViewActivity.class);
        act.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_view);

        IndexView IVIndex = findViewById(R.id.iv_index);
        final TextView tvIndex = findViewById(R.id.tv_index);

        IVIndex.setIndexViewChangeListener(new IndexView.OnIndexViewChangeListener() {
            @Override
            public void onIndexChange(String indexStr) {
                tvIndex.setText(indexStr);
            }
        });
    }
}
