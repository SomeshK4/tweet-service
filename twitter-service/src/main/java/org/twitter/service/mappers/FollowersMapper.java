package org.twitter.service.mappers;

import org.mapstruct.Mapper;
import org.twitter.entity.user.User;
import org.twitter.service.dto.FollowersDTO;

@Mapper(componentModel = "spring")
public interface FollowersMapper {

    FollowersDTO userToFollowersDTO(User user);
}
