package com.assignment.manish.movies.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment.manish.movies.R;
import com.assignment.manish.movies.persistence.DataMovies;
import com.assignment.manish.movies.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.Serializable;
import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private int layout;
    private ArrayList<DataMovies> list;
    private Context context;

    public MoviesAdapter(ArrayList<DataMovies> list, Context context, int layout) {
        this.list = list;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.layout,parent,false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder holder, int position) {
        final DataMovies data = list.get(position);
        if (layout==R.layout.row_movie) {

            holder.tvVoteCount.setText(data.getVote_count());
            holder.tvPopularity.setText(data.getPopularity());
            holder.cvMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MovieDetailsActivity.class);
                    intent.putExtra(Constants.Movies.ID, data.getId());
                    Pair<View, String> p1 = Pair.create((View)holder.tvTitle, context.getString(R.string.app_name));
                    Pair<View, String> p2 = Pair.create((View)holder.iv,context.getString(R.string.movie_banner));
                    Pair<View, String> p3 = Pair.create(holder.ivVotes,context.getString(R.string.action_votes));
                    Pair<View, String> p4 = Pair.create(holder.ivPopularity,context.getString(R.string.action_popularity));
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((MovieListActivity)context, p1, p2,p3,p4);
                    context.startActivity(intent, options.toBundle());
                }
            });
            holder.tvTitle.setText(data.getTitle());
        }
        Glide.with(context)
                .load(Constants.Urls.IMAGE_BASE+data.getPoster_path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void notifyWithList(ArrayList<DataMovies> list, boolean doClear)
    {
        if (doClear) {
            this.list.clear();
        }
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {
        View ivVotes,ivPopularity;
        TextView tvTitle;
        ImageView iv;
        TextView tvVoteCount;
        TextView tvPopularity;
        CardView cvMain;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            if(layout==R.layout.row_movie) {
                tvVoteCount = itemView.findViewById(R.id.tvVotes);
                tvPopularity = itemView.findViewById(R.id.tvPopularity);
                cvMain = itemView.findViewById(R.id.cvMain);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                ivPopularity = itemView.findViewById(R.id.ivPopularity);
                ivVotes = itemView.findViewById(R.id.ivVotes);
            }
        }
    }

}
