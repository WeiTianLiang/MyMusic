package com.example.wtl.mymusic.MyMusic.Presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wtl.mymusic.MyMusic.Model.MyMusicModel;
import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.Service.PlayingMusicService;

import java.util.List;

/**
 * 我的音乐适配器
 * Created by WTL on 2018/7/29.
 */

public class My_Music_Adapter extends RecyclerView.Adapter<My_Music_Adapter.ViewHolder>{

    private Context context;
    private List<MyMusicModel> adapterList;

    public My_Music_Adapter(Context context,List<MyMusicModel> adapterList) {
        this.context = context;
        this.adapterList = adapterList;
    }

    @Override
    public My_Music_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mysongcard,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final My_Music_Adapter.ViewHolder holder, int position) {
        final MyMusicModel model = adapterList.get(position);
        holder.my_songname.setText(model.getName());
        holder.my_songbrief.setText(model.getBrief());
        holder.my_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayingMusicService.class);
                intent.putExtra("My_Address",model.getAddress());
                context.startService(intent);
            }
        });
    }

    public void add(MyMusicModel model) {
        adapterList.add(0,model);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView my_songname;
        TextView my_songbrief;
        LinearLayout my_play;

        public ViewHolder(View itemView) {
            super(itemView);
            my_songname = itemView.findViewById(R.id.my_songname);
            my_songbrief = itemView.findViewById(R.id.my_songbrief);
            my_play = itemView.findViewById(R.id.my_play);
        }
    }
}
