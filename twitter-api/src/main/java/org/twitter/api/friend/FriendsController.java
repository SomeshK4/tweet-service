package org.twitter.api.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.twitter.security.handlers.UserContextHandler;
import org.twitter.service.friend.IFriendService;

@RestController
@RequestMapping("/api/v1/friends")
public class FriendsController {

    private final UserContextHandler userContextHandler;
    private final IFriendService friendService;

    @Autowired
    public FriendsController(UserContextHandler userContextHandler, IFriendService friendService) {
        this.userContextHandler = userContextHandler;
        this.friendService = friendService;
    }

    @PostMapping("/add")
    public void createFriends(@RequestParam(value = "username") String userNameToFollow) {
        String userContextEmail = userContextHandler.getCurrentUserContext().getEmail();
        friendService.createFriends(userContextEmail, userNameToFollow);
    }

    @DeleteMapping("/remove")
    public void deleteFriends(@RequestParam(value = "username") String userNameToUnfollow) {
        String userContextEmail = userContextHandler.getCurrentUserContext().getEmail();
        friendService.deleteFriends(userContextEmail, userNameToUnfollow);
    }
}
