package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.Presenter;

import android.support.v7.widget.RecyclerView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.wang.avi.AVLoadingIndicatorView;

/**
 * 接口
 * Created by WTL on 2018/7/21.
 */

public interface ISingerMVPresenter {

    void setMVRecycler(RecyclerView recycler, TextView wrong , AVLoadingIndicatorView wait, String Id);

    void playingVideo(String url, VideoView videoView, SeekBar seekBar);

}
