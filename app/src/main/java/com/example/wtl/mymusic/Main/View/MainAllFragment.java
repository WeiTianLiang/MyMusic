package com.example.wtl.mymusic.Main.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wtl.mymusic.MyMusic.View.MyMusicFragment;
import com.example.wtl.mymusic.OnLineMusic.View.OnLineMusicFragment;
import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.Tool.DivideViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面Fragment
 * 保证切换时底部音乐栏始终存在
 * Created by WTL on 2018/6/25.
 */

public class MainAllFragment extends Fragment {

    private View view;

    private TabLayout Top_Navigation;

    private ViewPager viewpager;

    private List<Fragment> fragmentList = new ArrayList<>();

    private List<String> stringList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmentmain_all,container,false);
        Top_Navigation =  view.findViewById(R.id.Top_Navigation);
        viewpager = view.findViewById(R.id.viewpager);
        init();

        DivideViewPagerAdapter adapter = new DivideViewPagerAdapter(getChildFragmentManager(), fragmentList, stringList);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);
        Top_Navigation.setupWithViewPager(viewpager);

        return view;
    }

    private void init() {
        fragmentList.add(new MyMusicFragment());
        fragmentList.add(new OnLineMusicFragment());

        stringList.add("我的");
        stringList.add("音乐馆");
    }
}
