package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wtl.mymusic.Main.View.MainActivity;
import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.Presenter.ISingerSong;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.Presenter.SingerSongCompl;
import com.example.wtl.mymusic.Tool.BaseLazyLoadFragment;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 歌手歌曲
 * Created by WTL on 2018/6/22.
 */

public class SingerSongFragment extends BaseLazyLoadFragment {

    private View view;
    private ListView songlist;
    private ISingerSong song;
    private AVLoadingIndicatorView wait;
    private TextView wrong;
    private String SingerID;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragmentsinger_song, container, false);
        songlist = view.findViewById(R.id.songlist);
        wait = view.findViewById(R.id.wait);
        wrong = view.findViewById(R.id.wrong);
        return view;
    }

    @Override
    public void initEvent() {
        if (song == null) {
            song = new SingerSongCompl(getContext());
        }
    }

    @Override
    public void onLazyLoad() {
        song.setSongListView(songlist, SingerID, wait, wrong);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        SingerID = ((MainActivity) context).getSingId();
    }
}
