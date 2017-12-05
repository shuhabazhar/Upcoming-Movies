package com.upcomingmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.upcomingmovies.R;
import com.upcomingmovies.adapter.UpcomingMoviesAdapter;
import com.upcomingmovies.application.AppController;
import com.upcomingmovies.model.UpcomingMovies;
import com.upcomingmovies.parser.ParseMovieObject;
import com.upcomingmovies.utils.ConnectivityReceiver;
import com.upcomingmovies.utils.Constant;
import com.upcomingmovies.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<UpcomingMovies> upcomingMoviesList;
    ParseMovieObject parseMovieObject;
    RecyclerView rvUpcomingMovies;
    Toolbar mToolbar;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initToolbar();
        if (ConnectivityReceiver.isConnected()) {
            getUpcomingMoviesList();
        } else {
            Toast.makeText(this, Constant.NO_INTERNET, Toast.LENGTH_SHORT).show();
        }
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void initViews() {
        rvUpcomingMovies = (RecyclerView) findViewById(R.id.rvUpcomingMovies);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        rvUpcomingMovies.setLayoutManager(llManager);
    }

    private void getUpcomingMoviesList() {
        utils = new Utils();
        utils.showProgressDialog(MainActivity.this, Constant.PLEASE_WAIT);
        String upcomingMovieUrl = Constant.BASE_URL + Constant.UPCOMING_MOVIE_URL;
        RequestQueue requestQueue = AppController.getInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, upcomingMovieUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                upcomingMoviesList = new ArrayList<>();
                parseMovieObject = new ParseMovieObject(response);
                JSONObject jsonObject = parseMovieObject.getJSONObject();
                JSONArray resultArr = parseMovieObject.getResultArr(jsonObject);
                for (int upcomingMovieCount = 0; upcomingMovieCount < resultArr.length(); upcomingMovieCount++) {
                    JSONObject arrObject = parseMovieObject.getArrObj(resultArr, upcomingMovieCount);
                    setGetData(arrObject);
                }
                initAdapter();
                utils.hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", ""+error);
                utils.hideProgressDialog();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void initAdapter() {
        UpcomingMoviesAdapter upcomingMoviesAdapter = new UpcomingMoviesAdapter(this, upcomingMoviesList);
//        Add Divider line
//        rvUpcomingMovies.addItemDecoration(new SimpleDividerItemDecoration(this));
        rvUpcomingMovies.setAdapter(upcomingMoviesAdapter);
    }

    private void setGetData(JSONObject arrObject) {
        UpcomingMovies upcomingMovies = new UpcomingMovies();
        String id = parseMovieObject.getMovieId(arrObject);
        String title = parseMovieObject.getTitle(arrObject);
        String posterImage = parseMovieObject.getPosterImage(arrObject);
        String releaseDate = parseMovieObject.getReleaseDate(arrObject);
        Boolean isAdult = parseMovieObject.getIsAdult(arrObject);
        upcomingMovies.setMovieId(id);
        upcomingMovies.setTitle(title);
        upcomingMovies.setPosterImage(posterImage);
        upcomingMovies.setReleaseDate(releaseDate);
        upcomingMovies.setIsAdult(isAdult);
        upcomingMoviesList.add(upcomingMovies);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                Intent intent = new Intent(this, InformationActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
