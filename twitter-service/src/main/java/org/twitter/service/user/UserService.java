package org.twitter.service.user;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twitter.entity.user.User;
import org.twitter.repository.user.UserRepository;
import org.twitter.service.dto.UserDTO;
import org.twitter.service.mappers.UserMapper;

/**
 * @author somesh kumar
 */
@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public void createUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        userRepository.save(user);

    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

}
