package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wtl.mymusic.Main.View.MainActivity;
import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.Presenter.ISingerMVPresenter;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.Presenter.SingerMVPresenterCompl;
import com.example.wtl.mymusic.Tool.BaseLazyLoadFragment;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 歌手MV
 * Created by WTL on 2018/6/22.
 */

public class SingerMVFragment extends BaseLazyLoadFragment {

    private View view;
    private RecyclerView mv_recycler;
    private ISingerMVPresenter presenter;
    private TextView wrong;
    private AVLoadingIndicatorView wait;
    private String SingerID;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragmentsinger_mv,container,false);
        mv_recycler = view.findViewById(R.id.mv_recycler);
        wrong = view.findViewById(R.id.wrong);
        wait = view.findViewById(R.id.wait);
        return view;
    }

    @Override
    public void initEvent() {
        if(presenter == null) {
            presenter = new SingerMVPresenterCompl(getContext());
        }
    }

    @Override
    public void onLazyLoad() {
        presenter.setMVRecycler(mv_recycler,wrong,wait,SingerID);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        SingerID = ((MainActivity) context).getSingId();
    }
}
