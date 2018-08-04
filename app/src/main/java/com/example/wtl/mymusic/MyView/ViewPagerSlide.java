package com.example.wtl.mymusic.MyView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义ViewPager加入禁止滑动模块
 * Created by WTL on 2018/6/21.
 */

public class ViewPagerSlide extends ViewPager {

    private boolean isSlide = true;

    public ViewPagerSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerSlide(Context context) {
        super(context);
    }

    public void setSlide(boolean isSlide) {
        this.isSlide = isSlide;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSlide) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isSlide) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return true;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
