package com.example.wtl.mymusic.OnLineMusic.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wtl.mymusic.MyView.ViewPagerSlide;
import com.example.wtl.mymusic.OnLineMusic.ChildernControl.MusicMV.View.MusicMVFragment;
import com.example.wtl.mymusic.OnLineMusic.ChildernControl.MusicRanking.View.MusicRankingFragment;
import com.example.wtl.mymusic.OnLineMusic.ChildernControl.MusicSorted.View.MusicSortedFragment;
import com.example.wtl.mymusic.OnLineMusic.ChildernControl.NewMusic.View.NewMusicFragment;
import com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.View.SingerFragment;
import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.Tool.DivideViewPagerAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线音乐主界面
 * Created by WTL on 2018/6/21.
 */

public class OnLineMusicFragment extends Fragment {

    private View view;

    private TabLayout music_classify;

    private ViewPagerSlide viewPagerChild;

    private List<Fragment> fragmentList = new ArrayList<>();

    private List<String> stringList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_onlinemusic, container, false);
        init();
        DivideViewPagerAdapter adapter = new DivideViewPagerAdapter(getChildFragmentManager(),fragmentList,stringList);
        viewPagerChild.setAdapter(adapter);
        viewPagerChild.setOffscreenPageLimit(5);
        music_classify.setupWithViewPager(viewPagerChild);
        return view;
    }

    private void init() {
        music_classify = view.findViewById(R.id.music_classify);
        viewPagerChild = view.findViewById(R.id.viewpagerchild);

        stringList.add("新歌");
        stringList.add("歌手");
        stringList.add("歌单");
        stringList.add("排行");
        stringList.add("MV");

        fragmentList.add(new NewMusicFragment());
        fragmentList.add(new SingerFragment());
        fragmentList.add(new MusicSortedFragment());
        fragmentList.add(new MusicRankingFragment());
        fragmentList.add(new MusicMVFragment());
    }
}
