package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.AlbumSongsFragment.Presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.AlbumSongsFragment.Model.AlbumSongModel;

import java.util.List;

/**
 * 专辑歌曲适配器
 * Created by WTL on 2018/7/20.
 */

public class AlbumSongAdapter extends RecyclerView.Adapter<AlbumSongAdapter.ViewHolder> {

    private Context context;
    private List<AlbumSongModel.songs> modelList;
    private OnTouchAlbumSongs onTouchAlbumSongs;

    public AlbumSongAdapter(Context context, List<AlbumSongModel.songs> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public AlbumSongAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.singersongcarditem,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumSongAdapter.ViewHolder holder, final int position) {
        AlbumSongModel.songs model = modelList.get(position);
        holder.songname.setText(model.getName());
        if(model.getAr().get(0).getAlia() != null) {
            holder.songbrief.setText(model.getAr().get(0).getAlia().get(0));
        }
        holder.tosong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTouchAlbumSongs.toTouchAlbumSongs(position);
            }
        });
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.islike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView songname;
        TextView songbrief;
        ImageView islike;
        ImageView download;
        RelativeLayout tosong;

        public ViewHolder(View itemView) {
            super(itemView);
            songname = itemView.findViewById(R.id.songname);
            songbrief = itemView.findViewById(R.id.songbrief);
            islike = itemView.findViewById(R.id.islike);
            download = itemView.findViewById(R.id.download);
            tosong = itemView.findViewById(R.id.tosong);
        }
    }

    public interface OnTouchAlbumSongs {
        void toTouchAlbumSongs(int position);
    }

    public void setOnTouchTheAlbum(OnTouchAlbumSongs onTouchAlbumSongs) {
        this.onTouchAlbumSongs = onTouchAlbumSongs;
    }

}
