package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.Presenter.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.Model.SingerSongModel;
import com.example.wtl.mymusic.Tool.Dialog_Download;

import java.util.ArrayList;
import java.util.List;

/**
 * 歌手音乐加载适配器
 * Created by WTL on 2018/6/22.
 */

public class SongListAdapter extends BaseAdapter{

    private List<SingerSongModel.hotSongs> hotSongsList;
    private Context context;
    private OnToSongPlaying onToSongPlaying;
    private List<Boolean> booleanList = new ArrayList<>();

    public SongListAdapter(List<SingerSongModel.hotSongs> hotSongsList,Context context) {
        this.hotSongsList = hotSongsList;
        this.context = context;
        for(int i = 0 ; i < hotSongsList.size() ; i++) {
            booleanList.add(false);
        }
    }

    @Override
    public int getCount() {
        return hotSongsList.size();
    }

    @Override
    public Object getItem(int i) {
        return hotSongsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final SingerSongModel.hotSongs hotSongs = hotSongsList.get(i);
        ViewHolder holder;
        View itemview;
        if(view == null) {
            itemview = View.inflate(context,R.layout.singersongcarditem,null);
            holder = new ViewHolder(itemview);
            itemview.setTag(holder);
        } else {
            itemview = view;
            holder = (ViewHolder) itemview.getTag();
        }
        holder.songname.setText(hotSongs.getName());
        holder.songbrief.setText(hotSongs.getAl().getName());
        if(booleanList.get(i)) {
            holder.tiao.setVisibility(View.VISIBLE);
        } else {
            holder.tiao.setVisibility(View.GONE);
        }
        if(hotSongs.getT() == 0) {
            holder.islike.setImageResource(R.mipmap.like);
        } else if(hotSongs.getT() == 1) {
            holder.islike.setImageResource(R.mipmap.islike);
        }
        holder.tosong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onToSongPlaying.toSongPlaying(i);
                for(int i = 0 ; i < hotSongsList.size() ; i++) {
                    booleanList.set(i,false);
                }
                booleanList.set(i,true);
                notifyDataSetChanged();
            }
        });
        //下载
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog_Download download = new Dialog_Download(context,
                        "http://music.163.com/song/media/outer/url?id="+hotSongs.getId()+".mp3",
                        hotSongs.getName(),
                        hotSongs.getAl().getName());
                download.show();
                download.setOnOperateMusic(new Dialog_Download.OnOperateMusic() {
                    @Override
                    public void setCancel() {
                        download.dismiss();
                    }
                });
            }
        });
        return itemview;
    }

    class ViewHolder {
        TextView songname;
        TextView songbrief;
        RelativeLayout tosong;
        ImageView islike;
        ImageView download;
        View tiao;

        ViewHolder(View view) {
            songname = view.findViewById(R.id.songname);
            songbrief = view.findViewById(R.id.songbrief);
            tosong = view.findViewById(R.id.tosong);
            islike = view.findViewById(R.id.islike);
            download = view.findViewById(R.id.download);
            tiao = view.findViewById(R.id.tiao);
        }
    }

    public interface OnToSongPlaying {
        void toSongPlaying(int position);
    }

    public void setOnToSongPlaying(OnToSongPlaying onToSongPlaying) {
        this.onToSongPlaying = onToSongPlaying;
    }
}
