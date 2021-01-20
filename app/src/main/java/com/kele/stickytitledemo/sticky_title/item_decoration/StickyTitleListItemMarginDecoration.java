package com.kele.stickytitledemo.sticky_title.item_decoration;

import android.graphics.Rect;
import android.view.View;

import com.kele.stickytitledemo.sticky_title.bean.StickyTitleItemBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Des: 间隙分割线
 * Created by kele on 2021/1/20.
 * E-mail:984127585@qq.com
 */
public class StickyTitleListItemMarginDecoration extends RecyclerView.ItemDecoration {

    private List<StickyTitleItemBean> mList;

    public StickyTitleListItemMarginDecoration(List<StickyTitleItemBean> list) {
        mList = list;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            outRect.set(0, 0, 0, 5);
        } else if (layoutManager instanceof GridLayoutManager) {

        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }
}
