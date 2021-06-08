package org.twitter.service.tweet;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twitter.entity.tweet.Tweet;
import org.twitter.entity.user.User;
import org.twitter.repository.tweet.TweetRepository;
import org.twitter.service.dto.TweetDTO;
import org.twitter.service.mappers.TweetMapper;
import org.twitter.service.user.IUserService;

@Service
public class TweetService implements ITweetService {

    @Autowired
    private IUserService userService;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TweetMapper tweetMapper;

    @Override
    public TweetDTO tweet(String userContextEmail, TweetDTO tweetDTO) {
        Optional<User> contextUser = userService.getUserByEmail(userContextEmail);

        Tweet tweet = tweetMapper.tweetDTOToTweet(tweetDTO);
        tweet.setUser(contextUser.get());
        Tweet savedTweet = tweetRepository.save(tweet);
        return TweetDTO.builder().tweetId(savedTweet.getId()).build();
    }
}
