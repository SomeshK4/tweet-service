package org.twitter.api.friend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Friends", description = "Add/Remove Friends API")
public class FriendsController {

    private final UserContextHandler userContextHandler;
    private final IFriendService friendService;

    @Autowired
    public FriendsController(UserContextHandler userContextHandler, IFriendService friendService) {
        this.userContextHandler = userContextHandler;
        this.friendService = friendService;
    }

    @Operation(summary = "Add Followers")
    @PostMapping("/add")
    public void createFriends(@RequestParam(value = "username") String userNameToFollow) {
        String userContextEmail = userContextHandler.getCurrentUserContext().getEmail();
        friendService.createFriends(userContextEmail, userNameToFollow);
    }

    @Operation(summary = "Remove Followers")
    @DeleteMapping("/remove")
    public void deleteFriends(@RequestParam(value = "username") String userNameToUnfollow) {
        String userContextEmail = userContextHandler.getCurrentUserContext().getEmail();
        friendService.deleteFriends(userContextEmail, userNameToUnfollow);
    }
}
