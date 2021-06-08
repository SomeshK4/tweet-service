package org.twitter.api.tweets;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.twitter.security.handlers.UserContextHandler;
import org.twitter.service.dto.ReplyDTO;
import org.twitter.service.dto.TweetDTO;
import org.twitter.service.tweet.ITweetService;

@RestController
@RequestMapping("/api/v1/tweets")
public class TweetController {

    @Autowired
    private UserContextHandler userContextHandler;

    @Autowired
    private ITweetService tweetService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TweetDTO> tweet(@RequestBody TweetDTO tweetDTO) {
        String userContextEmail = userContextHandler.getCurrentUserContext().getEmail();
        TweetDTO tweet = tweetService.tweet(userContextEmail, tweetDTO);
        return ResponseEntity.ok(tweet);
    }

    @PostMapping(value = "/{tweetId}/replies", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity reply(@PathVariable Long tweetId, @RequestBody ReplyDTO replyDTO) {
        tweetService.reply(tweetId, replyDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TweetDTO>> listTweets(@RequestParam String username) {
        List<TweetDTO> tweetDTOS = tweetService.listTweetAndReplies(username);
        return ResponseEntity.ok(tweetDTOS);
    }
}
