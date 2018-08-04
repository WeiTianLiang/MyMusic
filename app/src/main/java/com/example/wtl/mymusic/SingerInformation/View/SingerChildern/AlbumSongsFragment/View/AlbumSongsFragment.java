package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.AlbumSongsFragment.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wtl.mymusic.Main.View.MainActivity;
import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.AlbumSongsFragment.Presenter.AlbumSongsPresenterCompl;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.AlbumSongsFragment.Presenter.IAlbumSongsPresenter;

/**
 * 专辑歌曲碎片
 * Created by WTL on 2018/7/20.
 */

public class AlbumSongsFragment extends Fragment{

    private View view;
    private ImageView album_song_back;
    private ImageView album_song_head;
    private TextView album_song_name;
    private RecyclerView album_song_list;
    private String Id;
    private String name;
    private String img;
    private IAlbumSongsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.albumsongs_fragment,container,false);
        if(presenter == null) {
            presenter = new AlbumSongsPresenterCompl(getContext());
        }
        Montior(view);
        album_song_name.setText(name);
        Glide.with(getContext()).load(img).into(album_song_head);
        presenter.setAlbumSongsRecycler(album_song_list,Id,name);
        return view;
    }

    private void Montior(View view) {
        album_song_back = view.findViewById(R.id.album_song_back);
        album_song_head = view.findViewById(R.id.album_song_head);
        album_song_name = view.findViewById(R.id.album_song_name);
        album_song_list = view.findViewById(R.id.album_song_list);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Id = ((MainActivity) context).getSingId();
        name = ((MainActivity) context).getSingerName();
        img = ((MainActivity) context).getSingerPic();
    }
}
