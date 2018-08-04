package com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Presenter;

import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

/**
 * 歌手接口
 * Created by WTL on 2018/6/21.
 */

public interface ISingerPresenter {

    /**
     * 加载国家适配器
     */
    void setCountriesAdapter(RecyclerView recyclerView, ListView listView, AVLoadingIndicatorView loding, TextView wrongs);

    /**
     * 加载性别适配器
     */
    void setSexAdapter(RecyclerView recyclerView, ListView listView, AVLoadingIndicatorView loding, TextView wrongs);

    /**
     * 加载歌手数据适配器
     */
    void setNameAdapter(ListView listView, AVLoadingIndicatorView loding, TextView wrongs);

}
