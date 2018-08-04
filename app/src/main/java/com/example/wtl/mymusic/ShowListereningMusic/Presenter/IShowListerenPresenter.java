package com.example.wtl.mymusic.ShowListereningMusic.Presenter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 接口
 * Created by WTL on 2018/6/29.
 */

public interface IShowListerenPresenter {

    void setSqlData(TextView name, ImageView playstop);

    void updateSql(String state);

    void stopOrPlaying(ImageView imageView);

    void getSongText(RecyclerView lyric);

    void downLoadMusic();

}
