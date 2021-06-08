package org.twitter.service.tweet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twitter.entity.reply.Reply;
import org.twitter.entity.tweet.Tweet;
import org.twitter.entity.user.User;
import org.twitter.repository.tweet.TweetRepository;
import org.twitter.repository.user.UserRepository;
import org.twitter.service.dto.ReplyDTO;
import org.twitter.service.dto.TweetDTO;
import org.twitter.service.mappers.TweetMapper;
import org.twitter.service.user.IUserService;

@Service
public class TweetService implements ITweetService {

    private final IUserService userService;
    private final TweetRepository tweetRepository;
    private final  UserRepository userRepository;
    private final TweetMapper tweetMapper;

    @Autowired
    public TweetService(IUserService userService, TweetRepository tweetRepository, UserRepository userRepository,
            TweetMapper tweetMapper) {
        this.userService = userService;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.tweetMapper = tweetMapper;
    }

    @Override
    public TweetDTO tweet(String userContextEmail, TweetDTO tweetDTO) {
        Optional<User> contextUser = userService.getUserByEmail(userContextEmail);

        Tweet tweet = tweetMapper.tweetDTOToTweet(tweetDTO);
        tweet.setUser(contextUser.get());
        Tweet savedTweet = tweetRepository.save(tweet);
        return TweetDTO.builder().tweetId(savedTweet.getId()).build();
    }

    @Override
    @Transactional
    public void reply(Long tweetId, ReplyDTO replyDTO) {
        Optional<Tweet> tweet =  tweetRepository.findById(tweetId);

        Optional<User> user = userRepository.findById(replyDTO.getSenderId());

        Tweet replierTweet = Tweet.builder()
                .user(user.get())
                .post(replyDTO.getPost())
                .build();

        Tweet savedTweet = tweetRepository.save(replierTweet);

        Reply reply = Reply.builder().reply(savedTweet).build();
        tweet.get().addReply(reply);
    }

    @Override
    public List<TweetDTO> listTweetAndReplies(String username) {
        Optional<User> user = userService.getUserByUserName(username);

        List<TweetDTO> tweets = user.get().getTweets().stream().map(tweet -> {
            TweetDTO tweetDTO = TweetDTO.builder().tweetId(tweet.getId())
                    .post(tweet.getPost()).build();
            List<ReplyDTO> replyDTOS = tweet.getReplies().stream().map(reply -> ReplyDTO.builder().senderId(reply.getId())
                    .post(reply.getReply().getPost()).build()).collect(Collectors.toList());
            if (!replyDTOS.isEmpty()) {
                tweetDTO.setReplies(replyDTOS);
            }
            return tweetDTO;
        }).collect(Collectors.toList());

        return tweets;

    }


}
