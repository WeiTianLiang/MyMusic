package com.example.wtl.mymusic.ShowListereningMusic.Presenter.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.ShowListereningMusic.Model.LyricModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 歌词适配器
 * Created by WTL on 2018/7/1.
 */

public class LyricAdapter extends RecyclerView.Adapter<LyricAdapter.ViewHolder>{

    private List<LyricModel> modelList;
    private Context context;
    private List<Boolean> booleanList = new ArrayList<>();

    public LyricAdapter(List<LyricModel> modelList,Context context) {
        this.context = context;
        this.modelList = modelList;
        for(int i = 0 ; i < modelList.size() ; i++) {
            booleanList.add(false);
        }
    }

    @Override
    public LyricAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lyric_card,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LyricAdapter.ViewHolder holder, int position) {
        LyricModel model = modelList.get(position);
        holder.text.setText(model.getNode());
        if(booleanList.get(position)) {
            holder.text.setTextColor(context.getResources().getColor(R.color.songchange));
        } else {
            holder.text.setTextColor(context.getResources().getColor(R.color.songc));
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        TextView text1;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            text1 = itemView.findViewById(R.id.text1);
        }
    }

    public void setColor(int position) {
        for(int i = 0 ; i < modelList.size() ; i++) {
            booleanList.set(i,false);
        }
        booleanList.set(position,true);
        notifyDataSetChanged();
    }

}
