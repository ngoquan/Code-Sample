package com.example.ngoquan.demoswiperefreshlistview.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ngoquan.demoswiperefreshlistview.R;
import com.example.ngoquan.demoswiperefreshlistview.app.MyApplication;
import com.example.ngoquan.demoswiperefreshlistview.helper.Movie;
import com.example.ngoquan.demoswiperefreshlistview.helper.SwipeListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String TAG = MainActivity.class.getSimpleName();
    private String URL_TOP_250 = "http://api.androidhive.info/json/imdb_top_250.php?offset=";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private SwipeListAdapter adapter;
    private List<Movie> movieList;

    private int offset = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        movieList = new ArrayList<>();
        adapter = new SwipeListAdapter(this, movieList);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                fetchMovies();
            }
        });

    }

    private void fetchMovies() {
//        show refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);
        String url = URL_TOP_250 + offset;
//        Volley's json array request object
        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                if (response.length() > 0) {
//                    looping through json and adding to movies list
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject movieObj = response.getJSONObject(i);
                            int rank = movieObj.getInt("rank");
                            String title = movieObj.getString("title");
                            Movie m = new Movie(rank, title);
                            movieList.add(0,m);
//                            updating offset value to highest value
                            if (rank > offset)
                                offset = rank;
                        } catch (JSONException e) {
                            Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
//                stop swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Server error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

//        Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
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
    public void onRefresh() {
        fetchMovies();
    }
}
