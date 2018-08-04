package com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Presenter.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Model.SingerModel;
import com.example.wtl.mymusic.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * listview展示适配器
 * Created by WTL on 2018/6/21.
 */

public class ListViewAdapter extends BaseAdapter {

    private List<SingerModel.artists> artistsList;
    private Context context;
    private OnTouchJumpActivity onTouchJumpActivity;

    public ListViewAdapter(Context context, List<SingerModel.artists> artistsList) {
        this.context = context;
        this.artistsList = artistsList;
    }

    @Override
    public int getCount() {
        return artistsList.size();
    }

    @Override
    public Object getItem(int i) {
        return artistsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        View itemview;
        if (view == null) {
            itemview = View.inflate(context, R.layout.singercarditem, null);
            holder = new ViewHolder(itemview);
            itemview.setTag(holder);
        } else {
            itemview = view;
            holder = (ViewHolder) itemview.getTag();
        }
        SingerModel.artists artists = artistsList.get(i);
        Glide.with(context).load(artists.getImg1v1Url()).into(holder.singer_head);
        holder.singer_name.setText(artists.getName());
        holder.singer_touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTouchJumpActivity.touchJumpActivity(i);
            }
        });
        return itemview;
    }

    class ViewHolder {
        private CircleImageView singer_head;
        private TextView singer_name;
        private RelativeLayout singer_touch;

        ViewHolder(View view) {
            singer_head = view.findViewById(R.id.singer_head);
            singer_name = view.findViewById(R.id.singer_name);
            singer_touch = view.findViewById(R.id.singer_touch);
        }
    }

    public interface OnTouchJumpActivity {
        void touchJumpActivity(int position);
    }

    public void setOnTouchJumpFragment(OnTouchJumpActivity onTouchJumpActivity) {
        this.onTouchJumpActivity = onTouchJumpActivity;
    }
}
