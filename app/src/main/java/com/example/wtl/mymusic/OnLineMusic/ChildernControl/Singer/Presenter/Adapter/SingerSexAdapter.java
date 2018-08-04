package com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Presenter.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wtl.mymusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 歌手性别适配器
 * Created by WTL on 2018/6/21.
 */

public class SingerSexAdapter extends RecyclerView.Adapter<SingerSexAdapter.ViewHolder> {

    private List<String> stringList;
    private Context context;
    private List<Boolean> booleanlist = new ArrayList<>();
    private OnGetSexLocat onGetSexLocat;

    public SingerSexAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
        for (int i = 0; i < stringList.size(); i++) {
            if (i != 0) {
                booleanlist.add(false);
            } else {
                booleanlist.add(true);
            }
        }
    }

    @Override
    public SingerSexAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.textcarditem, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SingerSexAdapter.ViewHolder holder, final int position) {
        holder.text.setText(stringList.get(position));
        if(booleanlist.get(position)) {
            holder.text.setTextColor(context.getResources().getColor(R.color.singerChange));
        } else {
            holder.text.setTextColor(context.getResources().getColor(R.color.singerNo));
        }
        holder.texttouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < stringList.size(); i++) {
                    booleanlist.set(i, false);
                }
                booleanlist.set(position, true);
                notifyDataSetChanged();
                onGetSexLocat.getSexLocat(getLocat());
            }
        });
    }

    private String getLocat() {
        for(int i = 0 ; i < booleanlist.size() ; i++) {
            if(booleanlist.get(i)) {
                switch (i) {
                    case 0:
                        return "01";
                    case 1:
                        return "02";
                    case 2:
                        return "03";
                    default:
                        return null;
                }
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        LinearLayout texttouch;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            texttouch = itemView.findViewById(R.id.texttouch);
        }
    }

    public interface OnGetSexLocat {
        void getSexLocat(String p);
    }

    public void setOnGetSexLocat(OnGetSexLocat onGetSexLocat) {
        this.onGetSexLocat = onGetSexLocat;
    }
}
