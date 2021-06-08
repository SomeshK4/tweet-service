package org.twitter.service.tweet;

import org.twitter.service.dto.TweetDTO;

public interface ITweetService {

    TweetDTO tweet(String userContextEmail, TweetDTO tweetDTO);
}
