package com.kele.stickytitledemo.sticky_title;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kele.stickytitledemo.R;
import com.kele.stickytitledemo.sticky_title.adapter.StickyTitleAdapter;
import com.kele.stickytitledemo.sticky_title.bean.StickyTitleItemBean;
import com.kele.stickytitledemo.sticky_title.item_decoration.StickyTitleListItemMarginDecoration;
import com.kele.stickytitledemo.sticky_title.item_decoration.StickyTitleListItemStickyDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Des:
 * Created by kele on 2021/1/20.
 * E-mail:984127585@qq.com
 */
public class StickyTitleActivity extends AppCompatActivity {

    public static void launch(AppCompatActivity act) {
        Intent intent = new Intent(act, StickyTitleActivity.class);
        act.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_title);

        final RecyclerView mRV = findViewById(R.id.rv);

        LinearLayoutManager mManager = new LinearLayoutManager(this);
        mRV.setLayoutManager(mManager);
        final List<StickyTitleItemBean> mList = new ArrayList<>();
        mRV.addItemDecoration(new StickyTitleListItemMarginDecoration(mList));
        StickyTitleListItemStickyDecoration stickyDecoration = StickyTitleListItemStickyDecoration.Builder
                .init(new StickyTitleListItemStickyDecoration.StickyTitleGroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        return mList.get(position).title;
                    }

                    @Override
                    public View getGroupView(int position) {
                        if (position < mList.size()) {
                            View view = getLayoutInflater().inflate(R.layout.title_sticky_title, null, false);
                            TextView tvTitle = view.findViewById(R.id.tv_title);
                            tvTitle.setText(mList.get(position).title);
                            return view;
                        }
                        return null;
                    }
                })
                .setTitleHeight(60)
                //.setAlignLeft(true)
                .build();
        mRV.addItemDecoration(stickyDecoration);
        StickyTitleAdapter mAdapter = new StickyTitleAdapter(R.layout.item_sticky_title, mList);
        mRV.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new StickyTitleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(StickyTitleActivity.this, "点击了-" + position, Toast.LENGTH_SHORT).show();
            }
        });

        for (int i = 0; i < 50; i++) {
            StickyTitleItemBean b = new StickyTitleItemBean();
            b.isTitle = i % 5 == 0;
            if (i < 10) {
                b.title = "title_1";
            } else if (i < 20) {
                b.title = "title_2";
            } else if (i < 30) {
                b.title = "title_3";
            } else {
                b.title = "title_other";
            }
            b.content = "content-" + i;
            mList.add(b);
        }
    }
}
