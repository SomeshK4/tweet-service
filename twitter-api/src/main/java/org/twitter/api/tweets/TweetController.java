package org.twitter.api.tweets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.twitter.security.handlers.UserContextHandler;
import org.twitter.service.dto.TweetDTO;
import org.twitter.service.tweet.ITweetService;

@RestController
@RequestMapping("/api/v1/tweets")
public class TweetController {

    @Autowired
    private UserContextHandler userContextHandler;

    @Autowired
    private ITweetService tweetService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TweetDTO> tweet(@RequestBody TweetDTO tweetDTO) {
        String userContextEmail = userContextHandler.getCurrentUserContext().getEmail();
        TweetDTO tweet = tweetService.tweet(userContextEmail, tweetDTO);
        return ResponseEntity.ok(tweet);
    }
}
