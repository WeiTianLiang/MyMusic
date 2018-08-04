package com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.View;


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;

import com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Presenter.ISingerPresenter;
import com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Presenter.SingerPresenterCompl;
import com.example.wtl.mymusic.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 歌手碎片
 * Created by WTL on 2018/6/21.
 */

public class SingerFragment extends Fragment {

    private View view;

    private RecyclerView singer_countries;

    private RecyclerView singer_sex;

    private ListView singer_name;

    private ISingerPresenter presenter;

    private AVLoadingIndicatorView loding;

    private TextView wrongs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmentchild_singer, container, false);
        Montior();
        if (presenter == null) {
            presenter = new SingerPresenterCompl(getContext());
        }
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        singer_countries.setLayoutManager(manager);
        LinearLayoutManager manager1 = new LinearLayoutManager(getContext());
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        singer_sex.setLayoutManager(manager1);

        presenter.setCountriesAdapter(singer_countries, singer_name, loding, wrongs);
        presenter.setSexAdapter(singer_sex, singer_name, loding, wrongs);
        presenter.setNameAdapter(singer_name, loding, wrongs);
        return view;
    }

    private void Montior() {
        singer_countries = view.findViewById(R.id.singer_countries);
        singer_sex = view.findViewById(R.id.singer_sex);
        singer_name = view.findViewById(R.id.singer_name);
        loding = view.findViewById(R.id.loading);
        wrongs = view.findViewById(R.id.wrongs);
    }

}
