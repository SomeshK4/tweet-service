package org.twitter.service.tweet;

import java.util.List;
import org.twitter.service.dto.ReplyDTO;
import org.twitter.service.dto.TweetDTO;

public interface ITweetService {

    TweetDTO tweet(String userContextEmail, TweetDTO tweetDTO);

    void reply(Long tweetId, ReplyDTO replyDTO);

    List<TweetDTO> listTweetAndReplies(String username);
}
