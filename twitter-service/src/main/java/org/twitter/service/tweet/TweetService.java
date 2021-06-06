package org.twitter.service.tweet;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.twitter.entity.tweet.Tweet;
import org.twitter.entity.user.User;
import org.twitter.service.dto.TweetDTO;
import org.twitter.service.mappers.TweetMapper;
import org.twitter.service.user.IUserService;

@Service
public class TweetService implements ITweetService {

    @Autowired
    private IUserService userService;

    @Autowired
    private TweetMapper tweetMapper;

    @Override
    @Transactional
    public void tweet(String userContextEmail, TweetDTO tweetDTO) {
        Optional<User> contextUser = userService.getUserByEmail(userContextEmail);

        Tweet tweet = tweetMapper.tweetDTOToTweet(tweetDTO);
        contextUser.get().addTweet(tweet);

    }
}
