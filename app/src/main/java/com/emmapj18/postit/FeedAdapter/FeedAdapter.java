package com.emmapj18.postit.FeedAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.emmapj18.postit.Feed;
import com.emmapj18.postit.R;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private final List<Feed> items;
    private final Context context;

    public FeedAdapter(Context context, List<Feed> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemCount() { return items.size(); }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feed_card, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        Feed feed = items.get(position);
        holder._Description.setText(feed.description);
        Glide.with(context).load(feed.imageUrl).into(holder._Image);
    }

    static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView _Image;
        TextView _Description;

        FeedViewHolder(View itemView) {
            super(itemView);
            _Image = itemView.findViewById(R.id.imageViewPost);
            _Description = itemView.findViewById(R.id.textViewPostDescription);
        }
    }
}
