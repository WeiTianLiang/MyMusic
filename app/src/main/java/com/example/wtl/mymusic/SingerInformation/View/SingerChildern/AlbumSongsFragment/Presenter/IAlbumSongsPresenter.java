package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.AlbumSongsFragment.Presenter;

import android.support.v7.widget.RecyclerView;

/**
 * 专辑歌曲接口
 * Created by WTL on 2018/7/20.
 */

public interface IAlbumSongsPresenter {

    void setAlbumSongsRecycler(RecyclerView recycler,String Id,String name);

}
