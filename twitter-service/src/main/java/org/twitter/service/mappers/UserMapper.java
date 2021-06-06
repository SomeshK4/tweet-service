package org.twitter.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.twitter.entity.user.User;
import org.twitter.service.annotations.EncodedMapping;
import org.twitter.service.dto.UserDTO;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {


    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User userDTOToUser(UserDTO userDTO);

}
