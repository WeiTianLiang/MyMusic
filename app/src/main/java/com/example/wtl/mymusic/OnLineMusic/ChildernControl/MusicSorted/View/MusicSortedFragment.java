package com.example.wtl.mymusic.OnLineMusic.ChildernControl.MusicSorted.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wtl.mymusic.R;

/**
 * 歌单分类碎片
 * Created by WTL on 2018/6/21.
 */

public class MusicSortedFragment extends Fragment{

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmentchild_musicsorted,container,false);
        return view;
    }
}
