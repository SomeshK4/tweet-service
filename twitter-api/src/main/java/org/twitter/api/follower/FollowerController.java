package org.twitter.api.follower;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.twitter.service.dto.FollowersDTO;
import org.twitter.service.follower.FollowerService;

@RestController
@RequestMapping("/api/v1/followers")
@Tag(name = "Followers", description = "Follower's API")
public class FollowerController {


    @Autowired
    FollowerService followerService;

    @Operation(summary = "Get list of followers by username")
    @GetMapping
    public List<FollowersDTO> getFollowers(@RequestParam String username) {
        return followerService.getFollowers(username);
    }
}
