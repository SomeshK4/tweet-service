package org.twitter.entity.tweet;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.twitter.entity.audit.Auditable;
import org.twitter.entity.follower.Follower;
import org.twitter.entity.reply.Reply;
import org.twitter.entity.user.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tweets")
public class Tweet extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name ="post")
    private String post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    public void addReply(Reply reply) {
        replies.add(reply);
        reply.setTweet(this);
    }

}
