package com.upcomingmovies.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.upcomingmovies.R;
import com.upcomingmovies.adapter.CustomPagerAdapter;
import com.upcomingmovies.application.AppController;
import com.upcomingmovies.model.ImageList;
import com.upcomingmovies.parser.ParseImage;
import com.upcomingmovies.parser.ParseMovieDetails;
import com.upcomingmovies.utils.Constant;
import com.upcomingmovies.utils.ZoomOutPageTransformer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MovieDetailsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    String id, movieName;
    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    ArrayList<ImageList> imageLists;
    ParseImage parseImage;
    ParseMovieDetails parseMovieDetails;
    TextView txtTitle, txtOverview;
    RatingBar ratingBar;
    /*int[] mResources = {
            R.drawable.first,
            R.drawable.second,
            R.drawable.third,
            R.drawable.fourth,
            R.drawable.fifth,
            R.drawable.sixth
    };*/

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString(Constant.MOVIE_ID);
        movieName = bundle.getString(Constant.MOVIE_NAME);
        initToolbar();
        initViews();
        getImageList();
        getMovieDetails();
    }

    private void initViews() {
        txtTitle = (TextView) findViewById(R.id.title);
        txtOverview = (TextView) findViewById(R.id.overview);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    }

    private void getMovieDetails() {
        String movieDetailUrl = Constant.BASE_URL + "/" + id + "?api_key=b7cd3340a794e5a2f35e3abb820b497f";
        RequestQueue requestQueue = AppController.getInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, movieDetailUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseMovieDetails = new ParseMovieDetails(response);
                JSONObject jsonObject = parseMovieDetails.getJSONObject();
                String overview = parseMovieDetails.getOverview(jsonObject);
                String title = parseMovieDetails.getTitle(jsonObject);
                String popularity = parseMovieDetails.getPopularity(jsonObject);
                txtTitle.setText(title);
                txtOverview.setText(overview);
                ratingBar.setRating(Float.parseFloat(popularity));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle(movieName);
    }

    private void getImageList() {
        final RequestQueue requestQueue = AppController.getInstance().getRequestQueue();
        String imageUrl = Constant.BASE_URL + "/" + id + Constant.IMAGE_URL;
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, imageUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                imageLists = new ArrayList<>();
                parseImage = new ParseImage(response);
                JSONObject imageObject = parseImage.getJSONObject();
                JSONArray imageArr = parseImage.getJSONArr(imageObject);
                for (int imageCounter = 0; imageCounter < 6; imageCounter++) {
                    JSONObject arrObject = parseImage.getArrObject(imageArr, imageCounter);
                    setGetObject(arrObject);
                }
                setImageAdapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Is", "" + error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void setImageAdapter() {
        mCustomPagerAdapter = new CustomPagerAdapter(this, imageLists);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager, true);

        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setOnPageChangeListener(this);

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == imageLists.size()) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

       /* mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                // do transformation here
            }
        });*/
    }

    private void setGetObject(JSONObject arrObject) {
        ImageList imageList = new ImageList();
        String filePath = parseImage.getImagePath(arrObject);
        imageList.setImagepath(filePath);
        imageLists.add(imageList);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
