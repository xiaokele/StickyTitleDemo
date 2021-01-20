package com.kele.stickytitledemo.sticky_title.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kele.stickytitledemo.R;
import com.kele.stickytitledemo.sticky_title.bean.StickyTitleItemBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Des: 粘性状态栏适配器
 * Created by kele on 2021/1/20.
 * E-mail:984127585@qq.com
 */
public class StickyTitleAdapter extends RecyclerView.Adapter<StickyTitleAdapter.StickyTitleViewHolder> {

    private int mLayoutId;
    private List<StickyTitleItemBean> mData;

    public StickyTitleAdapter(int layoutId, List<StickyTitleItemBean> data) {
        mLayoutId = layoutId;
        mData = data;
    }

    @NonNull
    @Override
    public StickyTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new StickyTitleViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull StickyTitleViewHolder holder, final int position) {
        holder.bindViewHolder(mData.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemClickListenr) {
                    mOnItemClickListenr.onItemClick(position);
                }
            }
        });
    }

    class StickyTitleViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvContent;

        public StickyTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }

        public void bindViewHolder(StickyTitleItemBean bean) {
            tvContent.setText(bean.content);
        }
    }

    private OnItemClickListener mOnItemClickListenr;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListenr = listener;
    }

}
