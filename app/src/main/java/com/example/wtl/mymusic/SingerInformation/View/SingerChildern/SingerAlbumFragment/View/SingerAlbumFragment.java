package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerAlbumFragment.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wtl.mymusic.Main.View.MainActivity;
import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerAlbumFragment.Presenter.ISingerAlbumPresenter;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerAlbumFragment.Presenter.SingerAlbumFragmentPresenter;
import com.example.wtl.mymusic.Tool.BaseLazyLoadFragment;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 歌手专辑
 * Created by WTL on 2018/6/22.
 */

public class SingerAlbumFragment extends BaseLazyLoadFragment {

    private View view;
    private RecyclerView ablum_list;
    private TextView wrong;
    private AVLoadingIndicatorView wait;
    private ISingerAlbumPresenter presenter;
    private String SingerID;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragmentsinger_album, container, false);
        ablum_list = view.findViewById(R.id.ablum_list);
        wrong = view.findViewById(R.id.album_wrong);
        wait = view.findViewById(R.id.album_wait);
        return view;
    }

    @Override
    public void initEvent() {
        if(presenter == null) {
            presenter = new SingerAlbumFragmentPresenter(getContext());
        }
    }

    @Override
    public void onLazyLoad() {
        presenter.setAlbumRecycler(ablum_list,SingerID,wrong,wait);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        SingerID = ((MainActivity) context).getSingId();
    }
}
