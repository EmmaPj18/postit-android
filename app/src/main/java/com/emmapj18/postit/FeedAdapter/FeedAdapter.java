package com.emmapj18.postit.FeedAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emmapj18.postit.Models.Feed;
import com.emmapj18.postit.Helpers.FirebaseHelper;
import com.emmapj18.postit.R;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private List<Feed> items;
    private Context context;

    public FeedAdapter(Context context) {
        this.items = null;
        this.context = context;
    }

    public void setItems(List<Feed> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

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
        FirebaseHelper.setImage(feed.imageUrl, context, holder._Image);
        holder._DateAdded.setText(feed.dateAdded);
        holder._User.setText(feed.user);
        holder._Location.setText(feed.location);
    }

    static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView _Image;
        TextView _Description, _DateAdded, _Location, _User;

        FeedViewHolder(View itemView) {
            super(itemView);
            _Image = itemView.findViewById(R.id.imageViewPost);
            _Description = itemView.findViewById(R.id.textViewDescription);
            _Location = itemView.findViewById(R.id.textViewLocation);
            _DateAdded = itemView.findViewById(R.id.textViewDateAdded);
            _User = itemView.findViewById(R.id.textViewUser);
        }
    }
}
