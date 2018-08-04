package com.example.wtl.mymusic.OnLineMusic.ChildernControl.MusicRanking.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wtl.mymusic.R;

/**
 * 排行榜碎片
 * Created by WTL on 2018/6/21.
 */

public class MusicRankingFragment extends Fragment{

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmentchild_musicranking,container,false);
        return view;
    }
}
