package org.twitter.service.tweet;

import org.twitter.service.dto.TweetDTO;

public interface ITweetService {

    void tweet(String userContextEmail, TweetDTO tweetDTO);
}
