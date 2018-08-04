package com.example.wtl.mymusic.SingerInformation.View;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wtl.mymusic.Main.View.MainActivity;
import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.SingerInformation.Presenter.ISingerInformationPresenter;
import com.example.wtl.mymusic.SingerInformation.Presenter.SingerInformationPresenterCompl;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerAlbumFragment.View.SingerAlbumFragment;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.View.SingerMVFragment;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.View.SingerSongFragment;
import com.example.wtl.mymusic.Tool.BaseLazyLoadFragment;
import com.example.wtl.mymusic.Tool.DivideViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 歌手歌曲，专辑，MV界面
 * Created by WTL on 2018/6/25.
 */

public class SingerInformationFragment extends BaseLazyLoadFragment {

    private View view;

    private ImageView sinfback;
    private ImageView sinfhead;
    private TabLayout sinfnavion;
    private ViewPager viewpager_sinf;
    private TextView sinfname;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    private ISingerInformationPresenter presenter;
    private String name;
    private String SingerPic;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_singerinformation,container,false);
        Montior();
        init();
        return view;
    }

    @Override
    public void initEvent() {
        if (presenter == null) {
            presenter = new SingerInformationPresenterCompl(getContext());
        }
    }

    @Override
    public void onLazyLoad() {
        DivideViewPagerAdapter adapter = new DivideViewPagerAdapter(getChildFragmentManager(), fragmentList, stringList);
        viewpager_sinf.setAdapter(adapter);
        viewpager_sinf.setOffscreenPageLimit(3);
        sinfnavion.setupWithViewPager(viewpager_sinf);
    }

    private void Montior() {
        sinfback = view.findViewById(R.id.sinfback);
        sinfhead = view.findViewById(R.id.sinfhead);
        sinfnavion = view.findViewById(R.id.sinfnavion);
        viewpager_sinf = view.findViewById(R.id.viewpager_sinf);
        sinfname = view.findViewById(R.id.sinfname);
        sinfname.setText(name);
        Glide.with(this).load(SingerPic).into(sinfhead);
        sinfback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Presenter");
                intent.putExtra("viewlocal","0");
                getContext().sendBroadcast(intent);
            }
        });
    }

    private void init() {
        fragmentList.add(new SingerSongFragment());
        fragmentList.add(new SingerAlbumFragment());
        fragmentList.add(new SingerMVFragment());

        stringList = new ArrayList<>();
        stringList.add("歌曲");
        stringList.add("专辑");
        stringList.add("MV");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        name = ((MainActivity) context).getSingerName();
        SingerPic = ((MainActivity) context).getSingerPic();
    }
}
