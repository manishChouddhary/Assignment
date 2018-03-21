package com.assignment.manish.movies.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.manish.movies.R;
import com.assignment.manish.movies.communication.IObserver;
import com.assignment.manish.movies.persistence.DBHelper;
import com.assignment.manish.movies.persistence.DataMovies;
import com.assignment.manish.movies.utils.Constants;
import com.assignment.manish.movies.utils.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity implements IObserver {

    private DataMovies dataMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (getIntent()!=null && getIntent().hasExtra(Constants.Movies.ID)) {
            ArrayList<DataMovies> list = DBHelper.getDBData(Constants.Movies.ID, getIntent().getStringExtra(Constants.Movies.ID), "", this);
            if (list.size()!=0) {
                initView(list.get(0));
            }
        }
    }

    private void initView(DataMovies dataMovies)
    {
        this.dataMovies = dataMovies;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(dataMovies.getTitle());
        ImageView iv = findViewById(R.id.iv);
        Glide.with(this)
                .load(Constants.Urls.IMAGE_BASE+dataMovies.getPoster_path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);

        TextView tvVotes = findViewById(R.id.tvVotes);
        tvVotes.setText(dataMovies.getVote_count());

        TextView tvPopularity = findViewById(R.id.tvPopularity);
        tvPopularity.setText(dataMovies.getPopularity());

        TextView tvVotesAvg = findViewById(R.id.tvVotesAvg);
        tvVotesAvg.setText(dataMovies.getVote_average());

        TextView tvOverview = findViewById(R.id.tvOverview);
        tvOverview.setText(dataMovies.getOverview());

        TextView tvReleaseDate = findViewById(R.id.tvReleaseDate);
        tvReleaseDate.setText(dataMovies.getRelease_date());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_details,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem itemFav = menu.findItem(R.id.action_fav);
        MenuItem itemWish = menu.findItem(R.id.action_wish);
        if (dataMovies!=null && dataMovies.getIs_fav()) {
            itemFav.setIcon(R.drawable.selected_favorite);
        }
        else
        {
            itemFav.setIcon(R.drawable.favorite);
        }
        if (dataMovies!=null && dataMovies.getIs_in_wishlist()) {
            itemWish.setIcon(R.drawable.selected_wishlist);
        }
        else
        {
            itemWish.setIcon(R.drawable.wishlist);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_fav :
                if (dataMovies!=null)
                {
                    if(dataMovies.getIs_fav()) {
                        dataMovies.setIs_fav(Constants.Helper_Values.VALUE_FALSE);
                    }
                    else
                    {
                        dataMovies.setIs_fav(Constants.Helper_Values.VALUE_TRUE);
                    }
                    DBHelper.updateData(dataMovies,this,this);
                    Utility.checkoutDB(this);
                }
                return true;
            case R.id.action_wish:
                if (dataMovies!=null)
                {
                    if(dataMovies.getIs_in_wishlist()) {
                        dataMovies.setIs_in_wishlist(Constants.Helper_Values.VALUE_FALSE);
                    }
                    else
                    {
                        dataMovies.setIs_in_wishlist(Constants.Helper_Values.VALUE_TRUE);
                    }
                    DBHelper.updateData(dataMovies,this,this);
                    Utility.checkoutDB(this);
                }
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onObserverCallBack(String identifier, String observeData) {
        if (identifier.equals(Constants.Helper_Values.MOVIE_INSERTED)) {
            invalidateOptionsMenu();
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        }
    }
}
