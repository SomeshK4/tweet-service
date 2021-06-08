package org.twitter.service.friend;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.twitter.entity.follower.Follower;
import org.twitter.entity.user.User;
import org.twitter.repository.user.UserRepository;
import org.twitter.service.user.IUserService;

/**
 * @author someshkumar
 */
@Service
public class FriendService implements IFriendService {


    private final IUserService userService;
    private final UserRepository userRepository;

    @Autowired
    public FriendService(IUserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public void createFriends(String userContextEmail, String userNameToFollow) {

        Optional<User> contextUser = userService.getUserByEmail(userContextEmail);
        Optional<User> userByUserName = userService.getUserByUserName(userNameToFollow);

        Follower follower = Follower.builder().follower(userByUserName.get())
                .build();
        contextUser.get().addFollower(follower);
    }

    @Override
    @Transactional
    public void deleteFriends(String userContextEmail, String userNameToUnfollow) {

        Optional<User> contextUser = userService.getUserByEmail(userContextEmail);

        Follower unfollower = contextUser.get().getFollowers().stream()
                        .filter(follower -> follower.getFollower().getUsername().equals(userNameToUnfollow))
                .findFirst()
                .get();

        contextUser.get().removeFollower(unfollower);
    }
}
