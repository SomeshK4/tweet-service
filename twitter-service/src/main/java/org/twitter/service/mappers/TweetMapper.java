package org.twitter.service.mappers;

import org.mapstruct.Mapper;
import org.twitter.entity.tweet.Tweet;
import org.twitter.service.dto.TweetDTO;

@Mapper(componentModel = "spring")
public interface TweetMapper {

    Tweet tweetDTOToTweet(TweetDTO tweetDTO);
}
