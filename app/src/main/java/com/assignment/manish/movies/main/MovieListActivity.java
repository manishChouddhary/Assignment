package com.assignment.manish.movies.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.assignment.manish.movies.R;
import com.assignment.manish.movies.communication.GetMoviesTask;
import com.assignment.manish.movies.communication.IObserver;
import com.assignment.manish.movies.persistence.DBHelper;
import com.assignment.manish.movies.persistence.DataMovies;
import com.assignment.manish.movies.utils.Constants;
import com.assignment.manish.movies.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity implements IObserver
{

    private MoviesAdapter adapter;
    private ArrayList<DataMovies> list;
    private ArrayList<DataMovies> listBanner;
    private MoviesAdapter adapterBanner;
    private boolean showMenu = false;
    private boolean showFav=false,showWish=false;
    private CollapsingToolbarLayout cloapsingTollbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        initViews();
        if (getIntent()!=null && getIntent().hasExtra(Constants.Movies.IS_IN_WISHLIST) || getIntent().hasExtra(Constants.Movies.IS_FAV)) {
            if (getIntent().hasExtra(Constants.Movies.IS_FAV)) {
                showFav = getIntent().getStringExtra(Constants.Movies.IS_FAV).equals("T");
            }
            if (getIntent().hasExtra(Constants.Movies.IS_IN_WISHLIST)) {
                showWish = getIntent().getStringExtra(Constants.Movies.IS_IN_WISHLIST).equals("T");
            }
            getFromDB();
        }else
        getMovies();
    }

    private void getFromDB() {
        cloapsingTollbar.setTitle(showFav?getString(R.string.action_fav):getString(R.string.action_wishlist));

        ArrayList<DataMovies> list= new ArrayList<>();
        if(showFav) {
        list =DBHelper.getDBData(Constants.Movies.IS_FAV, "'T'", "", this);
        }
        if(showWish)
        {
            list =DBHelper.getDBData(Constants.Movies.IS_IN_WISHLIST, "'T'", "", this);

        }
        adapter.notifyWithList(list,true);
        showMenu = true;
        invalidateOptionsMenu();

    }

    private void initViews() {
        RecyclerView rvBanner = findViewById(R.id.rvBanner);
        rvBanner.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL));
        listBanner = new ArrayList<>();
        adapterBanner = new MoviesAdapter(listBanner,this,R.layout.image_item);
        rvBanner.setAdapter(adapterBanner);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cloapsingTollbar = findViewById(R.id.toolbar_layout);
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this, 2,GridLayoutManager.VERTICAL,false));
        list = new ArrayList<>();
        adapter = new MoviesAdapter(list,this, R.layout.row_movie);
        rv.setAdapter(adapter);
    }

    private void getMovies() {
        new GetMoviesTask(this,this, Constants.Helper_Values.GET_MOVIE_LIST).execute(Constants.Urls.API);
    }

    @Override
    public void onObserverCallBack(String identifier, String observeData) {
        if (!observeData.isEmpty()) {

        switch (identifier)
        {
            case Constants.Helper_Values.GET_MOVIE_LIST:
                try {
                    showMenu = true;
                    JSONObject object = new JSONObject(observeData);
                    JSONArray jsonList = object.getJSONArray(Constants.Api_Result.MOVIES);
                    for (int i = 0; i < jsonList.length(); i++) {
                        DBHelper.insertMovie(new DataMovies(jsonList.getJSONObject(i)),this,this);
                        //list.add(new DataMovies(jsonList.getJSONObject(i)));
                    }
                    //adapter.notifyDataSetChanged();
                    Utility.checkoutDB(MovieListActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constants.Helper_Values.MOVIE_INSERTED:
                ArrayList<DataMovies> list = DBHelper.getDBData(Constants.Movies.ID, observeData, "", this);
                if(listBanner.size()!=10)
                {
                    adapterBanner.notifyWithList(list, false);
                }
                adapter.notifyWithList(list, false);

                break;
        }
        invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_movie_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem itemFav = menu.findItem(R.id.action_fav);
        MenuItem itemWish = menu.findItem(R.id.action_wish);

        if(showMenu)
        {
            MenuItem item = menu.findItem(R.id.action_settings);
            if (item!=null) {
                item.setVisible(true);
            }
            if (itemFav!=null) {
                itemFav.setVisible(true);
            }
            if (itemWish!=null) {
                itemWish.setVisible(true);
            }
        }
        if(showFav)
        {
            if (itemFav!=null) {
                itemFav.setVisible(false);
            }
        }
        if(showWish)
        {
            if (itemWish!=null) {
                itemWish.setVisible(false);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_name:
            {
                ArrayList<DataMovies> list;
                if (showFav) {
                    list= DBHelper.getDBData(Constants.Movies.IS_FAV, "'T'", Constants.Movies.TITLE, this);
                }
                else if(showWish)
                    list= DBHelper.getDBData(Constants.Movies.IS_IN_WISHLIST, "'T'", Constants.Movies.TITLE, this);
                else
                list= DBHelper.getDBData("", "", Constants.Movies.TITLE, this);
                adapter.notifyWithList(list,true);
            }
            return true;
            case R.id.action_popularity:
            {
                ArrayList<DataMovies> list;
                if (showFav) {
                    list = DBHelper.getDBData(Constants.Movies.IS_FAV, "'T'", Constants.Movies.POPULARITY+" desc", this);
                }else if(showWish)
                    list = DBHelper.getDBData(Constants.Movies.IS_IN_WISHLIST, "'T'", Constants.Movies.POPULARITY+" desc", this);
                else
                list = DBHelper.getDBData("", "", Constants.Movies.POPULARITY+" desc", this);
                adapter.notifyWithList(list,true);
            }
            return true;
            case R.id.action_release:
            {
                ArrayList<DataMovies> list;
                if (showFav) {
                    list = DBHelper.getDBData(Constants.Movies.IS_FAV, "'T'", Constants.Movies.RELEASE_DATE+" desc", this);
                }else if (showWish)
                    list = DBHelper.getDBData(Constants.Movies.IS_IN_WISHLIST, "'T'", Constants.Movies.RELEASE_DATE+" desc", this);
                else
                list = DBHelper.getDBData("", "", Constants.Movies.RELEASE_DATE+" desc", this);
                adapter.notifyWithList(list,true);
            }
            return true;
            case R.id.action_fav:
            {
                Intent intent = new Intent(this,MovieListActivity.class);
                intent.putExtra(Constants.Movies.IS_FAV,Constants.Helper_Values.VALUE_TRUE);
                startActivity(intent);
            }
                return true;
            case R.id.action_wish:
            {
                Intent intent = new Intent(this,MovieListActivity.class);
                intent.putExtra(Constants.Movies.IS_IN_WISHLIST,Constants.Helper_Values.VALUE_TRUE);
                startActivity(intent);
            }
            return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
}
