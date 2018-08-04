package com.example.wtl.mymusic.Main.Presenter;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * 主接口
 * Created by WTL on 2018/6/28.
 */

public interface IMainPresenter {

    void setSqlData(TextView music_name, TextView music_actor_name, ImageView playingstop, ImageView music_image);

    void addSqlData(String songId, String music_name, String music_actor_name, String music_state, String music_image);

}
