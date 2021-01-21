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
    private float mTextSize;//字体大小
    private int mTextColor;//字体颜色
    private int mBgColor;//背景颜色
    private int mTouchBgColor;//按下背景颜色


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
        //初始化默认参数
        float density = context.getResources().getDisplayMetrics().density;
        mTextSize = Math.round(density * 16);
        mTextColor = Color.parseColor("#FFFFFF");
        //获取设置参数
        mBgColor = context.getResources().getColor(R.color.colorPrimaryDark);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IndexView);
        mTextSize = ta.getDimension(R.styleable.IndexView_index_text_size, mTextSize);
        mTextColor = ta.getColor(R.styleable.IndexView_index_text_color, mTextColor);
        mBgColor = ta.getColor(R.styleable.IndexView_index_bg_color, mBgColor);
        mTouchBgColor = ta.getColor(R.styleable.IndexView_index_touch_bg_color, mBgColor);
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置背景色
        if (isTouch) {
            canvas.drawColor(mTouchBgColor);
        } else {
            canvas.drawColor(mBgColor);
        }
        rawHeight = this.getHeight() / COUNT;//算出行高
        Paint paint = new Paint();
        paint.setColor(mTextColor);        //设置字体颜色
        paint.setTextSize(mTextSize);
        String dstr;
        //画索引
        for (int i = 1; i <= COUNT; i++) {
            dstr = intToString(i);
            canvas.drawText(dstr, this.getWidth() / 2 - paint.measureText(dstr) / 2, rawHeight * i, paint);      //paint.measureText(dstr)拿取字母的宽度
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int current = (int) (event.getY() / rawHeight) + 1;
        if (current > COUNT) {
            return super.onTouchEvent(event);
        }
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

    /**
     * 设置索引位置改变回调
     *
     * @param listener
     */
    public void setIndexViewChangeListener(OnIndexViewChangeListener listener) {
        mListener = listener;
    }

    /**
     * 索引位置改变回调
     */
    public interface OnIndexViewChangeListener {
        void onIndexChange(String indexStr);
    }
}
