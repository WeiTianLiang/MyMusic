package com.example.wtl.mymusic.ShowListereningMusic.Presenter;

import android.media.MediaPlayer;

/**
 * 更新进度条接口
 * Created by WTL on 2018/6/29.
 */

public interface IUpdateProcess {

    void updateProcess(long current,long max);

    void setLocal(MediaPlayer mediaPlayer);

}
