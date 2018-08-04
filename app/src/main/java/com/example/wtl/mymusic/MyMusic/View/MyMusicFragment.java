package com.example.wtl.mymusic.MyMusic.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wtl.mymusic.MyMusic.Presenter.IMyMusicPresenter;
import com.example.wtl.mymusic.MyMusic.Presenter.MyMusicPresenterCompl;
import com.example.wtl.mymusic.R;

/**
 * 我的音乐主fragment
 * Created by WTL on 2018/6/20.
 */

public class MyMusicFragment extends Fragment {

    private View view;
    private RecyclerView my_music_list;
    private IMyMusicPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mymusic,container,false);
        if(presenter == null) {
            presenter = new MyMusicPresenterCompl(getContext());
        }
        my_music_list = view.findViewById(R.id.my_music_list);
        presenter.setMyMusicAdapter(my_music_list);
        return view;
    }

}
