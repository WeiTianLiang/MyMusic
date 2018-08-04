package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerAlbumFragment.Presenter;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

/**
 * mv接口
 * Created by WTL on 2018/7/20.
 */

public interface ISingerAlbumPresenter {

    void setAlbumRecycler(RecyclerView recycler, String id, TextView wrong, AVLoadingIndicatorView wait);

}
