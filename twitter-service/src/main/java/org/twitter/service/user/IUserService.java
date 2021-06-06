package org.twitter.service.user;

import java.util.Optional;
import org.twitter.entity.user.User;
import org.twitter.service.dto.UserDTO;

public interface IUserService {

    void createUser(UserDTO userDTO) ;

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByUserName(String userName);

}
