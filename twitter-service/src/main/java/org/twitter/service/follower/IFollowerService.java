package org.twitter.service.follower;

import java.util.List;
import org.twitter.service.dto.FollowersDTO;

public interface IFollowerService {

    List<FollowersDTO> getFollowers(String username);
}
