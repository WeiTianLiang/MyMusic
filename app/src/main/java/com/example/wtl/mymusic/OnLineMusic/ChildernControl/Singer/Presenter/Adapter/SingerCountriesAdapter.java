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
 * 歌手城市适配器
 * Created by WTL on 2018/6/21.
 */

public class SingerCountriesAdapter extends RecyclerView.Adapter<SingerCountriesAdapter.ViewHolder> {

    private List<String> stringList;
    private Context context;
    private List<Boolean> booleanlist = new ArrayList<>();
    private OnGetCityLocat onGetCityLocat;

    public SingerCountriesAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) {
                booleanlist.add(true);
            } else {
                booleanlist.add(false);
            }
        }
    }

    @Override
    public SingerCountriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.textcarditem, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SingerCountriesAdapter.ViewHolder holder, final int position) {
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
                onGetCityLocat.getCityLocat(getLocat());
            }
        });
    }

    private String getLocat() {
        for(int i = 0 ; i < booleanlist.size() ; i++) {
            if(booleanlist.get(i)) {
                switch (i) {
                    case 0:
                        return "10";
                    case 1:
                        return "20";
                    case 2:
                        return "60";
                    case 3:
                        return "70";
                    case 4:
                        return "40";
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

    public interface OnGetCityLocat {
        void getCityLocat(String p);
    }

    public void setOnGetCityLocat(OnGetCityLocat onGetCityLocat) {
        this.onGetCityLocat = onGetCityLocat;
    }

}
