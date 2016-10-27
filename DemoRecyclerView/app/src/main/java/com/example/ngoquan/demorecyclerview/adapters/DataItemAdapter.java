package com.example.ngoquan.demorecyclerview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.ngoquan.demorecyclerview.R;
import com.example.ngoquan.demorecyclerview.models.DataItem;

import java.sql.Ref;
import java.util.List;

/**
 * Created by NGOQUAN on 7/26/2016.
 */
public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.MyViewHolder>{

    Context ct;
    private List<DataItem> arrList = null;
    public boolean showCheckBox = false;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(ct).inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DataItem dataItem = arrList.get(position);
        holder.title.setText(dataItem.getTitle());
        holder.genre.setText(dataItem.getGenre());
        holder.year.setText(dataItem.getYear());
        if (showCheckBox) {
            holder.checkBox.setVisibility(View.VISIBLE);
            if (dataItem.checked)
                holder.checkBox.setChecked(true);
            else
                holder.checkBox.setChecked(false);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, genre, year;
        public CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
        }


    }

    public DataItemAdapter(Context ct, List<DataItem> arrList) {
//            super(ct, );
        this.ct = ct;
        this.arrList = arrList;
    }

    public boolean setCheck(int pos, boolean value){
        if(arrList == null)
            return false;
        if(arrList.size() < pos)
            return false;
        arrList.get(pos).checked = value;
        return true;
    }



}
