package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerAlbumFragment.Presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerAlbumFragment.Model.SingerAlbumModel;

import java.util.List;

/**
 * mv适配器
 * Created by WTL on 2018/7/20.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private Context context;
    private List<SingerAlbumModel.hotAlbums> hotAlbumsList;
    private OnTouchTheAlbum onTouchTheAlbum;

    public AlbumAdapter(Context context,List<SingerAlbumModel.hotAlbums> hotAlbumsList) {
        this.context = context;
        this.hotAlbumsList = hotAlbumsList;
    }

    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_card,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumAdapter.ViewHolder holder, final int position) {
        SingerAlbumModel.hotAlbums hotAlbums = hotAlbumsList.get(position);
        Glide.with(context).load(hotAlbums.getPicUrl()).into(holder.album_img);
        holder.album_name.setText(hotAlbums.getName());
        holder.album_size.setText(String.valueOf(hotAlbums.getSize()));
        holder.album_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTouchTheAlbum.toTouchTheAlbum(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotAlbumsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView album_img;
        TextView album_name;
        TextView album_size;
        RelativeLayout album_next;
        public ViewHolder(View itemView) {
            super(itemView);
            album_img = itemView.findViewById(R.id.album_img);
            album_name = itemView.findViewById(R.id.album_name);
            album_size = itemView.findViewById(R.id.album_size);
            album_next = itemView.findViewById(R.id.album_next);
        }
    }

    public interface OnTouchTheAlbum {
        void toTouchTheAlbum(int position);
    }

    public void setOnTouchTheAlbum(OnTouchTheAlbum onTouchTheAlbum) {
        this.onTouchTheAlbum = onTouchTheAlbum;
    }
}
