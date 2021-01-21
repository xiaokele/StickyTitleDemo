package com.kele.stickytitledemo.index_view.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kele.stickytitledemo.R;

import androidx.annotation.Nullable;

/**
 * Des: 索引View
 * Created by kele on 2021/1/21.
 * E-mail:984127585@qq.com
 */
public class IndexView extends View {

    char str = 'A';
    final int COUNT = 26;//一共26个字母没错吧
    int rawHeight = 0;//每个字母的行高
    boolean isTouch = false;//是否在触摸状态，如果是改变背景颜色
    private float mDefaultTextSize;
    private int mDefaultTextColor;
    private int mDefaultBgColor;


    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        float density = context.getResources().getDisplayMetrics().density;
        mDefaultTextSize = Math.round(density * 16);
        mDefaultTextColor = Color.parseColor("#FFFFFF");
        mDefaultBgColor = context.getResources().getColor(R.color.colorPrimaryDark);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IndexView);
        mDefaultTextSize = ta.getDimension(R.styleable.IndexView_index_text_size, mDefaultTextSize);
        mDefaultTextColor = ta.getColor(R.styleable.IndexView_index_text_color, mDefaultTextColor);
        mDefaultBgColor = ta.getColor(R.styleable.IndexView_index_bg_color, mDefaultBgColor);
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isTouch) {
            canvas.drawColor(mDefaultBgColor);
        } else {
            canvas.drawColor(mDefaultBgColor);
        }
        rawHeight = this.getHeight() / COUNT;//算出行高
        Paint paint = new Paint();
        paint.setColor(mDefaultTextColor);        //设置字体颜色
        paint.setTextSize(mDefaultTextSize);
        String dstr;
        for (int i = 1; i <= COUNT; i++) {
            dstr = intToString(i);
            canvas.drawText(dstr, this.getWidth() / 2 - paint.measureText(dstr) / 2, rawHeight * i, paint);      //paint.measureText(dstr)拿取字母的宽度
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int current = (int) (event.getY() / rawHeight) + 1;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                if (current > 0 && mListener != null) {
                    mListener.onIndexChange(intToString(current));
                }
                invalidate();                    //更新界面
                break;
            case MotionEvent.ACTION_MOVE:
                if (current > 0 && mListener != null) {
                    mListener.onIndexChange(intToString(current));
                }
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                if (current > 0 && mListener != null) {
                    mListener.onIndexChange("");
                }
                invalidate();
                break;
        }
        return true;
    }

    /**
     * int转字符串
     *
     * @param len
     * @return
     */
    private String intToString(int len) {
        return ((char) (str + len - 1)) + "";
    }

    private OnIndexViewChangeListener mListener;

    public void setIndexViewChangeListener(OnIndexViewChangeListener listener) {
        mListener = listener;
    }

    public interface OnIndexViewChangeListener {
        void onIndexChange(String indexStr);
    }
}
