package org.twitter.service.mappers;

import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.twitter.entity.user.User;
import org.twitter.entity.user.User.UserBuilder;
import org.twitter.service.dto.UserDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-10T15:05:49+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_282 (AdoptOpenJDK)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private PasswordEncoderMapper passwordEncoderMapper;

    @Override
    public User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.password( passwordEncoderMapper.encode( userDTO.getPassword() ) );
        user.username( userDTO.getUsername() );
        user.email( userDTO.getEmail() );
        user.firstName( userDTO.getFirstName() );
        user.lastName( userDTO.getLastName() );

        return user.build();
    }
}
