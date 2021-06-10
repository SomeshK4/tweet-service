package org.twitter.service.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import org.twitter.entity.reply.Reply;
import org.twitter.entity.reply.Reply.ReplyBuilder;
import org.twitter.entity.tweet.Tweet;
import org.twitter.entity.tweet.Tweet.TweetBuilder;
import org.twitter.service.dto.ReplyDTO;
import org.twitter.service.dto.TweetDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-10T15:05:49+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_282 (AdoptOpenJDK)"
)
@Component
public class TweetMapperImpl implements TweetMapper {

    @Override
    public Tweet tweetDTOToTweet(TweetDTO tweetDTO) {
        if ( tweetDTO == null ) {
            return null;
        }

        TweetBuilder tweet = Tweet.builder();

        tweet.post( tweetDTO.getPost() );
        tweet.replies( replyDTOListToReplyList( tweetDTO.getReplies() ) );

        return tweet.build();
    }

    protected Reply replyDTOToReply(ReplyDTO replyDTO) {
        if ( replyDTO == null ) {
            return null;
        }

        ReplyBuilder reply = Reply.builder();

        return reply.build();
    }

    protected List<Reply> replyDTOListToReplyList(List<ReplyDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Reply> list1 = new ArrayList<Reply>( list.size() );
        for ( ReplyDTO replyDTO : list ) {
            list1.add( replyDTOToReply( replyDTO ) );
        }

        return list1;
    }
}
