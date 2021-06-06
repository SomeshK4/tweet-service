package org.twitter.service.follower;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twitter.entity.user.User;
import org.twitter.repository.user.UserRepository;
import org.twitter.service.dto.FollowersDTO;
import org.twitter.service.mappers.FollowersMapper;

@Service
public class FollowerService implements IFollowerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowersMapper followersMapper;


    @Override
    public List<FollowersDTO> getFollowers(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Username " + username + " not found"));

        return user.getFollowers().stream()
                .map(follower -> followersMapper.userToFollowersDTO(follower.getFollower()))
                .collect(Collectors.toList());
    }
}
