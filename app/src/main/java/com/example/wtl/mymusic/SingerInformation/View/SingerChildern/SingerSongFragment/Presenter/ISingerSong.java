package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.Presenter;

import android.widget.ListView;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

/**
 * 音乐加载接口
 * Created by WTL on 2018/6/22.
 */

public interface ISingerSong {

    /**
    * 设置歌曲适配器
    * */
    void setSongListView(ListView listView, String id, AVLoadingIndicatorView wait, TextView wrong);

}
