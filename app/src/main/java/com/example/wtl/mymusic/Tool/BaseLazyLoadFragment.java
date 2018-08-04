package com.example.wtl.mymusic.Tool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 对fragment实行懒加载
 * Created by WTL on 2018/7/28.
 */

public abstract class BaseLazyLoadFragment extends Fragment {

    private boolean isFirstLoad = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflateView(inflater,container);

        initEvent();

        isFirstLoad = true;

        if (getUserVisibleHint()) {
            onLazyLoad();
            isFirstLoad = false;
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad && isVisibleToUser) {
            onLazyLoad();
            isFirstLoad = false;
        }
    }

    public abstract View inflateView(LayoutInflater inflater,ViewGroup container);

    public abstract void initEvent();

    public abstract void onLazyLoad();

}
