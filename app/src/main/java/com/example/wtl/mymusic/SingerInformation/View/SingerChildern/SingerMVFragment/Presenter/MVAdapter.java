package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.Presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.Model.MVModel;

import java.util.List;

/**
 * mv适配器
 * Created by WTL on 2018/7/21.
 */

public class MVAdapter extends RecyclerView.Adapter<MVAdapter.ViewHolder>{

    private Context context;
    private List<MVModel.mvs> mvModelList;
    private OnTouchSongsMV onTouchSongsMV;

    public MVAdapter(Context context,List<MVModel.mvs> mvModelList) {
        this.context = context;
        this.mvModelList = mvModelList;
    }

    @Override
    public MVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mv_card,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MVAdapter.ViewHolder holder, final int position) {
        MVModel.mvs mvs = mvModelList.get(position);
        Glide.with(context).load(mvs.getImgurl()).into(holder.mv_picture);
        holder.desc.setText(mvs.getName());
        holder.mvtouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTouchSongsMV.toTouchSongsMV(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mvModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mv_picture;
        TextView desc;
        LinearLayout mvtouch;

        public ViewHolder(View itemView) {
            super(itemView);
            mv_picture = itemView.findViewById(R.id.mv_picture);
            desc = itemView.findViewById(R.id.desc);
            mvtouch = itemView.findViewById(R.id.mvtouch);
        }
    }

    public interface OnTouchSongsMV {
        void toTouchSongsMV(int position);
    }

    public void setOnTouchSongsMV(OnTouchSongsMV onTouchSongsMV) {
        this.onTouchSongsMV = onTouchSongsMV;
    }
}
