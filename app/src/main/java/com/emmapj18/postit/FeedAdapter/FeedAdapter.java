package com.emmapj18.postit.FeedAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.emmapj18.postit.Helpers.ShareHelper;
import com.emmapj18.postit.Models.Feed;
import com.emmapj18.postit.Helpers.FirebaseHelper;
import com.emmapj18.postit.R;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> implements View.OnClickListener {

    private List<Feed> items;
    private Context context;
    private Fragment fragment;
    private Bitmap sharePhoto;

    public FeedAdapter(Context context, Fragment fragment) {
        this.items = null;
        this.context = context;
        this.fragment = fragment;
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
        sharePhoto = FirebaseHelper.downloadImage(feed.imageUrl);
        holder._Description.setText(feed.description);
        FirebaseHelper.setImage(feed.imageUrl, context, holder._Image);
        holder._DateAdded.setText(feed.dateAdded);
        holder._User.setText(feed.user);
        holder._Location.setText(feed.location);
        holder._ShareButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        ShareHelper.share(sharePhoto, fragment);
    }

    static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView _Image;
        TextView _Description, _DateAdded, _Location, _User;
        Button _ShareButton;

        FeedViewHolder(View itemView) {
            super(itemView);
            _Image = itemView.findViewById(R.id.imageViewPost);
            _Description = itemView.findViewById(R.id.textViewDescription);
            _Location = itemView.findViewById(R.id.textViewLocation);
            _DateAdded = itemView.findViewById(R.id.textViewDateAdded);
            _User = itemView.findViewById(R.id.textViewUser);
            _ShareButton = itemView.findViewById(R.id.shareButton);
        }


    }
}
