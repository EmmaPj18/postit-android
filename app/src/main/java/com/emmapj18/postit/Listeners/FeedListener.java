package com.emmapj18.postit.Listeners;

import com.emmapj18.postit.Models.Feed;

import java.util.List;

public interface FeedListener {
    void onRetrieveFeeds(List<Feed> feeds);
}
