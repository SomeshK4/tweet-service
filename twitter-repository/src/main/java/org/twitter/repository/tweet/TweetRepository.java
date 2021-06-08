package org.twitter.repository.tweet;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twitter.entity.tweet.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

}
