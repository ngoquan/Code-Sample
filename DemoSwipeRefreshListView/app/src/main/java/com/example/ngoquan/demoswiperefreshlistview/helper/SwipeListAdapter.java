package com.example.ngoquan.demoswiperefreshlistview.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ngoquan.demoswiperefreshlistview.R;

import java.util.List;

/**
 * Created by NGOQUAN on 12/21/2015.
 */
public class SwipeListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<Movie> movieList;
    private String[] bgColors;

    public SwipeListAdapter(Activity activity, List<Movie> movieList) {
        this.activity = activity;
        this.movieList = movieList;
        bgColors = activity.getResources().getStringArray(R.array.movie_serial_bg);
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row, null);
        }
        TextView serial = (TextView) convertView.findViewById(R.id.serial);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        serial.setText(String.valueOf(movieList.get(position).id));
        title.setText(movieList.get(position).title);
        String color = bgColors[position % bgColors.length];
        serial.setBackgroundColor(Color.parseColor(color));
        return convertView;
    }
}
