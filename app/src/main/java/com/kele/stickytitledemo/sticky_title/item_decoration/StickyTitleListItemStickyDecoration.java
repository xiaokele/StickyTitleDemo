package com.kele.stickytitledemo.sticky_title.item_decoration;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Des:
 * Created by kele on 2021/1/20.
 * E-mail:984127585@qq.com
 */
public class StickyTitleListItemStickyDecoration extends RecyclerView.ItemDecoration {

    /**
     * 悬浮栏高度
     */
    private int mGroupHeight = 80;
    /**
     * 是否靠左边
     * true 靠左边
     * false 不靠左
     */
    //private boolean isAlignLeft = true;

    //private final Paint bgPaint;
    //private final Paint textPaint;
    /**
     * 通过构造函数将groupListener传递进来
     */
    private StickyTitleGroupListener mGroupListener;

    private StickyTitleListItemStickyDecoration(StickyTitleGroupListener groupListener) {
        this.mGroupListener = groupListener;

        //bgPaint = new Paint();
        //bgPaint.setAntiAlias(true);
        //bgPaint.setColor(Color.parseColor("#333F51B5"));
        //textPaint = new Paint();
        //textPaint.setAntiAlias(true);
        //textPaint.setColor(Color.parseColor("#ffffff"));
        //textPaint.setTextSize(45);
    }

    /**
     * item数据回调
     */
    public interface StickyTitleGroupListener {
        //获取每一组的组名
        String getGroupName(int position);

        //得到组View
        View getGroupView(int position);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //得到你添加的分割线View的位置
        int pos = parent.getChildAdapterPosition(view);
        //获取组名
        String groupName = getGroupName(pos);
        if (TextUtils.isEmpty(groupName)) return;
        //只有是同一组的第一个才显示悬浮栏
        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = mGroupHeight;
        }
    }

    /**
     * 获取组名
     *
     * @param position
     * @return 组名
     */
    private String getGroupName(int position) {
        if (mGroupListener != null) {
            return mGroupListener.getGroupName(position);
        } else {
            return null;
        }
    }

    /**
     * 判断是不是组中的第一个位置
     * 根据前一个组名，判断当前是否为新的组
     */
    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            String prevGroupId = getGroupName(pos - 1);
            String groupId = getGroupName(pos);
            return !TextUtils.equals(prevGroupId, groupId);
        }
    }

    /**
     * 获取组View
     *
     * @param position
     * @return 组名
     */
    private View getGroupView(int position) {
        if (mGroupListener != null) {
            return mGroupListener.getGroupView(position);
        } else {
            return null;
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //获取单条的数目
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        //得出距离左边和右边的padding
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        //开始绘制
        String preGroupName;
        String currentGroupName = null;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            preGroupName = currentGroupName;
            currentGroupName = getGroupName(position);
            if (currentGroupName == null || TextUtils.equals(currentGroupName, preGroupName)) {
                continue;
            }
            int viewBottom = view.getBottom();
            //top 决定当前顶部第一个悬浮Group的位置
            int top = Math.max(mGroupHeight, view.getTop());
            if (position + 1 < itemCount) {
                //获取下个GroupName
                String nextGroupName = getGroupName(position + 1);
                //下一组的第一个View接近头部
                if (!currentGroupName.equals(nextGroupName) && viewBottom < top) {
                    top = viewBottom;
                }
            }

            //根据position获取View
            View groupView = getGroupView(position);
            if (groupView == null) {
                return;
            }
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mGroupHeight);
            groupView.setLayoutParams(layoutParams);
            groupView.setDrawingCacheEnabled(true);
            groupView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            //指定高度、宽度的groupView
            groupView.layout(0, 0, right, mGroupHeight);
            groupView.buildDrawingCache();
            Bitmap bitmap = groupView.getDrawingCache();
            //int marginLeft = isAlignLeft ? 0 : right - groupView.getMeasuredWidth();
            //c.drawBitmap(bitmap, left + marginLeft, top - mGroupHeight, null);
            c.drawBitmap(bitmap, left, top - mGroupHeight, null);

            //c.drawRect(left, top - mGroupHeight, right, top, bgPaint);
            //Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            //float baseLine = top - (mGroupHeight - (fontMetrics.bottom - fontMetrics.top)) / 2 - fontMetrics.bottom;
            //c.drawText(getGroupName(position).toUpperCase(), left, baseLine, textPaint);
        }
    }

    /**
     * 构建者
     */
    public static class Builder {

        private StickyTitleListItemStickyDecoration mDecoration;

        private Builder(StickyTitleGroupListener listener) {
            mDecoration = new StickyTitleListItemStickyDecoration(listener);
        }

        /**
         * 初始化
         *
         * @param listener
         * @return
         */
        public static Builder init(StickyTitleGroupListener listener) {
            return new Builder(listener);
        }

        /**
         * 设置悬浮标题的高度
         *
         * @param height
         * @return
         */
        public Builder setTitleHeight(int height) {
            mDecoration.mGroupHeight = height;
            return this;
        }

//        /**
//         * 设置是否靠左显示
//         *
//         * @param alignLeft
//         * @return
//         */
//        public Builder setAlignLeft(boolean alignLeft) {
//            mDecoration.isAlignLeft = alignLeft;
//            return this;
//        }

        /**
         * 构建
         *
         * @return
         */
        public StickyTitleListItemStickyDecoration build() {
            return mDecoration;
        }
    }
}
