package org.twitter.service.mappers;

import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import org.twitter.entity.user.User;
import org.twitter.service.dto.FollowersDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-10T15:05:49+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_282 (AdoptOpenJDK)"
)
@Component
public class FollowersMapperImpl implements FollowersMapper {

    @Override
    public FollowersDTO userToFollowersDTO(User user) {
        if ( user == null ) {
            return null;
        }

        FollowersDTO followersDTO = new FollowersDTO();

        followersDTO.setUsername( user.getUsername() );
        followersDTO.setFirstName( user.getFirstName() );
        followersDTO.setLastName( user.getLastName() );

        return followersDTO;
    }
}
