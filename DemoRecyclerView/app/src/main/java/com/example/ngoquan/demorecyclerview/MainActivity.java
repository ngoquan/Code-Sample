package com.example.ngoquan.demorecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.ngoquan.demorecyclerview.adapters.DataItemAdapter;
import com.example.ngoquan.demorecyclerview.models.DataItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private List<DataItem> dataItemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DataItemAdapter mAdapter;

    Button btnSelect, btnSelectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnSelect = (Button) findViewById(R.id.btn_select);
        btnSelectAll = (Button) findViewById(R.id.btn_selectAll);

        btnSelect.setOnClickListener(this);
        btnSelectAll.setOnClickListener(this);

        mAdapter = new DataItemAdapter(this, dataItemList);
        recyclerView.setHasFixedSize(true);
//        Style Listview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        style GridView
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        Setting divider for beautiful
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                DataItem dataItem = dataItemList.get(position);
//                Nếu check box đang show thì khi click vào 1 item, sẽ chỉ tác động tới checkBox đó
                if (mAdapter.showCheckBox) {
                    if (dataItem.checked) {
                        dataItem.checked = false;
                    } else {
                        dataItem.checked = true;
                    }
//                    Sau khi thay đổi thì gọi notifyDataSetChanged để đổi check trên giao diện
                    mAdapter.notifyDataSetChanged();
                }
//                Nếu check Box không show, thì click vào item sẽ hoạt động tùy ý...
                else {
                    Toast.makeText(MainActivity.this, "Short click: " + dataItem.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                DataItem dataItem = dataItemList.get(position);
                Toast.makeText(MainActivity.this, "Long click: " + dataItem.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                mAdapter.showCheckBox = mAdapter.showCheckBox == false ? true : false;
                mAdapter.notifyDataSetChanged();
                showValidWithCheckBox();
            }
        }));

        prepareMovieData();
    }


    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public void showValidWithCheckBox() {
        if (mAdapter.showCheckBox) {
            btnSelect.setText("Un select");
            btnSelectAll.setVisibility(View.VISIBLE);
        } else {
            btnSelect.setText("Select");
            btnSelectAll.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
//        Nếu đang show checkBox thì bỏ đi đã
        if (mAdapter.showCheckBox) {
            mAdapter.showCheckBox = false;
            mAdapter.notifyDataSetChanged();
        } else {
            super.onBackPressed();
        }
    }

    private void prepareMovieData() {

        int count = 1;

        DataItem dataItem = new DataItem(count, "Mad Max: Fury Road", "Action & Adventure", "2015");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "Inside Out", "Animation, Kids & Family", "2015");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "Shaun the Sheep", "Animation", "2015");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "The Martian", "Science Fiction & Fantasy", "2015");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "Mission: Impossible Rogue Nation", "Action", "2015");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "Up", "Animation", "2009");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "Star Trek", "Science Fiction", "2009");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "The LEGO DataItem", "Animation", "2014");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "Iron Man", "Action & Adventure", "2008");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "Aliens", "Science Fiction", "1986");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "Chicken Run", "Animation", "2000");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "Back to the Future", "Science Fiction", "1985");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "Raiders of the Lost Ark", "Action & Adventure", "1981");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "Goldfinger", "Action & Adventure", "1965");
        dataItemList.add(dataItem);

        count++;
        dataItem = new DataItem(count, "Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        dataItemList.add(dataItem);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select:
                mAdapter.showCheckBox = mAdapter.showCheckBox == false ? true : false;
                mAdapter.notifyDataSetChanged();
                showValidWithCheckBox();
                break;
            case R.id.btn_selectAll:
                DataItem.toggleCheckAll(dataItemList);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }
}
