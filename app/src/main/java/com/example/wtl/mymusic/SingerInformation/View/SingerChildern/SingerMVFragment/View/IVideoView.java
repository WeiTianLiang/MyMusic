package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 重写VideoView
 * Created by WTL on 2018/7/21.
 */

public class IVideoView extends VideoView {

    public IVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public IVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IVideoView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
