package com.emmapj18.postit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emmapj18.postit.FeedAdapter.FeedAdapter;
import com.emmapj18.postit.Helpers.FirebaseHelper;
import com.emmapj18.postit.Listeners.FeedListener;
import com.emmapj18.postit.Models.Feed;

import java.util.Comparator;
import java.util.List;

public class FeedFragment extends Fragment implements FeedListener{

    private FeedAdapter adapter;

    @Override
    public void onRetrieveFeeds(List<Feed> feeds) {

        feeds.sort(Comparator.comparing(Feed::getDateAdded).reversed());

        adapter.setItems(feeds);
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.feed_fragment, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewContent);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new FeedAdapter(rootView.getContext(), this);
        recyclerView.setAdapter(adapter);

        FirebaseHelper.getFeeds(this);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseHelper.getFeeds(this);
    }
}
